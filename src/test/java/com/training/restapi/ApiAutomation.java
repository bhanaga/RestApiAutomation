package com.training.restapi;

import java.util.HashMap;
import java.util.List;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiAutomation {
	
	String sHostUrl = "https://us-central1-qa01-tekarch-accmanager.cloudfunctions.net";
	String sEPLogin = "/login";
	String sEPData = "/getdata";
    String sURI ="";	
    Response response;

  @Test
	public void login() {
	sURI = sHostUrl+sEPLogin;
	System.out.println(sURI);
	RestAssured.baseURI=sURI;
	String token;
	
	response=RestAssured.given().body("{\r\n"
			+ " \"username\": \"nagarajan.bharathi@gmail.com\",\r\n"
			+ " \"password\": \"nagarajan123\"\r\n"
			+ "}").when().contentType("application/json").post();
	
	int status= response.getStatusCode();
	
	System.out.println(response.asString());
	System.out.println(response.asPrettyString());
	token = response.jsonPath().get("token[0]");
	String userId = response.jsonPath().get("userid[0]");
	System.out.println(token);
	
	if(status==201) {
		System.out.println("Test case Passed");
	}else {
		System.out.println("Response failed");
	}
	
	 // token 
	
	 sURI=sHostUrl+sEPData;
	 RestAssured.baseURI=sURI;
	 HashMap<String, String> headerData = new HashMap();
	 headerData.put("Content-Type", "application/json");
	 headerData.put("token",token);
	 response=RestAssured.given().headers(headerData).get();
	 //response.prettyPrint();
      List<String> ids = response.jsonPath().getList("accountno");
      for(String id:ids) {
    	  if(id.equals("BN-1234535")) {
    		  System.out.println("Expected account is present"); 
    		  break;
    	  }
      }
	}
}
