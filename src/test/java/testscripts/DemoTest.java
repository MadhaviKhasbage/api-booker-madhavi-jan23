package testscripts;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class DemoTest {
	
	@Test
	public void phoneNumbersTypeTest() {
		RestAssured.baseURI = "https://0e686aed-6e36-4047-bcb4-a2417455c2d7.mock.pstmn.io";
		
		Response res = RestAssured.given()
			.headers("Accept", "application/json")
			.when()
			.get("/test");
		
		System.out.println(res.asPrettyString());
		List<String> listOfType = res.jsonPath().getList("phoneNumbers.type");
		System.out.println(listOfType);
	}
	@Test(priority=1)
	public void phoneNumbersTest() {
		RestAssured.baseURI = "https://0e686aed-6e36-4047-bcb4-a2417455c2d7.mock.pstmn.io";
		
		Response res = RestAssured.given()
			.headers("Accept", "application/json")
			.when()
			.get("/test");
		
		System.out.println(res.asPrettyString());
		List<Object> listOfPhoneNumber = res.jsonPath().getList("phoneNumbers");
		System.out.println(listOfPhoneNumber.size());
		System.out.println(listOfPhoneNumber);	
		for(Object obj:listOfPhoneNumber) {
			Map<String, String> mapOfPhonenumber=(Map<String, String>)obj;
			System.out.println(mapOfPhonenumber.get("type") + "--" + mapOfPhonenumber.get("number"));
			if(mapOfPhonenumber.get("type").equals("iphone")) {
				Assert.assertTrue(mapOfPhonenumber.get("number").startsWith("3456"));
			}else if(mapOfPhonenumber.get("type").startsWith("home")) {
				Assert.assertTrue(mapOfPhonenumber.get("number").startsWith("0123"));
			}
		}
		
	}
	
	@Test(priority=2)
	public void phoneallNumbersTest() {
		RestAssured.baseURI = "https://0e686aed-6e36-4047-bcb4-a2417455c2d7.mock.pstmn.io";
		
		Response res = RestAssured.given()
			.headers("Accept", "application/json")
			.when()
			.get("/test");
		
		System.out.println(res.asPrettyString());
		List<Object> listOfPhoneNumber = res.jsonPath().getList("phoneNumbers");
		Map<String,String> mapOfPhoneNos = (Map<String,String>)listOfPhoneNumber.get(0);
		System.out.println(mapOfPhoneNos);
		
	}


}
