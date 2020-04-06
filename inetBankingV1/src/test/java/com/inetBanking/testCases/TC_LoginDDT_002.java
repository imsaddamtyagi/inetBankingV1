package com.inetBanking.testCases;

import java.io.IOException;
import org.openqa.selenium.NoAlertPresentException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.inetBanking.pageObjects.LoginPage;
import com.inetBanking.utilities.XLUtils;


public class TC_LoginDDT_002 extends BaseClass
{
	
	
@Test(dataProvider="logindata")
public void LoginDDT(String user,String pwd)
{
	
	LoginPage lp = new LoginPage(driver);
	lp.setUserName(user);
	logger.info("user name is provided in test case2");
	lp.setPassword(pwd);
	logger.info(" password is provided in test case2");
	lp.clickSubmit();	
	
	if(isAlertPresent()==true)   // If Alert is present then it means that login not happened.
	{
		driver.switchTo().alert().accept();   // Then it will accept alert so that login page can come again.
		logger.info("Login was failed");
		driver.switchTo().defaultContent();   // to switch to main page.
		Assert.assertTrue(false);		
	}
	else
	{
		Assert.assertTrue(true);	
		logger.info("Login passed");
		lp.clickLogout();
		driver.switchTo().alert().accept();    // close logout alert.
		driver.switchTo().defaultContent();
	}
}

public boolean isAlertPresent()
{	
try {
	driver.switchTo().alert();
	return true;	              // If Alert is present then it means that login not happened.
}	
catch(NoAlertPresentException e)
{
	return false;	  // will return 'False' is alert is not present.
}
}

@DataProvider(name="logindata")
String [][]getData() throws IOException
{
	String path = System.getProperty("user.dir")+"/src/test/java/inetBanking/testData/LoginData.xlsx";
	int rownum = XLUtils.getRowCount(path, "Sheet1");
	int cocount = XLUtils.getCellCount(path,"Sheet1",1);
	
	String logindata[][]= new String [rownum][cocount];
	for(int i=1;i<=rownum;i++)   // rows
	{
		for(int j=0;j<cocount;j++)            // column
		{
			logindata[i-1][j]= XLUtils.getCellData(path, "Sheet1", i, j);
		}
	}
	
	return logindata;
}
	
}
