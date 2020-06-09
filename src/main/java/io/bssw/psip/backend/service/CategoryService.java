package io.bssw.psip.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.bssw.psip.backend.data.Activity;
import io.bssw.psip.backend.data.Category;
import io.bssw.psip.backend.repository.CategoryRepository;

@Service
public class CategoryService implements CrudService<CategoryRepository, Category> {

	@Autowired
	private CategoryRepository repository;

	@Override
	public CategoryRepository getRepository() {
		return repository;
	}
	
	public List<Category> getCategories(Activity activity) {
		return getRepository().findAll();
	}
	
	public Category getCategory(Activity activity, String name) {
		return getRepository().findByActivityAndName(activity, name);
	}
}
