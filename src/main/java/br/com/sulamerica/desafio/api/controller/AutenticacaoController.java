package br.com.sulamerica.desafio.api.controller;

import br.com.sulamerica.desafio.api.config.security.Profiles;
import br.com.sulamerica.desafio.api.controller.endpoint.Endpoints;
import br.com.sulamerica.desafio.api.model.Token;
import br.com.sulamerica.desafio.api.model.entity.Usuario;
import br.com.sulamerica.desafio.api.service.TokenService;
import br.com.sulamerica.desafio.api.view.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping(Endpoints.Autenticacao.AUTH_ENDPOINT)
@Profile(value = {Profiles.PRODUCAO, Profiles.TESTE})
public class AutenticacaoController {

    @Autowired
    private AuthenticationManager authManager;
    
    @Autowired
    private TokenService tokenService;

    @PostMapping
    @JsonView(Views.AutenticacaoView.Dto.class)
    public ResponseEntity<Token> auth(@RequestBody @Validated(Views.AutenticacaoView.Form.class)
                                      @JsonView(Views.AutenticacaoView.Form.class) Usuario login){

        var dadosLogin = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        try{
            Authentication authentication = authManager.authenticate(dadosLogin);
            String token = tokenService.token(authentication);
            Usuario usuario = (Usuario) authentication.getPrincipal();
            return ResponseEntity.ok(new Token(token, usuario));    
        }catch(BadCredentialsException e){
            throw new BadCredentialsException("Usuário Inválido");
        }catch (LockedException e){
            throw new LockedException("Usuário Bloqueado");
        }
    }
}
