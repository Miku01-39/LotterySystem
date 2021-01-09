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
			JOptionPane.showMessageDialog(null, "将通过txt文件导入参与人员数据");
			new Maintxt();
			setVisible(false);
		}
	}
	class Loginexcel implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "将通过excel文件导入参与人员数据");
			new MainExcel();
			setVisible(false);
		}
	}
public Interface() {
		this.Main();
	}
	void Main() {
		JFrame f = new JFrame("年会抽奖系统");
		f.setBackground(Color.WHITE);
		f.setLocation(300,200);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); 
		new JPanel();
		f.setLayout(new BorderLayout(30,30));
		JLabel title =new JLabel("欢迎进入年会抽奖系统!",JLabel.CENTER);
		f.add(title,BorderLayout.NORTH);
		title.setFont(new Font("楷体", Font.BOLD, 20));
		JButton txt =new JButton("从txt中导入");
		JButton excel =new JButton("从Excel中导入");
		f.add(txt,BorderLayout.WEST);
		f.add(excel,BorderLayout.EAST);
		txt.addActionListener(new Logintxt());//给按钮加事件监听器
		excel.addActionListener(new Loginexcel());//给按钮加事件监听器
		f.pack();
		f.setVisible(true);
	}
	public static void main(String args[]) {
		new Interface(); 
	}
}