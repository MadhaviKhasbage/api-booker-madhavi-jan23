package testscripts;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import Constants.Status_Code;
import Utilities.DataGenerator;
import io.restassured.response.Response;
import junit.framework.Assert;
import services.LoginServices;

public class FMCLoginAPITest {
	
	LoginServices loginServices= new LoginServices();	
	String emailid =DataGenerator.getEmailId();
	String password = "God2123";
	@Test(dataProvider = "loginDataDetails")
	public void loginTest(String result) {	
		Response res= loginServices.login(emailid,password);		
		if(result=="success") {
			Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		}else {
			Assert.assertEquals(res.getStatusCode(), Status_Code.BADREQUEST);			
		}	
		System.out.println(res.asPrettyString());		
	}
	
	@Test(dataProvider = "loginDataDetails")
	public void loginTest(String emailid,String password,String result ) {	
		Response res= loginServices.login(emailid,password);		
		if(result=="success") {
			Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		}else {
			Assert.assertEquals(res.getStatusCode(), Status_Code.BADREQUEST);			
		}	
		System.out.println(res.asPrettyString());		
	}
	
	public String[][] getLoginData() {
		String[][] data = new String[2][3];
		
		data[0][0] = "madhavi.khasbage@gmail.com";
		data[0][1] = "God@123";
		data[0][2] = "Success";
		data[1][0] = "madhavi.khasbage@gmail.com";
		data[1][1] = "";
		data[1][2] = "Fail";
		
		return data;
		
	}

	
	
}
