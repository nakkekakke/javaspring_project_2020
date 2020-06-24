package project.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.domain.Account;
import project.domain.AccountFormObject;
import project.repositories.AccountRepository;

@Controller
public class AccountController {
    
    @Autowired
    private AccountRepository accountRepo;
    
    @Autowired
    private PasswordEncoder pwEncoder;
    
    @GetMapping("/login")
    public String login(Model model, @ModelAttribute("registerParam") String registerParam) {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) { // user not logged in
            if (registerParam.contains("success")) {
                model.addAttribute("registerSuccess", "registerSuccess");
            }
            return "login";
        }
        return "redirect:/index"; // user logged in, cant login before logout
    }
    
    @GetMapping("/register")
    public String register(@ModelAttribute AccountFormObject accountFormObject) {
        if (SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken) { // user not logged in
            return "register";
        }
        return "redirect:/index"; // user logged in, cant register before logout
    }
    
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute AccountFormObject accountFormObject, BindingResult bindingResult, RedirectAttributes redirectAttributes) { // AccountFormObject used to validate fields
        if (bindingResult.hasErrors()) {
            return "register";
        }
        Account dbAccount = new Account();
        dbAccount.setName(accountFormObject.getName());
        dbAccount.setUsername(accountFormObject.getUsername());
        dbAccount.setPassword(pwEncoder.encode(accountFormObject.getPassword()));
        accountRepo.save(dbAccount);
        redirectAttributes.addFlashAttribute("registerParam", "success");
        return "redirect:/login";
    }
}
