

public class Account{
    private string name;
    private string password;
    private string role;

    public Account(string name, string password, string role){
        this.name= name;
        this.password = password;
        this.role = role;
    }

     public string getName(){
        return name;
    }

    public string getPassword(){
        return password;
    }

    public string getRole(){
        return role;
    }
}