/*
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                   *
 ****************************************************************************
 * 源文件名:  com.netqin.common.util.excel.CP_ExcelReaderUtil.java 													       
 * 功能: cpframework框架													   
 * 版本:	@version 1.0	                                                                   
 * 编制日期: 2014年9月23日 下午3:56:06 						    						                                        
 * 修改历史: (主要历史变动原因及说明)		
 * YYYY-MM-DD |    Author      |	 Change Description		      
 * 2014年9月23日    |    Administrator     |     Created 
 */
package org.cpframework.utils.excelreader;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Description: <类功能描述>. <br>
 * <p>
 * <使用说明>
 * </p>
 * Makedate:2014年9月23日 下午3:56:06
 * 
 * @author Administrator
 * @version V1.0
 */
public class CP_ExcelReaderUtil<E> {
	
	private String fileType = "xls";

	/**
	 * Excel文件读取类
	 */
	private CP_ExcelReader<E> excel;
	/**
	 * 实体对像
	 */
	private E entity;

	private void init(E entity, String filePath, CP_Excel2EntityConfig config,String fileType) {
		this.entity = entity;
		excel = new CP_ExcelReader<E>(fileType);
		excel.setExcel2EntityConfig(config);

		File file = new File(filePath);
		InputStream input;
		try {
			input = new FileInputStream(file);

			excel.InitExcelReader(input);
			excel.setEntity(this.entity);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		// 如果现现EXCEl编码问题引起的读取问题，请将InputStream换成 ByteArrayInputStream 可解决问题
		// ByteArrayInputStream input = new ByteArrayInputStream(byte[])
	}
	
	private void init(E entity, String filePath, CP_Excel2EntityConfig config) {
		if(config.getFileType() != null){
			this.fileType = config.getFileType();
		}
		
		init(entity, filePath,config,this.fileType);
	}

	/**
	 * @param filePath
	 *            excel文件路径
	 * @param columns
	 *            要封住的自动列表
	 */
	public CP_ExcelReaderUtil(E entity, String filePath, String[] columns,String fileType) {

		init(entity, filePath,
				CP_ExcelReaderUtil.getExcel2EntityConfig(columns, 2, 1),fileType);
	}
	
	/**
	 * @param filePath
	 *            excel文件路径
	 * @param columns
	 *            要封住的自动列表
	 */
	public CP_ExcelReaderUtil(E entity, String filePath, String[] columns) {
		
		init(entity, filePath,
				CP_ExcelReaderUtil.getExcel2EntityConfig(columns, 2, 1));
	}

	/**
	 * @param filePath
	 *            excel文件路径
	 * @param columns
	 *            要封住的自动列表
	 * @param currPosittion
	 *            开始读取的行，从1开始计算
	 * @param colStartPosittion
	 *            开始读取的列，从1开始计算
	 */
	public CP_ExcelReaderUtil(E entity, String filePath, String[] columns,
			int currPosittion, int colStartPosittion,String fileType) {

		init(entity, filePath, CP_ExcelReaderUtil.getExcel2EntityConfig(
				columns, currPosittion, colStartPosittion),fileType);
	}
	/**
	 * @param filePath
	 *            excel文件路径
	 * @param columns
	 *            要封住的自动列表
	 * @param currPosittion
	 *            开始读取的行，从1开始计算
	 * @param colStartPosittion
	 *            开始读取的列，从1开始计算
	 */
	public CP_ExcelReaderUtil(E entity, String filePath, String[] columns,
			int currPosittion, int colStartPosittion) {
		
		init(entity, filePath, CP_ExcelReaderUtil.getExcel2EntityConfig(
				columns, currPosittion, colStartPosittion));
	}

	/**
	 * @param filePath
	 *            excel文件路径
	 * @param config
	 *            读取excel的规则
	 */
	public CP_ExcelReaderUtil(E entity, String filePath,
			CP_Excel2EntityConfig config,String fileType) {

		init(entity, filePath, config,fileType);

	}
	/**
	 * @param filePath
	 *            excel文件路径
	 * @param config
	 *            读取excel的规则
	 */
	public CP_ExcelReaderUtil(E entity, String filePath,
			CP_Excel2EntityConfig config) {
		
		init(entity, filePath, config);
		
	}

	
	public static CP_Excel2EntityConfig getExcel2EntityConfig(String[] columns,
			int currPosittion, int colStartPosittion) {
		
		return getExcel2EntityConfig(columns,currPosittion,colStartPosittion,null,null);

	}
	
	public static CP_Excel2EntityConfig getExcel2EntityConfig(String[] columns,
			int currPosittion, int colStartPosittion,String fileType) {
		
		return getExcel2EntityConfig(columns,currPosittion,colStartPosittion,fileType,null);

	}
	
	/**
	 * 描述 : <初始化excel读取规则>. <br>
	 * <p>
	 * 
	 * @param columns
	 *            封装那些字段
	 * @param currPosittion
	 *            开始读取的行，从1开始计算
	 * @param colStartPosittion
	 *            开始读取的列，从1开始计算
	 * @param fileType
	 *            excel类型 ：xls 或者  xlsx
	 * @param dateFormat
	 *            日期格式化
	 * @return
	 */
	public static CP_Excel2EntityConfig getExcel2EntityConfig(String[] columns,
			int currPosittion, int colStartPosittion,String fileType,String dateFormat) {
		CP_Excel2EntityConfig config = new CP_Excel2EntityConfig();
		
		if (columns != null && ArrayUtils.contains(columns, null)) {
			columns = ArrayUtils.removeElement(columns, null);
		}
		config.setColumns(columns);
		if(dateFormat != null){
		// 设置日期的格式，和Excel里的日期格式一至
			config.setFormater(new SimpleDateFormat(dateFormat)); //dateFormat yyyy-MM-dd
		}
		
		// 设置从第几行开始读，从1开始计算
		config.setCurrPosittion(currPosittion);
		
		// 设置从第几列开始读取，从1开始计算
		config.setColStartPosittion(colStartPosittion);
		
		//设置默认的文件类型
		config.setFileType(fileType);
		return config;
		
	}

	/**
	 * 描述 : <获取实体的属性名称列表>. <br>
	 * <p>
	 * 
	 * @param obj
	 * @return
	 */
	public static String[] getEntityColumns(
			@SuppressWarnings("rawtypes") Class obj) {
		Field[] field = obj.getDeclaredFields();

		String[] columns = new String[field.length];
		for (int j = 0; j < field.length; j++) { // 遍历所有属性
			String name = field[j].getName(); // 获取属性的名字
			if (!"serialVersionUID".equals(name)) {
				columns[j] = name;
			}
		}
		return columns;
	}

	/**
	 * 描述 : <将excel中的数据保存近包含实体的list中>. <br>
	 * <p>
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<E> readExcelToList() {

		List<E> list = new ArrayList<E>();
		try {
			this.entity = excel.readLine();
			while (this.entity != null) {
				list.add(this.entity);
				excel.setEntity((E) this.entity.getClass().newInstance());
				this.entity = excel.readLine();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			excel.close();
		}
		return list;
	}

}
