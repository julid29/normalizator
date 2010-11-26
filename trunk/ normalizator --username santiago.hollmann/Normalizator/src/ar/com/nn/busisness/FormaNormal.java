package ar.com.nn.busisness;

import java.util.ArrayList;

public class FormaNormal {
	private ArrayList<String> atributos = new ArrayList<String>();
	private ArrayList<DepFuncional> depFuncionales = new ArrayList<DepFuncional>();
	
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

	//Constructores
	public FormaNormal(ArrayList<String> atributos,
			ArrayList<DepFuncional> depFuncionales) {
		this.atributos = atributos;
		this.depFuncionales = depFuncionales;
	}
		
	public FormaNormal(ArrayList<String> atributos, DepFuncional depFuncionales) {
		this.atributos = atributos;
		this.depFuncionales.add(depFuncionales);
	}
	
	public FormaNormal(ArrayList<String> atributos) {
		this.atributos = atributos;
		//La lista de dependencias puede ser vacia = null.
		this.depFuncionales = new ArrayList<DepFuncional>();
	}
}
