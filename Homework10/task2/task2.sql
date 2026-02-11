
SET search_path TO public;


SELECT 'product' AS t, count(*) FROM product
UNION ALL SELECT 'pc', count(*) FROM pc
UNION ALL SELECT 'laptop', count(*) FROM laptop
UNION ALL SELECT 'printer', count(*) FROM printer;

-- Task 1 --

select model
from pc p 
where price > 500;

-- Task 2 --

select maker
from product p 
where p.product_type = 'Printer';

-- Task 3 --

select model, ram, cd
from laptop l 
where l.price > 1000;

-- Task 4 --

select *
from printer p
where p.color = 'y';

-- Task 5 --

select model, cd, speed
from pc p
where (p.cd = '12x' or p.cd = '24x') and p.price < 1000;

-- Task 6 --

select distinct p.maker, l.speed
from product p
join laptop l on l.model = p.model 
where l.hd  >= 100;

-- Task 7 --

select p.model, pc.price
from product p
left join pc pc on p.model = pc.model
where p.maker = 'intel';

-- Task 8 --

select p.maker
from product p 
where p.product_type = 'PC' and p.product_type != 'Laptop';

-- Task 9 --

select p.maker
from product p 
join pc pc on pc.model = p.model
where p.product_type = 'PC' and pc.speed >= 2500;

-- Task 10 --

select p.model, pr.price
from product p 
join printer pr on p.model = pr.model 
where pr.price = (select max(price) from printer);

-- Task 11 --

select avg(pc.speed)
from pc pc;

-- Task 12 --

select avg(l.speed)
from laptop l 
where l.price > 150000;

-- Task 13 --

select avg(pc.speed) as avg_speed
from product p
join pc on pc.model = p.model
where p.maker = 'intel';

-- Task 14 --

SELECT
    speed,
    AVG(price) AS avg_price
FROM PC
GROUP BY speed;

-- Task 15	 --

select pc.hd
from pc pc
group by pc.hd
having count(*) >= 2;

-- Task 16 --

select 
p1.model as model_big,
p2.model as model_small,
p1.speed,
p1.price
from pc p1
join pc p2
on p1.speed = p2.speed
and p1.ram = p2.ram
and p1.model > p2.model;

-- Task 17 --

select l.model, l.speed, p.product_type 
from product p 
join laptop l on p.model = l.model
where l.speed < (select min(pc.speed) from pc);

-- Task 18 --

select p.maker, pr.price
from product p
join printer pr on p.model = pr.model
where pr.price = (select min(price) from printer);

-- Task 19 --

select p.maker, AVG(l.cd)
from product p
join laptop l on l.model = p.model 
group by p.maker;

-- Task 20 --

select p.maker, count(pc.model)
from product p
join pc pc on p.model = pc.model
group by p.maker  
having count(distinct pc.model) >= 3;

-- Task 21 --

SELECT p.maker, MAX(pc.price)
FROM product p
JOIN pc ON p.model = pc.model
GROUP BY p.maker;

-- Task 22 --

select pc.speed, AVG(pc.price)
from pc pc
where pc.speed > 600
group by pc.speed;

-- Task 23 --

select distinct p.maker
from product p
where exists (
  select 1
  from pc
  where pc.model = p.model and pc.speed >= 750
)
and exists (
  select 1
  from laptop
  where laptop.model = p.model and laptop.speed >= 750
);

-- Task 24 --

select model
from (
  select model, price from pc
  union all
  select model, price from laptop
  union all
  select model, price from printer
) t
where price = (
  select max(price)
  from (
    select price from pc
    union all
    select price from laptop
    union all
    select price from printer
  ) x
);

-- Task 25 --

select distinct p.maker
from product p
where p.product_type = 'printer'
and p.maker in (
  select p2.maker
  from product p2
  join pc on pc.model = p2.model
  where pc.ram = (select min(ram) from pc)
    and pc.speed = (
      select max(speed)
      from pc
      where ram = (select min(ram) from pc)
    )
);
























