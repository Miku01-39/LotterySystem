package example;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;
import java.awt.event.*;
import java.awt.event.MouseAdapter;
class Test extends JFrame {
    private String LEVELNUM;
    private int NUMBER = 0;
    private int ROUNDNUM = 1;
    private int LUNSHU = 0;
    private int PAGENUM = 5;
    private int[] indexs = new int[PAGENUM];
    private boolean stop = true;
    File file2 = new File("LuckyDog.txt");
    //定义面板
    private JFrame frame = new JFrame("年会抽奖系统");
    Container contentPanel = frame.getContentPane();
    private JPanel panelButton = new JPanel();
    private JPanel panelText = new JPanel();
    private JPanel panelTop = new JPanel();
    private final JTextArea luckyPerson = new JTextArea(PAGENUM, 1);
    private JTextField level = new JTextField("请输入几等奖：");
    private JTextField number = new JTextField("请输入名额：");
    private JButton start = new JButton("开始抽奖");
    private JButton end = new JButton("停止抽奖");
    private JLabel title = new JLabel("欢迎使用年会系统");
    private JLabel prizelevel = new JLabel("祝你中奖");
    private ArrayList<String> nameList = new ArrayList<String>();
    private ArrayList<String> luckyList = new ArrayList<String>();
    Thread thread = new RandomThread();
    //线程循环显示
    class RandomThread extends Thread {
        public void run() {
            stop = true;//此处用了一个boolean型的全局变量,在点击停止抽奖时,给stop赋值false,从而停止循环显示.
            while (stop) {
                luckyPerson.setText("");
                random();
                for (int i = 0; i < PAGENUM; i++) {
                    String line = (String) nameList.get(indexs[i]);
                    luckyPerson.append(i + line + "\n");
                }
                try {
                    sleep(100);
                    luckyPerson.paintImmediately(luckyPerson.getBounds());
                } catch (Exception eee) {
                }
            }
        }
    }
    //level监空
    class LevelEvent extends KeyAdapter {
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == '\n') {
                String numberStr = level.getText();
                try {
                    int levelNum = Integer.parseInt(numberStr);
                    switch (levelNum) {
                    case 1:
                        LEVELNUM = "一等";
                        break;
                    case 2:
                        LEVELNUM = "二等";
                        break;
                    case 3:
                        LEVELNUM = "三等";
                        break;
                    default:
                        LEVELNUM = "参与";
                        break;
                    }
                    level.setText(levelNum + "----------");
                    level.selectAll();
                } catch (Exception ee) {
                    LEVELNUM=numberStr;
                }
            }
        }
    }
    //number监听
    class NumberEvent extends KeyAdapter {
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == '\n') {
                String numberStr = number.getText();
                PAGENUM = 5;
                try {
                    NUMBER = Integer.parseInt(numberStr);
                    if ((NUMBER % PAGENUM) == 0) {
                        ROUNDNUM = NUMBER / PAGENUM;
                    } else {
                        ROUNDNUM = NUMBER / PAGENUM + 1;
                    }
                LUNSHU = 0;
                indexs = new int[PAGENUM];
                number.setText(NUMBER + "----------");
                number.selectAll();
                } catch (Exception ee) {
                    number.setText("请输入罗马数字");
                }

            }
        }
    }
    //stop并保存
    class EndEvent extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            stop = false;
            prizelevel.setText("恭喜以下成员 获得" + LEVELNUM + "奖");
            RandomThread.yield();
            thread.interrupt() ;
            String strs = luckyPerson.getText();
            String[] person = strs.split("\n{1,}");
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(file2, true));
                for (String str : person) {
                    out.write(LEVELNUM + "  " + "第" + LUNSHU + "轮:   " + str + "\n");
                    out.newLine();
                }
                luckyList.clear();
                for (int i : indexs) {
                    luckyList.add(nameList.get(i));
                }
                nameList.removeAll(luckyList);
                out.close();
            } catch (Exception eee) {
                System.out.println("out **");
            }

        }
    }
    //开始按钮，用来循环显示名单
    class StartEvent extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            LUNSHU = LUNSHU + 1;
            if (LUNSHU > ROUNDNUM) {
                prizelevel.setText("超过名额已满");
                return;
            } else if (LUNSHU == ROUNDNUM) {
                PAGENUM = NUMBER % PAGENUM == 0 ? PAGENUM : NUMBER % PAGENUM;
            }
            prizelevel.setText(LEVELNUM + "奖:共" + NUMBER + "名。 共" + ROUNDNUM + "轮,第" + LUNSHU + "轮");
            new RandomThread().start();
        }
    }
    void random() {
        for (int i = 0; i < PAGENUM;) {
            boolean allTrue = true;
            int index = (int) (Math.random() * (nameList.size()));
            for (int j = 0; j < i; j++) {
                if (index == indexs[j]) {
                    allTrue = !allTrue;
                }
            }
            if (allTrue) {
                indexs[i] = index;
                i++;
            }
        }
    }
    //子类结束
    //构造器
    public Test() {
        this.initFrame();
        this.importName();
        level.addKeyListener(new LevelEvent());
        number.addKeyListener(new NumberEvent());
        start.addMouseListener(new StartEvent());
        end.addMouseListener(new EndEvent());
        }
    //设置面板样式
    void initFrame() {
        //1  主面板
        panelTop.add(title);
        panelTop.setBackground(Color.yellow);
        title.setForeground(Color.red);
        title.setFont(new Font("楷体", Font.BOLD, 20));
        contentPanel.add(panelTop, BorderLayout.NORTH);
        //2 滚动显示面板
        panelText.add(prizelevel, BorderLayout.NORTH);
        prizelevel.setForeground(Color.magenta);
        prizelevel.setFont(new Font("黑体", Font.BOLD, 18));
        panelText.add(luckyPerson, BorderLayout.CENTER);
        panelText.setBackground(Color.pink);
        contentPanel.add(panelText, BorderLayout.CENTER);
        //文本框
        level.setBackground(Color.CYAN);
        level.selectAll();
        number.setBackground(Color.pink);
        number.setForeground(Color.red);
        number.selectAll();
        luckyPerson.setBackground(Color.pink);
        luckyPerson.setForeground(Color.red);
        luckyPerson.setFont(new Font("黑体", Font.BOLD, 15));
        //3 按钮面板
        panelButton.add(start);
        panelButton.add(end);
        panelButton.add(level, BorderLayout.EAST);
        panelButton.add(number, BorderLayout.EAST);
        panelButton.setBackground(Color.blue);
        contentPanel.add(panelButton, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }
    //设置文件导入
    void importName() {
        try {
            String line;
            File file = new File("name.txt");
            BufferedReader in = new BufferedReader(new FileReader(file));
            while ((line = in.readLine()) != null) {
                nameList.add(line);
            }
            in.close();
        } catch (Exception ee) {
            System.out.println("找不到文件");
        }
    }
    public static void main(String[] args) {
        new Test();
    }
}