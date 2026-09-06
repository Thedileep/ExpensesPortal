package com.expenses.portal.serviceimpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.expenses.portal.dto.ExpenseDTO;
import com.expenses.portal.entity.ExpensesEntity;
import com.expenses.portal.entity.LoginEntity;
import com.expenses.portal.exception.ExpensesException;
import com.expenses.portal.repository.ExpenseRepository;
import com.expenses.portal.repository.LoginRepository;
import com.expenses.portal.response.dto.ExpenseResponseDTO;
import com.expenses.portal.service.ExpenseService;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final LoginRepository loginRepository;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository,LoginRepository loginRepository) {

        this.expenseRepository = expenseRepository;
        this.loginRepository = loginRepository;
    }

    @Override
    public ExpenseResponseDTO addExpense(ExpenseDTO expenseDTO,String username)throws ExpensesException {

        LoginEntity user = loginRepository.findByUsername(username)
                .orElseThrow(() ->new ExpensesException("USER.NOT.FOUND"));

        ExpensesEntity expense = new ExpensesEntity();

        expense.setAmount(expenseDTO.getAmount());
        expense.setCategory(expenseDTO.getCategory());
        expense.setDescription(expenseDTO.getDescription());
        expense.setExpenseDate(expenseDTO.getExpenseDate());
        expense.setPaymentMethod(expenseDTO.getPaymentMethod());
        expense.setCreatedAt(LocalDateTime.now());

        expense.setUser(user);

        ExpensesEntity savedExpense =expenseRepository.save(expense);

        return convertToResponse(savedExpense);
    }

    @Override
    public List<ExpenseResponseDTO> getMyExpenses(String username)throws ExpensesException {
        List<ExpensesEntity> expenses =expenseRepository.findByUserUsername(username);

        return expenses.stream()
                .map(this::convertToResponse).toList();
    }

    @Override
    public ExpenseResponseDTO updateExpense(Long id,ExpenseDTO expenseDTO,String username)throws ExpensesException {

        ExpensesEntity expense =expenseRepository.findById(id)
                .orElseThrow(() ->new ExpensesException("EXPENSE.NOT.FOUND"));

        if (!expense.getUser().getUsername().equals(username)) {

            throw new ExpensesException("EXPENSE.ACCESS.DENIED");
        }

        expense.setAmount(expenseDTO.getAmount());
        expense.setCategory(expenseDTO.getCategory());
        expense.setDescription(expenseDTO.getDescription());
        expense.setExpenseDate(expenseDTO.getExpenseDate());
        expense.setPaymentMethod(expenseDTO.getPaymentMethod());

        ExpensesEntity updatedExpense =expenseRepository.save(expense);

        return convertToResponse(updatedExpense);
    }

    @Override
    public void deleteExpense(Long id,String username)throws ExpensesException {

        ExpensesEntity expense =expenseRepository.findById(id)
                .orElseThrow(() ->new ExpensesException("EXPENSE.NOT.FOUND"));

        if (!expense.getUser().getUsername().equals(username)) {

            throw new ExpensesException("EXPENSE.ACCESS.DENIED");
        }

        expenseRepository.delete(expense);
    }

    @Override
    public List<ExpenseResponseDTO> getExpensesBetweenDates(String username,LocalDate from,LocalDate to)throws ExpensesException {

        if (from.isAfter(to)) {
            throw new ExpensesException("INVALID.DATE.RANGE");
        }

        List<ExpensesEntity> expenses =expenseRepository
                .findByUserUsernameAndExpenseDateBetweenOrderByExpenseDateDesc(username,from,to);

        return expenses.stream()
                .map(this::convertToResponse).toList();
    }

    @Override
    public Double getTotalExpense(String username,LocalDate from,LocalDate to) throws ExpensesException {

        if (from.isAfter(to)) {
            throw new ExpensesException("INVALID.DATE.RANGE");
        }

        List<ExpensesEntity> expenses =expenseRepository.findByUserUsernameAndExpenseDateBetween(username,from,to);

        return expenses.stream()
                .mapToDouble(ExpensesEntity::getAmount).sum();
    }

    private ExpenseResponseDTO convertToResponse(ExpensesEntity expense) {

        return new ExpenseResponseDTO(expense.getId(),expense.getAmount(),expense.getCategory(),
                expense.getDescription(),expense.getExpenseDate(),expense.getPaymentMethod());
    }
}