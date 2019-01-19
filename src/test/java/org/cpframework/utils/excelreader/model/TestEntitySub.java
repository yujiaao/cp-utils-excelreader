/*
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                   *
 ****************************************************************************
 * 源文件名:  com.netqin.common.util.excel.example.TestEntitySub.java 													       
 * 功能: cpframework框架													   
 * 版本:	@version 1.0	                                                                   
 * 编制日期: 2014年9月26日 上午10:19:36 						    						                                        
 * 修改历史: (主要历史变动原因及说明)		
 * YYYY-MM-DD |    Author      |	 Change Description		      
 * 2014年9月26日    |    Administrator     |     Created 
 */
package org.cpframework.utils.excelreader.model;

import java.io.Serializable;

/** 
 *Description: <类功能描述>. <br>
 *<p>
	<使用说明>
 </p>
 *Makedate:2014年9月26日 上午10:19:36 
 * @author Administrator  
 * @version V1.0                             
 */
public class TestEntitySub implements Serializable{

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = -6298038850570747974L;
	
	private String id;
	private String name;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}


