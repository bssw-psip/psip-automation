package com.cefriel.coneyapi.repository;

import java.util.List;

import com.cefriel.coneyapi.model.db.custom.AnswerBlock;
import com.cefriel.coneyapi.model.db.custom.QuestionBlock;
import com.cefriel.coneyapi.model.db.entities.Block;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends Neo4jRepository<Block, Long> {
    @Query("MATCH (:Conversation {conv_id:$0})-[STARTS]->(b:Block) " +
        "CALL apoc.path.subgraphNodes(b, {relationshipFilter:'LEADS_TO>'}) YIELD node as n " +
        "WHERE n.block_type = 'Question' " +	
        "RETURN DISTINCT n")
    List<Block> getOrderedQuestions(String conversationId);

    @Query("MATCH (b:Block {block_id: $0, of_conversation: $1})-[:LEADS_TO]->(a:Block {block_type: 'Answer'}) return a")
	List<Block> getAnswersToQuestion(int blockId, String conversationId);

    @Query("MATCH (n:Block {of_conversation:$0}) RETURN n")
    List<Block> getBlocksOfConversation(String conversationId);

    @Query("MATCH (c:Conversation {conv_id:$0})-[:STARTS]->(n:Block)" +
            "RETURN n LIMIT 1")
    Block getFirstBlock(String conversationId);

    @Query("MATCH (t:Tag)<-[:ABOUT]-(b:Block {block_id:$0, of_conversation: $1}) return t.text LIMIT 1")
    String getTagOfBlock(int blockId, String conversationId);

    @Query("MATCH (prev {block_id:$0})-[:LEADS_TO]->(o) " +
            "WHERE prev.of_conversation = $1 " +
            "RETURN o")
    List<Block> getNextBlock(int blockId, String conversationId);
}
