package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;

import abs.Constants;

public class ExcelUtils {
	public static JSONArray readExcelAsJSON(String excelFileName,String xlSheetName) {
		JSONArray jArr=new JSONArray();
		JSONObject jObj;
        List<String> header=new ArrayList<String>();
        try {
        	FileInputStream excelFile = new FileInputStream(new File(Constants.DATA_PATH+excelFileName));
			Workbook workbook = new XSSFWorkbook(excelFile);
			Sheet sheet = workbook.getSheet(xlSheetName);
			int rowCount=sheet.getLastRowNum();
			for(int i=0;i<=rowCount;i++) {
				jObj=new JSONObject();
				Row headers=sheet.getRow(i);
				Iterator<Cell> c=headers.cellIterator();
				Cell cell=null;
				int j=0;
				while(c.hasNext()) {
					cell=c.next();
					if(i==0) {
						header.add(cell.getStringCellValue());
					}else {
						jObj.put(header.get(j), cell.getStringCellValue());
					}
					j++;
					
				}
				if(i!=0)
					jArr.put(jObj);
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return jArr;
	}
}
