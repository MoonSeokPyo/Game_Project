package FinalProject;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GamePanel extends JFrame {
	ImagePanel _imagePanel;
	Model _model;
	public JTextField text = new JTextField("  ");
	
	public GamePanel(ImagePanel imagePanel, Model model){
		_imagePanel=imagePanel;
		_model = model;
		
		JFrame frame = new JFrame();
		frame.setTitle("조각 그림 맞추기");
		frame.setLocation(500, 25);
		frame.setPreferredSize(new Dimension(1000, 1000));
		
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		JLabel label1 = new JLabel("난이도를 선택하세요.");
		JLabel label2 = new JLabel("상태 메세지 :");
		JButton button1 = new JButton("3 x 3");
		JButton button2 = new JButton("4 x 4");
		JButton button3 = new JButton("5 x 5");
		
		text.setPreferredSize(new Dimension(380, 50));
		text.setEditable(false);
		
		label1.setPreferredSize(new Dimension(120, 50));
		label2.setPreferredSize(new Dimension(80, 50));
		button1.setPreferredSize(new Dimension(100, 50));
		button2.setPreferredSize(new Dimension(100, 50));
		button3.setPreferredSize(new Dimension(100, 50));
		controlPanel.add(label1);
		controlPanel.add(button1);
		controlPanel.add(button2);
		controlPanel.add(button3);
		controlPanel.add(label2);
		controlPanel.add(text);
		
		Button_1 btn1Action = new Button_1(_imagePanel);
		button1.addActionListener(btn1Action);
		Button_2 btn2Action = new Button_2(_imagePanel);
		button2.addActionListener(btn2Action);
		Button_3 btn3Action = new Button_3(_imagePanel);
		button3.addActionListener(btn3Action);
		
		frame.add(controlPanel, BorderLayout.SOUTH);
		
		Container contentPane = frame.getContentPane();
		
		_imagePanel.addMouseListener(new MouseHandler(_imagePanel, _model, this));
		contentPane.add(_imagePanel, BorderLayout .CENTER);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	}
	
	class Button_1 implements ActionListener {
		ImagePanel _imagePanel;
		public Button_1(ImagePanel imagePanel) {
			_imagePanel = imagePanel;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			text.setText(" ");
			_model._num=3;
			_model._gamecheck=1;
			_model.shuffle(3);
			_imagePanel.repaint();
			_model._clickcount=0;
			_model._count=0;
			_model.starttime = System.currentTimeMillis();
			
			//System.out.println("1번버튼 실행");
			
		}
		
	}
	class Button_2 implements ActionListener {
		ImagePanel _imagePanel;
		public Button_2(ImagePanel imagePanel) {
			_imagePanel = imagePanel;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			text.setText(" ");
			_model._num=4;
			_model._gamecheck=2;
			_model.shuffle(4);
			_imagePanel.repaint();
			_model._clickcount=0;
			_model._count=0;
			_model.starttime = System.currentTimeMillis();
			
			//System.out.println("2번버튼 실행");
			
		}
		
	}
	class Button_3 implements ActionListener {

		ImagePanel _imagePanel;
		public Button_3(ImagePanel imagePanel) {
			_imagePanel = imagePanel;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			text.setText(" ");
			_model._num=5;
			_model._gamecheck=3;
			_model.shuffle(5);
			_imagePanel.repaint();
			_model._clickcount=0;
			_model._count=0;
			_model.starttime = System.currentTimeMillis();
			
			//System.out.println("3번버튼 실행");	
		}
	}
}
