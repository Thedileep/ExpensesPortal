
package com.expenses.portal.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expenses.portal.dto.LoginDTO;
import com.expenses.portal.dto.LoginResponseDTO;
import com.expenses.portal.exception.ExpensesException;
import com.expenses.portal.service.LoginService;

@RestController
@RequestMapping("/auth")
public class LoginAPI {

    private final LoginService loginService;

    public LoginAPI(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) throws ExpensesException {

        LoginResponseDTO response = loginService.login(loginDTO);

        return ResponseEntity.ok(response);
    }
}
