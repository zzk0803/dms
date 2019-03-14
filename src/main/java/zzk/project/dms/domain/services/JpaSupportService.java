package zzk.project.dms.domain.services;

import org.springframework.data.jpa.repository.JpaRepository;

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

    default int integerCount() {
        return (int) getRepository().count();
    }

    default List<T> finaAll() {
        return getRepository().findAll();
    }

    default T findById(ID id) {
        return getRepository().getOne(id);
    }
}
