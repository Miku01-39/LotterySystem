package example;
/*import javax.swing.JButton;*/
import javax.swing.JInternalFrame;

public class InternalFrame extends JInternalFrame{

    public InternalFrame() {
        super();
        setClosable(true);
        setIconifiable(true);
        setTitle("ÄÚ²¿´°Ìå");
        setBounds(50,50,400,300);
        setVisible(true);
    }
}