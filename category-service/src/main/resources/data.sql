INSERT INTO categories (id, name, description) VALUES
(1, 'Điện thoại', 'Danh mục điện thoại di động'),
(2, 'Laptop', 'Danh mục máy tính xách tay'),
(3, 'Phụ kiện', 'Danh mục phụ kiện điện tử')
ON DUPLICATE KEY UPDATE name = VALUES(name);
