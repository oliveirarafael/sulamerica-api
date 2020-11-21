package br.com.sulamerica.desafio.api.config.filter;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.sulamerica.desafio.api.model.entity.Usuario;
import br.com.sulamerica.desafio.api.repository.UsuarioRepository;
import br.com.sulamerica.desafio.api.service.TokenService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class AutenticacaoTokenFilter extends OncePerRequestFilter{

    private TokenService tokenService;
    private UsuarioRepository repository;

    public AutenticacaoTokenFilter(TokenService tokenService, UsuarioRepository repository) {
        this.tokenService = tokenService;
        this.repository = repository;
    }

	@Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = recuperaToken(request);

        if(tokenService.isValido(token)){
            autenticar(token);
        }
                                    
        filterChain.doFilter(request, response);
    }
    
    private void autenticar(String token) {
		Long idUsuario = tokenService.getIdUsuario(token);
		Usuario usuario = repository.findById(idUsuario).get();
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

    private String recuperaToken(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        if(!Optional.ofNullable(token).isPresent() || !token.startsWith("Bearer ")){
            return null;
        }

        return token.split(" ")[1];
    }

   
}
