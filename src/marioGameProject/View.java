package marioGameProject;

import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;

import javax.swing.*;

public class View {
	MyFrame myframe;
	MyPanel panel;
	Dimension window;
	int frame_width, frame_height;
	// window.width == 1920
	// window.height == 1080
	int map_width;
	// 화면 가로길이의 5배
	// map_width == 9600
	// 모든 화면에서 최적화 하려고 했지만 각 화면 마다 픽셀이 달라져서 맵 크기가 문제가 된다.
	boolean set_State;
	JButton Start, Continue, restart , exit;
	Mario mario;
	Map map;
	Monster monster;
	boolean Boundary[][], showBoundary[][];		// 맵 블록 유무 배열
	//int floor_1[][], floor_2[][], floor_3[][];
	//int floor_num, floor_x[], floor_y[];
	int main_x, main_y;
	boolean on, side;
	boolean left_key, right_key, jump_key;
	boolean left_run, right_run, nowjump, jump_up;		// nowjump == 공중
	boolean jump_left, jump_right;
	int life;
	Image nowmario[];
	int nownum;
	// Image 대입 연산을 줄이기 위해 nowmario를 배열로 하고 nownum 값을 바꾸자 
	Image map_1[][], showmap_1[][];		// 맵 이미지 배열, 실제 화면 맵 이미지 배열
	double time, score;
	
	public View(){
		window = Toolkit.getDefaultToolkit().getScreenSize();
		window.width = 1920;
		window.height = 1080;
		frame_width = window.width/2;
		frame_height = window.height/2;
		map_width = window.width * 5;
		myframe = new MyFrame();
		map = new Map();
		monster = new Monster();
		CreateMap();
		mario = new Mario();
		main_x = 100;
		main_y = (frame_height/8)*7 - mario.height;
		life = 3;
		score = 10000;
		time = 0;
		set_State = false;
		left_key = right_key = jump_key = left_run = right_run = nowjump = jump_up = jump_left = jump_right = false;
		nowmario = new Image[6];
		SetMarioImage();
		nownum = 0;
		System.out.println(window.width+ "\n" + window.height + "\n" + map_width);
	}
	
