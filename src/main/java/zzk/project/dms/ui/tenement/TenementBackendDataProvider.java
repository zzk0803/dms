package zzk.project.dms.ui.tenement;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import zzk.project.dms.domain.dao.TenementRepository;
import zzk.project.dms.domain.entities.Tenement;

import java.util.stream.Stream;

@SpringComponent
public class TenementBackendDataProvider extends AbstractBackEndDataProvider<Tenement, Void> {

    @Autowired
    private TenementRepository tenementRepository;

    @Override
    protected Stream<Tenement> fetchFromBackEnd(Query<Tenement, Void> query) {
        return tenementRepository.findAll(getPageable(query)).stream();
    }

    @Override
    protected int sizeInBackEnd(Query<Tenement, Void> query) {
        return (int) tenementRepository.count();
    }

    private Pageable getPageable(Query<Tenement, Void> query) {
        return PageRequest.of(query.getOffset() / query.getLimit(), query.getLimit());
    }
}
