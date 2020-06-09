package io.bssw.psip.backend.service;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.jpa.repository.JpaRepository;

import io.bssw.psip.backend.data.AbstractEntity;

public interface CrudService<R extends JpaRepository<T, Long>, T extends AbstractEntity> {

	R getRepository();

	default long count() {
		return getRepository().count();
	}

	default T load(long id) {
		T entity = getRepository().findById(id).orElse(null);
		if (entity == null) {
			throw new EntityNotFoundException();
		}
		return entity;
	}
}
