CREATE TABLE IF NOT EXISTS sample_cash_flow_data (
   id BIGINT NOT NULL,
   income DOUBLE NOT NULL,
   outcome DOUBLE NOT NULL,
   date DATE
);
ALTER TABLE sample_cash_flow_data ADD CONSTRAINT pk__sample_cash_flow_data__id PRIMARY KEY(id);

CREATE TABLE IF NOT EXISTS sample_sales_in_stores_data (
   id BIGINT NOT NULL,
   morning DOUBLE NOT NULL,
   noon DOUBLE NOT NULL,
   afternoon DOUBLE NOT NULL,
   evening DOUBLE NOT NULL,
   midnight DOUBLE NOT NULL,
   night DOUBLE NOT NULL,
   sales_day DATE
);
ALTER TABLE sample_sales_in_stores_data ADD CONSTRAINT pk__sample_sales_in_stores_data__id PRIMARY KEY(id);

CREATE TABLE IF NOT EXISTS sample_sales_in_stores_global_data (
   id BIGINT NOT NULL,
   morning DOUBLE NOT NULL,
   noon DOUBLE NOT NULL,
   afternoon DOUBLE NOT NULL,
   evening DOUBLE NOT NULL,
   midnight DOUBLE NOT NULL,
   night DOUBLE NOT NULL,
   sales_day DATE
);
ALTER TABLE sample_sales_in_stores_global_data ADD CONSTRAINT pk__sample_sales_in_stores_global_data__id PRIMARY KEY(id);

CREATE TABLE IF NOT EXISTS sample_sales_per_employers_data (
   id BIGINT NOT NULL,
   sales_qty BIGINT NOT NULL,
   employers_name VARCHAR(30) NOT NULL
);
ALTER TABLE sample_sales_per_employers_data ADD CONSTRAINT pk__sample_sales_per_employers_data__id PRIMARY KEY(id);

CREATE TABLE IF NOT EXISTS sample_selling_data (
   id BIGINT NOT NULL,
   revenue_gross DOUBLE NOT NULL,
   revenue_net DOUBLE NOT NULL,
   total_sales DOUBLE NOT NULL,
   sales_day DATE NOT NULL,
   salesman_name VARCHAR(100) NOT NULL
);
ALTER TABLE sample_selling_data ADD CONSTRAINT pk__sample_selling_data__id PRIMARY KEY(id);

CREATE TABLE IF NOT EXISTS sample_visualisation_data (
   id BIGINT NOT NULL,
   group_ VARCHAR(30) NOT NULL,
   value DOUBLE NOT NULL
);
ALTER TABLE sample_visualisation_data ADD CONSTRAINT pk__sample_visualisation_data__id PRIMARY KEY(id);


INSERT INTO sample_cash_flow_data (id, income, outcome, date) VALUES (1, 230044.23, 20000.00, '2013-11-20');
INSERT INTO sample_cash_flow_data (id, income, outcome, date) VALUES (2, 13044.23, 100000.00, '2013-11-21');
INSERT INTO sample_cash_flow_data (id, income, outcome, date) VALUES (3, 270404.23, 230000.00, '2013-11-22');
INSERT INTO sample_cash_flow_data (id, income, outcome, date) VALUES (4, 3232.23, 270000.00, '2013-11-23');
INSERT INTO sample_cash_flow_data (id, income, outcome, date) VALUES (5, 2404.23, 20000.00, '2013-11-20');
INSERT INTO sample_cash_flow_data (id, income, outcome, date) VALUES (6, 10404.23, 100000.00, '2013-11-21');
INSERT INTO sample_cash_flow_data (id, income, outcome, date) VALUES (7, 2404.23, 23000.00, '2013-11-22');
INSERT INTO sample_cash_flow_data (id, income, outcome, date) VALUES (8, 3332.23, 27050.00, '2013-11-23');
INSERT INTO sample_cash_flow_data (id, income, outcome, date) VALUES (9, 52444.23, 200550.00, '2013-11-24');
INSERT INTO sample_cash_flow_data (id, income, outcome, date) VALUES (10, 61434.23, 102300.00, '2013-11-25');
INSERT INTO sample_cash_flow_data (id, income, outcome, date) VALUES (11, 624554.23, 23400.00, '2013-11-26');
INSERT INTO sample_cash_flow_data (id, income, outcome, date) VALUES (12, 732332.23, 25700.00, '2013-11-27');
INSERT INTO sample_cash_flow_data (id, income, outcome, date) VALUES (13, 152424.23, 22000.00, '2013-12-20');
INSERT INTO sample_cash_flow_data (id, income, outcome, date) VALUES (14, 16144.23, 1000.00, '2013-12-21');
INSERT INTO sample_cash_flow_data (id, income, outcome, date) VALUES (15, 162454.23, 23300.00, '2013-12-22');
INSERT INTO sample_cash_flow_data (id, income, outcome, date) VALUES (16, 1733542.23, 273400.00, '2013-12-23');
INSERT INTO sample_cash_flow_data (id, income, outcome, date) VALUES (17, 1524434.23, 204300.00, '2014-01-02');
INSERT INTO sample_cash_flow_data (id, income, outcome, date) VALUES (18, 116144.23, 11000.00, '2014-01-03');
INSERT INTO sample_cash_flow_data (id, income, outcome, date) VALUES (19, 126244.23, 22300.00, '2014-01-04');
INSERT INTO sample_cash_flow_data (id, income, outcome, date) VALUES (20, 173332.23, 27300.00, '2014-01-05');

