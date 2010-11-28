package ar.com.nn.view;

import java.awt.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import ar.com.nn.busisness.Admin;
import ar.com.nn.busisness.DepFuncional;
import ar.com.nn.busisness.FormaNormal;
import ar.com.nn.busisness.Relacion;

public class FormasNormalesWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2855932006246049436L;

	private List lista3FN;
	private List listaFNBC;
	private Relacion r;

	private JFrame contenedor;
	private static FormasNormalesWindow INSTANCE;

	private synchronized static void createInstance() {
		if (INSTANCE == null) {
			INSTANCE = new FormasNormalesWindow();
		}
	}

	public static FormasNormalesWindow getInstance() {
		if (INSTANCE == null)
			createInstance();
		return INSTANCE;
	}

	public FormasNormalesWindow() {
		initialize();
		setVisible(true);
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
		contenedor.getContentPane().setLayout(null);
		
		JButton btnAtrs = new JButton("Atr\u00E1s");
		btnAtrs.setBounds(32, 330, 117, 29);
		contenedor.getContentPane().add(btnAtrs);
		btnAtrs.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				DatosRelacionWindow.getInstance().setVisible(true);
				setVisible(false);
			}
		});
		
		JButton btnSiguiente = new JButton("Comenzar");
		btnSiguiente.setBounds(502, 330, 117, 29);
		contenedor.getContentPane().add(btnSiguiente);
		btnSiguiente.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
//				NombreRelacionWindow v = NombreRelacionWindow.getInstance();
//				v.clear();
//				v.setVisible(true);
//				setVisible(false);
//				Admin.clearWindows();
				setVisible(false);
				String[] args = null;
				Admin.main(args);
			}
		});
		
		JLabel lblTitulo = new JLabel("Formas Normales de " + r.getNombre());
		lblTitulo.setBounds(223, 17, 352, 21);
		contenedor.getContentPane().add(lblTitulo);
		
		lista3FN = new List();
		lista3FN.setBounds(32, 77, 587, 104);
		contenedor.getContentPane().add(lista3FN);
		
		listaFNBC = new List();
		listaFNBC.setBounds(32, 209, 587, 104);
		contenedor.getContentPane().add(listaFNBC);
		
		JLabel lbl3FormaNormal = new JLabel("3ra Forma Normal");
		lbl3FormaNormal.setBounds(32, 55, 117, 16);
		contenedor.getContentPane().add(lbl3FormaNormal);
		
		JLabel lblFNBC = new JLabel("Forma Normal de Boyce-Codd");
		lblFNBC.setBounds(32, 187, 195, 16);
		contenedor.getContentPane().add(lblFNBC);
		
		JButton btnImprimir = new JButton("Imprimir");
		btnImprimir.setBounds(185, 330, 117, 29);
		contenedor.getContentPane().add(btnImprimir);
		btnImprimir.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
//				ESTO TENES QUE TOCAR PARA IMPRIMIR :P
			}
		});
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(342, 330, 117, 29);
		contenedor.getContentPane().add(btnGuardar);
		btnGuardar.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
//				ESTO TENES QUE TOCAR PARA GUARDAR :P
			}
		});
		
		completarDatos();
	}

	private void completarDatos() {
		ArrayList<FormaNormal> fn = r.getFormaNormal3();
		int i = 1;
		String depfuncoinales = "";
		for (FormaNormal r : fn) {
			ArrayList<DepFuncional> df = r.getDepFuncionales();
			for (DepFuncional d : df) {
				depfuncoinales += d.getDeterminantes().toString() + "->"
						+ d.getDeterminados() + " ";
			}
			lista3FN.add("R" + i + ": " + r.getAtributos().toString() + " DF: "
					+ depfuncoinales);
			i++;
		}

		ArrayList<FormaNormal> fnbc = r.getFormaNormalBC();
		i = 1;
		depfuncoinales = "";
		for (FormaNormal r : fnbc) {
			ArrayList<DepFuncional> df = r.getDepFuncionales();
			for (DepFuncional d : df) {
				depfuncoinales += d.getDeterminantes().toString() + "->"
						+ d.getDeterminados() + " ";
			}
			listaFNBC.add("R" + i + ": " + r.getAtributos().toString()
					+ " DF: " + depfuncoinales);
			i++;
		}

	}

	public void setVisible(boolean b) {
		if (b == true)
			contenedor.setVisible(true);
		else
			contenedor.setVisible(false);
	}

	public void clear() {
		lista3FN.removeAll();
		listaFNBC.removeAll();
	}
}
