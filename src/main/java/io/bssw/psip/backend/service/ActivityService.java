package io.bssw.psip.backend.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vaadin.flow.spring.annotation.VaadinSessionScope;

import io.bssw.psip.backend.data.Activity;
import io.bssw.psip.backend.data.Category;
import io.bssw.psip.backend.data.Item;
import io.bssw.psip.backend.repository.ActivityRepository;

// Must be session scope to ensure only one service (and resulting entities) per session
@VaadinSessionScope 
@Service
public class ActivityService implements CrudService<ActivityRepository, Activity> {

	@Autowired
	private ActivityRepository repository;

	private final Map<String, Item> items = new HashMap<String, Item>();
	private final Map<String, Item> prevItems = new HashMap<String, Item>();
	private final Map<String, Item> nextItems = new HashMap<String, Item>();
	private final Map<String, Category> categories = new HashMap<String, Category>();
	private final Map<String, Activity> activities = new HashMap<String, Activity>();

	@Override
	public ActivityRepository getRepository() {
		return repository;
	}
	
	@Transactional
	public List<Activity> getActivities() {
		List<Activity> activities = getRepository().findAll();
		activities.forEach(a -> Hibernate.initialize(a.getScores())); // TODO: should be lazy loaded
		return activities;
	}
	
	public Item getItem(String path) {
		return items.get(path);
	}
	
	public void setItem(String path, Item item) {
		items.put(path, item);
	}
	
	public Item getNextItem(String path) {
		return nextItems.get(path);
	}
	
	public void setNextItem(String path, Item item) {
		nextItems.put(path, item);
	}
	
	public Item getPrevItem(String path) {
		return prevItems.get(path);
	}
	
	public void setPrevItem(String path, Item item) {
		prevItems.put(path, item);
	}
	
	public Category getCategory(String path) {
		return categories.get(path);
	}
	
	public void setCategory(String path, Category category) {
		categories.put(path, category);
	}

	
	public Activity getActivity(String name) {
		return activities.get(name);
	}
	
	public void setActivity(String name, Activity activity) {
		activities.put(name, activity);
	}

}
