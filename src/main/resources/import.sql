/* Populate tables */
INSERT INTO rols (codigo, nombre) VALUES('ROLE_ADMIN', 'Administrador');
INSERT INTO rols (codigo, nombre) VALUES('ROLE_USER', 'Usuario');
INSERT INTO rols (codigo, nombre) VALUES('ROLE_SUPER', 'Super Administrador');
INSERT INTO rols (codigo, nombre) VALUES('ROLE_TASKMAN', 'Taskman Manager');


INSERT INTO departaments (codigo, nombre) VALUES('INF', 'Informàtica');
INSERT INTO departaments (codigo, nombre) VALUES('CON', 'Contabilitat');
INSERT INTO departaments (codigo, nombre) VALUES('PRD', 'Producció');



INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Vázquel Iglesias', '72345678A', 'Paula', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554411', 3, 'sergio@correo.com', 'Paula', '');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Esteban Gutiérrez', '12345678A', 'Sergio', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554411', 1, 'sergio@correo.com', 'Sergio', '');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Gómez Casas', '12345679A', 'Pablo', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554422', 2, 'pablo@correo.com', 'Pablo', '');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'López Carrasco', '22345678A', 'Aitor', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554411', 1, 'aitor@correo.com', 'Aitor', '');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'García Iniesta', '32345679A', 'Luz', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554422', 2, 'luz@correo.com', 'Luz', '');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Salas Escudero', '42345678A', 'Isabel', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554411', 1, 'isabel@correo.com', 'Isabel', '');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Carvajal Ramos', '52345679A', 'Sara', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554422', 2, 'sara@correo.com', 'Sara', '');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Fernández Valverde', '62345678A', 'Luis', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554411', 1, 'luis@correo.com', 'Luis', '');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Vázquez Asensio', '72345679A', 'Pablo', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554422', 2, 'lucas@correo.com', 'Pau', '');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Alarcón Blanco', '82345678A', 'Miguel', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554411', 1, 'miguel@correo.com', 'Miguel', '');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Hermoso Corredera', '92345679A', 'Marta', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554422', 2, 'marta@correo.com', 'Marta', '');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Piqué Alba', '13345678A', 'Jennifer', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554411', 1, 'jenny@correo.com', 'Jennifer', '');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Araujo Mingueza', '14345679A', 'Ana', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554422', 2, 'ana@correo.com', 'Ana', '');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Busquets Soler', '15345678A', 'Carlos', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554411', 1, 'carlos@correo.com', 'Carlos', '');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Merino Torres', '16345679A', 'Ferran', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554422', 2, 'ferran@correo.com', 'Ferran', '');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Hernández Soldevilla', '17345678A', 'Robert', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554411', 1, 'roberto@correo.com', 'Robert', '');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Luque Santos', '18345679A', 'Ariadna', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554422', 2, 'ari@correo.com', 'Ariadna', '');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Ortega Vílchez', '19345678A', 'Ester', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554411', 1, 'ester@correo.com', 'Ester', '');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'León Moreno', '12445679A', 'Mónica', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554422', 2, 'monica@correo.com', 'Monica', '');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Martínez Llorente', '12545678A', 'Adrián', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554411', 1, 'adri@correo.com', 'Adrian', '');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Ufarte Vidal', '12545679A', 'Nacho', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554422', 2, 'nacho@correo.com', 'Nacho', '');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Montoya Ruiz', '12645678A', 'Víctor', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554411', 1, 'victor@correo.com', 'Victor', '');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Ferrer Bakero', '11111111A', 'Maria', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554411', 1, 'maria@correo.com', 'Maria', '');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Martin Soria', '12845678A', 'Cristina', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554411', 1, 'cristina@correo.com', 'Cristina', '');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Díaz Martos', '12945679A', 'Dolores', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554422', 2, 'lola@correo.com', 'Dolors', '');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Taskman', '55555555A', 'Taskman', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554422', 4, 'taskman@correo.com', 'Taskman', '');


INSERT INTO authorities(user_id, authority) VALUES(1, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES(1, 'ROLE_ADMIN');
INSERT INTO authorities(user_id, authority) VALUES(1, 'ROLE_SUPER');
INSERT INTO authorities(user_id, authority) VALUES(2, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES(2, 'ROLE_ADMIN');
INSERT INTO authorities(user_id, authority) VALUES(3, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES(4, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES(4, 'ROLE_ADMIN');
INSERT INTO authorities(user_id, authority) VALUES(5, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES(6, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES(6, 'ROLE_ADMIN');
INSERT INTO authorities(user_id, authority) VALUES(7, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES(8, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES(8, 'ROLE_ADMIN');
INSERT INTO authorities(user_id, authority) VALUES(9, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES(10, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES(10, 'ROLE_ADMIN');
INSERT INTO authorities(user_id, authority) VALUES(11, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES(12, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES(12, 'ROLE_ADMIN');
INSERT INTO authorities(user_id, authority) VALUES(13, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES(14, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES(14, 'ROLE_ADMIN');
INSERT INTO authorities(user_id, authority) VALUES(15, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES(16, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES(16, 'ROLE_ADMIN');
INSERT INTO authorities(user_id, authority) VALUES(17, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES(18, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES(18, 'ROLE_ADMIN');
INSERT INTO authorities(user_id, authority) VALUES(19, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES(20, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES(20, 'ROLE_ADMIN');
INSERT INTO authorities(user_id, authority) VALUES(21, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES(22, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES(22, 'ROLE_ADMIN');
INSERT INTO authorities(user_id, authority) VALUES(23, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES(23, 'ROLE_ADMIN');
INSERT INTO authorities(user_id, authority) VALUES(24, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES(24, 'ROLE_ADMIN');
INSERT INTO authorities(user_id, authority) VALUES(25, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES(26, 'ROLE_USER');
INSERT INTO authorities(user_id, authority) VALUES(26, 'ROLE_ADMIN');
INSERT INTO authorities(user_id, authority) VALUES(26, 'ROLE_SUPER');
INSERT INTO authorities(user_id, authority) VALUES(26, 'ROLE_TASKMAN');


INSERT INTO fases(codigo, nombre, descripcion, id_departament) VALUES('RMA', 'Reunió de materials', 'Es reuniran materials', 3);
INSERT INTO fases(codigo, nombre, descripcion, id_departament) VALUES('MIM', 'Montatje i mecanitzat', 'Es montarà el que faci falta', 3);


INSERT INTO empreses(nombre, cif, direccion, localidad, provincia, cpostal, telefono, email, logo) VALUES('NIKE', 'A12345678', 'Carrer Aragon, 13', 'Barcelona', 'Barcelona', '08080', '543781204', 'nike@gmail.com', '');



	
