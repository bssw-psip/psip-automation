package com.cefriel.coneyapi.model.db.entities;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
public class Tag {
    @Id
    @GeneratedValue
    Long id;

    String text;
    int blockId;
    String conversationId;
    String templateId;


    public Tag(){
    }

    public Tag(String text){
        this.text = text;
    }
    public Tag(String text, int blockId, String conversationId){
        this.text = text;
        this.blockId = blockId;
        this.conversationId = conversationId;
    }

    public Tag(String text, String templateId){
        this.text = text;
        this.templateId = templateId;
    }

    public String getText() {
        return text;
    }

    public int getBlockId() {
        return blockId;
    }

    public String getConversationId() {
        return conversationId;
    }
}
