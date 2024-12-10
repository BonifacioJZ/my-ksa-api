package com.bonifacio.my_ksa_api.configuration.filter;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.bonifacio.my_ksa_api.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;


@AllArgsConstructor
public class JwtTokenValidation extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if(StringUtils.isNoneEmpty(token)){
            token = token.substring(7);

            DecodedJWT  decodedJWT = this.jwtUtils.validateJWT(token);

            String username = jwtUtils.getUsername(decodedJWT);
            String stringClaims = jwtUtils.getClaim(decodedJWT,"authorities").asString();

            Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(stringClaims);

            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(username,null,authorities);
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);
        }
        filterChain.doFilter(request,response);
    }
}
