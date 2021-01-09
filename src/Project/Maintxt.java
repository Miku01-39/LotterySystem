package project;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
@SuppressWarnings("serial")
public class Maintxt extends JFrame {
     String LEVEL;
     int NUMBER = 0;
     int ROUNDNUM = 1;
     int LUNSHU = 0;
     int PAGENUM = 5;
     int[] indexs = new int[PAGENUM];
     boolean stop = true;
    File file2 = new File("LuckyDog.txt");
    //�������
     JFrame frame = new JFrame("���齱ϵͳ");
    Container contentPanel = frame.getContentPane();
     JPanel panelButton = new JPanel();
     JPanel panelText = new JPanel();
     JPanel panelTop = new JPanel();
     final JTextArea luckyPerson = new JTextArea(PAGENUM, 1);
     JTextField level = new JTextField("�����뼸�Ƚ�");
     JTextField number = new JTextField("����������");
     JButton start = new JButton("��ʼ�齱");
     JButton end = new JButton("ֹͣ�齱");
     JLabel title = new JLabel("��ӭʹ�����ϵͳ");
     JLabel prizelevel = new JLabel("ף���н�");
     ArrayList<String> nameList = new ArrayList<String>();
     ArrayList<String> luckyList = new ArrayList<String>();
    Thread thread = new RandomThread();
    //�߳�ѭ����ʾ
    class RandomThread extends Thread {
        public void run() {
            stop = true;//�˴�����һ��boolean�͵�ȫ�ֱ���,�ڵ��ֹͣ�齱ʱ,��stop��ֵfalse,�Ӷ�ֹͣѭ����ʾ.
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
    //level����
    class LevelEvent extends KeyAdapter {
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == '\n') {
                String numberStr = level.getText();
                try {
                    int levelNum = Integer.parseInt(numberStr);
                    switch (levelNum) {
                        case 1:
                            LEVEL = "һ�Ƚ�";
                            break;
                        case 2:
                            LEVEL = "���Ƚ�";
                            break;
                        case 3:
                            LEVEL = "���Ƚ�";
                            break;
                        default:
                            LEVEL = "���뽱";
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
    //number����
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
                    number.setText("��������������");
                }

            }
        }
    }
    //stop������
    class EndEvent extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            stop = false;
            prizelevel.setText("��ϲ���³�Ա ���" + LEVEL);
            RandomThread.yield();
            thread.interrupt() ;
            String strs = luckyPerson.getText();
            String[] person = strs.split("\n{1,}");
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter(file2, true));
                for (String str : person) {
                    out.write(LEVEL + "  " + "��" + LUNSHU + "��:   " + str + "\n");
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
    //��ʼ��ť������ѭ����ʾ����
    class StartEvent extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            LUNSHU = LUNSHU + 1;
            if (LUNSHU > ROUNDNUM) {
                prizelevel.setText("������������");
                return;
            } else if (LUNSHU == ROUNDNUM) {
                PAGENUM = NUMBER % PAGENUM == 0 ? PAGENUM : NUMBER % PAGENUM;
            }
            prizelevel.setText(LEVEL+ "��:��" + NUMBER + "���� ��" + ROUNDNUM + "��,��" + LUNSHU + "��");
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
    //�������
    //������
    public Maintxt() {
        this.initFrame();
        this.importName();
        level.addKeyListener(new LevelEvent());
        number.addKeyListener(new NumberEvent());
        start.addMouseListener(new StartEvent());
        end.addMouseListener(new EndEvent());
    }
    //���������ʽ
    void initFrame() {
        //1  �����
        panelTop.add(title);
        panelTop.setBackground(Color.yellow);
        title.setForeground(Color.red);
        title.setFont(new Font("����", Font.BOLD, 20));
        contentPanel.add(panelTop, BorderLayout.NORTH);
		frame.setLocation(300, 200);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //2 ������ʾ���
        panelText.add(prizelevel, BorderLayout.NORTH);
        prizelevel.setForeground(Color.magenta);
        prizelevel.setFont(new Font("����", Font.BOLD, 18));
        panelText.add(luckyPerson, BorderLayout.CENTER);
        panelText.setBackground(Color.pink);
        contentPanel.add(panelText, BorderLayout.CENTER);
        //�ı���
        level.setBackground(Color.CYAN);
        level.selectAll();
        number.setBackground(Color.pink);
        number.setForeground(Color.red);
        number.selectAll();
        luckyPerson.setBackground(Color.pink);
        luckyPerson.setForeground(Color.red);
        luckyPerson.setFont(new Font("����", Font.BOLD, 15));
        //3 ��ť���
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
        try {
            String line;
            File file = new File("name.txt");
            BufferedReader in = new BufferedReader(new FileReader(file));
            while ((line = in.readLine()) != null) {
                nameList.add(line);
            }
            in.close();
        } catch (Exception ee) {
            System.out.println("�Ҳ����ļ���");
        }
    }
    public static void main(String[] args) {
        new Maintxt();
    }
}