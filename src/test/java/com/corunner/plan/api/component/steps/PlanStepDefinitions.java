package com.corunner.plan.api.component.steps;

import com.corunner.plan.api.infrastructure.plan.persistence.PlanJpaRepository;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PlanStepDefinitions {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlanJpaRepository planJpaRepository;

    @Autowired
    private GoalStepDefinitions goalStepDefinitions;

    private ResultActions lastResult;
    private UUID savedPlanId;

    // Runs before GoalStepDefinitions.setUp() (order 10000) to respect the FK constraint:
    // plans must be deleted before goals can be deleted.
    @Before(order = 100)
    public void cleanPlans() {
        planJpaRepository.deleteAll();
        lastResult = null;
        savedPlanId = null;
    }

    @Given("a plan exists for the saved goal with name {string}")
    public void aPlanExistsForTheSavedGoalWithName(String name) throws Exception {
        UUID goalId = goalStepDefinitions.getSavedGoalId();
        String body = String.format("""
                {
                  "goalId": "%s",
                  "name": "%s"
                }
                """, goalId, name);

        String responseBody = mockMvc.perform(post("/api/v1/plans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String idValue = responseBody.replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");
        savedPlanId = UUID.fromString(idValue);
    }

    @And("the response contains {int} plans")
    public void theResponseContainsPlans(int expectedCount) throws Exception {
        lastResult = goalStepDefinitions.getLastResult();
        lastResult.andExpect(jsonPath("$.length()").value(expectedCount));
    }

    @And("the first plan field {string} equals {string}")
    public void theFirstPlanFieldEqualsString(String field, String value) throws Exception {
        lastResult = goalStepDefinitions.getLastResult();
        lastResult.andExpect(jsonPath("$[0]." + field).value(value));
    }

    @And("the response is an empty plan list")
    public void theResponseIsAnEmptyPlanList() throws Exception {
        lastResult = goalStepDefinitions.getLastResult();
        lastResult.andExpect(jsonPath("$.length()").value(0));
    }
}
