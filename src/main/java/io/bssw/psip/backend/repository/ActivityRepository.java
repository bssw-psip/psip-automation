package io.bssw.psip.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.bssw.psip.backend.data.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
	Activity findByName(String name);
}
