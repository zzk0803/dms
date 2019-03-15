package zzk.project.dms.ui.summarize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import zzk.project.dms.domain.services.DormitorySpaceService;
import zzk.project.dms.domain.services.FinancialRecordService;
import zzk.project.dms.domain.services.TenementService;

@Configuration
public class BeanConfigurationForSummarizeView {

    @Autowired
    private TenementService tenementService;

    @Autowired
    private DormitorySpaceService dormitorySpaceService;

    @Autowired
    private FinancialRecordService financialRecordService;

    //--------------------------------------------------------------------------------
    //------------                       住户管理页面                             -----------------
    //--------------------------------------------------------------------------------
}
