package ar.com.nn.view;

import java.awt.Dimension;
import java.awt.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import ar.com.nn.busisness.DepFuncional;
import ar.com.nn.busisness.Relacion;

public class DependenciasFuncionalesWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1705121985862663041L;

	private JFrame contenedor;

	private String[] determinante;
	private String[] determinado;
	private List listaDeterminantes;
	private List listaDeterminados;
	private ArrayList<DepFuncional> arrayDF;
	private List listaDepFunc;
	
	private static DependenciasFuncionalesWindow INSTANCE = null;

	// creador sincronizado para protegerse de posibles problemas multi-hilo
	// otra prueba para evitar instanciaci—n mœltiple
	private synchronized static void createInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DependenciasFuncionalesWindow();
		}
	}

	public static DependenciasFuncionalesWindow getInstance() {
		if (INSTANCE == null)
			createInstance();
		return INSTANCE;
	}

	/**
	 * Create the application.
	 */
	public DependenciasFuncionalesWindow() {
		initialize();
		contenedor.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public void initialize() {
		Relacion r = Relacion.getInstance();
		arrayDF = new ArrayList<DepFuncional>();
		
		contenedor = new JFrame();
		contenedor.setTitle("Normalizator");
		contenedor.setResizable(false);
		contenedor.setBounds(100, 100, 650, 400);
		contenedor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contenedor.getContentPane().setLayout(null);

		JButton btnSiguiente = new JButton("Siguiente");
		btnSiguiente.setPreferredSize(new Dimension(110, 40));
		btnSiguiente.setBounds(379, 321, 110, 40);
		contenedor.add(btnSiguiente);

		JButton btnAtras = new JButton("Atras");
		btnAtras.setPreferredSize(new Dimension(110, 40));
		btnAtras.setBounds(135, 321, 110, 40);
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				contenedor.setVisible(false);
				CargaAtributosWindow cargaAt = CargaAtributosWindow
						.getInstance();
				cargaAt.setVisible(true);
			}
		});
		contenedor.add(btnAtras);

		JLabel lblCargueLasDependencias = new JLabel(
				"Cargue las dependencias funcionales de la relaci\u00F3n");
		lblCargueLasDependencias.setHorizontalAlignment(SwingConstants.CENTER);
		lblCargueLasDependencias.setBounds(150, 18, 324, 46);
		contenedor.add(lblCargueLasDependencias);

		listaDeterminantes = new List();
		listaDeterminantes.setMultipleMode(true);
		listaDeterminantes.setBounds(26, 70, 166, 232);
		contenedor.add(listaDeterminantes);

		listaDeterminados = new List();
		listaDeterminados.setMultipleMode(true);
		listaDeterminados.setBounds(221, 70, 166, 232);
		contenedor.add(listaDeterminados);

		listaDepFunc = new List();
		listaDepFunc.setBounds(463, 70, 166, 232);
		contenedor.add(listaDepFunc);

		JButton btnArmarDF = new JButton(">>");
		btnArmarDF.setBounds(416, 156, 34, 40);
		btnArmarDF.addMouseListener(new MouseAdapter(){

			public void mouseClicked(MouseEvent e){
				determinante = listaDeterminantes.getSelectedItems();
				determinado = listaDeterminados.getSelectedItems();
				ArrayList<String> d = new ArrayList<String>();
				for (String element : determinante) {
					d.add(element);
				}
				ArrayList<String> doo = new ArrayList<String>();
				for (String element : determinado) {
					doo.add(element);
				}
				DepFuncional df = new DepFuncional(d, doo);
				System.out.println(df.toString());
				arrayDF.add(df);
				listaDepFunc.add(d.toString() + "->"+ doo.toString());
			}
		});
		contenedor.add(btnArmarDF);

		for (String element : Relacion.getInstance().getAtributos()) {
			listaDeterminantes.add(element);
			listaDeterminados.add(element);
			
		}
	}
	
	public void setVisible(boolean b) {
		if (b == true)
			contenedor.setVisible(true);
		else
			contenedor.setVisible(false);
	}
}
