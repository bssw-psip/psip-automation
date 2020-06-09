package io.bssw.psip.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.bssw.psip.backend.data.Activity;
import io.bssw.psip.backend.data.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	Category findByActivityAndName(Activity activity, String name);
}
