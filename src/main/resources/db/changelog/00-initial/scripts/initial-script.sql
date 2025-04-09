-- Script to create tables for the Paw Paradise database

-- Sequences for table IDs
DROP SEQUENCE IF EXISTS seq_person_id;
CREATE SEQUENCE seq_person_id;

DROP SEQUENCE IF EXISTS seq_user_id;
CREATE SEQUENCE seq_user_id;

DROP SEQUENCE IF EXISTS seq_client_id;
CREATE SEQUENCE seq_client_id;

DROP SEQUENCE IF EXISTS seq_staff_id;
CREATE SEQUENCE seq_staff_id;

DROP SEQUENCE IF EXISTS seq_pet_id;
CREATE SEQUENCE seq_pet_id;

DROP SEQUENCE IF EXISTS seq_reservation_id;
CREATE SEQUENCE seq_reservation_id;

DROP SEQUENCE IF EXISTS seq_reservationdetail_id;
CREATE SEQUENCE seq_reservationdetail_id;

DROP SEQUENCE IF EXISTS seq_plan_id;
CREATE SEQUENCE seq_plan_id;

DROP SEQUENCE IF EXISTS seq_paymentmethod_id;
CREATE SEQUENCE seq_paymentmethod_id;

DROP SEQUENCE IF EXISTS seq_payment_id;
CREATE SEQUENCE seq_payment_id;

DROP SEQUENCE IF EXISTS seq_configuration_id;
CREATE SEQUENCE seq_configuration_id;

-- Table: Person
CREATE TABLE Person
(
    id         INT       DEFAULT nextval('seq_person_id') PRIMARY KEY,
    ci         VARCHAR(15)  NOT NULL,
    name       VARCHAR(100) NOT NULL,
    lastname   VARCHAR(100) NOT NULL,
    email      VARCHAR(100) NOT NULL,
    phone      VARCHAR(15),
    image      VARCHAR(100),
    gender     VARCHAR(10),
    birthdate  DATE,
    type       VARCHAR(10),
    address    VARCHAR(100),
    country    VARCHAR(100),
    role       VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE UNIQUE INDEX idx_person_email ON Person (email);

-- Table: User
CREATE TABLE Users
(
    id         INT       DEFAULT nextval('seq_user_id') PRIMARY KEY,
    username   VARCHAR(100) NOT NULL,
    email      VARCHAR(100) NOT NULL,
    password   VARCHAR(50)  NOT NULL,
    role       VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    person_id  INT          NOT NULL,
    CONSTRAINT fk_user_person FOREIGN KEY (person_id) REFERENCES Person (id)
);
CREATE UNIQUE INDEX idx_user_username ON Users (username);

-- Table: Client
CREATE TABLE Client
(
    id         INT       DEFAULT nextval('seq_client_id') PRIMARY KEY,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    person_id  INT NOT NULL,
    CONSTRAINT fk_client_person FOREIGN KEY (person_id) REFERENCES Person (id)
);

-- Table: Staff
CREATE TABLE Staff
(
    id         INT       DEFAULT nextval('seq_staff_id') PRIMARY KEY,
    profession VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    person_id  INT NOT NULL,
    CONSTRAINT fk_staff_person FOREIGN KEY (person_id) REFERENCES Person (id)
);

-- Table: Pet
CREATE TABLE Pet
(
    id         INT       DEFAULT nextval('seq_pet_id') PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    gender     VARCHAR(50),
    breed      VARCHAR(50),
    birthdate  DATE,
    image      VARCHAR(100),
    client_id  INT          NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_pet_client FOREIGN KEY (client_id) REFERENCES Client (id)
);

-- Table: Reservation
CREATE TABLE Reservation
(
    id             INT       DEFAULT nextval('seq_reservation_id') PRIMARY KEY,
    total          FLOAT NOT NULL,
    status         VARCHAR(100),
    payment_status VARCHAR(100),
    due            FLOAT,
    payment_total  FLOAT,
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- Table: Plan
CREATE TABLE Plan
(
    id         INT       DEFAULT nextval('seq_plan_id') PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    price      FLOAT        NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


-- Table: ReservationDetail
CREATE TABLE ReservationDetail
(
    id               INT       DEFAULT nextval('seq_reservationdetail_id') PRIMARY KEY,
    reservation_date DATE NOT NULL,
    presence_status  INT,
    cost             DECIMAL,
    pet_id           INT  NOT NULL,
    plan_id          INT  NOT NULL,
    reservation_id   INT  NOT NULL,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reservationdetail_pet FOREIGN KEY (pet_id) REFERENCES Pet (id),
    CONSTRAINT fk_reservationdetail_plan FOREIGN KEY (plan_id) REFERENCES Plan (id),
    CONSTRAINT fk_reservationdetail_reservation FOREIGN KEY (reservation_id) REFERENCES Reservation (id)
);

-- Table: PaymentMethod
CREATE TABLE PaymentMethod
(
    id         INT       DEFAULT nextval('seq_paymentmethod_id') PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table: Payment
CREATE TABLE Payment
(
    id                INT       DEFAULT nextval('seq_payment_id') PRIMARY KEY,
    total_mount       FLOAT NOT NULL,
    amount_paid       FLOAT NOT NULL,
    change            FLOAT,
    created_at        TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    reservation_id    INT   NOT NULL,
    payment_method_id INT   NOT NULL,
    CONSTRAINT fk_payment_reservation FOREIGN KEY (reservation_id) REFERENCES Reservation (id),
    CONSTRAINT fk_payment_payment_method FOREIGN KEY (payment_method_id) REFERENCES PaymentMethod (id)
);

-- Table: Configuration
CREATE TABLE Configuration
(
    id         INT       DEFAULT nextval('seq_configuration_id') PRIMARY KEY,
    capacity   INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
