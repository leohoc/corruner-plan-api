Feature: Create Goal
  As a user, I want to create training goals so I can track my progress

  Scenario Outline: Successfully create a goal of each type
    When I send a POST request to "/api/v1/goals" with body:
      """
      {
        "goalType": "<goalType>",
        "goalNumber": <goalNumber>,
        "targetDate": "2026-12-31",
        "weeklyWorkouts": 3,
        "description": "Test goal for <goalType>"
      }
      """
    Then the response status is 201
    And the response body contains a valid UUID in field "id"
    And the response body field "goalType" equals "<goalType>"
    And the response body field "weeklyWorkouts" equals 3
    And the response body field "targetDate" equals "2026-12-31"
    And the response body field "description" equals "Test goal for <goalType>"
    And the response body field "createdAt" is not null

    Examples:
      | goalType  | goalNumber |
      | DISTANCE  | 42.0       |
      | TIME      | 180.0      |
      | FREQUENCY | 5.0        |
      | PACE      | 5.5        |

  Scenario: Successfully create a goal without optional description
    When I send a POST request to "/api/v1/goals" with body:
      """
      {
        "goalType": "DISTANCE",
        "goalNumber": 10.0,
        "targetDate": "2026-06-30",
        "weeklyWorkouts": 2
      }
      """
    Then the response status is 201
    And the response body field "description" is absent

  Scenario: Fail to create a goal when goalType is missing
    When I send a POST request to "/api/v1/goals" with body:
      """
      {
        "goalNumber": 10.0,
        "targetDate": "2026-06-30",
        "weeklyWorkouts": 2
      }
      """
    Then the response status is 400
    And the response body field "title" equals "Validation Error"
    And the response body contains a validation error for field "goalType"

  Scenario: Fail to create a goal when goalNumber is zero
    When I send a POST request to "/api/v1/goals" with body:
      """
      {
        "goalType": "DISTANCE",
        "goalNumber": 0,
        "targetDate": "2026-06-30",
        "weeklyWorkouts": 2
      }
      """
    Then the response status is 400
    And the response body field "title" equals "Validation Error"
    And the response body contains a validation error for field "goalNumber"

  Scenario: Fail to create a goal when goalNumber is negative
    When I send a POST request to "/api/v1/goals" with body:
      """
      {
        "goalType": "DISTANCE",
        "goalNumber": -5.0,
        "targetDate": "2026-06-30",
        "weeklyWorkouts": 2
      }
      """
    Then the response status is 400
    And the response body contains a validation error for field "goalNumber"

  Scenario: Fail to create a goal when weeklyWorkouts is zero
    When I send a POST request to "/api/v1/goals" with body:
      """
      {
        "goalType": "DISTANCE",
        "goalNumber": 10.0,
        "targetDate": "2026-06-30",
        "weeklyWorkouts": 0
      }
      """
    Then the response status is 400
    And the response body contains a validation error for field "weeklyWorkouts"

  Scenario: Fail to create a goal when multiple required fields are missing
    When I send a POST request to "/api/v1/goals" with body:
      """
      {
        "description": "incomplete goal"
      }
      """
    Then the response status is 400
    And the response body field "title" equals "Validation Error"
    And the response body contains a validation error for field "goalType"
    And the response body contains a validation error for field "goalNumber"
    And the response body contains a validation error for field "targetDate"
    And the response body contains a validation error for field "weeklyWorkouts"

  Scenario: Fail to create a goal with an invalid goalType value
    When I send a POST request to "/api/v1/goals" with body:
      """
      {
        "goalType": "INVALID_TYPE",
        "goalNumber": 10.0,
        "targetDate": "2026-06-30",
        "weeklyWorkouts": 2
      }
      """
    Then the response status is 400
