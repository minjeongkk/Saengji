package JDBC.Statistic;

import JDBC.JDBC_Util;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

//import com.mysql.cj.jdbc.result.ResultSetMetaData;

//카테고리 통계 조회
//일간, 주간, 월간, 연간 - 카태고리별 통계 조회
public class StatisticJdbc {
    //static int userId;
    //static String Today;

    //static String url = "jdbc:mysql://localhost:3306/saengji";
    //static String user = "root";
    //static String password = "km@1032203";

    //월간  지출, 수입 카테고리 별  조회
    public static List monthCategory(Integer userId, String Today) {
        StringTokenizer std = new StringTokenizer(Today, "-");
        String Year = std.nextToken(); //2022
        String Month = std.nextToken(); //10
        int year = Integer.parseInt(Year);
        int month = Integer.parseInt(Month);

        int last_day;
        if(month %2 !=0 && month!=8) {
            last_day = 30;
        }
        else {
            last_day = 31;
        }

        //2월일 경우 (윤년인지 아닌지) 한 번 더 검사
        if(month == 2) {
            if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
                last_day = 29;
            } else {
                last_day = 28;
            }
        }
        String Day = Integer.toString(last_day);
        //Connection conn = null;

        // 월간 지출 조회 sql 쿼리 문
        String sql1 = "SELECT category, SUM(expense) as sum_expense FROM expense" +
                " WHERE userId = " + userId + " AND expenseDate >= '" + Year + "-" + Month + "-01'"
                + " AND expenseDate <= '" + Year + "-" + Month + "-" + Day + "' GROUP BY category";
        // 월간 수입 조회 sql 쿼리 문
        String sql2 = "SELECT category, SUM(income) as sum_income FROM income" +
                " WHERE userId = " + userId + " AND incomeDate >='" + Year + "-" + Month + "-01'"
                + " AND incomeDate <= '" + Year + "-" + Month + "-" + Day + "' GROUP BY category";


        try (Connection conn = JDBC_Util.getConnection();
             PreparedStatement pstmt1 = conn.prepareStatement(sql1);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2);
        ){

            ResultSet rs1 =pstmt1.executeQuery();
            ResultSet rs2 =pstmt2.executeQuery();

            List monthCategory = new ArrayList();

            List temp1 = ResultToList(rs1);
            List temp2 = ResultToList(rs2);

            monthCategory.addAll(temp1);
            monthCategory.addAll(temp2);

            return monthCategory;

        }catch(SQLException e) { //Exception
            System.out.println(e.getMessage());
            return null;

        }


    }

    //연간 지출 합 조회
    public static List yearCategory(Integer userId, String Today) {
        StringTokenizer std = new StringTokenizer(Today, "-");
        String Year = std.nextToken(); //2022
        String Month = std.nextToken(); //10
        int year = Integer.parseInt(Year);

        // 연간 지출 조회 sql 쿼리 문
        String sql1 = "SELECT category, SUM(expense) as sum_expense FROM expense" +
                " WHERE userId = " +userId+ " AND expenseDate >='" +Year+ "-01-01'"
                + " AND expenseDate <= '"+Year+"-12-31' GROUP BY category";

        // 연간 수입 조회 sql 쿼리 문
        String	sql2 = "SELECT category, SUM(income) as sum_income FROM income" +
                " WHERE userId = " +userId+ " AND incomeDate >='" +Year+ "-01-01'"
                + " AND incomeDate <= '" +Year+ "-12-31' GROUP BY category";



        try (Connection conn = JDBC_Util.getConnection();
             PreparedStatement pstmt1 = conn.prepareStatement(sql1);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2);
        ){

            ResultSet rs1 = pstmt1.executeQuery();
            ResultSet rs2 = pstmt2.executeQuery();

            List yearCategory = new ArrayList();

            List temp1 = ResultToList(rs1);
            List temp2 = ResultToList(rs2);

            yearCategory.addAll(temp1);
            yearCategory.addAll(temp2);

            return yearCategory;

        }catch(SQLException ex) {
            System.out.println(ex.getMessage());
            return null;

        }

    }

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


}