package com.tingshuo.tool;

public class ScoreToLevel {
	public static int covertScoreToLevel(int score){
		int level=0;
		if(score>=90){
			level=1;
		}else if(score>=80){
			level=2;
		}else if(score>=60){
			level=3;
		}else{
			level=4;
		}
		return level;
	}
	public static String covertLevelToString(int level){
		String str_level="";
		switch (level) {
		case 1:
			str_level+="<font color=#185D00>һ��</font>";
			break;
		case 2:
			str_level+="<font color=#FF5A00>����</font>";
			break;
		case 3:
			str_level+="<font color=#FF5A00>����</font>";
			break;
		case 4:
			str_level+="<font color=#FF0000>ʧ��</font>";
			break;
		default:
			break;
		}
		return str_level;
	}
}
