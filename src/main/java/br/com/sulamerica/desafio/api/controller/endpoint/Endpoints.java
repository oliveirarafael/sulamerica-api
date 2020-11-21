package br.com.sulamerica.desafio.api.controller.endpoint;

public class Endpoints {
    private Endpoints(){}

    public static class Autenticacao{
        private Autenticacao(){}
        public static final String AUTH = "/auth";
    }

    public static class Cargos {
        private Cargos(){}
        public static final String CARGOS_ENDPOINT = "/cargos";
        public static final String CARGOS_ID_ENDPOINT = "/{id}";
    }

    public static class Perfis {
        private Perfis(){}
        public static final String PERFIS_ENDPOINT = "/perfis";
        public static final String PERFIS_ID_ENDPOINT = "/{id}";
    }

    public static class Usuarios {
        private Usuarios(){}
        public static final String USUARIOS_ENDPOINT = "/usuarios";
        public static final String USUARIOS_ID_ENDPOINT = "/{id}";
    }
}
