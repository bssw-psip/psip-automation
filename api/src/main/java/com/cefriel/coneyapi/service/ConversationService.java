package com.cefriel.coneyapi.service;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

import org.neo4j.driver.Record;
import org.neo4j.driver.types.TypeSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cefriel.coneyapi.model.db.custom.AnswerBlock;
import com.cefriel.coneyapi.model.db.custom.ConversationResponse;
import com.cefriel.coneyapi.model.db.custom.QuestionBlock;
import com.cefriel.coneyapi.model.db.custom.UserProject;
import com.cefriel.coneyapi.model.db.entities.Block;
import com.cefriel.coneyapi.model.db.entities.Conversation;
import com.cefriel.coneyapi.model.db.entities.Edge;
import com.cefriel.coneyapi.model.db.entities.Tag;
import com.cefriel.coneyapi.repository.ConversationRepository;
import com.cefriel.coneyapi.repository.BlockRepository;
import com.cefriel.coneyapi.repository.ChatRepository;
import com.cefriel.coneyapi.repository.ProjectRepository;
import com.cefriel.coneyapi.utils.Utils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@Service
public class ConversationService {
    @Autowired
    private Neo4jClient neo4jClient;

    @Autowired
    private ConversationRepository conversationRepository;
	@Autowired
    private BlockRepository blockRepository;
	@Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ProjectRepository projectRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	private static final Logger logger = LoggerFactory.getLogger(ConversationService.class);

	public ConversationService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

	private ArrayList<Edge> edgeList;
	private ArrayList<Block> nodeList;
	private ArrayList<Tag> tagList;
	private boolean preview = false;
	private Utils utils = new Utils();
    
 	// GET the list the conversations filtered (by status and title), sorted and paginated
 	public List<ConversationResponse> searchConversation() {

		String usr = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("[CONV] Looking with user: "+ usr + "");

		List<Conversation> tmp;
		if(usr.equals("anonymousUser")){
			tmp = conversationRepository.searchAllConversation();
		} else {
			tmp = conversationRepository.searchConversation(usr);
		}

		return tmp.stream().map(c -> ConversationResponse.of(c)).collect(Collectors.toList());
 	}

 	public String getConversationProject(String conversationId){
		if(hasUserPermission(conversationId)) {
			return conversationRepository.getConversationProject(conversationId);
		}
		return null;
	}

 	public boolean updateConversationStatus(String conversationId, String status){

		String result = "false";
		if(hasUserPermission(conversationId)){
    		result = conversationRepository.updateConversationStatus(conversationId, status);
		}
		return Boolean.valueOf(result);
	}

	public boolean titleNotExists(String convTitle, String conversationId){
		String result = conversationRepository.findExistingTitle(convTitle, conversationId);

		if(Boolean.valueOf(result)){
			try {
				String path = conversationRepository.findJsonUrlByConversationId(conversationId);
				utils.deleteFile(path);
			} catch(Exception ignored){}
		}

		return !Boolean.valueOf(result);
	}

	public boolean createOrUpdateNewConversation(String conversationId, String title,
											  String jsonUrl, String projectName, int accessLevel, String lang){

		return Boolean.valueOf(conversationRepository.createOrUpdateNewConversation(conversationId, title,
				jsonUrl, projectName, accessLevel, lang));
	}

	public boolean createOrUpdateNewOpenConversation(String conversationId, String title,
												 String jsonUrl, String lang){

		return Boolean.valueOf(conversationRepository.createOrUpdateNewOpenConversation(conversationId, title,
				jsonUrl, lang));
	}


	public boolean createOrUpdateConversation(String conversationId, String title,
												 String jsonUrl){

		String result = "false";
		if(hasUserPermission(conversationId)) {
			result = conversationRepository.updateConversation(conversationId, title,
					jsonUrl);
		}
		return Boolean.valueOf(result);
	}

	public String findJsonUrlByConversationId(String conversationId) {

		if(hasUserPermission(conversationId)){
			return conversationRepository.findJsonUrlByConversationId(conversationId);
		} else {
			return "not_auth";
		}
	}

