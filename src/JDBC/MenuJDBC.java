package JDBC;

import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MenuJDBC {

    // Expense 추가
    public static void addExpense(Integer userId, Integer expense, Date expenseDate,
                                  String content, String category, Integer accountId){

        //for insert a new expense
        ResultSet rs = null;
        int nowAmount = 0;
        int result = 1;

        String sql_select = "SELECT amount from account WHERE ID = ?";

        String sql_insert = "INSERT INTO saengji.expense(userId, expense, expenseDate, content, category, accountId)"
                + "VALUES(?,?,?,?,?,?)";

        String sql_account = "UPDATE account " +
                "SET amount = ? "+
                "WHERE ID=?";


        try{
            Connection conn = JDBC_Util.getConnection();

            //현재 잔액 가져오기
            PreparedStatement pstmt0 = conn.prepareStatement(sql_select, Statement.RETURN_GENERATED_KEYS);
            pstmt0.setInt(1, accountId);
            rs = pstmt0.executeQuery();
            while(rs.next()) {
                nowAmount = rs.getInt("amount");
            }

            //insert new income
            conn.setAutoCommit(false);
            PreparedStatement pstmt1 = conn.prepareStatement(sql_insert, Statement.RETURN_GENERATED_KEYS);

            pstmt1.setInt(1, userId);
            pstmt1.setInt(2, expense);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            pstmt1.setDate(3,Date.valueOf(df.format(expenseDate)));
            pstmt1.setString(4,content);
            pstmt1.setString(5,category);
            pstmt1.setInt(6,accountId);

            //update account
            PreparedStatement pstmt2 = conn.prepareStatement(sql_account, Statement.RETURN_GENERATED_KEYS);
            pstmt2.setInt(1,nowAmount-expense);
            pstmt2.setInt(2,accountId);

            int rowAffected1 = pstmt1.executeUpdate();
            int rowAffected2 = pstmt2.executeUpdate();
            if(rowAffected1 == 1 && rowAffected2 == 1) {
                // get candidate id
                rs = pstmt1.getGeneratedKeys();
                if(rs.next())
                    result= rs.getInt(1);
                conn.commit();
            }else{
                conn.rollback();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }



    // Income 추가
    public static void addIncome(Integer userId, Integer income, Date incomeDate,
                                 String content, String category, Integer accountId){

        // for insert a new income
        ResultSet rs = null;
        int nowAmount = 0;
        int result = 1;

        String sql_select = "SELECT amount from account WHERE ID = ?";

        String sql_insert = "INSERT INTO income(userId, income, incomeDate, content, category, accountId) "+
                "VALUES(?,?,?,?,?,?)";

        String sql_account = "UPDATE account " +
                "SET amount = ? "+
                "WHERE ID=?";


        try{
            Connection conn = JDBC_Util.getConnection();

            //현재 잔액 가져오기
            PreparedStatement pstmt0 = conn.prepareStatement(sql_select, Statement.RETURN_GENERATED_KEYS);
            pstmt0.setInt(1, accountId);
            rs = pstmt0.executeQuery();
            while(rs.next()) {
                nowAmount = rs.getInt("amount");
            }

            //insert new income
            conn.setAutoCommit(false);
            PreparedStatement pstmt1 = conn.prepareStatement(sql_insert, Statement.RETURN_GENERATED_KEYS);

            pstmt1.setInt(1, userId);
            pstmt1.setInt(2, income);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            pstmt1.setDate(3,Date.valueOf(df.format(incomeDate)));
            pstmt1.setString(4,content);
            pstmt1.setString(5,category);
            pstmt1.setInt(6,accountId);

            //update account
            PreparedStatement pstmt2 = conn.prepareStatement(sql_account, Statement.RETURN_GENERATED_KEYS);
            pstmt2.setInt(1,nowAmount+income);
            pstmt2.setInt(2,accountId);

            int rowAffected1 = pstmt1.executeUpdate();
            int rowAffected2 = pstmt2.executeUpdate();
            if(rowAffected1 == 1 && rowAffected2 == 1) {
                // get candidate id
                rs = pstmt1.getGeneratedKeys();
                if(rs.next())
                    result= rs.getInt(1);
                conn.commit();
            }else{
                conn.rollback();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    // 일일 총지출, 총수입 합산 및 일일 카테고리별 합산
    public static List searchDaySum(Integer userId, Date expenseDate, Date incomeDate) {

        String sqlSUM_expense = "SELECT SUM(expense) as all_sum_expense FROM saengji.expense WHERE userId = ? && expenseDate=?;";
        String sqlSUM_income = "SELECT SUM(income) as all_sum_income FROM saengji.income WHERE userId = ? && incomeDate=?;";
        String sqlSELECT_e_category = "SELECT SUM(expense) as cate_sum_expense, category FROM saengji.expense WHERE userId = ? && expenseDate = ? group by category;";
        String sqlSELECT_i_category = "SELECT SUM(income) as cate_sum_income, category FROM saengji.income WHERE userId = ? && incomeDate = ? group by category;";

        try (Connection conn = JDBC_Util.getConnection();
             PreparedStatement pstmt1 = conn.prepareStatement(sqlSUM_expense);
             PreparedStatement pstmt2 = conn.prepareStatement(sqlSUM_income);
             PreparedStatement pstmt3 = conn.prepareStatement(sqlSELECT_e_category);
             PreparedStatement pstmt4 = conn.prepareStatement(sqlSELECT_i_category);
        ){

            pstmt1.setInt(1, userId);
            pstmt1.setDate(2, expenseDate);

            pstmt2.setInt(1, userId);
            pstmt2.setDate(2, incomeDate);

            pstmt3.setInt(1,userId);
            pstmt3.setDate(2, expenseDate);

            pstmt4.setInt(1,userId);
            pstmt4.setDate(2, incomeDate);

            ResultSet rs1 = pstmt1.executeQuery();
            ResultSet rs2 = pstmt2.executeQuery();
            ResultSet rs3 = pstmt3.executeQuery();
            ResultSet rs4 = pstmt4.executeQuery();

            List temp1 = ResultToList(rs1);
            List temp2 = ResultToList(rs2);
            List temp3 = ResultToList(rs3);
            List temp4 = ResultToList(rs4);

            List join_temp = new ArrayList();
            join_temp.addAll(temp1);
            join_temp.addAll(temp2);
            join_temp.addAll(temp3);
            join_temp.addAll(temp4);

            return join_temp;

        } catch (SQLException ex) {

            System.out.println(ex.getMessage());

            return null;

        }


    }

    // 일일 지출, 수입 목록
    public static List searchDayList(Integer userId, Date expenseDate, Date incomeDate) {

        String sqlList_expense = "SELECT expense, expenseDate, content, category, bank FROM saengji.expense " +
                "INNER JOIN account ON expense.accountId = account.ID WHERE expense.userId = ? && expense.expenseDate = ?;";
        String sqlList_income = "SELECT income, incomeDate, content, category, bank FROM saengji.income " +
                "INNER JOIN account ON income.accountId = account.ID WHERE income.userId = ? && income.incomeDate = ?;";


        try (Connection conn = JDBC_Util.getConnection();
             PreparedStatement pstmt1 = conn.prepareStatement(sqlList_expense);
             PreparedStatement pstmt2 = conn.prepareStatement(sqlList_income );
        ){

            pstmt1.setInt(1, userId);
            pstmt1.setDate(2, expenseDate);

            pstmt2.setInt(1, userId);
            pstmt2.setDate(2, incomeDate);

            ResultSet rs1 = pstmt1.executeQuery();
            ResultSet rs2 = pstmt2.executeQuery();

            List temp1 = ResultToList(rs1);
            List temp2 = ResultToList(rs2);

            List join_temp = new ArrayList();
            join_temp.addAll(temp1);
            join_temp.addAll(temp2);

            return join_temp;

        } catch (SQLException ex) {

            System.out.println(ex.getMessage());

            return null;

        }

    }

    // ResultSet to List
    public static List<Map<String, Object>> ResultToList(ResultSet rs) throws SQLException {

        List<Map<String, Object>> result = new ArrayList<>();
        ResultSetMetaData temp_rs = rs.getMetaData();
        int size_column = temp_rs.getColumnCount();

        while (rs.next()) {

            Map<String, Object> row = new HashMap<>();

            for (int i = 1; i <= size_column; i++) {

                String columnName = temp_rs.getColumnName(i);
                Object columnValue = rs.getObject(i);
                row.put(columnName, columnValue);

            }
            result.add(row);
        }
        return result;
    }

    public static void main(String[] args) {

        // ! search Day Sum test
        // System.out.println(searchDaySum(1, Date.valueOf("2020-01-01"), Date.valueOf("2020-01-01")));

        // ! search Day List test

        //expense, expenseDate, content, category, bank
        List a = searchDayList(1, Date.valueOf("2020-01-01"), Date.valueOf("2020-01-01"));
        for(int i = 0; i < a.size(); i ++){
            Map map = (Map) a.get(i);
            if(map.get("expense") != null) {
                System.out.println(map.get("expense") + "\t" + map.get("expenseDate") + "\t" + map.get("content")
                        + "\t" + map.get("category") + "\t" + map.get("bank"));
            }
            else if(map.get("income") != null){
                System.out.println(map.get("income") + "\t" + map.get("incomeDate") + "\t" + map.get("content")
                        + "\t" + map.get("category") + "\t" + map.get("bank"));
            }
        }





        // ! add Income test
        // addIncome(1, 7000, Date.valueOf("2020-01-01"), "bb", "cc", 1);

        // ! add Expense test
        // addExpense(1, 5000, Date.valueOf("2020-01-01"), "bb", "cc", 1);

    }
}