	class MyFrame extends JFrame{
		public MyFrame(){
			setTitle("Super Mario");
			setSize(frame_width,frame_height);
			setResizable(false);
			// 사이즈 조절
			setLocation(window.width/5, window.height/5);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
			//setExtendedState(this.MAXIMIZED_BOTH);
			//전체화면
			//setUndecorated(true);
			//상단바 삭제 현제 에러 뜸 처음에는 문제 없었는데
			
			getContentPane().setLayout(null);
			//setLayout(null);

			
			panel = new MyPanel();
			//setpanel = new SettingPanel();
			//add(setpanel);
			add(panel);
			
			
			//setpanel.setVisible(false);
			
			panel.setFocusable(true);
			panel.requestFocus();
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////////////////
	class MyPanel extends JPanel implements ActionListener{
		public MyPanel(){
			setLayout(null);
			setSize(map_width, frame_height);
//			setLocation(100,100);
			
			//setBackground(Color.decode("#6096ff"));
			
			Start = new JButton("시작하기");
			Continue = new JButton("이어하기");
			restart = new JButton("처음부터");
			exit = new JButton("게임종료");
//			Continue.setBounds((frame_width/8) + 15,(frame_height/6) + 25,150,150);
//			restart.setBounds((frame_width/8) + 180,(frame_height/6) + 25,150,150);
//			exit.setBounds((frame_width/8) + 345,(frame_height/6) + 25,150,150);
			Start.setBounds(frame_width/4, frame_height/5, frame_width/2, frame_height/2);
			Continue.setBounds((frame_width/8) + frame_width/28, (frame_height/6) + frame_height/12, frame_width/5, frame_height/3);
			restart.setBounds((frame_width/8) + (frame_width/5 + (2 * frame_width)/28), (frame_height/6) + frame_height/12, frame_width/5, frame_height/3);
			exit.setBounds((frame_width/8) + (2 * frame_width/5 + (3 * frame_width)/28), (frame_height/6) + frame_height/12, frame_width/5, frame_height/3);
			//Continue.setBackground(Color.decode("#755139"));
			
			//Continue.addActionListener(this);
//			restart.addActionListener(this);
			exit.addActionListener(this);
			
			
			// setVisible(true); 다음에 실행 되도록 해야한다.
//			setFocusable(true);
//			// 컴포넌트가 포커스를 받을 수 있도록 설정
//			requestFocus();
			// 컴포넌트에게 포커스를 주어 키입력 가능하도록 함
		}
		
		protected void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        g.drawString("Score : " + (int)score, 750, 10);
	        g.drawString("Time : " + (int)time, 850, 10);
	        g.drawString("Life : " + life, 0, 10);
			for (int i = 0; i < frame_width; i++) {
				for (int j = 0; j < frame_height; j++) {
					if(showmap_1[i][j] != null) {
						g.drawImage(showmap_1[i][j], i, j, null);
						//  이거 마지막 매게 this 어쩌고 하는데 왜 null인지 찾아보자.
					}
				}
			}
			// 코인 그리기
			g.drawImage(nowmario[nownum], main_x, main_y, null);
			// 몬스터 그리기~
			if(monster.checkmonster) {
				g.drawImage(monster.ghost, monster.ghost_x, monster.ghost_y, null);
			}
	        if(set_State) {
	        	g.setColor(Color.decode("#d64b01"));
				//g.fillRect(frame_width/8,frame_height/6,510,200);
	        	g.fillRect(frame_width/8,frame_height/6,(frame_width/8)*6,frame_height/2);
	        }
//	        Graphics2D g2d = (Graphics2D) g.create();
//	        g2d.setColor(Color.RED);
//	        g2d.fill(box);
//	        g2d.dispose();
	    }
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
//			if(e.getSource() == Continue) {
//				set_State = false;
//				remove(Continue);
//				remove(restart);
//				remove(exit);
//				repaint();
//				panel.setFocusable(true);
//				panel.requestFocus();
//			}
//			if(e.getSource() == restart) {
////				set_State = false;
////				remove(Continue);
////				remove(restart);
////				remove(exit);
////				main_x = 100;
////				main_y = 400;
////				repaint();
//				panel.setFocusable(true);
//				panel.requestFocus();
//			}
			if(e.getSource() == exit) {
				System.exit(0);
			}
		}
	}

