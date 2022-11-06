package JDBC.Income;

import JDBC.JDBC_Util;

import static java.sql.Date.valueOf;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class IncomeJdbc {
    public int saveIncome(Income income){
        // for insert a new income
        ResultSet rs = null;
        int result = 0;

        String sql = "INSERT INTO income(userId, income, incomeDate, content, category, accountId) "+
                "VALUES(?,?,?,?,?,?)";

        try{
            Connection conn = JDBC_Util.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, income.getUserId());
            pstmt.setInt(2, income.getExpense());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            pstmt.setDate(3,valueOf(df.format(income.getExpenseDate())));
            pstmt.setString(4,income.getContent());
            pstmt.setString(5,income.getCategory());
            pstmt.setInt(6,income.getAccountId());

            int rowAffected = pstmt.executeUpdate();
            if(rowAffected==1){
                //get user id
                rs=pstmt.getGeneratedKeys();
                if(rs.next()){
                    result=rs.getInt(1);
                }
            }
        }catch (
                SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(rs!=null) rs.close();
            }catch (SQLException e){
                System.out.println(e.getMessage());
            }
        }

        return result;
    }
}