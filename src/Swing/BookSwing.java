package Swing;

import JDBC.MenuJDBC;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

class CalendarDataManager{
    static final int CAL_WIDTH = 7;
    final static int CAL_HEIGHT = 6;
    int calDates[][] = new int[CAL_HEIGHT][CAL_WIDTH];
    int calYear;
    int calMonth;
    int calDayOfMon;
    final int calLastDateOfMonth[]={31,28,31,30,31,30,31,31,30,31,30,31};
    int calLastDate;
    Calendar today = Calendar.getInstance();
    Calendar cal;

    public CalendarDataManager(){
        setToday();
    }
    public void setToday(){
        calYear = today.get(Calendar.YEAR);
        calMonth = today.get(Calendar.MONTH);
        calDayOfMon = today.get(Calendar.DAY_OF_MONTH);
        makeCalData(today);
    }
    private void makeCalData(Calendar cal){
        int calStartingPos = (cal.get(Calendar.DAY_OF_WEEK)+7-(cal.get(Calendar.DAY_OF_MONTH))%7)%7;
        if(calMonth == 1) calLastDate = calLastDateOfMonth[calMonth] + leapCheck(calYear);
        else calLastDate = calLastDateOfMonth[calMonth];
        for(int i = 0 ; i<CAL_HEIGHT ; i++){
            for(int j = 0 ; j<CAL_WIDTH ; j++){
                calDates[i][j] = 0;
            }
        }
        for(int i = 0, num = 1, k = 0 ; i<CAL_HEIGHT ; i++){
            if(i == 0) k = calStartingPos;
            else k = 0;
            for(int j = k ; j<CAL_WIDTH ; j++){
                if(num <= calLastDate) calDates[i][j]=num++;
            }
        }
    }
    private int leapCheck(int year){
        if(year%4 == 0 && year%100 != 0 || year%400 == 0) return 1;
        else return 0;
    }
    public void moveMonth(int mon){
        calMonth += mon;
        if(calMonth>11) while(calMonth>11){
            calYear++;
            calMonth -= 12;
        } else if (calMonth<0) while(calMonth<0){
            calYear--;
            calMonth += 12;
        }
        cal = new GregorianCalendar(calYear,calMonth,calDayOfMon);
        makeCalData(cal);
    }
}

public class BookSwing extends CalendarDataManager{
    JFrame mainFrame;
    JPanel calOpPanel;
    JButton todayBut;
    JLabel todayLab;
    JButton lYearBut;
    JButton lMonBut;
    JLabel curMMYYYYLab;
    JButton nMonBut;
    JButton nYearBut;
    ListenForCalOpButtons lForCalOpButtons = new ListenForCalOpButtons();

    JPanel calPanel;
    JButton weekDaysName[];
    JButton dateButs[][] = new JButton[6][7];
    listenForDateButs lForDateButs = new listenForDateButs();

    JPanel infoPanel;
    JLabel infoClock;

    JPanel memoPanel;
    JLabel selectedDate;
    JPanel memoArea;
    JScrollPane memoAreaSP;
    JPanel addB;

    JPanel frameBottomPanel;

