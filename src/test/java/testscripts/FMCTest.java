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

public class FMCTest {
	String emailid;
	String token;
	Response res;
	BaseService baseService = new BaseService();

	
	
	private void createToken() {
		Map<String,String> headerMap=baseService.getHeaderWithoutAuth();		
		res = baseService.executeGetAPI("/fmc/token", headerMap);
		token = res.jsonPath().get("accessToken");
		System.out.println(token);
	}
	
	private String emailSignUp(String emailId) {
		JSONObject emailSignUpPayload = new JSONObject();
		emailSignUpPayload.put("email_id", emailId);
		Map<String,String> headerMap=baseService.getHeaderHavingAuth(token);
		res = baseService.executePostAPI("/fmc/email-signup-automation", headerMap, emailSignUpPayload);	
		Assert.assertEquals(res.getStatusCode(), Status_Code.CREATED);
		return res.jsonPath().getString("content.otp");
	}
	
	private void verifyOtp(String emailid,String fullName,String phoneNumber,String password,String otp) {		
		JSONObject veriftOTPPayload= new JSONObject();
		veriftOTPPayload.put("email_id",emailid);
		veriftOTPPayload.put("full_name",fullName);
		veriftOTPPayload.put("phone_number",phoneNumber);
		veriftOTPPayload.put("password",password);
		veriftOTPPayload.put("otp",otp);
		Map<String,String> headerMap=baseService.getHeaderHavingAuth(token);
		res = baseService.executePutAPI("/fmc/verify-otp", headerMap, veriftOTPPayload);		
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
	}

	private void createToken1() {
		RestAssured.baseURI = "http://Fmc-env.eba-5akrwvvr.us-east-1.elasticbeanstalk.com";
		res = given().headers("Accept", "application/json").when().get("/fmc/token");
		token = res.jsonPath().get("accessToken");
		System.out.println(token);
	}
	private String emailSignUp1(String emailId) {
		JSONObject emailSignUpPayload = new JSONObject();
		emailSignUpPayload.put("email_id", emailId);
		res = given().headers("Content-Type", "application/json").headers("Authorization", "Bearer " + token).log().all()
				.body(emailSignUpPayload)
				.when().post("/fmc/email-signup-automation");

		Assert.assertEquals(res.getStatusCode(), Status_Code.CREATED);
		return res.jsonPath().getString("content.otp");
	}

	private void verifyOtp1(String emailid,String fullName,String phoneNumber,String password,String otp) {
		
		JSONObject veriftOTPPayload= new JSONObject();
		veriftOTPPayload.put("email_id",emailid);
		veriftOTPPayload.put("full_name",fullName);
		veriftOTPPayload.put("phone_number",phoneNumber);
		veriftOTPPayload.put("password",password);
		veriftOTPPayload.put("otp",otp);
		
		res = given().headers("Content-Type", "application/json").headers("Authorization", "Bearer " + token)
				.log().all()
				.body("veriftOTPPayload")
				.when().put("/fmc/verify-otp");
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
	}

	@Test
	public void signupTest() {
		String emialId= DataGenerator.getEmailId();
		String fullName= DataGenerator.getFullName();
		String phoneNumber= DataGenerator.getPhoneNumber();
		String password="God@123";
		
		createToken();
		String otp=emailSignUp(emialId);
	/*	verifyOtp(emialId,fullName,phoneNumber,password,otp);
		System.out.println(otp);*/
	}

}
