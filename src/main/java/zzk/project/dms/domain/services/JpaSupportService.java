package zzk.project.dms.domain.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Transactional
public interface JpaSupportService<T, ID> {

    JpaRepository<T, ID> getRepository();

    default T put(T entity) {
        return getRepository().save(entity);
    }

    default T flush(T entity) {
        return getRepository().saveAndFlush(entity);
    }

    default List<T> putAll(Iterable<T> entities) {
        return getRepository().saveAll(entities);
    }

    default List<T> flushAll(Iterable<T> entities) {
        List<T> savedList = new LinkedList<>();
        for (T entity : entities) {
            T flush = flush(entity);
            savedList.add(flush);
        }
        return savedList;
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
