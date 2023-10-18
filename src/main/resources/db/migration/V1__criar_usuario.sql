CREATE TABLE users (
    id VARCHAR(60) PRIMARY KEY NOT NULL UNIQUE,
    login VARCHAR(60) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(10) NOT NULL
);

INSERT INTO users (login,password,role,id) values ('admin','$2a$10$tGuouZB1TlFLiBAMPV2Wh.Po4jAmY3t/4j0dNcVZXk1m.DpeGFpZG','ADMIN','e95d0e3b-2992-43a6-8914-756abc89595a');