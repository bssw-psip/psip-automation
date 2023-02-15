package io.bssw.psip.backend.service;

import org.springframework.stereotype.Service;

@Service
public class GitLabRepositoryProvider extends AbstractRepositoryProvider {

    @Override
    public String getName() {
        return "gitlab";
    }

    @Override
    public boolean canProvideRepositories() {
        return false;
    }
}