	public boolean deleteConversation(String conversationId){


		if(!hasUserPermission(conversationId)){
			return false;
		}

		String json_url = conversationRepository.findJsonUrlByConversationId(conversationId);
    	String result = conversationRepository.deleteConversation(conversationId);

		if(Boolean.valueOf(result)){
			Utils ut = new Utils();
			logger.info("[CONVERSATION] Deleting reteJS file at: "+json_url);
			ut.deleteFile(json_url);
		}

    	return Boolean.valueOf(result);
	}

	public List<Tag> searchTags(String convId){
 		return conversationRepository.searchTags(convId);
	}

	public boolean deleteConversationNodes(String conversationId){

		if(!hasUserPermission(conversationId)){
			return false;
		}

        conversationRepository.deleteConversationNodes(conversationId);
    	return true;
	}

	public boolean uploadNodesAndEdged(JsonObject nodes, String conversationId, boolean prev){

		if(!hasUserPermission(conversationId)){
			return false;
		}

		edgeList =new ArrayList<>();
		nodeList =new ArrayList<>();
		tagList = new ArrayList<>();
		preview = prev; //TRUE if blocks are meant to be deleted

		boolean readDataBool = true;
		for (String s : nodes.keySet()) {
			JsonObject singleNode = (JsonObject) nodes.get(s);
			readDataBool = (readDataBool && readData(singleNode, conversationId));
		}

		if(!readDataBool){
			logger.error("[CONVERSATION] ERROR: Failed to read JSON data");
			return false;
		}

		logger.info("[CONVERSATION] Json loaded, reading nodes");
		if(prev){
			conversationRepository.deletePreviewBlocks(conversationId);
		}


		//first loads the nodes, than the edges and finally creates the starting node

		if(!uploadQueryNodes()){
			logger.error("[CONVERSATION] ERROR: Failed to create conversation nodes");
			revertChanges(conversationId);
			return false;
		}
		logger.info("[CONVERSATION] Conversation nodes uploaded, loading conversation edges...");

		if(!uploadQueryEdges(conversationId)){
			logger.error("[CONVERSATION] ERROR: Failed to create conversation edges");
			revertChanges(conversationId);
			return false;
		}
		logger.info("[CONVERSATION] Edges uploaded, creating conversation starting node");

		if(!createAndLinkConversation(conversationId)){
			logger.error("[CONVERSATION] ERROR: Failed to create conversation starting node");
			revertChanges(conversationId);
			return false;
		}
		logger.info("[CONVERSATION] Conversation nodes created successfully");

		if(!uploadTagsAndRel()){
			logger.error("[CONVERSATION] Failed to upload and/or connect tags, conversation still created");
		}
		logger.info("[CONVERSATION] Tags created and linked successfully");

		if(!prev) {
			String result = conversationRepository.updateConversationStatus(conversationId, "published");

			if(Boolean.valueOf(result)){
				logger.info("[CONVERSATION] Status in DB updated to 'published'");
				return true;
			} else {
				logger.error("[CONVERSATION] Could not update status in DB, reverting changes");
				revertChanges(conversationId);
				return false;
			}
		}
		return true;
	}

	public boolean deletePreview(String conversationId, String session){
 		System.out.println(conversationId);
		String a = conversationRepository.deletePreviewRelationships(session);
 		String i = conversationRepository.deletePreviewBlocks(conversationId);
 		logger.info("[CONVERSATION] Deleting all preview blocks");

		conversationRepository.deletePreviewUserOfConv(conversationId);
 		return Boolean.valueOf(i) || Boolean.valueOf(a);
	}

	//true if the conversation is published
	public boolean isPublished(String conversationId){

		if(!hasUserPermission(conversationId)){
			return false;
		}
		String status = conversationRepository.getSingleConversationStatus(conversationId);

		return status.equals("published");
	}

