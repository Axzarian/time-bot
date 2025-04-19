CREATE TABLE IF NOT EXISTS users
(
    id          integer primary key autoincrement,
    telegram_id bigint      not null unique,
    first_name  varchar(32) ,
    last_name   varchar(32) ,
    user_name   varchar(32) not null unique
);