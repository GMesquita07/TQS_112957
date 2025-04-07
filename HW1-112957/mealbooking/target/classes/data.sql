-- Cantinas
INSERT INTO restaurant (id, name, location) VALUES (1, 'Cantina Central', 'Campus Norte');
INSERT INTO restaurant (id, name, location) VALUES (2, 'Cantina de Santiago', 'Campus Sul');

-- Refeições (hoje)
-- Cantina 1 - Cantina Central
INSERT INTO meal (description, date, type, restaurant_id) VALUES ('Bife com batatas', CURRENT_DATE, 'ALMOCO', 1);
INSERT INTO meal (description, date, type, restaurant_id) VALUES ('Sopa de legumes e arroz de pato', CURRENT_DATE, 'JANTAR', 1);

-- Cantina 2 - Cantina de Santiago
INSERT INTO meal (description, date, type, restaurant_id) VALUES ('Frango assado com esparguete', CURRENT_DATE, 'ALMOCO', 2);
INSERT INTO meal (description, date, type, restaurant_id) VALUES ('Lasanha vegetariana', CURRENT_DATE, 'JANTAR', 2);

-- Exemplo para 2025-04-05
INSERT INTO meal (description, type, date, restaurant_id) VALUES
('Arroz de pato', 'ALMOCO', '2025-04-05', 1),
('Frango grelhado', 'JANTAR', '2025-04-05', 1);
