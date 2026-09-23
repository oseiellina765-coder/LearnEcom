CREATE TABLE products (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),

                          name VARCHAR(255) NOT NULL,

                          description TEXT,

                          price NUMERIC(12, 2) NOT NULL,

                          stock_quantity INTEGER NOT NULL,

                          sku VARCHAR(255),

                          category_id UUID,

                          brand VARCHAR(255),

                          image_url VARCHAR(500),

                          active BOOLEAN NOT NULL DEFAULT TRUE,

                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                          updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

                          CONSTRAINT uq_products_sku
                              UNIQUE (sku),

                          CONSTRAINT chk_products_price
                              CHECK (price > 0),

                          CONSTRAINT chk_products_stock
                              CHECK (stock_quantity >= 0),

                          CONSTRAINT fk_products_category
                              FOREIGN KEY (category_id)
                                  REFERENCES categories(id)
                                  ON DELETE SET NULL
);

CREATE INDEX idx_products_category_id
    ON products(category_id);

CREATE INDEX idx_products_name
    ON products(name);

CREATE INDEX idx_products_brand
    ON products(brand);

CREATE INDEX idx_products_active
    ON products(active);