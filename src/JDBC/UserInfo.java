package JDBC;

import java.io.*;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class UserInfo {
    public static void setUser(String userID) throws IOException {

        String sql_select = "SELECT ID from user WHERE userID = ?";

        ResultSet rs = null;
        int ID = 0;

        try{
            Connection conn = JDBC_Util.getConnection();

            //현재 잔액 가져오기
            PreparedStatement pstmt0 = conn.prepareStatement(sql_select, Statement.RETURN_GENERATED_KEYS);
            pstmt0.setString(1, userID);
            rs = pstmt0.executeQuery();
            while(rs.next()) {
                ID = rs.getInt("ID");
            }

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        File file = new File("./userInfo.txt");
        if (!file.exists()) {
            file.createNewFile();
        }

        file.setReadable(true);
        file.setWritable(true);

        BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));

        writer.write(Integer.toString(ID));
        System.out.println(ID);
        writer.flush(); // 버퍼의 남은 데이터를 모두 쓰기
        writer.close(); // 스트림 종료

        file.setReadable(false);
        file.setWritable(false);
    }

    public static Integer getUser() throws IOException {
        File file = new File("./userInfo.txt");
        file.setReadable(true);
        String ID = null;
        if(file.exists()){ // 파일이 존재하면
            BufferedReader reader = new BufferedReader(new FileReader(file));

            String line = null;
            while ((line = reader.readLine()) != null){
                ID = line;
            }

            reader.close();
        }
        file.setReadable(false);

        return Integer.parseInt(ID);
    }
}