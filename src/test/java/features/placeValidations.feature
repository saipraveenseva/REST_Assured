Feature: Validating Place APIs
  Scenario: Verify if place is being successfully added using AddPlaceAPI
    Given Add Place Payload
    When user calls "AddPlaceAPI" with Post http request  // Double quotes indicate I will replace AddPlaceAPI with other API without changing the syntax of the steps
    Then the API call is success with status code 200
    And "status" in response body is "OK"   // Same here
    And "scope" in response body is "APP"
