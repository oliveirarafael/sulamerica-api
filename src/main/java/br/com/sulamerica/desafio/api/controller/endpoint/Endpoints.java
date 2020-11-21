package br.com.sulamerica.desafio.api.controller.endpoint;

public class Endpoints {
    private Endpoints(){}

    public static class Cargo{
        private Cargo(){}
        public static final String CARGOS_ENDPOINT = "/cargos";
        public static final String CARGOS_ID_ENDPOINT = "/{id}";
    }

    public static class Perfil{
        private Perfil(){}
        public static final String PERFIS_ENDPOINT = "/perfis";
        public static final String PERFIS_ID_ENDPOINT = "/{id}";
    }

    public static class Usuario{
        private Usuario(){}
        public static final String USUARIOS_ENDPOINT = "/usuarios";
    }
}
