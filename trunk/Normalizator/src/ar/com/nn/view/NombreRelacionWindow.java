package ar.com.nn.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
	// otra prueba para evitar instanciaci�n m�ltiple
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
		lblIngreseNombre.setHorizontalTextPosition(SwingConstants.CENTER);
		lblIngreseNombre.setHorizontalAlignment(SwingConstants.CENTER);

		txtboxNombre = new JTextField();
		txtboxNombre.setLocation(76, 170);
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
		
		JButton btnAtrs = new JButton("Atr\u00E1s");
		btnAtrs.setBounds(76, 319, 117, 29);
		panelAbsoluto.add(btnAtrs);
		btnAtrs.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				VentanaPrincipal.getInstance().setVisible(true);
				setVisible(false);
			}
		});
		
		JButton btnSiguiente = new JButton("Siguiente");
		btnSiguiente.setBounds(455, 319, 117, 29);
		panelAbsoluto.add(btnSiguiente);
		btnSiguiente.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				Relacion rel = Relacion.getInstance();
				rel.setNombre(txtboxNombre.getText());
				CargaAtributosWindow.getInstance().setVisible(true);
				setVisible(false);
			}
		});
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
