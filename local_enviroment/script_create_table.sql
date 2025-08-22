CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE public.tasks
(
    task_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    title character varying,
    description character varying,
    priority character varying,
    completed boolean
);