-- 
-- El contenido de este fichero se cargará al arrancar la aplicación, suponiendo que uses
-- 		application-default ó application-externaldb en modo 'create'
--

-- Usuario de ejemplo con username = b y contraseña = aa  
-- INSERT INTO user(id,enabled,username,password,roles,first_name,last_name) VALUES (
-- 	1, 1, 'a', 
-- 	'{bcrypt}$2a$10$xLFtBIXGtYvAbRqM95JhcOaG23fHRpDoZIJrsF2cCff9xEHTTdK1u',
-- 	'USER,ADMIN',
-- 	'Abundio', 'Ejémplez'
-- );

-- -- Otro usuario de ejemplo con username = SPACEMARINE y contraseña = aa  
INSERT INTO user(id,apellidos,caducidadTarjeta, email,enabled,nombre,numSecreto,numtarjeta,numtelefono,password,preguntaseguridad,respuestaseguridad,puntuacion,roles,username) VALUES (
	2,'Sanchez Granado','22/03','email',1,'Jesus',12,87,333, 
	'{bcrypt}$2a$10$xLFtBIXGtYvAbRqM95JhcOaG23fHRpDoZIJrsF2cCff9xEHTTdK1u','Pregunta','Respuesta',4,
	'USER',
	'SPACEMARINE');

INSERT INTO user(id,apellidos,caducidadTarjeta, email,enabled,nombre,numSecreto,numtarjeta,numtelefono,password,preguntaseguridad,respuestaseguridad,puntuacion,roles,username) VALUES (
	3,'Garcia Tomasulo','22/03','email',1,'Sonia',12,87,333, 
	'{bcrypt}$2a$10$xLFtBIXGtYvAbRqM95JhcOaG23fHRpDoZIJrsF2cCff9xEHTTdK1u','Pregunta','Respuesta',4,
	'USER',
	'SONIA');

INSERT INTO TOUR_OFERTADO VALUES (1,'Madrid','Una visita por el centro de Madrid',TRUE,'Centro','',50,'España','',25,'Descubriendo Madrid',2);
INSERT INTO TOUR VALUES(1,0,'2021-04-21','2021-04-20',1,2);

INSERT INTO REVIEW VALUES(1,3,'Buen tour',3,2,1);

-- -- Unos pocos auto-mensajes de prueba
-- INSERT INTO MESSAGE VALUES(1,NULL,'2020-03-23 10:48:11.074000','probando 1',1,1);
-- INSERT INTO MESSAGE VALUES(2,NULL,'2020-03-23 10:48:15.149000','probando 2',1,1);
-- INSERT INTO MESSAGE VALUES(3,NULL,'2020-03-23 10:48:18.005000','probando 3',1,1);
-- INSERT INTO MESSAGE VALUES(4,NULL,'2020-03-23 10:48:20.971000','probando 4',1,1);
-- INSERT INTO MESSAGE VALUES(5,NULL,'2020-03-23 10:48:22.926000','probando 5',1,1);


