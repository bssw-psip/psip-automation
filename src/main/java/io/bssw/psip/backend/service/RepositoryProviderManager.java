package io.bssw.psip.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Service;

@Service
public class RepositoryProviderManager {
    @Autowired
    private List<RepositoryProvider> providers;

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

    public <A> A getAttribute(String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof OAuth2AuthenticatedPrincipal) {
            OAuth2AuthenticatedPrincipal principal = (OAuth2AuthenticatedPrincipal)authentication.getPrincipal();
            return principal.getAttribute(name);
        }
		return null;
	}
}
