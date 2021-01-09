package project;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
@SuppressWarnings("serial")
public class Interface extends JFrame {
	class Logintxt implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "��ͨ��txt�ļ����������Ա����");
			new Maintxt();
			setVisible(false);
		}
	}
	class Loginexcel implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "��ͨ��excel�ļ����������Ա����");
			new MainExcel();
			setVisible(false);
		}
	}
public Interface() {
		this.Main();
	}
	void Main() {
		JFrame f = new JFrame("���齱ϵͳ");
		f.setBackground(Color.WHITE);
		f.setLocation(300,200);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); 
		new JPanel();
		f.setLayout(new BorderLayout(30,30));
		JLabel title =new JLabel("��ӭ�������齱ϵͳ!",JLabel.CENTER);
		f.add(title,BorderLayout.NORTH);
		title.setFont(new Font("����", Font.BOLD, 20));
		JButton txt =new JButton("��txt�е���");
		JButton excel =new JButton("��Excel�е���");
		f.add(txt,BorderLayout.WEST);
		f.add(excel,BorderLayout.EAST);
		txt.addActionListener(new Logintxt());//����ť���¼�������
		excel.addActionListener(new Loginexcel());//����ť���¼�������
		f.pack();
		f.setVisible(true);
	}
	public static void main(String args[]) {
		new Interface(); 
	}
}