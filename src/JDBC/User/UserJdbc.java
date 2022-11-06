package JDBC.User;

import JDBC.JDBC_Util;

import static java.sql.Date.valueOf;

import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class UserJdbc {
    public int saveUser(User user){
        // for insert a new user
        ResultSet rs = null;
        int result = 0;

        String sql = "INSERT INTO user(userId, userPw, phoneNumber, birthday, created, userName) "+
                "VALUES(?,?,?,?,?,?)";

        try{
            Connection conn = JDBC_Util.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getUserPw());
            pstmt.setString(3, user.getPhoneNumber());

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            pstmt.setDate(4, valueOf(df.format(user.getBirthday())));
            pstmt.setDate(5, valueOf(df.format(new Date())));
            pstmt.setString(6, user.getUserName());

            int rowAffected = pstmt.executeUpdate();
            if(rowAffected==1){
                //get user id
                rs=pstmt.getGeneratedKeys();
                if(rs.next()){
                    result=rs.getInt(1);
                }
            }
        }catch (SQLException e){
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

    public int login(String userId, String userPw){
        int result=0;
        // for select a user
        String sql = "select * "+
                "From user "+
                "WHERE userId='"+userId+"' "+
                "AND userPw='"+userPw+"'";
        System.out.println(sql);

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = JDBC_Util.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while(rs.next()) {
                result=rs.getInt("ID");
            }

            if(result==0){
                return 0;
            }else{
                return 1;
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
