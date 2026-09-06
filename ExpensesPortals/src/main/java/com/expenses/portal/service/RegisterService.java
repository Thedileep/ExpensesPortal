package com.expenses.portal.service;

import com.expenses.portal.dto.RegisterDTO;
import com.expenses.portal.exception.ExpensesException;
import com.expenses.portal.response.dto.RegisterResponseDTO;

public interface RegisterService {
	 RegisterResponseDTO register(RegisterDTO registerDTO)throws ExpensesException;
}
