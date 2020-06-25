package project.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.domain.Account;
import project.domain.AccountFormObject;
import project.domain.Connection;
import project.repositories.AccountRepository;
import project.repositories.ConnectionRepository;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepo;
    
    @Autowired
    private PasswordEncoder pwEncoder;
    
    
    public void createFromFormObject(AccountFormObject accountFormObject) {
        Account dbAccount = new Account();
        dbAccount.setName(accountFormObject.getName());
        dbAccount.setUsername(accountFormObject.getUsername());
        dbAccount.setPassword(pwEncoder.encode(accountFormObject.getPassword()));
        dbAccount.setProfileId(accountFormObject.getProfileId());
        accountRepo.save(dbAccount);
    }

    public Account getOneByProfileId(String profileId) {
        return accountRepo.findByProfileId(profileId);
    }

    public boolean notLoggedIn() {
        return SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;
    }

    public Account getOneByUsername(String username) {
        return accountRepo.findByUsername(username);
    }
    
    public Account getOneByName(String name) {
        return accountRepo.findByName(name);
    }

    public Account getCurrentAccount() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getOneByUsername(username);
    }

    public List<Account> searchWithKeyword(String keyword) {
        return accountRepo.findByNameIgnoreCaseContaining(keyword);
    }

    public List<Account> getConnectedAccounts(String profileId, List<Connection> connections) {
        if (connections.isEmpty()) return null;
        
        List<Account> connectedAccounts = new ArrayList<>();
        
        connections.forEach(conn -> {      // for each connection, find the account that is not the current user's account (so you don't list yourself in your connections)
            if (conn.getRequesterProfileId().equals(profileId)) {
                connectedAccounts.add(accountRepo.findByProfileId(conn.getTargetProfileId()));
            } else if (conn.getTargetProfileId().equals(profileId)) {
                connectedAccounts.add(accountRepo.findByProfileId(conn.getRequesterProfileId()));
            }
        });
        return connectedAccounts;
    }
    
    public List<Account> getRequesterAccounts(String profileId, List<Connection> connections) {
        if (connections.isEmpty()) return null;
        
        List<Account> connectedAccounts = new ArrayList<>();
        
        connections.forEach(conn -> {      // for each connection, find the requester account
            connectedAccounts.add(accountRepo.findByProfileId(conn.getRequesterProfileId()));
        });
        
        return connectedAccounts;
    }
    
}
