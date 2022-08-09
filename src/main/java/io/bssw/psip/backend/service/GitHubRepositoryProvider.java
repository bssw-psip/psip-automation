package io.bssw.psip.backend.service;

import org.springframework.stereotype.Service;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.kohsuke.github.GHMeta;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

import java.io.IOException;

@Service
public class GitHubRepositoryProvider extends AbstractRepositoryProvider {
    private GitHub github;

    @Override
    public String getName() {
        return "GITHUB";
    }

    @Override
    public boolean login() throws IOException {
        boolean loginSuccess = login("/oauth2/authorization/github");
        if (!loginSuccess) {
            return false;
        }
        OAuth2AccessToken token = repositoryManager.getAccessToken();
        github = new GitHubBuilder().withJwtToken(token.toString()).build();
        System.out.println(github.getMeta().toString());
        return true;
    }

}
