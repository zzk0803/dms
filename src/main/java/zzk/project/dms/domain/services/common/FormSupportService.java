package zzk.project.dms.domain.services.common;

public interface FormSupportService<T, ID> extends JpaSupportService<T, ID> {
    default T commit(T entity) {
        return save(entity);
    }
}
