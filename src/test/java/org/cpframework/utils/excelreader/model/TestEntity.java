/*
 * COPYRIGHT Beijing NetQin-Tech Co.,Ltd.                                   *
 ****************************************************************************
 * 源文件名:  com.netqin.common.util.excel.example.TestEntity.java 													       
 * 功能: cpframework框架													   
 * 版本:	@version 1.0	                                                                   
 * 编制日期: 2014年9月23日 下午3:50:05 						    						                                        
 * 修改历史: (主要历史变动原因及说明)		
 * YYYY-MM-DD |    Author      |	 Change Description		      
 * 2014年9月23日    |    Administrator     |     Created 
 */
package org.cpframework.utils.excelreader.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Description: <类功能描述>. <br>
 * <p>
 * <使用说明>
 * </p>
 * Makedate:2014年9月23日 下午3:50:05
 * 
 * @author Administrator
 * @version V1.0
 */
public class TestEntity implements Serializable {

	/** 
	* @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么) 
	*/ 
	private static final long serialVersionUID = 7332144243848068665L;
	private Integer id;
	private String name;
	private String password;
	private Date birthday;
	
	private TestEntitySub testSub;

	public TestEntitySub getTestSub() {
		return testSub;
	}

	public void setTestSub(TestEntitySub testSub) {
		this.testSub = testSub;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}
