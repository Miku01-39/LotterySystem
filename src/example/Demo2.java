package example;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
/**
 *
 * @author ICHARM
 */
//按钮状态改变线程类
class ButtonChangeState extends Thread{
	Demo2 one =null;
	public ButtonChangeState(Demo2 tmp){
		one = tmp;
	}
        @Override
	public void run(){
		one.ButtonChangeState(); 
	}
}
//开始按钮事件进程类
class StartButtonEvent extends Thread{
	Demo2 one =null;
	public StartButtonEvent(Demo2 tmp){
		one = tmp;
	}
        @Override
	public void run(){
		one.StartButtonEvent(); 
	}
}
//开始按钮事件监听器
class StartButtonEventListener implements ActionListener{
	private Demo2 form = null;
	public StartButtonEventListener(Demo2 One){
		this.form = One;
	}
        @Override
	public void actionPerformed(ActionEvent e){
		ButtonChangeState obj = new ButtonChangeState(form);
		obj.start();
		StartButtonEvent obj1 = new StartButtonEvent(form);        
		obj1.start();
	}
}
//停止按钮事件监听器
class StopButtonEventListener implements ActionListener{
	private Demo2 form = null;
	public StopButtonEventListener(Demo2 One){
		this.form = One;
	}
        @Override
	public void actionPerformed(ActionEvent e){
		form.StopButtonEvent();
	}
}

//保存按钮事件监听器
class SaveButtonEventListener implements ActionListener{
	private Demo2 form = null;
	public SaveButtonEventListener(Demo2 One){
		this.form = One;
	}
        @Override
	public void actionPerformed(ActionEvent e){
		form.SaveButtonEvent();
	}
}
@SuppressWarnings("serial")
public class Demo2 extends JFrame {

	private JFrame frame;
	private JLabel name;
	public JTextArea area2;
	private JButton startbutton;
	private JButton stopbutton;
	private JButton endbutton;
	private JScrollPane AreaScrollPane;
	private int count = 0;
	private int i=0;
    private int j=0;
	public boolean stopflag = false;  
    String[] Name =null;
	public Demo2() {  
		frame=new JFrame("抽奖");
		frame.setLayout(null);
		name=new JLabel("中奖者");
		name.setBounds(50, 50, 200, 20);
		frame.getContentPane().add(name);
		area2=new JTextArea();
		area2.setBounds(200, 20, 300, 180);
		area2.setEditable(false);
		AreaScrollPane = new JScrollPane(area2);
		AreaScrollPane.setBounds(200, 20, 300, 180);
		AreaScrollPane.setVisible(true);
		AreaScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  //设置垂直滚动条总是显示�
		startbutton=new JButton("开始");
		startbutton.setBounds(50, 220, 90, 30);
		startbutton.addActionListener(new StartButtonEventListener(this));         //StatrtButton事件监听器
		frame.getContentPane().add(startbutton);
		stopbutton=new JButton("暂停");
		stopbutton.setBounds(220, 220, 90, 30);
		stopbutton.addActionListener(new StopButtonEventListener(this));         //StopButton事件监听器
		stopbutton.setEnabled(false);
		frame.getContentPane().add(stopbutton);
		endbutton=new JButton("保存");
		endbutton.setBounds(400, 220, 90, 30);
		endbutton.setEnabled(false);
		endbutton.addActionListener(new SaveButtonEventListener(this));          //SaveButton事件监听器
		frame.getContentPane().add(endbutton);
		frame.setSize(555,300);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.add(AreaScrollPane, BorderLayout.EAST);
	}
    //按钮状态改变事件处理函数
	public void ButtonChangeState(){
		startbutton.setEnabled(false);       
		stopbutton.setEnabled(true);
		endbutton.setEnabled(true);
	}
    //开始按钮事件处理函数 
	public void StartButtonEvent(){
		try{
			FileReader fin = new FileReader("E:\\编程文件\\Java\\课程设计\\LotterySystem\\test1.txt");
			@SuppressWarnings("resource")
			BufferedReader in = new BufferedReader(fin);
			String line = in.readLine();
			Name = new String[100];
			while(line != null)
			{
				line = line.trim();
				if(line.length()>0 && count<=100 )
				{
					Name[count] = line;
					count++;	
				}
				line = in.readLine();
			}
			area2.setText(Name[0]);
			for(i = 1; i <= count+1; i++){
				if(i == count){
					i=0;
				}
				if(!stopflag){
					area2.append("\n" + Name[i]);
					name.setText("中奖者：\n"+Name[i]);
                    j=i;
                    area2.setSelectionStart(area2.getText().length());  //滚动条自动滚动到底端
                    area2.paintImmediately(area2.getBounds());          //刷新
				}
			}
			in.close();
			fin.close();
		}
		catch(IOException g)
		{
			System.out.println("IOException");
		}

	}
        //停止按钮事件处理函数
	public void StopButtonEvent(){
		stopflag = true;

	} 
        //保存按钮事件处理函数
	public void SaveButtonEvent(){
            if(stopflag){
                try{ 
                    
                    FileWriter fw = new FileWriter("E:\\编程文件\\Java\\课程设计\\LotterySystem\\Test2.txt",true);
                    BufferedWriter fww = new BufferedWriter(fw);
                    fww.write(Name[j]);
                    fww.close();   
                }
               catch(IOException e){
                   System.out.println("IOException");
               }
            }
	} 
	public static void main(String[] args) {
		Demo2 application = new Demo2();
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}