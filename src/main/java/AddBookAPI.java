import files.payload;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class AddBookAPI {
    @Test(dataProvider = "booksData")

    public void addBook(String isbn, String aisle) {
        RestAssured.baseURI = "https://rahulshettyacademy.com/";

        String addBookResponse = given().log().all()
                                        .header("Content-Type", "application/json")
                                        .body(payload.Addbook(isbn,aisle))


                .when().post("Library/Addbook.php")
                .then().assertThat().statusCode(200)
                                    .body("Msg", equalTo("successfully added")).extract().response().asString();

        JsonPath js = new JsonPath(addBookResponse);
        String bookID = js.getString("ID");

        System.out.println("Book ID: "+bookID);


        String deleteBookResponse=given().log().all()
                                        .header("Content-Type", "application/json")
                                        .body("{\n" +
                        "    \"ID\": \"" + bookID + "\"\n" +
                        "}")


        .when().delete("Library/DeleteBook.php")
                .then().assertThat().statusCode(200)
                                    .body("msg", equalTo("book is successfully deleted")).extract().response().asString();



    }

    @DataProvider(name="booksData")
    public Object[][] getData(){
        //array is a collection of elements
        //multidimensional array is a collection of arrays.
        return new Object[][] {{"bnm","789"},{"okl","456"},{"bji","123"}};
    }
}
