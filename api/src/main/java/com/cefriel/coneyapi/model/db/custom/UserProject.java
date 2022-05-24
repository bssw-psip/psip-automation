package com.cefriel.coneyapi.model.db.custom;

import com.cefriel.coneyapi.model.db.entities.Project;

public class UserProject {

    String projectName;
    int accessLevel;
    
    public static UserProject of(Project p) {
    	UserProject up = new UserProject();
    	up.setProjectName(p.getName());
    	return up;
    }

    public UserProject(){}

    public String getProjectName() {
        return projectName;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }
}
