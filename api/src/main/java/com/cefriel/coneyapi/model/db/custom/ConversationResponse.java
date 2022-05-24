package com.cefriel.coneyapi.model.db.custom;

import com.cefriel.coneyapi.model.db.entities.Conversation;

public class ConversationResponse {

    String conversationId;
    String projectName;
    String status;
    String conversationTitle;
    int accessLevel;
    
    public static ConversationResponse of(Conversation c) {
    	ConversationResponse resp = new ConversationResponse();
    	resp.setAccessLevel(c.getAccessLevel());
    	resp.setConversationId(c.getConversationId());
    	resp.setConversationTitle(c.getTitle());
    	resp.setStatus(c.getStatus());
    	return resp;
    }
    
    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConversationTitle() {
        return conversationTitle;
    }

    public void setConversationTitle(String conversationTitle) {
        this.conversationTitle = conversationTitle;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(int accessLevel) {
        this.accessLevel = accessLevel;
    }
}
