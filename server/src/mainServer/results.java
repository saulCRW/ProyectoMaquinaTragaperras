import javax.swing.JOptionPane;
import java.util.logging.Level;
import java.util.logging.Logger;

/*Esta clase se encarga de calcular el resultado de la tragaperras */
public class results {

        //Este metodo procesara los resultados enviados por el servidor, que a su vez son mandados por el cliente

        private void comprobarResultado(boolean respuesta1, boolean respuesta2, boolean respuesta3, boolean respuesta4, boolean respuesta5) {

            this.respuesta1 = respuesta1;
            this.respuesta2 = respuesta2;
            this.respuesta3 = respuesta3;
            this.respuesta4 = respuesta4;
            this.respuesta5 = respuesta5;

            if (respuesta1 && respuesta2 && respuesta3 && respuesta4 && respuesta5) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {
                    Logger.getLogger(frmPersonas.class.getName()).log(Level.SEVERE, null, ex);
                }
                btnIniciar.setEnabled(true);
                if (lblPersona1.getIcon().toString().equals(lblPersona2.getIcon().toString()) && lblPersona1.getIcon().toString().equals(lblPersona3.getIcon().toString())) {
                    JOptionPane.showMessageDialog(null, "Felicitaciones, has ganado!");
                } else {
                    JOptionPane.showMessageDialog(null, "Vuelve a intentarlo");
                }
            }
    }
    
    
}
