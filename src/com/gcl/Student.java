package com.gcl;

public class Student
{
    private int number;                                 //学号
    private String name;                                //姓名
    private int age;                                    //年龄
    private double grade[]=new double[100];             //各科分数
    private String project[]=new String[100];           //各科名称
    private double  average;                            //平均分
    private int count;                                  //科目数量
    public void setNumber(int num)
    {
        this.number=num;
    }
    public int getNumber()
    {
        return number;
    }
    public void setName(String name)
    {
        this.name=name;
    }
    public String getName()
    {
        return name;
    }
    public void setAge(int age)
    {
        this.age=age;
    }
    public int getAge()
    {
        return age;
    }
    public void setGrade(double a[])
    {
        System.arraycopy(a, 0, grade, 0, a.length);
    }
    public double getGrade(int i)
    {
        return grade[i];
    }
    public void setProject(String a[])
    {
        System.arraycopy(a,0,project,0,a.length);
        this.count=a.length;
    }
    public String getProject(int i)
    {
        return project[i];
    }
    public void setAverage(int len)
    {
        double sum=0;
        int i=0;
        for (;i<len;i++)
        {
            sum+=grade[i];
        }
        this.average=sum/(i*1.0);
    }
    public double getAverage()
    {
        return average;
    }
    public int getCount()
    {
        return count;
    }
}