Feature: Get Goal by ID
  As a user, I want to retrieve a goal by ID so I can review my training targets

  Background:
    Given a goal exists with the following data:
      | goalType       | DISTANCE   |
      | goalNumber     | 42.0       |
      | targetDate     | 2026-12-31 |
      | weeklyWorkouts | 3          |
      | description    | Marathon   |

  Scenario: Successfully retrieve an existing goal
    When I send a GET request to "/api/v1/goals/{savedGoalId}"
    Then the response status is 200
    And the response body field "goalType" equals "DISTANCE"
    And the response body field "targetDate" equals "2026-12-31"
    And the response body field "weeklyWorkouts" equals 3
    And the response body field "description" equals "Marathon"
    And the response body field "createdAt" is not null

  Scenario: Fail to retrieve a goal with a non-existent ID
    When I send a GET request to "/api/v1/goals/00000000-0000-0000-0000-000000000000"
    Then the response status is 404
    And the response body field "title" equals "Goal Not Found"
    And the response body field "detail" contains "00000000-0000-0000-0000-000000000000"

  Scenario: Fail to retrieve a goal with a malformed UUID
    When I send a GET request to "/api/v1/goals/not-a-uuid"
    Then the response status is 400
