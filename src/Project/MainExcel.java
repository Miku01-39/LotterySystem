package project;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import jxl.Sheet;
import jxl.Workbook;
@SuppressWarnings("serial")
public class MainExcel extends JFrame {
	 String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
     String dbURL="jdbc:sqlserver://0.0.0.0:1443;DatabaseName=test";//这里的test是数据库名称
     String userName="sa";//这里的sa是数据库用户名，默认是sa
     String userPwd="miku0139";//这里是密码
     String LEVEL;
     int NUMBER = 0;
     int ROUNDNUM = 1;
     int LUNSHU = 0;
     int PAGENUM = 5;
     int[] indexs = new int[PAGENUM];
     boolean stop = true;
    File file2 = new File("LuckyDog.txt");
    //定义面板
     JFrame frame = new JFrame("年会抽奖系统");
    Container contentPanel = frame.getContentPane();
     JPanel panelButton = new JPanel();
     JPanel panelText = new JPanel();
     JPanel panelTop = new JPanel();
     final JTextArea luckyPerson = new JTextArea(PAGENUM, 1);
     JTextField level = new JTextField("请输入几等奖");
     JTextField number = new JTextField("请输入名额");
     JButton start = new JButton("开始抽奖");
     JButton end = new JButton("停止抽奖");
     JLabel title = new JLabel("欢迎使用年会系统");
     JLabel prizelevel = new JLabel("祝你中奖");
     ArrayList<String> nameList = new ArrayList<String>();
     ArrayList<String> luckyList = new ArrayList<String>();
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
    //level监听
    class LevelEvent extends KeyAdapter {
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == '\n') {
                String numberStr = level.getText();
                try {
                    int levelNum = Integer.parseInt(numberStr);
                    switch (levelNum) {
                        case 1:
                            LEVEL = "一等奖";
                            break;
                        case 2:
                            LEVEL = "二等奖";
                            break;
                        case 3:
                            LEVEL = "三等奖";
                            break;
                        default:
                            LEVEL = "参与奖";
                            break;
                    }
                    level.setText(levelNum + "----------");
                    level.selectAll();
                } catch (Exception ee) {
                    LEVEL=numberStr;
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
            prizelevel.setText("恭喜以下成员 获得" + LEVEL);
            RandomThread.yield();
            thread.interrupt() ;
            String strs = luckyPerson.getText();
            String[] person = strs.split("\n{1,}");
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(file2, true));
                for (String str : person) {
                    out.write(LEVEL + "  " + "第" + LUNSHU + "轮:   " + str + "\n");
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
            prizelevel.setText(LEVEL+ "奖:共" + NUMBER + "名。 共" + ROUNDNUM + "轮,第" + LUNSHU + "轮");
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
    //从Excel文件中导入
    class ExcelImport {
    	void Import(String[] args) {
    		ExcelImport excelImport = new ExcelImport();
    		excelImport.getAllByExcel("E:\\编程文件\\Java\\课程设计\\Excel\\participant.xls");
    	}
    	void getAllByExcel(String file) {
    		try {
    			Workbook rwb = Workbook.getWorkbook(new File(file));
    			Sheet rs = rwb.getSheet(0);// 或者rwb.getSheet(0)
    			int clos = rs.getColumns();// 得到所有的列
    			int rows = rs.getRows();// 得到所有的行
    			for (int i = 1; i < rows; i++) {
    				for (int j = 0; j < clos; j++) {
    					// 第一个是列数，第二个是行数
    					String name = rs.getCell(j, i).getContents();
    					nameList.add(name);
    				}
    			}
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    }
    //子类结束
    //构造器
    public MainExcel() {
        this.initFrame();
        this.importName();
        new ExcelImport();
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
		frame.setLocation(300, 200);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
    void importName() {
    	ExcelImport excelImport = new ExcelImport();
		excelImport.getAllByExcel("E:\\编程文件\\Java\\课程设计\\LotterySystem\\participant.xls");
    }
    public static void main(String[] args) {
        new MainExcel();
    }
}