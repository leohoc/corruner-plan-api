package com.corunner.plan.api.infrastructure.plan.persistence;

import com.corunner.plan.api.domain.plan.Plan;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlanRepositoryAdapterTest {

    @Mock
    private PlanJpaRepository jpaRepository;

    @Mock
    private PlanMapper mapper;

    @InjectMocks
    private PlanRepositoryAdapter adapter;

    @Test
    void save_mapsToJpaEntity_savesAndMapsBack() {
        UUID goalId = UUID.randomUUID();
        Plan plan = new Plan(UUID.randomUUID(), goalId, "Plan A", "Desc", Instant.now());
        PlanJpaEntity entity = new PlanJpaEntity(plan.getId(), goalId, "Plan A", "Desc", plan.getCreatedAt());

        when(mapper.toJpaEntity(plan)).thenReturn(entity);
        when(jpaRepository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(plan);

        Plan result = adapter.save(plan);

        verify(mapper).toJpaEntity(plan);
        verify(jpaRepository).save(entity);
        verify(mapper).toDomain(entity);
        assertThat(result).isEqualTo(plan);
    }

    @Test
    void findByGoalId_returnsMappedPlans() {
        UUID goalId = UUID.randomUUID();
        PlanJpaEntity entity = new PlanJpaEntity(UUID.randomUUID(), goalId, "Plan A", null, Instant.now());
        Plan plan = new Plan(entity.getId(), goalId, "Plan A", null, entity.getCreatedAt());

        when(jpaRepository.findByGoalId(goalId)).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(plan);

        List<Plan> result = adapter.findByGoalId(goalId);

        assertThat(result).hasSize(1).containsExactly(plan);
        verify(mapper).toDomain(entity);
    }

    @Test
    void findByGoalId_returnsEmptyList_whenNoneFound() {
        UUID goalId = UUID.randomUUID();
        when(jpaRepository.findByGoalId(goalId)).thenReturn(List.of());

        List<Plan> result = adapter.findByGoalId(goalId);

        assertThat(result).isEmpty();
    }
}
