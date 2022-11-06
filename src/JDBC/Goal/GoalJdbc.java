package JDBC.Goal;

import JDBC.JDBC_Util;

import static java.sql.Date.valueOf;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class GoalJdbc {
    public int saveGoal(Goal goal){
        ResultSet rs = null;
        int result = 0;

        String sql = "INSERT INTO goal(userId, goalAmount, goalStartDate, GoalEndDate) "+
                "VALUES(?,?,?,?)";

        try{
            Connection conn = JDBC_Util.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setInt(1, goal.getUserId());
            pstmt.setInt(2, goal.getGoalAmount());
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            pstmt.setDate(3, valueOf(df.format(goal.getGoalStartDate())));
            pstmt.setDate(4, valueOf(df.format(goal.getGoalEndDate())));

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

    //1 반환시 목표 달성 성공, 0 반환시 목표 달성실패
    public int achieveGoal(int goalId){
        // for select a user
        String sql = "SELECT * "+
                "FROM goal "+
                "WHERE ID="+goalId+"";

        Statement stmt = null;
        ResultSet rs = null;
        try {
            Connection conn = JDBC_Util.getConnection();
            stmt  = conn.createStatement();
            rs = stmt.executeQuery(sql);
            Goal goal = new Goal();
            while(rs.next()) {
                goal.setID(goalId);
                goal.setUserId(rs.getInt("userId"));
                goal.setGoalAmount(rs.getInt("goalAmount"));
                goal.setGoalStartDate(rs.getDate("goalStartDate"));
                goal.setGoalEndDate(rs.getDate("goalEndDate"));
            }

            //goalPeriod내에 목표한 금액만큼 모았는지 확인//////////////////
            String sql2 = "SELECT sum(income), userId, incomeDate " +
                    "from income " +
                    "where incomeDate>='" + goal.getGoalStartDate() + "' " +
                    "and incomeDate<= '" + goal.getGoalEndDate() + "' " +
                    "and userId=" + goal.getUserId();

            stmt  = conn.createStatement();
            rs = stmt.executeQuery(sql2);
            int incomeAmount = 0;
            while(rs.next()) {
                incomeAmount = rs.getInt("sum(income)");
            }
            //////////////////////////////////////////////////////////
            if(incomeAmount>=goal.getGoalAmount()){
                return 1;
            }else{
                return 0;
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    // 목표를 리스트 형식으로 출력
    public List<Goal> listGoal(){
        // for select a goal
        ResultSet rs = null;
        Statement stmt = null;
        Connection conn = null;

        String sql = "SELECT * from goal";

        try {
            conn = JDBC_Util.getConnection();
            stmt  = conn.createStatement();
            rs = stmt.executeQuery(sql);

            List<Goal> goalList = new ArrayList<>();
            while(rs.next()) {
                Goal goal = new Goal();
                goal.setID(rs.getInt("ID"));
                goal.setUserId(rs.getInt("userId"));
                goal.setGoalAmount(rs.getInt("goalAmount"));
                goal.setGoalStartDate(rs.getDate("goalStartDate"));
                goal.setGoalEndDate(rs.getDate("goalEndDate"));
                goalList.add(goal);
            }
            return goalList;
        }catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public int getGoalId(){
        // for select a goal
        ResultSet rs = null;
        Statement stmt = null;
        Connection conn = null;

        String sql = "SELECT * from goal";
        try {
            conn = JDBC_Util.getConnection();
            stmt  = conn.createStatement();
            rs = stmt.executeQuery(sql);

            int goalId  = 0;
            while(rs.next()) {
                goalId = rs.getInt("ID");
            }

            return goalId;
        }catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    //목표 삭제
    public void deleteGoal(){
        // for select a goal
        Connection conn = null;

        int goalId = getGoalId();
        String sql = "DELETE FROM goal where ID = ?";
        try {
            conn = JDBC_Util.getConnection();

            PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, goalId);
            pstmt.execute();

        }catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
