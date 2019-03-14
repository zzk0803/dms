package zzk.project.dms.domain.services.impl;

import com.vaadin.flow.data.provider.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import zzk.project.dms.domain.DormitoryManageException;
import zzk.project.dms.domain.dao.TenementRepository;
import zzk.project.dms.domain.entities.Tenement;
import zzk.project.dms.domain.services.TenementService;
import zzk.project.dms.middle.ServiceAndSubscriber;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@ServiceAndSubscriber
@Transactional(rollbackFor = DormitoryManageException.class)
public class TenementServiceImpl implements TenementService {

    @Autowired
    private TenementRepository tenementRepository;

    @Override
    public JpaRepository<Tenement, Long> getRepository() {
        return tenementRepository;
    }

    @Override
    public Stream<Tenement> filterFromBackend(Query<Tenement, String> query) {
        int offset = query.getOffset();
        int limit = query.getLimit();
        Optional<String> filter = query.getFilter();
        if (filter.isPresent()) {
            String string = filter.get();
            List<Tenement> byNameContains = tenementRepository.findDistinctByNameContains(string);
            return byNameContains.stream().skip(offset).limit(limit);
        }
        return fetchFromBackend(offset,limit);
    }

    @Override
    public int sizeInFilterBackend(Query<Tenement, String> query) {
        Optional<String> queryFilter = query.getFilter();
        if (queryFilter.isPresent()) {
            String string = queryFilter.get();
            return tenementRepository.countDistinctByNameContains(string);
        }
        return integerCount();
    }
}
