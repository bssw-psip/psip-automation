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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import com.vaadin.flow.spring.annotation.VaadinSessionScope;

import io.bssw.psip.backend.model.Activity;
import io.bssw.psip.backend.model.ActivityContent;

// Must be session scope to ensure only one service (and resulting entities) per session
@VaadinSessionScope 
@Service
public class ActivityService {
	private final Map<String, Activity> activityMap = new HashMap<String, Activity>();
	private List<Activity> activities;

	public List<Activity> getActivities() {
		if (activities == null) {
			InputStream inputStream = getClass().getResourceAsStream("/activities.yml");
			load(inputStream);
		}
		return activities;
	}
	
	public Activity getActivity(String name) {
		return activityMap.get(name);
	}
	
	public void setActivity(String name, Activity activity) {
		activityMap.put(name, activity);
	}

	public void load(InputStream inputStream) {
		Yaml yaml = new Yaml(new Constructor(ActivityContent.class));
		try {
			ActivityContent content = yaml.load(inputStream);
			activities = content.getActivities();
        } catch (Exception e) {
			activities = new ArrayList<>();
        	System.out.println(e.getLocalizedMessage());
        }
	}

}
