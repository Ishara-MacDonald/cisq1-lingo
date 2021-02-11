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