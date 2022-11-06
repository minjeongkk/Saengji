package JDBC.Expense;

import java.util.Date;

public class Expense {
    private int ID;
    private int userId;
    private int expense;
    private Date expenseDate;
    private String content;
    private String category;
    private int accountId;

    // 생성자
    public Expense(){}
    public Expense(int userId, int expense, Date expenseDate, String content, int accountId) {
        this.userId = userId;
        this.expense = expense;
        this.expenseDate = expenseDate;
        this.content = content;
        this.accountId = accountId;
    }
    public Expense(int userId, int expense, Date expenseDate, String content, String category, int accountId) {
        this.userId = userId;
        this.expense = expense;
        this.expenseDate = expenseDate;
        this.content = content;
        this.category = category;
        this.accountId = accountId;
    }

    // getter, setter
    public int getID() {
        return ID;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getExpense() {
        return expense;
    }

    public void setExpense(int expense) {
        this.expense = expense;
    }

    public Date getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(Date expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }
}