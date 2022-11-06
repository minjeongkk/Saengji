package JDBC.Goal;

import java.util.Date;

public class Goal {
    private int ID;
    private int userId;
    private int goalAmount;
    private Date goalStartDate;
    private Date GoalEndDate;

    //constructor
    public Goal(){}
    public Goal(int userId, int goalAmount, Date goalStartDate, Date goalEndDate) {
        this.userId = userId;
        this.goalAmount = goalAmount;
        this.goalStartDate = goalStartDate;
        GoalEndDate = goalEndDate;
    }

    //getter, setter
    public void setID(int id) {
        ID = id;
    }

    public int getId() {
        return ID;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(int goalAmount) {
        this.goalAmount = goalAmount;
    }

    public Date getGoalStartDate() {
        return goalStartDate;
    }

    public void setGoalStartDate(Date goalStartDate) {
        this.goalStartDate = goalStartDate;
    }

    public Date getGoalEndDate() {
        return GoalEndDate;
    }

    public void setGoalEndDate(Date goalEndDate) {
        GoalEndDate = goalEndDate;
    }
}
