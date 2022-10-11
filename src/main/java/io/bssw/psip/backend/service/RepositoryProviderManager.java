package io.bssw.psip.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Service;

@Service
public class RepositoryProviderManager {
    @Autowired
    private List<RepositoryProvider> providers;
    @Autowired
    private OAuth2AuthorizedClientService clientService;

    private RepositoryProvider currentProvider;

    private String currentUrl;

    /**
     * Get a list of all know repository providers.
     * 
     * @return list of repository providers
     */
    public List<RepositoryProvider> getProviders() {
        for (RepositoryProvider provider : providers) {
            AbstractRepositoryProvider base = (AbstractRepositoryProvider)provider;
            base.setRepositoryManager(this);
        }
        return providers;
    }

    /**
     * Set the chosen repository provider. 
     * 
     * @param provider provider selected by the user
     */
    public void setProvider(RepositoryProvider provider) {
        this.currentProvider = provider;
    }

    /**
     * Get the currently selected provider
     * 
     * @return currently selected provider
     */
    public RepositoryProvider getProvider() {
        return currentProvider;
    }

    /**
     * Set the current browser URL. Used to redirect back
     * to the currrent view when authentication is completed.
     * 
     * @param url
     */
    public void setCurrentUrl(String url) {
        this.currentUrl = url;
    }

    /**
     * Get the current browser URL.
     * 
     * @return url
     */
    public String getCurrentUrl() {
        return currentUrl;
    }

    /**
     * Check if the user is logged in to a provider.
     * 
     * @return true if logged in
     */
    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !"anonymousUser".equals(authentication.getPrincipal());
    }

    /**
     * Get the current access token. Only valid if {@link #isLoggedIn()} returns true.
     * 
     * @return access token
     */
    public OAuth2AccessToken getAccessToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken)authentication;
        if (authentication.getPrincipal() instanceof OAuth2AuthenticatedPrincipal) {
            OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal)authentication.getPrincipal();
            OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(authToken.getAuthorizedClientRegistrationId(), principal.getName());
            return client.getAccessToken();
        }
        return null;
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
}
