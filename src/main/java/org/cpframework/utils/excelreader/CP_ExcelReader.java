/*
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                   *
 ****************************************************************************
 * 源文件名:  com.netqin.common.util.excel.CP_ExcelReader.java 													       
 * 功能: cpframework框架													   
 * 版本:	@version 1.0	                                                                   
 * 编制日期: 2014年9月23日 下午3:44:10 						    						                                        
 * 修改历史: (主要历史变动原因及说明)		
 * YYYY-MM-DD |    Author      |	 Change Description		      
 * 2014年9月23日    |    Administrator     |     Created 
 */
package org.cpframework.utils.excelreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Description: <类功能描述>. <br>
 * <p>
 * <使用说明>
 * </p>
 * Makedate:2014年9月23日 下午3:44:10
 * 
 * @author Administrator
 * @version V1.0
 */
public class CP_ExcelReader<E> {
	private E entity;
	private CP_Excel2EntityConfig excel2EntityConfig;
	private BufferedReader reader = null;

	/**
	 * @Fields fileType : TODO(默认的文件类型，03格式xls)
	 */
	private String fileType = "xls";

	private InputStream is = null;
	private int currSheet;
	private int currPosittion;
	private int numOfSheets;
	private Workbook workbook = null;

	// private HSSFWorkbook workbook = null;
	// private XSSFWorkbook xssfWorkbook = null;

	public CP_ExcelReader() {

	}

	public CP_ExcelReader(String fileType) {
		this.fileType = fileType;
	}

	public void InitExcelReader(InputStream inputfile) throws Exception {
		if (inputfile == null) {
			throw new IOException("文件输入流为空");
		}

		this.currPosittion = this.excel2EntityConfig.getCurrPosittion();

		this.currSheet = 0;

		this.is = inputfile;

		if (this.fileType.equalsIgnoreCase("xls")) {// 2003
			this.workbook = new HSSFWorkbook(this.is);
		} else if (this.fileType.equalsIgnoreCase("xlsx")) {// 2007
			this.workbook = new XSSFWorkbook(this.is);
		} else {
			throw new Exception("文件格式不正确!");
		}
		this.numOfSheets = this.workbook.getNumberOfSheets();

		// 读取excel的第一个sheet的第一行作为字段列表
		if (this.excel2EntityConfig.getColumns() == null) {
			this.excel2EntityConfig.setColumns(getColumnsByFirstRow());
		}
	}

	/**
	 * 描述 : <如果没有指定绑定属性，则通过读取excle的第一个sheet的第一行获取属性>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @return
	 */
	private String[] getColumnsByFirstRow() {

		Sheet sheet = this.workbook.getSheetAt(0);
		Row rowline = sheet.getRow(0);
		int filledColumns = rowline.getLastCellNum();
		String[] columns = new String[filledColumns];
		Cell cell = null;

		for (int i = 0; i < filledColumns; i++) {
			cell = rowline.getCell(i);
			columns[i] = cell.getStringCellValue().trim();
		}

		return columns;
	}

	/**
	 * 描述 : <读取一行数据>. <br>
	 * <p>
	 * <遍历所有sheet>
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 */
	public E readLine() throws Exception {

		if (this.currSheet <= this.numOfSheets) {
			Sheet sheet = this.workbook.getSheetAt(this.currSheet);
			// HSSFSheet sheet = this.workbook.getSheetAt(this.currSheet);

			if (this.currPosittion > sheet.getLastRowNum()) {
				this.currPosittion = 1;

				this.currSheet += 1;
				sheet = this.workbook.getSheetAt(this.currSheet);

			}
			int row = this.currPosittion;
			this.currPosittion += 1;
			return getLine(sheet, row);
		} else {
			return null;
		}

	}

	/**
	 * 描述 : <读取指定sheet的指定行数据>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @param sheet
	 * @param row
	 * @return
	 * @throws Exception
	 */
	private E getLine(Sheet sheet, int row) throws Exception {
		E entity = this.entity;

		Row rowline = sheet.getRow(row);
		if (rowline != null) {

			int filledColumns = rowline.getLastCellNum();
			Cell cell = null;

			int colStart = getExcel2EntityConfig().getColStartPosittion();

			for (int i = colStart; i < filledColumns; i++) {
				cell = rowline.getCell(i);
				String cellvalue = null;

				if (cell != null) {
					switch (cell.getCellType()) {
					case 0:
						if (DateUtil.isCellDateFormatted(cell)) {
							Date date = cell.getDateCellValue();
							cellvalue = this.excel2EntityConfig.getFormater()
									.format(date);

							break;
						}

						Integer num = new Integer(
								(int) cell.getNumericCellValue());
						cellvalue = String.valueOf(num);

						break;
					case 1:
						cellvalue = cell.getStringCellValue()
								.replace("'", "''");
						break;
					default:
						cellvalue = " ";
						break;
					}
				} else
					cellvalue = "";

				String column = this.excel2EntityConfig.getColumns()[(i - colStart)];

				this.setColumn(column, entity, cellvalue);

			}

			return entity;
		}

		return null;
	}

