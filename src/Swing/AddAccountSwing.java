package Swing;

import JDBC.Account.Account;
import JDBC.Account.AccountJdbc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddAccountSwing extends JFrame {
    JTextField accountNum, bank, balance, checkCredit;
    JRadioButton check, credit;

    public AddAccountSwing(){
        setTitle("계좌등록");
        Container c = getContentPane();

        setLayout(new FlowLayout(FlowLayout.CENTER));

        //JPanel p1
        JPanel p1 = new JPanel();
        JLabel title = new JLabel("계좌등록");
        title.setFont(new Font("맑은고딕",Font.BOLD,20));
        title.setSize(210,10);
        p1.add(title);

        //JPanel p2
        JPanel p21 = new JPanel();
        p21.add(new JLabel("계좌번호",Label.LEFT));
        accountNum = new JTextField("",20);
        p21.add(accountNum);
        JPanel p22 = new JPanel();
        p22.add(new JLabel("은행사",Label.LEFT));
        bank = new JTextField("",20);
        p22.add(bank);
        JPanel p23 = new JPanel();
        p23.add(new JLabel("잔액",Label.LEFT));
        balance = new JTextField("",20);
        p23.add(balance);
        p21.setSize(210,50);
        p22.setSize(210,50);
        p23.setSize(210,50);
        // 라디오 버튼
        // 버튼 그룹 객체 생성
        JPanel p24 = new JPanel();
        ButtonGroup g = new ButtonGroup();
        // 라디오 버튼 2개 생성
        p24.add(new JLabel("체크/신용",Label.LEFT));
        check = new JRadioButton("체크카드");
        credit = new JRadioButton("신용카드");
        p24.add(check);
        p24.add(credit);
        p24.add(check);
        p24.add(credit);
        p24.setSize(210,50);

        //JPanel p3
        JPanel p3 = new JPanel();
        JButton b1=  new JButton("등록");
        p3.add(b1);
        b1.addActionListener(new Listener(this));
        p3.setSize(210,10);

        c.add(p1);
        c.add(p21);
        c.add(p22);
        c.add(p23);
        c.add(p24);
        c.add(p3);
        setSize(300,350);
        Dimension frameSize = getSize();

        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((windowSize.width - frameSize.width) / 2,
                (windowSize.height - frameSize.height) / 2); //화면 중앙에 띄우기
        setVisible(true);

    }

    class Listener implements ActionListener{
        JFrame frame;
        public Listener(JFrame f){
            frame=f;
        }
        @Override
        public void actionPerformed(ActionEvent arg0){
            String accountNum_ = accountNum.getText();
            String bank_ = bank.getText();
            int balance_ = Integer.parseInt(balance.getText());
            String checkCredit_ = "";
            if(check.isSelected()){
                checkCredit_="check";
            }else{
                checkCredit_="credit";
            }

            //AccountJdbc
            AccountJdbc accountJdbc = new AccountJdbc();
            System.out.println(accountJdbc.saveAccount(new Account(accountNum_, bank_, balance_, checkCredit_, 1, 0)));
            JOptionPane.showMessageDialog(null,"계좌 등록이 완료되었습니다.","Messeage",JOptionPane.PLAIN_MESSAGE);
        }
    }
    public static void main(String[] args){
        new AddAccountSwing();
    }
}