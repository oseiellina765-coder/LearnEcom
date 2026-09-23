CREATE TABLE carts (
                       id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                       user_id UUID UNIQUE,

                       session_token VARCHAR(255) UNIQUE,

                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                       CONSTRAINT fk_carts_user
                           FOREIGN KEY (user_id)
                               REFERENCES users(id)
                               ON DELETE CASCADE
);

CREATE INDEX idx_carts_user_id
    ON carts(user_id);

CREATE INDEX idx_carts_session_token
    ON carts(session_token);

