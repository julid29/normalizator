package ar.com.nn.view;

import java.awt.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import ar.com.nn.busisness.Clave;
import ar.com.nn.busisness.DepFuncional;
import ar.com.nn.busisness.Relacion;

public class DatosRelacionWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6070788625848135521L;
	
	private JLabel lblAtributos;
	private JLabel lblRelacionNombre;
	private JFrame contenedor;
	private Relacion r;
	private List listaSC;
	private List listaCC;
	private List listaDepFunc;
	private List listFmin;
	
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

		lblRelacionNombre = new JLabel("");
		lblRelacionNombre.setBounds(177, 10, 410, 23);
		contenedor.getContentPane().add(lblRelacionNombre);

		lblAtributos = new JLabel("");
		lblAtributos.setBounds(34, 45, 587, 23);
		contenedor.getContentPane().add(lblAtributos);

		JButton btnAtras = new JButton("Atr\u00E1s");
		btnAtras.setBounds(34, 322, 117, 29);
		contenedor.getContentPane().add(btnAtras);
		btnAtras.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				DependenciasFuncionalesWindow.getInstance().setVisible(true);
				r.removeCalculos();
				clear();
				setVisible(false);
			}
		});

		JButton btnSiguiente = new JButton("Siguiente");
		btnSiguiente.setBounds(504, 322, 117, 29);
		contenedor.getContentPane().add(btnSiguiente);
		btnSiguiente.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				FormasNormalesWindow.getInstance().setVisible(true);
				FormasNormalesWindow.getInstance().clear();
				FormasNormalesWindow.getInstance().completarDatos();
				setVisible(false);
				
			}
		});

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
		listaDepFunc.setBounds(228, 89, 175, 208);
		contenedor.getContentPane().add(listaDepFunc);

		listFmin = new List();
		listFmin.setBounds(446, 89, 175, 208);
		contenedor.getContentPane().add(listFmin);

		JLabel lblFminTitulo = new JLabel("Fmin");
		lblFminTitulo.setBounds(446, 69, 61, 16);
		contenedor.getContentPane().add(lblFminTitulo);
		
		JLabel lblDependenciasFuncionales = new JLabel("Dependencias Funcionales");
		lblDependenciasFuncionales.setBounds(228, 69, 181, 16);
		contenedor.getContentPane().add(lblDependenciasFuncionales);
		}

	public void completarDatos() {
		
		r.calcularClaves();
		lblRelacionNombre.setText("Relaci—n " + r.getNombre() + " est‡ en " + r.getFormaNormal());
		lblAtributos.setText("R:" + r.getAtributos());
		
//		Cargo SC
		ArrayList<Clave> sc = r.getSuperClaves();
		for (Clave c : sc){
			listaSC.add(c.toString());
		}
		
//		Cargo CC
		ArrayList<Clave> cc = r.getClavesCandidatas();
		for (Clave c : cc){
			listaCC.add(c.toString());
		}
		
//		Cargo Fmin
		ArrayList<DepFuncional> fmin = r.getfMin();
		ArrayList<String> de = new ArrayList<String>(), doo = new ArrayList<String>();
		for (DepFuncional df : fmin){
			de = df.getDeterminantes();
			doo = df.getDeterminados();
			listFmin.add(de.toString() + "->" + doo.toString());
		}
		
//		Cargo Dep Funcionales
		ArrayList<DepFuncional>dfs = r.getDepFuncionales();
		for (DepFuncional df : dfs){
			de = df.getDeterminantes();
			doo = df.getDeterminados();
			listaDepFunc.add(de.toString() + "->" + doo.toString());
		}
		
		
	}
	
	public void setVisible(boolean b) {
		if (b == true)
			contenedor.setVisible(true);
		else
			contenedor.setVisible(false);
	}
	
	public void clear(){
		listaCC.removeAll();
		listaDepFunc.removeAll();
		listaSC.removeAll();
		listFmin.removeAll();
		lblAtributos.setText("");
		lblRelacionNombre.setText("");
		
	}
}