	//getQuestionsAndAnswers
	public String getOrderedQuestionsAndAnswers(String conv_id) {

		if(!hasUserPermission(conv_id)){
			return null;
		}

 		List<Block> questions = blockRepository.getOrderedQuestions(conv_id);

 		if(questions.size()==0){
 			return "no_nodes";
		}

 		logger.info("Conversation found, total questions: "+questions.size());

 		List<Block> answers;
 		int sequence = 0;
		StringBuilder res = new StringBuilder();
		res.append("sequence").append(",").append("question").append(",").append("type").append(",").append("tag").append(",")
				.append("answer").append(",").append("value").append(",").append("order");
		res.append(System.getProperty("line.separator"));

 		for(Block b : questions){
 			answers = blockRepository.getAnswersToQuestion(b.getBlockId(), conv_id);
 			String tag = conversationRepository.getTagOfBlock(b.getBlockId(), conv_id);
 			for(Block ans : answers){
				res.append(sequence).append(",")
						.append(b.getText()).append(",")
						.append(b.getVisualization()).append(",")
						.append(tag).append(",")
						.append(ans.getText()).append(",")
						.append(ans.getValue()).append(",")
						.append(ans.getOrder());
				res.append(System.getProperty("line.separator"));
			}
 			sequence++;
		}
 		return res.toString();
	}

	// I don't know how to make this as work as a Cypher query. We can use subgraphNodes to get all the questions belonging to 
    // a conversation, but there doesn't seem to be any way to get the depth. Fetching paths using expand is
    // too expensive.
    // "MATCH p=(c:Conversation {conv_id:$conv_id})-[:STARTS]->(n:Block) " +
	// 		"CALL apoc.path.subgraphNodes(n, {relationshipFilter:'LEADS_TO>'}) YIELD node as b " +
	// 		"WHERE b.block_type = 'Question' " +
	// 		"WITH b, p " +
	// 		"RETURN b.block_id as block_id, b.block_type as block_type, b.block_subtype as block_subtype, "+
	// 		"b.text as text, b.visualization as visualization, b.of_conversation as of_conversation, " +
	// 		"id(b) as neo4jId, LENGTH(p) as depth"
    //
    // This the pattern version but it takes too long/never finishes for a large graph:
	//
    // @Query("MATCH p=(c:Conversation {conv_id:$0})-[:STARTS|LEADS_TO*]->(b:Block {block_type: 'Question'}) " +
    //     "WITH b, p " +
    //     "RETURN DISTINCT b, id(b) as neo4jId, LENGTH(p) as depth")
	//
	// Instead, we recursively find all questions belonging to a conversation. This is an alternative to 
	// using the above. We need to keep track of which nodes we have visited to avoid infinite recursion
	//
	private void getQuestions(List<QuestionBlock> questions, Map<Long, Block> visited, Block block, int depth, String conv_id) {
		if (block.getBlockType().equals("Question")) {
			questions.add(QuestionBlock.builder()
				.reteId(block.getBlockId())
				.neo4jId((int)block.getNeo4jId())
				.type(block.getBlockType())
				.subtype(block.getBlockSubtype())
				.ofConversation(block.getOfConversation())
				.questionType(block.getVisualization())
				.text(block.getText())
				.depth(depth)
				.build());
			depth++;
		}
		visited.put(block.getNeo4jId(), block);
		for(Block b: chatRepository.getNextBlock(block.getBlockId(), conv_id)) {
			if (!visited.containsKey(b.getNeo4jId())) {
				getQuestions(questions, visited, b, depth, conv_id);
			}
		}
	}

