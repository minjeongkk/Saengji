package Swing;

import JDBC.User.User;
import JDBC.User.UserJdbc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpSwing extends JFrame {
    JTextField userID, userPW, userPhone, userBirth, userName;

    public SignUpSwing(){
        setTitle("회원가입");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();

        setLayout(new FlowLayout(FlowLayout.CENTER));
        setLocationRelativeTo(null);
        setResizable(false);

        //JPanel p1
        JPanel p1 = new JPanel();
        JLabel title = new JLabel("회원가입");
        title.setFont(new Font("맑은고딕",Font.BOLD,20));
        title.setSize(210,10);
        p1.add(title);

        //JPanel p2
        JPanel p21 = new JPanel();
        p21.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
        p21.add(new JLabel("아이디",Label.LEFT));
        userID = new JTextField("",20);
        p21.add(userID);

        JPanel p22 = new JPanel();
        p22.add(new JLabel("패스워드",Label.LEFT));
        userPW = new JTextField("",20);
        p22.add(userPW);

        JPanel p23 = new JPanel();
        p23.add(new JLabel("전화번호",Label.LEFT));
        userPhone = new JTextField("",20);
        p23.add(userPhone);

        JPanel p24 = new JPanel();
        p24.add(new JLabel("생년월일",Label.LEFT));
        userBirth = new JTextField("",20);
        p24.add(userBirth);

        JPanel p25 = new JPanel();
        p25.setBorder(BorderFactory.createEmptyBorder(5,20,0,0));
        p25.add(new JLabel("이름",Label.LEFT));
        userName = new JTextField("",20);
        p25.add(userName);


        p21.setSize(210,50);
        p22.setSize(210,50);
        p23.setSize(210,50);
        p24.setSize(210, 50);

        //JPanel p3
        JPanel p3 = new JPanel();
        JButton b1=  new JButton("회원가입");
        p3.add(b1);
        b1.addActionListener(new Listener(this));
        p3.setSize(210,10);

        JPanel p4 = new JPanel();
        JButton b2=  new JButton("돌아가기");
        p4.add(b2);
        b2.addActionListener(new backhome());
        p4.setSize(210,10);

        c.add(p1);
        c.add(p25);
        c.add(p21);
        c.add(p22);
        c.add(p23);
        c.add(p24);
        c.add(p3);
        c.add(p4);
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
            String singupID = userID.getText();
            String sPW = userPW.getText();
            String sPNUM = userPhone.getText();
            String sName = userName.getText();
            String sBirth = userBirth.getText();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date Birth = null;
            try {
                Birth = df.parse(sBirth);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }


            //UserJDBC
            UserJdbc userJdbc = new UserJdbc();
            System.out.println(userJdbc.saveUser(new User(singupID, sPW, sPNUM, Birth, sName)));
            JOptionPane.showMessageDialog(null,"회원가입이 완료되었습니다.","Messeage",JOptionPane.PLAIN_MESSAGE);
        }
    }
    // 돌아가기 버튼 클릭 이벤트 ( 회원가입 창은 안보이고 로그인창 띄우기)
    class backhome implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            new LoginSwing();


        }
    }
}




