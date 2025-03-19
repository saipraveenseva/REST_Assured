package Thirteen_Important;


// Now we are going to see those Ecommerce end to end operations using REST Assured
// We will brush up, serialize, deserialize and spec builders usecases here as well


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification; // required for given, when and then
import io.restassured.specification.ResponseSpecification;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class EcommerceAPITest {
    public static void main(String[] args) {

        // We are going to implement the Login request first.


        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
        // To avoid write these for every request we are using SpecBuilders and building it along with ContentType.
        // Lets start given, when and then

        // The .body() here requires the payload of username and password for login. We can copy paste the code in json format or we can write POJO classes.
        // We are going to write a LoginRequest.java POJO class.

        LoginRequest login = new LoginRequest(); // creating an object of LoginRequest.java class and passing it to the body instead of writing payload in json format. More readability
        login.setUserEmail("saipraveenseva2222@gmail.com");
        login.setUserPassword("Qwerty@11"); // Passing username and password for the ecommerce website.

        RequestSpecification reqLogin = given().spec(req).body(login);
        // given here is again assign to a RequestSpecification object to perform operations.

        LoginResponse loginResponse = reqLogin.when().post("/api/ecom/auth/login") // Writing when and passing the rest of the base URI
        // This when() is assigned to a LoginResponse object.
        .then().extract().response().as(LoginResponse.class); //
                // With the then statement we can capture the response, convert it into a string and use JsonPath to retrieve the response generated and assert them.
                // Another way is using ResponseBuilder. We are going to write a LoginResponse.java class for that and we pass the LoginResponse.class as a parameter.

        String token = loginResponse.getToken(); // Storing token for future use.
        String userID= loginResponse.getUserId(); // Stroring userID as well.

        System.out.println("Token: "+loginResponse.getToken());
        System.out.println("userID: "+loginResponse.getUserId());
        System.out.println("message: "+loginResponse.getMessage());

        // Printing the response

        //********************************************************************************

        // Now that the login is sucessful we are going to add a product into the website

        /*
        Things we need
            POST request
            Authorization header(tokenID got from login)
            productName:qwerty
            productAddedBy:{{userId}}
            productCategory:fashion
            productSubCategory:shirts
            productPrice:11500
            productDescription:Addias Originals
            productFor:women
            productImage
         */

        RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization",token)
                .build();

        RequestSpecification reqAddProduct = given().spec(addProductBaseReq) // For form parameters Is this the only way? Yes I guess.
                .param("productName", "Meteor 350")     // Adding all the product details carefully. names should match
                .param("productAddedBy", userID) // userID we stored earlier
                .param("productCategory","Sports")
                .param("productSubCategory","Riding")
                .param("productPrice","300000")
                .param("productDescription","Royal Enfield")
                .param("productFor","men")
                .multiPart("productImage",new File("C:\\Users\\saipr\\Pictures\\meteor.jpg"));

        String addProductResponse = reqAddProduct.when().post("/api/ecom/product/add-product")
                .then().assertThat().extract().response().asString();
                // We can write POJO class here as well as we did earlier but revisiting Jsonpath again

        JsonPath js = new JsonPath(addProductResponse);
        String productID = js.get("productID"); // Storing product ID
        String productAddedMessage = js.get("message");
        System.out.println(productAddedMessage);

        // Product is now added succesfully. We can check for statusCode assertions as well if we need it. reload the website to make sure the product is added.

        /*
            Now we are going to place an order of the product we added earlier

            Things we need
            URL: https://rahulshettyacademy.com/api/ecom/order/create-order
            orders array with country and productID(nested json)
            Authorization header
         */

        RequestSpecification createOrderReqBaseURI = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization",token).setContentType(ContentType.JSON).build(); // This ContentType.JSON is only required when the payload is json

        // creating POJO for nested json(orders) is a bit tricky but we have seen worse in the courses usecase.

        OrderDetails orderDetails = new OrderDetails(); // We create a OrderDetails class for setting the order details ling country and product ID
        orderDetails.setCountry("India");
        orderDetails.setProductOrderId(productID);  // productID we got from earlier.

        // As the payload is a nested json we pass it as a List.
        // We take the orderDetails object from the above, add it into a List and then pass the list to the setOrders

        List<OrderDetails> orderDetailsList = new ArrayList<OrderDetails>();
        orderDetailsList.add(orderDetails);

        Orders orders = new Orders();   // We create Orders.java for nested array
        orders.setOrders(orderDetailsList);

        RequestSpecification createOrderReq = given().spec(createOrderReqBaseURI).body(orders);
        String responseAddOrder = createOrderReq.when().post("/api/ecom/order/create-order")
                .then().extract().response().asString();

        System.out.println(responseAddOrder);

    }

}
