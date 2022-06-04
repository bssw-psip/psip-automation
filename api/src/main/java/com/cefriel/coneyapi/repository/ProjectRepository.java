package com.cefriel.coneyapi.repository;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import com.cefriel.coneyapi.model.db.entities.Project;

@Repository
public interface ProjectRepository extends Neo4jRepository<Project, Long> {

	@Query("MATCH (pr:Project) return pr")
	List<Project> getProjects();

	@Query("MATCH (c:Customer {username: $0})-[wo:WORKS_ON]->(pr:Project) " +
          "RETURN pr")
	List<Project> getProjectsOfCustomer(String username);

	@Query("MATCH (c:Conversation {conv_id: $0})-[:BELONGS_TO]->(pr:Project) RETURN pr.name AS projectName")
	List<Project> getProjectOfConversation(String conversationId);

	@Query("MATCH (c:Customer {username: $0})-[wo:WORKS_ON]->(pr:Project)" +
          "<-[:BELONGS_TO]-(co:Conversation {conv_id: $1}) " +
          "RETURN pr")
	List<Project> getProjectsOfCustomerAndConversation(String username, String conversationId);
	
	@Query("MATCH (c:Customer {username: $0})-[wo:WORKS_ON]->(pr:Project) " +
			"RETURN pr") //.name AS projectName, wo.access_level AS accessLevel")
	List<Project> getCustomerProjects(String username);
}
