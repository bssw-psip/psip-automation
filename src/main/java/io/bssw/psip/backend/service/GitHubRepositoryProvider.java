package io.bssw.psip.backend.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.kohsuke.github.GHBranch;
import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.PagedIterator;
import org.kohsuke.github.PagedSearchIterable;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

@Service
public class GitHubRepositoryProvider extends AbstractRepositoryProvider {
    private GitHub gh;
    private GHRepository ghRepo;
    private GHBranch ghBranch;

    @Override
    public String getName() {
        return "github";
    }

    @Override
    public boolean login() {
        if (gh == null) {
            OAuth2AccessToken token = getRepositoryManager().getAccessToken();
            try {
                gh = GitHub.connectUsingOAuth(token.getTokenValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public void logout() {
        gh = null;
        ghRepo = null;
        ghBranch = null;
    }

    @Override
    public void connect(String url, String branch) throws Exception {
        if (gh != null && url != null && branch != null) {
            ghRepo = gh.getRepository(url);
            ghBranch = ghRepo.getBranch(branch);
        }
    }

    @Override
    public void disconnect() {
        ghRepo = null;
        ghBranch = null;    
    }

    @Override
    public boolean isConnected() {
        return ghBranch != null;
    }

    @Override
    public InputStream getSurveyFile() throws IOException {
        if (ghRepo != null && ghBranch != null) {
            GHContent survey = ghRepo.getFileContent(".psip/survey.yml", ghBranch.getName());
            return survey.read();
        }
        throw new IOException("Not connected");
    }

    @Override
    public boolean canProvideRepositories() {
        // For now, until we can work out how to get a reasonable list
        return false;
    }

    public List<String> getRepositories() {
        if (gh != null) {
            try {
                List<String> repos = new ArrayList<>();
                PagedIterator<GHRepository> i = gh.getMyself().listRepositories().iterator();
                while (i.hasNext()) {
                    List<GHRepository> p = i.nextPage();
                    System.out.println("got pagelen = " + p.size());
                    for (GHRepository repo : p) {
                        repos.add(repo.getFullName());
                    }
                }
                return repos;
            } catch (IOException e) {
            }
        }
        return new ArrayList<>();
    }

    public List<String> getBranches(String repository) {
        List<String> branches = new ArrayList<>();
        if (gh != null) {
            try {
                GHRepository repo = gh.getRepository(repository);
                for (String branch : repo.getBranches().keySet()) {
                    branches.add(branch);
                }
            } catch (IOException e) {
                // fall through
            }
        }
        return branches;
    }

    public String getDefaultBranch(String repository) {
        if (gh != null) {
            try {
                GHRepository repo = gh.getRepository(repository);
                return repo.getDefaultBranch();
            } catch (IOException e) {
                // fall through
            }
        }
        return "";
    }

    public List<String> findRepositories(String query) {
        PagedSearchIterable<GHRepository> repos = gh.searchRepositories().q(query).q("in:name").list();
        System.out.println("count="+repos.getTotalCount());
        List<String> res = new ArrayList<>();
        for (GHRepository repo : repos) {
            res.add(repo.getName());
            System.out.println("repo="+repo.getFullName());
        }
        return res;
    }
}
