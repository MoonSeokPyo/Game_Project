package marioGameProject;

import java.awt.Image;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;

//public class Model {
//	Charater ch;
//	public Model(){
//		
//		
//	}
//}
class mainImage{
	Image Temporary;
	mainImage() {
		Temporary = new ImageIcon("Image\\mario\\main.png").getImage();
	}
}
class Score{
	int score;
}
class Mario{
	Image left, right;
	Image left_run, right_run;
	Image left_jump, right_jump;
	Image hit;
	//int x, y;
	int width, height;
	//int life;
	boolean alive;
	public Mario() {
		width = 15;
		height = 20;
		left = new ImageIcon("Image\\mario\\mario_left.png").getImage();
		right = new ImageIcon("Image\\mario\\mario_right.png").getImage();
		left_run = new ImageIcon("Image\\mario\\mario_left_run.png").getImage();
		right_run = new ImageIcon("Image\\mario\\mario_right_run.png").getImage();
		left_jump = new ImageIcon("Image\\mario\\mario_left_jump.png").getImage();
		right_jump = new ImageIcon("Image\\mario\\mario_right_jump.png").getImage();
		hit = new ImageIcon("Image\\mario\\mario_hit.png").getImage();
	}
}
class Monster{
	int ghost_x, ghost_y;
	int boss_x, boss_y;
	int width, height;
	int life;
	Image ghost;
	Image boss;
	boolean checkmonster;
	public Monster() {
		checkmonster = false;
		ghost = new ImageIcon("Image\\mario\\ghost.png").getImage();
		boss = new ImageIcon("Image\\mario\\boss.png").getImage();
	}
}

class Music{
	//AudioInputStream audioInputStream;
	Clip main;
	Clip dead;
	Clip jump;
	public Music() {
		Dead_Music();
	}
	void Main_Music() {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Music\\mario\\Super-Mario-Bross-Theme-Song.wav"));
//			Clip clip = AudioSystem.getClip();
//			clip.open(audioInputStream);				
//			clip.loop(Clip.LOOP_CONTINUOUSLY);
//			clip.start();
			main = AudioSystem.getClip();
			main.open(audioInputStream);				
			main.loop(Clip.LOOP_CONTINUOUSLY);
			main.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	void Dead_Music() {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Music\\mario\\Mario_Dead.wav"));
			dead = AudioSystem.getClip();
			dead.open(audioInputStream);
//			dead.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	void Jump_Music() {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Music\\mario\\Jump.wav"));
			jump = AudioSystem.getClip();
			jump.open(audioInputStream);
			// 하 start하면 객체가 사라지는것 같은데??
			jump.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
class Map{
	int width, height;
	Image floor[][];
	Image door;
	// 59 77
	Image event, event_end;
	// 59 59
	Image coin;
	// 45 59
	Image trap;
	public Map() {
		width = height = 16;
		floor = new Image[4][4];
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				floor[i][j] = new ImageIcon("Image\\mario\\floor_" + ((4 * i) + j + 1) + ".png").getImage();
			}
		}
		door = new ImageIcon("Image\\mario\\door.png").getImage();
		event = new ImageIcon("Image\\mario\\event.png").getImage();
		event_end = new ImageIcon("Image\\mario\\event_end.png").getImage();
		coin = new ImageIcon("Image\\mario\\coin.png").getImage();
		trap = new ImageIcon("Image\\mario\\trap.png").getImage();
	}
}
class LinkedList{
	int x, y;
	int mapmovevalue;
	LinkedList link;
	public LinkedList(int x, int y) {
		this.x = x;
		this.y = y;
		link = null;
	}
	// 아으 위에 생성자 필요 없음 아래 생성자만 사용
	// 오버로딩
	public LinkedList(int x, int y, int value) {
		this.x = x;
		this.y = y;
		this.mapmovevalue = value;
		link = null;
	}
}