	///////////////////////////////////////////////////////////////////////// 메소드
	void SetMarioImage() {
		nowmario[0] = mario.right;
		nowmario[1] = mario.left;
		nowmario[2] = mario.right_run;
		nowmario[3] = mario.left_run;
		nowmario[4] = mario.right_jump;
		nowmario[5] = mario.left_jump;
	}
	boolean CheckBoundary(int x, int y) {
		return showBoundary[x][y];
	}
	boolean mario_right_Boundary(int x, int y) {
		for (int i = 0; i < mario.height; i++) {
			if(!CheckBoundary(x + 15, y+i)) {
				return false;
			}
		}
		return true;
	}
	boolean mario_left_Boundary(int x, int y) {
		for (int i = 0; i < mario.height; i++) {
			if(!CheckBoundary(x - 1, y+i)) {
				return false;
			}
		}
		return true;
	}
	boolean mario_up_Boundary(int x, int y) {
		for (int i = 0; i < mario.width; i++) {
			if(!CheckBoundary(x + i, y-1)) {
				return false;
			}
		}
		return true;
	}
	boolean mario_down_Boundary(int x, int y) {
		for (int i = 0; i < mario.width; i++) {
			if(!CheckBoundary(x + i, y + 20)) {
				return false;
			}
		}
		return true;
	}
	void CreateMap() {
		map_1 = new Image[map_width][frame_height];
		showmap_1 = new Image[frame_width][frame_height];
		Boundary = new boolean[map_width][frame_height + 100];
		showBoundary = new boolean[frame_width][frame_height + 100];
		
//		floor_1 = new int[10][5];
//		for (int i = 0; i < 10; i++) {
//			for (int j = 0; j < 5; j++) {
//				//floor_1[i][j] = 
//			}
//		}
//		floor_num = 100;
//		floor_x = new int [floor_num];
//		floor_y	= new int [floor_num];
//		floor_x[0] = 15;
//		floor_y[0] = 3;
		
		
		// 맵 이동마다 새로 갈아 주는 것과 처음부터 배열 크기를 크게 주어서 하는것 중 무엇이 좋은 거지?
		//Boundary = new boolean[frame_width][frame_height + 100];
		//////////////////////////////////////////////////////////////////////////////
		// 맵구현
		
		map_1[5*16][(frame_height/8)*7 - 77] = map.door;
		
		
		for (int i = 0; i < 18; i++) {
			for (int j = 0; j < 3; j++) {
				for (int x = 0; x < 4; x++) {
					for (int y = 0; y < 4; y++) {
						map_1[(i*16) + (y*4)][j*16 + (x*4) + (frame_height/8)*7] = map.floor[x][y];
					}
				}
			}
		}
		for (int i = 20; i < 25; i++) {
			for (int j = 0; j < 3; j++) {
				for (int x = 0; x < 4; x++) {
					for (int y = 0; y < 4; y++) {
						map_1[(i*16) + (y*4)][j*16 + (x*4) + (frame_height/8)*7] = map.floor[x][y];
					}
				}
				//map_1[i*16][j*16 + (frame_height/8)*7] = map.floor.getImage();
			}
		}
		for (int i = 27; i < 65; i++) {
			for (int j = 0; j < 3; j++) {
				for (int x = 0; x < 4; x++) {
					for (int y = 0; y < 4; y++) {
						map_1[(i*16) + (y*4)][j*16 + (x*4) + (frame_height/8)*7] = map.floor[x][y];
					}
				}
				//map_1[i*16][j*16 + (frame_height/8)*7] = map.floor.getImage();
			}
		}
		for (int i = 45; i < 47; i++) {
			for (int j = 1; j < i-43; j++) {
				for (int k = 1; k <= j; k++) {
					for (int x = 0; x < 4; x++) {
						for (int y = 0; y < 4; y++) {
							map_1[(i*16) + (y*4)][-k*16 + (x*4) + (frame_height/8)*7] = map.floor[x][y];
						}
					}
				}
			}
		}
		for (int i = 50; i < 60; i++) {
			for (int j = 1; j < i-48; j++) {
				for (int k = 1; k <= j; k++) {
					for (int x = 0; x < 4; x++) {
						for (int y = 0; y < 4; y++) {
							map_1[(i*16) + (y*4)][-k*16 + (x*4) + (frame_height/8)*7] = map.floor[x][y];
						}
					}
				}
			}
		}
		
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				map_1[(42*16) + (y*4)][-3*16 + (x*4) + (frame_height/8)*7] = map.floor[x][y];
			}
		}
		
		for (int i = 1; i <= 10; i++) {
			for (int x = 0; x < 4; x++) {
				for (int y = 0; y < 4; y++) {
					map_1[((64 + 2*i)*16) + (y*4)][-i*16 + (x*4) + (frame_height/8)*7] = map.floor[x][y];
				}
			}
		}
		for (int i = 1; i <= 5; i++) {
			for (int x = 0; x < 4; x++) {
				for (int y = 0; y < 4; y++) {
					map_1[((84 + 4*i)*16) + (y*4)][-10*16 + (x*4) + (frame_height/8)*7] = map.floor[x][y];
				}
			}
		}
		for (int i = 84; i <= 104; i++) {
			for (int j = 0; j < 3; j++) {
				for (int x = 0; x < 4; x++) {
					for (int y = 0; y < 4; y++) {
						map_1[(i*16) + (y*4)][j*16 + (x*4) + (frame_height/8)*7] = map.floor[x][y];
					}
				}
			}
		}
		
		for(int i = 0; i < 5; i++) {
			for (int x = 0; x < 4; x++) {
				for (int y = 0; y < 4; y++) {
					map_1[(104*16) + (y*4)][(-16 - i)*16 + (x*4) + (frame_height/8)*7] = map.floor[x][y];
				}
			}
		}
		for(int i = 104; i < 110; i++) {
			for (int x = 0; x < 4; x++) {
				for (int y = 0; y < 4; y++) {
					map_1[(i*16) + (y*4)][-15*16 + (x*4) + (frame_height/8)*7] = map.floor[x][y];
				}
			}
		}
		for(int i = 0; i < 2; i++) {
			for (int x = 0; x < 4; x++) {
				for (int y = 0; y < 4; y++) {
					map_1[(110*16) + (y*4)][(-14 - i)*16 + (x*4) + (frame_height/8)*7] = map.floor[x][y];
				}
			}
		}
		for(int i = 110; i < 113; i++) {
			for (int x = 0; x < 4; x++) {
				for (int y = 0; y < 4; y++) {
					map_1[(i*16) + (y*4)][-13*16 + (x*4) + (frame_height/8)*7] = map.floor[x][y];
				}
			}
		}
		for(int i = 113; i < 116; i++) {
			for (int x = 0; x < 4; x++) {
				for (int y = 0; y < 4; y++) {
					map_1[(i*16) + (y*4)][-12*16 + (x*4) + (frame_height/8)*7] = map.floor[x][y];
				}
			}
		}
		for(int i = 116; i < 119; i++) {
			for (int x = 0; x < 4; x++) {
				for (int y = 0; y < 4; y++) {
					map_1[(i*16) + (y*4)][-11*16 + (x*4) + (frame_height/8)*7] = map.floor[x][y];
				}
			}
		}
		
		for (int i = 108; i <= 130; i++) {
			for (int j = 0; j < 10; j++) {
				for (int x = 0; x < 4; x++) {
					for (int y = 0; y < 4; y++) {
						map_1[(i*16) + (y*4)][j*16 + (x*4) + ((frame_height/8)*7 - 7*16)] = map.floor[x][y];
					}
				}
			}
		}
		
		
		
		for (int i = 135; i <= 250; i++) {
			for (int j = 0; j < 3; j++) {
				for (int x = 0; x < 4; x++) {
					for (int y = 0; y < 4; y++) {
						map_1[(i*16) + (y*4)][j*16 + (x*4) + ((frame_height/8)*7)] = map.floor[x][y];
					}
				}
			}
		}
		
		map_1[245*16][(frame_height/8)*7 - 77] = map.door;
		
		/////////////////////////////////////////////////////////////////////////////
		for (int i = 0; i < map_width; i++) {
			for (int j = 0; j < frame_height + 100; j++) {
				Boundary[i][j] = true;
			}
		}
		for (int i = 0; i < map_width; i++) {
			for (int j = 0; j < frame_height + 100; j++) {
				if(j < frame_height && map_1[i][j] != null) {
					if(map_1[i][j] != null) {
						for (int x = 0; x < 4; x++) {
							for (int y = 0; y < 4; y++) {
								Boundary[i+x][j+y] = false;
							}
						}
					}
				}
			}
		}
		

		for (int i = 0; i < frame_width; i++) {
			for (int j = 0; j < frame_height; j++) {
				showmap_1[i][j] = map_1[i][j];
			}
		}
		for (int i = 0; i < frame_width; i++) {
			for (int j = 0; j < frame_height + 100; j++) {
				showBoundary[i][j] = Boundary[i][j];
			}
		}
	}
}