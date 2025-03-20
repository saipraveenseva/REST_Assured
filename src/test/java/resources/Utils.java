package resources;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Utils {
    RequestSpecification requestSpec;

    public RequestSpecification requestSpecificationUtil(){
        RestAssured.baseURI="https://rahulshettyacademy.com";     // Commented this line because its a common line that we will use often and we write it in a RequestSpect Builder.


        requestSpec=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key","qaclick123").setContentType(ContentType.JSON).build();
        return requestSpec;
    }
}
