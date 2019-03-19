package zzk.project.dms.ui.finance;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import zzk.project.dms.domain.dao.FinancialRecordRepository;
import zzk.project.dms.domain.entities.FinancialRecord;

import java.util.stream.Stream;

@SpringComponent
public class FinanceRecordDataProvider extends AbstractBackEndDataProvider<FinancialRecord,Void> {

    @Autowired
    private FinancialRecordRepository financialRecordRepository;

    @Override
    protected Stream<FinancialRecord> fetchFromBackEnd(Query<FinancialRecord, Void> query) {
        return financialRecordRepository.findAll(getPageable(query)).stream();
    }

    @Override
    protected int sizeInBackEnd(Query<FinancialRecord, Void> query) {
        return (int) financialRecordRepository.count();
    }

    private Pageable getPageable(Query<FinancialRecord, Void> query) {
        return PageRequest.of(query.getOffset() / query.getLimit(), query.getLimit());
    }

}
