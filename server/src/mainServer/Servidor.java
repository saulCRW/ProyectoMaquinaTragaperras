import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor {

    public static void main(String[] args) {
        
        ServerSocket servidor = null;//Socket del Servidor
        Socket sc = null; //Socket del Cliente
        int Puerto = 5000; //Definimos pel puerto a usar
        DataInputStream in; 
        DataOutputStream out;

        try{
            servidor = new ServerSocket(Puerto);
            System.out.println("Servidor iniciado");

            while (true) {

                sc = servidor.accept();//Creamos la conexion con el cliente
                System.out.println("Cliente conectado");

                in = new DataInputStream(sc.getInputStream());//Camino del Cliente al Servidor
                out = new DataOutputStream(sc.getOutputStream());//Camino del Servidor al Cliente

                String mensaje = in.readUTF();//Espera que el cliente mande algo
                System.out.println(mensaje);//Imprimimos el mensaje del cliente

                out.writeUTF("Hola desde el servidor");//Mandamos mensaje al cliente

                sc.close();//Cerramos la conexion con el cliente
                System.out.println("Cliente desconectado");            
            }

        } catch (IOException ex){
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
