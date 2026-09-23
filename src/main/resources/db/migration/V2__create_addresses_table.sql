CREATE TABLE addresses (
                           id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                           user_id UUID NOT NULL,
                           address_line VARCHAR(255) NOT NULL,
                           city VARCHAR(255) NOT NULL,
                           region VARCHAR(255) NOT NULL,
                           country VARCHAR(255) NOT NULL,
                           postal_code VARCHAR(50),
                           phone_number VARCHAR(50),

                           is_default BOOLEAN NOT NULL DEFAULT FALSE,

                           CONSTRAINT fk_addresses_user
                               FOREIGN KEY (user_id)
                                   REFERENCES users(id)
                                   ON DELETE CASCADE
);

CREATE INDEX idx_addresses_user_id
    ON addresses(user_id);