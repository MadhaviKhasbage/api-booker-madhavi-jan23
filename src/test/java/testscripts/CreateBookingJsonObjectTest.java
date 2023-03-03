package testscripts;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import Constants.Status_Code;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.datafaker.Faker;
import pojo.request.createbooking.Bookingdates;
import pojo.request.createbooking.CreateBookingRequest;

public class CreateBookingJsonObjectTest {
	@Test(enabled=false)
	public void createBookingTest() {
		Response res = RestAssured.given().header("Content-Type", "application/json")
				.header("Accept", "application/json")
				.body("{\r\n" + "    \"firstname\" : \"Madhavi\",\r\n" + "    \"lastname\" : \"Khasbage\",\r\n"
						+ "    \"totalprice\" : 111,\r\n" + "    \"depositpaid\" : true,\r\n"
						+ "    \"bookingdates\" : {\r\n" + "        \"checkin\" : \"2023-05-02\",\r\n"
						+ "        \"checkout\" : \"2023-05-05\"\r\n" + "    },\r\n"
						+ "    \"additionalneeds\" : \"Breakfast\"\r\n" + "}")
				.when().post("/booking");
		System.out.println(res.getStatusCode());
		System.out.println(res.getStatusLine());
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);

	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void createBookingTestWithJSONPOJO() {	
		Faker faker= new Faker();
		RestAssured.baseURI ="https://restful-booker.herokuapp.com";
		
		JSONObject jsonbookingDate = new JSONObject();
		jsonbookingDate.put("checkin", "2023-02-02");
		jsonbookingDate.put("checkout", "2023-02-02");		
		
		JSONObject jsonObject = new JSONObject(); 	
		jsonObject.put("firstname", faker.name().firstName());
		jsonObject.put("lastname", faker.name().lastName());
		jsonObject.put("totalprice",Integer.parseInt(faker.number().digits(3)));
		jsonObject.put("depositpaid", faker.bool().bool());
		jsonObject.put("bookingdates", jsonbookingDate);
		jsonObject.put("additionalneeds", "Breakfast");		
		
		Response res = RestAssured.given()	
		.headers("Content-Type", "application/json")
		.headers("Accept", "application/json")
		.body(jsonObject)
		.log().all()
		.when()
		.post("/booking");
		
		int bookingId= res.jsonPath().getInt("bookingid");
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		//Assert.assertEquals(Integer.valueOf(res.jsonPath().getInt(("bookingid")) instanceof Integer);
		
		System.out.println(bookingId);		
		Assert.assertTrue(bookingId>0);
		//validateResponse(res,payload,"booking.");		
	}	
}
