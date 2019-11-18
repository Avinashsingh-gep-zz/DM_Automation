@web @R2481
Feature: Web Testing


 Background: 
 	Given Establish Database Connection

  @C177817
  Scenario Outline: Verify user can login to Should Cost 
    Given Login using valid credentials
    When Create Should Cost From Blank

    Examples: 
      | username | password |
      | user     | pass     |
      
  @C177819
  Scenario Outline: Verify user can Save Should Cost as Draft 
    Given Login using valid credentials
    When Create Should Cost From Blank
    Given Valid test data available to fill Basic information
    And Fill Basic information
    Then Click on Done
    Then Verify Save As Draft button is visible
    When Click on Save as draft
    Then Click on Done  
     
    Examples: 
      | username | password |
      | user     | pass     |     
      
 