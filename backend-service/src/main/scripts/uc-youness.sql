-- Users Table
CREATE TABLE users
(
    userId   SERIAL PRIMARY KEY,
    user_uid VARCHAR(15) not null,
    name     VARCHAR     not null
);

-- Cards Table
CREATE TABLE cards
(
    cardId       SERIAL PRIMARY KEY,
    cardUId      VARCHAR,
    userId       SERIAL  not null,
    FOREIGN KEY (userId) REFERENCES users (userId),
    expirable    BOOLEAN not null,
    expired_date DATE
);

-- Roles Table
CREATE TABLE roles
(
    roleId       SERIAL PRIMARY KEY,
    abbreviation VARCHAR(10) NOT NULL,
    designation  VARCHAR     NOT NULL,
    enabled      BOOLEAN     not null
);

-- ROLE USER relationship table
CREATE TABLE roles_users
(
    roleId SERIAL,
    userId SERIAL,
    FOREIGN KEY (userId) REFERENCES users (userId),
    FOREIGN KEY (roleId) REFERENCES roles (roleId),
    PRIMARY KEY (userId, roleId)
);

-- Building Table
CREATE TABLE buildings
(
    buildingId   SERIAL PRIMARY KEY,
    abbreviation VARCHAR(10) NOT NULL,
    designation  VARCHAR     NOT NULL,
    enabled      BOOLEAN     not null
);

-- Floor Table
CREATE TABLE floors
(
    floorId   SERIAL PRIMARY KEY,
    buildingId SERIAL,
    FOREIGN KEY (buildingId) REFERENCES buildings (buildingId),
    abbreviation VARCHAR(10) NOT NULL,
    designation  VARCHAR     NOT NULL,
    enabled      BOOLEAN     not null
);

-- Desks Table
CREATE TABLE desks
(
    deskId   SERIAL PRIMARY KEY,
    floorId SERIAL,
    FOREIGN KEY (floorId) REFERENCES floors (floorId),
    abbreviation VARCHAR(10) NOT NULL,
    designation  VARCHAR     NOT NULL,
    enabled      BOOLEAN     not null
);

-- permissions guard table
CREATE TABLE roles_desks
(
    roleId SERIAL,
    deskId SERIAL,
    FOREIGN KEY (deskId) REFERENCES desks (deskId),
    FOREIGN KEY (roleId) REFERENCES roles (roleId),
    PRIMARY KEY (deskId, roleId)
);

-- Desk Permissions
CREATE TABLE desk_permission
(
    cardId SERIAL,
    deskId SERIAL,
    FOREIGN KEY (deskId) REFERENCES desks (deskId),
    FOREIGN KEY (cardId) REFERENCES cards (cardId),
    PRIMARY KEY (deskId, cardId)
);

-- Equipment Table
CREATE TABLE equipment
(
    equipmentID SERIAL PRIMARY KEY,
    deskId SERIAL,
    FOREIGN KEY (deskId) REFERENCES desks (deskId),
    abbreviation VARCHAR(10) NOT NULL,
    designation  VARCHAR     NOT NULL,
    enabled      BOOLEAN     not null
);

-- Desk Permissions
CREATE TABLE equipment_permission
(
    cardId SERIAL,
    equipmentID SERIAL,
    FOREIGN KEY (equipmentID) REFERENCES equipment (equipmentID),
    FOREIGN KEY (cardId) REFERENCES cards (cardId),
    PRIMARY KEY (equipmentID, cardId)
);