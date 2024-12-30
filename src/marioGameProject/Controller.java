package marioGameProject;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Timer;

public class Controller {
	View view;
	Music music;
	int map_move;	// 맵 이동 변수
	int acceleration;
	// 중력 가속도 구현 불가능 하다.....
	Timer Gravity;
	Timer t;
	Timer monster_t;
	Timer score;
	int before_x, before_y;
	int ju_num;		// 자연스러운 점프를 위한 변수
	int ju_ti;		// 점프 연산 횟수
	int savenownum;
	int x_speed;
	boolean StartGame;
	boolean EndGame;
	// 달리기 구현과 점프가 조금 더 높아야 한다.
	// 중력 가속도는 불가능 하고 시작화면이랑 함정 같은거 만들어야 한다.
	LinkedList head;
	// 10초전 위치
	boolean monster[];
	int monster_num;
	
	public Controller(){
		view = new View();
		music = new Music();
		acceleration = 1;
		map_move = 0;
		ju_num = 1;
		ju_ti = 0;
		x_speed = 4;
		StartGame = false;
		EndGame = false;
		head = null;
		monster = new boolean[3];
		for (int i = 0; i < 3; i++) {
			monster[i]= false;
		}
		monster_num = 0;
		view.Start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				view.panel.setBackground(Color.decode("#6096ff"));
				StartGame = true;
				view.panel.remove(view.Start);
				view.panel.setFocusable(true);
				view.panel.requestFocus();
			}
		});
		view.panel.add(view.Start);
		
		//view.panel.repaint();
		
		while(!StartGame) {
			System.out.println("loading");
		}
