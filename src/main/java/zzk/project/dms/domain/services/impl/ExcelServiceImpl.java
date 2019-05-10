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

    private static final String EXCEL2003 = "application/vnd.ms-excel";
    private static final String EXCEL2007PLUS = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    private static final int HEADER_ROW_INDEX = 0;

    private static final int NAME_COLUMN_INDEX = 0;
    private static final int GENDER_COLUMN_INDEX = 1;
    private static final int IDENTITY_COLUMN_INDEX = 2;

    @Override
    public List<Tenement> parseExcelFileToTenementList(InputStream xlsInputStream, String xlsMime) throws DormitoryManageException {
        LinkedList<Tenement> tenements;
        Workbook workbook;
        try {
            workbook = decideWorkbookType(xlsInputStream, xlsMime);
            tenements = new LinkedList<>();
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = firstSheet.rowIterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                int rowNum = row.getRowNum();
                if (rowNum == HEADER_ROW_INDEX) {
                    validExcelRow(row);
                    continue;
                }
                String tenementName = parseNameColumn(row);
                String tenementGender = parseGenderColumn(row);
                String tenementIdentity = parseIdentityColumn(row);
                Tenement tenement = new Tenement();
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

    private String parseIdentityColumn(Row row) {
        Cell identityCell = row.getCell(IDENTITY_COLUMN_INDEX);
        identityCell.setCellType(CellType.STRING);
        return identityCell.getStringCellValue();
    }

    private String parseGenderColumn(Row row) {
        return row.getCell(GENDER_COLUMN_INDEX).getStringCellValue();
    }

    private String parseNameColumn(Row row) {
        return row.getCell(NAME_COLUMN_INDEX).getStringCellValue();
    }

    private Workbook decideWorkbookType(InputStream xlsInputStream, String xlsMine) throws IOException {
        Workbook workbook;
        if (EXCEL2003.equals(xlsMine)) {
            workbook = new HSSFWorkbook(xlsInputStream);
        } else if (EXCEL2007PLUS.equals(xlsMine)) {
            workbook = new XSSFWorkbook(xlsInputStream);
        } else {
            throw new DormitoryManageException("multiple dealing file is not a current type");
        }
        return workbook;
    }

    private void validExcelRow(Row firstRow) throws DormitoryManageException {
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
