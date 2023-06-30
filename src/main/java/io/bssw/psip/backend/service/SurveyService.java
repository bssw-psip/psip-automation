/******************************************************************************
 *
 * Copyright 2020-, UT-Battelle, LLC. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *******************************************************************************/
package io.bssw.psip.backend.service;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.YAMLException;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.bssw.psip.backend.model.Category;
import io.bssw.psip.backend.model.CategoryScore;
import io.bssw.psip.backend.model.Item;
import io.bssw.psip.backend.model.ItemScore;
import io.bssw.psip.backend.model.Survey;
import io.bssw.psip.backend.model.SurveyContent;
import io.bssw.psip.backend.model.SurveyScore;
import io.bssw.psip.backend.service.events.AuthChangeEvent;
import io.bssw.psip.backend.model.SurveyHistory;

// Must be session scope to ensure only one service (and resulting entities) per session
// @VaadinSessionScope
@Service
public class SurveyService implements ApplicationListener<AuthChangeEvent> {
    private static final String SURVEY_FILE = ".psip/survey.yml";
    private static final String HISTORY_FILE = ".psip/history.json";

    @Autowired
    private RepositoryProviderManager repositoryManager;

    private Survey survey;
    private SurveyHistory history;

    private final Map<String, Item> items = new HashMap<String, Item>();
    private final Map<String, Item> prevItems = new HashMap<String, Item>();
    private final Map<String, Item> nextItems = new HashMap<String, Item>();

    public Survey getSurvey() {
        if (survey == null) {
            loadSurvey();
            loadSurveyHistory();
        }
        return survey;
    }

    public SurveyHistory getHistory() {
        return history;
    }

    public String getTimestamp(int index) {
        return history.getScores().get(index).getTimestamp();
    }

    public void reset() {
        survey = null;
    }

    public Item getItem(String path) {
        return items.get(path);
    }

    public void setItem(String path, Item item) {
        items.put(path, item);
    }

    public Item getNextItem(String path) {
        return nextItems.get(path);
    }

    public void setNextItem(String path, Item item) {
        nextItems.put(path, item);
    }

    public void setDate(String timestamp, int index) {
        history.getScores().get(index).setTimestamp(timestamp);
    }

    public Item getPrevItem(String path) {
        return prevItems.get(path);
    }

    public void setPrevItem(String path, Item item) {
        prevItems.put(path, item);
    }

    public Survey load(InputStream inputStream) throws YAMLException {
        Yaml yaml = new Yaml(new Constructor(SurveyContent.class));
        SurveyContent content = yaml.load(inputStream);
        return content.getSurvey();
    }

    /*
     * (Re)load a new survey from a repository. Assumes that the repository
     * provider is connected. If not it will load the default survey.
     */
    private void loadSurvey() throws YAMLException {
        items.clear();
        prevItems.clear();
        nextItems.clear();
        InputStream stream = null;
        if (repositoryManager.isLoggedIn()) {
            RepositoryProvider provider = repositoryManager.getRepositoryProvider();
            try {
                stream = provider.readFile(SURVEY_FILE);
            } catch (IOException e) {
                // Just use default file
            }
        }
        if (stream == null) {
            stream = getClass().getResourceAsStream("/assessment.yml");
        }
        survey = load(stream);
    }

    private void loadSurveyHistory() {
        history = new SurveyHistory();

        InputStream stream = null;
        if (repositoryManager.isLoggedIn()) {
            RepositoryProvider provider = repositoryManager.getRepositoryProvider();
            try {
                stream = provider.readFile(HISTORY_FILE);
            } catch (IOException e) {
                // Just use default file
            }
        }
        if (stream != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                history = mapper.readValue(stream, SurveyHistory.class);
            } catch (IOException e) {
            }
        }
    }



    public SurveyScore generateScore(Survey survey) {
        SurveyScore surveyScore = new SurveyScore();
        surveyScore.setVersion(survey.getVersion());
        surveyScore.setTimestamp(Instant.now().toString());
        Iterator<Category> categoryIter = survey.getCategories().iterator();
        while (categoryIter.hasNext()) {
            Category category = categoryIter.next();
            CategoryScore catScore = new CategoryScore();
            catScore.setPath(category.getPath());
            Iterator<Item> itemIter = category.getItems().iterator();
            while (itemIter.hasNext()) {
                Item item = itemIter.next();
                ItemScore score = new ItemScore();
                score.setPath(item.getPath());
                score.setValue(item.getScore().orElse(0).toString());
                catScore.getItemScores().add(score);
            }
            surveyScore.getCategoryScores().add(catScore);
        }
        return surveyScore;
    }

    /*
     * Save the current survey values into the history and
     * write out to the repository
     */
    public void saveSurveyHistory(Survey survey) throws Exception {
        SurveyScore score = generateScore(survey);
        history.addScore(score);
        ObjectMapper mapper = new ObjectMapper();
        String value = mapper.writeValueAsString(history);
        if (repositoryManager.isLoggedIn()) {
            RepositoryProvider provider = repositoryManager.getRepositoryProvider();
            provider.writeFile(HISTORY_FILE, value);
        } else {
            throw new IOException("Not logged in");
        }
    }

    @Override
    public void onApplicationEvent(AuthChangeEvent event) {
        reset();
    }
}