package Swing;


import JDBC.Goal.Goal;
import JDBC.Goal.GoalJdbc;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GoalsSwing extends JFrame {
    GoalJdbc goalJdbc = new GoalJdbc();
    List<Goal> goalList = new ArrayList<>();

    public GoalsSwing(){
        goalList = goalJdbc.listGoal();

        setTitle("목표목록");
        Container c = getContentPane();

        setLayout(new FlowLayout(FlowLayout.CENTER));

        //JPanel p1
        JPanel p1 = new JPanel();
        JLabel title = new JLabel("목표목록");
        title.setFont(new Font("맑은고딕",Font.BOLD,20));
        title.setSize(300,10);
        p1.add(title);
        c.add(p1);

        //JPanel p2
        ButtonGroup g = new ButtonGroup();
        for(int i=0;i<goalList.toArray().length;i++){
            JPanel panel = new JPanel();
            panel.setBorder(new LineBorder(Color.BLACK));
            panel.setLayout(new GridLayout(2,1));

            JPanel p = new JPanel();
            //목표 시작 날짜
            String start = String.valueOf(goalList.get(i).getGoalStartDate());
            p.add(new JLabel(start,Label.LEFT));

            String connect = "~";
            p.add(new JLabel(connect,Label.LEFT));

            //목표 종료 날짜
            String end = String.valueOf(goalList.get(i).getGoalEndDate());
            p.add(new JLabel(end,Label.LEFT));

            String connect2 = "동안 ";
            p.add(new JLabel(connect2,Label.LEFT));

            //잔액
            String amount = String.valueOf(goalList.get(i).getGoalAmount());
            p.add(new JLabel(amount,Label.LEFT));

            String connect3 = "원 모으기";
            p.add(new JLabel(connect3,Label.LEFT));

            JPanel p2 = new JPanel();
            if(goalList.toArray().length!=0){
                int result = goalJdbc.achieveGoal(goalJdbc.getGoalId());
                if(result==1){
                    JLabel success = new JLabel("이걸해냄",Label.LEFT);
                    success.setForeground(Color.red);
                    p2.add(success);
                }else{
                    JLabel failure = new JLabel("이게 안되네",Label.LEFT);
                    failure.setForeground(Color.blue);
                    p2.add(failure);
                }
            }

            panel.add(p);
            panel.add(p2);
            c.add(panel);
            p.setSize(350,200);
        }

        if(goalList.toArray().length==0){
            JPanel p3 = new JPanel();
            JButton b1=  new JButton("목표 추가");
            p3.add(b1);
            b1.addActionListener(new Listener(this));
            p3.setSize(300,10);
            c.add(p3);
        }else{
            JPanel p3 = new JPanel();
            JButton b1=  new JButton("목표 삭제");
            p3.add(b1);
            b1.addActionListener(new DeleteListener(this));
            p3.setSize(300,10);
            c.add(p3);
        }

        setSize(350,400);

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
            setVisible(false);
            new AddGoalSwing();
        }
    }

    class DeleteListener implements ActionListener {
        JFrame frame;
        public DeleteListener(JFrame f){
            frame=f;
        }
        @Override
        public void actionPerformed(ActionEvent arg0){
            goalJdbc.deleteGoal();
            JOptionPane.showMessageDialog(null,"목표 삭제가 완료되었습니다.","Messeage",JOptionPane.PLAIN_MESSAGE);
            setVisible(false);
            new GoalsSwing();
        }
    }

    public static void main(String[] args){
        new GoalsSwing();
    }
}