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
        jp2.add(btn_add);

        btn_add.addActionListener(new MyActionListener());

        //Panel 3
        JPanel jp3 = new JPanel();
        JButton btn_close = new JButton("확인");
        jp3.add(btn_close);
        btn_close.addActionListener(new MyActionListener());

        c.add(jp1);
        c.add(jp2);
        c.add(jp3);
        setSize(350, 150);

        Dimension frameSize = getSize();

        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((windowSize.width - frameSize.width) / 2,
                (windowSize.height - frameSize.height) / 2); //화면 중앙에 띄우기

        setVisible(true);

    }
    private class MyActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JButton b = (JButton) e.getSource();
            if (b.getText().equals("추가 등록")){
                new InsertListSwing();
                setVisible(false);
            }
            if (b.getText().equals("확인")){
                setVisible(false);
            }
        }
    }

}