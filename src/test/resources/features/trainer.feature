Feature: Practicing Lingo
As a Lingo participant,
I want to practice guessing words,
In order to prepare myself for a participation in Lingo.

Scenario: Start New Game
  When I win the round
  Then I should gain points
  And I should be able to play a new round

Scenario: Start New Round
  When I guess the correct word
  Then I should gain points
  And I should get a new word to guess

  Scenario Outline: Start a new round
    Given I am playing a game
    And the round was won
    And the last word had "<previous length>" letters
    When I start a new round
    Then the word to guess has "<next length>" letters

    Examples:
      | previous length | next length |
      | 5               | 6           |
      | 6               | 7           |
      | 7               | 5           |

  # Failure path
    Given I am playing a game
    And the round was lost
    Then I cannot start a new round

  Scenario Outline: Guess a Word
    Given I'm given the first letter of a word
    When I guess the word "<guess>"
    And the correct word is "<word>"
    Then I should see "<feedback>" as feedback

    Examples:
      | word | guess |               feedback              |
      | cat  | dog   |        absent, absent, absent       |
      | cat  | cash  |  invalid, invalid, invalid, invalid |
      | cat  | act   |       present, present, correct     |
      | cat  | cat   |       correct, correct, correct     |

    # Failure Path
    Given I am given the first letter of a word
    And I don't guess the word after "<amount of tries>" tries
    Then I lost the round