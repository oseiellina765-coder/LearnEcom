CREATE TABLE orders (
                        id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                        user_id UUID NOT NULL,
                        shipping_address_id UUID NOT NULL,
                        status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
                        total_amount NUMERIC(12, 2) NOT NULL DEFAULT 0,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                        CONSTRAINT fk_orders_user
                            FOREIGN KEY (user_id)
                                REFERENCES users(id)
                                ON DELETE RESTRICT,

                        CONSTRAINT fk_orders_shipping_address
                            FOREIGN KEY (shipping_address_id)
                                REFERENCES addresses(id)
                                ON DELETE RESTRICT
);


CREATE INDEX idx_orders_user_id
    ON orders(user_id);


CREATE INDEX idx_orders_status
    ON orders(status);


