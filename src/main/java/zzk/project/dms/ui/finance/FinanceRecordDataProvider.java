package zzk.project.dms.ui.finance;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.data.provider.QuerySortOrder;
import com.vaadin.flow.data.provider.QuerySortOrderBuilder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.vaadin.artur.spring.dataprovider.FilterablePageableDataProvider;
import zzk.project.dms.domain.dao.FinancialRecordRepository;
import zzk.project.dms.domain.entities.FinancialRecord;
import zzk.project.dms.domain.entities.Tenement;
import zzk.project.dms.domain.services.FinancialRecordService;

import java.util.List;
import java.util.stream.Stream;

@SpringComponent
public class FinanceRecordDataProvider extends FilterablePageableDataProvider<FinancialRecord,Void> {

    @Autowired
    private FinancialRecordRepository financialRecordRepository;

    @Override
    protected Page<FinancialRecord> fetchFromBackEnd(Query<FinancialRecord, Void> query, Pageable pageable) {
        return financialRecordRepository.findAll(pageable);
    }

    @Override
    protected List<QuerySortOrder> getDefaultSortOrders() {
        QuerySortOrderBuilder builder = new QuerySortOrderBuilder();
        return builder.thenAsc("id").build();
    }

    @Override
    protected int sizeInBackEnd(Query<FinancialRecord, Void> query) {
        return (int) financialRecordRepository.count();
    }
}