INSERT INTO sample_sales_in_stores_data (id, morning, noon, afternoon, evening, midnight, night, sales_day) VALUES (1, 800, 600, 1000, 1200, 400, 200, '2013-11-20');
INSERT INTO sample_sales_in_stores_data (id, morning, noon, afternoon, evening, midnight, night, sales_day) VALUES (2, 900, 700, 1100, 1300, 500, 200, '2013-11-21');
INSERT INTO sample_sales_in_stores_data (id, morning, noon, afternoon, evening, midnight, night, sales_day) VALUES (3, 500, 400, 800, 900, 100, 200, '2013-11-21');
INSERT INTO sample_sales_in_stores_data (id, morning, noon, afternoon, evening, midnight, night, sales_day) VALUES (4, 500, 400, 800, 900, 100, 200, '2013-11-22');
INSERT INTO sample_sales_in_stores_data (id, morning, noon, afternoon, evening, midnight, night, sales_day) VALUES (5, 2000, 1600, 1700, 1400, 600, 300, '2013-11-23');
INSERT INTO sample_sales_in_stores_data (id, morning, noon, afternoon, evening, midnight, night, sales_day) VALUES (6, 2200, 1800, 1900, 1600, 800, 300, '2013-11-24');
INSERT INTO sample_sales_in_stores_data (id, morning, noon, afternoon, evening, midnight, night, sales_day) VALUES (7, 1600, 1300, 1200, 1100, 200, 400, '2013-11-25');
INSERT INTO sample_sales_in_stores_data (id, morning, noon, afternoon, evening, midnight, night, sales_day) VALUES (8, 1100, 1000, 900, 850, 400, 200, '2013-11-26');
INSERT INTO sample_sales_in_stores_data (id, morning, noon, afternoon, evening, midnight, night, sales_day) VALUES (9, 1200, 1100, 1000, 950, 700, 200, '2013-11-27');
INSERT INTO sample_sales_in_stores_data (id, morning, noon, afternoon, evening, midnight, night, sales_day) VALUES (10, 1200, 1100, 1000, 650, 700, 200, '2013-11-28');
INSERT INTO sample_sales_in_stores_data (id, morning, noon, afternoon, evening, midnight, night, sales_day) VALUES (11, 1100, 1300, 1100, 1050, 850, 200, '2013-11-29');
INSERT INTO sample_sales_in_stores_data (id, morning, noon, afternoon, evening, midnight, night, sales_day) VALUES (12, 1200, 1400, 1200, 1150, 950, 250, '2013-11-30');

