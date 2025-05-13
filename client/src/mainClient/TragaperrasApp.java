import javax.swing.*;

/**
 * Clase principal que inicia la aplicación de la máquina tragaperras.
 * Esta clase contiene el método main y se encarga de lanzar la interfaz gráfica.
 */
public class TragaperrasApp {
    
    public static void main(String[] args) {
        // Utilizamos SwingUtilities para asegurar que la interfaz se crea en el hilo de eventos de Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Establecemos el Look and Feel del sistema operativo para una mejor apariencia
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                // Creamos y mostramos la interfaz gráfica
                InterfazTragaperras interfaz = new InterfazTragaperras();
                interfaz.setVisible(true);
                
                // Imprimimos información en la consola
                System.out.println("Aplicación de tragaperras iniciada con éxito");
                System.out.println("Esperando conexiones de red para el modo multijugador...");
            }
        });
    }
}