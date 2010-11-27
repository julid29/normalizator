package ar.com.nn.view;

import java.awt.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import ar.com.nn.busisness.Relacion;

public class FormasNormalesWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2855932006246049436L;

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
		
		JButton btnSiguiente = new JButton("Siguiente");
		btnSiguiente.setBounds(502, 330, 117, 29);
		contenedor.getContentPane().add(btnSiguiente);
		
		JLabel lblTitulo = new JLabel("Formas Normales de " + r.getNombre());
		lblTitulo.setBounds(145, 17, 352, 21);
		contenedor.getContentPane().add(lblTitulo);
		
		List list = new List();
		list.setBounds(32, 77, 240, 239);
		contenedor.getContentPane().add(list);
		
		List list_1 = new List();
		list_1.setBounds(379, 77, 240, 239);
		contenedor.getContentPane().add(list_1);
		
		JLabel lbl3FormaNormal = new JLabel("3ra Forma Normal");
		lbl3FormaNormal.setBounds(32, 55, 117, 16);
		contenedor.getContentPane().add(lbl3FormaNormal);
		
		JLabel lblFNBC = new JLabel("Forma Normal de Boyce-Codd");
		lblFNBC.setBounds(379, 55, 195, 16);
		contenedor.getContentPane().add(lblFNBC);
		
		JButton btnImprimir = new JButton("Imprimir");
		btnImprimir.setBounds(185, 330, 117, 29);
		contenedor.getContentPane().add(btnImprimir);
		btnImprimir.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
//				ESTO TENES QUE TOCAR PARA IMPRIMIR :P
			}
		});
		
		JButton btnGuardar = new JButton("guardar");
		btnGuardar.setBounds(342, 330, 117, 29);
		contenedor.getContentPane().add(btnGuardar);
		initialize();
		contenedor.setVisible(true);
		btnGuardar.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
//				ESTO TENES QUE TOCAR PARA GUARDAR :P
			}
		});
	}
	
	public void setVisible(boolean b) {
		if (b == true)
			contenedor.setVisible(true);
		else
			contenedor.setVisible(false);
	}
}
