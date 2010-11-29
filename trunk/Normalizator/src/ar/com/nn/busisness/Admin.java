package ar.com.nn.busisness;

import ar.com.nn.view.CargaAtributosWindow;
import ar.com.nn.view.DatosRelacionWindow;
import ar.com.nn.view.DependenciasFuncionalesWindow;
import ar.com.nn.view.FormasNormalesWindow;
import ar.com.nn.view.NombreRelacionWindow;
import ar.com.nn.view.VentanaPrincipal;


public class Admin {

	public static void clearWindows(){
		Relacion.getInstance().clear();
		CargaAtributosWindow.getInstance().clear();
		DatosRelacionWindow.getInstance().clear();
		DependenciasFuncionalesWindow.getInstance().clear();
		FormasNormalesWindow.getInstance().clear();
		NombreRelacionWindow.getInstance().clear();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args){
		VentanaPrincipal.getInstance();
	}
}
