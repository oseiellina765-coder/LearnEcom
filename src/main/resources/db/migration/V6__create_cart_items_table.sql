CREATE TABLE cart_items (
                            id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                            cart_id UUID NOT NULL,
                            product_id UUID NOT NULL,
                            quantity INTEGER NOT NULL,
                            unit_price NUMERIC(12, 2) NOT NULL,

                            CONSTRAINT fk_cart_items_cart
                                FOREIGN KEY (cart_id)
                                    REFERENCES carts(id)
                                    ON DELETE CASCADE,

                            CONSTRAINT fk_cart_items_product
                                FOREIGN KEY (product_id)
                                    REFERENCES products(id)
                                    ON DELETE RESTRICT,

                            CONSTRAINT uq_cart_product
                                UNIQUE (cart_id, product_id),

                            CONSTRAINT chk_cart_items_quantity
                                CHECK (quantity > 0),

                            CONSTRAINT chk_cart_items_unit_price
                                CHECK (unit_price >= 0)
);

CREATE INDEX idx_cart_items_cart_id
    ON cart_items(cart_id);

CREATE INDEX idx_cart_items_product_id
    ON cart_items(product_id);