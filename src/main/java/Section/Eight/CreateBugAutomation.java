package Section.Eight;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;


import java.io.File;
import java.sql.SQLOutput;

import static io.restassured.RestAssured.*;

public class CreateBugAutomation {

    public static void main(String[] args) {
        RestAssured.baseURI="https://saipraveenseva.atlassian.net/";

        String createBugResponse =
                given()
                    .header("Content-Type","application/json")
                    .header("Authorization","Basic c2FpcHJhdmVlbnNldmFAZ21haWwuY29tOkFUQVRUM3hGZkdGMEk2dU9COFdqWGdhaFVYX1VLUV9XMFlIVjQtTVFubm1NWm5LU3ZYRHZhTnVZQkt5dFJLcGs1eDRDTmtWSjRWdTVjd2J2Q2VDVURtbXNpRUV6QzFVaDJwOXpwX2N5alhMS1hxQ09vZEZiX3NONFVFcFdES2JPa0VZOXkwRVZCVzZJMGw1MExhZERKN3JEV09fZ04tUFlMVDgtaXpGYWZ6VGNEcVk5R0xfclo2WT01MTNDODY1Qw==")
                    .body("{\n" +
                        "  \"fields\": {\n" +
                        "    \"project\": {\n" +
                        "      \"key\": \"SCRUM\"\n" +
                        "    },\n" +
                        "    \"summary\": \"Bug 2 created via automation\",\n" +
                        "    \"description\": {\n" +
                        "      \"version\": 1,\n" +
                        "      \"type\": \"doc\",\n" +
                        "      \"content\": [\n" +
                        "        {\n" +
                        "          \"type\": \"paragraph\",\n" +
                        "          \"content\": [\n" +
                        "            {\n" +
                        "              \"type\": \"text\",\n" +
                        "              \"text\": \"Your bug description here. This can be multiple lines.\"\n" +
                        "            }\n" +
                        "          ]\n" +
                        "        }\n" +
                        "      ]\n" +
                        "    },\n" +
                        "    \"issuetype\": {\n" +
                        "      \"name\": \"Bug\"\n" +
                        "    }\n" +
                        "  }\n" +
                        "}")
                .log().all()
                .when().post("rest/api/3/issue")
                .then().log().all()
                .assertThat()
                .statusCode(201)
                .extract().response().asString();

        JsonPath js = new JsonPath(createBugResponse);
        String issueID=js.getString("id");

        System.out.println("Issue Id: "+issueID);


        // Now adding attachment to the filed bug using REST API calls.

        String addAttachmentResponse =
                given()
                        .pathParam("key",issueID)
                        .header("Authorization","Basic c2FpcHJhdmVlbnNldmFAZ21haWwuY29tOkFUQVRUM3hGZkdGMEk2dU9COFdqWGdhaFVYX1VLUV9XMFlIVjQtTVFubm1NWm5LU3ZYRHZhTnVZQkt5dFJLcGs1eDRDTmtWSjRWdTVjd2J2Q2VDVURtbXNpRUV6QzFVaDJwOXpwX2N5alhMS1hxQ09vZEZiX3NONFVFcFdES2JPa0VZOXkwRVZCVzZJMGw1MExhZERKN3JEV09fZ04tUFlMVDgtaXpGYWZ6VGNEcVk5R0xfclo2WT01MTNDODY1Qw==")
                        .header("X-Atlassian-Token","no-check")

                        .multiPart("file", new File("C:\\Users\\saipr\\Pictures\\IMG_0181.jpg")).log().all()

                .when().post("rest/api/3/issue/{key}/attachments")

                        .then().log().all().assertThat().statusCode(200).toString();


    }
}
