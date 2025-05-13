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

    private final String[] SIMBOLOS = { "cereza", "limon", "melon", "sandia", "diamante", "siete", "uva" };
    private Random random = new Random();

    public InterfazTragaperras() {
        setTitle("Máquina Tragaperras - Estilo Visual");
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

        rodillos = new JLabel[NUM_RODILLOS];
        for (int i = 0; i < NUM_RODILLOS; i++) {
            rodillos[i] = new JLabel();
            rodillos[i].setHorizontalAlignment(SwingConstants.CENTER);
            rodillos[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            rodillos[i].setPreferredSize(new Dimension(80, 80));
            rodillos[i].setIcon(obtenerIcono(SIMBOLOS[0])); // imagen inicial
            panelRodillos.add(rodillos[i]);
        }

        btnGirar = new JButton("GIRAR");
        btnGirar.setFont(new Font("Arial", Font.BOLD, 16));
        btnGirar.setBackground(Color.GREEN);
        btnGirar.setForeground(Color.WHITE);

        btnSalir = new JButton("SALIR");
        btnSalir.setFont(new Font("Arial", Font.BOLD, 14));
        btnSalir.setBackground(Color.RED);
        btnSalir.setForeground(Color.WHITE);

        txtApuesta = new JTextField("5");
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
        panelControles.add(new JLabel("")); // espacio vacío
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
        if (enJuego)
            return;

        int apuesta;
        try {
            apuesta = Integer.parseInt(txtApuesta.getText().trim());
            if (apuesta <= 0)
                throw new NumberFormatException();
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
                // Animación
                for (int i = 0; i < 15; i++) {
                    for (int j = 0; j < NUM_RODILLOS; j++) {
                        final int index = random.nextInt(SIMBOLOS.length);
                        final int r = j;
                        SwingUtilities.invokeLater(() -> rodillos[r].setIcon(obtenerIcono(SIMBOLOS[index])));
                    }
                    Thread.sleep(100);
                }

                // Resultado final
                final int[] resultado = new int[NUM_RODILLOS];
                for (int j = 0; j < NUM_RODILLOS; j++) {
                    resultado[j] = random.nextInt(SIMBOLOS.length);
                    final int r = j;
                    SwingUtilities.invokeLater(() -> rodillos[r].setIcon(obtenerIcono(SIMBOLOS[resultado[r]])));
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
            if (resultado[0] == SIMBOLOS.length - 1) {
                ganancia = apuesta * 50;
                lblEstado.setText("¡JACKPOT! Ganaste " + ganancia + " créditos");
            } else if (resultado[0] == SIMBOLOS.length - 2) {
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

    private ImageIcon obtenerIcono(String nombreSimbolo) {
        String ruta = "imagenes/" + nombreSimbolo + ".png"; // ahora sin src ni carpetas intermedias
        java.io.File archivo = new java.io.File(ruta);
        if (!archivo.exists()) {
            System.err.println("❌ No se encontró la imagen: " + archivo.getAbsolutePath());
            return null;
        }

        ImageIcon iconoOriginal = new ImageIcon(ruta);
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        return new ImageIcon(imagenEscalada);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfazTragaperras().setVisible(true));
    }
}
