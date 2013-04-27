package br.com.casadocodigo.impossible;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 
 * Classe responsável por executar o game loop
 * e desenhar elemntos na tela
 * assim como detectar colisões.
 * 
 * @author andersonleite
 *
 */
class Impossible extends SurfaceView implements Runnable {
	
	// variáveis do android
	Paint paint;
	SurfaceHolder holder;
	Thread renderThread = null;
	
	// variáveis do jogo
	public int score;
	private double distance;
	private boolean gameover;
	volatile boolean running = false;
	int enemyX, enemyY, enemyRadius = 50;
	int playerX = 300, playerY = 300, playerRadius = 50;

	public Impossible(Context context) {
		super(context);
		paint = new Paint();
		holder = getHolder();
	}

	// inicializa o jogo
	public void resume() {
		running = true;
		renderThread = new Thread(this);
		renderThread.start();
	}

	// configurações iniciais
	public void init() {
		enemyX = enemyY = enemyRadius = 0;
		playerX = playerY = 300;
		playerRadius = 50;
		gameover = false;
	}

	// Game Loop
	public void run() {
		while (running) {
			if (!holder.getSurface().isValid())
				continue;

			if (gameover) {
				break;
			}

			// desenha no canvas	
			Canvas canvas = holder.lockCanvas();
			canvas.drawColor(Color.BLACK);

			// desenha jogador e inimigo
			drawEnemy(canvas);
			drawPlayer(canvas);

			// desenha botões	
			drawButtons(canvas);

			// detecta colisões	
			checkCollision(canvas);

			// atualiza placar	
			drawScore(canvas);

			// libera o canvas	
			holder.unlockCanvasAndPost(canvas);

		}
	}

	//desenha os botões
	private void drawButtons(Canvas canvas) {
		paint.setStyle(Style.FILL);
		paint.setColor(Color.WHITE);
		paint.setTextSize(30);
		canvas.drawText("Restart", 50, 300, paint);

		paint.setStyle(Style.FILL);
		paint.setColor(Color.WHITE);
		paint.setTextSize(30);
		canvas.drawText("Exit", 50, 500, paint);

	}

	// verifica colisões	
	private void checkCollision(Canvas canvas) {

		// teorema de pitágoras	
		distance = Math.pow(playerY - enemyY, 2)
				+ Math.pow(playerX - enemyX, 2);
		distance = Math.sqrt(distance);

		if (distance <= playerRadius + enemyRadius) {
			gameover = true;
			stopGame(canvas);
		}
	}

	// para o jogo
	private void stopGame(Canvas canvas) {
		paint.setStyle(Style.FILL);
		paint.setColor(Color.LTGRAY);
		paint.setTextSize(100);
		canvas.drawText("GAME OVER!", 50, 150, paint);
	}

	// atualiza o placar
	private void drawScore(Canvas canvas) {
		paint.setStyle(Style.FILL);
		paint.setColor(Color.WHITE);
		paint.setTextSize(50);
		canvas.drawText(String.valueOf(score), 50, 200, paint);
	}

	//desenha o player
	private void drawPlayer(Canvas canvas) {
		paint.setColor(Color.GREEN);
		canvas.drawCircle(playerX, playerY, playerRadius, paint);
	}

	// desenha o inimigo	
	private void drawEnemy(Canvas canvas) {
		paint.setColor(Color.GRAY);
		enemyRadius++;
		canvas.drawCircle(enemyX, enemyY, enemyRadius, paint);
	}

	// move o player
	public void moveDown(int pixels) { 
		playerY += pixels;
	}

	// soma pontos
	public void addScore(int points) { 
		score += points;
	}
}