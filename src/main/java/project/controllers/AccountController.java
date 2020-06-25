package project.controllers;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import project.domain.Account;
import project.domain.AccountFormObject;
import project.domain.Connection;
import project.services.AccountService;
import project.services.ConnectionService;

@Controller
public class AccountController {
    
    @Autowired
    public AccountService accountService;
    
    @Autowired
    public ConnectionService connectionService;
    
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
        if (accountService.notLoggedIn()) {
            return "register";
        }
        return "redirect:/index"; // user logged in, cant register before logout
    }
    
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute AccountFormObject accountFormObject, BindingResult bindingResult, RedirectAttributes redirectAttributes) { // AccountFormObject used to validate fields
        if (bindingResult.hasErrors()) {
            return "register";
        }
        
        accountService.createFromFormObject(accountFormObject);
        
        redirectAttributes.addFlashAttribute("registerParam", "success");
        return "redirect:/login";
    }
    
    @GetMapping("/profile")
    public String showCurrentProfile(Model model) {
        if (accountService.notLoggedIn()) {
            return "redirect:/login";
        }
        
        Account currentAccount = accountService.getCurrentAccount();
        return "redirect:/account/" + currentAccount.getProfileId();
    }
    
    @GetMapping("/account/{profileId}")
    public String showAccount(Model model, @PathVariable String profileId) {
        if (accountService.notLoggedIn()) {
            return "redirect:/login";
        }
        
        Account account = accountService.getOneByProfileId(profileId);
        
        if (account == null) {      // redirect if account doesn't exist
            return "redirect:/profile";
        }
        
        String currentUserProfileId = accountService.getCurrentAccount().getProfileId();
        if (!currentUserProfileId.equals(profileId)) { // runs if the user is visiting someone else's page
            
            // get connection status between the current user and the visited page's user (null if no connection)
            model.addAttribute("mutualConnection", connectionService.getConnectionBetween(currentUserProfileId, profileId)); // FIX: EI TOIMI VASTAANOTTAJALLA LÄHETTÄJÄN SIVULLA
            model.addAttribute("ownPage", false);
        } else {                                      // runs if the user is visiting their own page
            model.addAttribute("requesterAccounts", accountService.getRequesterAccounts(profileId, connectionService.getConnectionsOfTarget(profileId, false))); // get unconfirmed connection requests
            model.addAttribute("connectedAccounts", accountService.getConnectedAccounts(profileId, connectionService.getConnectionsOf(profileId)));               // get connected accounts
            model.addAttribute("ownPage", true);
        }
        
        model.addAttribute("account", account);
        return "account";
    }
    
    @PostMapping("/search")
    public String search(@RequestParam String keyword, RedirectAttributes redirectAttributes) {
        if (accountService.notLoggedIn()) {
            return "redirect:/login";
        }     
        
        List<Account> accounts = accountService.searchWithKeyword(keyword);
        redirectAttributes.addFlashAttribute("accounts", accounts);
        return "redirect:/search";
    }
    
    @GetMapping("/search")
    public String showSearchResults(Model model, @ModelAttribute("accounts") ArrayList<Account> accounts) {
        if (accountService.notLoggedIn()) {
            return "redirect:/login";
        }
        
        model.addAttribute("accounts", accounts);
        return "search";
    }
    
    @PostMapping("/account/{profileId}/connect/request")
    public String connectionRequest(@PathVariable String profileId) {
        connectionService.requestConnection(accountService.getCurrentAccount().getProfileId(), profileId);
        return "redirect:/account/" + profileId;
    }
    
    @PostMapping("/account/{profileId}/connect/answer")
    public String connectionAnswer(@RequestParam String requesterProfileId, 
                                   @RequestParam boolean answer, 
                                   @PathVariable String profileId) {
        connectionService.answerConnection(requesterProfileId, profileId, answer);
        return "redirect:/profile";
    }
}
