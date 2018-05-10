package com.gcl;

import java.util.Scanner;

public class MAIN {
    private static String[] a=new String[5];
//    private static Teacher ac=new Teacher("Admin","123456");     	//初始化教师账号密码
    private static Teacher tch=new Teacher();
    private static Student su[]=new Student[10];                       //实例化学生类数组(一次性只能添加10个)
    private static Student stu[]=new Student[10];  						//存读入的学生信息
    public static void main(String[] args)
    {	
//        try {
//			ExcelUtils.createTchExcel(ac);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
        surfaceShow();                                                        //进入系统初始页面
    }
    //系统初始页面
    private static void surfaceShow()
    {
    	findStu();
        System.out.println("----------欢迎来到学生综合信息管理系统----------");
        System.out.println("-                   请先登录：                 -");
        System.out.println("-                   1.教师登录                 -");
        System.out.println("-                   2.学生登录                 -");
        System.out.println("-                   0.退出                     -");
        System.out.println("------------------------------------------------");
        Scanner in=new Scanner(System.in);
        int ch=in.nextInt();
        if (ch==1) Login();
        else if (ch==2) Student();
        else if (ch==0) System.exit(0);
        else System.exit(1);
    }
    //教师登录页面
//    private static void Login(Teacher ac)                                     //把ac当作参数传入
    private static void Login()
    {
        Scanner in=new Scanner(System.in);
        System.out.println("请输入账户名称：");
        String c=in.nextLine();
//        if (!c.equals(ac.getAccont()))
//        {
//            System.out.println("账号不存在！");
//            MAIN.Login(ac);
//        }
        System.out.println("请输入密码：");
        String b=in.nextLine();
//        //构造代码块
//        {
//            if (!b.equals(ac.getPassword()))
//            {
//                System.out.println("密码错误！");
//                MAIN.Login(ac);
//            }
//        }
        try {
			int flag = ExcelUtils.readTchExcel(c, b,tch);
	        switch (flag) {
			case 1:
				System.out.println("账号不存在！");MAIN.Login();
				break;
			case 2:
				System.out.println("密码错误！");MAIN.Login();
				break;
			case 3:
		        System.out.println("登录成功！");
		        MAIN.Teacher();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//        System.out.println("登录成功！");
//        MAIN.Teacher();
    }
    
    //教师主页面
    private static void Teacher()
    {
    	findStu();
        System.out.println("-----------------------------------------------");
        System.out.println("-----请选择你要执行的操作：     ---------------");
        System.out.println("-----1.创建学生信息             ---------------");
        System.out.println("-----2.查询学生信息             ---------------");
        System.out.println("-----3.显示学生信息             ---------------");
        System.out.println("-----4.删除学生信息             ---------------");
        System.out.println("-----5.修改学生信息             ---------------");
        System.out.println("-----6.修改密码                 ---------------");
        System.out.println("-----0.退出系统                 ---------------");
        Scanner ch=new Scanner(System.in);
        int c=ch.nextInt();
        if (c==1) Create();
        else if (c==2) Search(1);
        else if (c==3) {findStu();allShow();}
        else if (c==4) {findStu();Delete();}
        else if (c==5) {findStu();Renew();}
        else if (c==6) Recode();
        else if (c==0) surfaceShow();
        else
        {
            System.out.println("输入错误！");
            Teacher();
        }
    }
    //学生主页面
    private static void Student()
    {
        System.out.println("-----------------------------------------------");
        System.out.println("-----请选择你要执行的操作：     ---------------");
        System.out.println("-----1.查询学生信息             ---------------");
        System.out.println("-----0.退出系统                 ---------------");
        Scanner In=new Scanner(System.in);
        int ch=In.nextInt();
        if (ch==1) Search(2);
        else if (ch==0) surfaceShow();
        else
        {
            System.out.println("输入错误！");
            Student();
        }
    }
    //创建学生信息
    private static void Create()
    {
        String str;
        int num1=0,t;
        int n = 0;														//学生数量
        int m=0;                                      				      //科目数量
        double[] g=new double[50];										//各科分数暂存变量
        while(true)
        {
            su[n]=new Student();
            System.out.println("请输入学生学号:");
            Scanner in=new Scanner(System.in);
            num1=in.nextInt();
            for (int i=0;i<=n;i++)                                        //检测学号是否重复（遗留项！！！！！！！！！！！！！！）
            {
                while(su[i].getNumber() == num1)
                {
                    System.out.println("已经存在该学号，请重新输入：");
                    num1=in.nextInt();
                }
            }
            su[n].setNumber(num1);
            System.out.println("请输入学生姓名：");
            str=in.next();
            su[n].setName(str);
            System.out.println("请输入学生年龄:");
            t=in.nextInt();
            su[n].setAge(t);
            if (n==0)                                                    //单次创建时，在创建第一个学生时，同时创建学科的数量和名称
            {															//没有考虑多次创建，科目不同的情况（遗留项！！！！！！！！！）
                System.out.println("请输入N门课");
                m=in.nextInt();
                System.out.println("请分别输入"+m+"门科目的名字：");
                for(int i=0;i<m;i++)
                {
                    a[i]=in.next();
                }
            }
            su[n].setProject(a);										//将科目信息存放到对应的学生信息中
            for (int i=0;i<m;i++)
            {
                System.out.println("请输入"+su[n].getProject(i)+"科目的成绩：");
                g[i]=in.nextInt();
            }
            su[n].setGrade(g);
            su[n].setAverage(m);
            n++;
            System.out.println("是否继续添加?(是/Y，否/N)");
            String cho=in.next();
            char ch=cho.charAt(0);
            if (ch=='N'||ch=='n')
				try {
					ExcelUtils.createStuExcel(su,m,a,n);			
				} catch (Exception e) {
					e.printStackTrace();
				}
                Teacher();                                                       //返回教师主页面
            while(ch!='N'&&ch!='n'&&ch!='Y'&&ch!='y')
            {
                System.out.println("输入无效，请重新输入：");
                cho=in.next();
                ch=cho.charAt(0);
            }
        }
    }
    //展示学生信息
    private static void allShow()
    {	
    	try {
    		stu = ExcelUtils.readStuExcel();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
        System.out.println("学生信息如下：");
        for (int i=0; i<stu.length; i++)
        {
            System.out.println("第"+(i+1)+"位学生：");
            display(i);
        }
        System.out.println("输入任意值返回主菜单");
        Scanner in=new Scanner(System.in);
        in.next();
        Teacher();
    }
    //搜索学生信息，参数是用来判断访问该函数的是学生还是老师
    private static void Search(int jud)
    {
    	findStu();
        System.out.println("----------------------");
        System.out.println("----请选择搜索方式----");                                    //选择搜索方式
        System.out.println("----1.按学号搜索  ----");
        System.out.println("----2.按姓名搜索  ----");
        System.out.println("----0.返回主菜单  ----");
        System.out.println("----------------------");
        Scanner In=new Scanner(System.in);
        int ch=In.nextInt();
        boolean temp=false;
        if (ch==1)
        {
            System.out.println("请输入学号：");
            int nu=In.nextInt();
            for (int i=0;i<stu.length;i++)
            {
                if (stu[i].getNumber()==nu)
                {
                    System.out.println("寻找成功");
                    display(i);
                    System.out.println("输入任意值返回主菜单");
                    Scanner in=new Scanner(System.in);
                    in.next();
                    if (jud==1)                                           //如果是老师访问，返回Teacher()
                      Teacher();
                    Student();                                            //否则返回Student()
                }
            }
            System.out.println("没有找到该学生，即将返回");
            if (jud==1)
                Teacher();
            Student();
        }
        else if (ch==2)
        {
            System.out.println("请输入姓名：");
            String na=In.next();
            for (int i=0;i<stu.length;i++)
            {
                if (stu[i].getName().equals(na))
                {
                    System.out.println("寻找成功");
                    display(i);
                    temp=true;
                }
            }
            if (temp)
            {
                System.out.println("输入任意值返回主菜单");
                Scanner in=new Scanner(System.in);
                in.next();
                if (jud==1)
                    Teacher();
                Student();
            }
            else
            {
                System.out.println("没有找到该学生，即将返回");
                if (jud==1)
                    Teacher();
                Student();
            }
        }
        else if (ch==0)
        {
            if (jud==1)
                Teacher();
            Student();
        }
        else
        {
            System.out.println("输入错误!");
            Search(jud);
        }
    }
    //删除学生信息
    private static void Delete()
    {
        System.out.println("请输入要删除的学生的学号:");
        Scanner In=new Scanner(System.in);
        int num=In.nextInt();
        boolean temp=true;                                                       //判断是否找到该学生
//        int tem=0;
        int i=0;
        for (i=0;i<stu.length;i++)
        {
            if (stu[i].getNumber()==num)
            {
                temp=false;
//                tem=i;                                                           //用tem记录该学生的位置
                break;
            }
        }
        if (temp)
        {
            System.out.println("未找到该学生信息");
            System.out.println("输入任意值返回主菜单");
            Scanner in=new Scanner(System.in);
            in.next();
            Teacher();
        }
        else
        {
            System.out.println("你要删除的学生信息:");
            display(i);
            System.out.println("你确定要删除该学生吗?(是/Y，否/N)");
            String ch=In.next();
            char se=ch.charAt(0);
            while(se!='N'&&se!='n'&&se!='Y'&&se!='y')
            {
                System.out.println("输入无效，请重新输入：");
                ch=In.next();
                se=ch.charAt(0);
            }
            if (se=='N'||se=='n') {
                System.out.println("返回主菜单");
                Teacher();
            }
            if (se=='Y'||se=='y')                                               
            {
//                for (i=tem;i<n-1;i++)					//通过线性表的删除操作来删除该学生的信息
//                {
//                    stu[i]=stu[i+1];
//                }
//                n--;
				try {
					ExcelUtils.deleteStuExcel(num);
				} catch (Exception e) {
//					e.printStackTrace();				//能够删除成功但是，会报错，Warning:  Cannot find sheet 学生成绩表  in supbook record （待解决）
					System.out.println("学生信息删除成功！");
				}
                
                System.out.println("输入任意值返回主菜单");
                Scanner in=new Scanner(System.in);
                in.next();
                Teacher();
            }
        }
    }
    //修改学生信息
    private static void Renew()
    {
        System.out.println("请输入要修改的学生的学号:");
        Scanner In=new Scanner(System.in);
        int num=In.nextInt();
        boolean temp=true;
//        int tem=0;
        int i=0;
        for (i=0;i<stu.length;i++)
        {
            if (stu[i].getNumber()==num)
            {
                temp=false;
//                tem=i;                                                           
                break;
            }
        }
        if (temp)
        {
            System.out.println("未找到该学生信息");
            System.out.println("输入任意值返回主菜单");
            Scanner in=new Scanner(System.in);
            in.next();
            Teacher();
        }
        else
        {
            System.out.println("你要修改的学生信息如下：");
            display(i);
            System.out.println("你确定要修改该学生吗?(是/Y，否/N)");
            String ch=In.next();
            char se=ch.charAt(0);
            while(se!='N'&&se!='n'&&se!='Y'&&se!='y')
            {
                System.out.println("输入无效，请重新输入：");
                ch=In.next();
                se=ch.charAt(0);
            }
            if (se=='N'||se=='n')
            {
                System.out.println("返回主菜单");
                Teacher();
            }
            if (se=='Y'||se=='y')
            {
                double[] g=new double[50];
                System.out.println("请选择修改的内容：");                                               //选择修改哪些内容
                System.out.println("---1.修改姓名----");
                System.out.println("---2.修改年龄----");
                System.out.println("---3.修改成绩----");
                System.out.println("---0.返回主菜单--");
                int cho=In.nextInt();
                if (cho==1)
                {
                    System.out.println("请输入姓名:");
                    String name=In.next();
//                    su[tem].setName(name);
                    stu[i].setName(name);
                    try {
						ExcelUtils.updataStuExcel(stu[i], cho);
					} catch (Exception e) {
						e.printStackTrace();
					}
                    System.out.println("修改成功!");
                    System.out.println("还要继续修改吗?(是/Y，否/N)");
                    ch=In.next();
                    se=ch.charAt(0);
                    while (se!='N'&&se!='n'&&se!='Y'&&se!='y')
                    {
                        System.out.println("输入无效，请重新输入：");
                        ch=In.next();
                        se=ch.charAt(0);
                    }
                    if (se=='N'||se=='n')
                    {
                        System.out.println("返回主菜单");
                        Teacher();
                    }
                    if (se=='y'||se=='Y')
                    {
                        Renew();
                    }
                }
                else if (cho==2)
                {
                    System.out.println("请输入年龄:");
                    int ag=In.nextInt();
//                    su[tem].setAge(ag);
                    stu[i].setAge(ag);
                    try {
						ExcelUtils.updataStuExcel(stu[i], cho);
					} catch (Exception e) {
						e.printStackTrace();
					}
                    System.out.println("修改成功!");
                    System.out.println("还要继续修改吗?(是/Y，否/N)");
                    ch=In.next();
                    se=ch.charAt(0);
                    while (se!='N'&&se!='n'&&se!='Y'&&se!='y')
                    {
                        System.out.println("输入无效，请重新输入：");
                        ch=In.next();
                        se=ch.charAt(0);
                    }
                    if (se=='N'||se=='n')
                    {
                        System.out.println("返回主菜单");
                        Teacher();
                    }
                    if (se=='y'||se=='Y')
                    {
                        Renew();
                    }
                }
                else if (cho==3)
                {
                    for (int j=0;j<stu[i].getCount();j++)
                    {
                        System.out.println("请输入"+stu[i].getProject(j)+"科目的成绩：");
                        g[j]=In.nextInt();
                    }
//                    su[tem].setGrade(g);
//                    su[tem].setAverage(m);
                    stu[i].setGrade(g);
                    stu[i].setAverage(stu[i].getCount());
                    try {
						ExcelUtils.updataStuExcel(stu[i], cho);
					} catch (Exception e) {
						e.printStackTrace();
					}
                    System.out.println("修改成功!");
                    System.out.println("还要继续修改吗?(是/Y，否/N)");
                    ch=In.next();
                    se=ch.charAt(0);
                    while (se!='N'&&se!='n'&&se!='Y'&&se!='y')
                    {
                        System.out.println("输入无效，请重新输入：");
                        ch=In.next();
                        se=ch.charAt(0);
                    }
                    if (se=='N'||se=='n')
                    {
                        System.out.println("返回主菜单");
                        Teacher();
                    }
                    if (se=='y'||se=='Y')
                    {
                        Renew();
                    }
                }
                else if (cho==0)
                {
                    Teacher();
                }
                else
                {
                    System.out.println("输入无效!");
                    Renew();
                }
            }
        }
    }
    //修改教师的密码
    private static void Recode()
    {
        System.out.println("请输入原来的密码:");
        Scanner In=new Scanner(System.in);
        String code =In.nextLine();
        if (!code.equals(tch.getPassword()))
        {
            System.out.println("密码输入错误！");
            Teacher();
        }
        //要求输入两次新密码
        System.out.println("请输入新密码:");                                       
        String co1=In.nextLine();
        System.out.println("请再次输入新密码:");
        String co2=In.nextLine();
        if (!co1.equals(co2))
        {
            System.out.println("两次输入密码不一致！");
            System.out.println("即将返回主界面");
            Teacher();
        }
        else
        {
//            ac.setPassword(co1);
          try {
  			ExcelUtils.updataTchExcel(code, co1, tch);
          }catch (Exception e) {
          	e.printStackTrace();
  		}
            System.out.println("密码修改成功!");
            System.out.println("输入任意值返回主菜单");
            Scanner in=new Scanner(System.in);
            in.next();
            Teacher();
        }
    }
    
    //控制台显示学生信息
    private static void display(int i)
    {
		 System.out.println("学号："+stu[i].getNumber()+"\t姓名："+stu[i].getName()+"\t年龄:"+stu[i].getAge());
	     System.out.println("该学生各科成绩为：");
	     for (int j=0;j<stu[i].getCount();j++)
	     {
	         System.out.println(stu[i].getProject(j)+":\t"+stu[i].getGrade(j));
	     }
	     System.out.println("该学生平均分为："+stu[i].getAverage());
    }
    //查询学生信息
    private static void findStu(){
    	try {
    		stu = ExcelUtils.readStuExcel();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}