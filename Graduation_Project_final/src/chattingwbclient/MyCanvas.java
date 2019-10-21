package chattingwbclient;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class MyCanvas extends Canvas {
	// 처음에 까만색 점 안찍히게 하기 위해서 x,y -값 지정
		int x = -50;
		int y = -50;
		int w = 10, h = 10;
		Color cr = Color.black;
		public MyCanvas() {
			setSize(400,400);
			setBackground(Color.white);
		}
		@Override
		public void paint(Graphics g) {
			g.setColor(cr);
			g.fillOval(x, y, w, h); // x, y 지점에 70,70 크기의 원 그리기
		}

		@Override
		public void update(Graphics g) {
			paint(g);
		}
		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getW() {
			return w;
		}

		public void setW(int w) {
			this.w = w;
		}

		public int getH() {
			return h;
		}

		public void setH(int h) {
			this.h = h;
		}

		public Color getCr() {
			return cr;
		}

		public void setCr(Color cr) {
			this.cr = cr;
		}


}
