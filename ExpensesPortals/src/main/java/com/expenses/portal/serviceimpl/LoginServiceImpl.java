package com.expenses.portal.serviceimpl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.expenses.portal.dto.LoginDTO;
import com.expenses.portal.dto.LoginResponseDTO;
import com.expenses.portal.exception.ExpensesException;
import com.expenses.portal.security.JwtService;
import com.expenses.portal.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public LoginServiceImpl(AuthenticationManager authenticationManager,
                            JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public LoginResponseDTO login(LoginDTO loginDTO)
            throws ExpensesException {

        Authentication authentication =
                authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                        loginDTO.getUsernameOrEmail(),
                        loginDTO.getPassword()
                    )
                );

        UserDetails user =
                (UserDetails) authentication.getPrincipal();

        String token = jwtService.generateToken(user);

        return new LoginResponseDTO(
                token,
                user.getUsername(),
                user.getAuthorities()
                    .iterator()
                    .next()
                    .getAuthority()
        );
    }
}