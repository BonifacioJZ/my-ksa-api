package com.bonifacio.my_ksa_api.services.user;

import com.bonifacio.my_ksa_api.controller.dto.LoginDto;
import com.bonifacio.my_ksa_api.controller.dto.RegisterDto;
import com.bonifacio.my_ksa_api.controller.response.AuthResponse;
import com.bonifacio.my_ksa_api.persistence.entities.RoleEntity;
import com.bonifacio.my_ksa_api.persistence.reporsitory.IRoleRepository;
import com.bonifacio.my_ksa_api.persistence.reporsitory.IUserRepository;
import com.bonifacio.my_ksa_api.utils.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private final IUserRepository userRepository;
    @Autowired
    private final IRoleRepository roleRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final JwtUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe."));
    }

    public AuthResponse loginUser(LoginDto loginDto){
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();

        Authentication authentication = this.authentication(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token  = this.jwtUtils.generateToken(authentication);
        return AuthResponse.builder()
                .jwt(token)
                .message("El usuario se a logueado correctamente")
                .status(true)
                .build();
    }
    private AuthResponse registerUser(RegisterDto registerDto){
        List<String> rolesRequest = registerDto.getRoles().getRoles();
        Set<RoleEntity> roles = new HashSet<>(this.roleRepository.findRoleEntitiesByRoleEnumIn(rolesRequest));
        if(roles.isEmpty()){
            throw new IllegalArgumentException("los roles no existen");
        }
        return null;
    }
    private Authentication authentication(String username,String password){
        UserDetails userDetails = this.loadUserByUsername(username);

        if(userDetails ==null){
            throw  new BadCredentialsException(String.format("Invalid username or password"));
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw  new BadCredentialsException(String.format("Invalid username or password"));
        }
        return new UsernamePasswordAuthenticationToken(username,password,userDetails.getAuthorities());
    }
}
