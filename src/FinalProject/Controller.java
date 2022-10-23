package FinalProject;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Controller {
	Model _model = new Model();
	MouseHandler _handler;
	ImagePanel _imagepanel;
	GamePanel _gamepanel;
	
	public Controller(){
		_imagepanel = new ImagePanel(_model);
		_model.music();
		_gamepanel = new GamePanel(_imagepanel, _model);
		_handler = new MouseHandler(_imagepanel, _model, _gamepanel);
	}
	
	void run() {
//		_model.music();
//		_gamepanel = new GamePanel(_imagepanel, _model);
//		_handler = new MouseHandler(_imagepanel, _model, _gamepanel);
	}
}