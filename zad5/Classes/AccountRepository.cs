using System.Collections.Generic;
using System.Linq;

public class AccountRepository
{
    private List<Account> accounts = new List<Account>();

    public AccountRepository()
    {
        accounts.Add(new Account("user1", "lozinka1", "DATE"));
        accounts.Add(new Account("user2", "lozinka2", "HANGMAN"));
        accounts.Add(new Account("user3", "lozinka3", "HANGMAN"));
        accounts.Add(new Account("user4", "lozinka4", "DATE"));
    }

    public Account CheckAccount(string username, string password)
    {
        return accounts.FirstOrDefault(x => x.getName() == username && x.getPassword() == password);
    }
}
