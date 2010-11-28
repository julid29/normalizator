package ar.com.nn.busisness;

import java.util.ArrayList;


public class Admin {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Bienvenido al aplicativo BD");
		
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
		
		r.calcular3FormaNormal();
		System.out.println("3ra forma normal");
		System.out.println("Atributos:");
		for (FormaNormal fn3 : r.getFormaNormal3()) {
			for(String a : fn3.getAtributos()){
					System.out.println(a);
			}
		}
	}

}