INSERT INTO sample_sales_in_stores_global_data (id, morning, noon, afternoon, evening, midnight, night, sales_day) VALUES (1, 80000, 60000, 100000, 120000, 40000, 20000, '2013-11-20');
INSERT INTO sample_sales_in_stores_global_data (id, morning, noon, afternoon, evening, midnight, night, sales_day) VALUES (2, 90000, 70000, 110000, 130000, 50000, 20000, '2013-11-21');
INSERT INTO sample_sales_in_stores_global_data (id, morning, noon, afternoon, evening, midnight, night, sales_day) VALUES (3, 50000, 40000, 80000, 90000, 10000, 20000, '2013-11-21');
INSERT INTO sample_sales_in_stores_global_data (id, morning, noon, afternoon, evening, midnight, night, sales_day) VALUES (4, 50000, 40000, 80000, 90000, 10000, 20000, '2013-11-22');
INSERT INTO sample_sales_in_stores_global_data (id, morning, noon, afternoon, evening, midnight, night, sales_day) VALUES (5, 200000, 160000, 170000, 140000, 60000, 30000, '2013-11-23');
INSERT INTO sample_sales_in_stores_global_data (id, morning, noon, afternoon, evening, midnight, night, sales_day) VALUES (6, 220000, 180000, 190000, 160000, 80000, 30000, '2013-11-24');
INSERT INTO sample_sales_in_stores_global_data (id, morning, noon, afternoon, evening, midnight, night, sales_day) VALUES (7, 160000, 130000, 120000, 110000, 20000, 40000, '2013-11-25');
INSERT INTO sample_sales_in_stores_global_data (id, morning, noon, afternoon, evening, midnight, night, sales_day) VALUES (8, 110000, 100000, 90000, 85000, 40000, 20000, '2013-11-26');
INSERT INTO sample_sales_in_stores_global_data (id, morning, noon, afternoon, evening, midnight, night, sales_day) VALUES (9, 120000, 110000, 100000, 95000, 70000, 20000, '2013-11-27');
INSERT INTO sample_sales_in_stores_global_data (id, morning, noon, afternoon, evening, midnight, night, sales_day) VALUES (10, 120000, 110000, 100000, 65000, 70000, 20000, '2013-11-28');
INSERT INTO sample_sales_in_stores_global_data (id, morning, noon, afternoon, evening, midnight, night, sales_day) VALUES (11, 110000, 130000, 110000, 105000, 85000, 20000, '2013-11-29');
INSERT INTO sample_sales_in_stores_global_data (id, morning, noon, afternoon, evening, midnight, night, sales_day) VALUES (12, 120000, 140000, 120000, 115000, 95000, 25000, '2013-11-30');

INSERT INTO sample_sales_per_employers_data (id, sales_qty, employers_name) VALUES (1, 234, 'Jon B.');
INSERT INTO sample_sales_per_employers_data (id, sales_qty, employers_name) VALUES (2, 343, 'Mary A.');
INSERT INTO sample_sales_per_employers_data (id, sales_qty, employers_name) VALUES (3, 256, 'Rachel G.');
INSERT INTO sample_sales_per_employers_data (id, sales_qty, employers_name) VALUES (4, 278, 'James E.');
INSERT INTO sample_sales_per_employers_data (id, sales_qty, employers_name) VALUES (5, 543, 'Mark T.');
INSERT INTO sample_sales_per_employers_data (id, sales_qty, employers_name) VALUES (6, 123, 'Anna U.');

