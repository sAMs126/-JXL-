package com.gcl;

public class Teacher {
    private String password;		//密码
    private String account;		//账号

    public Teacher() {}
    public Teacher(String admin, String s) {		//设置教师账号密码
        this.account=admin;
        this.password=s;
    }
    public void setAccont(String account)
    {
        this.account=account;
    }
    public String getAccont()
    {
        return account;
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}