package ar.com.nn.view;

import java.awt.Dimension;
import java.awt.List;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import ar.com.nn.busisness.Relacion;

public class CargaAtributosWindow {

	private JFrame contenedor;
	private JTextField txtfieldCargarAtributos;
	private List listaAtributos;
	private JButton btnBorrar;
	private ArrayList<String> atributosRelacion;
	private String atributo;
	private static CargaAtributosWindow INSTANCE = null;

	// creador sincronizado para protegerse de posibles problemas multi-hilo
	// otra prueba para evitar instanciaci—n mœltiple
	private synchronized static void createInstance() {
		if (INSTANCE == null) {
			INSTANCE = new CargaAtributosWindow();
		}
	}

	public static CargaAtributosWindow getInstance() {
		if (INSTANCE == null)
			createInstance();
		return INSTANCE;
	}

	/**
	 * Crea la ventana
	 */
	public CargaAtributosWindow() {
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
		contenedor.setBounds(100, 100, 650, 400);
		contenedor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contenedor.getContentPane().setLayout(null);

		atributosRelacion = new ArrayList<String>();

		JLabel lblCargaLosAtributos = new JLabel(
				"Cargue los atributos de la relaci\u00F3n");
		lblCargaLosAtributos.setHorizontalAlignment(SwingConstants.CENTER);
		lblCargaLosAtributos.setBounds(145, 11, 324, 46);
		contenedor.getContentPane().add(lblCargaLosAtributos);

		JButton btnAtras = new JButton("Atr\u00E1s");
		contenedor.getContentPane().add(btnAtras);
		btnAtras.setPreferredSize(new Dimension(110, 40));
		btnAtras.setBounds(72, 325, 110, 29);
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				contenedor.setVisible(false);
				NombreRelacionWindow nomRelWin = NombreRelacionWindow
						.getInstance();
				nomRelWin.setVisible(true);
			}
		});

		JButton btnSiguiente = new JButton("Siguiente");
		contenedor.getContentPane().add(btnSiguiente);
		btnSiguiente.setPreferredSize(new Dimension(110, 40));
		btnSiguiente.setBounds(449, 324, 110, 29);
		btnSiguiente.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				Relacion.getInstance().setAtributos(atributosRelacion);
				System.out.println(Relacion.getInstance().getAtributos());
				setVisible(false);
				DependenciasFuncionalesWindow.getInstance().setVisible(true);
				DependenciasFuncionalesWindow.getInstance().clear();
				DependenciasFuncionalesWindow.getInstance().completarDatos();
			}
		});
		txtfieldCargarAtributos = new JTextField();
		txtfieldCargarAtributos.setSize(new Dimension(496, 35));
		txtfieldCargarAtributos.setPreferredSize(new Dimension(500, 35));
		txtfieldCargarAtributos.setMinimumSize(new Dimension(500, 20));
		txtfieldCargarAtributos.setHorizontalAlignment(SwingConstants.CENTER);
		txtfieldCargarAtributos.setColumns(35);
		txtfieldCargarAtributos.setBounds(82, 68, 350, 35);
		contenedor.getContentPane().add(txtfieldCargarAtributos);
		txtfieldCargarAtributos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				txtfieldCargarAtributos.setText("");
			}
		});
		txtfieldCargarAtributos.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				if (key == KeyEvent.VK_ENTER) {
					atributo = txtfieldCargarAtributos.getText();
					listaAtributos.add(atributo);
					atributosRelacion.add(atributo);
					txtfieldCargarAtributos.setText("");
				}
			}
		});

		JButton btnCargar = new JButton("Cargar");
		btnCargar.setBounds(431, 67, 95, 37);
		btnCargar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				atributo = txtfieldCargarAtributos.getText();
				listaAtributos.add(atributo);
				atributosRelacion.add(atributo);

			}
		});
		contenedor.getContentPane().add(btnCargar);

		listaAtributos = new List();
		listaAtributos.setBounds(82, 115, 477, 159);
		contenedor.getContentPane().add(listaAtributos);

		btnBorrar = new JButton("Borrar");
		btnBorrar.setBounds(72, 280, 497, 29);
		contenedor.getContentPane().add(btnBorrar);
		btnBorrar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int index = listaAtributos.getSelectedIndex();
				listaAtributos.remove(index);
				atributosRelacion.remove(index);
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
		atributosRelacion.clear();
		listaAtributos.removeAll();
		txtfieldCargarAtributos.setText("");
	}
}
