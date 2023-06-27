-- Insert data into the users table
INSERT INTO users (first_name, last_name, birth_date, zipcode, username, email, password, total_breweries)
VALUES
    ('John', 'Doe', '1990-01-01', 12345, 'johndoe', 'john@example.com', 'password1', 10),
    ('Jane', 'Smith', '1992-05-10', 54321, 'janesmith', 'jane@example.com', 'password2', 5),
    ('Mike', 'Johnson', '1985-12-15', 98765, 'mikejohnson', 'mike@example.com', 'password3', 8);

-- Insert data into the drivers table
INSERT INTO drivers (drivers_license_num, license_plate_num, car_make, car_model, total_passengers, user_id)
VALUES
    ('DL123456', 'ABC123', 'Toyota', 'Camry', 4, 1),
    ('DL987654', 'XYZ789', 'Honda', 'Accord', 3, 2),
    ('DL456789', 'DEF456', 'Ford', 'Mustang', 2, 3);

-- Insert data into the favorites table
INSERT INTO favorites (brewery_id, user_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3);

-- Insert data into the friends table
INSERT INTO friends (user_id, friend_user_id)
VALUES
    (1, 2),
    (1, 3),
    (2, 1);

-- Insert data into the reviews table
INSERT INTO reviews (brewery_id, rating, description, passengers, user_id)
VALUES
    ('BREW123', 5, 'Great beer!', 2, 1),
    ('BREW456', 4, 'Nice atmosphere.', 4, 2),
    ('BREW789', 3, 'Average experience.', 1, 3);

-- Insert data into the wishlists table
INSERT INTO wishlists (brewery_id, user_id)
VALUES
    (4, 1),
    (5, 2),
    (6, 3);
