Feature: Activation


	Scenario: User clicks activation link
		Given I have received an activation e-mail
		When I open the e-mail
		Then I should see "Click the following link to activate your account"
		When I follow "Click here" in the e-mail
		Then I should be redirected to an activation page
		And I should see "You have been successfully activated"
