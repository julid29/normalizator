package ar.com.nn.view;

import java.awt.Label;
import java.awt.List;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import ar.com.nn.busisness.Relacion;

public class DatosRelacionWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6070788625848135521L;

	private JFrame contenedor;
	private Relacion r;
	private List listaSC;
	private List listaCC;
	private List listaDepFunc;
	private Label lblFmin;
	
	private static DatosRelacionWindow INSTANCE;

	private synchronized static void createInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DatosRelacionWindow();
		}
	}

	public static DatosRelacionWindow getInstance() {
		if (INSTANCE == null)
			createInstance();
		return INSTANCE;
	}

	public DatosRelacionWindow() {
		initialize();
		contenedor.setVisible(true);
	}

	public void initialize() {
		r = Relacion.getInstance();

		contenedor = new JFrame();
		contenedor.setTitle("Normalizator");
		contenedor.setResizable(false);
		contenedor.setBounds(100, 100, 650, 400);
		contenedor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contenedor.getContentPane().setLayout(null);
		contenedor.getContentPane().setLayout(null);

		JLabel lblRelacionNombre = new JLabel("Relaci—n " + r.getNombre());
		lblRelacionNombre.setBounds(132, 16, 396, 23);
		contenedor.getContentPane().add(lblRelacionNombre);

		JLabel lblAtributos = new JLabel("R:" + r.getAtributos());
		lblAtributos.setBounds(34, 45, 587, 23);
		contenedor.getContentPane().add(lblAtributos);

		JButton btnAtras = new JButton("Atras");
		btnAtras.setBounds(34, 322, 117, 29);
		contenedor.getContentPane().add(btnAtras);

		JButton btnSiguiente = new JButton("Siguiente");
		btnSiguiente.setBounds(504, 322, 117, 29);
		contenedor.getContentPane().add(btnSiguiente);

		listaSC = new List();
		listaSC.setBounds(34, 89, 175, 91);
		contenedor.getContentPane().add(listaSC);

		JLabel lblSuperClave = new JLabel("Super Claves");
		lblSuperClave.setBounds(34, 69, 91, 16);
		contenedor.getContentPane().add(lblSuperClave);

		JLabel lblClavesCandidatas = new JLabel("Claves Candidatas");
		lblClavesCandidatas.setBounds(34, 186, 117, 16);
		contenedor.getContentPane().add(lblClavesCandidatas);

		listaCC = new List();
		listaCC.setBounds(34, 208, 175, 89);
		contenedor.getContentPane().add(listaCC);

		listaDepFunc = new List();
		listaDepFunc.setBounds(254, 86, 175, 211);
		contenedor.getContentPane().add(listaDepFunc);

		lblFmin = new Label("");
		lblFmin.setBounds(481, 89, 140, 227);
		contenedor.getContentPane().add(lblFmin);

		JLabel lblFminTitulo = new JLabel("Fmin");
		lblFminTitulo.setBounds(481, 69, 61, 16);
		contenedor.getContentPane().add(lblFminTitulo);
		
		completarDatos();
	}

	private void completarDatos() {
		ArrayList<String> sc = r.getSuperClave();
		for (String s : sc){
			listaSC.add(s);
		}
		
	}

	public void setVisible(boolean b) {
		if (b == true)
			contenedor.setVisible(true);
		else
			contenedor.setVisible(false);
	}
}
