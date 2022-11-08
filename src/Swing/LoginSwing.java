package Swing;

import JDBC.User.UserJdbc;
import JDBC.UserInfo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class LoginSwing extends JFrame {

    JTextField userID, userPW;
    public LoginSwing(){
        setTitle("생각하는 지갑");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container c = getContentPane();

        setResizable(false);              // 사용자가 화면 크기 조정 불가
        setLocationRelativeTo(null);     //프레임 화면 가운데에 띄우기
        setLayout(new FlowLayout(FlowLayout.CENTER));

        //app_name JPanel
        JPanel p0 = new JPanel();
        p0.setBorder(BorderFactory.createEmptyBorder(10,0,0,0));
        JLabel app_name = new JLabel("<생각하는 지갑>");
        app_name.setFont(new Font("monospaced",Font.BOLD,25));
        app_name.setForeground(Color.blue);
        app_name.setSize(210, 15);
        p0.add(app_name);

        //login JPanel
        JPanel p1 = new JPanel();
        JLabel title = new JLabel("로그인");
        title.setFont(new Font("맑은고딕",Font.BOLD,18));
        p1.setBorder(BorderFactory.createEmptyBorder(5,0,0,0));
        title.setSize(210,10);
        p1.add(title);

        //ID JPanel
        JPanel ID = new JPanel();
        ID.add(new JLabel("아이디",Label.LEFT));
        userID = new JTextField("", 20);
        ID.setBorder(BorderFactory.createEmptyBorder(5,10,0,0));
        ID.add(userID);
        ID.setSize(210,50);

        JPanel PW = new JPanel();
        PW.add(new JLabel("패스워드",Label.LEFT));
        userPW = new JTextField("",20);
        PW.add(userPW);
        PW.setSize(210,50);

        //Log_btn JPanel
        JPanel bt1 = new JPanel();
        JButton b1=  new JButton("로그인");
        bt1.add(b1);

        JPanel bt2 = new JPanel();
        JButton b2 = new JButton("회원가입");
        bt2.add(b2);

        b1.addActionListener(new Listener(this));
        bt1.setSize(210,10);

        b2.addActionListener(new change());
        bt2.setSize(210,10);

        c.add(p0);
        c.add(p1);
        c.add(ID);
        c.add(PW);
        c.add(bt1);
        c.add(bt2);

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
        public void actionPerformed(ActionEvent arg0)  {
            String userID_ = userID.getText();
            String userPW_ = userPW.getText();

            //UserJDBC
            UserJdbc userJdbc = new UserJdbc();

            try {
                UserInfo.setUser(userID_);
                System.out.println(UserInfo.getUser());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            int result = userJdbc.login(userID_, userPW_); // 성공 1 실패 0
            if(result == 1){
                JOptionPane.showMessageDialog(null,"로그인 성공!","Messeage",JOptionPane.PLAIN_MESSAGE);
                new BookSwing();
                setVisible(false);
            }
            else{
                JOptionPane.showMessageDialog(null,"로그인 실패. 다시 시도하세요.","Messeage",JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    //회원가입 버튼 클릭 이벤트(로그인 화면 안보이고 회원가입 창 보이기)
    class change implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            new SignUpSwing();
            setVisible(false);
        }
    }


    public static void main(String[] args){
        new LoginSwing();
    }

}