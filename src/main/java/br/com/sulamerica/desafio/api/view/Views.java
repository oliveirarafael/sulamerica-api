package br.com.sulamerica.desafio.api.view;

public interface Views {
    static interface Autenticacao{
        static interface Dto{}
        static interface Form{}
    }

    static interface UsuarioView{
        static interface Dto{}
        static interface Form{
            static interface Post {}
            static interface Put {}
        }
    }

}