
# JXL-StuSystem

_利用 JXL 根据 Excel 对学生信息进行管理_
***

## [项目简介]：
根据 JXL 实现对Excel的操作，其实就是对单元格的操作来模拟CRUD操作

## [主要文件说明]：

|文件 | 说明               |
| --------------- | ------------------------------------ |
| ExcelUtils.java | 用于模拟CRUD操作               |
| Student.java    | 学生基本信息情况             |
| Teacher.java    | 教师基本信息情况             |
| MAIN.java       | 控制台提示相关信息，将信息和操作连接 |

## [未解决的问题]：
* 创建学生环节：
  1. 单次创建多个学生信息时能够进行学号判断，并由第一个学生的科目为依据进行成绩添加，
     但当提交并存入Excel后，再次选择添加名没有做表中现有学号和输入学号的对比。
* 删除学生环节：
  1. 在删除学生信息是，能够删除成功但是回报 “ Warning:  Cannot find sheet 学生成绩表  in supbook record ”  错误，
     望各位指点
* 其他未发现的错误，望各位海涵并指正
     
### [参考信息]：
		
* [jxl基本操作][1]
* [jxl相关Api的使用][2]
* [实现对报表的更新][3]
* [批量操作、单元格格式][4]
* [更新添加新的工作表][5]
* [判断单元格是否为空][6]
* [数字转字符串单元格格式][7]
* [获取数字单元格内容][8]
* [删除一整行][9]

[1]:https://www.cnblogs.com/biehongli/p/6497653.html
[2]:https://blog.csdn.net/lcz_ptr/article/details/7687658
[3]:http://www.voidcn.com/article/p-gigcldpu-eb.html
[4]:http://blog.51cto.com/lavasoft/174244
[5]:http://www.cnblogs.com/hongten/archive/2011/05/19/2050946.html
[6]:https://bbs.csdn.net/topics/390435240
[7]:https://bbs.csdn.net/topics/120034691
[8]:https://blog.csdn.net/xiaoxun2802/article/details/71191061
[9]:https://bbs.csdn.net/topics/310221649
