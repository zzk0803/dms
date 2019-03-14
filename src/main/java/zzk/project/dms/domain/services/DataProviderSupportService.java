package zzk.project.dms.domain.services;

import com.vaadin.flow.data.provider.Query;

import java.util.stream.Stream;

public interface DataProviderSupportService<T, ID, FILTER> extends FormSupportService<T, ID> {
    default Stream<T> fetchFromBackend(int offset, int limit) {
        return finaAll().stream().skip(offset).limit(limit);
    }

    default int sizeInBackEnd() {
        return integerCount();
    }

    default Stream<T> filterFromBackend(Query<T, FILTER> query) {
        return fetchFromBackend(query.getOffset(), query.getLimit());
    }

    default int sizeInFilterBackend(Query<T, FILTER> query) {
        return sizeInBackEnd();
    }
}