	//getQuestionsAndAnswers
	public String getOrderedConversation(String conv_id) {

		if(!hasUserPermission(conv_id)){
			logger.info("does not have permission");
			return null;
		}

		List<QuestionBlock> questions = new ArrayList<>();
		Map<Long, Block> visited = new HashMap<>();
		getQuestions(questions, visited, chatRepository.getFirstBlock(conv_id), 0, conv_id);
		
		Collections.sort(questions);

		if(questions.size()==0){
			logger.info("no questions");
			return "no_nodes";
		}

		JsonArray questionsArray = new JsonArray();

		logger.info("Conversation found, total questions: "+questions.size());

		List<AnswerBlock> answers;
		int orderInConversation = 1;

		for (QuestionBlock question : questions){

			JsonObject questionJson = new JsonObject();
			JsonArray answersArray = new JsonArray();

			logger.info("q id:"+question.getNeo4jId()+" conv:"+question.getOfConversation());
			question.setAnswersAmount(conversationRepository.getAnswersOfBlockAmount(question.getNeo4jId(), question.getOfConversation()));
			question.setTag(conversationRepository.getTagOfBlock(question.getReteId(), conv_id));
			question.setOrderInConversation(orderInConversation);

			answers = neo4jClient.query(
				"MATCH (b:Block {block_id: $block_id, of_conversation: $conv_id})-[:LEADS_TO]->(a:Block {block_type: 'Answer'})" +
				" OPTIONAL MATCH n=(a)-[:LEADS_TO*..10]->(nq:Block {block_type:'Question'}) " +
				" WITH a, nq ORDER BY length(n) ASC " +
				"RETURN DISTINCT a.block_id as block_id, a.block_type as block_type, a.block_subtype as block_subtype, "+
				"a.text as text, a.value as value, a.order as order, a.of_conversation as of_conversation, " +
				"id(a) as neo4jId, nq.block_id as nextQuestionId LIMIT $limit")
				.bind(question.getReteId()).to("block_id")
				.bind(question.getOfConversation()).to("conv_id")
				.bind(question.getAnswersAmount()).to("limit")
				.fetchAs(AnswerBlock.class)
				.mappedBy(this::toAnswerBlock)
				.all()
				.stream()
				.collect(Collectors.toCollection(ArrayList::new));

			for (AnswerBlock ans : answers){

				logger.info("val: "+ans.getValue()+"");
				logger.info("text: "+ans.getText()+"");

				JsonObject answerJson = new JsonObject();
				answerJson.addProperty("text", ans.getText()); //string
				answerJson.addProperty("value", ans.getValue()); //int
				answerJson.addProperty("order", ans.getOrder()); //int

				logger.info("-"+ans.getNextQuestionId()+"-");

				try {
					answerJson.addProperty("nextQuestionId", ans.getNextQuestionId()); //int
				} catch(Exception e){
					answerJson.addProperty("nextQuestionId", 0); //int
				}
				answersArray.add(answerJson);
			}

			questionJson.addProperty("id", question.getReteId());
			questionJson.addProperty("order", question.getOrderInConversation());
			questionJson.addProperty("type", question.getQuestionType());
			questionJson.addProperty("tag", question.getTag());
			questionJson.addProperty("text", question.getText());
			questionJson.addProperty("depth", question.getDepth());
			questionJson.add("answers", answersArray);

			questionsArray.add(questionJson);

			orderInConversation++;
		}
		return questionsArray.toString();
	}
	
	public QuestionBlock toQuestionBlock(TypeSystem ignored, Record record) {
		return QuestionBlock.builder()
				.reteId(record.get("block_id").asInt())
				.neo4jId(record.get("neo4jId").asInt())
				.type(record.get("block_type").asString())
				.subtype(record.get("block_subtype").asString())
				.ofConversation(record.get("of_conversation").asString())
				.questionType(record.get("visualization").asString())
				.text(record.get("text").asString())
				.depth(record.get("depth").asInt())
				.build();
	}

	public AnswerBlock toAnswerBlock(TypeSystem ignored, Record record) {
		return AnswerBlock.builder()
				.reteId(record.get("block_id").asInt())
				.neo4jId(record.get("neo4jId").asInt())
				.type(record.get("block_type").asString())
				.subtype(record.get("block_subtype").asString())
				.ofConversation(record.get("of_conversation").asString())
				.text(record.get("text").asString())
				.value(record.get("value").asInt())
				.order(record.get("order").asInt())
				.nextQuestionId(record.get("nextQuestionId").asInt())
				.build();
	}

	//returns all the customers with access to a project
	public List<UserProject> getCustomerProjects(){
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return projectRepository.getCustomerProjects(username).stream().map(c -> UserProject.of(c)).collect(Collectors.toList());
	}

