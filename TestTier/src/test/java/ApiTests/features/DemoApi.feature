@api 
Feature: API 

@test1 
Scenario Outline: Verify user can Save Should Cost as Draft 
	Given Generate Basic Toekn 
	When Post API with <FileName> 
	Then Staus should be Ok 
	
	Examples: 
		| FileName |
		| APIDATA.json | 