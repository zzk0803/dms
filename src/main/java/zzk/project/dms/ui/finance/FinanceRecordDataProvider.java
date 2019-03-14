package zzk.project.dms.ui.finance;

import com.vaadin.flow.data.provider.AbstractBackEndDataProvider;
import com.vaadin.flow.data.provider.Query;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import zzk.project.dms.domain.entities.FinancialRecord;
import zzk.project.dms.domain.services.FinancialRecordService;

import java.util.stream.Stream;

@SpringComponent
public class FinanceRecordDataProvider extends AbstractBackEndDataProvider<FinancialRecord,Void> {

    @Autowired
    private FinancialRecordService financialRecordService;

    @Override
    protected Stream<FinancialRecord> fetchFromBackEnd(Query<FinancialRecord, Void> query) {
        return financialRecordService.fetchFromBackend(query.getOffset(),query.getLimit());
    }

    @Override
    protected int sizeInBackEnd(Query<FinancialRecord, Void> query) {
        return financialRecordService.sizeInBackEnd();
    }
}
