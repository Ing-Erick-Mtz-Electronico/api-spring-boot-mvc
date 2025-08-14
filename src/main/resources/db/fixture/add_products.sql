-- Primero insertar las categorías
INSERT INTO categories (name, description, is_active, created_at, updated_at) VALUES
('Laptops', 'Computadoras portátiles de alto rendimiento', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Smartphones', 'Teléfonos inteligentes móviles', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Libros', 'Libros físicos y digitales', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Tablets', 'Dispositivos tablet táctiles', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Audio', 'Dispositivos de audio y auriculares', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insertar productos
INSERT INTO products (name, description, price, is_active, created_at, updated_at) VALUES
('MacBook Pro 14"', 'Laptop Apple MacBook Pro con procesador M2 Pro, pantalla Liquid Retina XDR de 14 pulgadas', 2499.99, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('iPhone 15 Pro', 'Smartphone Apple iPhone 15 Pro con chip A17 Pro, cámara de 48MP y almacenamiento de 128GB', 999.99, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Cien años de soledad', 'Novela del escritor colombiano Gabriel García Márquez, obra maestra del realismo mágico', 19.99, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Dell XPS 13', 'Ultrabook Dell XPS 13 con procesador Intel Core i7 de 11ª generación', 1299.99, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Samsung Galaxy S24', 'Smartphone Samsung Galaxy S24 con cámara de 50MP y pantalla Dynamic AMOLED 2X', 799.99, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('El Quijote', 'La obra más famosa de Miguel de Cervantes, considerada la primera novela moderna', 24.99, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('iPad Pro 12.9"', 'Tablet Apple iPad Pro con chip M2, pantalla Liquid Retina XDR de 12.9 pulgadas', 1099.99, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Sony WH-1000XM5', 'Auriculares inalámbricos Sony con cancelación de ruido líder en la industria', 399.99, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Lenovo ThinkPad X1 Carbon', 'Laptop empresarial Lenovo ThinkPad con diseño ultraligero y duradero', 1599.99, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Rayuela', 'Novela experimental del escritor argentino Julio Cortázar', 18.99, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Relacionar productos con categorías (tabla intermedia)
INSERT INTO product_categories (product_id, category_id) VALUES
(1, 1), -- MacBook Pro -> Laptops
(2, 2), -- iPhone 15 Pro -> Smartphones
(3, 3), -- Cien años de soledad -> Libros
(4, 1), -- Dell XPS 13 -> Laptops
(5, 2), -- Samsung Galaxy S24 -> Smartphones
(6, 3), -- El Quijote -> Libros
(7, 4), -- iPad Pro -> Tablets
(8, 5), -- Sony WH-1000XM5 -> Audio
(9, 1), -- ThinkPad X1 Carbon -> Laptops
(10, 3); -- Rayuela -> Libros

-- Insertar atributos para las laptops
INSERT INTO attributes (name, description, product_id, is_active, created_at, updated_at) VALUES
('Procesador', 'Apple M2 Pro', 1, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('RAM', '16 GB', 1, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Almacenamiento', '512 GB SSD', 1, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Tamaño Pantalla', '14 pulgadas', 1, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Sistema Operativo', 'macOS', 1, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Peso', '1.6 kg', 1, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('Procesador', 'Intel Core i7-1165G7', 4, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('RAM', '16 GB LPDDR4x', 4, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Almacenamiento', '512 GB PCIe NVMe SSD', 4, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Tamaño Pantalla', '13.4 pulgadas', 4, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Sistema Operativo', 'Windows 11', 4, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Peso', '1.27 kg', 4, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('Procesador', 'Intel Core i7-1270P', 9, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('RAM', '32 GB LPDDR5', 9, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Almacenamiento', '1 TB PCIe Gen4 SSD', 9, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Tamaño Pantalla', '14 pulgadas', 9, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Sistema Operativo', 'Windows 11 Pro', 9, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Peso', '1.12 kg', 9, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insertar atributos para smartphones
INSERT INTO attributes (name, description, product_id, is_active, created_at, updated_at) VALUES
('Procesador', 'Apple A17 Pro', 2, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('RAM', '8 GB', 2, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Almacenamiento', '128 GB', 2, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Tamaño Pantalla', '6.1 pulgadas', 2, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Sistema Operativo', 'iOS 17', 2, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Cámara Principal', '48 MP', 2, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Batería', '3274 mAh', 2, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('Procesador', 'Snapdragon 8 Gen 3', 5, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('RAM', '12 GB', 5, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Almacenamiento', '256 GB', 5, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Tamaño Pantalla', '6.2 pulgadas', 5, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Sistema Operativo', 'Android 14', 5, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Cámara Principal', '50 MP', 5, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Batería', '4000 mAh', 5, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insertar atributos para libros
INSERT INTO attributes (name, description, product_id, is_active, created_at, updated_at) VALUES
('Autor', 'Gabriel García Márquez', 3, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Editorial', 'Editorial Sudamericana', 3, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('ISBN', '978-0307474728', 3, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Páginas', '417', 3, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Idioma', 'Español', 3, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Fecha Publicación', '1967', 3, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('Autor', 'Miguel de Cervantes', 6, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Editorial', 'Real Academia Española', 6, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('ISBN', '978-8420412146', 6, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Páginas', '1376', 6, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Idioma', 'Español', 6, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Fecha Publicación', '1605', 6, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),

('Autor', 'Julio Cortázar', 10, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Editorial', 'Editorial Sudamericana', 10, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('ISBN', '978-8420471891', 10, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Páginas', '736', 10, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Idioma', 'Español', 10, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Fecha Publicación', '1963', 10, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insertar atributos para tablets
INSERT INTO attributes (name, description, product_id, is_active, created_at, updated_at) VALUES
('Procesador', 'Apple M2', 7, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('RAM', '8 GB', 7, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Almacenamiento', '128 GB', 7, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Tamaño Pantalla', '12.9 pulgadas', 7, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Sistema Operativo', 'iPadOS 17', 7, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Cámara Principal', '12 MP', 7, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Batería', '10,758 mAh', 7, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insertar atributos para audio
INSERT INTO attributes (name, description, product_id, is_active, created_at, updated_at) VALUES
('Tipo', 'Over-ear', 8, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Conectividad', 'Bluetooth 5.2', 8, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Cancelación Ruido', 'Sí', 8, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Batería', '30 horas', 8, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Peso', '250 g', 8, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Frecuencia', '4 Hz - 40 kHz', 8, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- Insertar imágenes de productos
INSERT INTO images (url, product_id, is_active, created_at, updated_at) VALUES
('https://images.pexels.com/photos/18105/pexels-photo.jpg', 1, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('https://images.pexels.com/photos/18105/pexels-photo.jpg', 1, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('https://images.pexels.com/photos/404280/pexels-photo-404280.jpeg', 2, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('https://images.pexels.com/photos/404280/pexels-photo-404280.jpeg', 2, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('https://images.pexels.com/photos/256541/pexels-photo-256541.jpeg', 3, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('https://images.pexels.com/photos/18105/pexels-photo.jpg', 4, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('https://images.pexels.com/photos/404280/pexels-photo-404280.jpeg', 5, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('https://images.pexels.com/photos/256541/pexels-photo-256541.jpeg', 6, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('https://images.pexels.com/photos/1334597/pexels-photo-1334597.jpeg', 7, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('https://images.pexels.com/photos/3394650/pexels-photo-3394650.jpeg', 8, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('https://images.pexels.com/photos/18105/pexels-photo.jpg', 9, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('https://images.pexels.com/photos/256541/pexels-photo-256541.jpeg', 10, true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);