package ar.com.nn.busisness;

import java.util.ArrayList;

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
	public static void main(String[] args) {
		System.out.println("Bienvenido al aplicativo BD");
		VentanaPrincipal.getInstance();
		ArrayList<String> atributos = new ArrayList<String>();
		atributos.add("a");
		atributos.add("b");
		atributos.add("c");

		
		ArrayList<DepFuncional> depFuncionales = new ArrayList<DepFuncional>();
		DepFuncional depFn = new DepFuncional();
		depFn.setDeterminantes(atributos.get(0));
		depFn.setDeterminados(atributos.get(1));
		depFn.setDeterminados(atributos.get(2));
		depFuncionales.add(depFn);
		
		Relacion r = new Relacion(1, "Nahuel",atributos, depFuncionales);
		
		System.out.println(r.getNombre());
		System.out.println("Atributos:");
		for (String a : r.getAtributos()) {
			System.out.println(a);
		}
		r.obtenerFmin();
		System.out.println("Fmin:");
		for (DepFuncional depFuncional : r.getfMin()) {
			for (String s : depFuncional.getDeterminantes()) {
				System.out.println(s);
			}
			for (String s : depFuncional.getDeterminados()) {
				System.out.println(s);
			}
		}
		r.calcularClaves();
		System.out.println(r.getFormaNormal());
		r.calcular3FormaNormal();
		System.out.println("3ra forma normal");
		for (FormaNormal fn3 : r.getFormaNormal3()) {
			System.out.println("Atributos:");
			for(String a : fn3.getAtributos()){
					System.out.println(a);
			}
		}
	}

}
