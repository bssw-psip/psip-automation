package io.bssw.psip.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinServletRequest;

import io.bssw.psip.backend.model.ProviderConfiguration;
import io.bssw.psip.backend.service.events.AuthChangeEvent;
import io.bssw.psip.backend.service.events.AuthChangeEvent.Type;

@Service
public class RepositoryProviderManager implements ApplicationEventPublisherAware {
    @Autowired
    private List<RepositoryProvider> repositoryProviders;
    @Autowired
    private ProviderService providerService;
    @Autowired
    private OAuth2AuthorizedClientService clientService;

    private ApplicationEventPublisher publisher;
    private ProviderConfiguration currentConfiguration;
    private RepositoryProvider currentProvider;
    private String redirectUrl;

    private RepositoryProvider findProviderByType(ProviderConfiguration configuration) {
        for (RepositoryProvider provider : repositoryProviders) {
            if (provider.getName().equalsIgnoreCase(configuration.getType()) &&
                    provider instanceof AbstractRepositoryProvider) {
                ((AbstractRepositoryProvider)provider).setConfiguration(configuration);
                ((AbstractRepositoryProvider)provider).setRepositoryManager(this);
                return provider;
            }
        }
        return null;
    }

    public List<ProviderConfiguration> getProviderConfigurations() {
        return providerService.getProviderConfigurations();
    }

    private ProviderConfiguration getProviderConfiguration() {
        return currentConfiguration;
    }

    private void setProviderConfiguration(ProviderConfiguration configuration) {
        this.currentConfiguration = configuration;
        this.currentProvider = configuration != null ? findProviderByType(configuration) : null;
    }

    public RepositoryProvider getRepositoryProvider() {
        return currentProvider;
    }

    /**
     * Set the browser URL used to redirect back
     * to the currrent view when authentication is completed.
     * 
     * @param url
     */
    private void setRedirectUrl(String url) {
        this.redirectUrl = url;
    }

    /**
     * Get the browser URL to redirect to after authentication.
     * 
     * @return url
     */
    private String getRedirectUrl() {
        return redirectUrl;
    }

    /**
     * Check if the user is logged in to a provider.
     * 
     * @return true if logged in
     */
    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && !"anonymousUser".equals(authentication.getPrincipal());
    }

    /**
     * Get an attribute from the provider. Only valid if {@link #isLoggedIn()} is true.
     * 
     * @param <A>
     * @param name
     * @return
     */
    public <A> A getAttribute(String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof OAuth2AuthenticatedPrincipal) {
            OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal)authentication.getPrincipal();
            return principal.getAttribute(name);
        }
		return null;
	}

    /*
     * Entrypoint for OAuth2 login. This initates the OAuth2 flow and will result in calls to either
     * #loginSuccessful() or loginFailure().
     */
    public boolean login(ProviderConfiguration configuration) {
        setProviderConfiguration(configuration);
        /*
         * Save the current browser URL before redirecting to the OAuth 2.0 auth
         * URL. This will be used to restore the current view when redirecting
         * back to the application. This is needed because the standard Spring
         * Security success handler will not work with Vaadin.
         */
        UI.getCurrent().getPage().fetchCurrentURL(currentUrl -> {
            setRedirectUrl(currentUrl.toString());
            UI.getCurrent().getUI().ifPresent(ui -> {
                ui.getPage().setLocation("/oauth2/authorization/" + getProviderConfiguration().getRegistrationId());
            });
        });
        return true;
    }

    /*
     * Logs out of an OAuth2 session.
     */
    public boolean logout() {
        if (currentProvider != null) {
            currentProvider.logout();
        }
        publisher.publishEvent(new AuthChangeEvent(this, Type.LOGOUT));
        setProviderConfiguration(null);
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(
            VaadinServletRequest.getCurrent().getHttpServletRequest(), null, null);
        return true;
    }

    /*
     * Callback when OAuth2 login is successful
     */
    public String loginSuccessful() {
        if (currentProvider != null) {
            currentProvider.login();
        }
        publisher.publishEvent(new AuthChangeEvent(this, Type.LOGIN));
        String url = getRedirectUrl();
        setRedirectUrl(null);
        return url;
    }

    /*
     * Callback when OAuth2 login is unsuccessful
     */
    public void loginFailure() {
        setProviderConfiguration(null);
    }

     /**
     * Get the current access token. Only valid if {@link #isLoggedIn()} returns true.
     * 
     * @return access token
     */
    protected OAuth2AccessToken getAccessToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken)authentication;
        if (authentication.getPrincipal() instanceof OAuth2AuthenticatedPrincipal) {
            OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal)authentication.getPrincipal();
            OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(authToken.getAuthorizedClientRegistrationId(), principal.getName());
            return client.getAccessToken();
        }
        return null;
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }
}
