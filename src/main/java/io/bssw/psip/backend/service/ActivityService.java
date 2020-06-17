package io.bssw.psip.backend.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.bssw.psip.backend.data.Activity;
import io.bssw.psip.backend.repository.ActivityRepository;

@Service
public class ActivityService implements CrudService<ActivityRepository, Activity> {

	@Autowired
	private ActivityRepository repository;

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
	
	public Activity getActivity(String name) {
		return getRepository().findByName(name);
	}
}
