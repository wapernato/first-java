
BEGIN;

DROP TABLE IF EXISTS service_order CASCADE;
DROP TABLE IF EXISTS service CASCADE;
DROP TABLE IF EXISTS reservation_room CASCADE;
DROP TABLE IF EXISTS reservation CASCADE;
DROP TABLE IF EXISTS room CASCADE;
DROP TABLE IF EXISTS room_type CASCADE;
DROP TABLE IF EXISTS guest CASCADE;
DROP TABLE IF EXISTS hotel CASCADE;

CREATE TABLE hotel (
  hotel_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  name     VARCHAR(200) NOT NULL,
  city     VARCHAR(120) NOT NULL,
  stars    INT NOT NULL CHECK (stars BETWEEN 1 AND 5)
);

CREATE TABLE room_type (
  room_type_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  stars        INT NOT NULL CHECK (stars BETWEEN 1 AND 5),
  capacity     INT NOT NULL CHECK (capacity > 0),
  base_price   NUMERIC(12,2) NOT NULL CHECK (base_price >= 0)
);

CREATE TABLE room (
  room_id      INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  hotel_id     INT NOT NULL REFERENCES hotel(hotel_id) ON DELETE CASCADE,
  room_number  VARCHAR(20) NOT NULL,
  status       VARCHAR(30) NOT NULL CHECK (status IN ('available','occupied','maintenance','out_of_service')),
  CONSTRAINT uq_room_hotel_number UNIQUE (hotel_id, room_number)
);


CREATE TABLE guest (
  guest_id    INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  locale_tag  VARCHAR(20),         
  number      VARCHAR(60),         
  check_in    DATE,
  check_out   DATE,
  CONSTRAINT chk_guest_dates CHECK (check_out IS NULL OR check_in IS NULL OR check_out > check_in)
);

CREATE TABLE reservation (
  reservation_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  hotel_id       INT NOT NULL REFERENCES hotel(hotel_id) ON DELETE RESTRICT,
  guest_id       INT NOT NULL REFERENCES guest(guest_id) ON DELETE RESTRICT,
  check_in       DATE NOT NULL,
  check_out      DATE NOT NULL,
  status         VARCHAR(30) NOT NULL CHECK (status IN ('new','confirmed','checked_in','checked_out','cancelled','no_show')),
  total_amount   NUMERIC(12,2) NOT NULL DEFAULT 0 CHECK (total_amount >= 0),
  created_at     TIMESTAMP NOT NULL DEFAULT NOW(),
  CONSTRAINT chk_reservation_dates CHECK (check_out > check_in)
);

CREATE TABLE reservation_room (
  reservation_id  INT NOT NULL REFERENCES reservation(reservation_id) ON DELETE CASCADE,
  room_id         INT NOT NULL REFERENCES room(room_id) ON DELETE RESTRICT,
  price_per_night NUMERIC(12,2) NOT NULL CHECK (price_per_night >= 0),
  guests_count    INT NOT NULL CHECK (guests_count > 0),
  PRIMARY KEY (reservation_id, room_id)
);

CREATE TABLE service (
  service_id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  hotel_id   INT NOT NULL REFERENCES hotel(hotel_id) ON DELETE CASCADE,
  name       VARCHAR(140) NOT NULL,
  price      NUMERIC(12,2) NOT NULL CHECK (price >= 0),
  CONSTRAINT uq_service_hotel_name UNIQUE (hotel_id, name)
);

CREATE TABLE service_order (
  order_id       INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
  reservation_id INT NOT NULL REFERENCES reservation(reservation_id) ON DELETE CASCADE,
  service_id     INT NOT NULL REFERENCES service(service_id) ON DELETE RESTRICT,
  qty            INT NOT NULL CHECK (qty > 0),
  unit_price     NUMERIC(12,2) NOT NULL CHECK (unit_price >= 0),
  status         VARCHAR(30) NOT NULL CHECK (status IN ('ordered','paid','cancelled','delivered')),
  ordered_at     TIMESTAMP NOT NULL DEFAULT NOW()
);


CREATE INDEX idx_room_hotel            ON room(hotel_id);
CREATE INDEX idx_reservation_hotel     ON reservation(hotel_id);
CREATE INDEX idx_reservation_guest     ON reservation(guest_id);
CREATE INDEX idx_reservation_room_room ON reservation_room(room_id);
CREATE INDEX idx_service_hotel         ON service(hotel_id);
CREATE INDEX idx_so_reservation        ON service_order(reservation_id);
CREATE INDEX idx_so_service            ON service_order(service_id);

COMMIT;


SELECT COUNT(*) FROM hotel;
SELECT COUNT(*) FROM room_type;
SELECT COUNT(*) FROM room;
SELECT COUNT(*) FROM guest;
SELECT COUNT(*) FROM reservation;
SELECT COUNT(*) FROM reservation_room;
SELECT COUNT(*) FROM service;
SELECT COUNT(*) FROM service_order;
