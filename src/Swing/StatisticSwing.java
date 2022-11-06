package Swing;

import JDBC.MenuJDBC;
import JDBC.Statistic.StatisticJdbc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.util.Map;
import java.util.List;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

public class StatisticSwing extends JFrame {
    private int period_flag=0;
    private int type_flag=0;
    private Container contentPane;
    private JPanel row;
    private GridBagConstraints gbc;
    private JScrollPane scrollList;
    private JTextArea txtLog;
    public static String date;

    public StatisticSwing() {
        //setTitle("통계 화면");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        contentPane = getContentPane();
        row = new JPanel();
        row.setLayout(new GridBagLayout());
        gbc=new GridBagConstraints();
        gbc.fill=GridBagConstraints.BOTH;

        //날짜(클릭시 캘린더)
        JLabel title = new JLabel("날짜");
        title.setHorizontalAlignment(JLabel.CENTER);
        gbc.weighty=0.05;// 비율이 0.2:0.1이므로 버튼의 크기는 가로축으로 2배
        gbc.gridx=0;
        gbc.gridy=0;   //버튼이 두개로 0,0 기준으로 생성
        row.add(title,gbc);
        title.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                CalendarMain cal = new CalendarMain();

            }
        });

        //기간 설정 버튼
        JPanel p_periodBtn = new JPanel();
        p_periodBtn.setLayout(new GridLayout(1,4,1,1));
        JButton day_btn = new JButton("일일");
        JButton week_btn = new JButton("주간");
        JButton month_btn = new JButton("월간");
        JButton year_btn = new JButton("년간");
        p_periodBtn.add(day_btn);
        p_periodBtn.add(week_btn);
        p_periodBtn.add(month_btn);
        p_periodBtn.add(year_btn);
        gbc.weighty=0.05;// 비율이 0.2:0.1이므로 버튼의 크기는 가로축으로 2배
        gbc.gridx=0;
        gbc.gridy=1;
        row.add(p_periodBtn,gbc);
        day_btn.addActionListener(new ListenerPeriod(1));
        week_btn.addActionListener(new ListenerPeriod(2));
        month_btn.addActionListener(new ListenerPeriod(3));
        year_btn.addActionListener(new ListenerPeriod(4));

        //수입, 지출 선택 버튼
        JPanel p_moneyTpyeBtn = new JPanel();
        p_moneyTpyeBtn.setLayout(new GridLayout(1,2,1,1));
        JButton income_btn = new JButton("수입");
        JButton expense_btn = new JButton("지출");
        p_moneyTpyeBtn.add(income_btn);
        p_moneyTpyeBtn.add(expense_btn);
        gbc.weighty=0.1;// 비율이 0.2:0.1이므로 버튼의 크기는 가로축으로 2배
        gbc.gridx=0;
        gbc.gridy=2;
        row.add(p_moneyTpyeBtn,gbc);
        income_btn.addActionListener(new ListenerType(1));
        expense_btn.addActionListener(new ListenerType(2));

        JLabel listText = new JLabel("내역");
        gbc.weighty=0.05;// 비율이 0.2:0.1이므로 버튼의 크기는 가로축으로 2배
        gbc.gridx=0;
        gbc.gridy=3;
        row.add(listText,gbc);

        //txtLog = new JPanel();
        txtLog = new JTextArea();
        //txtLog.setLayout(new GridLayout(10,1));
        scrollList = new JScrollPane(txtLog);//txtLog
        scrollList.setBorder(new EmptyBorder(2,2,2,2));
        gbc.weighty=0.6;// 비율이 0.2:0.1이므로 버튼의 크기는 가로축으로 2배
        gbc.gridx=0;
        gbc.gridy=4;
        row.add(scrollList,gbc);

        JLabel space = new JLabel("  ");
        gbc.weighty=0.15;// 비율이 0.2:0.1이므로 버튼의 크기는 가로축으로 2배
        gbc.gridx=0;
        gbc.gridy=5;
        row.add(space,gbc);

        contentPane.add(row);
        setSize(350, 400);
        Dimension frameSize = getSize();

        Dimension windowSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((windowSize.width - frameSize.width) / 2,
                (windowSize.height - frameSize.height) / 2); //화면 중앙에 띄우기
        setVisible(true);

        //rePaint();

    }

    class ListenerPeriod implements ActionListener {

        int flagNum;
        public ListenerPeriod(int flagNum){
            this.flagNum=flagNum;
        }
        @Override
        public void actionPerformed(ActionEvent arg0){
            period_flag = flagNum;

        }
    }
    class ListenerType implements ActionListener {
        int flagNum;
        List list;
        //JTextField log;

        public ListenerType(int flagNum){
            this.flagNum=flagNum;

        }
        @Override
        public void actionPerformed(ActionEvent arg0) {
            type_flag = flagNum;
            //txtLog.removeAll();
            //txtLog.repaint();
            rePaint();

            if (date == null) {
                addLog("날짜를 설정하세요.");
            } else {

                switch (period_flag) {
                    case 1:
                        list = MenuJDBC.searchDaySum(1, Date.valueOf(date), Date.valueOf(date));
                        if (type_flag == 1) {
                            for (int i = 0; i < list.size(); i++) {
                                Map map = (Map) list.get(i);
                                if (map.get("cate_sum_income") != null) {
                                    //log = new JTextField(map.get("category") + " : 일간 총 " + map.get("cate_sum_income") + "(원) 수입\n\n",150);
                                    addLog(map.get("category") + " : 일간 총 " + map.get("cate_sum_income") + "(원) 수입");

                                }

                            }
                        } else {
                            for (int i = 0; i < list.size(); i++) {
                                Map map = (Map) list.get(i);
                                if (map.get("cate_sum_expense") != null) {
                                    //log = new JTextField(map.get("category") + " : 일간 총 " + map.get("cate_sum_expense") + "(원) 지출\n\n",150);
                                    addLog(map.get("category") + " : 일간 총 " + map.get("cate_sum_expense") + "(원) 지출");
                                    //addLog(map.get("category") + " : 일간 총 " + map.get("cate_sum_expense") + "(원) 지출");
                                }
                            }
                        }
                        break;
                    case 2:
                        //log = new JTextField("서비스 준비중 입니다~\n\n",100);
                        addLog("서비스 준비중 입니다~");
                        break;
                    case 3:
                        list = StatisticJdbc.monthCategory(1, date);
                        if (type_flag == 1) {
                            for (int i = 0; i < list.size(); i++) {
                                Map map = (Map) list.get(i);
                                if (map.get("sum_income") != null) {
                                    //log = new JTextField(map.get("category") + " : 월간 총 " + map.get("sum_income") + "(원) 수입\n\n",150);
                                    //addLog(log);
                                    addLog(map.get("category") + " : 월간 총 " + map.get("sum_income") + "(원) 수입");

                                }

                            }
                        } else {
                            for (int i = 0; i < list.size(); i++) {
                                Map map = (Map) list.get(i);
                                if (map.get("sum_expense") != null) {
                                    //log = new JTextField(map.get("category") + " : 월간 총 " + map.get("sum_expense") + "(원) 지출\n\n",150);
                                    addLog(map.get("category") + " : 월간 총 " + map.get("sum_expense") + "(원) 지출");
                                    //addLog(map.get("category") + " : 월간 총 " + map.get("sum_expense") + "(원) 지출");
                                }
                            }
                        }
                        break;
                    case 4:
                        list = StatisticJdbc.yearCategory(1, date);
                        if (type_flag == 1) {
                            for (int i = 0; i < list.size(); i++) {
                                Map map = (Map) list.get(i);
                                if (map.get("sum_income") != null) {
                                    //log = new JTextField(map.get("category") + " : 년간 총 " + map.get("sum_income") + "(원) 수입\n\n",150);
                                    addLog(map.get("category") + " : 년간 총 " + map.get("sum_income") + "(원) 수입");
                                    //addLog(map.get("category") + " : 년간 총 " + map.get("sum_income") + "(원) 수입");

                                }

                            }
                        } else {
                            for (int i = 0; i < list.size(); i++) {
                                Map map = (Map) list.get(i);
                                if (map.get("sum_expense") != null) {
                                    //log = new JTextField(map.get("category") + " : 년간 총 " + map.get("sum_expense") + "(원) 지출\n\n",150);
                                    addLog(map.get("category") + " : 년간 총 " + map.get("sum_expense") + "(원) 지출");
                                    //addLog(map.get("category") + " : 년간 총 " + map.get("sum_expense") + "(원) 지출");
                                }
                            }
                        }
                        break;

                }
            }
        }
    }


    private void rePaint(){
        //내역 출력 스크롤 화면

        txtLog = new JTextArea();
        scrollList = new JScrollPane(txtLog);
        scrollList.setBorder(new EmptyBorder(2,2,2,2));
        gbc.weighty=0.6;// 비율이 0.2:0.1이므로 버튼의 크기는 가로축으로 2배
        gbc.gridx=0;
        gbc.gridy=4;
        row.add(scrollList,gbc);


        JLabel space = new JLabel("  ");
        gbc.weighty=0.15;// 비율이 0.2:0.1이므로 버튼의 크기는 가로축으로 2배
        gbc.gridx=0;
        gbc.gridy=5;
        row.add(space,gbc);


        contentPane.add(row);
        setSize(350, 400);
        setVisible(true);
    }
    private void addLog(String log)
    {
        //rePaint();
        txtLog.append(log+"\n\n");

    }



    public static void main(String[] args) {
        new StatisticSwing();

    }

}