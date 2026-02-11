
DROP TABLE IF EXISTS public.pc CASCADE;
DROP TABLE IF EXISTS public.laptop CASCADE;
DROP TABLE IF EXISTS public.printer CASCADE;
DROP TABLE IF EXISTS public.product CASCADE;



CREATE TABLE product (
    maker        varchar(10)  NOT NULL,
    model        varchar(50)  NOT NULL,
    product_type         varchar(10)  NOT NULL,
    CONSTRAINT product_pk PRIMARY KEY (model),
    CONSTRAINT product_type_chk CHECK ( product_type IN ('PC','Laptop','Printer'))
);



CREATE TABLE pc (
    code         integer      NOT NULL,
    model        varchar(50)  NOT NULL,
    speed        smallint     NOT NULL,
    ram          smallint     NOT NULL,
    hd           real         NOT NULL,
    cd           varchar(10)  NOT NULL,   
    price        numeric(10,2) NOT NULL,
    CONSTRAINT pc_pk PRIMARY KEY (code),
    CONSTRAINT pc_model_fk FOREIGN KEY (model) REFERENCES public.product(model),
    CONSTRAINT pc_speed_chk CHECK (speed > 0),
    CONSTRAINT pc_ram_chk   CHECK (ram > 0),
    CONSTRAINT pc_hd_chk    CHECK (hd > 0),
    CONSTRAINT pc_price_chk CHECK (price >= 0)
);

CREATE TABLE laptop (
  code   integer NOT NULL,
  model  varchar(50) NOT NULL,
  speed  smallint NOT NULL,
  ram    integer NOT NULL,
  hd     real NOT NULL,
  cd numeric(4,1) NOT NULL,
  price  numeric(10,2) NOT NULL,
  CONSTRAINT laptop_pk PRIMARY KEY (code),
  CONSTRAINT laptop_model_fk FOREIGN KEY (model) REFERENCES public.product(model)
);

CREATE TABLE printer (
    code         integer       NOT NULL,
    model        varchar(50)   NOT NULL,
    color        char(1)       NOT NULL,
    type         varchar(10)   NOT NULL,
    price        numeric(10,2) NOT NULL,
    CONSTRAINT printer_pk PRIMARY KEY (code),
    CONSTRAINT printer_model_fk FOREIGN KEY (model) REFERENCES public.product(model),
    CONSTRAINT printer_color_chk CHECK (color IN ('y','n')),
    CONSTRAINT printer_type_chk  CHECK (type IN ('Laser','Jet','Matrix')),
    CONSTRAINT printer_price_chk CHECK (price >= 0)
);
    
    
    

INSERT INTO public.product(maker, model, product_type) VALUES

('intel',  'hp1',  'PC'),
('amd',    'hp2',  'PC'),
('dell',   'hp3',  'PC'),
('asus',   'hp4',  'PC'),
('lenovo', 'hp5',  'PC'),

('hyperx', 'lp1',  'Laptop'),
('acer',   'lp2',  'Laptop'),
('msi',    'lp3',  'Laptop'),
('apple',  'lp4',  'Laptop'),
('hp',     'lp5',  'Laptop'),

('epson',  'pr1',  'Printer'),
('canon',  'pr2',  'Printer'),
('hp',     'pr3',  'Printer'),
('xerox',  'pr4',  'Printer'),
('brother','pr5',  'Printer');


INSERT INTO public.laptop(code, model, speed, ram, hd, cd, price) VALUES
(101, 'lp1', 1000, 4000,  512, 14.0, 90000.00),
(102, 'lp2', 2000, 6000, 1024, 15.6, 120000.00),
(103, 'lp3', 3000, 8000, 2048, 17.0, 180000.00),
(104, 'lp4', 2500, 8000, 1024, 13.3, 130000.00),
(105, 'lp5', 3500, 16000, 4096, 16.0, 230000.00);

INSERT INTO public.pc(code, model, speed, ram, hd, cd, price) VALUES
(1, 'hp1', 1000, 16, 500, '4x', 95000.00),
(2, 'hp2', 2000, 32, 1000, '6x', 150000.00),
(3, 'hp3', 3000, 64, 2000, '8x', 200000.00),
(4, 'hp4', 2400, 16, 512, '8x', 120000.00),
(5, 'hp5', 3200, 32, 1500, '12x', 210000.00);


INSERT INTO public.printer(code, model, color, "type", price) VALUES
(201, 'pr1', 'y', 'Jet', 10000.00),
(202, 'pr2', 'y', 'Matrix', 12000.00),
(203, 'pr3', 'n', 'Laser', 18000.00),
(204, 'pr4', 'n', 'Laser', 25000.00),
(205, 'pr5', 'y', 'Jet', 22000.00);

SELECT column_name
FROM information_schema.columns
WHERE table_schema='public' AND table_name='product'
ORDER BY ordinal_position;






    
    

