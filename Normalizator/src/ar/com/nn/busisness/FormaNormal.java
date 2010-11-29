package ar.com.nn.busisness;

import java.util.ArrayList;

public class FormaNormal {
	private ArrayList<String> atributos;
	private ArrayList<DepFuncional> depFuncionales;
	
	//Marca utilizada para eliminar la relacion.
	private boolean marca = false;
	
	//Getters & Setters
	public ArrayList<String> getAtributos() {
		return atributos;
	}

	public void setAtributos(ArrayList<String> atributos) {
		this.atributos = atributos;
	}

	public ArrayList<DepFuncional> getDepFuncionales() {
		return depFuncionales;
	}

	public void setDepFuncionales(ArrayList<DepFuncional> depFuncionales) {
		this.depFuncionales = depFuncionales;
	}

	public boolean getMarca() {
		return marca;
	}

	public void setMarca(boolean marca) {
		this.marca = marca;
	}

	public ArrayList<String> getDeterminantes() {
		return this.depFuncionales.get(0).getDeterminantes();
	}
	
	public ArrayList<String> getDeterminados() {
		ArrayList<String> resultado = new ArrayList<String>();
		for(DepFuncional depFun : this.depFuncionales){
			resultado.addAll(depFun.getDeterminados());
		}
		return resultado;
	}
	
	//Constructores
	public FormaNormal(ArrayList<String> atributos,
			ArrayList<DepFuncional> depFuncionales) {
		atributos = new ArrayList<String>();
		depFuncionales = new ArrayList<DepFuncional>();
		
		this.atributos = atributos;
		this.depFuncionales = depFuncionales;
	}
		
	public FormaNormal(ArrayList<String> atributos, DepFuncional depFuncionales) {
		this.atributos = new ArrayList<String>();
		this.depFuncionales = new ArrayList<DepFuncional>();
		
		this.atributos = atributos;
		this.depFuncionales.add(depFuncionales);
	}
	
	public FormaNormal(ArrayList<String> atributos) {
		this.atributos = atributos;
		//La lista de dependencias puede ser vacia = null.
		this.depFuncionales = new ArrayList<DepFuncional>();
	}

	public FormaNormal() {
		atributos = new ArrayList<String>();
		depFuncionales = new ArrayList<DepFuncional>();
	}

	public void agregarAtributos(ArrayList<String> atributos) {
		this.atributos.addAll(atributos);		
	}

	public void agregarDepFuncionales(ArrayList<DepFuncional> depFuncionales) {
		this.depFuncionales.addAll(depFuncionales);		
	}
}
