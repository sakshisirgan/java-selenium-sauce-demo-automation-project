Feature: Login Module

  Scenario: Login with valid credentials
    Given User is on login page using "chrome"
    When User enters "standard_user" and "secret_sauce"
    Then User should be redirected to inventory page

  Scenario: Login with locked-out user
    Given User is on login page
    When User enters "locked_out_user" and "secret_sauce"
    Then Error message "Epic sadface: Sorry, this user has been locked out." should be displayed

  Scenario: Login with problem user
    Given User is on login page
    When User enters "problem_user" and "secret_sauce"
    Then User should be redirected to inventory page

  Scenario: Login with performance glitch user
    Given User is on login page
    When User enters "performance_glitch_user" and "secret_sauce"
    Then User should be redirected to inventory page

  Scenario: Login with invalid credentials
    Given User is on login page
    When User enters "wrong_user" and "wrong_pass"
    Then Error message "Epic sadface: Username and password do not match any user in this service" should be displayed

  Scenario: Login with blank username and password
    Given User is on login page
    When User enters "" and ""
    Then Error message "Epic sadface: Username is required" should be displayed

  Scenario:  Verify error message for failed login
    Given User is on login page
    When User enters "standard_user" and ""
    Then Error message "Epic sadface: Password is required" should be displayed
    
   Scenario: Verify logout functionality
    Given User is logged in with "standard_user" and "secret_sauce"
    When User logs out
    Then User should be on login page
