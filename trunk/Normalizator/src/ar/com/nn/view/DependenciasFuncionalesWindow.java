package ar.com.nn.view;

import java.awt.Component;
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
	private Relacion r;

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
		r = Relacion.getInstance();
		arrayDF = new ArrayList<DepFuncional>();

		contenedor = new JFrame();
		contenedor.setTitle("Normalizator");
		contenedor.setResizable(false);
		contenedor.setBounds(100, 100, 650, 400);
		contenedor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contenedor.getContentPane().setLayout(null);


		JLabel lblCargueLasDependencias = new JLabel(
				"Cargue las dependencias funcionales de la relaci\u00F3n");
		contenedor.getContentPane().add(lblCargueLasDependencias);
		lblCargueLasDependencias.setHorizontalAlignment(SwingConstants.CENTER);
		lblCargueLasDependencias.setBounds(157, 6, 324, 46);

		listaDeterminantes = new List();
		contenedor.getContentPane().add(listaDeterminantes);
		listaDeterminantes.setMultipleMode(true);
		listaDeterminantes.setBounds(33, 58, 145, 243);

		listaDepFunc = new List();
		contenedor.getContentPane().add(listaDepFunc);
		listaDepFunc.setBounds(383, 58, 240, 214);
		
		JButton btnArmarDF = new JButton(">");
		contenedor.getContentPane().add(btnArmarDF);
		btnArmarDF.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnArmarDF.setBounds(343, 148, 34, 40);
		btnArmarDF.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
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
				arrayDF.add(df);
				listaDepFunc.add(d.toString() + "->" + doo.toString());
			}
		});

		listaDeterminados = new List();
		listaDeterminados.setMultipleMode(true);
		listaDeterminados.setBounds(192, 58, 145, 243);
		contenedor.getContentPane().add(listaDeterminados);

		JButton btnAtras = new JButton("Atr\u00E1s");
		contenedor.getContentPane().add(btnAtras);
		btnAtras.setPreferredSize(new Dimension(110, 40));
		btnAtras.setBounds(33, 324, 110, 29);

		JButton btnBorrar = new JButton("Borrar");
		btnBorrar.setBounds(383, 278, 240, 29);
		btnBorrar.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				int index = listaDepFunc.getSelectedIndex();
				arrayDF.remove(index);
				listaDepFunc.remove(index);
			}
		});
		
		contenedor.getContentPane().add(btnBorrar);
		btnAtras.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				contenedor.setVisible(false);
				CargaAtributosWindow cargaAt = CargaAtributosWindow
						.getInstance();
				cargaAt.setVisible(true);
			}
		});

		JButton btnSiguiente = new JButton("Siguiente");
		contenedor.getContentPane().add(btnSiguiente);
		btnSiguiente.setPreferredSize(new Dimension(110, 40));
		btnSiguiente.setBounds(513, 324, 110, 29);
		btnSiguiente.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				r.setDepFuncionales(arrayDF);
				DatosRelacionWindow.getInstance().clear();
				DatosRelacionWindow.getInstance().setVisible(true);
				DatosRelacionWindow.getInstance().completarDatos();
				setVisible(false);
			}
		});

	}
	
	public void completarDatos(){
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
	
	public void clear(){
		arrayDF.clear();
		listaDepFunc.removeAll();
		listaDeterminados.removeAll();
		listaDeterminantes.removeAll();
	}
	public void clearDD(){
		listaDeterminados.removeAll();
		listaDeterminantes.removeAll();
	}
	
}
