import javax.swing.*;
import java.awt.*;
import Threads.thread_1;
import Threads.thread_2;
import Threads.thread_3;
import Threads.thread_4;
import Threads.thread_5;



//ATENCION, AQUI SOLO SE MANEJARA LA INTERFAZ GRAFICA y la creacion de los objetos threads
public class InterfazTragaPerrasClient {

    

    JFrame marco = new JFrame("Maquina Tragaperras");
 
    //Panel Izquierdo
    JPanel panelIzquierdo = new JPanel();

    /*Parte de las imagenes, estas no se integraran como imagenes desde este documento,
     * cada una creara un objeto de threads*/

    thread_1 thread_one;
    thread_2 thread_two;
    thread_3 thread_three;
    thread_4 thread_four;
    thread_5 thread_five;

    boolean resultado1 = false;
    boolean resultado2 = false;
    boolean resultado3 = false;
    boolean resultado4 = false;
    boolean resultado5 = false;

    //Inicializamos los resultados de cada hilo como 



    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {
    int tiempoMilisegundos = 100;
    thread_one = new thread_1(tiempoMilisegundos);
    thread_two = new thread_2(tiempoMilisegundos);
    thread_three = new thread_3(tiempoMilisegundos);
    thread_four = new thread_4(tiempoMilisegundos);
    thread_five = new thread_5(tiempoMilisegundos);

    palanca.setEnabled(false);

    thread_one.start();
    thread_two.start();
    thread_three.start();
    thread_four.start();
    thread_five.start();
    }

    /*private void btnDetenerPersona1ActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        thread_one.stop();
        thread_two.stop();
        thread_three.stop();
        thread_four.stop();
        thread_five.stop();
        respuesta1 = true;
        respuesta2 = true;
        respuesta3 = true;
        respuesta4 = true;
        respuesta5 = true;
        //comprobarResultado();  Esto se hara en el servidor
    }*/

    /*Agregar despues, esto ira en el boton de empezar el juego
     * respuesta1 = true;  Para almacenar la respuesta de cada hilo
     * comprobarResultado();  Para comprobar los resultados, esto se hara en servidor
     */

    JLabel img1 = new JLabel("Imagen1");
    JLabel img2 = new JLabel("Imagen2");
    JLabel img3 = new JLabel("Imagen3");
    JLabel img4 = new JLabel("Imagen4");
    JLabel img5 = new JLabel("Imagen5");



    //Panel Derecho
    JPanel panelDerecho = new JPanel();
    JButton palanca = new JButton("Aqui ira la imagen de una palanca que iniciara el juego");
    
    public InterfazTragaPerrasClient() {
        
        marco.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        marco.setExtendedState(marco.MAXIMIZED_BOTH);
        marco.setLayout(new BorderLayout());
        marco.setVisible(true);

        //Panel Izquierdo
        panelIzquierdo.setBackground(Color.red);
        panelIzquierdo.setLayout(new FlowLayout());
        panelIzquierdo.add(img1);
        panelIzquierdo.add(img2);
        panelIzquierdo.add(img3);
        panelIzquierdo.add(img4);
        panelIzquierdo.add(img5);

        //Panel Derecho
        panelDerecho.setBackground(Color.blue);
        panelDerecho.setLayout(new GridLayout(2,1));
        panelDerecho.add(palanca);

        palanca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });
        

        marco.getContentPane().add(panelIzquierdo, BorderLayout.CENTER);
        marco.getContentPane().add(panelDerecho, BorderLayout.EAST);
    }

    public static void main(String[] args) {
        new InterfazTragaPerrasClient();
    }
}
