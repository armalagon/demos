
-- ===============================================================================================
-- Author: Armando Alaniz
-- Purpose: create a db to keep track of employees attendance to physical facilities
-- ===============================================================================================
drop table if exists attendance cascade;
drop table if exists client cascade;
drop table if exists employee cascade;
drop table if exists employee_client_assignment cascade;
drop table if exists attendance_detail cascade;


create table if not exists attendance (
    id integer not null constraint attendance_pk primary key
    , attd_date date not null
    , attd_year smallint not null
    , attd_month smallint not null
    , attd_day smallint not null
    , attd_week_day smallint not null
    , attd_dayname character varying(30) not null
    , attd_week_year smallint not null
    , created_on timestamp(3) without time zone not null default (now())
    , updated_on timestamp(3) without time zone null
    , constraint attendance_date_uq unique (attd_date)
    , constraint attendance_date_ck check (attd_date <= current_date)
);

alter table if exists attendance owner to payroll;

create table if not exists client (
    id serial not null constraint client_pk primary key
    , short_name character varying(100) not null
    , full_name character varying(300) not null
    , is_active boolean not null default true
    , client_since date not null
    , contract_cancellation_date date null
    , country character varying(2) not null
    , created_on timestamp(3) without time zone not null default (now())
    , updated_on timestamp(3) without time zone null
    , constraint client_short_name_uq unique (short_name)
    , constraint client_full_name_uq unique (full_name)
    , constraint client_since_ck check (client_since <= current_date)
    , constraint client_contract_cancellation_date_ck check (contract_cancellation_date is null or contract_cancellation_date > client_since)
);

alter table if exists client owner to payroll;

create table if not exists employee (
    id serial not null constraint employee_pk primary key
    , names character varying(100) not null
    , lastnames character varying(100) not null
    , gender character varying(10) not null
    , date_of_birth date not null
    , country_of_birth character varying(2) not null
    , empl_role character varying(100) not null
    , is_active boolean not null default true
    , start_working_date date not null
    , end_working_date date null
    , created_on timestamp(3) without time zone not null default (now())
    , updated_on timestamp(3) without time zone null
    , constraint employee_date_of_birth_ck check (date_of_birth < current_date)
    , constraint employee_start_working_date_ck check (start_working_date <= current_date)
    , constraint employee_end_working_date_ck check (end_working_date is null or end_working_date > start_working_date)
);

alter table if exists employee owner to payroll;

create table if not exists employee_client_assignment (
    id serial not null constraint employee_client_assignment_pk primary key
    , employee_id integer not null constraint employee_client_assignment_fk1 references employee (id)
    , client_id integer not null constraint employee_client_assignment_fk2 references client (id)
    , is_active boolean not null default true
    , start_date date not null
    , end_date date null
    , team character varying(50) null
    , created_on timestamp(3) without time zone not null default (now())
    , updated_on timestamp(3) without time zone null
    , constraint employee_client_assignment_uq unique (employee_id, client_id, start_date)
    , constraint employee_client_assignment_end_date_ck check (end_date is null or end_date > start_date)
);

alter table if exists employee_client_assignment owner to payroll;

create table if not exists attendance_detail (
    id serial not null constraint attendance_detail_pk primary key
    , attendance_id integer not null constraint attendance_header_fk references attendance (id)
    , employee_id integer not null constraint attendance_detail_employee_fk references employee (id)
    , start_time timestamp(3) not null
    , end_time timestamp(3) null
    , created_on timestamp(3) without time zone not null default (now())
    , updated_on timestamp(3) without time zone null
    , constraint attendance_detail_uq unique (attendance_id, employee_id)
    , constraint attendance_detail_end_time_ck check (end_time is null or end_time > start_time)
);

alter table if exists attendance_detail owner to payroll;

-- ===============================================================================================
-- Generate dummy data
-- ===============================================================================================

do $$
declare
    processing_date date := date '2019-01-01';
    attendance_pk integer := 0;
    week_day integer := 0;
begin
    while processing_date <= current_date loop
        attendance_pk := (extract(year from processing_date) * 100 + extract(month from processing_date))*100 + extract(day from processing_date);
        week_day := extract(isodow from processing_date);

        if week_day >= 1 and week_day <= 5 then
            insert into attendance(
                id
                , attd_date
                , attd_year
                , attd_month
                , attd_day
                , attd_week_day
                , attd_dayname
                , attd_week_year)
            select
                attendance_pk
                , processing_date
                , extract(isoyear from processing_date)
                , extract(month from processing_date)
                , extract(day from processing_date)
                , extract(isodow from processing_date)
                , to_char(processing_date, 'day')
                , extract(week from processing_date);
        end if;

        processing_date := processing_date + integer '1';
    end loop;
end $$;


insert into client(
    short_name
    , full_name
    , client_since
    , country)
values(
    'Allsafe'
    , 'Allsafe Cybersecurity'
    , date '2018-12-21'
    , 'US');


insert into employee(
        names
        , lastnames
        , gender
        , date_of_birth
        , country_of_birth
        , empl_role
        , start_working_date)
select  names.first_name
        , adj.last_name
        , names.gender
        , date '1989-12-15'
        , 'US'
        , 'IT_CONTRACTOR'
        , date '2018-04-01'
from
        (select 'Mike' as first_name, 'MALE' as gender union all
        select 'Albert', 'MALE' union all
        select 'Giancarlo', 'MALE' union all
        select 'Derek', 'MALE' union all
        select 'Mr Robot', 'MALE' union all
        select 'Neo', 'MALE' union all
        select 'Bill', 'MALE' union all
        select 'Steve', 'MALE' union all
        select 'Linus', 'MALE' union all
        select 'Rami', 'MALE' union all
        select 'Duke', 'MALE' union all
        select 'Keanu', 'MALE' union all
        select 'Matt', 'MALE' union all
        select 'Jason', 'MALE' union all
        select 'Rachel', 'FEMALE' union all
        select 'Ana', 'FEMALE' union all
        select 'Carmen', 'FEMALE' union all
        select 'Scarlett', 'FEMALE' union all
        select 'Keira', 'FEMALE' union all
        select 'Anne', 'FEMALE' union all
        select 'Charlize', 'FEMALE'
        ) names cross join
        (select 'Amazing' as last_name union all
        select 'Awesome' union all
        select 'Wonderful' union all
        select 'Inspiring' union all
        select 'Different'
        ) adj;


with one_client as (
    select id from client where is_active = true limit 1
)
insert into employee_client_assignment(
        employee_id
        , client_id
        , start_date
        , team)
select  id
        , (select id from one_client)
        , date '2019-01-01'
        , 'FSOCIETY'
from    employee
where   is_active = true;


insert into attendance_detail(
        attendance_id
        , employee_id
        , start_time)
select  a.id
        , e.id
        , attd_date + time '07:15'
from    attendance a cross join employee e;

/*
select to_char(current_date, 'day');
select * from attendance;

select * from attendance order by id desc;
select * from attendance where id = 20200405;
delete from attendance where id = 20200405;

select * from attendance_detail where attendance_id = 20190920;
truncate table attendance;
*/
