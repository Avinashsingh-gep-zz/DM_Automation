@api 
Feature: API 

@test1 
Scenario Outline: Verify user can Save Should Cost as Draft 
	Given Generate Basic Token 
	When Post API with <FileName> 
	Then Status should be Ok 
	
	Examples: 
		| FileName |
		| APIDATA.json | 