package Swing;

import JDBC.Account.Account;
import JDBC.Account.AccountJdbc;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

public class AccountsSwing extends JFrame {
    AccountJdbc accountJdbc = new AccountJdbc();
    List<Account> accountList = new ArrayList<>();
    List<JRadioButton> rbtnList = new ArrayList<>();

    public AccountsSwing(){
        accountList = accountJdbc.findAllAccounts();

        setTitle("계좌목록");
        Container c = getContentPane();

        setLayout(new FlowLayout(FlowLayout.CENTER));

        //JPanel p1
        JPanel p1 = new JPanel();
        JLabel title = new JLabel("계좌목록");
        title.setFont(new Font("맑은고딕",Font.BOLD,20));
        title.setSize(300,10);
        p1.add(title);
        c.add(p1);

        //JPanel p2
        ButtonGroup g = new ButtonGroup();
        for(int i=0;i<accountList.toArray().length;i++){
            JPanel p = new JPanel();
            p.setBorder(new LineBorder(Color.BLACK));
            p.setLayout(new FlowLayout(FlowLayout.CENTER));

            JRadioButton b1 = new JRadioButton();
            if(accountList.get(i).getMainAccount()==1){
                b1.setSelected(true);
            }
            g.add(b1);
            rbtnList.add(b1);
            b1.addItemListener(new itemEventListener());
            p.add(b1);

            //계좌번호
            JPanel p21 = new JPanel();
            p21.add(new JLabel("계좌번호",Label.LEFT));
            JTextField accountNum = new JTextField(accountList.get(i).getAccountNum(),10);
            p21.add(accountNum);
            accountNum.setEnabled(false);
            p21.setSize(300,50);
            p.add(p21);

            //은행
            JPanel p22 = new JPanel();
            p22.add(new JLabel("은행",Label.LEFT));
            JTextField bank = new JTextField(accountList.get(i).getBank(),10);
            p22.add(bank);
            bank.setEnabled(false);
            p22.setSize(300,50);
            p.add(p22);

            //잔액
            JPanel p23 = new JPanel();
            p23.add(new JLabel("잔액",Label.LEFT));
            JTextField amount = new JTextField(String.valueOf(accountList.get(i).getAmount()),10);
            p23.add(amount);
            amount.setEnabled(false);
            p23.setSize(300,50);
            p.add(p23);

            c.add(p);
            p.setSize(300,200);
        }

        JPanel p3 = new JPanel();
        JButton b1=  new JButton("계좌 추가");
        p3.add(b1);
        b1.addActionListener(new Listener(this));
        p3.setSize(300,10);
        c.add(p3);


        setSize(500,350);
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
            new AddAccountSwing();
        }
    }

    class itemEventListener implements ItemListener{
        @Override
        public void itemStateChanged(ItemEvent e) {
            for(int i=0;i<rbtnList.toArray().length;i++){
                if(rbtnList.get(i).isSelected()){
                    accountJdbc.setMainAccount(i+1);
                    JOptionPane.showMessageDialog(null,"대표계좌 설정이 완료되었습니다.","Messeage",JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
    }
    public static void main(String[] args){
        new AccountsSwing();
    }
}