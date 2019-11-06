@web
Feature: Web


#  @test1
#  Scenario Outline: Verify user can login to Should Cost
#    Given Login using valid credentials
#    When Create Should Cost From Blank
#
#    Examples: 
#      | username | password |
#      | user     | pass     |
#      
  #@test2
  #Scenario Outline: Verify user can see basic details section
    #Given Login using valid credentials
    #When Create Should Cost From Blank
    #Given Valid test data available to fill Basic information
#
    #Examples: 
      #| username | password |
      #| user     | pass     |    
      #
      #
  #@test3
  #Scenario Outline: Verify user can fill Should Cost basic details
    #Given Login using valid credentials
    #When Create Should Cost From Blank
    #Given Valid test data available to fill Basic information
    #And Fill Basic information
    #Then Click on Done
  #
    #Examples: 
      #| username | password |
      #| user     | pass     |    
#
  @test4
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


 #@test5
  #Scenario: Verify user can edit and Save existing Should Cost document as Draft 
    #Given Login using valid credentials
    #When Create Should Cost From Blank
    #Given Valid test data available to fill Basic information
    #And Fill Basic information
    #Then Click on Done
    #Then Verify Save As Draft button is visible
    #When Click on Save as draft
    #Then Click on Done
    #Given Valid test data available to fill Basic information
    #And Fill Basic information
    #Then Click on Done
    #Then Verify Save As Draft button is visible
    #When Click on Save as draft
    #Then Click on Done
    
