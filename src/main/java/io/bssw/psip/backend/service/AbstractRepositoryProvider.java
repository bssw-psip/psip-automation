package io.bssw.psip.backend.service;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Image;

public abstract class AbstractRepositoryProvider implements RepositoryProvider {
    private RepositoryProviderManager repositoryManager;

    @Override
    public Image getImage() {
        return null;
    }

    protected boolean login(String oauth2url) {
        UI.getCurrent().getUI().ifPresent(ui -> ui.getPage().setLocation(oauth2url));
        repositoryManager.setProvider(this);
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
