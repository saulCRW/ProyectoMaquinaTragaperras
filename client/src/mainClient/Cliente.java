import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*Atencion, dentro de Cliente solo establecera la conexion con el servidor, 
y enviara algunas variables de InterfazTragaPerrasClient por aqui directo al servidor*/

public class Cliente {
    public static void main(String[] args) {
        
        final String HOST = "127.0.0.1";
        final int puerto = 5000;
        DataInputStream in; 
        DataOutputStream out;
        

        try {
            Socket sc = new Socket(HOST, puerto);

            in = new DataInputStream(sc.getInputStream());//Camino del Cliente al Servidor
            out = new DataOutputStream(sc.getOutputStream());//Camino del Servidor al Cliente

            out.writeUTF("Hola mundo desde el cliente");//Mandamos un mensaje a Servidor

            String mensaje = in.readUTF();//Leemos mensaje del servidor
            System.out.println(mensaje);

            sc.close();//Nos desconectamos del servidor 


        } catch (IOException ex){
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