	//uploads a given translation
	public String uploadTranslation(String conversationId, String language, JsonArray translationBlocks){

 		String res = "failed";
		if(!hasUserPermission(conversationId)){
			return "not_auth";
		}
		logger.info("[CONVERSATION] Adding "+language+" translation to conversation: "+conversationId);
 		for(JsonElement jsonBlock: translationBlocks){
 			JsonObject joBlock = jsonBlock.getAsJsonObject();

 			String blockId = joBlock.get("block_id").getAsString();
 			String translation = joBlock.get("translation").getAsString();

 			if(!translation.equals("")){
 				conversationRepository.uploadBlockTranslation(conversationId, language,
						Integer.parseInt(blockId), translation);
			}
 			res = "success";
		}
 		return res;
	}

	//returns all the Talk blocks followed by all the Questions with their answers
	public String getLanguageTranslationCSV(String conversationId){

		if(!hasUserPermission(conversationId)){
			return "not_auth";
		}

 		String check = conversationRepository.getSingleConversationStatus(conversationId);
 		if(!check.toLowerCase().contains("published")){
 			return "not_published";
		}

 		List<Block> talkBlocks = conversationRepository.getOrderedBlocksOfType(conversationId, "Talk");
 		List<Block> questionBlocks = blockRepository.getOrderedQuestions(conversationId);
		logger.info("[CONVERSATION] Exporting Language Translation CSV for conv "+conversationId);
		String line = "block_id,type,text,translation";
		StringBuilder sb = new StringBuilder();
		sb.append(line);
		sb.append(System.getProperty("line.separator"));


		logger.info("[CONVERSATION] Adding "+talkBlocks.size() + " talk blocks");
		//add talk blocks first
		for(Block talkBlock: talkBlocks){
			String tmpTalkText = talkBlock.getImageUrl();
			if(tmpTalkText.equals("")){
				tmpTalkText = talkBlock.getText();
			}
			line = "" + talkBlock.getNeo4jId() + ",\"" + "Talk" + "\",\"" + tmpTalkText + "\",\"\"";
			sb.append(line);
			sb.append(System.getProperty("line.separator"));
		}

		//add questions
		for(Block questionBlock: questionBlocks){

			line = "" + questionBlock.getNeo4jId() +  ",\"" + "Question" + "\",\"" + questionBlock.getText() + "\",\"\"";
			sb.append(line);
			sb.append(System.getProperty("line.separator"));

			//add answers
			List<Block> answerBlocks = blockRepository
					.getAnswersToQuestion(questionBlock.getBlockId(), conversationId);

			for(Block answerBlock: answerBlocks){

				String ansTxt = answerBlock.getText();
				if(ansTxt == null || ansTxt.equals("")){
					ansTxt = "NO TEXT ANSWER";
				}
				line = "" + answerBlock.getNeo4jId() + ",\"" + "Answer" + "\",\"" + ansTxt + "\",\"\"";
				sb.append(line);
				sb.append(System.getProperty("line.separator"));
			}
		}
		byte[] tmpStr = sb.toString().getBytes();
		try {
			return new String(tmpStr, StandardCharsets.UTF_8);
		} catch (Exception e) {
			return sb.toString();
		}

	}

	//changes the user's password
	public String changeCustomerPassword(String username, String oldPassword, String newPassword){

 		String res = conversationRepository.getCustomerByUsername(username);
 		String newEncodedPw = bcryptEncoder.encode(newPassword);

 		if(res!=null && bcryptEncoder.matches(res, oldPassword)){
 			res = conversationRepository.updateCustomerPassword(username, newEncodedPw);
		}

 		if(res==null || !res.equals(newEncodedPw)){
 			return "false";
		}
 		return "true";
	}

	//set the delete flag on user ***not used
	public void setCustomerForDeletion(String username){
		conversationRepository.setCustomerForDeletion(username);
	}

