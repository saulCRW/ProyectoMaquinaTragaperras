package Threads;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/* Toda la logica del Hilo 1 */
public class thread_1 extends Thread {

    private int tiempo;

    public thread_1(int milisegundos) {
        this.tiempo = milisegundos;
    }

    public void run() {
        while (true) {
            int numero = (int) (Math.random() * (8) + 1);
            String ruta = "client/src/resources/assets" + numero + ".png";
            ImageIcon imageIcon = new ImageIcon(ruta);
            
            try {
                Thread.sleep(this.tiempo);
            } catch (InterruptedException ex) {
                Logger.getLogger(thread_1.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}