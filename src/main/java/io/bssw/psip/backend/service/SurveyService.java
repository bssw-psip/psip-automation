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

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.vaadin.flow.spring.annotation.VaadinSessionScope;

import io.bssw.psip.backend.model.Item;
import io.bssw.psip.backend.model.Survey;
import io.bssw.psip.backend.model.SurveyContent;

// Must be session scope to ensure only one service (and resulting entities) per session
@VaadinSessionScope 
@Service
public class SurveyService {
	private Survey survey;

	private final Map<String, Item> items = new HashMap<String, Item>();
	private final Map<String, Item> prevItems = new HashMap<String, Item>();
	private final Map<String, Item> nextItems = new HashMap<String, Item>();

	public Survey getSurvey() {
		if (survey == null) {
			load(getClass().getResourceAsStream("/assessment.yml"));
		}
		return survey;
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
	
	public Item getPrevItem(String path) {
		return prevItems.get(path);
	}
	
	public void setPrevItem(String path, Item item) {
		prevItems.put(path, item);
	}
	
	public void load(InputStream inputStream) {
		Yaml yaml = new Yaml(new Constructor(SurveyContent.class));
		try {
			SurveyContent content = yaml.load(inputStream);
			survey = content.getSurvey();
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
	}

}