	/**
	 * 描述 : <设置实体的指定属性值>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @param column
	 * @param entity
	 * @param cellvalue
	 * @throws Exception
	 */
	private void setColumn(String column, E entity, String cellvalue)
			throws Exception {
		String userType = "";
		String idType = "S"; // 子类ID是字符串类型
		if (column.contains("#")) {//columnName#classFullName#IdClassType   :   testSub#com.netqin.common.util.excel.example.TestEntitySub#S
			String[] columnAndtype = column.split("#");

			column = columnAndtype[0];
			userType = columnAndtype[1];
			if (columnAndtype.length == 3) {
				idType = columnAndtype[2];
			}

		}

		Field[] field = entity.getClass().getDeclaredFields();
		boolean columnVerify = false;
		for (int j = 0; j < field.length; j++) {
			String name = field[j].getName();

			if (name.equals(column)) {
				columnVerify = true;

				if (!"serialVersionUID".equals(name)) {
					name = A2UpperCase(name);
					String type = field[j].getGenericType().toString();

					if (cellvalue == null || cellvalue.trim().equals(""))
						continue;
					if (type.equals("class java.lang.String")) {
						Method sm = entity.getClass().getDeclaredMethod(
								"set" + name, new Class[] { String.class });
						sm.invoke(entity, new Object[] { cellvalue });

					} else if (type.equals("class java.lang.Integer")) {
						Method sm = entity.getClass().getDeclaredMethod(
								"set" + name, new Class[] { Integer.class });
						sm.invoke(entity, new Object[] { Integer
								.valueOf(Integer.parseInt(cellvalue)) });

					} else if (type.equals("class java.lang.Long")) {
						Method sm = entity.getClass().getDeclaredMethod(
								"set" + name, new Class[] { Long.class });
						sm.invoke(entity, new Object[] { Long.valueOf(Long
								.parseLong(cellvalue)) });

					} else if (type.equals("class java.lang.Short")) {
						Method sm = entity.getClass().getDeclaredMethod(
								"set" + name, new Class[] { Short.class });
						sm.invoke(entity, new Object[] { Short.valueOf(Short
								.parseShort(cellvalue)) });

					} else if (type.equals("class java.lang.Double")) {
						Method sm = entity.getClass().getDeclaredMethod(
								"set" + name, new Class[] { Double.class });
						sm.invoke(entity, new Object[] { Double.valueOf(Double
								.parseDouble(cellvalue)) });

					} else if (type.equals("class java.lang.Boolean")) {
						Method sm = entity.getClass().getDeclaredMethod(
								"set" + name, new Class[] { Boolean.class });
						sm.invoke(entity, new Object[] { Boolean
								.valueOf(Boolean.parseBoolean(cellvalue)) });

					} else if (type.equals("class java.util.Date")) {
						Method sm = entity.getClass().getDeclaredMethod(
								"set" + name, new Class[] { Date.class });
						sm.invoke(entity,
								new Object[] { this.excel2EntityConfig
										.getFormater().parse(cellvalue) });

					} else if (type.equals("class " + userType)) {
						Method sm = entity.getClass().getDeclaredMethod(
								"set" + name,
								new Class[] { Class.forName(userType) });
						sm.invoke(
								entity,
								new Object[] { getSubClassId(userType, idType,
										cellvalue) });

					}
				}
			}

		}
		if (!columnVerify) {
			throw new Exception(column + " is wrong !!!");
		}
	}

	/**
	 * 描述 : <设置关联类Id>. <br>
	 * <p>
	 * <使用方法说明>
	 * </p>
	 * 
	 * @param userType
	 * @param idType
	 * @param cellvalue
	 * @return
	 * @throws Exception
	 */
	public Object getSubClassId(String userType, String idType, String cellvalue)
			throws Exception {
		Object subClass = Class.forName(userType).newInstance();

		if (idType.equalsIgnoreCase("S") || idType.equalsIgnoreCase("String")) {
			Method sm = subClass.getClass().getDeclaredMethod("setId",
					new Class[] { String.class });
			sm.invoke(subClass, new Object[] { cellvalue });
		} else if (idType.equalsIgnoreCase("I")
				|| idType.equalsIgnoreCase("Integer")) {
			Method sm = subClass.getClass().getDeclaredMethod("setId",
					new Class[] { Integer.class });
			sm.invoke(
					subClass,
					new Object[] { Integer.valueOf(Integer.parseInt(cellvalue)) });
		} else if (idType.equalsIgnoreCase("L")
				|| idType.equalsIgnoreCase("Long")) {
			Method sm = subClass.getClass().getDeclaredMethod("setId",
					new Class[] { Long.class });
			sm.invoke(subClass,
					new Object[] { Long.valueOf(Long.parseLong(cellvalue)) });
		}

		return subClass;
	}

	public void close() {
		if (this.is != null) {
			try {
				this.is.close();
			} catch (IOException e) {
				this.is = null;
				e.printStackTrace();
			}
		}

		if (this.reader != null)
			try {
				this.reader.close();
			} catch (IOException e) {
				this.reader = null;
				e.printStackTrace();
			}
	}

	public E getEntity() {
		return this.entity;
	}

	public void setEntity(E entity) {
		this.entity = entity;
	}

	public CP_Excel2EntityConfig getExcel2EntityConfig() {
		return this.excel2EntityConfig;
	}

	public void setExcel2EntityConfig(CP_Excel2EntityConfig excel2EntityConfig) {
		this.excel2EntityConfig = excel2EntityConfig;
	}

	private String A2UpperCase(String filed) {
		// return filed.substring(0, 1).toUpperCase()
		// + filed.substring(1, filed.length());

		char[] cs = filed.toCharArray();
		cs[0] -= 32;
		return String.valueOf(cs);
	}
}
