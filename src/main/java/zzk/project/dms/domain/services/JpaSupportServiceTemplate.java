package zzk.project.dms.domain.services;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaSupportServiceTemplate<T, ID> {

    JpaRepository<T, ID> getRepository();

    default T createOrUpdate(T entity) {
        return getRepository().save(entity);
    }

    default T delete(T entity) {
        getRepository().delete(entity);
        return entity;
    }

    default int countAll() {
        return (int) getRepository().count();
    }

    default List<T> listAll() {
        return getRepository().findAll();
    }

    default T findById(ID id) {
        return getRepository().getOne(id);
    }
}
