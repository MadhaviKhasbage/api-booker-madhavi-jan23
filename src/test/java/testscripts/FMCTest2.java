package testscripts;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import Constants.Status_Code;
import Utilities.DataGenerator;
import base.BaseService;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import junit.framework.Assert;
import net.datafaker.Faker;

import static io.restassured.RestAssured.given;

import java.util.Map;

public class FMCTest2 {	
	String token;
	Response res;
	BaseService baseService = new BaseService();
	String emailId= DataGenerator.getEmailId();
	String fullName= DataGenerator.getFullName();
	String phoneNumber= DataGenerator.getPhoneNumber();
	String password="God@123";
	String otp;
	
	@Test
	public void createToken() {
		Map<String,String> headerMap=baseService.getHeaderWithoutAuth();		
		res = baseService.executeGetAPI("/fmc/token", headerMap);
		token = res.jsonPath().get("accessToken");
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		System.out.println(token);
	}
	
	@Test (priority=1)
	public void emailSignUp() {
		JSONObject emailSignUpPayload = new JSONObject();
		emailSignUpPayload.put("email_id", emailId);
		Map<String,String> headerMap=baseService.getHeaderHavingAuth(token);
		res = baseService.executePostAPI("/fmc/email-signup-automation", headerMap, emailSignUpPayload);	
		Assert.assertEquals(res.getStatusCode(), Status_Code.CREATED);
		otp= res.jsonPath().getString("content.otp");
	}
	
	@SuppressWarnings("unchecked")
	@Test (priority=2)
	public void verifyOtp() {		
		JSONObject veriftOTPPayload= new JSONObject();
		veriftOTPPayload.put("email_id",emailId);
		veriftOTPPayload.put("full_name",fullName);
		veriftOTPPayload.put("phone_number",phoneNumber);
		veriftOTPPayload.put("password",password);
		veriftOTPPayload.put("otp",otp);
		Map<String,String> headerMap=baseService.getHeaderHavingAuth(token);
		res = baseService.executePutAPI("/fmc/verify-otp/", headerMap, veriftOTPPayload);	
		int userId=res.jsonPath().getInt("content.userid");
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
	}
	
	

}
