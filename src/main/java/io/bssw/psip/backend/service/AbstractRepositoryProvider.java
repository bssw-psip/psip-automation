package io.bssw.psip.backend.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationListener;

import com.vaadin.flow.component.html.Image;

import io.bssw.psip.backend.model.ProviderConfiguration;
import io.bssw.psip.backend.service.events.AuthChangeEvent;
import io.bssw.psip.backend.service.events.AuthChangeEvent.Type;

public abstract class AbstractRepositoryProvider implements RepositoryProvider, ApplicationListener<AuthChangeEvent> {
    private ProviderConfiguration configuration;
    private RepositoryProviderManager repositoryManager;

    @Override
    public Image getImage() {
        return null;
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

    @Override
    public boolean login() {
        return true;
    }

    @Override
    public void logout() {
    }

    @Override
    public void connect(String url, String branch) throws Exception {
    }

    @Override
    public void disconnect() {
    }

    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public InputStream readFile(String path) throws IOException {
        return null;
    }

    @Override
    public void writeFile(String path, String contents) throws IOException {
    }

    @Override
    public List<String> getRepositories() {
        return null;
    }

    @Override
    public List<String> getBranches(String repository) {
        return null;
    }

    @Override
    public String getDefaultBranch(String repository) {
        return null;
    }

    protected ProviderConfiguration getConfiguration() {
        return configuration;
    }

    protected void setConfiguration(ProviderConfiguration repository) {
        this.configuration = repository;
    }

    protected RepositoryProviderManager getRepositoryManager() {
        return repositoryManager;
    }

    protected void setRepositoryManager(RepositoryProviderManager repositoryManager) {
        this.repositoryManager = repositoryManager;
    }

    @Override
    public void onApplicationEvent(AuthChangeEvent event) {
        if (event.getType() == Type.LOGIN) {
            login();
        } else {
            logout();
        }
        
    }
}
