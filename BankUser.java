public class BankUser {

	private final ID id = new ID();

	// the name of the bank user.
	private String username;

	// the password of the bank user.
    private String password;
    
    public BankUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

	public ID getID() {
        return id;
    }

    public String getUsername(){
    	return username;
    }

    public void setUsername(String username){
    	this.username = username;
    }

    public String getPassword(){
    	return password;
    }

    public void setPassword(String password){
    	this.password = password;
    }

    // a method that prints all the info and details of the bank user
    public void printUserInfo(){
    	System.out.println("Bank user ID: " + id); 
        System.out.println("Bank user name: " + username); 
        System.out.println("Bank user password: " + password);
        System.out.println(); 
    }
}