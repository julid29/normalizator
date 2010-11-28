package ar.com.nn.busisness;

import java.util.ArrayList;

public class Clave {
	private ArrayList<String> atributos;

	public Clave(ArrayList<String> atributos) {
		this.atributos = atributos;
	}
	
	public Clave(String atributo) {
		this.atributos = new ArrayList<String>();
		this.atributos.add(atributo);
	}

	public Clave() {
		this.atributos = new ArrayList<String>();
	}

	public ArrayList<String> getAtributos() {
		return atributos;
	}

	public void setAtributos(ArrayList<String> atributos) {
		this.atributos = atributos;
	}
	
	public void agregarAtributo(String atributo){
		this.atributos.add(atributo);
	}
	
	
}