    final String WEEK_DAY_NAME[] = { "SUN", "MON", "TUE", "WED", "THR", "FRI", "SAT" };
    final String title = "Saengji";

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new BookSwing();
            }
        });
    }
    public BookSwing(){

        mainFrame = new JFrame(title);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(450,500);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        try{
            UIManager.setLookAndFeel ("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");//LookAndFeel Windows ��Ÿ�� ����
            SwingUtilities.updateComponentTreeUI(mainFrame) ;
        }catch(Exception e){
        }

        calOpPanel = new JPanel();
        todayBut = new JButton("Today");
        todayBut.setToolTipText("Today");
        todayBut.addActionListener(lForCalOpButtons);
        lYearBut = new JButton("<<");
        lYearBut.setToolTipText("Previous Year");
        lYearBut.addActionListener(lForCalOpButtons);
        lMonBut = new JButton("<");
        lMonBut.setToolTipText("Previous Month");
        lMonBut.addActionListener(lForCalOpButtons);
        curMMYYYYLab = new JLabel("<html><table width=100><tr><th><font size=5>"+calYear+"/"+((calMonth+1)<10?"&nbsp;":"")+(calMonth+1)+"</th></tr></table></html>");
        nMonBut = new JButton(">");
        nMonBut.setToolTipText("Next Month");
        nMonBut.addActionListener(lForCalOpButtons);
        nYearBut = new JButton(">>");
        nYearBut.setToolTipText("Next Year");
        nYearBut.addActionListener(lForCalOpButtons);
        calOpPanel.setLayout(new GridBagLayout());
        GridBagConstraints calOpGC = new GridBagConstraints();
        calOpGC.gridx = 1;
        calOpGC.gridy = 1;
        calOpGC.gridwidth = 2;
        calOpGC.gridheight = 1;
        calOpGC.weightx = 1;
        calOpGC.weighty = 1;
        calOpGC.insets = new Insets(5,5,0,0);
        calOpGC.anchor = GridBagConstraints.WEST;
        calOpGC.fill = GridBagConstraints.NONE;
        calOpPanel.add(todayBut,calOpGC);
        calOpGC.anchor = GridBagConstraints.CENTER;
        calOpGC.gridwidth = 1;
        calOpGC.gridx = 1;
        calOpGC.gridy = 2;
        calOpPanel.add(lYearBut,calOpGC);
        calOpGC.gridwidth = 1;
        calOpGC.gridx = 2;
        calOpGC.gridy = 2;
        calOpPanel.add(lMonBut,calOpGC);
        calOpGC.gridwidth = 2;
        calOpGC.gridx = 3;
        calOpGC.gridy = 2;
        calOpPanel.add(curMMYYYYLab,calOpGC);
        calOpGC.gridwidth = 1;
        calOpGC.gridx = 5;
        calOpGC.gridy = 2;
        calOpPanel.add(nMonBut,calOpGC);
        calOpGC.gridwidth = 1;
        calOpGC.gridx = 6;
        calOpGC.gridy = 2;
        calOpPanel.add(nYearBut,calOpGC);

        calPanel=new JPanel();
        calPanel.setSize(400,300);
        weekDaysName = new JButton[7];
        for(int i=0 ; i<CAL_WIDTH ; i++){
            weekDaysName[i]=new JButton(WEEK_DAY_NAME[i]);
            weekDaysName[i].setBorderPainted(false);
            weekDaysName[i].setContentAreaFilled(false);
            weekDaysName[i].setForeground(Color.WHITE);
            if(i == 0) weekDaysName[i].setBackground(new Color(200, 50, 50));
            else if (i == 6) weekDaysName[i].setBackground(new Color(50, 100, 200));
            else weekDaysName[i].setBackground(new Color(150, 150, 150));
            weekDaysName[i].setOpaque(true);
            weekDaysName[i].setFocusPainted(false);
            calPanel.add(weekDaysName[i]);
        }
        for(int i=0 ; i<CAL_HEIGHT ; i++){
            for(int j=0 ; j<CAL_WIDTH ; j++){
                dateButs[i][j]=new JButton();
                dateButs[i][j].setBorderPainted(false);
                dateButs[i][j].setContentAreaFilled(false);
                dateButs[i][j].setBackground(Color.WHITE);
                dateButs[i][j].setOpaque(true);
                dateButs[i][j].addActionListener(lForDateButs);
                calPanel.add(dateButs[i][j]);
            }
        }
        calPanel.setLayout(new GridLayout(0,7,2,2));
        calPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        showCal();

        infoPanel = new JPanel();
        infoPanel.setLayout(new BorderLayout());
        infoClock = new JLabel("", SwingConstants.RIGHT);
        infoClock.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel.add(infoClock, BorderLayout.NORTH);
        selectedDate = new JLabel("<Html><font size=3>"+today.get(Calendar.YEAR)+"/"+(today.get(Calendar.MONTH)+1)+"/"+today.get(Calendar.DAY_OF_MONTH)+"</html>", SwingConstants.LEFT);
        selectedDate.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

        memoPanel=new JPanel();
        memoPanel.setBorder(BorderFactory.createTitledBorder("Account book"));
        memoArea = new JPanel();
        memoArea.setLayout(new GridLayout(10,1));
        addB = new JPanel();
        addB.setSize(20,20);
        JButton addBtn = new JButton("+");
        addB.add(addBtn);
        addBtn.addActionListener(new BookSwing.addBookList());
        memoPanel.add(addB);
        memoAreaSP = new JScrollPane(memoArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        readMemo();

        memoPanel.setLayout(new BorderLayout());
        memoPanel.add(selectedDate, BorderLayout.NORTH);
        memoPanel.add(addB,BorderLayout.AFTER_LINE_ENDS);
        memoPanel.add(memoAreaSP,BorderLayout.CENTER);


        JPanel frameSubPanelWest = new JPanel();
        Dimension calOpPanelSize = calOpPanel.getPreferredSize();
        calOpPanel.setPreferredSize(calOpPanelSize);
        frameSubPanelWest.setLayout(new BorderLayout());
        frameSubPanelWest.add(calOpPanel,BorderLayout.NORTH);
        frameSubPanelWest.add(calPanel,BorderLayout.CENTER);

        JPanel frameSubPanelEast = new JPanel();
        Dimension infoPanelSize=infoPanel.getPreferredSize();;
        infoPanel.setPreferredSize(infoPanelSize);
        frameSubPanelEast.setLayout(new BorderLayout());
        frameSubPanelEast.add(infoPanel,BorderLayout.NORTH);
        frameSubPanelEast.add(memoPanel,BorderLayout.CENTER);

        Dimension frameSubPanelWestSize = frameSubPanelWest.getPreferredSize();
        frameSubPanelWest.setPreferredSize(frameSubPanelWestSize);

        frameBottomPanel = new JPanel();
        JPanel bt1 = new JPanel();
        JButton b1=  new JButton("통계");
        bt1.add(b1);

        JPanel bt2 = new JPanel();
        JButton b2 = new JButton("계좌 설정");
        bt2.add(b2);

        JPanel bt3 = new JPanel();
        JButton b3 = new JButton("목표 설정");
        bt3.add(b3);

        b1.addActionListener(new BookSwing.changeStatistic());
        bt1.setSize(150,10);

        b2.addActionListener(new BookSwing.changeAccount());
        bt2.setSize(150,10);

        b3.addActionListener(new BookSwing.changeMyPage());
        bt3.setSize(150,10);

        frameBottomPanel.setLayout(new GridLayout(1,3));
        frameBottomPanel.add(bt1);
        frameBottomPanel.add(bt2);
        frameBottomPanel.add(bt3);

//        mainFrame.setLayout(new GridLayout(3,1));

        mainFrame.add(frameSubPanelWest,BorderLayout.NORTH);
        mainFrame.add(frameSubPanelEast,BorderLayout.CENTER);
        mainFrame.add(frameBottomPanel,BorderLayout.SOUTH);
        mainFrame.setVisible(true);

        focusToday();

    }
    private void focusToday(){
        if(today.get(Calendar.DAY_OF_WEEK) == 1)
            dateButs[today.get(Calendar.WEEK_OF_MONTH)][today.get(Calendar.DAY_OF_WEEK)-1].requestFocusInWindow();
        else
            dateButs[today.get(Calendar.WEEK_OF_MONTH)-1][today.get(Calendar.DAY_OF_WEEK)-1].requestFocusInWindow();
    }
    private void readMemo(){
//        try{
            memoArea.removeAll();
            memoArea.repaint();
            MenuJDBC menuJDBC = new MenuJDBC();
            java.sql.Date date=  java.sql.Date .valueOf(calYear+"-"+((calMonth+1)<10?"0":"")+(calMonth+1)+"-"+(calDayOfMon<10?"0":"")+calDayOfMon);
            System.out.println(date);
            String ti = "money" + "\t" +"Date" + "\t" + "content"
                    + "\t" +"category" + "\t" + "bank";
            JTextField title = new JTextField(ti,150);
            memoArea.add(title);
            List readList = new ArrayList();
            readList=menuJDBC.searchDayList(1,date,date);
            if (readList!=null){
                for(int i = 0; i < readList.size(); i ++){
                    Map map = (Map) readList.get(i);
                    if(map.get("expense") != null) {
                        String ex = "-"+map.get("expense") + "\t" + map.get("expenseDate") + "\t" + map.get("content")
                                + "\t" + map.get("category") + "\t" + map.get("bank");
                        JTextField expense = new JTextField(ex,150);
                        memoArea.add(expense);
                    }
                    else if(map.get("income") != null){
                        String in = "+"+map.get("income") + "\t" + map.get("incomeDate") + "\t" + map.get("content")
                                + "\t" + map.get("category") + "\t" + map.get("bank");
                        JTextField income = new JTextField(in,150);
                        memoArea.add(income);
                    }
                }
            }
    }
    private void showCal(){
        for(int i=0;i<CAL_HEIGHT;i++){
            for(int j=0;j<CAL_WIDTH;j++){
                String fontColor="black";
                if(j==0) fontColor="red";
                else if(j==6) fontColor="blue";

                File f =new File("MemoData/"+calYear+((calMonth+1)<10?"0":"")+(calMonth+1)+(calDates[i][j]<10?"0":"")+calDates[i][j]+".txt");
                if(f.exists()){
                    dateButs[i][j].setText("<html><b><font color="+fontColor+">"+calDates[i][j]+"</font></b></html>");
                }
                else dateButs[i][j].setText("<html><font color="+fontColor+">"+calDates[i][j]+"</font></html>");

                JLabel todayMark = new JLabel("<html><font color=green>*</html>");
                dateButs[i][j].removeAll();
                if(calMonth == today.get(Calendar.MONTH) &&
                        calYear == today.get(Calendar.YEAR) &&
                        calDates[i][j] == today.get(Calendar.DAY_OF_MONTH)){
                    dateButs[i][j].add(todayMark);
                    dateButs[i][j].setToolTipText("Today");
                }

                if(calDates[i][j] == 0) dateButs[i][j].setVisible(false);
                else dateButs[i][j].setVisible(true);
            }
        }
    }
    private class ListenForCalOpButtons implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == todayBut){
                setToday();
                lForDateButs.actionPerformed(e);
                focusToday();
            }
            else if(e.getSource() == lYearBut) moveMonth(-12);
            else if(e.getSource() == lMonBut) moveMonth(-1);
            else if(e.getSource() == nMonBut) moveMonth(1);
            else if(e.getSource() == nYearBut) moveMonth(12);

            curMMYYYYLab.setText("<html><table width=100><tr><th><font size=5>"+calYear+"/"+((calMonth+1)<10?"&nbsp;":"")+(calMonth+1)+"</th></tr></table></html>");
            showCal();
        }
    }
    private class listenForDateButs implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            int k=0,l=0;
            for(int i=0 ; i<CAL_HEIGHT ; i++){
                for(int j=0 ; j<CAL_WIDTH ; j++){
                    if(e.getSource() == dateButs[i][j]){
                        k=i;
                        l=j;
                    }
                }
            }

            if(!(k ==0 && l == 0)) calDayOfMon = calDates[k][l];

            cal = new GregorianCalendar(calYear,calMonth,calDayOfMon);

            String dDayString = new String();

            selectedDate.setText("<Html><font size=3>"+calYear+"/"+(calMonth+1)+"/"+calDayOfMon+"</html>");

            readMemo();
        }
    }

    class changeStatistic implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            new StatisticSwing();
        }
    }

    class addBookList implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            new InsertListSwing();
        }
    }

    class changeAccount implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            new AccountsSwing();
        }
    }

    class changeMyPage implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            new GoalsSwing();
        }
    }
}