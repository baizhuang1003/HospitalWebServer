package com.tianyuan.core;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHelper {
	private static ExcelHelper instance;

	public static ExcelHelper getInstance() {
		if (instance == null)
			instance = new ExcelHelper();
		return instance;
	}
	
	/**
	 * 导出excel
	 * @param list 数据列表
	 * @param fileName 文件名称 eg. 用户信息.xls
	 * @param res
	 * @throws IOException
	 */
	public void exportExcel(List<Map<String, Object>> list, String fileName, HttpServletResponse res)
			throws IOException {
		res.setContentType("application/vnd.ms-excel");
		res.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
		OutputStream outputStream = res.getOutputStream();
		createWorkbook(list).write(outputStream);
		outputStream.flush();
		outputStream.close();
	}
	
	private HSSFWorkbook createWorkbook(List<Map<String, Object>> list) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("sheet1");

		HSSFRow headerRow = sheet.createRow(0);
		// 设置为居中加粗
		// 创建标题
		HSSFCellStyle headerStyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();
		font.setBold(true);
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setFont(font);
		Map<String, Object> header = list.get(0);
		int cellIndex = 0;
		for (Map.Entry<String, Object> entry : header.entrySet()) {
			HSSFCell cell = headerRow.createCell(cellIndex);
			cell.setCellStyle(headerStyle);
			Object obj = entry.getKey();
			if (obj == null)
				cell.setCellValue("");
			else
				cell.setCellValue(obj.toString());
			cellIndex++;
		}
		// 创建内容
		cellIndex = 0;
		int rowIndex = 1;
		for (Map<String, Object> item : list) {
			HSSFRow row = sheet.createRow(rowIndex);
			for (Map.Entry<String, Object> entry : item.entrySet()) {
				HSSFCell cell = row.createCell(cellIndex);
				Object obj = entry.getValue();
				if (obj == null)
					cell.setCellValue("");
				else
					cell.setCellValue(obj.toString());
				cellIndex++;
			}
			cellIndex = 0;
			rowIndex++;
		}

		return workbook;
	}
	/**
	 * 保存excel
	 * @param list
	 * @param filePath 文件路径 eg. d:\用户信息.xls
	 * @throws IOException
	 */
	public void saveExcel(List<Map<String, Object>> list, String filePath) throws IOException {
		FileOutputStream fos = new FileOutputStream(filePath);
		createWorkbook(list).write(fos);
		fos.flush();
		fos.close();
	}
	
	/**
	 * 导入excel
	 * @param is  
	 * @param fileName excel文件名
	 * @return
	 * @throws IOException
	 */
	public List<Map<String,Object>> importExcel(InputStream is,String fileName) throws IOException{
		Workbook workbook =null;
		if(fileName.toLowerCase().contains(".xlsx")) {
			workbook = new XSSFWorkbook(is);
		}else if(fileName.toLowerCase().contains(".xls")) {
			workbook = new HSSFWorkbook(is);
		}
		is.close();
		return getList(workbook);
	}
	
	/**
	 * 导入excel
	 * 得到List<Map<String, Object>>
	 * @param workbook
	 * @return
	 */
	public List<Map<String, Object>> getList(Workbook workbook){
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if(workbook==null) return list;
		//遍历excel中所有的sheet
		Sheet sheet = null;  
        Row row = null;
        Cell cell = null;
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			sheet = workbook.getSheetAt(i);
			if(sheet==null) continue;
			//遍历sheet中的所有的行
			for (int j = sheet.getFirstRowNum(); j <=sheet.getLastRowNum(); j++) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				row = sheet.getRow(j);
				if(row==null) continue;
				if(j==sheet.getFirstRowNum()) {  }
				else {
					//遍历所有的数据列  
					int index = 1;
					for (int k = row.getFirstCellNum(); k <row.getLastCellNum(); k++) {
						cell = row.getCell(k);
						if(cell==null) continue;
						map.put("A"+index, getCellValue(cell));
						index++;
					}
					list.add(map);
				}
			}
		}
		return list;
	}
	
	/**
	 * 得到cell的值
	 * @param cell
	 * @return
	 */
	public String getCellValue(Cell cell) {
        String cellValue = "";
        try {
        	// 以下是判断数据的类型
            switch (cell.getCellType()) {
                case NUMERIC: // 数字
                	cellValue = cell.getNumericCellValue()+""; 
                    break;
                case STRING: // 字符串
                    cellValue = cell.getStringCellValue();
                    break;
                case BOOLEAN: // Boolean
                    cellValue = cell.getBooleanCellValue() + "";
                    break;
                case FORMULA: // 公式
                    cellValue = cell.getCellFormula() + "";
                    break;
                case BLANK: // 空值
                    cellValue = "";
                    break;
                case ERROR: // 故障
                    cellValue = "";
                    break;
                default:
                    cellValue = "";
                    break;
            }
		} catch (Exception e) {
			// TODO: handle exception
			cellValue="";
		}
        return cellValue;
    }
}
