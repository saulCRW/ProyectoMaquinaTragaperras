import javax.swing.*;
import java.awt.*;

public class TragaPerrasClient {

    JFrame marco = new JFrame("Maquina Tragaperras");
 
    //Panel Izquierdo
    JPanel panelIzquierdo = new JPanel();
    JLabel img1 = new JLabel("Imagen1");
    JLabel img2 = new JLabel("Imagen2");
    JLabel img3 = new JLabel("Imagen3");
    JLabel img4 = new JLabel("Imagen4");
    JLabel img5 = new JLabel("Imagen5");

    //Panel Derecho
    JPanel panelDerecho = new JPanel();
    JButton palanca = new JButton("Aqui Ira la imagen de una palanca que iniciara el juego");
    
    public TragaPerrasClient() {
        
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
        

        marco.getContentPane().add(panelIzquierdo, BorderLayout.CENTER);
        marco.getContentPane().add(panelDerecho, BorderLayout.EAST);
    }

    public static void main(String[] args) {
        new TragaPerrasClient();
    }
}
