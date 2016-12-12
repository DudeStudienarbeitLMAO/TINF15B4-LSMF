
Feature: Registration


	Scenario: I register with new details
		Given I wait for the "register" screen to appear
		And I enter text "Lellekpopp" into field with id "input_name"
		And I enter text "lachsack@prallermann.de" into field with id "input_email"
		And I enter text "jackojacko23" into field with id "input_password"
		And I enter text "jackojacko23" into field with id "input_passwordconfirm"
		And I press "Create Account"
		Then I should see "Registration successful, confirmation Email sent"


	Scenario: I register with already existing details
                Given I wait for the "register" screen to appear
		And I enter text "Lellekpopp" into field with id "input_name"
		And I enter text "lachsack@prallermann.de" into field with id "input_email"
		And I enter text "jackojacko23" into field with id "input_password"
		And I enter text "jackojacko23" into field with id "input_passwordconfirm"
		And I press "Create Account"
		Then I should see "Registration failure"


	Scenario: I register with already existing details
		Given I wait for the "register" screen to appear
		And I enter text "Passwordwurst" into field with id "input_name"
		And I enter text "lachsack@metzgerfreun.de" into field with id "input_email"
		And I enter text "mama" into field with id "input_password"
		And I enter text "mama" into field with id "input_passwordconfirm"
		And I press "Create Account"
		Then I should see "Failed"
