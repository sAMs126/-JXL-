package com.gcl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
 
import jxl.Cell;
import jxl.CellType;
import jxl.NumberCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * 创建excel表格
 * @author sAms
 * @date 2018/5/9
 */

public class ExcelUtils {
    //该类中的方法都是静态的，所以该类是不需要的创建对象的。为了保证不让其他成创建该类对象  
    //可以将构造函数私有化
    private ExcelUtils(){}  
    
	/**
	 * 创建成绩管理表，第一个sheet作为老师信息进行存放
	 * @param teacher	教师数据
	 */
    static void createTchExcel(Teacher teacher)throws IOException, RowsExceededException, WriteException{
	    //1:创建excel文件
		File file=new File("test.xls");
		file.createNewFile();
	    //2:创建工作簿
	    WritableWorkbook workbook=Workbook.createWorkbook(file);
	    //3:创建工作表名称
        WritableSheet sheet=workbook.createSheet("教师账号信息", 0);
        WritableSheet sheet1=workbook.createSheet("学生成绩信息", 1);
        //4：设置titles
        String[] titles={"教师编号","账号","密码"};
        //5:单元格
        Label label=null;
        //6:给第一行设置列名
        for(int i=0;i<titles.length;i++){
            //第一行的列名
        	//jxl.write.Label.Label(int col(列), int row(行), String title(标题))
            label=new Label(i,0,titles[i]);
            //7：添加单元格
            sheet.addCell(label);
        }

        for(int i=1;i<2;i++){
            //8:设置教师编号
            label = new Label(0, i, "t01");
            sheet.addCell(label);
            
            //9:记录账号
            label = new Label(1, i, teacher.getAccont());
            sheet.addCell(label);
            
            //10：记录密码
            label = new Label(2, i, teacher.getPassword());
            sheet.addCell(label);
        }

        //写入数据
        workbook.write();
        //最后一步，关闭工作簿
        workbook.close();
	}
    /**
     * 查询教师表，根据输入的信息和教师表中的信息进行对比
     * @param account		输入的账号
     * @param code			输入的密码
     * @param tch			将信息存入的对象
     * @throws Exception
     */
    static int readTchExcel(String account,String code,Teacher tch)throws Exception{
        //1:根据原有excel创建workbook
        Workbook workbook=Workbook.getWorkbook(new File("test.xls")); 
        //2:获取教师信息工作表对应的sheet
        Sheet sheet=workbook.getSheet(0);
        
        //3:获取数据,并进行比较
        /*
         * 教师表：第一行标题，
         * 第二行第一列是教师编号，第二列是账号，第三列是密码
         */
        for(int i=1; i<sheet.getRows(); i++){
        	//jxl.Sheet.getCell(int col(列), int row(行))
        	Cell cell_ac = sheet.getCell(1, i);//获取第二列的账号
        	Cell cell_cd = sheet.getCell(2, i);//获取第三列的密码
        	if ( !account.equals( cell_ac.getContents() ) ){
        		//账号不正确
        		continue;
        	}else if( !code.equals( cell_cd.getContents() ) ){
        		//密码不正确
        		return 2;
        	}else{
        		/*
        		 * 验证通过
        		 * 将账号、密码存入传入的对象方便进行修改
        		 */
        		tch.setAccont(cell_ac.getContents());
        		tch.setPassword(cell_cd.getContents());
        		return 3;
        	}
        }
        //4：关闭资源
        workbook.close();
        
        return 1;
    }
    /**
     * 教师密码修改，根据账号进行定位
     * @param code			输入的原始密码
     * @param newPass		输入的新密码
     * @param teacher		当前教师的信息
     * @throws Exception
     */
    static void updataTchExcel(String code,String newPass, Teacher teacher)throws Exception{
        //1:根据原有excel创建workbook
    	File file=new File("test.xls");
        Workbook workbook=Workbook.getWorkbook(new File("test.xls")); 
        //2:创建一个副本
        WritableWorkbook copyExcel = Workbook.createWorkbook(file,workbook);
        //3:获取教师信息工作表对应的sheet
      	WritableSheet sheet = copyExcel.getSheet(0);
      	
      	//4:对密码进行修改
      	/*
      	 * 修改教师账户密码
      	 * 根据教师账号查找到该信息对应的行，修改第三列密码
      	 */
      	for(int i=1; i<sheet.getRows(); i++){
      		Cell cell_ac = sheet.getCell(1, i);//获取第二列的账号
      		if( !teacher.getAccont().equals( cell_ac.getContents() ) ){ 
      			//当前行信息不属于该教师信息
      			continue;
      		}else if( !teacher.getPassword().equals(code) ){
      			//输入原始密码与账户中密码不匹配
      			continue;
      		}else{
      			/*
      			 * 匹配成功
      			 * 用新设置的密码替换旧密码 
      			 */
      			Label pas_label = new Label(2, i, newPass);
      			sheet.addCell(pas_label);
      			break;
      		}
      	}
        //写入Excel对象 
      	copyExcel.write(); 
        //关闭可写入的Excel对象 
      	copyExcel.close(); 
        //关闭只读的Excel对象 
      	workbook.close(); 
    }
    /**
     * 在原有成绩管理表中，将第二个sheet作为学生信息进行存放
     * @param stu			学生信息
     * @param m				学生科目总数
     * @param a				课程列表
     * @param n				学生个数
     * @throws Exception
     */
    static void createStuExcel(Student stu[],int m,String a[], int n)throws Exception{
        //1:根据原有excel创建workbook
    	File file=new File("test.xls");
        Workbook workbook=Workbook.getWorkbook(new File("test.xls")); 
        //2:创建一个副本
        WritableWorkbook copyExcel = Workbook.createWorkbook(file,workbook);
        //3:获取学生信息工作表对应的sheet
      	WritableSheet sheet = copyExcel.getSheet(1);
        //4:初始化单元格
        Label label=null;
        //5：设置titles
        /*
         * 将第一个学生的课程作为初始化课程列表
         * 录入时对比字符串，找出不同的进行表头添加
         * 
         * 获取第一行第一列单元格，该单元格为 "学生学号" 
         * 若(0,0)为空则证明工作表标题尚未填写
         */
        String[] titles = new String[15];
      	if(sheet.getCell(0, 0).getType() == CellType.EMPTY){
      		//标题为空
      		 titles[0]="学生学号"; titles[1]="学生姓名"; titles[2]="学生年龄";
      		 for(int i=0; i<m; i++){
      			 titles[i+3] = a[i];
      		 }
             //给第一行设置列名
             for(int i=0;i<titles.length;i++){
            	 if(titles[i]!=""){
                     //第一行的列名
                     label=new Label(i,0,titles[i]);
                     sheet.addCell(label);
            	 }
             }
      	}else{
      		/*
      		 * 标题不为空，对titles[]和a[]进行比较
      		 * contain()比较是否a[]包含在titles[]中
      		 * 包含则省略标题添加过程，否则添加
      		 */

      		//得到当前标题栏内容,并存放到titles[]中
            //获取第1行所有的单元格 
            jxl.Cell[] cells = sheet.getRow(0); 
	      	for(int i=0; i<cells.length; i++){
	      		if(cells[i] != null){
		            titles[i] = cells[i].getContents();	      			
	      		}
	        }
	      	
	      	List<String> titlesList = Arrays.asList(titles);
	      	//对titles[]和a[]进行比较
	      	for(int i=0; i<a.length ;i++){
	      		if(!titlesList.contains(a[i])){
	      			//当前科目不在原始标题中,则添加到标题栏末尾
	      			titles[sheet.getColumns()]=a[i];
	      			Label titlesLabel = new Label(sheet.getColumns(), 0, a[i]);
	      			sheet.addCell(titlesLabel);
	      		}
	      	}
      	}
      	//6:填入学生信息
      	/*
      	 * 前三列：学生学号 , 学生姓名 , 学生年龄	相同，先填写前三列
      	 *之后根据对应的学科标题填写对应的学生成绩，若学生没有此成绩，此项为空
      	 */
      	//先填写前三列的信息
        for(int j=0; j<n; j++){
        	jxl.write.Number stuNum = new Number(0, sheet.getRows()+j, stu[j].getNumber());
        	Label stuName = new Label(1, sheet.getRows()+j, stu[j].getName());
        	jxl.write.Number stuAge = new Number(2, sheet.getRows()+j, stu[j].getAge());
        	
        	sheet.addCell(stuNum);
        	sheet.addCell(stuName);
        	sheet.addCell(stuAge);
        }
 
        List<String> titlesList = Arrays.asList(titles);
        List<Integer> stuNumList = new ArrayList<Integer>();
        //获取第1列学生学号的单元格
         for(int i=1; i<sheet.getRows(); i++){
        	 if( sheet.getCell(0,i).getType() == CellType.NUMBER ){
        		 NumberCell cellNum = (NumberCell) sheet.getCell(0, i);
        		 if(!cellNum.toString().equals("")){
        			 stuNumList.add((int) cellNum.getValue());
             	}
        	 }
        }
        //根据科目进行填写
        for(int i=0; i<n; i++){
    		//学生信息不为空
        	for(int j=0;j<m; j++){
        		jxl.write.Number stuGrade = new Number(titlesList.indexOf(stu[i].getProject(j)), stuNumList.indexOf(stu[i].getNumber())+1, stu[i].getGrade(j));
        		sheet.addCell(stuGrade);
        	}
        }
        
        //写入Excel对象 
      	copyExcel.write(); 
        //关闭可写入的Excel对象
      	copyExcel.close(); 
        //关闭只读的Excel对象 
      	workbook.close(); 
    }
    /**
     * 查询学生成绩
     * @return	返回读取到的信息并存入Student[]中
     * @throws Exception
     */
    static Student[] readStuExcel()throws Exception{
        //1:根据原有excel创建workbook
        Workbook workbook=Workbook.getWorkbook(new File("test.xls")); 
        //2:获取学生信息工作表对应的sheet
        Sheet sheet=workbook.getSheet(1);
        //3.获取数据，存入学生对象数组中
        /*
         * 存学生成绩时先匹配学生是否存在此科目成绩
         * 若有则存入对应科目名的grade中，否则不存入
         */
      	//学生个数：总函数-1 （标题栏）
        Student[] stu = new Student[sheet.getRows()-1];
        for(int i=1; i<sheet.getRows(); i++){
        	List<String> project = new ArrayList<String>();//暂存科目
        	List<Double> grade = new ArrayList<Double>();//暂存分数
        	stu[i-1] = new Student();		//初始化学生
        	//学号，姓名，年龄
        	for(int j=0; j<3; j++){
        		switch (j) {
				case 0://存学号
					NumberCell cellNum = (NumberCell) sheet.getCell(0, i);
					stu[i-1].setNumber((int)cellNum.getValue());
					break;
				case 1://存姓名
					Cell cellName = sheet.getCell(1, i);
					stu[i-1].setName(cellName.getContents());
					break;
				case 2://存年龄
					NumberCell cellAge = (NumberCell) sheet.getCell(2, i);
					stu[i-1].setAge((int)cellAge.getValue());
					break;
				}
        	}
        	//查成绩
        	for(int j=3; j<sheet.getColumns(); j++){
        		if(sheet.getCell(j, i).getType()!= CellType.EMPTY){
            		NumberCell cellNum = (NumberCell) sheet.getCell(j, i);
        			project.add(sheet.getCell(j, 0).getContents());
        			grade.add(cellNum.getValue());
        		}
        	}
        	//成绩，科目转为数组
        	String[] pro = project.toArray(new String[project.size()]);
        	double[] grd = new double[grade.size()];
        	for(int j = 0;j<grade.size();j++){  
        	    grd[j] = grade.get(j);  
        	}
        	//存成绩
        	for(int j=0; j<pro.length; j++){
        		stu[i-1].setProject(pro);
        		stu[i-1].setGrade(grd);
        	}
        	stu[i-1].setAverage(grade.size());
        }
        //4：关闭资源
        workbook.close();
        return stu;
    }
    /**
     * 根据学号删除学生信息
     * @param stuNum		要删除的学生学号
     * @throws Exception
     */
    static void deleteStuExcel(int stuNum)throws Exception{
        //1:根据原有excel创建workbook
    	File file=new File("test.xls");
        Workbook workbook=Workbook.getWorkbook(new File("test.xls")); 
        //2:创建一个副本
        WritableWorkbook copyExcel = Workbook.createWorkbook(file,workbook);
        //3:获取学生信息工作表对应的sheet
      	WritableSheet sheet = copyExcel.getSheet(1);
      	
      	//4.获取第1列学生学号的单元格
        List<Integer> stuNumList = new ArrayList<Integer>();
        for(int i=1; i<sheet.getRows(); i++){
	    	 if( sheet.getCell(0,i).getType() == CellType.NUMBER ){
	    		 NumberCell cellNum = (NumberCell) sheet.getCell(0, i);
	    		 if(!cellNum.toString().equals("")){
	    			 stuNumList.add((int) cellNum.getValue());
	         	}
	    	 }
	    }
        //5.根据stuNum删除对应的学生信息行
        sheet.removeRow(stuNumList.indexOf(stuNum)+1);
        
        //写入Excel对象 
      	copyExcel.write(); 
        //关闭可写入的Excel对象
      	copyExcel.close(); 
        //关闭只读的Excel对象 
      	workbook.close(); 
    }
    