INSERT INTO sample_selling_data (id, revenue_gross, revenue_net, total_sales, sales_day, salesman_name) VALUES (1, 2344.23, 2000.00, 23, '2013-11-20', 'Jon B.');
INSERT INTO sample_selling_data (id, revenue_gross, revenue_net, total_sales, sales_day, salesman_name) VALUES (2, 1344.23, 1000.00, 14, '2013-11-21', 'Jean K.');
INSERT INTO sample_selling_data (id, revenue_gross, revenue_net, total_sales, sales_day, salesman_name) VALUES (3, 2744.23, 2300.00, 25, '2013-11-22', 'Michel E.');
INSERT INTO sample_selling_data (id, revenue_gross, revenue_net, total_sales, sales_day, salesman_name) VALUES (4, 3232.23, 2700.00, 28, '2013-11-23', 'David K.');
INSERT INTO sample_selling_data (id, revenue_gross, revenue_net, total_sales, sales_day, salesman_name) VALUES (5, 244.23, 2000.00, 2, '2013-11-20', 'Mary A.');
INSERT INTO sample_selling_data (id, revenue_gross, revenue_net, total_sales, sales_day, salesman_name) VALUES (6, 144.23, 1000.00, 1, '2013-11-21', 'Nathaniel T.');
INSERT INTO sample_selling_data (id, revenue_gross, revenue_net, total_sales, sales_day, salesman_name) VALUES (7, 244.23, 2300.00, 2, '2013-11-22', 'Rachel G.');
INSERT INTO sample_selling_data (id, revenue_gross, revenue_net, total_sales, sales_day, salesman_name) VALUES (8, 332.23, 2700.00, 2, '2013-11-23', 'Jim D.');
INSERT INTO sample_selling_data (id, revenue_gross, revenue_net, total_sales, sales_day, salesman_name) VALUES (9, 5244.23, 2000.00, 32, '2013-11-24', 'Adele R.');
INSERT INTO sample_selling_data (id, revenue_gross, revenue_net, total_sales, sales_day, salesman_name) VALUES (10, 6144.23, 1000.00, 31, '2013-11-25', 'Alexandra A.');
INSERT INTO sample_selling_data (id, revenue_gross, revenue_net, total_sales, sales_day, salesman_name) VALUES (11, 6244.23, 2300.00, 32, '2013-11-26', 'Andrea L.');
INSERT INTO sample_selling_data (id, revenue_gross, revenue_net, total_sales, sales_day, salesman_name) VALUES (12, 7332.23, 2700.00, 32, '2013-11-27', 'Anna Z.');
INSERT INTO sample_selling_data (id, revenue_gross, revenue_net, total_sales, sales_day, salesman_name) VALUES (13, 15244.23, 2000.00, 321, '2013-12-20', 'Max B.');
INSERT INTO sample_selling_data (id, revenue_gross, revenue_net, total_sales, sales_day, salesman_name) VALUES (14, 16144.23, 1000.00, 311, '2013-12-21', 'Jeremy C.');
INSERT INTO sample_selling_data (id, revenue_gross, revenue_net, total_sales, sales_day, salesman_name) VALUES (15, 16244.23, 2300.00, 321, '2013-12-22', 'Dominic E.');
INSERT INTO sample_selling_data (id, revenue_gross, revenue_net, total_sales, sales_day, salesman_name) VALUES (16, 17332.23, 2700.00, 321, '2013-12-23', 'Ken D.');
INSERT INTO sample_selling_data (id, revenue_gross, revenue_net, total_sales, sales_day, salesman_name) VALUES (17, 15244.23, 2000.00, 325, '2014-01-02', 'Grace K.');
INSERT INTO sample_selling_data (id, revenue_gross, revenue_net, total_sales, sales_day, salesman_name) VALUES (18, 16144.23, 1000.00, 317, '2014-01-03', 'Alice R.');
INSERT INTO sample_selling_data (id, revenue_gross, revenue_net, total_sales, sales_day, salesman_name) VALUES (19, 16244.23, 2300.00, 329, '2014-01-04', 'Claudia U.');
INSERT INTO sample_selling_data (id, revenue_gross, revenue_net, total_sales, sales_day, salesman_name) VALUES (20, 17332.23, 2700.00, 321, '2014-01-05', 'Gabriella J.');

INSERT INTO sample_visualisation_data (id, group_, value) VALUES (1, 'A', -29.7660);
INSERT INTO sample_visualisation_data (id, group_, value) VALUES (2, 'B', 68.0000);
INSERT INTO sample_visualisation_data (id, group_, value) VALUES (3, 'C', 32.8078);
INSERT INTO sample_visualisation_data (id, group_, value) VALUES (4, 'D', 166.4595);
INSERT INTO sample_visualisation_data (id, group_, value) VALUES (5, 'E', 82.1943);
INSERT INTO sample_visualisation_data (id, group_, value) VALUES (6, 'F', -98.0798);
INSERT INTO sample_visualisation_data (id, group_, value) VALUES (7, 'G', -13.9257);
INSERT INTO sample_visualisation_data (id, group_, value) VALUES (8, 'H', -5.1387);