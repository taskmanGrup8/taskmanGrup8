/* Populate tables */
INSERT INTO rols (codigo, nombre) VALUES('ROLE_ADMIN', 'Administrador');
INSERT INTO rols (codigo, nombre) VALUES('ROLE_USER', 'Usuari');
INSERT INTO rols (codigo, nombre) VALUES('ROLE_SUPER', 'Super Administrador');
INSERT INTO rols (codigo, nombre) VALUES('ROLE_TASKMAN', 'Taskman Manager');


INSERT INTO departaments (codigo, nombre) VALUES('INF', 'Informàtica');
INSERT INTO departaments (codigo, nombre) VALUES('CON', 'Contabilitat');
INSERT INTO departaments (codigo, nombre) VALUES('PRD', 'Producció');



INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Vázquel Iglesias', '72345678A', 'Paula', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', false, '666554411', 3, 'seres210403@hotmail.com', 'Paula', 'img-profile-default.png');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Esteban Gutiérrez', '12345678A', 'Sergio', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554411', 1, 'sergio@correo.com', 'Sergio', 'img-profile-default.png');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Gómez Casas', '12345679A', 'Pablo', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554422', 2, 'pablo@correo.com', 'Pablo', 'img-profile-default.png');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'López Carrasco', '22345678A', 'Aitor', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554411', 1, 'aitor@correo.com', 'Aitor', 'img-profile-default.png');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'García Iniesta', '32345679A', 'Luz', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554422', 2, 'luz@correo.com', 'Luz', 'img-profile-default.png');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Salas Escudero', '42345678A', 'Isabel', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554411', 1, 'isabel@correo.com', 'Isabel', 'img-profile-default.png');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Carvajal Ramos', '52345679A', 'Sara', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554422', 2, 'sara@correo.com', 'Sara', 'img-profile-default.png');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Fernández Valverde', '62345678A', 'Luis', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554411', 1, 'luis@correo.com', 'Luis', 'img-profile-default.png');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Vázquez Asensio', '72345679A', 'Pablo', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554422', 2, 'lucas@correo.com', 'Pau', 'img-profile-default.png');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Alarcón Blanco', '82345678A', 'Miguel', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554411', 1, 'miguel@correo.com', 'Miguel', 'img-profile-default.png');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Hermoso Corredera', '92345679A', 'Marta', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554422', 2, 'marta@correo.com', 'Marta', 'img-profile-default.png');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Piqué Alba', '13345678A', 'Jennifer', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554411', 1, 'jenny@correo.com', 'Jennifer', 'img-profile-default.png');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Araujo Mingueza', '14345679A', 'Ana', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554422', 2, 'ana@correo.com', 'Ana', '');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Busquets Soler', '15345678A', 'Carlos', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554411', 1, 'carlos@correo.com', 'Carlos', 'img-profile-default.png');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Merino Torres', '16345679A', 'Ferran', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554422', 2, 'ferran@correo.com', 'Ferran', 'img-profile-default.png');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Hernández Soldevilla', '17345678A', 'Robert', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554411', 1, 'roberto@correo.com', 'Robert', 'img-profile-default.png');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Luque Santos', '18345679A', 'Ariadna', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554422', 2, 'ari@correo.com', 'Ariadna', 'img-profile-default.png');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Ortega Vílchez', '19345678A', 'Ester', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554411', 1, 'ester@correo.com', 'Ester', 'img-profile-default.png');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'León Moreno', '12445679A', 'Mónica', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554422', 2, 'monica@correo.com', 'Monica', 'img-profile-default.png');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Martínez Llorente', '12545678A', 'Adrián', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554411', 1, 'adri@correo.com', 'Adrian', 'img-profile-default.png');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Ufarte Vidal', '12545679A', 'Nacho', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554422', 2, 'nacho@correo.com', 'Nacho', 'img-profile-default.png');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Montoya Ruiz', '12645678A', 'Víctor', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554411', 1, 'victor@correo.com', 'Victor', 'img-profile-default.png');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Ferrer Bakero', '11111111A', 'Maria', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554411', 1, 'maria@correo.com', 'Maria', 'img-profile-default.png');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Martin Soria', '12845678A', 'Cristina', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554411', 1, 'cristina@correo.com', 'Cristina', 'img-profile-default.png');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Díaz Martos', '12945679A', 'Dolores', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554422', 2, 'lola@correo.com', 'Dolors', 'img-profile-default.png');
INSERT INTO usuaris (activo, apellidos, dni, nombre, password, privacidad_firmada, telefono, id_rol, email, username, foto) VALUES(true, 'Taskman', '55555555A', 'Taskman', '$2a$12$cWBnxzLRHjrW8yDvUSBKDu5h909HU6kmBrZKt9P/WzuBhOwp5v.8a', true, '666554422', 4, 'taskman@correo.com', 'Taskman', 'img-profile-default.png');


