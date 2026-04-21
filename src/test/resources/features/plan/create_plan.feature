Feature: Create Plan
  As a user, I want to create a training plan linked to a goal so I can organize my training

  Background:
    Given a goal exists with the following data:
      | goalType       | DISTANCE   |
      | goalNumber     | 42.0       |
      | targetDate     | 2026-12-31 |
      | weeklyWorkouts | 3          |
      | description    | Marathon   |

  Scenario: Successfully create a plan for an existing goal
    When I send a POST request to "/api/v1/plans" with body:
      """
      {
        "goalId": "{savedGoalId}",
        "name": "16-Week Marathon Plan",
        "description": "Build up to 42km gradually"
      }
      """
    Then the response status is 201
    And the response body contains a valid UUID in field "id"
    And the response body field "name" equals "16-Week Marathon Plan"
    And the response body field "description" equals "Build up to 42km gradually"
    And the response body field "createdAt" is not null

  Scenario: Successfully create a plan without description
    When I send a POST request to "/api/v1/plans" with body:
      """
      {
        "goalId": "{savedGoalId}",
        "name": "Basic Plan"
      }
      """
    Then the response status is 201
    And the response body field "name" equals "Basic Plan"
    And the response body field "description" is absent

  Scenario: Fail to create a plan for a non-existent goal
    When I send a POST request to "/api/v1/plans" with body:
      """
      {
        "goalId": "00000000-0000-0000-0000-000000000000",
        "name": "Orphan Plan"
      }
      """
    Then the response status is 404
    And the response body field "title" equals "Goal Not Found"

  Scenario: Fail to create a plan without a name
    When I send a POST request to "/api/v1/plans" with body:
      """
      {
        "goalId": "{savedGoalId}",
        "name": ""
      }
      """
    Then the response status is 400
    And the response body contains a validation error for field "name"

  Scenario: Fail to create a plan without a goalId
    When I send a POST request to "/api/v1/plans" with body:
      """
      {
        "name": "Plan without goal"
      }
      """
    Then the response status is 400
    And the response body contains a validation error for field "goalId"
