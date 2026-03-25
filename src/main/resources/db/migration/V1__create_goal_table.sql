CREATE TABLE goals (
    id              UUID            NOT NULL,
    goal_type       VARCHAR(20)     NOT NULL,
    goal_number     NUMERIC(10, 4)  NOT NULL,
    target_date     DATE            NOT NULL,
    weekly_workouts INTEGER         NOT NULL,
    description     TEXT,
    created_at      TIMESTAMPTZ     NOT NULL,
    CONSTRAINT pk_goals PRIMARY KEY (id)
);
