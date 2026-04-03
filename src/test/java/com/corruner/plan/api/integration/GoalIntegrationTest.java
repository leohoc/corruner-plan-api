package com.corruner.plan.api.integration;

import com.corruner.plan.api.infrastructure.goal.persistence.GoalJpaRepository;
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
class GoalIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GoalJpaRepository goalJpaRepository;

    @BeforeEach
    void cleanUp() {
        goalJpaRepository.deleteAll();
    }

    @Test
    void createGoal_happyPath() throws Exception {
        mockMvc.perform(post("/api/v1/goals")
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
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.goalType").value("DISTANCE"))
                .andExpect(jsonPath("$.targetDate").value("2026-12-31"))
                .andExpect(jsonPath("$.weeklyWorkouts").value(3))
                .andExpect(jsonPath("$.description").value("Marathon training"))
                .andExpect(jsonPath("$.createdAt").isNotEmpty());
    }

    @Test
    void createGoal_thenGetById_happyPath() throws Exception {
        MvcResult createResult = mockMvc.perform(post("/api/v1/goals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "goalType": "TIME",
                                  "goalNumber": 180.0,
                                  "targetDate": "2026-06-30",
                                  "weeklyWorkouts": 4,
                                  "description": "Half marathon"
                                }
                                """))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = createResult.getResponse().getContentAsString();
        String id = responseBody.replaceAll(".*\"id\":\"([^\"]+)\".*", "$1");

        mockMvc.perform(get("/api/v1/goals/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.goalType").value("TIME"))
                .andExpect(jsonPath("$.targetDate").value("2026-06-30"))
                .andExpect(jsonPath("$.weeklyWorkouts").value(4))
                .andExpect(jsonPath("$.description").value("Half marathon"));
    }
}