//		while(true) {
//			if(StartGame) {
//				break;
//			}
//		}
		// 와 이건 진짜 모르겠다 머가 문제인지;;;;;;;;;;
		music.Main_Music();
		
		view.Continue.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource() == view.Continue) {
					view.set_State = false;
					view.panel.remove(view.Continue);
					view.panel.remove(view.restart);
					view.panel.remove(view.exit);
					view.panel.setFocusable(true);
					view.panel.requestFocus();
					ResetStatus();
					view.nownum = savenownum;
					t.start();
				}
			}
		});
		view.restart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource() == view.restart) {
					view.set_State = false;
					view.panel.remove(view.Continue);
					view.panel.remove(view.restart);
					view.panel.remove(view.exit);
					view.main_x = 100;
					view.main_y = (view.frame_height/8)*7 - view.mario.height;
					map_move = 0;
					MoveMap(map_move);
					view.panel.setFocusable(true);
					view.panel.requestFocus();
					ResetStatus();
					view.nownum = 0;
					t.start();
				}
			}
		});
		view.panel.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				int keycode = e.getKeyCode();
				switch(keycode) {
				case KeyEvent.VK_UP:
					System.out.println("방향키 떼어짐");
//					jump_key = false;
					break;
				case KeyEvent.VK_DOWN:		System.out.println("방향키 떼어짐");	break;
				case KeyEvent.VK_RIGHT:	
					System.out.println("방향키 떼어짐");
					view.right_key = false;
//					if(!view.right_key&&!view.left_key&&!view.mario_down_Boundary(view.main_x, view.main_y)) {
					if(!view.right_key&&!view.left_key) {
						view.nownum = 0;
					}
					break;
				case KeyEvent.VK_LEFT:
					System.out.println("방향키 떼어짐");
					view.left_key = false;
//					if(!view.right_key&&!view.left_key&&!view.mario_down_Boundary(view.main_x, view.main_y)) {
					if(!view.right_key&&!view.left_key) {
						view.nownum = 1;
					}
					break;
				case KeyEvent.VK_SHIFT:
					x_speed = 4;
					break;
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				int keycode = e.getKeyCode();
				switch(keycode) {
				case KeyEvent.VK_SPACE:
					if(!view.nowjump && !view.mario_down_Boundary(view.main_x, view.main_y)) {
						if(view.nownum == 1 || view.nownum == 3) {
							view.nownum = 5;
						}
						else if(view.nownum == 0 || view.nownum == 2) {
							view.nownum = 4;
						}
						music.Jump_Music();
						
						view.jump_key = view.nowjump = view.jump_up = true;
					}
					break;
				case KeyEvent.VK_UP:
					System.out.println("방향키 눌림");
//					if(!nowjump && !CheckBoundary(main_x + 7, main_y + mario.height + 1)) {
//						jump_key = nowjump = jump_up = true;
//						if(nowmario == mario.left || nowmario == mario.left_run) {
//							nowmario = mario.left_jump;
//						}
//						else if(nowmario == mario.right || nowmario == mario.right_run) {
//							nowmario = mario.right_jump;
//						}
//					}
					break;
				case KeyEvent.VK_DOWN:		System.out.println("방향키 눌림");	break;
				case KeyEvent.VK_RIGHT:	
//					System.out.println("방향키 눌림");
//					if(!view.mario_down_Boundary(view.main_x, view.main_y)) {
					view.right_key = true;
					view.left_key = false;
//					}
//					else {
//						view.jump_right = true;
//					}
					break;
				case KeyEvent.VK_LEFT:
//					System.out.println("방향키 눌림");
//					if(!view.mario_down_Boundary(view.main_x, view.main_y)) {
					view.left_key = true;
					view.right_key = false;
//					}
//					else {
//						view.jump_left = true;
//					}
					break;
				case KeyEvent.VK_SHIFT:
					x_speed = 8;
					break;
				case KeyEvent.VK_CONTROL:
					skill();
					break;
				case KeyEvent.VK_ESCAPE:
					if(view.set_State) {
						view.set_State = false;
						view.panel.remove(view.Continue);
						view.panel.remove(view.restart);
						view.panel.remove(view.exit);
						ResetStatus();
						view.nownum = savenownum;
						t.start();
					}
					else {
						view.set_State = true;
						view.panel.add(view.Continue);
						view.panel.add(view.restart);
						view.panel.add(view.exit);
						savenownum = view.nownum;
						t.stop();
					}
					view.panel.repaint();
					break;
				}
			}
		});
		t = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				inputnode();
				view.time = view.time + 0.05;
				view.score = view.score - 0.1;
				// 점프
				if(view.jump_key) {
					// 왼쪽 위 점프
					if(view.left_key && view.jump_up) {
						view.nownum = 5;
//						if(view.main_x - x_speed > 0 && view.mario_left_Boundary(view.main_x - x_speed, view.main_y)) {
//							if(view.main_x < view.frame_width/10 && map_move != 0 && map_move - x_speed >= 0) {
//								if(map_move - 1 > 0) {
//									map_move = map_move - x_speed;
//									MoveMap(map_move);
//								}
//							}
//							else if(view.main_x - x_speed > 0) {
//								view.main_x = view.main_x - x_speed;
//							}
//						}
//						else if(map_move == 0 && view.main_x - x_speed <= 0) {
//							for (int i = view.main_x; i > 0; i--) {
//								view.main_x--;
//							}
//						}
//						else {
//							if(view.main_x < view.frame_width/10 && map_move != 0) {
//								if(map_move - 1 > 0) {
//									while(map_move != 0 && view.mario_left_Boundary(view.main_x, view.main_y)) {
//										map_move--;
//										MoveMap(map_move);
//									}
//								}
//							}
//							else if(view.main_x - x_speed > 0) {
//								while(view.mario_left_Boundary(view.main_x, view.main_y)) {
//									view.main_x--;
//								}
//							}
//						}
						
						if(ju_ti >= 4) {
							ju_num = 1;
							ju_ti = 0;
							view.jump_up = false;
						}
						//else if(view.mario_up_Boundary(view.main_x, view.main_y-ju_num)) {
						else if(jumpcheck(ju_num)) {
							//Jump();
							view.main_y = view.main_y - ju_num;
							ju_num = ju_num * 4;
							//main_y = main_y - 16;
							ju_ti++;
						}
						//else if(!view.mario_up_Boundary(view.main_x, view.main_y-ju_num)) {
						else if(!jumpcheck(ju_num)) {
							for(int i = 0; i <= ju_num; i++) {
								if(!view.mario_up_Boundary(view.main_x, view.main_y - i)) {
									ju_num = i - 1;
									break;
								}
							}
							view.main_y = view.main_y - ju_num;
							ju_num = 1;
							ju_ti = 0;
							view.jump_up = false;
						}
						else if((view.main_y-ju_num) < 0) {
							ju_num = 1;
							ju_ti = 0;
							view.jump_up = false;
						}
						
						if(view.main_x - x_speed > 0 && view.mario_left_Boundary(view.main_x - x_speed, view.main_y)) {
							if(view.main_x < view.frame_width/10 && map_move != 0 && map_move - x_speed >= 0) {
								if(map_move - 1 > 0) {
									map_move = map_move - x_speed;
									MoveMap(map_move);
								}
							}
							else if(view.main_x - x_speed > 0) {
								view.main_x = view.main_x - x_speed;
							}
						}
						else if(map_move == 0 && view.main_x - x_speed <= 0) {
							for (int i = view.main_x; i > 0; i--) {
								view.main_x--;
							}
						}
						else {
							if(view.main_x < view.frame_width/10 && map_move != 0) {
								if(map_move - 1 > 0) {
									while(map_move != 0 && view.mario_left_Boundary(view.main_x, view.main_y)) {
										map_move--;
										MoveMap(map_move);
									}
								}
							}
							else if(view.main_x - x_speed > 0) {
								while(view.mario_left_Boundary(view.main_x, view.main_y)) {
									view.main_x--;
								}
							}
						}
						
					}
					// 오른쪽 위 점프
					else if(view.right_key && view.jump_up) {
						view.nownum = 4;
//						if(view.mario_right_Boundary(view.main_x + x_speed, view.main_y)) {
//							if(view.main_x > view.frame_width/3) {
//								if(map_move < view.map_width) {
//									map_move = map_move + x_speed;
//									MoveMap(map_move);
//								}
//							}
//							else {
//								view.main_x = view.main_x + x_speed;
//							}
//						}
//						else {
//							if(view.main_x > view.frame_width/3) {
//								if(map_move < view.map_width - view.frame_width/3) {
//									while(view.mario_right_Boundary(view.main_x, view.main_y)) {
//										map_move++;
//										MoveMap(map_move);
//									}
//								}
//							}
//							else {
//								while(view.mario_right_Boundary(view.main_x, view.main_y)) {
//									view.main_x++;
//								}
//							}
//						}
						
						if(ju_ti >= 4) {
							ju_num = 1;
							ju_ti = 0;
							view.jump_up = false;
						}
						//else if(view.mario_up_Boundary(view.main_x, view.main_y-ju_num)) {
						else if(jumpcheck(ju_num)) {
							view.main_y = view.main_y - ju_num;
							ju_num = ju_num * 4;
							//main_y = main_y - 16;
							ju_ti++;
						}
						//else if(!view.mario_up_Boundary(view.main_x, view.main_y-ju_num)) {
						else if(!jumpcheck(ju_num)) {
							for(int i = 0; i <= ju_num; i++) {
								if(!view.mario_up_Boundary(view.main_x, view.main_y - i)) {
									ju_num = i - 1;
									break;
								}
							}
							view.main_y = view.main_y - ju_num;
							ju_num = 1;
							ju_ti = 0;
							view.jump_up = false;
						}
						else if((view.main_y-ju_num) < 0) {
							ju_num = 1;
							ju_ti = 0;
							view.jump_up = false;
						}
						
						if(view.mario_right_Boundary(view.main_x + x_speed, view.main_y)) {
							if(view.main_x > view.frame_width/3) {
								if(map_move < view.map_width) {
									map_move = map_move + x_speed;
									MoveMap(map_move);
									// 유령 등장!
//									if(!monster[monster_num] && map_move >= 100 + (monster_num * 100)) {
//										new_monster();
//									}
								}
							}
							else {
								view.main_x = view.main_x + x_speed;
							}
						}
						else {
							if(view.main_x > view.frame_width/3) {
								if(map_move < view.map_width - view.frame_width/3) {
									while(view.mario_right_Boundary(view.main_x, view.main_y)) {
										map_move++;
										MoveMap(map_move);
										// 유령 등장!
//										if(!monster[monster_num] && map_move >= 100 + (monster_num * 100)) {
//											new_monster();
//										}
									}
								}
							}
							else {
								while(view.mario_right_Boundary(view.main_x, view.main_y)) {
									view.main_x++;
								}
							}
						}
						
					}
					// 제자리 점프
					else if(view.jump_up) {
						if(ju_ti >= 4) {
							ju_num = 1;
							ju_ti = 0;
							view.jump_up = false;
						}
						//else if(view.mario_up_Boundary(view.main_x, view.main_y-ju_num)) {
						else if(jumpcheck(ju_num)) {
							view.main_y = view.main_y - ju_num;
							ju_num = ju_num * 4;
							//main_y = main_y - 16;
							ju_ti++;
						}
						//else if(!view.mario_up_Boundary(view.main_x, view.main_y-ju_num)) {
						else if(!jumpcheck(ju_num)) {
							for(int i = 0; i <= ju_num; i++) {
								if(!view.mario_up_Boundary(view.main_x, view.main_y - i)) {
									ju_num = i - 1;
									break;
								}
							}
							view.main_y = view.main_y - ju_num;
							ju_num = 1;
							ju_ti = 0;
							view.jump_up = false;
						}
						else if((view.main_y-ju_num) < 0) {
//							main_y = main_y - ju_num++;
//							ju_ti++;
							// 정프 중지
							ju_num = 1;
							ju_ti = 0;
							view.jump_up = false;
						}
					}
					// 점프후 내려올 때 좌우
					else if(view.left_key) {
						if(view.main_x - x_speed > 0 && view.mario_left_Boundary(view.main_x - x_speed, view.main_y)) {
							if(view.main_x < view.frame_width/10 && map_move != 0 && map_move - x_speed >= 0) {
								if(map_move - 1 > 0) {
									map_move = map_move - x_speed;
									MoveMap(map_move);
								}
							}
							else if(view.main_x - x_speed > 0) {
								view.main_x = view.main_x - x_speed;
							}
						}
						else if(map_move == 0 && view.main_x - x_speed <= 0) {
							for (int i = view.main_x; i > 0; i--) {
								view.main_x--;
							}
						}
						else {
							if(view.main_x < view.frame_width/10 && map_move != 0) {
								if(map_move - 1 > 0) {
									while(map_move != 0 && view.mario_left_Boundary(view.main_x, view.main_y)) {
										map_move--;
										MoveMap(map_move);
									}
								}
							}
							else if(view.main_x - x_speed > 0) {
								while(view.mario_left_Boundary(view.main_x, view.main_y)) {
									view.main_x--;
								}
							}
						}
					}
					else if(view.right_key) {
						if(view.mario_right_Boundary(view.main_x + x_speed, view.main_y)) {
							if(view.main_x > view.frame_width/3) {
								if(map_move < view.map_width) {
									map_move = map_move + x_speed;
									MoveMap(map_move);
									// 유령 등장!
//									if(!monster[monster_num] && map_move >= 100 + (monster_num * 100)) {
//										new_monster();
//									}
								}
							}
							else {
								view.main_x = view.main_x + x_speed;
							}
						}
						else {
							if(view.main_x > view.frame_width/3) {
								if(map_move < view.map_width - view.frame_width/3) {
									while(view.mario_right_Boundary(view.main_x, view.main_y)) {
										map_move++;
										MoveMap(map_move);
										// 유령 등장!
//										if(!monster[monster_num] && map_move >= 100 + (monster_num * 100)) {
//											new_monster();
//										}
									}
								}
							}
							else {
								while(view.mario_right_Boundary(view.main_x, view.main_y)) {
									view.main_x++;
								}
							}
						}
					}
					// 점프 내려오는 거 처리
					if (!view.mario_down_Boundary(view.main_x, view.main_y)) {
						view.jump_key = false;
						view.nowjump = false;
						if(view.nownum == 5) {
							view.nownum = 1;
						}
						else if(view.nownum == 4) {
							view.nownum = 0;
						}
					}
				}
				// 땅 밟고 이동
				else if(view.left_key&&!view.jump_key&&!view.mario_down_Boundary(view.main_x, view.main_y)) {
					if(view.main_x - x_speed > 0 && view.mario_left_Boundary(view.main_x - x_speed, view.main_y)) {
						if(view.main_x < view.frame_width/10 && map_move != 0 && map_move - x_speed >= 0) {
							if(map_move - 1 > 0) {
								map_move = map_move - x_speed;
								MoveMap(map_move);
							}
						}
						else if(view.main_x - x_speed > 0) {
							view.main_x = view.main_x - x_speed;
						}
					}
					else if(map_move == 0 && view.main_x - x_speed <= 0) {
						for (int i = view.main_x; i > 0; i--) {
							view.main_x--;
						}
					}
					else {
						if(view.main_x < view.frame_width/10 && map_move != 0) {
							if(map_move - 1 > 0) {
								while(map_move != 0 && view.mario_left_Boundary(view.main_x, view.main_y)) {
									map_move--;
									MoveMap(map_move);
								}
							}
						}
						else if(view.main_x - x_speed > 0) {
							while(view.mario_left_Boundary(view.main_x, view.main_y)) {
								view.main_x--;
							}
						}
					}
					if(view.left_run) {
						view.nownum = 1;
						view.left_run = false;
					}
					else {
						view.nownum = 3;
						view.left_run = true;
					}
				}
				else if(view.right_key&&!view.jump_key&&!view.mario_down_Boundary(view.main_x, view.main_y)) {
					if(view.mario_right_Boundary(view.main_x + x_speed, view.main_y)) {
						if(view.main_x > view.frame_width/3) {
							if(map_move < view.map_width) {
								map_move = map_move + x_speed;
								MoveMap(map_move);
								// 유령 등장!
//								if(!monster[monster_num] && map_move >= 10 + (monster_num * 100)) {
//									new_monster();
//								}
							}
						}
						else {
							view.main_x = view.main_x + x_speed;
						}
					}
					else {
						if(view.main_x > view.frame_width/3) {
							if(map_move < view.map_width - view.frame_width/3) {
								while(view.mario_right_Boundary(view.main_x, view.main_y)) {
									map_move++;
									MoveMap(map_move);
									// 유령 등장!
//									if(!monster[monster_num] && map_move >= 10 + (monster_num * 100)) {
//										new_monster();
//									}
								}
							}
						}
						else {
							while(view.mario_right_Boundary(view.main_x, view.main_y)) {
								view.main_x++;
							}
						}
					}
					if(view.right_run) {
						view.nownum = 0;
						view.right_run = false;
					}
					else {
						view.nownum = 2;
						view.right_run = true;
					}
				}
				// 공중 이동
				else if(view.left_key) {
					if(view.main_x - x_speed > 0 && view.mario_left_Boundary(view.main_x - x_speed, view.main_y)) {
						if(view.main_x < view.frame_width/10 && map_move != 0 && map_move - x_speed >= 0) {
							if(map_move - 1 > 0) {
								map_move = map_move - x_speed;
								MoveMap(map_move);
							}
						}
						else if(view.main_x - x_speed > 0) {
							view.main_x = view.main_x - x_speed;
						}
					}
					else if(map_move == 0 && view.main_x - x_speed <= 0) {
						for (int i = view.main_x; i > 0; i--) {
							view.main_x--;
						}
					}
					else {
						if(view.main_x < view.frame_width/10 && map_move != 0) {
							if(map_move - 1 > 0) {
								while(map_move != 0 && view.mario_left_Boundary(view.main_x, view.main_y)) {
									map_move--;
									MoveMap(map_move);
								}
							}
						}
						else if(view.main_x - x_speed > 0) {
							while(view.mario_left_Boundary(view.main_x, view.main_y)) {
								view.main_x--;
							}
						}
					}
				}
				else if(view.right_key) {
					if(view.mario_right_Boundary(view.main_x + x_speed, view.main_y)) {
						if(view.main_x > view.frame_width/3) {
							if(map_move < view.map_width - view.frame_width/3) {
								map_move = map_move + x_speed;
								MoveMap(map_move);
								// 유령 등장!
//								if(!monster[monster_num] && map_move >= 100 + (monster_num * 100)) {
//									new_monster();
//								}
							}
						}
						else {
							view.main_x = view.main_x + x_speed;
						}
					}
					else {
						if(view.main_x > view.frame_width/3) {
							if(map_move < view.map_width - view.frame_width/3) {
								while(view.mario_right_Boundary(view.main_x, view.main_y)) {
									map_move++;
									MoveMap(map_move);
									// 유령 등장!
//									if(!monster[monster_num] && map_move >= 100 + (monster_num * 100)) {
//										new_monster();
//									}
								}
							}
						}
						else {
							while(view.mario_right_Boundary(view.main_x, view.main_y)) {
								view.main_x++;
							}
						}
					}
				}
				view.panel.repaint();
				System.out.println(checklistsize());
			}
		});
		t.start();
		monster_t = new Timer(50, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				switch(monster_num) {
				case 1:
					view.monster.ghost_x--;
					//if(monster[0])
					//showmap_1[][]=
					
					// 아으 맵에다가 하면 안되는 구나;;;;
					break;
				case 2:
					break;
				case 3:
					break;
				}
			}
		});
		while(true) {
			Gravity();
			try {
				Thread.sleep(15);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	void Gravity() {
		if(!view.jump_up && !view.set_State) {
			for (int i = 0; i < 4; i++) {
				if(view.mario_down_Boundary(view.main_x, view.main_y)) {
					view.main_y++;
				}
			}
			if(view.main_y > view.frame_height + 50 && !EndGame){
				//music.Dead_Music();
				music.dead.start();
				music.main.stop();
				EndGame = true;
				Timer end = new Timer(2000, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						System.exit(0);
					}
				});
				t.stop();
				end.start();
			}
		}
	}
	void MoveMap(int n) {
		for (int i = 0; i < view.frame_width; i++) {
			for (int j = 0; j < view.frame_height; j++) {
				view.showmap_1[i][j] = view.map_1[i+n][j];
			}
		}
		for (int i = 0; i < view.frame_width; i++) {
			for (int j = 0; j < view.frame_height + 100; j++) {
				view.showBoundary[i][j] = view.Boundary[i+n][j];
			}
		}
	}
	void ResetStatus() {
		view.left_key = view.right_key = view.jump_key = view.left_run = view.right_run = view.nowjump = view.jump_up = false;
	}
	boolean jumpcheck(int n) {
		for (int i = 0; i <= n; i++) {
			if(!view.mario_up_Boundary(view.main_x, view.main_y - i)) {
				return false; 
			}
		}
		return true;
	}
	void inputnode() {
		if(head == null) {
			head = new LinkedList(view.main_x, view.main_y, map_move);
			return;
		}
		LinkedList node = new LinkedList(view.main_x, view.main_y, map_move);
		LinkedList temp = head;
		while(temp.link != null) {
			temp = temp.link;
		}
		temp.link = node;
		if(checklistsize() == 201) {
			LinkedList deletenode = head;
			head = head.link;
			deletenode = null;
		}
	}
	int checklistsize() {
		if(head == null) {
			return 0;
		}
		LinkedList temp = head;
		int size = 1;
		while(temp.link != null) {
			temp = temp.link;
			size++;
		}
		return size;
	}
	void deletelist() {
		if(head == null) {
			return;
		}
		LinkedList temp = head;
		//head = null;
		// 데이터 낭비 일어날텐데;;;;;
		LinkedList dl;
		while(temp.link != null) {
			dl = temp;
			temp = temp.link;
			dl = null;
		}
	}
	void skill() {
		view.main_x = head.x;
		view.main_y = head.y;
		map_move = head.mapmovevalue;
		deletelist();
		MoveMap(map_move);
	}
	void new_monster() {
		view.monster.ghost_x = 1900;
		view.monster.ghost_y = 1000;
		monster_num++;
		view.monster.checkmonster = true;
		monster_t.start();
	}
//	void Jump() {
//		view.main_y = view.main_y - ju_num;
//		ju_num = ju_num * 3;
//		//main_y = main_y - 16;
//		ju_ti++;
//	}
}
