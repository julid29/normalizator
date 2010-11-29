package ar.com.nn.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

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
			guardarArchivos.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

			int resultado = guardarArchivos.showSaveDialog(this);
			if (resultado != guardarArchivos.CANCEL_OPTION) {
				archivo = new File(guardarArchivos.getCurrentDirectory(),
						r.getNombre() + ".txt");
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
				int i = 1;
				String depfuncoinales = "";
				for (FormaNormal forma : r.getFormaNormal3()) {
					ArrayList<DepFuncional> df = forma.getDepFuncionales();
					for (DepFuncional d : df) {
						depfuncoinales += d.getDeterminantes().toString()
								+ "->" + d.getDeterminados() + " ";
					}
					ps.println("R" + i + ": " + forma.getAtributos().toString()
							+ " DF: " + depfuncoinales);
					i++;
				}
				i = 1;
				depfuncoinales = "";
				for (FormaNormal forma : r.getFormaNormalBC()) {
					ArrayList<DepFuncional> df = forma.getDepFuncionales();
					for (DepFuncional d : df) {
						depfuncoinales += d.getDeterminantes().toString()
								+ "->" + d.getDeterminados() + " ";
					}
					ps.println("R" + i + ": " + forma.getAtributos().toString()
							+ " DF: " + depfuncoinales);
					i++;
				}
				ps.close();
			}

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}

	}

	public void imprimirArchivo() {
		
	}
}
