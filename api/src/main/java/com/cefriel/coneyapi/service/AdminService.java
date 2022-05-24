package com.cefriel.coneyapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cefriel.coneyapi.model.db.custom.UserProject;
import com.cefriel.coneyapi.model.db.entities.Conversation;
import com.cefriel.coneyapi.model.db.entities.Project;
import com.cefriel.coneyapi.repository.AdminRepository;
import com.cefriel.coneyapi.repository.ConversationRepository;
import com.cefriel.coneyapi.repository.ProjectRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    public AdminService(AdminRepository adminRepository){
        this.adminRepository = adminRepository;
    }

    public List<String> getCustomers(String filterType, String filter, String filter2){

        if(!checkUserPermission()){
            return null;
        }

        switch (filterType) {
            case "conversation":
                return adminRepository.getCustomersOfConversation(filter);
            case "project":
                return adminRepository.getCustomersOfProject(filter);
            case "both":
                return adminRepository.getCustomersOfConversationAndProject(filter, filter2);
            default:
                return adminRepository.getCustomers();
        }
    }

    public List<Conversation> getConversations(String filterType, String filter, String filter2){

        if(!checkUserPermission()){
            return null;
        }
        logger.info("[CONTROL] IN service, filtering for "+filterType);
        switch (filterType) {
            case "customer":
                return conversationRepository.getConversationsOfCustomer(filter);
            case "project":
                return conversationRepository.getConversationsOfProject(filter);
            case "both":
                return conversationRepository.getConversationsOfCustomerAndProject(filter, filter2);
            default:
                logger.info(conversationRepository.getConversations().toString());
                return conversationRepository.getConversations();
        }
    }

    public List<UserProject> getProjects(String filterType, String filter, String filter2){

        if(!checkUserPermission()){
            return null;
        }

        List<Project> projects;
        switch (filterType) {
            case "conversation":
                projects = projectRepository.getProjectOfConversation(filter);
                break;
            case "customer":
            	projects = projectRepository.getProjectsOfCustomer(filter);
                break;
            case "both":
            	projects = projectRepository.getProjectsOfCustomerAndConversation(filter, filter2);
                break;
            default:
            	projects = projectRepository.getProjects();
        }
		return projects.stream().map(c -> UserProject.of(c)).collect(Collectors.toList());
    }

    //CREATE

    public String createCustomer(String username, String password){
        if(!checkUserPermission()){
            return "auth";
        }

        if(adminRepository.checkIfUsernameIsTaken(username) > 0){
            return "taken";
        }
        return adminRepository.createCustomer(username, bcryptEncoder.encode(password));
    }

    public boolean createProject(String projectName){
        if(!checkUserPermission()){
            return false;
        }
        return projectName.equals(adminRepository.createProject(projectName));
    }

    public boolean linkConversationToProject(String conversationId, String projectName){
        if(!checkUserPermission()){
            return false;
        }
        return adminRepository.linkConversationToProject(conversationId, projectName);
    }

    public boolean linkCustomerToProject(String username, String projectName, int accessLevel){
        if(!checkUserPermission()){
            return false;
        }
        return accessLevel == adminRepository.linkCustomerToProject(username, projectName, accessLevel);
    }

    //EDIT

    public boolean changeConversationAccessLevel(String conversationId, int accessLevel){
        if(!checkUserPermission()){
            return false;
        }
        return accessLevel == adminRepository.changeConversationAccessLevel(conversationId, accessLevel);
    }

    public boolean changeCustomerProjectAccessLevel(String username, String projectName, int accessLevel){
        if(!checkUserPermission()){
            return false;
        }
        return accessLevel == adminRepository.changeCustomerProjectAccessLevel(username, projectName, accessLevel);
    }

    public boolean changeCustomerPassword(String username, String newPassword){
        if(!checkUserPermission()){
            return false;
        }
        return newPassword.equals(adminRepository.changeCustomerPassword(username, bcryptEncoder.encode(newPassword)));
    }

    public boolean changeProjectName(String oldName, String newName){
        if(!checkUserPermission()){
            return false;
        }
        return newName.equals(adminRepository.changeProjectName(oldName, newName));
    }

    public void deleteCustomer(String username){
        if(checkUserPermission()){
            adminRepository.deleteCustomer(username);
        }
    }

    public void deleteProject(String projectName){
        if(checkUserPermission()){
            adminRepository.deleteProject(projectName);
        }
    }

    public void deleteConversationProjectLink(String conversationId, String projectName){
        adminRepository.deleteConversationProjectLink(conversationId, projectName);
    }

    public void deleteCustomerProjectLink(String username, String projectName){
        if(checkUserPermission()) {
            adminRepository.deleteCustomerProjectLink(username, projectName);
        }
    }

    public boolean isUsernameTaken(String username){
        if(!checkUserPermission()){
            return false;
        }
        return adminRepository.isUsernameTaken(username);
    }

    public boolean isProjectNameTaken(String projectName){
        if(!checkUserPermission()){
            return false;
        }
        return adminRepository.isProjectNameTaken(projectName);
    }

    public boolean checkUserPermission() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if(username == null || username.equals("anonymousUser")){
            return true;
        }
        String check = adminRepository.isUserAdmin(username);
        if(check == null || !Boolean.valueOf(check)){
            logger.error("[CONTROL] User is not authorized to access this content");
            return false;
        }
        return true;
    }

}
