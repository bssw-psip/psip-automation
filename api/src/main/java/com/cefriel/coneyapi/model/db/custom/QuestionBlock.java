package com.cefriel.coneyapi.model.db.custom;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class QuestionBlock implements Comparable<Object> {
    private int reteId;
    private String type;
    private String subtype;
    private String text;
    private String questionType;
    private String ofConversation;
    private int neo4jId;
    private int depth;

    private String tag;
    private int orderInConversation;
    private int answersAmount;

    public int getReteId() {
        return reteId;
    }

    public int getNeo4jId() {
        return neo4jId;
    }

    public Integer getDepth() {
        return depth;
    }

    @Override
    public int compareTo(Object o) {
        QuestionBlock qb = (QuestionBlock) o;
        return getDepth().compareTo(qb.getDepth());
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
        return text == null ? "" : text;
    }

    public String getQuestionType() {
        return questionType;
    }

    public int getOrderInConversation() {
        return orderInConversation;
    }

    public void setOrderInConversation(int orderInConversation) {
        this.orderInConversation = orderInConversation;
    }

    public int getAnswersAmount() {
        return answersAmount;
    }

    public void setAnswersAmount(int answersAmount) {
        this.answersAmount = answersAmount;
    }

    public String getTag() {
        return tag == null ? "" : tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
