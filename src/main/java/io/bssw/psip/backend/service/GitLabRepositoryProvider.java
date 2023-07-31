package io.bssw.psip.backend.service;

import io.bssw.psip.ui.views.Home;
import org.gitlab4j.api.*;
import org.gitlab4j.api.models.Branch;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.RepositoryFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

//NOTE: *** all methods requiring the "projectIdOrPath" argument (usually the first argument) may not require
// the GITLAB_URL constant to be concatenated, will know after testing ***

@Service
public class GitLabRepositoryProvider extends AbstractRepositoryProvider {

    private static final String GITLAB_URL = "https://code.ornl.gov/";
    private static final String COMMIT_MESSAGE_CREATE = "created .psip/history.json file and added the first survey score.";
    private static final String COMMIT_MESSAGE_UPDATE = "updated .psip/history.json file.";
    private static final String SURVEY_FILE = ".psip/survey.yml";
    public static final String HISTORY_FILE = ".psip/history.json";

    private static final Logger LOGGER = LoggerFactory.getLogger(GitLabRepositoryProvider.class);
    private GitLabApi gl;
    private RepositoryApi glRepo;
    private Branch glBranch;

    @Override
    public String getName() {
        return "gitlab";
    }
    @Override
    public boolean login() {
        if (gl == null) {
            OAuth2AccessToken token = getRepositoryManager().getAccessToken();
            gl = new GitLabApi(GITLAB_URL, token.getTokenValue());
        }
        return true;
    }
    public void logout() {
        gl = null;
        glRepo = null;
        glBranch = null;
    }
    @Override
    public void connect(String url, String branch) throws Exception {
        if (gl != null && url != null && branch != null) {
            glRepo = gl.getRepositoryApi();
            glBranch = glRepo.getBranch(GITLAB_URL + url, branch);
        }
    }
    @Override
    public void disconnect() {
        glRepo = null;
        glBranch = null;
    }

    @Override
    public boolean isConnected() {
        return glRepo != null && glBranch != null;
    }

    @Override
    public InputStream readFile(String path) throws IOException {
        if (isConnected()) {
            try {
                RepositoryFileApi repoFileApi = new RepositoryFileApi(gl);
                RepositoryFile repoFile = repoFileApi.getFile(GITLAB_URL + Home.getRepo(), path, Home.getBranch());
                return new ByteArrayInputStream(repoFile.getDecodedContentAsBytes());
            } catch (GitLabApiException e) {
                LOGGER.error(e.getLocalizedMessage());
                return null;
            }
        }
        throw new IOException("Could not read from " + SURVEY_FILE + " because there is no connection to the repo.");
    }
    @Override
    public void writeFile(String path, String contents) throws IOException {
        RepositoryFileApi repoFileApi = new RepositoryFileApi(gl);
        if (isConnected()) {
           try {
               RepositoryFile repoFile = repoFileApi.getFile(GITLAB_URL + Home.getRepo(), path, Home.getBranch());
               repoFile.setContent(contents);
               repoFile.setFilePath(path);
               repoFileApi.updateFile(GITLAB_URL + Home.getRepo(), repoFile, Home.getBranch(), COMMIT_MESSAGE_UPDATE);
               return;
           } catch (GitLabApiException e) {
               LOGGER.error(e.getLocalizedMessage());
           }
           try {
               RepositoryFile repoFile = new RepositoryFile();
               repoFile.setContent(contents);
               repoFileApi.createFile(GITLAB_URL + Home.getRepo(), repoFile, Home.getBranch(), COMMIT_MESSAGE_CREATE);
               return;
           } catch (GitLabApiException e) {
               LOGGER.error(e.getLocalizedMessage());
           }
       }
       throw new IOException("Could not write to " + HISTORY_FILE + " because there is no connection to the repo.");
    }
    @Override
    public boolean canProvideRepositories() {
        return false;
    }
    @Override
    public List<String> getRepositories() {
        List<String> repositories = new ArrayList<>();
        if (gl != null) {
            ProjectApi projectApi = gl.getProjectApi();
            try {
                List<Project> projectList = (List<Project>) projectApi.getProjects(gl.getDefaultPerPage());
                for (Project project : projectList) {
                    repositories.add(project.getName());
                }
            } catch (GitLabApiException e) {
                LOGGER.error("Error while trying to get the gitlab projects");
                throw new RuntimeException(e);
            }
        }
        return repositories;
    }
    @Override
    public List<String> getBranches(String repository) {
        //NOTE: really don't have a way to use this repository argument with this API
        List<String> branches = new ArrayList<>();
        if (glRepo != null) {
            try {
                List<Branch> branchList = glRepo.getBranches(GITLAB_URL + Home.getRepo());
                for (Branch branch : branchList) {
                    branches.add(branch.getName());
                }
            } catch (GitLabApiException e) {
                LOGGER.error("Error while trying to return the branches.");
//                throw new RuntimeException(e);
            }
        }
        return branches;
    }
    @Override
    public String getDefaultBranch(String repository) {
        String defaultBranch = "";
        try {
            List<Branch> branches = glRepo.getBranches(GITLAB_URL + Home.getRepo());
            for (Branch branch: branches) {
                if (branch.getDefault()) {
                    defaultBranch = branch.getName();
                    break;
                }
            }
            return defaultBranch;
        } catch (GitLabApiException e) {
            throw new RuntimeException(e);
        }
    }



}
