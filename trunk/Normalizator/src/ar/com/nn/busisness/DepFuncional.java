package ar.com.nn.busisness;

import java.util.ArrayList;

public class DepFuncional {

	private ArrayList<String> determinantes;
	private ArrayList<String> determinados;
	private Boolean visto;
	private Boolean marca;

	// Getters & setters de la clase DepFuncional
	public ArrayList<String> getDeterminantes() {
		return determinantes;
	}

	public void setDeterminantes(ArrayList<String> determinantes) {
		this.determinantes = determinantes;
	}

	public void setDeterminantes(String determinante) {
		this.determinantes.add(determinante);
	}

	public ArrayList<String> getDeterminados() {
		return determinados;
	}

	public void setDeterminados(ArrayList<String> determinados) {
		this.determinados = determinados;
	}

	public void setDeterminados(String determinado) {
		this.determinados.add(determinado);
	}

	public Boolean getVisto() {
		return visto;
	}

	public void setVisto(Boolean visto) {
		this.visto = visto;
	}

	public Boolean getMarca() {
		return marca;
	}

	public void setMarca(Boolean marca) {
		this.marca = marca;
	}

	// Constructores de la clase DepFuncional.
	public DepFuncional(ArrayList<String> determinantes,
			ArrayList<String> determinados) {
		this.determinantes = determinantes;
		this.determinados = determinados;
		visto = Boolean.FALSE;
		marca = Boolean.FALSE;
	}

	public DepFuncional(ArrayList<String> determinantes, String determinado) {
		this.determinantes = determinantes;

		this.determinados = new ArrayList<String>();
		this.determinados.add(determinado);
		visto = Boolean.FALSE;
		marca = Boolean.FALSE;
	}

	public DepFuncional() {
		determinados = new ArrayList<String>();
		determinantes = new ArrayList<String>();
		visto = Boolean.FALSE;
		marca = Boolean.FALSE;
	}

	// TODO agregar los metodos faltantes.
	/**
	 * Determina si una lista de atributos los contiene en TODOS sus
	 * DETERMINANTES
	 */
	public boolean loTenesEnDeterminantes(ArrayList<String> atributos) {
		for (String a : determinantes){
			if (!atributos.contains(a))
				return false;
		}
		return true;
	}

	public boolean determinantesParcial(ArrayList<String> atributos){
		for (String a : atributos){
			if (determinantes.contains(a))
				return true;
		}
		return false;
	}
	
	public boolean loTenesEnDeterminados(ArrayList<String> atributos) {
		for (String a : determinados){
			if (!atributos.contains(a))
				return false;
		}
		return true;
	}
	
	public boolean determinadosParcial(ArrayList<String> atributos){
		for (String a : atributos){
			if (determinados.contains(a))
				return true;
		}
		return false;
	}
	
	public String toString(){
		String depFun="";
		for (String dtte : determinantes){
			depFun += dtte; 
		}
		depFun += "->";
		for (String ddo : determinados){
			depFun += ddo; 
		}
		return depFun;
	}
	
}// Fin de la clase
