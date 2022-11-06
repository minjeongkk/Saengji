package JDBC.Account;

public class Account {
    private int ID;
    private String accountNum;
    private String bank;
    private int amount;
    private String checkCredit;
    private int userId;
    private int mainAccount;

    //constructor
    public Account(){}
    public Account(String accountNum, String bank, int amount, String checkCredit, int userId, int mainAccount) {
        this.accountNum = accountNum;
        this.bank = bank;
        this.amount = amount;
        this.checkCredit = checkCredit;
        this.userId = userId;
        this.mainAccount = mainAccount;
    }

    //getter, setter
    public int getID() {
        return ID;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCheckCredit() {
        return checkCredit;
    }

    public void setCheckCredit(String checkCredit) {
        this.checkCredit = checkCredit;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMainAccount() {
        return mainAccount;
    }

    public void setMainAccount(int mainAccount) {
        this.mainAccount = mainAccount;
    }
}