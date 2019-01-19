
Download from 
https://hanqunfeng.iteye.com/blog/2122817

1.依赖apache-poi

 

2.支持xls和xlsx

 

3.支持按属性名称绑定数据值

 

4.支持从指定行、列开始读取

 

5.支持同时读取多个sheet

 

6.具体使用方式参见org.cpframework.utils.excelreader.CP_ExcelReaderUtilTest.java

比如：

Java代码 
```Java
String[] columns = CP_ExcelReaderUtil  
                .getEntityColumns(TestEntity.class);  
  
        String filePath = CP_ExcelReaderUtilTest.class.getResource("/").getPath()  
                + "testEntity.xls";  
  
  
        // 从第二行第一列开始读取  
        CP_ExcelReaderUtil<TestEntity> reder = new CP_ExcelReaderUtil<TestEntity>(  
                new TestEntity(), filePath,  
                CP_ExcelReaderUtil.getExcel2EntityConfig(columns, 2, 1));  
  
        List<TestEntity> list = reder.readExcelToList();  
```