package io.bssw.psip.backend.service;

import static org.yaml.snakeyaml.env.EnvScalarConstructor.ENV_FORMAT;
import static org.yaml.snakeyaml.env.EnvScalarConstructor.ENV_TAG;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.env.EnvScalarConstructor;

import com.vaadin.flow.component.UI;

import io.bssw.psip.backend.model.Repository;
import io.bssw.psip.backend.model.RepositoryContent;

@Service
public class RepositoryProviderManager {
    private List<Repository> repositories;
    private Repository currentRepository;
    private String redirectUrl;

    /**
     * Get a list of all know repository providers.
     * 
     * @return list of repository providers
     */
    public List<Repository> getRepositories() {
        if (repositories == null) {
            InputStream inputStream = getClass().getResourceAsStream("/repositories.yml");
            load(inputStream);
        }
        return repositories;
    }

    private void load(InputStream inputStream) {
		Yaml yaml = new Yaml(new EnvScalarConstructor(new TypeDescription(RepositoryContent.class),
        new ArrayList<TypeDescription>(), new LoaderOptions()));
        yaml.addImplicitResolver(ENV_TAG, ENV_FORMAT, "$");
		try {
			RepositoryContent content = yaml.load(inputStream);
			repositories = content.getRepositories();
        } catch (Exception e) {
            repositories = new ArrayList<>();
        	System.out.println(e.getLocalizedMessage());
        }
	}

    public Repository getRepository() {
        return currentRepository;
    }

    public void setRepository(Repository currenRepository) {
        this.currentRepository = currenRepository;
    }

    /**
     * Set the browser URL used to redirect back
     * to the currrent view when authentication is completed.
     * 
     * @param url
     */
    public void setRedirectUrl(String url) {
        this.redirectUrl = url;
    }

    /**
     * Get the browser URL to redirect to after authentication.
     * 
     * @return url
     */
    public String getRedirectUrl() {
        return redirectUrl;
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
    // public OAuth2AccessToken getAccessToken() {
    //     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //     OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken)authentication;
    //     if (authentication.getPrincipal() instanceof OAuth2AuthenticatedPrincipal) {
    //         OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal)authentication.getPrincipal();
    //         OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(authToken.getAuthorizedClientRegistrationId(), principal.getName());
    //         return client.getAccessToken();
    //     }
    //     return null;
    // }

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

    public boolean login(Repository repository) {
        setRepository(repository);
        /*
         * Save the current browser URL before redirecting to the OAuth 2.0 auth
         * URL. This will be used to restore the current view when redirecting
         * back to the application. This is needed because the standard Spring
         * Security success handler will not work with Vaadin.
         */
        UI.getCurrent().getPage().fetchCurrentURL(currentUrl -> {
            setRedirectUrl(currentUrl.toString());
            UI.getCurrent().getUI().ifPresent(ui -> ui.getPage().setLocation("/oauth2/authorization/" + getRepository().getRegistrationId()));
        });
        return true;
    }

    public boolean logout() {
        setRepository(null);
        return true;
    }
}
