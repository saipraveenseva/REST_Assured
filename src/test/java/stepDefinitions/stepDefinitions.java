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

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class stepDefinitions extends Utils {

    RequestSpecification res;

    ResponseSpecification responseSpec;
    Response response;

    TestData data = new TestData();
    @Given("Add Place Payload")
    public void add_place_payload() {
        // Write code here that turns the phrase above into concrete actions
        //*************************************** Copied given condition from SpecBuilders.java***********************


        // Syntax for RequestSpec builder goes like above. RequestSpecification is class responsible for that.
        // RequestSpecBuilder() is a method which can build the specBuilder.
        // We set the baseURI here and also add queryParameters. No need to mention these everytime we write given, when and then.

        responseSpec=new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        // ResponseSpecBuilder can help avoid rewriting the assertion elements.
        // Wherever we need to assert something, no need to write assertThat again and again for every given, when and then.
        // We are asserting the statusCode and ContentType here.

        res=given().spec(requestSpecificationUtil())  // Instead of given.addQueryParam() we just write .spec(requestSpec) and pass on requestSpec object
                .body(data.addPlacePayload());         // We have split the given with the response by writing Response separately

    }
    @When("user calls {string} with Post http request  \\/\\/ Double quotes indicate I will replace AddPlaceAPI with other API without changing the syntax of the steps")
    public void user_calls_with_post_http_request_double_quotes_indicate_i_will_replace_add_place_api_with_other_api_without_changing_the_syntax_of_the_steps(String string) {
        // Write code here that turns the phrase above into concrete actions

        //*************** Copied when condition from SpecBuilders.java *******************
        response=res.when().post("/maps/api/place/add/json")   // Declare a variable response of type Response as we normally do.

                .then().spec(responseSpec).extract().response();    // Instead of .then().assertThat() we write spec(responseSpec) and pass responseSpec object.


        //throw new io.cucumber.java.PendingException();
    }
    @Then("the API call is success with status code {int}")
    public void the_api_call_is_success_with_status_code(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(response.statusCode(),200);


    }
    @Then("{string} in response body is {string}   \\/\\/ Same here")
    public void in_response_body_is_same_here(String key, String value) {
        // Write code here that turns the phrase above into concrete actions

        String resp = response.asString();
        JsonPath js = new JsonPath(resp);
        assertEquals(js.get(key),value);

    }
    @Then("{string} in response body is {string}")
    public void in_response_body_is(String key1, String value1) {
        // Write code here that turns the phrase above into concrete actions
        String resp2 = response.asString();
        JsonPath js1 = new JsonPath(resp2);

        assertEquals(js1.get(key1),value1);
    }

}
