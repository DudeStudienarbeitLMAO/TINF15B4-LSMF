
Feature: Registration


	  Scenario: User Registration with valid details
		When I go to the registration tab
		And I fill in the following:
			| Username     | Drachenlord1510           |
			| E-Mail       | drachenlord1510@gmail.com |
			| Password     | drachenfeuer89            |
			| Confirmation | drachenfeuer89            |
		And I press "Register"
		Then I should see "You should receive a confirmation e-mail soon"
		And "drachenlord1510@gmail.com" should receive an e-mail
		When I open the e-mail
		Then I should see "Click here to confirm your registration" in the e-mail body



	Scenario: User Registration with invalid e-mail
		When I go to the registration tab
		And I fill in the following:
			| Username     | Lellek |
			| E-Mail       | Shindy |
			| Password     | quont  |
			| Confirmation | quont  |
		And I press "Register"
		Then I should see "That is not a valid e-mail address"




	Scenario: User Registration with invalid password combination
		When I go to the registration tab
		And I fill in the following:
			| Username     | SergioDerUebermensch |
			| E-Mail       | serj@jva-schwerin.de |
			| Password     | buppkes              |
			| Confirmation | bappkes              |
		And I press "Register"
		Then I should see "The passwords do not match"




	Scenario: User Registration with already existing details
		Given a user "jimbo" with e-mail "jimbo@kraftklub.de" already exists
		When I go to registration tab
		And I fill in the following:
			| Username     | jimbo              |
			| E-Mail       | jimbo@kraftklub.de |
			| Password     | guteschwingung23   |
			| Confirmation | guteschwingung23   |
		And I press "Register"
		Then I should see "This user already exists"
