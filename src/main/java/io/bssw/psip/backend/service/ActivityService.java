package io.bssw.psip.backend.service;

import java.util.List;

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
	
	public List<Activity> getActivities() {
		return getRepository().findAll();
	}
	
	public Activity getActivity(String name) {
		return getRepository().findByName(name);
	}
}
