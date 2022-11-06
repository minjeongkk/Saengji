package Swing;

import JDBC.MenuJDBC;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class IncomeSwing extends JFrame{
    JTextField J_income, J_incomeDate, J_content, J_category;

    public IncomeSwing(){
        setTitle("내역 입력");

        Container c = getContentPane();
        setLayout(new FlowLayout(FlowLayout.CENTER));

        //Panel 1
        JPanel jp1 = new JPanel();
        JLabel title = new JLabel("수입 내역 입력");
        title.setFont(new Font("맑은고딕",Font.BOLD,20));
        title.setSize(210,10);
        jp1.add(title);

        //Panel 2
        JPanel jp2_1 = new JPanel();
        jp2_1.add(new JLabel("수입 금액",JLabel.LEFT));
        J_income = new JTextField(20);
        jp2_1.add(J_income);

        JPanel jp2_2 = new JPanel();
        jp2_2.add(new JLabel("수입 날짜",JLabel.LEFT));
        J_incomeDate = new JTextField(20);
        jp2_2.add(J_incomeDate);

        JPanel jp2_3 = new JPanel();
        jp2_3.add(new JLabel("내용",JLabel.LEFT));
        J_content = new JTextField(20);
        jp2_3.add(J_content);

        JPanel jp2_4 = new JPanel();
        jp2_4.add(new JLabel("카테고리",JLabel.LEFT));
        J_category = new JTextField(20);
        jp2_4.add(J_category);

        jp2_1.setSize(210, 50);
        jp2_2.setSize(210, 50);
        jp2_3.setSize(210, 50);
        jp2_4.setSize(210, 50);


        //Panel 3
        JPanel jp3 = new JPanel();
        JButton btn_income = new JButton("등록!");
        jp3.add(btn_income);
        btn_income.addActionListener(new Listener(this));

        //container에 add
        c.add(jp1);
        c.add(jp2_1);
        c.add(jp2_2);
        c.add(jp2_3);
        c.add(jp2_4);

        c.add(jp3);

        setSize(300, 350);

        Dimension frameSize = getSize();

        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((windowSize.width - frameSize.width) / 2,
                (windowSize.height - frameSize.height) / 2); //화면 중앙에 띄우기

        setVisible(true);
    }

    class Listener implements ActionListener{
        JFrame frame;
        public Listener(JFrame f) {
            frame = f;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            //이거 어케 설정하지? 지금 유저 아이디 어디서 가져와서 조회해야하나
            Integer userId = 1;
            Integer income = Integer.valueOf(J_income.getText());
            Date incomeDate = Date.valueOf(J_incomeDate.getText());
            String content = J_content.getText();
            String category = J_category.getText();
            Integer accountId = 1;

            MenuJDBC.addIncome(userId, income, incomeDate, content, category, accountId);

            new SuccessSwing();
            setVisible(false);

        }
    }
}