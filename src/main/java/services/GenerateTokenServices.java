package services;

import java.util.Map;

import org.json.simple.JSONObject;

import Constants.APIENDPoints;
import Utilities.DataGenerator;
import base.BaseService;
import io.restassured.response.Response;

public class GenerateTokenServices extends BaseService {

	String emailId;

	public Response getTokenResponse() {
		Map<String, String> headerMap = getHeaderWithoutAuth();
		Response res = executeGetAPI(APIENDPoints.TOKEN, headerMap);
		return res;
	}

	public String getToken() {
		Response res = getTokenResponse();
		return res.jsonPath().get("accessToken");
	}

	public Response getEmailSignUpResponse(JSONObject emailSignUpPayload) {
		Map<String, String> headerMap = getHeaderHavingAuth(getToken());
		return executePostAPI(APIENDPoints.EMAIL_SIGNUP, headerMap, emailSignUpPayload);
	}

	public String getOptFromEmailSignUpResponse(JSONObject getEmailSignUpResponse) {
		Response res = getEmailSignUpResponse(getEmailSignUpResponse);
		String otp = res.jsonPath().getString("content.otp");
		return otp;
	}

	public Response getEmailSignUpResponse() {
		JSONObject emailSignUpPayload = new JSONObject();
		emailSignUpPayload.put("email_id", emailId);

		Map<String, String> headerMap = getHeaderHavingAuth(getToken());
		return executePutAPI(APIENDPoints.EMAIL_SIGNUP, headerMap, emailSignUpPayload);

	}

	public Response getVerifyOtpResponse(JSONObject veriftOTPPayload) {
		Map<String, String> headerMap = getHeaderHavingAuth(getToken());
		return executePutAPI(APIENDPoints.VERIFY_OTP, headerMap, veriftOTPPayload);
	}

	public int getUserIdFromVerifyOtpResponse(JSONObject veriftOTPPayload) {
		Map<String, String> headerMap = getHeaderHavingAuth(getToken());
		Response res = executePutAPI(APIENDPoints.VERIFY_OTP, headerMap, veriftOTPPayload);
		return res.jsonPath().getInt("content.userid");
	}

	@SuppressWarnings("unchecked")
	public int getUserId(String emailId, String password) {
		JSONObject emailSignUpPayload = new JSONObject();
		emailSignUpPayload.put("email_id", emailId);
		String otp = getOptFromEmailSignUpResponse(emailSignUpPayload);

		String fullName = DataGenerator.getFullName();
		String phoneNumebr = DataGenerator.getPhoneNumber();

		JSONObject veriftOTPPayload = new JSONObject();
		veriftOTPPayload.put("email_id", emailId);
		veriftOTPPayload.put("full_name", fullName);
		veriftOTPPayload.put("phone_number", phoneNumebr);
		veriftOTPPayload.put("password", password);
		veriftOTPPayload.put("otp", otp);

		return getUserIdFromVerifyOtpResponse(veriftOTPPayload);
	}

}
