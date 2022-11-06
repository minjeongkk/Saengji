package JDBC.Account;

import JDBC.JDBC_Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountJdbc {
    // 계좌 저장; ID값 리턴(계좌아이디)
    public int saveAccount(Account account){
        String sql = "INSERT INTO account(accountNum, bank, amount, checkCredit, userId, mainAccount) "+
                "VALUES(?,?,?,?,?,?)";
        ResultSet rs = null;
        int result = 0;

        try{
            Connection conn = JDBC_Util.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, account.getAccountNum());
            pstmt.setString(2, account.getBank());
            pstmt.setInt(3, account.getAmount());
            pstmt.setString(4, account.getCheckCredit());
            pstmt.setInt(5, account.getUserId());
            pstmt.setInt(6, account.getMainAccount());

            int rowAffected = pstmt.executeUpdate();
            if(rowAffected == 1) {
                // get ID
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    result = rs.getInt(1);
                }
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }finally{
            try{
                if(rs!=null) rs.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }
        return result;
    }

    //김희원
    public int updateBalance(Account account, int isIncome, int money) {
        Statement stmt = null;
        ResultSet rs = null;

        int result = 0;
        int accountID = account.getID();
        int accountAmount = account.getAmount();
        int sign = (int) (Math.pow(-1, isIncome + 1));

        String sql = "UPDATE account " +
                "SET amount = ? " +
                " WHERE ID = " + accountID;

        try {
            Connection conn = JDBC_Util.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, (accountAmount + sign * money));
            System.out.println(pstmt);

            int rowAffected = pstmt.executeUpdate();
            if (rowAffected == 1) {
                rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    result = rs.getInt(1);
                }
            }
            return result;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return result;
    }

    public List<Account> findAllAccounts(){
        String sql = "select * from account";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = JDBC_Util.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Account> accountList = new ArrayList<>();
            while(rs.next()) {
                Account account = new Account();
                account.setAccountNum(rs.getString("accountNum"));
                account.setBank(rs.getString("bank"));
                account.setAmount(rs.getInt("amount"));
                account.setUserId(rs.getInt("userId"));
                account.setCheckCredit(rs.getString("checkCredit"));
                account.setUserId(rs.getInt("userId"));
                account.setMainAccount(rs.getInt("mainAccount"));
                accountList.add(account);
            }
            return accountList;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public int setMainAccount(int accountId){
        int result=0;
        String sql = "UPDATE account " +
                "SET mainAccount = 0 "+
                "WHERE mainAccount = 1;";
        String sql_new = "UPDATE account " +
                "SET mainAccount = 1 "+
                "WHERE ID=?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        try {
            conn = JDBC_Util.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt2 = conn.prepareStatement(sql_new,Statement.RETURN_GENERATED_KEYS);
            pstmt2.setInt(1,accountId);

            int rowAffected1 = pstmt.executeUpdate();
            int rowAffected2 = pstmt2.executeUpdate();
            if(rowAffected1 == 1 && rowAffected2 == 1)
            {
                // get candidate id
                rs = pstmt.getGeneratedKeys();
                if(rs.next())
                    result= rs.getInt(1);
                conn.commit();
            }else{
                conn.rollback();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        return result;
    }
}
