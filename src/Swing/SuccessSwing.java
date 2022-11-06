package Swing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SuccessSwing extends JFrame {
    public SuccessSwing (){
        setTitle("등록 성공");

        Container c = getContentPane();
        setLayout(new FlowLayout(FlowLayout.CENTER));

        //Panel 1
        JPanel jp1 = new JPanel();
        JLabel title = new JLabel("등록 성공!");
        title.setFont(new Font("맑은고딕",Font.BOLD,20));
        title.setSize(210,10);
        jp1.add(title);

        //Panel 2
        JPanel jp2 = new JPanel();
        JButton btn_add = new JButton("추가 등록");
        JButton btn_main = new JButton("메인 메뉴");
        jp2.add(btn_add);
        jp2.add(btn_main);

        btn_add.addActionListener(new MyActionListener());
        btn_main.addActionListener(new MyActionListener());

        c.add(jp1);
        c.add(jp2);
        setSize(300, 350);

        Dimension frameSize = getSize();

        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((windowSize.width - frameSize.width) / 2,
                (windowSize.height - frameSize.height) / 2); //화면 중앙에 띄우기
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisible(true);

    }
    private class MyActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton b = (JButton) e.getSource();
            if (b.getText().equals("추가 등록")){
                new InsertListSwing();
                setVisible(false);
            }
            else{
                // 메인 화면으로 돌아가기
            }
        }
    }

}