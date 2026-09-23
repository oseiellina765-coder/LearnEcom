CREATE TABLE categories (
                            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                            name VARCHAR(255) NOT NULL,
                            description TEXT,
                            image_url VARCHAR(500),
                            active BOOLEAN NOT NULL DEFAULT TRUE,

                            CONSTRAINT uq_categories_name
                                UNIQUE (name)
);