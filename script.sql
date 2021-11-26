SELECT * FROM comunidad.developer;
SELECT * FROM comunidad.developer_tecnologias;
SELECT * FROM comunidad.tecnologias;
drop database comunidad;

SELECT * FROM comunidad.ong;
SELECT * FROM comunidad.usuario inner join comunidad.developer;
SELECT * FROM comunidad.usuario;
SELECT * FROM comunidad.developer;

SELECT * FROM ong inner join usuario;
create database comunidad;
SELECT * FROM comunidad.foto;
SELECT max(id_foto) from foto;
SELECT * FROM comunidad.foto;
select * from proyecto;
SELECT * FROM proyecto_developer;
SELECT * FROM usuario u WHERE u.email = 'aaaa@gmail.com';
select * from comunidad.proyecto;
select * from comunidad.proyecto_developer;
describe proyecto;
UPDATE `comunidad`.`proyecto` SET `admitir_deve` = '1' WHERE (`id_proyecto` = 'true');
-- insert into ong (alta, apellido_rep, foto_id_foto, marca, nombre_rep, usuario_id_usuario) values (?, ?, ?, ?, ?, ?)

INSERT INTO `comunidad`.`tecnologias` (`id_tecnologia`, `lenguaje`) VALUES ('2', 'java');
select * from comunidad.proyecto;
UPDATE `comunidad`.`proyecto` SET `admitir_deve` = NOT `admitir_deve`  WHERE (`id_proyecto` = '1');

