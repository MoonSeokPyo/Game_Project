package FinalProject;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
import java.util.*;


import java.io.File;
import javax.sound.sampled.*;

public class Model {
	int _count=0;				// 이미지 교체 횟수
	int _score=10000;			// 점수
	public int _clickcount=0;	// 클릭 횟수
	
	long starttime;				// 시작 시간
	long finishTime;			// 종료 시간
    long elapsedTime;			// 종료 시간 - 시작 시간 
	
    public int _num;			// numxnum
    public int _gamecheck=0;	// 게임 상태
	public int _shuffle[];		// 섞을 순서
	public int _answer[];		// 정답 순서
	public int _shufflememory;	// 이전 선택 기억
	public int _temp;			// 이미지 교체를 위한 temp 변수
	
	Random _randnum = new Random();
	
	public Model() {
		
	}
	
	void music() {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Music\\sample2.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);				
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void shuffle(int n) {
		_shuffle = new int[n*n];
		_answer = new int[n*n];
		for (int i = 0; i<n*n; i++) {
			_shuffle[i]=_randnum.nextInt(n*n);
			for(int j=0; j<i; j++) {
				if(_shuffle[j]==_shuffle[i]) {
					i--;
				}	
			}
			_answer[i]=i;
		}
		if(_answer==_shuffle) {
			shuffle(n);
		}
	}
	
	void score(GamePanel gamepanel) {
		elapsedTime = finishTime - starttime;
		_score=_score-((_count*50)+((int)(elapsedTime/1000)*10));
		if(_score < 1) {
			_score=1;
		}
		//System.out.println("점수는 : " + _score + " / 10000 점 입니다.");
		//System.out.println("총 이미지 교체 횟수는 : " + _count + "번 입니다.");
		//System.out.println("총 총 걸린 시간은 : " + elapsedTime +" (" + elapsedTime/1000 + "초) 입니다.");
		gamepanel.text.setText("점수는 " + _score + "점 입니다. 이미지를 " + _count + "번 교체했고 " + elapsedTime/1000 + "초 걸렸습니다.");
		
	}
}
