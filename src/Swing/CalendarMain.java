package Swing;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


class CalendarMain extends JFrame implements ActionListener{
    Container container = getContentPane();
    JPanel panel1 = new JPanel();
    JPanel panel2 = new JPanel();

    JButton buttonBefore = new JButton("Before");
    JButton buttonAfter = new JButton("After");

    JLabel label = new JLabel("00-00");

    JButton[] buttons = new JButton[49];
    String[] dayNames = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};

    CalendarFunction cF = new CalendarFunction();
    public String date;


    public CalendarMain() {
        setTitle("만년 달력");
        setSize(470, 320);
        init();
        start();
        Dimension frameSize = getSize();

        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((windowSize.height - frameSize.height) / 2 +700,
                (windowSize.height - frameSize.height) / 2); //화면 중앙에 띄우기
        setVisible(true);
    }
    private void init() {
        container.setLayout(new BorderLayout());
        container.add("North", panel1);
        container.add("Center", panel2);

        panel1.setLayout(new FlowLayout());
        panel1.add(buttonBefore);
        panel1.add(label);
        panel1.add(buttonAfter);

        Font font = new Font("SansSerif", Font.BOLD, 16);
        buttonAfter.setFont(font);
        buttonBefore.setFont(font);
        label.setFont(font);

        label.setText(cF.getCalText());

        panel2.setLayout(new GridLayout(7, 7, 5, 5));
        for(int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton();
            panel2.add(buttons[i]);

            buttons[i].setFont(new Font("SansSerif", Font.BOLD, 18));

            if(i < 7) buttons[i].setText(dayNames[i]);

            if(i%7 == 0) buttons[i].setForeground(Color.RED);
            if(i%7 == 6) buttons[i].setForeground(Color.BLUE);
            buttons[i].addActionListener(new dateListener());
        }
        cF.setButtons(buttons);
        cF.calSet();
    }
    class dateListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent arg0){
            String selected_date;
            for(int i=0;i<buttons.length; i++){
                if(arg0.getSource() == buttons[i]){
                    if(Integer.valueOf(buttons[i].getText()) < 10){
                        selected_date = label.getText()+"-0"+buttons[i].getText();
                    }else{
                        selected_date = label.getText()+"-"+buttons[i].getText();
                    }

                    StatisticSwing.date = selected_date;
                }
            }
        }
    }

    private void start() {
        buttonAfter.addActionListener(this);
        buttonBefore.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        int gap = 0;
        if(e.getSource() == buttonAfter) {				// 1달 후
            gap = 1;
        } else if(e.getSource() == buttonBefore ) {		// 1달 전
            gap = -1;
        }
        cF.allInit(gap);
        label.setText(cF.getCalText());		// 년월 글자 갱신
    }

}
