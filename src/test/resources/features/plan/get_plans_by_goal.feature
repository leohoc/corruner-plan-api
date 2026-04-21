Feature: Get Plans by Goal
  As a user, I want to retrieve all training plans for a given goal so I can choose the best one

  Background:
    Given a goal exists with the following data:
      | goalType       | DISTANCE   |
      | goalNumber     | 42.0       |
      | targetDate     | 2026-12-31 |
      | weeklyWorkouts | 3          |
      | description    | Marathon   |

  Scenario: Successfully retrieve plans for a goal
    Given a plan exists for the saved goal with name "16-Week Marathon Plan"
    When I send a GET request to "/api/v1/goals/{savedGoalId}/plans"
    Then the response status is 200
    And the response contains 1 plans
    And the first plan field "name" equals "16-Week Marathon Plan"

  Scenario: Return empty list when goal has no plans
    When I send a GET request to "/api/v1/goals/{savedGoalId}/plans"
    Then the response status is 200
    And the response is an empty plan list
