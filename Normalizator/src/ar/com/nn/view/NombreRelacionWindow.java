package ar.com.nn.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ar.com.nn.busisness.Relacion;

public class NombreRelacionWindow {

	private JFrame contenedor;
	private JTextField txtboxNombre;

	private static NombreRelacionWindow INSTANCE = null;

	// creador sincronizado para protegerse de posibles problemas multi-hilo
	// otra prueba para evitar instanciaci—n mœltiple
	private synchronized static void createInstance() {
		if (INSTANCE == null) {
			INSTANCE = new NombreRelacionWindow();
		}
	}

	public static NombreRelacionWindow getInstance() {
		if (INSTANCE == null)
			createInstance();
		return INSTANCE;
	}

	/**
	 * Create the application.
	 */
	public NombreRelacionWindow() {
		initialize();
		contenedor.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		contenedor = new JFrame();
		contenedor.setTitle("Normalizator");
		contenedor.setResizable(false);
		contenedor.getContentPane().setPreferredSize(new Dimension(200, 200));
		contenedor.setBounds(100, 100, 650, 400);
		contenedor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panelAbsoluto = new JPanel();
		contenedor.getContentPane().add(panelAbsoluto, BorderLayout.CENTER);
		panelAbsoluto.setLayout(null);

		JLabel lblIngreseNombre = new JLabel(
				"Ingrese el nombre de su relaci\u00F3n");
		lblIngreseNombre.setBounds(147, 56, 350, 35);
		panelAbsoluto.add(lblIngreseNombre);
		lblIngreseNombre.setPreferredSize(new Dimension(350, 35));
		lblIngreseNombre.setFont(new Font("Arial", lblIngreseNombre.getFont()
				.getStyle() | Font.BOLD, 22));
		lblIngreseNombre.setHorizontalTextPosition(SwingConstants.CENTER);
		lblIngreseNombre.setHorizontalAlignment(SwingConstants.CENTER);

		txtboxNombre = new JTextField();
		txtboxNombre.setLocation(76, 170);
		txtboxNombre.setFont(new Font("Arial", txtboxNombre.getFont()
				.getStyle(), 18));
		txtboxNombre.setMinimumSize(new Dimension(500, 20));
		txtboxNombre.setPreferredSize(new Dimension(500, 35));
		txtboxNombre.setSize(new Dimension(496, 35));
		txtboxNombre.setHorizontalAlignment(SwingConstants.CENTER);
		txtboxNombre.setColumns(35);
		txtboxNombre.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int k = e.getKeyCode();
				if (k == KeyEvent.VK_ENTER) {
					Relacion rel = Relacion.getInstance();
					rel.setNombre(txtboxNombre.getText());
					setVisible(false);
					CargaAtributosWindow cargaWin = CargaAtributosWindow
							.getInstance();
					cargaWin.setVisible(true);
				}
			}
		});
		panelAbsoluto.add(txtboxNombre);

		JButton btnAtras = new JButton("Atras");
		btnAtras.setFont(new Font("Arial", btnAtras.getFont().getStyle(), 14));
		btnAtras.setBounds(141, 313, 110, 40);
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				contenedor.setVisible(false);
				VentanaPrincipal ventanaPrincipalWin = VentanaPrincipal
						.getInstance();
				ventanaPrincipalWin.setVisible(true);
			}
		});

		panelAbsoluto.add(btnAtras);
		btnAtras.setPreferredSize(new Dimension(110, 40));

		JButton btnSiguiente = new JButton("Siguiente");
		btnSiguiente.setFont(new Font("Arial", btnSiguiente.getFont()
				.getStyle(), 14));
		btnSiguiente.setBounds(387, 313, 110, 40);

		// Boton Siguiente
		btnSiguiente.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Relacion rel = Relacion.getInstance();
				rel.setNombre(txtboxNombre.getText());
				setVisible(false);
				CargaAtributosWindow cargaWin = CargaAtributosWindow
						.getInstance();
				cargaWin.setVisible(true);
			}
		});

		panelAbsoluto.add(btnSiguiente);
		btnSiguiente.setPreferredSize(new Dimension(110, 40));
	}

	public void setVisible(boolean b) {
		if (b == true)
			contenedor.setVisible(true);
		else
			contenedor.setVisible(false);
	}

	public void clear() {
		txtboxNombre.setText("");
	}

}
