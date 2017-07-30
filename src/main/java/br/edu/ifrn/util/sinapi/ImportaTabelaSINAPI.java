package br.edu.ifrn.util.sinapi;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ImportaTabelaSINAPI {

    public void importar(String arquivo) throws IOException {

        // String texto = "/Documentos/workspace/DownloadFile/";

        System.out.println("Download arquivo: "+arquivo);    
        
        InputStream ExcelArquivo = new FileInputStream(arquivo);
        
        HSSFWorkbook wb = new HSSFWorkbook(ExcelArquivo);

        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row;
        HSSFCell cell;

        Iterator rows = sheet.rowIterator();

        LocalDateTime lv = LocalDateTime.now();
        int conta = 0;
        System.out.println(Calendar.getInstance());
        while (rows.hasNext()) {

            row = (HSSFRow) rows.next();
            Iterator cells = row.cellIterator();

            while (cells.hasNext()) {
                cell = (HSSFCell) cells.next();

                if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                    
                    System.out.print(cell.getStringCellValue() + " ; ");
                    
                } else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
                    
                    System.out.print(cell.getNumericCellValue() + " ; ");
                
                } else {
                        //U Can Handel Boolean, Formula, Errors
                }
            }
            System.out.println();
            conta++;
        }
        System.out.println("FIM");
        System.out.println("Quantidade de registro: " + conta);
        System.out.println();
        System.out.println(lv.getSecond());

    }

}
