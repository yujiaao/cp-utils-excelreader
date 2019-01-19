/*
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                   *
 ****************************************************************************
 * 源文件名:  com.netqin.common.util.excel.CP_Excel2EntityConfig.java 													       
 * 功能: cpframework框架													   
 * 版本:	@version 1.0	                                                                   
 * 编制日期: 2014年9月23日 下午3:43:36 						    						                                        
 * 修改历史: (主要历史变动原因及说明)		
 * YYYY-MM-DD |    Author      |	 Change Description		      
 * 2014年9月23日    |    Administrator     |     Created 
 */
package org.cpframework.utils.excelreader;

import java.text.SimpleDateFormat;

/**
 * Description: <excel实体绑定配置类>. <br>
 * <p>
 * <使用说明>
 * </p>
 * Makedate:2014年9月23日 下午3:43:36
 * 
 * @author Administrator
 * @version V1.0
 */
public class CP_Excel2EntityConfig {
	
	private String fileType = "xls";
	
	/** 
	* @Fields columns : TODO(绑定实体字段列表) 
	*/ 
	private String[] columns;
	
	/** 
	* @Fields formater : TODO(默认日期格式) 
	*/ 
	private SimpleDateFormat formater = new SimpleDateFormat(
			"yyyy-MM-dd HH：mm：ss ");

	/** 
	* @Fields currPosittion : TODO(开始读取的行，从1开始计算) 
	*/ 
	private int currPosittion = 0;

	
	/** 
	* @Fields colStartPosittion : TODO(开始读取的列，从1开始计算) 
	*/ 
	private int colStartPosittion = 1;

	public SimpleDateFormat getFormater() {
		return this.formater;
	}

	public void setFormater(SimpleDateFormat formater) {
		this.formater = formater;
	}

	public String[] getColumns() {
		return this.columns;
	}

	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	public int getCurrPosittion() {
		return this.currPosittion;
	}

	public void setCurrPosittion(int currPosittion) {
		this.currPosittion = (currPosittion - 1);
	}

	public int getColStartPosittion() {
		return this.colStartPosittion;
	}

	public void setColStartPosittion(int colStartPosittion) {
		this.colStartPosittion = (colStartPosittion - 1);
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	
	
}
