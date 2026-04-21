CREATE TABLE plans (
    id          UUID         NOT NULL,
    goal_id     UUID         NOT NULL,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    created_at  TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT pk_plans PRIMARY KEY (id),
    CONSTRAINT fk_plans_goal FOREIGN KEY (goal_id) REFERENCES goals (id)
);
