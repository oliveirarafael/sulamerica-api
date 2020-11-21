package br.com.sulamerica.desafio.api.view;

public interface Views {
    static interface Dto{}
    static interface Form{
        static interface Post {}
        static interface Put {}
    }

    static interface AutenticacaoView {
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