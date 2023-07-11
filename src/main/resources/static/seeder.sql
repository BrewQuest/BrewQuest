use BrewQuest_db;
INSERT INTO users (first_name, last_name, birth_date, zipcode, username, email, password, total_breweries)
VALUES
    ('Michael', 'Smith', '1980-01-01', 90210, 'michaelsmith01', 'michael@example.com', '$2a$12$SeEcwNyruDM/iZsa1dqXfu7s/D./vTHxYNEgkJjh9tBh3vnWOdbcq', 5),
    ('Jennifer', 'Jones', '1985-05-10', 11235, 'jenniferjones1985', 'jennifer@example.com', '$2a$12$MIDhaXlMhCwxtPYlSLPTpORr.Q8pTd2KyoaCXeujUIbJrhTxhTTWy', 10),
    ('David', 'Williams', '1990-12-15', 12345, 'DavidTheMAn', 'david@example.com', '$2a$12$x8cw4GfFKXkenFCfXkqxbeqKJbvH7XbZox8eJsy./AKOf070VxWSC', 3),
    ('Jennifer', 'Vasquez', '1990-12-15', 12345, 'Jennifer90', 'Jennifer@example.com', '$2a$12$x8cw4GfFKXkenFCfXkqxbeqKJbvH7XbZox8eJsy./AKOf070VxWSC', 20),
    ('Ammar', 'Morton', '1998-4-16', 12345, 'Morton98', 'Ammar@example.com', '$2a$12$x8cw4GfFKXkenFCfXkqxbeqKJbvH7XbZox8eJsy./AKOf070VxWSC', 3),
    ('Maggie', 'Kidd', '2000-1-04', 12345, 'KiddMag', 'david@example.com', '$2a$12$x8cw4GfFKXkenFCfXkqxbeqKJbvH7XbZox8eJsy./AKOf070VxWSC', 3),
    ('Zuzanna', 'Gilbert', '1976-12-15', 12345, 'ZuzannaGil76', 'david@example.com', '$2a$12$x8cw4GfFKXkenFCfXkqxbeqKJbvH7XbZox8eJsy./AKOf070VxWSC', 3),
    ('Maddie', 'Johnston', '1990-12-15', 12345, 'davidwilliams', 'david@example.com', '$2a$12$x8cw4GfFKXkenFCfXkqxbeqKJbvH7XbZox8eJsy./AKOf070VxWSC', 3),
    ('Jensen', 'Cain', '1990-12-15', 12345, 'davidwilliams', 'david@example.com', '$2a$12$x8cw4GfFKXkenFCfXkqxbeqKJbvH7XbZox8eJsy./AKOf070VxWSC', 3),
    ('Cai', 'Gross', '1990-12-15', 12345, 'davidwilliams', 'david@example.com', '$2a$12$x8cw4GfFKXkenFCfXkqxbeqKJbvH7XbZox8eJsy./AKOf070VxWSC', 3),
    ('Wade', 'Knight', '1990-12-15', 12345, 'davidwilliams', 'david@example.com', '$2a$12$x8cw4GfFKXkenFCfXkqxbeqKJbvH7XbZox8eJsy./AKOf070VxWSC', 3),
    ('Mason', 'Lozano', '1990-12-15', 12345, 'davidwilliams', 'david@example.com', '$2a$12$x8cw4GfFKXkenFCfXkqxbeqKJbvH7XbZox8eJsy./AKOf070VxWSC', 3),
    ('Cynthia', 'Boyer', '1990-12-15', 12345, 'davidwilliams', 'david@example.com', '$2a$12$x8cw4GfFKXkenFCfXkqxbeqKJbvH7XbZox8eJsy./AKOf070VxWSC', 3),
    ('Monica', 'Lam', '1990-12-15', 12345, 'davidwilliams', 'david@example.com', '$2a$12$x8cw4GfFKXkenFCfXkqxbeqKJbvH7XbZox8eJsy./AKOf070VxWSC', 3),
    ('Katy', 'Sawyer', '1990-12-15', 12345, 'davidwilliams', 'david@example.com', '$2a$12$x8cw4GfFKXkenFCfXkqxbeqKJbvH7XbZox8eJsy./AKOf070VxWSC', 3),
    ('Philippa', 'Barton', '1990-12-15', 12345, 'davidwilliams', 'david@example.com', '$2a$12$x8cw4GfFKXkenFCfXkqxbeqKJbvH7XbZox8eJsy./AKOf070VxWSC', 3),
    ('Noor', 'Ferrell', '1990-12-15', 12345, 'davidwilliams', 'david@example.com', '$2a$12$x8cw4GfFKXkenFCfXkqxbeqKJbvH7XbZox8eJsy./AKOf070VxWSC', 3),


INSERT INTO drivers (drivers_license_num, license_plate_num, car_make, car_model, total_passengers, user_id)
VALUES
    ('DL123456', 'ABC123', 'Toyota', 'Camry', 4, 1),
    ('DL987654', 'XYZ789', 'Honda', 'Accord', 3, 2),
    ('DL456789', 'DEF456', 'Ford', 'Mustang', 2, 3);

INSERT INTO favorites (brewery_id, user_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3);

INSERT INTO friends (user_id, friend_user_id)
VALUES
    (1, 2),
    (1, 3),
    (2, 1);

INSERT INTO reviews (brewery_id, rating, description, passengers, user_id)
VALUES
    ('BREW123', 5, 'Great beer!', 2, 1),
    ('BREW456', 4, 'Nice atmosphere.', 4, 2),
    ('BREW22', 3, 'Average experience.', 1, 3);

INSERT INTO wishlists (brewery_id, user_id)
VALUES
    (4, 1),
    (5, 2),
    (6, 3);
