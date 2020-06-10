package io.bssw.psip.backend.data;

import java.util.List;

public class Content {
	public List<Activity> activities;

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	
	@Override
	public String toString() {
		return "Content [activities=" + activities + "]";
	}
}
