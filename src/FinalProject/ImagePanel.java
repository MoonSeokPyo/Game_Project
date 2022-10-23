package FinalProject;

import java.awt.*;
import java.awt.image.*;
import java.util.*;
import javax.swing.*;
import javax.imageio.ImageIO;

public class ImagePanel extends JPanel {
	Model _model;
	
	Toolkit _toolkit = getToolkit();
	Random _randnum = new Random();
	
	Image _image;
	Image _mainimage;
	int _width, _height;
	
	BufferedImage nxn_1[];
	BufferedImage nxn_2[];
	BufferedImage nxn_3[];
	
	public ImagePanel(Model model) {
		_model = model;
		
		MediaTracker tracker = new MediaTracker(this);
		
		
		_mainimage = _toolkit.getImage("Image\\main.jpg");
		_image = _toolkit.getImage("Image\\image" + (_randnum.nextInt(6) + 1) + ".jpg");
		
		tracker.addImage(_image, 0);
		try {
			tracker.waitForAll();
		} catch (InterruptedException e) {;}
		
		_width = _image.getWidth(this);
		_height = _image.getHeight(this);
		
		
			nxn_1 = new BufferedImage[3*3];
			int cnt=0;
			for ( int i = 0; i < 3; i++) {
				for ( int j = 0; j < 3; j++) {
					nxn_1[cnt] = new BufferedImage(_width/3, _height/3, BufferedImage.TYPE_INT_RGB);
					Graphics g = nxn_1[cnt].getGraphics();
					g.drawImage(_image, 0, 0, (_width/3), (_height/3), j*(_width/3), i*(_height/3), (j+1)*(_width/3), (i+1)*(_height/3), this);
					cnt++;
				}
			}
			nxn_2 = new BufferedImage[4*4];
			cnt=0;
			for ( int i = 0; i < 4; i++) {
				for ( int j = 0; j < 4; j++) {
					nxn_2[cnt] = new BufferedImage(_width/4, _height/4, BufferedImage.TYPE_INT_RGB);
					Graphics g = nxn_2[cnt].getGraphics();
					g.drawImage(_image, 0, 0, (_width/4), (_height/4), j*(_width/4), i*(_height/4), (j+1)*(_width/4), (i+1)*(_height/4), this);
					cnt++;
				}
			}
			nxn_3 = new BufferedImage[5*5];
			cnt=0;
			for ( int i = 0; i < 5; i++) {
				for ( int j = 0; j < 5; j++) {
					nxn_3[cnt] = new BufferedImage(_width/5, _height/5, BufferedImage.TYPE_INT_RGB);
					Graphics g = nxn_3[cnt].getGraphics();
					g.drawImage(_image, 0, 0, (_width/5), (_height/5), j*(_width/5), i*(_height/5), (j+1)*(_width/5), (i+1)*(_height/5), this);
					cnt++;
				}
			}
		
		
	}
	
	public void paint(Graphics g) {
		//System.out.println("paint check:"+_gamecheck);
		
		g.clearRect(0, 0, getWidth(), getHeight());
		if(_model._gamecheck == 0) {
			g.drawImage(_mainimage, 0, 0, 1000, 800, this);
		}
		else if(_model._gamecheck == 1) {
			int cnt=0;
			for(int row = 0; row < _model._num; row++) {
				for(int col = 0; col < _model._num; col++) {
					int x = col * 300;
					int y = row * 300;
					g.drawImage(nxn_1[_model._shuffle[cnt]], x, y, 280, 280, this);
					cnt++;
				}
			}
		}
		else if(_model._gamecheck == 2) {
			int cnt=0;
			for(int row = 0; row < _model._num; row++) {
				for(int col = 0; col < _model._num; col++) {
					int x = col * 225;
					int y = row * 225;
					g.drawImage(nxn_2[_model._shuffle[cnt]], x, y, 205, 205, this);
					cnt++;
				}
			}
		}
		else if(_model._gamecheck == 3) {
			int cnt=0;
			for(int row = 0; row < _model._num; row++) {
				for(int col = 0; col < _model._num; col++) {
					int x = col * 180;
					int y = row * 180;
					g.drawImage(nxn_3[_model._shuffle[cnt]], x, y, 160, 160, this);
					cnt++;
				}
			}
		}
		else if(_model._gamecheck == 10) {
			g.drawImage(_image, 0, 0, 1000, 800, this);
		}
	}
	
}