package testscripts;

import static org.testng.Assert.assertEquals;

import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Constants.Status_Code;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.request.createbooking.Bookingdates;
import pojo.request.createbooking.CreateBookingRequest;

public class CreateBookingTest {
	String token;
	int bookingId;
	CreateBookingRequest payload;

	@BeforeMethod	
	public void generateToten() {
		RestAssured.baseURI = "https://restful-booker.herokuapp.com";
		Response res = RestAssured.given().log().all().header("Content-Type", "application/json")
				.body("{\r\n" + "    \"username\" : \"admin\",\r\n" + "    \"password\" : \"password123\"\r\n" + "}")
				.when().post("/auth");
		Assert.assertEquals(res.statusCode(), 200);
		token = res.jsonPath().getString("token");
		System.out.println(token);

	}
	
	@Test
	public void createBookingTestWithPOJO() {
		RestAssured.baseURI ="https://restful-booker.herokuapp.com";
		Bookingdates bookingdates = new Bookingdates();
		bookingdates.setCheckin("2023-05-10");
		bookingdates.setCheckout("2023-05-13");
		
		payload = new CreateBookingRequest();
		payload.setFirstname("Madhavi");
		payload.setLastname("Khasbage");
		payload.setTotalprice(1234);
		payload.setDepositpaid(true);
		payload.setAdditionalneeds("breakfast");
		payload.setBookingdates(bookingdates);
		
		Response res = RestAssured.given()	
		.headers("Content-Type", "application/json")
		.headers("Accept", "application/json")
		.body(payload)
		.log().all()
		.when()
		.post("/booking");
		
		bookingId= res.jsonPath().getInt("bookingid");
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		//Assert.assertEquals(Integer.valueOf(res.jsonPath().getInt(("bookingid")) instanceof Integer);
		
		System.out.println(bookingId);
		
		Assert.assertTrue(bookingId>0);
		validateResponse(res,payload,"booking.");
		
	}
	
	private void validateResponse(Response res, CreateBookingRequest payload, String object) {
		Assert.assertEquals(res.jsonPath().getString(object+"firstname"), payload.getFirstname());
		Assert.assertEquals(res.jsonPath().getString(object+"lastname"), payload.getLastname());
 		Assert.assertEquals(res.jsonPath().getInt(object+"totalprice"), payload.getTotalprice());
		Assert.assertEquals(res.jsonPath().getBoolean(object+"depositpaid"), payload.isDepositpaid());
		Assert.assertEquals(res.jsonPath().getString(object+"bookingdates.checkin"), payload.bookingdates.getCheckin());
		Assert.assertEquals(res.jsonPath().getString(object+"bookingdates.checkout"), payload.bookingdates.getCheckout());
		Assert.assertEquals(res.jsonPath().getString(object+"additionalneeds"), payload.getAdditionalneeds());
	}
	
	@Test(priority=1, enabled=false)
	public void getAllBookingTest() {		
		int bookinid=4061;
		RestAssured.baseURI ="https://restful-booker.herokuapp.com";
		Response res = RestAssured.given()
				.headers("Accept", "application/json")				
				.log().all()
				.when()
				.get("/booking");
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		System.out.println(res.asPrettyString());
		List<String> listofBookingIds = res.jsonPath().getList("bookingid");
		System.out.println(listofBookingIds.size());
		Assert.assertTrue(listofBookingIds.contains(bookinid));
	}
	
	@Test(priority=2, enabled=false)
	public void getAllBookingIdTest() {			
		Response res = RestAssured.given()
				.headers("Accept", "application/json")	
				.when()
				.get("/booking/" + bookingId);
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		System.out.println(res.asPrettyString());		
		validateResponse(res,payload,"");
	}
	
	@Test(priority=2,enabled=false)
	public void getAllBookingIdDeserializedTest() {		
		int bookingid=2522;
		Response res = RestAssured.given()
				.headers("Accept", "application/json")	
				.when()
				.get("/booking/" + bookingId);
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		System.out.println(res.asPrettyString());		
		validateResponse(res,payload,"");
		
		CreateBookingRequest responseBody= res.as(CreateBookingRequest.class);
		System.out.println(responseBody);		
		Assert.assertTrue(responseBody.equals(payload));
	}
	
	@Test(priority=3, enabled=false)
	public void updateBookingTest() {			
		payload.setFirstname("Hrudaya");
		Response res = RestAssured.given()
				.headers("Content-Type", "application/json")
				.headers("Accept", "application/json")		
				.headers("Cookie", "token=" + token)
				.log().all()
				.body(payload)
				.when()
				.put("/booking/" + bookingId);
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		System.out.println(res.asPrettyString());	 		
		
		CreateBookingRequest responseBody= res.as(CreateBookingRequest.class);			
		Assert.assertTrue(responseBody.equals(payload));
	}
	
	@Test(priority=4, enabled=false)
	public void partialUpdateBookingTest() {			
		payload.setFirstname("Hrudaya11");
		Response res = RestAssured.given()
				.headers("Content-Type", "application/json")
				.headers("Accept", "application/json")		
				.headers("Cookie", "token=" + token)
				.log().all()
				.body(payload)
				.when()
				.patch("/booking/" + bookingId);
		Assert.assertEquals(res.getStatusCode(), Status_Code.OK);
		System.out.println(res.asPrettyString());	 		
		
		CreateBookingRequest responseBody= res.as(CreateBookingRequest.class);			
		Assert.assertTrue(responseBody.equals(payload));
	}
	
	@Test(priority=5)
	public void deleteBookingTest() {	
		
		Response res = RestAssured.given()
				.headers("Content-Type", "application/json")						
				.headers("Cookie", "token=" + token)
				.log().all()				
				.when()
				.delete("/booking/" + bookingId);
		Assert.assertEquals(res.getStatusCode(), Status_Code.CREATED);
				
		Response resGet = RestAssured.given()
				.headers("Accept", "application/json")
				.when()
				.get("/booking/"+bookingId);
		Assert.assertEquals(resGet.getStatusCode(), 404);
	}
	
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
	@Test(enabled=false)
	public void createBookingTestInPlanMode() {

		String payload = "{\r\n" + "    \"username\" : \"admin\",\r\n" + "    \"password\" : \"password123\"\r\n" + "}";
		RequestSpecification reqSpec = RestAssured.given();
		reqSpec.baseUri("https://restful-booker.herokuapp.com");
		reqSpec.headers("Content-Type", "application/json");
		reqSpec.body(payload);
		Response res = reqSpec.post("/auth");
		Assert.assertEquals(res.statusCode(), 200);
		System.out.println(res.asPrettyString());
	}


	}
