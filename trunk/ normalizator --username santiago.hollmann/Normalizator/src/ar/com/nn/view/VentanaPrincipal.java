package ar.com.nn.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class VentanaPrincipal {

	private static final int ANCHO_INTRO = 600;
	private static final int ALTO_INTRO = 40;
	private JFrame frmNormalizator;

	private static VentanaPrincipal INSTANCE = null;

	// creador sincronizado para protegerse de posibles problemas multi-hilo
	// otra prueba para evitar instanciaci—n mœltiple
	private synchronized static void createInstance() {
		if (INSTANCE == null) {
			INSTANCE = new VentanaPrincipal();
		}
	}

	public static VentanaPrincipal getInstance() {
		if (INSTANCE == null)
			createInstance();
		return INSTANCE;
	}
	
	/**
	 * Create the application.
	 */
	public VentanaPrincipal() {
		initialize();
		frmNormalizator.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmNormalizator = new JFrame();
		frmNormalizator.setFont(new Font("Arial", Font.PLAIN, 12));
		frmNormalizator.setResizable(false);
		frmNormalizator.setTitle("Normalizator");
		frmNormalizator.setBounds(100, 100, 650, 400);
		frmNormalizator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel txtTitulo = new JLabel("Normalizator");
		txtTitulo.setBounds(228, 50, 140, 30);
		txtTitulo.setPreferredSize(new Dimension(130, 80));
		txtTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		txtTitulo.setFont(new Font("Arial", txtTitulo.getFont().getStyle() | Font.BOLD, 21));
		
		JLabel txtIntro = new JLabel("Normaliz\u00E1 y calcul\u00E1 las distintas claves de tus relaciones");
		txtIntro.setLocation(49, 135);
		txtIntro.setPreferredSize(new Dimension(600, 170));
		txtIntro.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		txtIntro.setHorizontalAlignment(SwingConstants.CENTER);
		txtIntro.setFont(new Font("Arial", txtIntro.getFont().getStyle(), 15));
		txtIntro.setSize(530, 66);
		
		JButton btnComenzar = new JButton("COMENZAR");
		btnComenzar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frmNormalizator.setVisible(false);
				NombreRelacionWindow nombreRelWin = NombreRelacionWindow.getInstance();
				nombreRelWin.setVisible(true);
			}
		});
		btnComenzar.setBounds(243, 268, 125, 52);
		btnComenzar.setFont(new Font("Arial", btnComenzar.getFont().getStyle(), 14));
		btnComenzar.setPreferredSize(new Dimension(110, 40));
		
		JPanel panelAbsoluto = new JPanel();
		frmNormalizator.getContentPane().add(panelAbsoluto, BorderLayout.CENTER);
		panelAbsoluto.setLayout(null);
		panelAbsoluto.add(txtTitulo);
		panelAbsoluto.add(txtIntro);
		panelAbsoluto.add(btnComenzar);
	}

	public void setVisible(boolean b) {
		if (b == true)
			frmNormalizator.setVisible(true);
		else
			frmNormalizator.setVisible(false);
	}

}
