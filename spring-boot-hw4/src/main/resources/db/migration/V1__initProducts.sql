DROP TABLE IF EXISTS products;
CREATE TABLE products (
                          id SERIAL PRIMARY KEY,
                          title VARCHAR(255) UNIQUE NOT NULL,
                          description TEXT,
                          price BIGINT
);

INSERT INTO products (title, description, price)
VALUES
('iPhone 12 Pro 256GB', 'Новая эра. Новые скорости.', 109990),
('iPhone 12 mini 128GB', 'Во-первых, это быстро.', 74990),
('iPhone 12 128GB', 'Легко держать. Трудно отвести взгляд.', 84990),
('iPhone XR 128GB', 'Выбери свой цвет.', 52990),
('Watch S3 42mm', 'Ваш личный тренер и врач.', 18990),
('Watch S5 Nike+ 44mm', 'Таких часов вы еще не видели.', 24075),
('Watch SE 44mm', 'Мало не покажут.', 27490),
('Watch S6 44mm', 'Технологие из будущего. Вам на пользу.', 39490),
('iPad mini 7.9 WF+CL 256Gb', 'Совсем как большой.', 59990),
('iPad Air 10.9 Wi-Fi 256GB', 'Великолепный дисплей Liquid Retina.', 69990),
('iPad 10.2 Wi-Fi 128GB', 'Легкий путь к большим возможностям.', 38990),
('iPad Pro 11" (2020) 512GB Wi-Fi Cell', 'Ваш будущий компьютер теперь не компьютер.', 108990),
('MacBook Air 13 M1/8/512', 'С новой силой.', 124990),
('MacBook Pro 13 M1/8/512', 'Серьёзно прибавил.', 144990),
('Mac Mini M1/8/512', 'Обновлён и заряжен.', 89990),
('AirPods w/Wireless Charg.Case', 'Это не просто еще одни беспроводные наушники.', 14990),
('AirPods Pro with Wireless Case', 'Когда звук создаёт тишину.', 18990),
('AirPods Max', 'Поверьте ушам своим.', 62990),
('TV 4K 64Gb', 'Эпохальная премьера.', 16990),
('iPod Touch 256Gb', '1000 песен у Вас в кармане.', 37390);