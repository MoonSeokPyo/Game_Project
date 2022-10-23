package FinalProject;

import java.awt.event.*;

public class MouseHandler implements MouseListener {
	Model _model;
	ImagePanel _imagePanel;
	GamePanel _gamepanel;
	
	public MouseHandler(ImagePanel imagepanel, Model model, GamePanel gamepanel) {
		_imagePanel = imagepanel;
		_model = model;
		_gamepanel = gamepanel;
	}
	
	public void mouseEntered (MouseEvent e) {
		
	}
	// 컴포넌트 위에 올라온 마우스가 컴포넌트를 벗어날 때
	public void mouseExited (MouseEvent e) {
		
	}
	// 눌러질 때
	public void mousePressed (MouseEvent e) {
		
	}
	// 클릭
	public void mouseClicked (MouseEvent e) {
		int num[][];
		int cnt = 0;
		int answernum=0;
		if(_model._gamecheck == 1) {
			int col = e.getX() / 300;
			int row = e.getY() / 300;
			if (e.getX()<= 900 && e.getY()<= 900) {
				_model._clickcount++;
				num = new int[3][3];
				for(int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						num[i][j] = cnt;
						cnt++;
					}
				}
				if(_model._clickcount % 2 == 0) {
					_model._shuffle[_model._shufflememory]=_model._shuffle[num[row][col]];
					_model._shuffle[num[row][col]]=_model._temp;
					_model._count++;
				}
				else if(_model._clickcount % 2 == 1) {
					_model._temp=_model._shuffle[num[row][col]];
					_model._shufflememory=num[row][col];
				}
			}
			for (int i = 0; i < 9; i++) {
				if(_model._shuffle[i]==_model._answer[i]) {
					answernum++;
				}
			}
			_gamepanel.text.setText("마우스 클릭 횟수"+_model._clickcount + "교체 횟수"+_model._count);
			if(answernum == 9) {
				_model._gamecheck=10;
				_model.finishTime = System.currentTimeMillis();
				_model.score(_gamepanel);
			}
		}
		else if(_model._gamecheck == 2) {
			int col = e.getX() / 225;
			int row = e.getY() / 225;
			if (e.getX()<= 900 && e.getY()<= 900) {
				_model._clickcount++;
				num = new int[4][4];
				for(int i = 0; i < 4; i++) {
					for (int j = 0; j < 4; j++) {
						num[i][j] = cnt;
						cnt++;
					}
				}
				if(_model._clickcount % 2 == 0) {
					_model._shuffle[_model._shufflememory]=_model._shuffle[num[row][col]];
					_model._shuffle[num[row][col]]=_model._temp;
					_model._count++;
				}
				else if(_model._clickcount % 2 == 1) {
					_model._temp=_model._shuffle[num[row][col]];
					_model._shufflememory=num[row][col];
				}
			}
			for (int i = 0; i < 16; i++) {
				if(_model._shuffle[i]==_model._answer[i]) {
					answernum++;
				}
			}
			_gamepanel.text.setText("마우스 클릭 횟수"+_model._clickcount + "교체 횟수"+_model._count);
			if(answernum == 16) {
				_model._gamecheck=10;
				_model.finishTime = System.currentTimeMillis();
				_model.score(_gamepanel);
			}
		}
		else if(_model._gamecheck == 3) {
			int col = e.getX() / 180;
			int row = e.getY() / 180;
			if (e.getX()<= 900 && e.getY()<= 900) {
				_model._clickcount++;
				num = new int[5][5];
				for(int i = 0; i < 5; i++) {
					for (int j = 0; j < 5; j++) {
						num[i][j] = cnt;
						cnt++;
					}
				}
				if(_model._clickcount % 2 == 0) {
					_model._shuffle[_model._shufflememory]=_model._shuffle[num[row][col]];
					_model._shuffle[num[row][col]]=_model._temp;
					_model._count++;
				}
				else if(_model._clickcount % 2 == 1) {
					_model._temp=_model._shuffle[num[row][col]];
					_model._shufflememory=num[row][col];
				}
			}
			for (int i = 0; i < 25; i++) {
				if(_model._shuffle[i]==_model._answer[i]) {
					answernum++;
				}
			}
			_gamepanel.text.setText("마우스 클릭 횟수"+_model._clickcount + "교체 횟수"+_model._count);
			if(answernum == 25) {
				_model._gamecheck=10;
				_model.finishTime = System.currentTimeMillis();
				_model.score(_gamepanel);
			}
		}

		//System.out.println("마우스 클릭 횟수"+_model._clickcount);
		//System.out.println("교체 횟수"+_model._count);
		
		_imagePanel.repaint();
	}
	// 눌러진 마우스가 떼어질 때
	public void mouseReleased (MouseEvent e) {
		
	}
}