
drop table product;
create table product (
	pid 		number(8),
	pname		varchar2(40),
	quantity	number(8),
	price 		number(8)	
);

-- �⺻Ű [ pid ]
alter table product add constraint product_pid_pk primary key(pid);

-- ����ũ [ pname ]
alter table product add constraint product_pname_unique UNIQUE (pname);

-- NOT NULL [ pname, quantity, price ]
alter table product modify pname constraint product_pname_nn not null;
alter table product modify quantity constraint product_quantity_nn not null;
alter table product modify price constraint product_price_nn not null;


-- ������ ����
drop sequence product_pid_seq;
create sequence product_pid_seq;

--commit;