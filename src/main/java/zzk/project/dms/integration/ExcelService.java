package zzk.project.dms.integration;

import zzk.project.dms.domain.entities.Tenement;

import java.io.InputStream;
import java.util.List;

public interface ExcelService {
    List<Tenement> parseExcelFileToTenementList(InputStream xlsInputStream, String xlsMine);
}
