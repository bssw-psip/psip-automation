package com.cefriel.coneyapi.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import com.cefriel.coneyapi.model.db.entities.Conversation;

@Repository
public interface DataRepository extends Neo4jRepository<Conversation, Long> {

    @Query("MATCH (cust:Customer {username: $0})-[wo:WORKS_ON]->(pr:Project), " +
            "(pr)<-[bt:BELONGS_TO]-(c:Conversation {conv_id:$1}) " +
            "RETURN EXISTS ( (cust)-[wo:WORKS_ON]-(pr)-[bt]-(c) ) AND wo.access_level >= c.access_level")
    String hasUserPermission(String username, String conversationId);

    @Query("MATCH (c:Conversation {conv_id:$0}) return c.lang")
    String getDefaultLanguageOfConversation(String conversationId);
}
