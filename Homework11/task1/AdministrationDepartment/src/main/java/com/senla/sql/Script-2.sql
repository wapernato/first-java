BEGIN;


INSERT INTO hotel (name, city, stars) VALUES
  ('Riga Central Hotel', 'Riga', 4),
  ('Baltic Sea Resort', 'Jurmala', 5);


INSERT INTO room_type (stars, capacity, base_price) VALUES
  (3, 1, 55.00),
  (4, 2, 85.00),
  (4, 4, 130.00),
  (5, 2, 220.00);


INSERT INTO room (hotel_id, room_number, status) VALUES
  (1, '101', 'available'),
  (1, '102', 'available'),
  (1, '103', 'maintenance'),
  (1, '201', 'available'),
  (2, 'A11', 'available'),
  (2, 'A12', 'occupied'),
  (2, 'B21', 'available');


INSERT INTO guest (locale_tag, number, check_in, check_out) VALUES
  ('ru-RU', 'AA1234567', DATE '2026-02-20', DATE '2026-02-23'),
  ('lv-LV', 'BB7654321', DATE '2026-03-01', DATE '2026-03-05'),
  ('en-US', 'CC9876543', DATE '2026-02-25', DATE '2026-02-28');


INSERT INTO reservation (hotel_id, guest_id, check_in, check_out, status, total_amount, created_at) VALUES
  (1, 1, DATE '2026-02-20', DATE '2026-02-23', 'confirmed', 0, NOW()),
  (1, 2, DATE '2026-03-01', DATE '2026-03-05', 'new',       0, NOW()),
  (2, 3, DATE '2026-02-25', DATE '2026-02-28', 'confirmed', 0, NOW());


INSERT INTO reservation_room (reservation_id, room_id, price_per_night, guests_count) VALUES
  (1, 2, 90.00, 2),
  (2, 1, 60.00, 1),
  (2, 4, 200.00, 2),
  (3, 6, 95.00, 2);


INSERT INTO service (hotel_id, name, price) VALUES
  (1, 'Breakfast', 12.00),
  (1, 'Airport Transfer', 35.00),
  (1, 'Laundry', 8.00),
  (2, 'Breakfast', 15.00),
  (2, 'Spa Access', 40.00);


INSERT INTO service_order (reservation_id, service_id, qty, unit_price, status, ordered_at) VALUES
  (1, 1, 2, 12.00, 'paid',      NOW()),
  (1, 2, 1, 35.00, 'ordered',   NOW()),
  (2, 1, 1, 12.00, 'ordered',   NOW()),
  (2, 3, 3,  8.00, 'delivered', NOW()),
  (3, 5, 2, 40.00, 'paid',      NOW());

UPDATE reservation r
SET total_amount =
    COALESCE((
      SELECT SUM((r.check_out - r.check_in) * rr.price_per_night)
      FROM reservation_room rr
      WHERE rr.reservation_id = r.reservation_id
    ), 0)
  + COALESCE((
      SELECT SUM(so.qty * so.unit_price)
      FROM service_order so
      WHERE so.reservation_id = r.reservation_id
        AND so.status <> 'cancelled'
    ), 0);

COMMIT;