INSERT INTO usuari_departament(id_usuario, id_departament) VALUES(3, 3);

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
INSERT INTO fases(codigo, nombre, descripcion, id_departament) VALUES('MVL', 'Montar Volant', 'Es montarà un volant qualsevol', 3);
INSERT INTO fases(codigo, nombre, descripcion, id_departament) VALUES('MOR', 'Montar ordinador', 'Es montarà un ordinador qualsevol', 1);


INSERT INTO tasques(ciclica, codigo, descripcion, nombre, tiempo_ciclo, tiempo) VALUES(false, 'RLJ', 'Hacer relojes de muñeca', 'hacer relojes', 0, 3);
INSERT INTO tasques(ciclica, codigo, descripcion, nombre, tiempo_ciclo, tiempo) VALUES(false, 'MV1', 'Montar volant tipus 1', 'Montar volant del tipus asf', 0, 63);
INSERT INTO tasques(ciclica, codigo, descripcion, nombre, tiempo_ciclo, tiempo) VALUES(false, 'OB3', 'Montar ordinador B00', 'Montar un ordinador de tipus B300', 0, 127);

INSERT INTO fases_con_tiempo(tiempo, id_fase, tasca_id) VALUES(1, 1, 1);
INSERT INTO fases_con_tiempo(tiempo, id_fase, tasca_id) VALUES(2, 2, 1);
INSERT INTO fases_con_tiempo(tiempo, id_fase, tasca_id) VALUES(3, 1, 2);
INSERT INTO fases_con_tiempo(tiempo, id_fase, tasca_id) VALUES(60, 3, 2);
INSERT INTO fases_con_tiempo(tiempo, id_fase, tasca_id) VALUES(7, 1, 3);
INSERT INTO fases_con_tiempo(tiempo, id_fase, tasca_id) VALUES(120, 4, 3);


INSERT INTO ordres(cantidad, ciclica, data_fin, notificada, prioridad, tasca_id) VALUES(2, false, '2022-01-23', false, 1, 1);
INSERT INTO ordres(cantidad, ciclica, data_fin, notificada, prioridad, tasca_id) VALUES(50, false, '2022-01-03', false, 1, 2);
INSERT INTO ordres(cantidad, ciclica, data_fin, notificada, prioridad, tasca_id) VALUES(3, false, '2021-12-29', false, 2, 3);

INSERT INTO fases_executables(bloqueada, notificada, fase_id, orden_id) VALUES(false, false, 1, 1);
INSERT INTO fases_executables(bloqueada, notificada, fase_id, orden_id) VALUES(true, false, 1, 1);
INSERT INTO fases_executables(bloqueada, notificada, fase_id, orden_id) VALUES(false, false, 3, 2);
INSERT INTO fases_executables(bloqueada, notificada, fase_id, orden_id) VALUES(true, false, 4, 2);
INSERT INTO fases_executables(bloqueada, notificada, fase_id, orden_id) VALUES(false, false, 5, 3);
INSERT INTO fases_executables(bloqueada, notificada, fase_id, orden_id) VALUES(true, false, 6, 3);


INSERT INTO notificacions(cantidad, data_fin, data_inici, fase_id, usuari_id) VALUES(13, '2021/12/28 11:33:21.500 am', '2021/12/28 10:30:20.500 am', 3, 3);

INSERT INTO empreses(nombre, cif, direccion, localidad, provincia, cpostal, telefono, email, logo) VALUES('NIKE', 'A12345678', 'Carrer Aragon, 13', 'Barcelona', 'Barcelona', '08080', '543781204', 'ester210403@gmail.com', '');



	
