package br.com.sulamerica.desafio.api.service;

import java.util.Date;
import java.util.Optional;


import br.com.sulamerica.desafio.api.config.security.Profiles;
import br.com.sulamerica.desafio.api.model.entity.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Profile(value = {Profiles.PRODUCAO, Profiles.TESTE})
public class TokenService {
    
    private Date hoje;

    @Value("${exchange.dm10.api.jwt.expiration}")
    private String expiration;

    @Value("${exchange.dm10.api.jwt.secret}")
    private String secret;

    private Date dataExpiracao;

	public String token(Authentication authentication) {
        Usuario usuarioLogado = (Usuario) authentication.getPrincipal();
        hoje = new Date();
        dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
        return Jwts.builder()
                   .setIssuer("API Desafio SulAmerica")
                   .setSubject(usuarioLogado.getId().toString())
                   .setIssuedAt(hoje)
                   .setExpiration(dataExpiracao)
                   .signWith(SignatureAlgorithm.HS256, secret)
                   .compact();
	}

	public boolean isValido(String token) {

        if(!Optional.ofNullable(token).isPresent()){
            return false;
        }

        try{
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
    public Long getIdUsuario(String token) {
		Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}

}