	//Read JSON nodes, creates Node/Edge Objects and store them in ArrayLists
	private boolean readData(JsonObject content, String convId){

		try {
			String nodeId_s = content.get("id").getAsString();
			int nodeId = Integer.parseInt(nodeId_s);
			if(preview){
				nodeId = nodeId * (-1);
			}
			conversationRepository.deletePreviewUserOfConv(convId);
			ArrayList<String> checkboxes = new ArrayList<>();


			String blockText = null;
			String blockSubtype;
			int blockPoints;

			JsonObject data_obj = content.getAsJsonObject("data");
			String blockType = data_obj.get("type").getAsString();

			String tag;
			try {
				tag = data_obj.get("tag").getAsString();
			} catch (Exception e){
				tag = null;
			}

			if(tag!=null) {
				Tag t = new Tag((tag), nodeId, convId);
				tagList.add(t);
			}

			if (blockType.equals("Talk")) {


				blockSubtype = data_obj.get("subtype").getAsString();

				String blockUrl = null;
				String blockImageUrl = null;

				switch (blockSubtype) {
					case ("text"):
						try{
							blockText = data_obj.get("text").getAsString();
						} catch (Exception e){
							blockText = "";
						}

						break;
					case ("link"):
						blockText = data_obj.get("title").getAsString();
						blockUrl = data_obj.get("url").getAsString();
						break;
					case ("imageUrl"):
						blockImageUrl = data_obj.get("url").getAsString();
						break;
				}
				Block n = new Block(nodeId, blockType, blockSubtype, blockText, blockUrl, blockImageUrl, convId);
				nodeList.add(n);

			}
			else if (blockType.equals("Answer")) {

				blockSubtype = data_obj.get("subtype").getAsString();

				try {
					blockText = data_obj.get("text").getAsString();
				} catch (Exception jse) {
					blockText = null;
				}
				try{
					long temp = Long.parseLong(data_obj.get("points").getAsString());
					blockPoints = Math.toIntExact(temp);
				} catch(Exception e){
					blockPoints = 0;
				}

				int blockScaleNumber = 0;
				int blockOrder = 0;
				int lastBlockScaleNumber = 0;
				int none = 0;
				if (blockSubtype.equals("multiple")) {
					long temp_int = Long.parseLong(data_obj.get("value").getAsString());
					blockScaleNumber = Math.toIntExact(temp_int);
					String sortVal = data_obj.get("sort").getAsString();
					if (sortVal == null) {
						logger.info("sortval is null for block:" + nodeId);
						return false;
					}
					temp_int = Long.parseLong(sortVal);
					blockOrder = Math.toIntExact(temp_int);
				} else if(blockSubtype.equals("checkbox")){

				    long temp_int = Long.parseLong(data_obj.get("value").getAsString());
                    blockScaleNumber = Math.toIntExact(temp_int);

					JsonArray boxes = data_obj.get("checkbox").getAsJsonArray();
					for(int i = 0; i<boxes.size(); i++){
						JsonObject box = boxes.get(i).getAsJsonObject();
						String text = box.get("v").getAsString();


						try{
							none = box.get("n").getAsInt();
						} catch (Exception ignored){}

						if(none == 1){
							text = "----"+text;
							try{
								lastBlockScaleNumber = box.get("value").getAsInt();
							} catch(Exception ignored){	}
                        }

						if(!text.equals("")){
							checkboxes.add(text);
						}

					}

				}

				if(checkboxes.size() < 1) {
					Block n = new Block(nodeId, blockType, blockSubtype, blockText, blockScaleNumber, blockOrder, convId, blockPoints);
					nodeList.add(n);
				} else {
					int tmpOrder = 0;
					for(String box : checkboxes){
						Block n;
						if(box.contains("----")){
							n = new Block(nodeId, blockType, blockSubtype, box, lastBlockScaleNumber, tmpOrder, convId, blockPoints);
						} else {
							n = new Block(nodeId, blockType, blockSubtype, box, blockScaleNumber, tmpOrder, convId, blockPoints);
						}
						nodeList.add(n);
						tmpOrder ++;
					}
				}

			}
			else {
				//QUESTION

				blockText = data_obj.get("text").getAsString();
				//null pointer exception
				blockSubtype = data_obj.get("subtype").getAsString();

				String visualization = "";

				if(blockSubtype!=null && blockSubtype.equals("single")){

					try{
						visualization = data_obj.get("text_type").getAsString();
					} catch (Exception ignored){}

				} else if (blockSubtype!=null && blockSubtype.equals("multiple")) {

					try{
						visualization = data_obj.get("visualization").getAsString();
					} catch (Exception ignored){}

				}


				Block n = new Block(nodeId, blockType, blockSubtype, blockText, visualization, convId);
				nodeList.add(n);

			}

			//Store outgoing connections
			JsonObject outputs_obj = content.getAsJsonObject("outputs");
			JsonObject outputs_out = outputs_obj.getAsJsonObject("out");
			JsonArray outputs = outputs_out.getAsJsonArray("connections");
			JsonObject next_node;
			Edge edge;

			for (int i = 0; i < outputs.size(); i++) {
				next_node = (JsonObject) outputs.get(i);
				String node = next_node.get("node").getAsString();

				int nextNodeId = Integer.parseInt(node);
				if(preview){
					nextNodeId = nextNodeId * (-1);
				}
				edge = new Edge(nodeId, nextNodeId);
				edgeList.add(edge);
			}

			return true;

		} catch (Exception je){
			je.printStackTrace();
			return false;
		}


	}

