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

-- -- Otro usuario de ejemplo con username = b y contraseña = aa  
INSERT INTO user VALUES (
	2,'Sanchez Granado','22/03','email', 1, '/img/guia1.jpg', 'Jesus',12,87,333, 
	'{bcrypt}$2a$10$xLFtBIXGtYvAbRqM95JhcOaG23fHRpDoZIJrsF2cCff9xEHTTdK1u','Pregunta','Respuesta',
	'USER',
	'SPACEMARINE');

INSERT INTO TOUR_OFERTADO VALUES (1, 'Alcala', 'Esta es la descripcion del tour numero 1', 1, '2021-12-12 20:00:00', '2021-12-12 20:00:00', 'tour1', '/img/mapa1.jpg', 2, 'adas', '/img/tour1.jpg', 8, 'Tour1', 2);
INSERT INTO TOUR_OFERTADO_ETIQUETAS  VALUES(1, 'museo');
INSERT INTO TOUR_OFERTADO_ETIQUETAS  VALUES(1, 'monumento');
INSERT INTO TOUR_OFERTADO_ETIQUETAS  VALUES(1, 'familia');
INSERT INTO TOUR_OFERTADO_ETIQUETAS  VALUES(1, 'historia');
--INSERT INTO TOUR_OFERTADO VALUES (2,'Madrid','Una visita por el centro de Madrid',TRUE,'2021-12-12 20:00:00', '2021-12-12 20:00:00','Centro','/img/mapa1.jpg',50,'España','/img/tour1.jpg',25,'Descubriendo Madrid',2);
INSERT INTO TOUR VALUES(1,0,'2021-04-21','2021-04-20',1);
INSERT INTO USER_IDIOMASHABLADOS VALUES (2, 'Ingles');
INSERT INTO USER_IDIOMASHABLADOS VALUES (2, 'Frances');
INSERT INTO USER_IDIOMASHABLADOS VALUES (2, 'Aleman');
-- -- Unos pocos auto-mensajes de prueba
-- INSERT INTO MESSAGE VALUES(1,NULL,'2020-03-23 10:48:11.074000','probando 1',1,1);
-- INSERT INTO MESSAGE VALUES(2,NULL,'2020-03-23 10:48:15.149000','probando 2',1,1);
-- INSERT INTO MESSAGE VALUES(3,NULL,'2020-03-23 10:48:18.005000','probando 3',1,1);
-- INSERT INTO MESSAGE VALUES(4,NULL,'2020-03-23 10:48:20.971000','probando 4',1,1);
-- INSERT INTO MESSAGE VALUES(5,NULL,'2020-03-23 10:48:22.926000','probando 5',1,1);


