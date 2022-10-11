package io.bssw.psip.backend.service;

import java.util.Map;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Image;

public abstract class AbstractRepositoryProvider implements RepositoryProvider {
    private RepositoryProviderManager repositoryManager;

    @Override
    public Image getImage() {
        return null;
    }

    protected boolean login(String oauth2url) {
        repositoryManager.setProvider(this);
        /*
         * Save the current browser URL before redirecting to the OAuth 2.0 auth
         * URL. This will be used to restore the current view when redirecting
         * back to the application. This is needed because the standard Spring
         * Security success handler will not work with Vaadin.
         */
        UI.getCurrent().getPage().fetchCurrentURL(currentUrl -> {
            repositoryManager.setCurrentUrl(currentUrl.toString());
            UI.getCurrent().getUI().ifPresent(ui -> ui.getPage().setLocation(oauth2url));
        });
        return true;
    }

    @Override
    public boolean logout() {
        repositoryManager.setProvider(null);
        return true;
    }

    @Override
    public Map<String, String> getUserInfo() {
        return null;
    }

    @Override
    public String getFile(String owner, String repo, String ref, String path) {
        return null;
    }

    @Override
    public boolean putFile(String owner, String repo, String ref, String path, String message, String content) {
        return false;
    }

    public void setRepositoryManager(RepositoryProviderManager manager) {
        this.repositoryManager = manager;
    }
}
