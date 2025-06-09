Feature: User login

  Background:
    Given I use the base URL

  Scenario Outline: Valid User login
    Given I use credentials "<email>" and "<password>" for login
    And I send a POST request to "/auth/sign-in" with my request payload
    Then I should see the response status code as 200
    And I should see the response matching the "loginResponseSchema" schema


    Examples:
        | email                  | password       |
        | demo01@gmail.com       | Demo@12345     |

  Scenario Outline: Check for Invalid login attempt
    Given I use credentials <email> and <password> for login
    And I send a POST request to "/auth/sign-in" with my request payload
    Then I should see the response status code as <statusCode>

    Examples:
        | email                  | password       | statusCode |
        | "demo01@gmail.com"     | "sdawd"        | 401        |
        | ""                     | "Demo@12345"   | 400        |
        | "demo01@gmail.com"     | ""             | 400        |
