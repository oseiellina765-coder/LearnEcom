CREATE TABLE order_items (
                             id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                             order_id UUID NOT NULL,
                             product_id UUID NOT NULL,
                             quantity INTEGER NOT NULL,
                             unit_price NUMERIC(12, 2) NOT NULL,
                             subtotal NUMERIC(12, 2) NOT NULL,

                             CONSTRAINT fk_order_items_order
                                 FOREIGN KEY (order_id)
                                     REFERENCES orders(id)
                                     ON DELETE CASCADE,

                             CONSTRAINT fk_order_items_product
                                 FOREIGN KEY (product_id)
                                     REFERENCES products(id)
                                     ON DELETE RESTRICT,

                             CONSTRAINT uq_order_product
                                 UNIQUE (order_id, product_id),

                             CONSTRAINT chk_order_item_quantity
                                 CHECK (quantity > 0),

                             CONSTRAINT chk_order_item_price
                                 CHECK (unit_price >= 0),

                             CONSTRAINT chk_order_item_subtotal
                                 CHECK (subtotal >= 0)
);


CREATE INDEX idx_order_items_order_id
    ON order_items(order_id);


CREATE INDEX idx_order_items_product_id
    ON order_items(product_id);