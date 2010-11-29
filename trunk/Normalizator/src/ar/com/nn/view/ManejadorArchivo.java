package ar.com.nn.view;

import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ar.com.nn.busisness.Clave;
import ar.com.nn.busisness.DepFuncional;
import ar.com.nn.busisness.FormaNormal;
import ar.com.nn.busisness.Relacion;

public class ManejadorArchivo extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4720034172438192267L;
	private File archivo;

	public ManejadorArchivo() {
	}

	public void guardarArchivo(Relacion r) {
		try {
			JFileChooser guardarArchivos = new JFileChooser();
			guardarArchivos
					.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

			int resultado = guardarArchivos.showSaveDialog(this);
			if (resultado != guardarArchivos.CANCEL_OPTION) {
				String nombre = guardarArchivos.getSelectedFile().getName();
				if (nombre == null || nombre == "")
					nombre = r.getNombre();
				archivo = new File(guardarArchivos.getCurrentDirectory(),
						nombre + ".txt");
				if (archivo.exists()) {
					if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(
							this, "El fichero existe,deseas reemplazarlo?",
							"Titulo", JOptionPane.YES_NO_OPTION)) {
						guardar(r);
					}
				}
				else
					guardar(r);
			}				
		}catch (Exception e) {}
	}

	private void guardar(Relacion r) throws FileNotFoundException{
		// Enviar a un archivo de texto.
		FileOutputStream os = new FileOutputStream(archivo);
		PrintStream ps = new PrintStream(os);

		ps.println("Nombre: " + r.getNombre());
		ps.println("Atributos:");
		for (String atributo : r.getAtributos()) {
			ps.println(atributo);
		}
		ps.println("Dependencias Funcionales:");
		for (DepFuncional depFun : r.getDepFuncionales()) {
			ps.println(depFun.toString());
		}
		ps.println("Fmin:");
		for (DepFuncional depFun : r.getfMin()) {
			ps.println(depFun.toString());
		}
		ps.println("Super Claves:");
		for (Clave c : r.getSuperClaves()) {
			ps.println(c.toString());
		}
		ps.println("Claves Candidatas:");
		for (Clave c : r.getClavesCandidatas()) {
			ps.println(c.toString());
		}
		
		ps.println("Formas Normales de " + r.getNombre());
		ps.println("3ra Forma Normal:");
		ArrayList<FormaNormal>fn = r.getFormaNormal3();
		int i = 1;
		String depfuncionales = "";
		for (FormaNormal forma : fn) {
			ArrayList<DepFuncional> df = forma.getDepFuncionales();
			for (DepFuncional d : df) {
				depfuncionales += (d.toString()+" ");
			}
			ps.println("R" + i + ": " + forma.getAtributos().toString() + " DF: "
					+ depfuncionales);
			depfuncionales = "";
			i++;
		}
		ps.println("Forma Normal de Boyce Codd:");
		ArrayList<FormaNormal> fnbc = r.getFormaNormalBC();
		i = 1;
		depfuncionales = "";
		for (FormaNormal rbc : fnbc) {
			ArrayList<DepFuncional> df = rbc.getDepFuncionales();
			for (DepFuncional d : df) {
				depfuncionales += (d.toString()+" ");
			}
			ps.println("R" + i + ": " + rbc.getAtributos().toString()
					+ " DF: " + depfuncionales);
			depfuncionales = "";
			i++;
		}
		
		
		ps.close();
	}

	public void imprimirArchivo(Relacion r) throws IOException {
		if (archivo == null)
			guardarArchivo(r);
		PrinterJob printJob = PrinterJob.getPrinterJob();
		printJob.setPrintable((Printable) archivo);

		PageFormat pf = printJob.pageDialog(printJob.defaultPage());

		if (printJob.printDialog()) {
			try {
				printJob.print();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}
