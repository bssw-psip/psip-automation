package com.cefriel.coneyapi.model.db.custom;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class AnswerBlock {

    private int reteId;
    private String type;
    private String subtype;
    private String text;
    private String ofConversation;
    private int value;
    private int order;
    private int neo4jId;
    private int nextQuestionId;

    public int getReteId() {
        return reteId;
    }

    public int getNeo4jId() {
        return neo4jId;
    }

    public void getNeo4jId(int neo4jId) {
        this.neo4jId = neo4jId;
    }

    public int getNextQuestionId() {
        return nextQuestionId;
    }

    public void setNextQuestionId(int nextQuestionId) {
        this.nextQuestionId = nextQuestionId;
    }

    public String getType() {
        return type;
    }

    public String getSubtype() {
        return subtype;
    }

    public String getOfConversation() {
        return ofConversation;
    }

    public String getText() {
        if(text != null && text.length()>4 && text.substring(0, 4).equals("----")){
            return text.substring(4);
        }
        return text == null ? "" : text;
    }

    public int getValue() {
        return value;
    }

    public int getOrder() {
        return order;
    }
}
