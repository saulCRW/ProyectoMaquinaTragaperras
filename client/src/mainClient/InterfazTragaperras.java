import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class InterfazTragaperras extends JFrame {

    private JPanel panelPrincipal, panelRodillos, panelControles, panelEstado;
    private JLabel[] rodillos;
    private final int NUM_RODILLOS = 3;

    private JButton btnGirar, btnSalir;
    private JTextField txtApuesta;
    private JLabel lblCreditos, lblEstado;

    private int creditos = 100;
    private boolean enJuego = false;

    private final String[] nombresFrutas = {
        "cereza.png", "limon.png", "melon.png", "sandia.png", "uva.png", "diamante.png", "siete.png"
    };
    private final String RUTA_IMAGENES = "imagenes/";
    private ImageIcon[] iconosFrutas;

    private Random random = new Random();

    public InterfazTragaperras() {
        setTitle("Máquina Tragaperras con Imágenes");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);

        inicializarComponentes();
        configurarLayout();
        configurarEventos();
        actualizarInterfaz();
    }

    private void inicializarComponentes() {
        panelPrincipal = new JPanel(new BorderLayout());
        panelRodillos = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));
        panelControles = new JPanel(new GridLayout(3, 2, 10, 10));
        panelEstado = new JPanel(new BorderLayout());

        // Cargar imágenes
        iconosFrutas = new ImageIcon[nombresFrutas.length];
        for (int i = 0; i < nombresFrutas.length; i++) {
            iconosFrutas[i] = new ImageIcon(RUTA_IMAGENES + nombresFrutas[i]);
            Image img = iconosFrutas[i].getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            iconosFrutas[i] = new ImageIcon(img);
        }

        // Crear rodillos
        rodillos = new JLabel[NUM_RODILLOS];
        for (int i = 0; i < NUM_RODILLOS; i++) {
            rodillos[i] = new JLabel(iconosFrutas[0]); // Imagen inicial
            rodillos[i].setHorizontalAlignment(SwingConstants.CENTER);
            rodillos[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            rodillos[i].setPreferredSize(new Dimension(80, 80));
            panelRodillos.add(rodillos[i]);
        }

        // Botones
        btnGirar = new JButton("GIRAR");
        btnGirar.setFont(new Font("Arial", Font.BOLD, 16));
        btnGirar.setBackground(Color.GREEN);
        btnGirar.setForeground(Color.WHITE);

        btnSalir = new JButton("SALIR");
        btnSalir.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalir.setBackground(Color.RED);
        btnSalir.setForeground(Color.WHITE);

        // Campo de apuesta
        txtApuesta = new JTextField("5");

        // Labels
        lblCreditos = new JLabel("Créditos: " + creditos);
        lblCreditos.setFont(new Font("Arial", Font.BOLD, 14));

        lblEstado = new JLabel("Listo para jugar");
        lblEstado.setHorizontalAlignment(SwingConstants.CENTER);
        lblEstado.setFont(new Font("Arial", Font.ITALIC, 12));
    }

    private void configurarLayout() {
        panelControles.setBorder(BorderFactory.createTitledBorder("Controles"));
        panelControles.add(new JLabel("Apuesta:"));
        panelControles.add(txtApuesta);
        panelControles.add(lblCreditos);
        panelControles.add(btnGirar);
        panelControles.add(new JLabel(""));
        panelControles.add(btnSalir);

        panelEstado.setBorder(BorderFactory.createTitledBorder("Estado del Juego"));
        panelEstado.add(lblEstado, BorderLayout.CENTER);

        panelPrincipal.add(panelRodillos, BorderLayout.CENTER);
        panelPrincipal.add(panelControles, BorderLayout.SOUTH);
        panelPrincipal.add(panelEstado, BorderLayout.NORTH);

        add(panelPrincipal);
    }

    private void configurarEventos() {
        btnGirar.addActionListener(e -> jugar());
        btnSalir.addActionListener(e -> System.exit(0));
    }

    private void jugar() {
        if (enJuego) return;

        int apuesta;
        try {
            apuesta = Integer.parseInt(txtApuesta.getText().trim());
            if (apuesta <= 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            lblEstado.setText("Introduce una apuesta válida");
            return;
        }

        if (creditos < apuesta) {
            lblEstado.setText("Créditos insuficientes");
            return;
        }

        enJuego = true;
        creditos -= apuesta;
        actualizarInterfaz();

        new Thread(() -> {
            try {
                for (int i = 0; i < 15; i++) {
                    for (int j = 0; j < NUM_RODILLOS; j++) {
                        final int index = random.nextInt(iconosFrutas.length);
                        final int r = j;
                        SwingUtilities.invokeLater(() -> rodillos[r].setIcon(iconosFrutas[index]));
                    }
                    Thread.sleep(100);
                }

                final int[] resultado = new int[NUM_RODILLOS];
                for (int j = 0; j < NUM_RODILLOS; j++) {
                    resultado[j] = random.nextInt(iconosFrutas.length);
                    final int r = j;
                    SwingUtilities.invokeLater(() -> rodillos[r].setIcon(iconosFrutas[resultado[r]]));
                }

                SwingUtilities.invokeLater(() -> {
                    calcularResultado(resultado, apuesta);
                    enJuego = false;
                    actualizarInterfaz();
                });

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    private void calcularResultado(int[] resultado, int apuesta) {
        boolean premio = false;
        int ganancia = 0;

        if (resultado[0] == resultado[1] && resultado[1] == resultado[2]) {
            premio = true;
            if (resultado[0] == 6) { // siete
                ganancia = apuesta * 50;
                lblEstado.setText("¡JACKPOT! Ganaste " + ganancia + " créditos");
            } else if (resultado[0] == 5) { // diamante
                ganancia = apuesta * 20;
                lblEstado.setText("¡PREMIO ALTO! Ganaste " + ganancia + " créditos");
            } else {
                ganancia = apuesta * 10;
                lblEstado.setText("¡Premio! Ganaste " + ganancia + " créditos");
            }
        } else if (resultado[0] == resultado[1] || resultado[1] == resultado[2] || resultado[0] == resultado[2]) {
            premio = true;
            ganancia = apuesta * 2;
            lblEstado.setText("¡Ganaste " + ganancia + " créditos!");
        } else {
            lblEstado.setText("Nada esta vez... sigue intentando");
        }

        if (premio) {
            creditos += ganancia;
        }
    }

    private void actualizarInterfaz() {
        lblCreditos.setText("Créditos: " + creditos);
        btnGirar.setEnabled(!enJuego && creditos > 0);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfazTragaperras().setVisible(true));
    }
}
