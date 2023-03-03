package services;

import java.util.Map;

import org.json.simple.JSONObject;

import Constants.APIENDPoints;
import base.BaseService;
import io.restassured.response.Response;

public class LoginServices extends BaseService {

	GenerateTokenServices generateTokenServices = new GenerateTokenServices();	
	
	@SuppressWarnings("unchecked")
	public Response login(String emailID,String password) {
		JSONObject loginPayload = new JSONObject();
		loginPayload.put("email_id", emailID);
		loginPayload.put("password", password);				
		return login(loginPayload);
	}
	public Response login(JSONObject loginPayload) {
		
		String password=loginPayload.get("password").toString();
		if(password=="" || password == null) {password="temp";}
		generateTokenServices.getUserId( loginPayload.get("email_id").toString(),  password);
		Map<String, String> headerMap = getHeaderHavingAuth(generateTokenServices.getToken());
		return executePostAPI(APIENDPoints.LOGIN, headerMap, loginPayload);
	}

}
