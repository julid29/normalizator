package ar.com.nn.busisness;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Relacion {

	private String nombre;
	private ArrayList<String> atributos;
	private ArrayList<Clave> clavesCandidatas = new ArrayList<Clave>();
	private ArrayList<Clave> superClaves = new ArrayList<Clave>();
	private ArrayList<DepFuncional> depFuncionales;
	private ArrayList<DepFuncional> fMin = new ArrayList<DepFuncional>();
	private String formaNormal;
	private ArrayList<FormaNormal> formaNormal3 = new ArrayList<FormaNormal>();
	private ArrayList<FormaNormal> formaNormalBC = new ArrayList<FormaNormal>();

	private static Relacion INSTANCE = null;

	// Private constructor suppresses
	private Relacion() {
	}

	// creador sincronizado para protegerse de posibles problemas multi-hilo
	// otra prueba para evitar instanciaci�n m�ltiple
	private synchronized static void createInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Relacion();
		}
	}

	public static Relacion getInstance() {
		if (INSTANCE == null)
			createInstance();
		return INSTANCE;
	}

	// Getters & setters de la clase Relacion.

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public ArrayList<String> getAtributos() {
		return atributos;
	}

	public void setAtributos(ArrayList<String> Strings) {
		this.atributos = Strings;
	}

	public ArrayList<Clave> getClavesCandidatas() {
		return clavesCandidatas;
	}

	public void setClavesCandidatas(ArrayList<Clave> clavesCandidatas) {
		this.clavesCandidatas = clavesCandidatas;
	}

	public ArrayList<Clave> getSuperClaves() {
		return superClaves;
	}

	public void setSuperClaves(ArrayList<Clave> superClaves) {
		this.superClaves = superClaves;
	}

	public ArrayList<DepFuncional> getDepFuncionales() {
		return depFuncionales;
	}

	public void setDepFuncionales(ArrayList<DepFuncional> depFuncionales) {
		this.depFuncionales = depFuncionales;
	}

	public ArrayList<DepFuncional> getfMin() {
		obtenerFmin();
		return fMin;
	}

	public void setfMin(ArrayList<DepFuncional> fMin) {
		this.fMin = fMin;
	}

	public String getFormaNormal() {
		if (formaNormal == null || formaNormal == "")
			formaNormal = this.verificarFormaNormal();
		return formaNormal;
	}

	public void setFormaNormal(String formaNormal) {
		this.formaNormal = formaNormal;
	}

	public ArrayList<FormaNormal> getFormaNormal3() {
		calcular3FormaNormal();
		return formaNormal3;
	}

	public void setFormaNormal3(ArrayList<FormaNormal> formaNormal3) {
		this.formaNormal3 = formaNormal3;
	}

	public ArrayList<FormaNormal> getFormaNormalBC() {
		calcularFNBC();
		return formaNormalBC;
	}

	public void setFormaNormalBC(ArrayList<FormaNormal> formaNormalBC) {
		this.formaNormalBC = formaNormalBC;
	}

	// Constructor de la clase Relacion.
	public Relacion(int idRelacion, String nombre, ArrayList<String> atributos,
			ArrayList<DepFuncional> depFuncionales) {
		this.nombre = nombre;
		this.atributos = atributos;
		this.depFuncionales = depFuncionales;
	}

	/**
	 * Metodo para obtener la clausura de uno mas atributos. Implementando el
	 * siguiente seudocodigo: Resultado = X Mientras (cambios en resultado)
	 * HACER Por cada dependencia funcional Y--> Z en F HACER Comienzo Si Y
	 * <Incluido> Resultado => Resultado = Resultado U Z Fin
	 * 
	 * @param atrs
	 *            : Los atributos a tratar.
	 * @param opcion
	 *            : Indica el caso de tratarce una clausura de las dependencias
	 *            originales (nro 0 -cero-) tratarce de una clausura en el Fmin
	 *            (nro 1).
	 */
	public ArrayList<String> calcularClausura(ArrayList<String> atrs, int opcion) {

		ArrayList<DepFuncional> dependenciasATratar = new ArrayList<DepFuncional>();
		switch (opcion) {
		case 0:
			dependenciasATratar = this.depFuncionales;
			break;
		case 1:
			dependenciasATratar = this.fMin;
			break;
		}

		ArrayList<String> arrayAuxiliar = new ArrayList<String>();
		arrayAuxiliar.addAll(atrs);

		boolean cambiosResultado = true;

		while (cambiosResultado) {
			cambiosResultado = false;
			for (DepFuncional depFun : dependenciasATratar) {
				/*
				 * Por cada dependencia funcional, verifica que la clausura
				 * obtenida hasta el momento, se encuentre en sus determinantes.
				 */
				if (!depFun.getMarca()) {
					if (depFun.determinantesParcial(arrayAuxiliar)) {
						// Adhiere todos los determinados de la dependecia
						// funcional.
						for (String determinado : depFun.getDeterminados()) {
							if (!(arrayAuxiliar.contains(determinado))) {
								arrayAuxiliar.add(determinado);
								cambiosResultado = true;
							}
						}
					}
				}
			}
		}
		return arrayAuxiliar;
	}

	/**
	 * Metodo para resolver el Fmin de la relacion
	 */
	public void obtenerFmin() {
		/*
		 * En primer lugar, simplifico atributos a derecha. por cada dependencia
		 * funcional, llamo al metodo correspondiente agrego en la lista del
		 * Fmin, las dependencias obtenidas como resultado.
		 */
		try {
			for (DepFuncional dependenciaFuncional : this.depFuncionales) {
				if (dependenciaFuncional.getDeterminados().size() > 1) {
					simplificarADerecha(
							dependenciaFuncional.getDeterminantes(),
							dependenciaFuncional.getDeterminados());
				} else
					fMin.add(dependenciaFuncional);
			}
		} catch (Exception e) {
			System.out.println("Excepcion: " + e.getMessage());
		}
		// **********************Fin del 1er
		// paso*******************************************************
		/*
		 * En segundo lugar, quito los atributos reduntantes a izquierda. Por
		 * cada dependencia funcional, calculo la clausura de sus determinantes
		 * y si obtengo sus determinantes, la dependencia en cuestion es
		 * REDUNDANTE.
		 */
		identificarRedundantesAIzq();
		// ********************Fin del 2do
		// paso*********************************************************
		/*
		 * Por utlimo quito las dependencias repetidas.
		 */
		int recorrer = 0;
		DepFuncional depATratar = new DepFuncional();
		while (recorrer < this.fMin.size()) {
			depATratar = this.fMin.get(recorrer);
			if (!depATratar.getMarca()) {
				depATratar.setMarca(true);
				for (DepFuncional dependencia : this.fMin) {
					if (!dependencia.getMarca()) {
						if (dependencia.loTenesEnDeterminantes(depATratar
								.getDeterminantes())
								&& dependencia.loTenesEnDeterminados(depATratar
										.getDeterminados()))
							dependencia.setMarca(true);
					}
					depATratar.setMarca(false);
				}
			}
			recorrer += 1;
		}
	}

	/**
	 * Determina si los determinantes de cada dependencia funcional del Fmin
	 * (paso 2) pueden alcanzar sus determinados sin la dependencia en cuestion.
	 * En caso de ser afirmativo, dicha dependencia es redundante.
	 */
	private void identificarRedundantesAIzq() {

		for (DepFuncional dependenciaATratar : this.fMin) {
			dependenciaATratar.setMarca(true);
			ArrayList<String> clausura = calcularClausura(dependenciaATratar
					.getDeterminantes(), 1);
			// Si NO TODOS los determinados estan en la clausura. Quito la
			// marca.
			if (!clausura.containsAll(dependenciaATratar.getDeterminados()))
				dependenciaATratar.setMarca(false);
		}

		// A las dependencias marcadas las elimino.
		Iterator<DepFuncional> iterador = this.fMin.iterator();
		while (iterador.hasNext()) {
			DepFuncional dF = iterador.next();
			if (dF.getMarca())
				iterador.remove();
		}

	}

	/**
	 * En base a una dependencia con mas de un determinado, este metodo incluir
	 * en el Fmin de la relacion tantas dependencias funcionales como
	 * determinantes tenga la dependencia funcional en cuestion.
	 */
	public void simplificarADerecha(ArrayList<String> determinantes,
			ArrayList<String> determinados) {
		for (String determinado : determinados) {
			DepFuncional depFuncional = new DepFuncional(determinantes,
					determinado);
			fMin.add(depFuncional);
		}
	}

	public String verificarFormaNormal() {
		int i = 0, aux = 1;
		String stringFormaNormal = "";

		if (atributos.size() == 2) {
			stringFormaNormal = "Forma Normal de Boyce Codd.";
			return stringFormaNormal;
		}
		for (DepFuncional depFun : this.depFuncionales) {
			// El o los determinantes, NO pertenecen a algun subconjunto de
			// alguna clave de R
			for (Clave cCandidata : this.clavesCandidatas) {
				if (!depFun.getDeterminantes().containsAll(
						cCandidata.getAtributos())) {
					aux = 2;
					break;
				} else
					aux = 1;
			}
			// El o los determinados, son un atributo primo.
			for (Clave cCandidata : this.clavesCandidatas) {
				if (cCandidata.getAtributos().containsAll(
						depFun.getDeterminados())) {
					aux = 3;
					break;
				} else
					aux = 1;
			}
			// El o los determinantes, son superClave.
			for (Clave cCandidata : this.superClaves) {
				if (cCandidata.getAtributos().containsAll(
						depFun.getDeterminantes())
						&& cCandidata.getAtributos().size() == depFun
								.getDeterminantes().size()) {
					aux = 4;
					break;
				} else
					aux = 1;
			}
			if (i == 0)
				i = aux;
			else {
				if (i > aux)
					i = aux;
			}
		}

		switch (i) {
		case 1:
			stringFormaNormal = "1ra Forma Normal.";
			break;
		case 2:
			stringFormaNormal = "2da FormaNormal.";
			break;
		case 3:
			stringFormaNormal = "3ra Forma Normal.";
			break;
		case 4:
			stringFormaNormal = "Forma Normal de Boyce Codd.";
			break;
		}

		return stringFormaNormal;

	}

	/**
	 * EL metodo formara las relaciones necesarias para hallar la Tercera Forma
	 * Normal.
	 */
	public void calcular3FormaNormal() {
//		// 1) Encontrar Fmin.
//		if (this.fMin == null)
//			obtenerFmin();
		/*
		 * 2) Crear esquemas de relacion Ri, cuyos atributos contengan las
		 * dependencias funcionales del esquema R
		 */
		for (DepFuncional depFun : this.depFuncionales) {
			// compongo una lista con los determiantes y los determinados.
			ArrayList<String> atrs = new ArrayList<String>();
			atrs.addAll(depFun.getDeterminados());
			atrs.addAll(depFun.getDeterminantes());
			// Creo la forma normal y se lo agrego a la 3raFN.
			FormaNormal formaNormal = new FormaNormal(atrs, depFun);
			formaNormal3.add(formaNormal);
		}
		/*
		 * 3) Si ningun subesquema contiene la clave candidata de R, crear un
		 * subesquema con los attributos necesarios y depFuncional vacia
		 */
		boolean crear;
		for (Clave clave : this.clavesCandidatas) {
			crear = true;
			for (FormaNormal forma : this.formaNormal3) {
				if (forma.getDeterminantes().containsAll(clave.getAtributos())
						&& forma.getDeterminantes().size() == clave
								.getAtributos().size()) {
					crear = false;
					break;
				}
			}
			if (crear) {
				FormaNormal formaNormal = new FormaNormal(clave.getAtributos());
				formaNormal.setMarca(true);
				formaNormal3.add(formaNormal);
			}
		}
		// Verificar uniones posibles.
		this.verificarUniones();
	}

	private void verificarUniones() {
		ArrayList<FormaNormal> eliminar = new ArrayList<FormaNormal>();
		Iterator<FormaNormal> iterador = this.formaNormal3.iterator();
		while (iterador.hasNext()) {
			FormaNormal formaATratar = iterador.next();
			if (eliminar == null || !eliminar.contains(formaATratar)) {
				for (FormaNormal forma : this.formaNormal3) {
					if (forma != formaATratar) {
						try {
							if (formaATratar.getDeterminantes().size() == forma
									.getDeterminantes().size()
									&& forma.getDeterminantes().containsAll(
											formaATratar.getDeterminantes())) {
								formaATratar.agregarAtributos(forma
										.getDeterminados());
								formaATratar.agregarDepFuncionales(forma
										.getDepFuncionales());
								eliminar.add(forma);
							}
						} catch (IndexOutOfBoundsException exception) {
						}
					}
				}
			}
			else
				iterador.remove();
		}
	}

	public void calcularFNBC() {
		ArrayList<DepFuncional> depPerdidas = new ArrayList<DepFuncional>();
		FormaNormal principal = new FormaNormal();
		principal.setAtributos(atributos);
		principal.setDepFuncionales(depFuncionales);
//		/*
//		 * 1) Calculo el Fmin.
//		 */
//		if (fMin == null)
//			obtenerFmin();
		/*
		 * 2)Mientras existan dependencias funcionales que violen la forma
		 * normal se proyecta R en R1 y R2
		 */
		Iterator<DepFuncional> iterador = principal.getDepFuncionales().iterator();
		while (iterador.hasNext()){
			DepFuncional dependencia = iterador.next();
			ArrayList<String>atributos = new ArrayList<String>();
			atributos.addAll(dependencia.getDeterminantes());
			atributos.addAll(dependencia.getDeterminados());
			FormaNormal forma = new FormaNormal(atributos,dependencia);
			formaNormalBC.add(forma);
		}
		
		
//		int i = 1;// Esta variable se usa para recorrer las dependencias
//		// funcionales dentro del foreach (omitiendo la primera).
//
//		for (DepFuncional depFun : principal.getDepFuncionales()) {
//			// compongo una lista con los determiantes y los determinados.
//			ArrayList<String> atrs = new ArrayList<String>();
//			// Creo la forma normal y se lo agrego a la lista.
//			if (!depFun.getMarca()) {
//				atrs.addAll(depFun.getDeterminados());
//				atrs.addAll(depFun.getDeterminantes());
//				// Creo la forma normal y se lo agrego a la FNBC.
//				FormaNormal formaNormal = new FormaNormal(atrs, depFun);
//				formaNormalBC.add(formaNormal);
//				int aux = i;
//				while (aux < principal.getDepFuncionales().size()) {
//					/*
//					 * Evaluo si los atributos del subesquema creado, lo
//					 * contiene alguna dependencia funcional tanto en sus
//					 * determinantes o sus determinados. En caso de ser
//					 * afirmativo, esta dependencia se perdera, entonces se
//					 * marca dicha dependencia y se la agrega a la lista de
//					 * dependencias perdidas.
//					 */
//					if (!principal.getDepFuncionales().get(i).getMarca()) {
//						if (principal.getDepFuncionales().get(i)
//								.loTenesEnDeterminados(
//										formaNormal.getAtributos())) {
//							principal.getDepFuncionales().get(i).setMarca(true);
//							depPerdidas.add(principal.getDepFuncionales()
//									.get(i));
//						} else if (principal.getDepFuncionales().get(i)
//								.loTenesEnDeterminados(
//										formaNormal.getAtributos())) {
//							principal.getDepFuncionales().get(i).setMarca(true);
//							depPerdidas.add(principal.getDepFuncionales()
//									.get(i));
//						}
//						aux++;
//					}
//				}
//				i++;
//				atrs.clear();
//			}
//		}
	}

	public void calcularClaves() {
		this.calcularSuperClaves();
		this.calcularClavesCandidatas();
	}

	private void calcularClavesCandidatas() {
		int i = 0, aux = 0;
		i = this.superClaves.get(0).getAtributos().size();
		for (Clave superClave : this.superClaves) {
			aux = superClave.getAtributos().size();
			if (aux < i)
				i = aux;
		}

		for (Clave superClave : this.superClaves) {
			aux = superClave.getAtributos().size();
			if (aux == i) {
				this.clavesCandidatas.add(superClave);
			}
		}
	}

	private void calcularSuperClaves() {
		int i = 1;
		while (i <= this.atributos.size()) {

			CombinatorialIterator<String> iterador = new CombinatorialIterator<String>(
					i, this.atributos);

			while (iterador.hasNext()) {
				Clave superClave = new Clave();
				Collection<String> aux = iterador.next();
				for (String s : aux) {
					superClave.agregarAtributo(s);
				}
				this.superClaves.add(superClave);
			}
			i++;
		}
		Iterator<Clave> clave = this.superClaves.iterator();
		while (clave.hasNext()) {
			Clave aux = clave.next();
			if (!(calcularClausura(aux.getAtributos(), 0).size() == this.atributos
					.size()))
				clave.remove();
		}
	}

	public void clear() {
		atributos = new ArrayList<String>();
		clavesCandidatas = new ArrayList<Clave>();
		depFuncionales = new ArrayList<DepFuncional>();
		fMin = new ArrayList<DepFuncional>();
		formaNormal = "";
		formaNormal3 = new ArrayList<FormaNormal>();
		formaNormalBC = new ArrayList<FormaNormal>();
		nombre = "";
		superClaves = new ArrayList<Clave>();

	}

}
