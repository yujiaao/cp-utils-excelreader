/*
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                   *
 ****************************************************************************
 * 源文件名:  org.cpframework.utils.excelreader.CP_ExcelReaderUtilTest.java 													       
 * 功能: cpframework框架													   
 * 版本:	@version 1.0	                                                                   
 * 编制日期: 2014年9月26日 下午3:02:28 						    						                                        
 * 修改历史: (主要历史变动原因及说明)		
 * YYYY-MM-DD |    Author      |	 Change Description		      
 * 2014年9月26日    |    Administrator     |     Created 
 */
package org.cpframework.utils.excelreader;

import java.text.DateFormat;
import java.util.List;

import org.cpframework.utils.excelreader.model.TestEntity;
import org.junit.Test;

/** 
 *Description: <测试类>. <br>
 *<p>
	<使用说明>
 </p>
 *Makedate:2014年9月26日 下午3:02:28 
 * @author Administrator  
 * @version V1.0                             
 */
public class CP_ExcelReaderUtilTest {
	
	
	@Test
	public void readExcel2003_twoColumns() {
		String[] columns = {"name","password"};

		String filePath = CP_ExcelReaderUtilTest.class.getResource("/").getPath()
				+ "testEntity_twoColumns.xls";

		System.out.println(filePath);

		// 从第二行第一列开始读取
		CP_ExcelReaderUtil<TestEntity> reder = new CP_ExcelReaderUtil<TestEntity>(
				new TestEntity(), filePath,
				CP_ExcelReaderUtil.getExcel2EntityConfig(columns, 2, 1));

		List<TestEntity> list = reder.readExcelToList();

		for (TestEntity e : list) {
			
			System.out.print(e.getName() + " ");
			System.out.println(e.getPassword() + " ");
			
		}
	}

	@Test
	public void readExcel2003() {

		String[] columns = CP_ExcelReaderUtil
				.getEntityColumns(TestEntity.class);

		String filePath = CP_ExcelReaderUtilTest.class.getResource("/").getPath()
				+ "testEntity.xls";

		System.out.println(filePath);

		// 从第二行第一列开始读取
		CP_ExcelReaderUtil<TestEntity> reder = new CP_ExcelReaderUtil<TestEntity>(
				new TestEntity(), filePath,
				CP_ExcelReaderUtil.getExcel2EntityConfig(columns, 2, 1));

		List<TestEntity> list = reder.readExcelToList();

		for (TestEntity e : list) {
			System.out.print(e.getId() + " ");
			System.out.print(e.getName() + " ");
			System.out.print(e.getPassword() + " ");
			System.out.println(DateFormat.getDateInstance(DateFormat.DEFAULT)
					.format(e.getBirthday()) + " ");
		}
	}
	
	@Test
	public void readExcel2007() {

		String[] columns = CP_ExcelReaderUtil
				.getEntityColumns(TestEntity.class);
		
		String filePath = CP_ExcelReaderUtilTest.class.getResource("/").getPath()
				+ "testEntity.xlsx";
		
		System.out.println(filePath);
		
		// 从第二行第一列开始读取		
		CP_ExcelReaderUtil<TestEntity> reder = new CP_ExcelReaderUtil<TestEntity>(
				new TestEntity(), filePath,
				CP_ExcelReaderUtil.getExcel2EntityConfig(columns, 2, 1), "xlsx");
		
		List<TestEntity> list = reder.readExcelToList();
		
		for (TestEntity e : list) {
			System.out.print(e.getId() + " ");
			System.out.print(e.getName() + " ");
			System.out.print(e.getPassword() + " ");
			System.out.println(DateFormat.getDateInstance(DateFormat.DEFAULT)
					.format(e.getBirthday()) + " ");
		}
	}
	
	@Test
	public void readExcel2007_02() {

		String[] columns = CP_ExcelReaderUtil
				.getEntityColumns(TestEntity.class);
		
		String filePath = CP_ExcelReaderUtilTest.class.getResource("/").getPath()
				+ "testEntity.xlsx";
		
		System.out.println(filePath);
		
		// 从第二行第一列开始读取		
		CP_ExcelReaderUtil<TestEntity> reder = new CP_ExcelReaderUtil<TestEntity>(
				new TestEntity(), filePath,
				CP_ExcelReaderUtil.getExcel2EntityConfig(columns, 2, 1,"xlsx"));
		
		List<TestEntity> list = reder.readExcelToList();
		
		for (TestEntity e : list) {
			System.out.print(e.getId() + " ");
			System.out.print(e.getName() + " ");
			System.out.print(e.getPassword() + " ");
			System.out.println(DateFormat.getDateInstance(DateFormat.DEFAULT)
					.format(e.getBirthday()) + " ");
		}
	}
	
	@Test
	public void readExcel2007ByFirstRow() {

		String[] columns = null; //赋值字段从excel的第一个sheet的第一行读取
		
		String filePath = CP_ExcelReaderUtilTest.class.getResource("/").getPath()
				+ "testEntityByFirstRow.xlsx";
		
		System.out.println(filePath);
		
		// 从第二行第一列开始读取		
		CP_ExcelReaderUtil<TestEntity> reder = new CP_ExcelReaderUtil<TestEntity>(
				new TestEntity(), filePath,
				CP_ExcelReaderUtil.getExcel2EntityConfig(columns, 2, 1), "xlsx");
		
		List<TestEntity> list = reder.readExcelToList();
		
		for (TestEntity e : list) {
			System.out.print(e.getId() + " ");
			System.out.print(e.getName() + " ");
			System.out.print(e.getPassword() + " ");
			System.out.println(DateFormat.getDateInstance(DateFormat.DEFAULT)
					.format(e.getBirthday()) + " ");
		}
	}
	
	
	@Test
	public void readExcel2003_subClass() {
		String[] columns = null;

		String filePath = CP_ExcelReaderUtilTest.class.getResource("/").getPath()
				+ "testEntity_subClass.xls";

		System.out.println(filePath);

		// 从第二行第一列开始读取
		CP_ExcelReaderUtil<TestEntity> reder = new CP_ExcelReaderUtil<TestEntity>(
				new TestEntity(), filePath,
				CP_ExcelReaderUtil.getExcel2EntityConfig(columns, 2, 1));

		List<TestEntity> list = reder.readExcelToList();

		for (TestEntity e : list) {
			
			System.out.print(e.getId() + " ");
			System.out.print(e.getName() + " ");
			System.out.print(e.getPassword() + " ");
			System.out.print(DateFormat.getDateInstance(DateFormat.DEFAULT)
					.format(e.getBirthday()) + " ");			
			System.out.println(e.getTestSub().getId());
			
		}
	}

}


