package com.expenses.portal.service;

import java.time.LocalDate;
import java.util.List;
import com.expenses.portal.dto.ExpenseDTO;
import com.expenses.portal.exception.ExpensesException;
import com.expenses.portal.response.dto.ExpenseResponseDTO;

public interface ExpenseService {

    ExpenseResponseDTO addExpense(
            ExpenseDTO expenseDTO,
            String username)
            throws ExpensesException;

    List<ExpenseResponseDTO> getMyExpenses(
            String username)
            throws ExpensesException;

    ExpenseResponseDTO updateExpense(
            Long id,
            ExpenseDTO expenseDTO,
            String username)
            throws ExpensesException;

    void deleteExpense(
            Long id,
            String username)
            throws ExpensesException;

    List<ExpenseResponseDTO> getExpensesBetweenDates(
            String username,
            LocalDate from,
            LocalDate to)
            throws ExpensesException;

    Double getTotalExpense(
            String username,
            LocalDate from,
            LocalDate to)
            throws ExpensesException;
}