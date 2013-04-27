package br.com.casadocodigo.impossible;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
/**
 * 
 * Activity responsável interação do Android
 * Aqui configuramos as opções principais
 * como touch e view.
 * 
 * @author andersonleite
 *
 */
public class Game extends Activity implements OnTouchListener{
	
	// Essa é a view com a lógica do protótipo do jogo
    Impossible view;            
        
    // Método inicial da Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Inicia a lógica do jogo 
        view = new Impossible(this);
        
        // Habilita o touch
        view.setOnTouchListener(this);
        
        // Configura a view
        setContentView(view);
    }      
    
    // Método executado em sequência ao onCreate
    protected void onResume() {
        super.onResume();
        
        // Inicializa o protótipo
        view.resume();
    }
    
    
    @Override
    public boolean onTouch(View v, MotionEvent event) {
   
    	// Verifica ao toque na tela se a área contém um botão e move	
    	if(event.getX() < 100 && event.getY() > 290 &&  event.getY() < 310) {
    		view.init();
    	}
    	
    	// Verifica ao toque na tela se a área contém um botão e finaliza
    	if(event.getX() < 100 && event.getY() > 490 &&  event.getY() < 510) {
    		System.exit(0);
    	}
    	
    	// Incrementa em 10 pixels a posição
    	// vertical do player e o placar
    	view.moveDown(10); 
    	view.addScore(100);
    	
        return true;
    }    
}