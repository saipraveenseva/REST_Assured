package resources;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class Utils {
    RequestSpecification requestSpec;

    public RequestSpecification requestSpecificationUtil() throws FileNotFoundException {   // This file concept need to catch a FileNotFoundException so we write this.

        PrintStream log = new PrintStream(new FileOutputStream("logging.txt")); // We are creating an object of PrintStream class which allows us to send the contents of the object "log" into a file "logging.txt"

        //logging.txt is located at REST_Assured/target/logging.txt and you can find all the logs in there.

        RestAssured.baseURI="https://rahulshettyacademy.com";       // We declared BaseURI here.

        // Unlike doing .log().all() in the .given() we now globalise the logging and avoid rewriting

        requestSpec=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key","qaclick123")
                .addFilter(RequestLoggingFilter.logRequestTo(log))      // .addFilter helps with the logging. RequestLoggingFilter is a class that contains .logRequestTo. It has the ability to log everything and paste it in a file.
                .addFilter(ResponseLoggingFilter.logResponseTo(log))    // Doing the same with response and sending it to the file logging.txt
                .setContentType(ContentType.JSON).build();
        // passing the required params using the RequestSpecBuilder.
        return requestSpec;
    }
}
