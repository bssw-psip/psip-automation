package io.bssw.psip.backend.service;

import org.springframework.stereotype.Service;

@Service
public class GitLabRepositoryProvider extends AbstractRepositoryProvider {

    @Override
    public String getName() {
        return "GITLAB";
    }

    @Override
    public boolean login() {
        return login("/oauth2/authorization/gitlab");
    }
    
}
