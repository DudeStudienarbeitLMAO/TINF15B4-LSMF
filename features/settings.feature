Feature: Settings
          Settings are stored properly

            Scenario: Change Settings Multiselect
                    Given I wait for the "SettingsActivity" screen to appear
                    Then I press "General"
                    Then I press "Add friends to messages"
                    And I press "Always"
                    And I wait for 5 seconds

            Scenario: Changes are applied
                    Given I wait for the "SettingsActivity" screen to appear
                    Then I press "General"
                    Then I press "Add friends to messages"
                    Then I should see "Always"
