package Test;
/*import java.awt.BorderLayout;*/
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*import javax.swing.JButton;*/
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
/*import javax.swing.JToolBar;*/

public class MainExcel extends JFrame{

    private static final JDesktopPane DESKTOP_PANE = new JDesktopPane();

    public MainExcel() {
        super("����������");
        setSize(640, 480);

        //�˵�����
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JMenu menu1 = new JMenu("�˵�1");
        JMenu menu101 = new JMenu("�˵�101");
        JMenuItem menu10101 = new JMenuItem("�˵�10101");
        JMenuItem menu102 = new JMenuItem("�˵�102");
        menu102.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addIFame(new InternalExcel());
            }
        });
        menu101.add(menu10101);
        menu1.add(menu101);
        menu1.add(menu102);
        JMenu menu2 = new JMenu("�˵�2");
        menuBar.add(menu1);
        menuBar.add(menu2);

        this.getContentPane().add(DESKTOP_PANE);
        this.setVisible(true);
    }

    public static void addIFame(JInternalFrame iframe) { // ����Ӵ���ķ���
		DESKTOP_PANE.add(iframe);
	}

    public static void main(String[] args) {
        new MainExcel();
    }
}
