CREATE TABLE users (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                       first_name VARCHAR(255),
                       last_name VARCHAR(255),
                       email_id VARCHAR(255) NOT NULL,

                       CONSTRAINT uq_users_email
                           UNIQUE (email_id)
);