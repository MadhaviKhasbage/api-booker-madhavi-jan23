package testscripts;

import org.testng.annotations.BeforeMethod;
import io.restassured.response.Response;
import services.LoginServices;

public class ReportAPITest {
	LoginServices loginServices= new LoginServices();	
	String emailid ="madhavi2@gmail.com";
	String password = "God2123";
	int userid;
	
	@BeforeMethod
	public void login() {
		Response res = loginServices.login(emailid,password);
		userid= res.jsonPath().getInt("content.id");
	}
	
	public void verifyAddReportTest() {
		
	}
}
