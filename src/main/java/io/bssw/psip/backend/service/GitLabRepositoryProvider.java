package io.bssw.psip.backend.service;

import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GitLabRepositoryProvider extends AbstractRepositoryProvider {

    @Override
    public String getName() {
        return "GITLAB";
    }

    @Override
    public boolean login() throws IOException {
        return login("/oauth2/authorization/gitlab");
    }
    
}
