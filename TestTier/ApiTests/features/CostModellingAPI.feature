@api
Feature: Inventory

  Background: 
    Given Feature base url <ModuleName>
      | Inventory |

  Scenario Outline: Quicklink API Testing
    When Get the Quicklinks
    Then Verify Return Value "<verification>"

    Examples: 
      | verification       |
      | Create_Goods_Issue |
