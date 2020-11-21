create table cargo (
    id bigint not null,
    nome varchar(255) not null,
    primary key (id)
)
create table perfil (
    id bigint not null,
    nome varchar(255) not null,
    primary key (id)
)
create table usuario (
    id bigint not null,
    cpf varchar(255) not null,
    data_nascimento date not null,
    nome varchar(255) not null,
    senha varchar(255) not null,
    sexo varchar(255) not null,
    status boolean not null,
    cargo_id bigint not null,
    primary key (id)
)
create table usuario_perfis (
    usuario_id bigint not null,
    perfis_id bigint not null
)

alter table usuario add constraint cargo_fk foreign key (cargo_id) references cargo
alter table usuario_perfis add constraint perfil_fk foreign key (perfis_id) references perfil
alter table usuario_perfis add constraint usuario_fk foreign key (usuario_id) references usuario
