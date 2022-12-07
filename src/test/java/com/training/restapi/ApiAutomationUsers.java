package com.training.restapi;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

/*  Number  
 *  equalTo 
 *  greaterThan
 *  lessThan
 *  greateThanorEqualTo
 *  lessThanorEqualTo
 * 
 *  String
 *  equalTo
 *  StartsWith
 *  equalToIgnoreCase
 *  EndsWith
 *  ContainsString
 *  equalToIgnoreWhitespace
 *  
 *  collections:
 *  hasItems
 *  hasItem
 *  hasKey
 *  hadEntry
 *  empty
 * 
 */


public class ApiAutomationUsers {

	String sHostUrl = "https://jsonplaceholder.typicode.com";
	String sEPUsers = "/users";
	String sURI = "";

	@Test
	public void validateuser() throws IOException {

		String filePath = System.getProperty("user.dir")+File.separator+"TestData"+File.separator+"ExpectedData.json";
		
		String fileReadytoRead = readFileReturnString(filePath);
		
		String Expname = JsonPath.read(fileReadytoRead, "$.[0].address.city");
		System.out.println("Expected Value" +Expname);
		sURI = sHostUrl + sEPUsers;
		RestAssured.baseURI = sURI;
		Response response = RestAssured.given().get();
		//response.prettyPrint();
		String ActualValue = response.jsonPath().get("name[0]");
		System.out.println("ActualValue"+ActualValue);
	   System.out.println(response.jsonPath().get("address.city"));
		
		System.out.println(response.statusCode());
	
		//response.then().body("name[0]",equalTo(Expname));
		response.then().body("email[0]",startsWith("S"));
		response.then().body("address.city",hasItem(Expname));
		
	}

	public static String readFileReturnString(String filePath) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(filePath));
		return new String(encoded, StandardCharsets.UTF_8);
	}

}
