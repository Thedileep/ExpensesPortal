package com.expenses.portal.api;

import java.time.LocalDate;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.expenses.portal.dto.ExpenseDTO;
import com.expenses.portal.exception.ExpensesException;
import com.expenses.portal.response.dto.ExpenseResponseDTO;
import com.expenses.portal.service.ExpenseService;

@RestController
@RequestMapping("/expenses")
public class ExpenseAPI {

    private final ExpenseService expenseService;

    public ExpenseAPI(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> addExpense(@RequestBody ExpenseDTO expenseDTO,Authentication authentication)            throws ExpensesException {

        String username =authentication.getName();
        ExpenseResponseDTO response =expenseService.addExpense(expenseDTO,username);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponseDTO>> getMyExpenses(Authentication authentication)throws ExpensesException {

        String username =authentication.getName();
        List<ExpenseResponseDTO> response =expenseService.getMyExpenses(username);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> updateExpense(@PathVariable Long id,@RequestBody ExpenseDTO expenseDTO,
            Authentication authentication)throws ExpensesException {

        String username =authentication.getName();
        ExpenseResponseDTO response =expenseService.updateExpense(id,expenseDTO,username);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable Long id,Authentication authentication)throws ExpensesException {

        String username =authentication.getName();
        expenseService.deleteExpense(id,username);
        return ResponseEntity.ok("Expense deleted successfully");
    }

    @GetMapping("/between")
    public ResponseEntity<List<ExpenseResponseDTO>> getExpensesBetweenDates(@RequestParam LocalDate from,
            @RequestParam LocalDate to, Authentication authentication)throws ExpensesException {

        String username =authentication.getName();
        List<ExpenseResponseDTO> response =expenseService.getExpensesBetweenDates(username,from,to);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/total")
    public ResponseEntity<Double> getTotalExpense(@RequestParam LocalDate from,@RequestParam LocalDate to,
            Authentication authentication) throws ExpensesException {

        String username =authentication.getName();
        Double total =expenseService.getTotalExpense(username,from,to);
        return ResponseEntity.ok(total);
    }
}