-- Create table if they don't exist
CREATE TABLE
  IF NOT EXISTS customer (
    id UUID NOT NULL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
  );

CREATE TABLE
  IF NOT EXISTS admin (
    id UUID NOT NULL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL
  );

CREATE TABLE
  IF NOT EXISTS review (
    id UUID NOT NULL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    score BIGINT NOT NULL,
    content VARCHAR(255) NOT NULL,
    CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES customer (id),
    created_at TIMESTAMP NOT NULL
  );

CREATE TABLE
  IF NOT EXISTS job (
    id UUID NOT NULL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(255) NOT NULL,
    CONSTRAINT fk_customer_id FOREIGN KEY (customer_id) REFERENCES customer (id),
    created_at TIMESTAMP NOT NULL,
    completed BOOLEAN NOT NULL
  );

------------------------------------
-- Insert data for Customer
INSERT INTO
  customer (
    id,
    first_name,
    last_name,
    email,
    password,
    created_at
  )
VALUES
  (
    '65c2b5c5-572b-4462-ade8-d827754dd9cd',
    'Simon',
    'Jönsson',
    'smn@ths.nu',
    '$2a$10$GstuLSM/Gmiiie2NiCJ0UuxGzsoonlU6g8VEQqqD.2qcvFlpCFs9W',
    '2025-05-01 13:55:20.004188'
  ),
  (
    '576b035a-decb-438a-b9e2-e9c691fad8df',
    'Mange',
    'Karlsson',
    'mangek@gmail.com',
    '$2a$10$nrhcQefnRL4njNQCOOJHaO5iOjItJ.qAVUhuLJjtV6m6vXajP0PRW',
    '2025-05-01 13:55:20.004188'
  ),
  (
    '2b0b3282-a298-4153-8695-0319d31b436b',
    'Ola',
    'Konradsson',
    'KoolaOla@gmail.com',
    '$2a$10$osN6pzGvzxf9g5AFEoJGJO78PdFFJvAD0CXJKl6Q9B90cBpc2aieG',
    '2025-05-01 13:55:20.004188'
  );

-- Insert data for Admin
INSERT INTO
  admin (
    id,
    first_name,
    last_name,
    email,
    password,
    created_at
  )
VALUES
  (
    '26ecf0ae-491e-4087-bb03-1745fe9fba01',
    'Thony',
    'Jönsson',
    'thony@ths.nu',
    '$2a$10$GstuLSM/Gmiiie2NiCJ0UuxGzsoonlU6g8VEQqqD.2qcvFlpCFs9W',
    '2025-05-01 13:55:20.004188'
  );

-- Insert data for review
INSERT INTO
  review (
    id,
    title,
    score,
    content,
    customer_id,
    created_at
  )
VALUES
  (
    '7716927c-a2b8-458d-96da-22609fa3c65b',
    'Bra service!',
    4,
    'Väldigt nöjd med servicen! :)',
    '65c2b5c5-572b-4462-ade8-d827754dd9cd',
    '2025-05-01 13:55:20.004188'
  ),
  (
    '683eb3c4-b693-4eac-ba99-ae492584d1d4',
    'Hade anställt igen!',
    5,
    'Kommer att anlita Thonys Hushållsservice igen.',
    '576b035a-decb-438a-b9e2-e9c691fad8df',
    '2025-05-01 13:55:20.004188'
  );

-- Insert data for job
INSERT INTO
  job (id, title, content, customer_id, created_at, completed)
VALUES
  (
    '087e6a14-3794-4198-baf0-41db7ea22003',
    'Min diskmaskin fungerar inte!',
    'Min diskmaskin vägrar att starta!!!',
    '65c2b5c5-572b-4462-ade8-d827754dd9cd',
    '2025-05-01 13:55:20.004188',
    false
  ),
  (
    '6c41c32f-e9bf-4365-aef4-116e9cec6386',
    'Min diskmaskin fungerar fortfarande inte!',
    'Min diskmaskin vägrar att starta!!! Jag behöver akut hjälp!',
    '65c2b5c5-572b-4462-ade8-d827754dd9cd',
    '2025-05-01 13:55:20.004188',
    false
  ),
  (
    '40c67129-8e4f-485a-9e85-a1ba05db264a',
    'Behöver en ny tvättmaskin!',
    'Den gamla gick sönder och behöver en ny!',
    '2b0b3282-a298-4153-8695-0319d31b436b',
    '2025-05-01 13:55:20.004188',
    true
  );