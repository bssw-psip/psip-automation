package com.cefriel.coneyapi.model.db.custom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class AnswersResponse {
	
	String conversation_id;
    String user;
    String project_id;
    String project_name;
    String tag;
    String question;
    String question_type;
    String answer_type;
    int question_id;
    String option;
    int answer_id;
    int value;
    int points;
    String free_answer;
    String timestamp;
    String session;
	String start_timestamp;
    String end_timestamp;
    String language;

    private static final Logger logger = LoggerFactory.getLogger(AnswersResponse.class);

    public AnswersResponse(){}

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user == null ? "" : user;
    }

    public String getAnonymizedUser(){
        return user == null ? "" : "u"+(user.hashCode() & 0xfffffff);
    }

    public void setTags(String tag) {
        this.tag = tag;
    }

    public String getTags() {
        return tag == null ? "" : tag;
    }

    public void setProjectId(String projectId) {
        this.project_id = projectId;
    }

    public String getProjectId() {
        return project_id == null ? "" : project_id;
    }

    public void setProjectName(String projectName) {
        this.project_name = projectName;
    }

    public String getProjectName() {
        return project_name == null ? "" : project_name;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
    	// Aggiungere escape su \n + trim()? Per ora aggiunto nell'export del CSV
        if (question == null) {
            question = "";
        }
        question = question.replaceAll("\\R", " ");
        return question;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }

    public int getQuestionId() {
        return question_id;
    }

    public String getOption() {
        if(option!=null){
            return option.replaceAll("^\\s+|\\s+$", "");
        }
        return "";
    }


    public String getQuestionType() {
        return question_type == null ? "" : question_type;
    }

    public void setQuestionType(String question_type) {
        this.question_type = question_type;
    }

    public String getAnswerType() {
        return answer_type == null ? "" : answer_type;
    }

    public void setAnswerType(String answer_type) {
        this.answer_type = answer_type;
    }

    public String getLanguage() {
        return language == null ? "en" : language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getSession() {
        return session == null ? "" : session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getFreeAnswer() {
        if(free_answer == null){
            return "";
        }
        return free_answer.replaceAll("\\R", " ");
    }

    public void setFreeAnswer(String freeAnswer) {
        this.free_answer = freeAnswer;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getTimestamp() {
        return timestamp == null ? "" : timestamp;
    }

    public String getTime(){
        if(timestamp==null){
            return "";
        }
        String [] tmp = timestamp.split(" ");
        String temp = tmp.length > 1 ? tmp[1] : tmp[0];
        return temp.replaceAll("\\.", ":");
    }

    public String getDate(){
        if(timestamp == null){
            return "";
        }
        return timestamp.split(" ")[0];
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getStartTimestamp() {
        return start_timestamp;
    }

    private String getStartTimestampDuration() {
        if(start_timestamp == null){
            return "";
        }
        String [] tmp = start_timestamp.split(" ");
        String temp = tmp.length > 1 ? tmp[1] : tmp[0];
        return temp.replaceAll("\\.", ":");
    }

    public void setStart_timestamp(String start_timestamp) {
        this.start_timestamp = start_timestamp;
    }
    
    public String getEndTimestamp() {
        return end_timestamp;
    }

    private String getEndTimestampDuration() {
        if(end_timestamp == null){
            return "";
        }
        String [] tmp = end_timestamp.split(" ");
        String temp = tmp.length > 1 ? tmp[1] : tmp[0];
        return temp.replaceAll("\\.", ":");
    }

    public String getDuration() {
        if(session == null) {
            return "";
        }

        if(end_timestamp == null || end_timestamp.equals("")){
            return "unfinished";
        }

        int tot_sec = 0;
        int tot_min = 0;
        int tot_hour = 0;
        
        String [] start = getStartTimestampDuration().split(":");
        String [] end = getEndTimestampDuration().split(":");

        if (start.length > 0 && end.length > 0) {
            try {
                int start_hour = Integer.parseInt(start[0]);
                int end_hour = Integer.parseInt(end[0]);
                tot_hour = end_hour-start_hour;
            } catch (NumberFormatException e) {
                // Ignore
            }

            if (start.length > 1 && end.length > 1) {
                try {
                    int start_min = Integer.parseInt(start[1]);
                    int end_min = Integer.parseInt(end[1]);
                    tot_min = end_min-start_min;
                    if(end_min<start_min){
                        tot_min = 60 - start_min + end_min;
                        tot_hour--;
                    }
                } catch (NumberFormatException e) {
                    // Ignore
                }
    
                if (start.length > 2 && end.length > 2) {
                    try {
                        int start_sec = Integer.parseInt(start[2]);
                        int end_sec = Integer.parseInt(end[2]);

                        tot_sec = end_sec-start_sec;
                        if(end_sec<start_sec){
                            tot_sec = 60 - start_sec + end_sec;
                            tot_min--;
                        }
                    } catch (NumberFormatException e) {
                        // Ignore
                    }
                    }
            }
        }
        return (tot_hour)+":"+(tot_min)+":"+(tot_sec);
    }

    public void setEnd_timestamp(String end_timestamp) {
        this.end_timestamp = end_timestamp;
    }
    
    public String getConversation_id() {
		return conversation_id;
	}

	public void setConversation_id(String conversation_id) {
		this.conversation_id = conversation_id;
	}

	public int getAnswerId() {
		return answer_id;
	}

	public void setAnswer_id(int answer_id) {
		this.answer_id = answer_id;
	}
	
}
