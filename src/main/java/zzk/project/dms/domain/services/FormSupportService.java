package zzk.project.dms.domain.services;

public interface FormSupportService<T, ID> extends JpaSupportService<T, ID> {
    default T save(T entity) {
        return put(entity);
    }

    default T update(T entity) {
        return save(entity);
    }
}
