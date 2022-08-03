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

    public List<RepositoryProvider> getProviders() {
        for (RepositoryProvider provider : providers) {
            AbstractRepositoryProvider base = (AbstractRepositoryProvider)provider;
            base.setRepositoryManager(this);
        }
        return providers;
    }

    public void setProvider(RepositoryProvider provider) {
        this.currentProvider = provider;
    }

    public RepositoryProvider getProvider() {
        return currentProvider;
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !"anonymousUser".equals(authentication.getPrincipal());
    }

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

    public <A> A getAttribute(String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof OAuth2AuthenticatedPrincipal) {
            OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal)authentication.getPrincipal();
            return principal.getAttribute(name);
        }
		return null;
	}
}
