package zzk.project.dms.ui.tenement;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import zzk.project.dms.domain.entities.Tenement;
import zzk.project.dms.domain.services.TenementService;

import java.util.stream.Stream;

@SpringComponent
public class TenementBackendDataProvider extends AbstractBackEndDataProvider<Tenement, Void> {

    @Autowired
    private TenementService tenementService;

    @Override
    protected Stream<Tenement> fetchFromBackEnd(Query<Tenement, Void> query) {
        int offset = query.getOffset();
        int limit = query.getLimit();
        return tenementService.finaAll().stream().skip(offset).limit(limit);
    }

    @Override
    protected int sizeInBackEnd(Query<Tenement, Void> query) {
        return tenementService.integerCount();
    }
}
