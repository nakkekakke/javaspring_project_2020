package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.domain.Account;
import project.repositories.AccountRepository;

@Controller
public class AccountController {
    
    @Autowired
    private AccountRepository accountRepo;
    
    @Autowired
    private BCryptPasswordEncoder pwEncoder;
    
    @PostMapping("/register")
    public String register(@RequestParam String name, @RequestParam String username, @RequestParam String password) {
        Account account = new Account();
        account.setUsername(username);
        account.setName(name);
        account.setPassword(pwEncoder.encode(password));
        accountRepo.save(account);
        return "redirect:/login";
    }
    
}
