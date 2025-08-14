-- =====================================================
-- SCHEMA SQL PARA PRODUCTS API
-- =====================================================
-- Este archivo contiene la definición completa del esquema
-- de base de datos para la aplicación Products API
-- =====================================================

-- Crear base de datos (PostgreSQL)
-- CREATE DATABASE products_api;

-- Usar base de datos
-- USE products_api;

-- =====================================================
-- TABLA BASE (ABSTRACT)
-- =====================================================
-- Nota: Esta tabla no se crea directamente, sus campos
-- se heredan en las entidades concretas

-- =====================================================
-- TABLA DE CATEGORÍAS
-- =====================================================
CREATE TABLE IF NOT EXISTS categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    
    -- Constraints
    CONSTRAINT uk_categories_name UNIQUE (name),
    CONSTRAINT chk_categories_name_length CHECK (LENGTH(TRIM(name)) > 0)
);

-- =====================================================
-- TABLA DE PRODUCTOS
-- =====================================================
CREATE TABLE IF NOT EXISTS products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    price DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    
    -- Constraints
    CONSTRAINT uk_products_name UNIQUE (name),
    CONSTRAINT chk_products_name_length CHECK (LENGTH(TRIM(name)) > 0),
    CONSTRAINT chk_products_price_positive CHECK (price > 0),
    CONSTRAINT chk_products_price_precision CHECK (price <= 99999999.99)
);

-- =====================================================
-- TABLA DE IMÁGENES
-- =====================================================
CREATE TABLE IF NOT EXISTS images (
    id BIGSERIAL PRIMARY KEY,
    url VARCHAR(255) NOT NULL,
    product_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    
    -- Foreign Keys
    CONSTRAINT fk_images_product_id FOREIGN KEY (product_id) 
        REFERENCES products(id) ON DELETE CASCADE ON UPDATE CASCADE,
    
    -- Constraints
    CONSTRAINT uk_images_url UNIQUE (url),
    CONSTRAINT chk_images_url_length CHECK (LENGTH(TRIM(url)) > 0)
);

-- =====================================================
-- TABLA DE ATRIBUTOS
-- =====================================================
CREATE TABLE IF NOT EXISTS attributes (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    product_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    deleted_at TIMESTAMP,
    is_active BOOLEAN NOT NULL DEFAULT TRUE,
    
    -- Foreign Keys
    CONSTRAINT fk_attributes_product_id FOREIGN KEY (product_id) 
        REFERENCES products(id) ON DELETE CASCADE ON UPDATE CASCADE,
    
    -- Constraints
    CONSTRAINT uk_attributes_name_product UNIQUE (name, product_id),
    CONSTRAINT chk_attributes_name_length CHECK (LENGTH(TRIM(name)) > 0),
    CONSTRAINT chk_attributes_description_length CHECK (LENGTH(TRIM(description)) > 0)
);

-- =====================================================
-- TABLA DE RELACIÓN PRODUCTO-CATEGORÍA (MANY-TO-MANY)
-- =====================================================
CREATE TABLE IF NOT EXISTS product_categories (
    product_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    -- Primary Key compuesta
    PRIMARY KEY (product_id, category_id),
    
    -- Foreign Keys
    CONSTRAINT fk_product_categories_product_id FOREIGN KEY (product_id) 
        REFERENCES products(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_product_categories_category_id FOREIGN KEY (category_id) 
        REFERENCES categories(id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- =====================================================
-- ÍNDICES PARA OPTIMIZACIÓN
-- =====================================================

-- Índices para productos
CREATE INDEX IF NOT EXISTS idx_products_name ON products(name);
CREATE INDEX IF NOT EXISTS idx_products_price ON products(price);
CREATE INDEX IF NOT EXISTS idx_products_is_active ON products(is_active);
CREATE INDEX IF NOT EXISTS idx_products_created_at ON products(created_at);

-- Índices para categorías
CREATE INDEX IF NOT EXISTS idx_categories_name ON categories(name);
CREATE INDEX IF NOT EXISTS idx_categories_is_active ON categories(is_active);

-- Índices para imágenes
CREATE INDEX IF NOT EXISTS idx_images_product_id ON images(product_id);
CREATE INDEX IF NOT EXISTS idx_images_url ON images(url);

-- Índices para atributos
CREATE INDEX IF NOT EXISTS idx_attributes_product_id ON attributes(product_id);
CREATE INDEX IF NOT EXISTS idx_attributes_name ON attributes(name);

-- Índices para la tabla de relación
CREATE INDEX IF NOT EXISTS idx_product_categories_product_id ON product_categories(product_id);
CREATE INDEX IF NOT EXISTS idx_product_categories_category_id ON product_categories(category_id);

-- =====================================================
-- TRIGGERS PARA ACTUALIZACIÓN AUTOMÁTICA
-- =====================================================

-- Función para actualizar updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Triggers para actualizar updated_at automáticamente
CREATE TRIGGER update_products_updated_at 
    BEFORE UPDATE ON products 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_categories_updated_at 
    BEFORE UPDATE ON categories 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_images_updated_at 
    BEFORE UPDATE ON images 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_attributes_updated_at 
    BEFORE UPDATE ON attributes 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- =====================================================
-- VISTAS ÚTILES
-- =====================================================

-- Vista para productos con información completa
CREATE OR REPLACE VIEW v_products_complete AS
SELECT 
    p.id,
    p.name,
    p.description,
    p.price,
    p.created_at,
    p.updated_at,
    p.is_active,
    COUNT(DISTINCT i.id) as image_count,
    COUNT(DISTINCT a.id) as attribute_count,
    COUNT(DISTINCT pc.category_id) as category_count
FROM products p
LEFT JOIN images i ON p.id = i.product_id AND i.is_active = true
LEFT JOIN attributes a ON p.id = a.product_id AND a.is_active = true
LEFT JOIN product_categories pc ON p.id = pc.product_id
WHERE p.is_active = true
GROUP BY p.id, p.name, p.description, p.price, p.created_at, p.updated_at, p.is_active;

-- Vista para productos por categoría
CREATE OR REPLACE VIEW v_products_by_category AS
SELECT 
    c.id as category_id,
    c.name as category_name,
    p.id as product_id,
    p.name as product_name,
    p.price,
    p.is_active
FROM categories c
JOIN product_categories pc ON c.id = pc.category_id
JOIN products p ON pc.product_id = p.id
WHERE c.is_active = true AND p.is_active = true;

-- =====================================================
-- COMENTARIOS DE TABLAS
-- =====================================================

COMMENT ON TABLE products IS 'Tabla principal de productos del sistema';
COMMENT ON TABLE categories IS 'Categorías disponibles para clasificar productos';
COMMENT ON TABLE images IS 'Imágenes asociadas a los productos';
COMMENT ON TABLE attributes IS 'Atributos específicos de cada producto';
COMMENT ON TABLE product_categories IS 'Tabla de relación muchos a muchos entre productos y categorías';

-- =====================================================
-- FIN DEL SCHEMA
-- =====================================================
