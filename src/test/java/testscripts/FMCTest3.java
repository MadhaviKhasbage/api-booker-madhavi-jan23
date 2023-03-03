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
import services.GenerateTokenServices;

import static io.restassured.RestAssured.given;

import java.util.Map;

public class FMCTest3 {
	String token;
	Response res;
	BaseService baseService = new BaseService();
	String emailId = DataGenerator.getEmailId();
	String fullName = DataGenerator.getFullName();
	String phoneNumber = DataGenerator.getPhoneNumber();
	String password = "God@123";
	String otp;
	GenerateTokenServices generateTokenServices = new GenerateTokenServices();

	@Test
	public void createToken() {
		Response res = generateTokenServices.getTokenResponse();
		System.out.println(res.asPrettyString());
		token = res.jsonPath().get("accessToken");
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		Assert.assertTrue(token.length() > 0);
		Assert.assertEquals(res.jsonPath().get("tokenType"), token);
		System.out.println(token);
	}

	@Test(priority = 1)
	public void emailSignUp() {
		JSONObject emailSignUpPayload = new JSONObject();
		emailSignUpPayload.put("email_id", emailId);
		res = generateTokenServices.getEmailSignUpResponse(emailSignUpPayload);
		otp = generateTokenServices.getOptFromEmailSignUpResponse(emailSignUpPayload);
		Assert.assertEquals(res.getStatusCode(), Status_Code.CREATED);
	}

	@SuppressWarnings("unchecked")
	@Test(priority = 2)
	public void verifyOtp() {		
		int userId = generateTokenServices.getUserId(emailId, password);
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		System.out.println(res.asPrettyString());
	}

}
