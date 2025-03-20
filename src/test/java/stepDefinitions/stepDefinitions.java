package stepDefinitions;

import Eleven.AddPlaceSerialization;
import Eleven.Location;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.TestData;
import resources.Utils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class stepDefinitions extends Utils {        // ANy other utilities like Base URI are declated in Utils.java so we can reuse it instead of writing it again and again.
    // Instead of creating object for Utils.java we just extend it. Just demonstrating inheritance.
    RequestSpecification res;

    ResponseSpecification responseSpec;
    Response response;


    //*************** WE CREATE the RequestSpecification and Response object above instead of writing in .given()************

    TestData data = new TestData(); // payload in written in TestData.java we are creating an object for it.

    // This is the code we get into the output console if we run TestRunner.java without implementing stepDfinitions.jva
    // I copied the entire code and pasted it.
    @Given("Add Place Payload")
    public void add_place_payload() throws FileNotFoundException {  // The logging involves creating a file and writing the logs in it. Addressed in Utils.java. So we throw a FileNotFoundExpection incase of a missing file

        //*************************************** Copied given condition from SpecBuilders.java***********************
        // I deleted the comments that further explain the code SpecBuilder.java. Refer this file for further understanding

        // We set the baseURI here and also add queryParameters. No need to mention these everytime we write given, when and then.

        //*************** WE CREATE the RequestSpecification and Response object above instead of writing in .given()************


        // ResponseSpecBuilder can help avoid rewriting the assertion elements.
        // Wherever we need to assert something, no need to write assertThat again and again for every given, when and then.
        // We are asserting the statusCode and ContentType here.

        res=given().spec(requestSpecificationUtil())  // requestSpecificationUtil() exists in Utils.java where we declared Base URI.
                // Instead of given.addQueryParam() we just write .spec(requestSpec) and pass on requestSpec object
                .body(data.addPlacePayload());         // We have split the given with the response by writing Response separately

                // Instead of writing the payload in .given(), we write in TestData.java file inside a addPlacePayload() method.
                // We create an object for this class as well.

    }
    @When("user calls {string} with Post http request" )
    public void user_calls_with_post_http_request_double_quotes_indicate_i_will_replace_add_place_api_with_other_api_without_changing_the_syntax_of_the_steps(String string) {

        responseSpec=new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        //*************** Copied when condition from SpecBuilders.java *******************
        response=res.when().post("/maps/api/place/add/json")

                .then().spec(responseSpec).extract().response();    // Instead of .then().assertThat() we write spec(responseSpec) and pass responseSpec object.

    }
    @Then("the API call is success with status code {int}")
    public void the_api_call_is_success_with_status_code(Integer int1) {
        assertEquals(response.statusCode(),200);
        // This is how we write assertions in JUnit


    }
    @Then("check {string} in response body is {string}")
    public void in_response_body_is(String keyValue, String ExpectedValue) {
            // In .then() of placeValidations.feature file we have "status" and "OK" as value. These will sit in "keyValue" and "ExpectedValue".
        String resp = response.asString();
        JsonPath js = new JsonPath(resp);
        assertEquals(js.get(keyValue),ExpectedValue);
        //js.get(keyValue) gives the keyValue of the key "status" as "OK"
        // We are comparing it with ExpectedValue "OK"

    }

    @Then("validate {string} in response body is {string}")
    public void in_response_body_is_here(String keyValue1, String ExpectedValue1) {
        // In .then() of placeValidations.feature file we have "status" and "OK" as value. These will sit in "keyValue" and "ExpectedValue".
        String resp = response.asString();
        JsonPath js = new JsonPath(resp);
        assertEquals(js.get(keyValue1),ExpectedValue1);
        //js.get(keyValue) gives the keyValue of the key "status" as "OK"
        // We are comparing it with ExpectedValue "OK"

    }


}
