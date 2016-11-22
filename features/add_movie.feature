Feature: User adds a movie to his list

	Background:
		Given I am in the movie list screen
		And I follow "Add Movie"

	Scenario: User movie does not exist
		When I fill in "The Shawshank Redemption 2: Electric Boogaloo"
		And I press "Find"
		Then I should see "Sorry, we don't know that movie"

	Scenario: User movie exists and user adds it to his list
		When I fill in "12 Angry Men"
		And I press "Find"
		Then I should see "12 Angry Men" as a movie in the movie list
		When I press "12 Angry Men"
		Then I should be in my movie list
		And I should see "12 Angry Men" as a movie in the movie list

