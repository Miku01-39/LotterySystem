package example;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
@SuppressWarnings("serial")
public class LoginFrame extends JFrame {
	JButton button = new JButton("�����");

	class LoginOKAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null, "��������һ�����壡");
			new MainFrame();
			setVisible(false);
		}
	}
	public LoginFrame(){
		super();
		this.setResizable(false);
		this.setSize(new Dimension(300, 205));
		this.setTitle("��һ������");
		this.setLayout(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocation(300, 200);
		this.setVisible(true);
		this.getContentPane().add(button, null);
		button.setBounds(new Rectangle(111, 70, 78, 27));
		button.addActionListener(new LoginOKAction());//����ť���¼�������
	}
	public static void main(String[] args) {
		new LoginFrame();
	}
}