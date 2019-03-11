package zzk.project.dms.domain.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface JpaSupportService<T, ID> {

    JpaRepository<T, ID> getRepository();

    default T put(T entity) {
        return getRepository().save(entity);
    }

    default List<T> putAll(Iterable<T> ts) {
        return getRepository().saveAll(ts);
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
