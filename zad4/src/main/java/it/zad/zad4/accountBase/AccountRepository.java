package it.zad.zad4.accountBase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AccountRepository {
    List<Account> accounts = new ArrayList<>();

    {
        accounts.add(new Account("user1", "lozinka1", "DATE"));
        accounts.add(new Account("user2", "lozinka2", "HANGMAN"));
        accounts.add(new Account("user3", "lozinka3", "HANGMAN"));
        accounts.add(new Account("user4", "lozinka4", "DATE"));
    }

    public Account checkAccount(String username, String password) {
        return accounts.stream()
                .filter(x -> x.getUsername().equals(username) && x.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    }
}
