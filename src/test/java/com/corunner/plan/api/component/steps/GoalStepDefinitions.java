package com.corunner.plan.api.component.steps;

import com.corunner.plan.api.infrastructure.goal.persistence.GoalJpaRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.datatable.DataTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Map;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GoalStepDefinitions {

    private static final String UUID_REGEX =
            "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GoalJpaRepository goalJpaRepository;

    private ResultActions lastResult;
    private UUID savedGoalId;

    @Before
    public void setUp() {
        goalJpaRepository.deleteAll();
        lastResult = null;
        savedGoalId = null;
    }

    @Given("a goal exists with the following data:")
    public void aGoalExistsWithTheFollowingData(DataTable dataTable) throws Exception {
        Map<String, String> data = dataTable.asMap(String.class, String.class);
        String body = String.format("""
                {
                  "goalType": "%s",
                  "goalNumber": %s,
                  "targetDate": "%s",
                  "weeklyWorkouts": %s,
                  "description": "%s"
                }
                """,
                data.get("goalType"),
                data.get("goalNumber"),
                data.get("targetDate"),
                data.get("weeklyWorkouts"),
                data.getOrDefault("description", "")
        );

        String responseBody = mockMvc.perform(post("/api/v1/goals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String idValue = responseBody.replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");
        savedGoalId = UUID.fromString(idValue);
    }

    @When("I send a POST request to {string} with body:")
    public void iSendAPostRequestWithBody(String url, String body) throws Exception {
        String resolvedBody = savedGoalId != null
                ? body.replace("{savedGoalId}", savedGoalId.toString())
                : body;
        lastResult = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(resolvedBody));
    }

    public UUID getSavedGoalId() {
        return savedGoalId;
    }

    public ResultActions getLastResult() {
        return lastResult;
    }

    @When("I send a GET request to {string}")
    public void iSendAGetRequest(String url) throws Exception {
        String resolvedUrl = savedGoalId != null
                ? url.replace("{savedGoalId}", savedGoalId.toString())
                : url;
        lastResult = mockMvc.perform(get(resolvedUrl));
    }

    @Then("the response status is {int}")
    public void theResponseStatusIs(int statusCode) throws Exception {
        lastResult.andExpect(status().is(statusCode));
    }

    @And("the response body field {string} equals {string}")
    public void theResponseBodyFieldEqualsString(String field, String value) throws Exception {
        lastResult.andExpect(jsonPath("$." + field).value(value));
    }

    @And("the response body field {string} equals {int}")
    public void theResponseBodyFieldEqualsInt(String field, int value) throws Exception {
        lastResult.andExpect(jsonPath("$." + field).value(value));
    }

    @And("the response body field {string} is not null")
    public void theResponseBodyFieldIsNotNull(String field) throws Exception {
        lastResult.andExpect(jsonPath("$." + field).value(notNullValue()));
    }

    @And("the response body field {string} is absent")
    public void theResponseBodyFieldIsAbsent(String field) throws Exception {
        lastResult.andExpect(jsonPath("$." + field).doesNotExist());
    }

    @And("the response body contains a valid UUID in field {string}")
    public void theResponseBodyContainsAValidUUIDInField(String field) throws Exception {
        lastResult.andExpect(jsonPath("$." + field).value(matchesPattern(UUID_REGEX)));
    }

    @And("the response body contains a validation error for field {string}")
    public void theResponseBodyContainsAValidationErrorForField(String fieldName) throws Exception {
        lastResult.andExpect(jsonPath("$.errors[*]", hasItem(containsString(fieldName))));
    }

    @And("the response body field {string} contains {string}")
    public void theResponseBodyFieldContains(String field, String substring) throws Exception {
        lastResult.andExpect(jsonPath("$." + field, containsString(substring)));
    }
}
