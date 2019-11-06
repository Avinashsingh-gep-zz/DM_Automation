@api
Feature: API

Background: 


  Scenario Outline: Save as Draft
    Given Should Cost is loaded1
    And Login
    When Click on Scost Icon1
    And Click on Blank Icon1
    And Fill Basic information1
    Then Click on Done1

    Examples: 
      | username | password |
      | user     | pass     |
