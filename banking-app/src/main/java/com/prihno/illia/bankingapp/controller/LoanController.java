package com.prihno.illia.bankingapp.controller;

import com.prihno.illia.bankingapp.dto.LoanApplicationDTO;
import com.prihno.illia.bankingapp.dto.LoanApplicationRequestDTO;
import com.prihno.illia.bankingapp.entity.loan.LoanApplication;
import com.prihno.illia.bankingapp.entity.loan.LoanGrade;
import com.prihno.illia.bankingapp.entity.loan.LoanIntent;
import com.prihno.illia.bankingapp.entity.loan.LoanStatus;
import com.prihno.illia.bankingapp.entity.other.Account;
import com.prihno.illia.bankingapp.entity.user.User;
import com.prihno.illia.bankingapp.entity.user.details.UserDetails;
import com.prihno.illia.bankingapp.service.AccountService;
import com.prihno.illia.bankingapp.service.LoanApplicationService;
import com.prihno.illia.bankingapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/loans")
public class LoanController {

    @Autowired
    UserService userService;

    @Autowired
    LoanApplicationService loanApplicationService;

    @Autowired
    AccountService accountService;

    @GetMapping
    public String loans(Model model) throws Exception {
        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        List<LoanApplication> loanApplications = loanApplicationService.findAllByUserEmail(userEmail);
        model.addAttribute("loanApplications", loanApplications);
        return "loans";
    }

    @GetMapping("/loan_application")
    public String loanApplication(LoanApplicationDTO loanApplicationDTO, Model model) throws Exception {
        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByEmail(userEmail);

        boolean userDetailsExists = user.getUserDetails() != null;
        model.addAttribute("userDetailsExists", userDetailsExists);
        model.addAttribute("accounts", user.getAccounts());
        model.addAttribute("loanIntentOptions", LoanIntent.values());
        model.addAttribute("loanGradeOptions", LoanGrade.values());
        return "loanApplication";
    }

    @PostMapping("/loan_application")
    public String loanApplication(@ModelAttribute LoanApplicationDTO loanApplicationDTO, RedirectAttributes redirectAttributes) throws Exception {
        final String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        UserDetails userDetails = userService.findByEmail(userEmail).getUserDetails();

        LoanApplicationRequestDTO loanApplicationRequestDTO = LoanApplicationRequestDTO.fromUserDetailsAndLoanApplication(userDetails, loanApplicationDTO);

        try {
            LoanStatus loanStatus = loanApplicationService.sendLoanApplicationRequest(loanApplicationRequestDTO);
            Account account = accountService.findByCardNumber(loanApplicationDTO.getToAccount());

            LoanApplication loanApplication = LoanApplication.builder()
                    .intent(loanApplicationDTO.getIntent())
                    .amount(loanApplicationDTO.getAmount())
                    .interestRate(loanApplicationDTO.getInterestRate())
                    .grade(loanApplicationDTO.getGrade())
                    .status(loanStatus)
                    .toAccount(account)
                    .build();

            if (loanStatus == LoanStatus.ACCEPTED) {
                account.setBalance(account.getBalance().add(loanApplicationDTO.getAmount()));
                accountService.update(account);
                redirectAttributes.addFlashAttribute("successMessage", "Loan accepted!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Loan denied!");
            }
            loanApplicationService.save(loanApplication);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Error during processing loan application: " + e.getMessage());
        }
        return "redirect:/accounts";
    }
}
