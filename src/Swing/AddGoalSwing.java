package Swing;

import JDBC.Goal.Goal;
import JDBC.Goal.GoalJdbc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

public class AddGoalSwing extends JFrame{
    JTextField startYear, startMonth, startDay, endYear, endMonth, endDay, amount;

    public AddGoalSwing(){
        setTitle("목표등록");
        Container c = getContentPane();

        setLayout(new FlowLayout(FlowLayout.CENTER));

        //JPanel p1
        JPanel p1 = new JPanel();
        JLabel title = new JLabel("목표등록");
        title.setFont(new Font("맑은고딕",Font.BOLD,20));
        title.setSize(300,10);
        p1.add(title);

        //JPanel p2
        JPanel p2 = new JPanel();
        p2.add(new JLabel("시작 날짜",Label.LEFT));
        //startYear
        JPanel p21 = new JPanel();
        p21.add(new JLabel("Year",Label.LEFT));
        startYear = new JTextField("",5);
        p21.add(startYear);
        //startMonth
        JPanel p22 = new JPanel();
        p22.add(new JLabel("Month",Label.LEFT));
        startMonth = new JTextField("",3);
        p22.add(startMonth);
        //startDay
        JPanel p23 = new JPanel();
        p23.add(new JLabel("Day",Label.LEFT));
        startDay = new JTextField("",3);
        p23.add(startDay);
        p2.add(p21);
        p2.add(p22);
        p2.add(p23);
        p2.setSize(350,50);

        //JPanel p3
        JPanel p3 = new JPanel();
        p3.add(new JLabel("종료 날짜",Label.LEFT));
        //startYear
        JPanel p31 = new JPanel();
        p31.add(new JLabel("Year",Label.LEFT));
        endYear = new JTextField("",5);
        p31.add(endYear);
        //startMonth
        JPanel p32 = new JPanel();
        p32.add(new JLabel("Month",Label.LEFT));
        endMonth = new JTextField("",3);
        p32.add(endMonth);
        //startDay
        JPanel p33 = new JPanel();
        p33.add(new JLabel("Day",Label.LEFT));
        endDay = new JTextField("",3);
        p33.add(endDay);
        p3.add(p31);
        p3.add(p32);
        p3.add(p33);
        p3.setSize(350,50);

        //JPanel p4
        JPanel p4 = new JPanel();
        p4.add(new JLabel("목표 금액",Label.LEFT));
        amount = new JTextField("", 25);
        p4.add(amount);
        p4.setSize(350,50);

        //JPanel p5
        JPanel p5 = new JPanel();
        JButton b1=  new JButton("등록");
        p5.add(b1);
        b1.addActionListener(new AddGoalSwing.Listener(this));
        p5.setSize(350,10);

        c.add(p1);
        c.add(p2);
        c.add(p3);
        c.add(p4);
        c.add(p5);
        setSize(350,350);
        Dimension frameSize = getSize();

        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((windowSize.width - frameSize.width) / 2,
                (windowSize.height - frameSize.height) / 2); //화면 중앙에 띄우기
        setVisible(true);

    }

    class Listener implements ActionListener {
        JFrame frame;
        public Listener(JFrame f){
            frame=f;
        }
        @Override
        public void actionPerformed(ActionEvent arg0){
            int startYear_ = Integer.parseInt(startYear.getText());
            int startMonth_ = Integer.parseInt(startMonth.getText());
            int startDay_ = Integer.parseInt(startDay.getText());
            int endYear_ = Integer.parseInt(endYear.getText());
            int endMonth_ = Integer.parseInt(endMonth.getText());
            int endDay_ = Integer.parseInt(endDay.getText());
            int amount_ = Integer.parseInt(amount.getText());

            Calendar cal = Calendar.getInstance();
            cal.set(startYear_,startMonth_-1,startDay_); //10월 1일
            Date goalStartDate = cal.getTime();
            cal.set(endYear_,endMonth_-1,endDay_); //11월 1일
            Date goalEndDate = cal.getTime();

            GoalJdbc goalJdbc = new GoalJdbc();
            System.out.println(goalJdbc.saveGoal(new Goal(1, amount_, goalStartDate, goalEndDate)));
            JOptionPane.showMessageDialog(null,"목표 등록이 완료되었습니다.","Messeage",JOptionPane.PLAIN_MESSAGE);
            setVisible(false);
            new GoalsSwing();
        }
    }
    public static void main(String[] args){
        new AddGoalSwing();
    }
}