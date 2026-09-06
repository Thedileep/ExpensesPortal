package com.expenses.portal.service;

import com.expenses.portal.dto.LoginDTO;
import com.expenses.portal.dto.LoginResponseDTO;
import com.expenses.portal.exception.ExpensesException;

public interface LoginService {
	LoginResponseDTO login(LoginDTO loginDTO) throws ExpensesException; 
}
