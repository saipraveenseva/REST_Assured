Feature: Validating Place APIs
  Scenario: Verify if place is being successfully added using AddPlaceAPI
    Given Add Place Payload
    When user calls "AddPlaceAPI" with Post http request
    Then the API call is success with status code 200
    And check "status" in response body is "OK"
    And validate "scope" in response body is "APP"


    # is used to write comments in Gherkin

  # This is where the Cucumber BDD framework begins. We write it from scratch

  # We will link the AddPlace API to every line of the above code.
  # We do the linkage in stepDefinitions.java file

  #"AddPlaceAPI" is written in quotes indicating that I can replace it with DeleteAPI or any other API in the future.
  # Instead of writing a seperate statement we just replaces what's in the quotes.

  # same goes for "status", "scope" or the field "OK" or "APP"

