create table financas.usuario
(
	id bigserial not null primary key,
	nome character varying(150),
	email character varying(150),
	senha character varying(100),
	data_cadastro date
);

create table financas.lancamento
(
	id bigserial not null primary key,
	descricao character varying(150),
	mes integer not null,
	ano integer not null,
	valor numeric(16,2),
	tipo character varying(20),
	status character varying(20),
	id_usuario bigint references financas.usuario (id),
	data_cadastro date
	
);