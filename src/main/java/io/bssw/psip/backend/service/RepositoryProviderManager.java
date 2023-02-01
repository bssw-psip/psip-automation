package io.bssw.psip.backend.service;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import io.bssw.psip.backend.model.Repository;
import io.bssw.psip.backend.model.RepositoryContent;

@Service
public class RepositoryProviderManager {
    @Autowired
    private OAuth2AuthorizedClientService clientService;
    @Autowired
    private List<RepositoryProvider> providerTypes;

    private List<Repository> repositories;
    private Map<String, RepositoryProvider> providers = new HashMap<>();
    private RepositoryProvider currentProvider;
    private String redirectUrl;

    /**
     * Get a list of all know repository providers.
     * 
     * @return list of repository providers
     */
    public List<Repository> getRepositories() {
        if (providers.isEmpty()) {
            for (RepositoryProvider provider : providerTypes) {
                providers.put(provider.getName().toLowerCase(), provider);
                AbstractRepositoryProvider base = (AbstractRepositoryProvider)provider;
                base.setRepositoryManager(this);
            }
        }
        if (repositories == null) {
            InputStream inputStream = getClass().getResourceAsStream("/repositories.yml");
            load(inputStream);
        
            for (Repository repository : repositories) {
                RepositoryProvider provider = providers.get(repository.getType());
                if (provider != null) {
                    repository.setProvider(provider);
                }
            }
        }
        return repositories;
    }

    public void load(InputStream inputStream) {
		Yaml yaml = new Yaml(new Constructor(RepositoryContent.class));
		try {
			RepositoryContent content = yaml.load(inputStream);
			repositories = content.getRepositories();
        } catch (Exception e) {
            repositories = new ArrayList<>();
        	System.out.println(e.getLocalizedMessage());
        }
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
     * Get the provider associated with the host from the URL.
     * 
     * @return provider
     */
    public RepositoryProvider getProvider(String url) {
        try {
            URL pathUrl = new URL(url);
            String host = pathUrl.getHost();
            for (Repository repository : repositories) {
                if (repository.getHost().equals(host)) {
                    return repository.getProvider();
                }
            }
            return null;
        } catch (MalformedURLException e) {
            return null;
        }
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
