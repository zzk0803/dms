package zzk.project.dms.domain.services.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import zzk.project.dms.domain.DormitoryManageException;
import zzk.project.dms.domain.entities.Tenement;
import zzk.project.dms.domain.entities.TenementGender;
import zzk.project.dms.domain.services.ExcelService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@Service
public class ExcelServiceImpl implements ExcelService {

    public static final int HEADER_ROW_INDEX = 0;
    public static final int NAME_COLUMN_INDEX = 0;
    public static final int GENDER_COLUMN_INDEX = 1;
    public static final int IDENTITY_COLUMN_INDEX = 2;

    //    application/xls,
//    application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
    @Override
    public List<Tenement> parseExcelFileToTenementList(InputStream xlsInputStream, String xlsMine) throws DormitoryManageException {
        LinkedList<Tenement> tenements;
        Workbook workbook = null;
        try {
            if (xlsMine.equals("application/vnd.ms-excel")) {
                workbook = new HSSFWorkbook(xlsInputStream);
            } else if ("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equals(xlsMine)) {
                workbook = new XSSFWorkbook(xlsInputStream);
            } else {
                throw new DormitoryManageException("multiple dealing file is not a current type");
            }
            tenements = new LinkedList<>();
            final Sheet firstSheet = workbook.getSheetAt(0);
            final Iterator<Row> rowIterator = firstSheet.rowIterator();
            while (rowIterator.hasNext()) {
                final Row row = rowIterator.next();
                final int rowNum = row.getRowNum();
                if (rowNum == HEADER_ROW_INDEX) {
                    checkRow(row);
                    continue;
                }
                final String tenementName = row.getCell(NAME_COLUMN_INDEX).getStringCellValue();
                final String tenementGender = row.getCell(GENDER_COLUMN_INDEX).getStringCellValue();
                final Cell identityCell = row.getCell(IDENTITY_COLUMN_INDEX);
                identityCell.setCellType(CellType.STRING);
                final String tenementIdentity = identityCell.getStringCellValue();
                final Tenement tenement = new Tenement();
                tenement.setName(tenementName);
                tenement.setGender(TenementGender.forCN(tenementGender));
                tenement.setPersonIdentityID(tenementIdentity);
                tenements.push(tenement);
            }
        } catch (Throwable e) {
            throw new DormitoryManageException(e);
        }
        return tenements;
    }

    private void checkRow(Row firstRow) throws DormitoryManageException {
        final Cell nameCell = firstRow.getCell(NAME_COLUMN_INDEX);
        final String nameCellStringCellValue = nameCell.getStringCellValue();
        final Cell genderCell = firstRow.getCell(GENDER_COLUMN_INDEX);
        final String genderCellStringCellValue = genderCell.getStringCellValue();
        final Cell identityCell = firstRow.getCell(IDENTITY_COLUMN_INDEX);
        final String identityCellStringCellValue = identityCell.getStringCellValue();
        final boolean valid = "姓名".equals(nameCellStringCellValue)
                && "性别".equals(genderCellStringCellValue)
                && "身份证号码".equals(identityCellStringCellValue);
        if (!valid) {
            throw new DormitoryManageException("file should contain exactly name,gender,identity  for first row");
        }
    }
}