	//create nodes
	private boolean uploadQueryNodes(){
	logger.info("[CONVERSATION] uploading query nodes");
 		boolean out = true;
 		String queryOutput;
		for(Block block : nodeList){

			String id = Integer.toString(block.getBlockId());

			int idToAdd = block.getBlockId();

			//rete always starts  from 1 up to set block ids
			// 	=> impossible to normally find a block with id<0
			// 	so that's the condition to understand they are for preview only

			if(block.getBlockType().equals("Answer")){


				queryOutput = conversationRepository
						.uploadAnswerNode(idToAdd, block.getBlockSubtype(), block.getText(), block.getValue(),
								block.getOrder(), block.getOfConversation(), block.getPoints());

			} else if(block.getBlockType().equals("Talk")){

				queryOutput = conversationRepository.uploadTalkNode(idToAdd, block.getBlockSubtype(),
						block.getText(),block.getUrl(), block.getImageUrl(),block.getOfConversation());

			} else {

				queryOutput = conversationRepository.uploadQuestionNode(idToAdd, block.getBlockSubtype(),
						block.getText(),block.getOfConversation(), block.getVisualization());
			}

			out = out && id.equals(queryOutput);
		}
		logger.info("[CONVERSATION] upload done");
		return out;
	}

	//create edges
	private boolean uploadQueryEdges(String conversationId){

		boolean out = true;
		logger.info("[CONVERSATION] Total edges: "+edgeList.size());
		for(Edge edge : edgeList) {
			int start = edge.getStartId();
			int end = edge.getEndId();
			List<String> res = conversationRepository.uploadRelationships(start, end, conversationId);
			out = (out && (res.size()>0));
		}

		return out;
	}

	//upload tags
	public boolean uploadTags(JsonArray tag_list){

 		boolean check = true;

		for(JsonElement one_tag : tag_list){
			String toAdd = one_tag.getAsString();
			String res = conversationRepository.uploadTags(toAdd);
			check = check && (res.equals(toAdd));
		}
		return check;
	}

	//add tags
	private boolean uploadTagsAndRel(){
 		boolean out = true;
 		for(Tag t : tagList){
 			String tag = t.getText().replaceAll("\"", "");
 			if(!tag.equals("")){
				String res = conversationRepository.createTagsAndRelationship(t.getConversationId(),
						t.getBlockId(), tag);
				out = out && (("ABOUT").equals(res));
			}

		}
 		return out;
	}

	//create conversation start node with all the properties
	private boolean createAndLinkConversation(String convId){
		String outCheck = conversationRepository.createStartRelationship(convId);
		return outCheck.equals(convId);
	}

	private void revertChanges(String conversationId){
		for(Block node : nodeList) {
			conversationRepository.deleteBlock(node.getBlockId(), conversationId);
		}
		logger.info("[CONVERSATION] Changes reverted, exiting now");
	}

	private boolean hasUserPermission(String conversationId) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();

		if(username.equals("anonymousUser")){
			return true;
		}

		String res =  conversationRepository.hasUserPermission(username, conversationId);
		if(res == null || !Boolean.valueOf(res)){
			logger.error("[CONVERSATION] User not authorized to access conversation: "+conversationId);
			return false;
		}
		return Boolean.valueOf(res);
	}
}
