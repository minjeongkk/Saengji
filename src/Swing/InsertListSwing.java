package Swing;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class InsertListSwing extends JFrame {
    public InsertListSwing(){
        setTitle("내역 입력");

        Container c = getContentPane();
        c.setLayout(new FlowLayout());
        JPanel jPanel = new JPanel();
        JButton btn_income = new JButton("수입 입력");
        JButton btn_expense = new JButton("지출 입력");

        btn_income.addActionListener(new MyActionListener());
        btn_expense.addActionListener(new MyActionListener());

        jPanel.add(btn_income);
        jPanel.add(btn_expense);

        c.add(jPanel);
        setSize(350, 200);


        Dimension frameSize = getSize();

        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((windowSize.width - frameSize.width) / 2,
                (windowSize.height - frameSize.height) / 2); //화면 중앙에 띄우기

        setVisible(true);
    }

    private class MyActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            JButton b = (JButton) e.getSource();
            if (b.getText().equals("수입 입력")){
                new IncomeSwing();
                setVisible(false);
            }
            else if(b.getText().equals("지출 입력")) {
                new ExpenseSwing();
                setVisible(false);
            }
            else{
                // 메인 화면으로 돌아가기
            }
        }
    }

    public static void main(String[] agrs){
        new InsertListSwing();
    }
}