    static void updataStuExcel(Student stu,int flag)throws Exception{
        //1:根据原有excel创建workbook
    	File file=new File("test.xls");
        Workbook workbook=Workbook.getWorkbook(new File("test.xls")); 
        //2:创建一个副本
        WritableWorkbook copyExcel = Workbook.createWorkbook(file,workbook);
        //3:获取学生信息工作表对应的sheet
      	WritableSheet sheet = copyExcel.getSheet(1);
      	
      	//4.获取第1列学生学号的单元格
        List<Integer> stuNumList = new ArrayList<Integer>();
        for(int i=1; i<sheet.getRows(); i++){
	    	 if( sheet.getCell(0,i).getType() == CellType.NUMBER ){
	    		 NumberCell cellNum = (NumberCell) sheet.getCell(0, i);
	    		 if(!cellNum.toString().equals("")){
	    			 stuNumList.add((int) cellNum.getValue());
	         	}
	    	 }
	    }
        //获取第1行所有的单元格 
        List<String> titleList = new ArrayList<String>(); 
        jxl.Cell[] cells = sheet.getRow(0); 
      	for(int i=0; i<cells.length; i++){
      		if(cells[i] != null){
	            titleList.add(cells[i].getContents());	      			
      		}
        }
      	
        //5.确定学生信息所在的行号
        int stuLocation = stuNumList.indexOf(stu.getNumber())+1;
      	//6.根据传入的修改选项进行信息修改
      	switch (flag) {
		case 1://修改姓名
			Label nameLabel = new Label(1, stuLocation, stu.getName());
  			sheet.addCell(nameLabel);
			break;
		case 2://修改年龄
			jxl.write.Number stuAge = new Number(2, stuLocation, stu.getAge());
			sheet.addCell(stuAge);
			break;
		case 3://修改成绩
			for(int i=0; i<stu.getCount(); i++){
				jxl.write.Number stuGrade = new Number(titleList.indexOf(stu.getProject(i)), stuLocation, stu.getGrade(i));
        		sheet.addCell(stuGrade);
			}
			break;
		}
      	
        //写入Excel对象 
      	copyExcel.write(); 
        //关闭可写入的Excel对象 
      	copyExcel.close(); 
        //关闭只读的Excel对象 
      	workbook.close(); 
    	
    }

    
}
