package com.corunner.plan.api.integration;

import com.corunner.plan.api.infrastructure.goal.persistence.GoalJpaRepository;
import com.corunner.plan.api.infrastructure.plan.persistence.PlanJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration")
@Tag("integration")
class PlanIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlanJpaRepository planJpaRepository;

    @Autowired
    private GoalJpaRepository goalJpaRepository;

    @BeforeEach
    void cleanUp() {
        planJpaRepository.deleteAll();
        goalJpaRepository.deleteAll();
    }

    @Test
    void createPlan_thenGetByGoal_happyPath() throws Exception {
        MvcResult goalResult = mockMvc.perform(post("/api/v1/goals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "goalType": "DISTANCE",
                                  "goalNumber": 42.0,
                                  "targetDate": "2026-12-31",
                                  "weeklyWorkouts": 3,
                                  "description": "Marathon training"
                                }
                                """))
                .andExpect(status().isCreated())
                .andReturn();

        String goalId = goalResult.getResponse().getContentAsString()
                .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

        mockMvc.perform(post("/api/v1/plans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("""
                                {
                                  "goalId": "%s",
                                  "name": "16-Week Marathon Plan",
                                  "description": "Build up to 42km"
                                }
                                """, goalId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.goalId").value(goalId))
                .andExpect(jsonPath("$.name").value("16-Week Marathon Plan"))
                .andExpect(jsonPath("$.description").value("Build up to 42km"))
                .andExpect(jsonPath("$.createdAt").isNotEmpty());

        mockMvc.perform(get("/api/v1/goals/" + goalId + "/plans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("16-Week Marathon Plan"))
                .andExpect(jsonPath("$[0].goalId").value(goalId));
    }

    @Test
    void getPlansByGoal_returnsEmptyList_whenNoPlansExist() throws Exception {
        MvcResult goalResult = mockMvc.perform(post("/api/v1/goals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "goalType": "TIME",
                                  "goalNumber": 180.0,
                                  "targetDate": "2026-06-30",
                                  "weeklyWorkouts": 4
                                }
                                """))
                .andExpect(status().isCreated())
                .andReturn();

        String goalId = goalResult.getResponse().getContentAsString()
                .replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

        mockMvc.perform(get("/api/v1/goals/" + goalId + "/plans"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }
}
