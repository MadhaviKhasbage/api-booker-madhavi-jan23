package Utilities;

import net.datafaker.Faker;

public class DataGenerator {
	private static Faker objFaker = new Faker();
	public static String getEmailId() {		
		return objFaker.name().firstName() + "." + objFaker.name().lastName() + "@gmail.com";
	}
	
	public static String getFullName() {		
		return objFaker.name().fullName();
	}

	public static String getPhoneNumber() {		
		return objFaker.number().digits(10);
	}

}
