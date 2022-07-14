package io.bssw.psip.backend.service;

import org.springframework.stereotype.Service;

@Service
public class GitHubRepositoryProvider extends AbstractRepositoryProvider {

    @Override
    public String getName() {
        return "GITHUB";
    }

    @Override
    public boolean login() {
        return login("/oauth2/authorization/github");
    }
}
