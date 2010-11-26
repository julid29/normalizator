package ar.com.nn.busisness;

import java.util.ArrayList;

public class Relacion {

	private int idRelacion;
	private String nombre;
	private ArrayList<String> atributos;
	private ArrayList<String> superClave;
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
	// otra prueba para evitar instanciaci—n mœltiple
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
	public int getIdRelacion() {
		return idRelacion;
	}

	public void setIdRelacion(int idRelacion) {
		this.idRelacion = idRelacion;
	}

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

	public ArrayList<String> getSuperClave() {
		return superClave;
	}

	public void setSuperClave(ArrayList<String> superClave) {
		this.superClave = superClave;
	}

	public ArrayList<DepFuncional> getDepFuncionales() {
		return depFuncionales;
	}

	public void setDepFuncionales(ArrayList<DepFuncional> depFuncionales) {
		this.depFuncionales = depFuncionales;
	}

	public ArrayList<DepFuncional> getfMin() {
		return fMin;
	}

	public void setfMin(ArrayList<DepFuncional> fMin) {
		this.fMin = fMin;
	}

	public String getFormaNormal() {
		return formaNormal;
	}

	public void setFormaNormal(String formaNormal) {
		this.formaNormal = formaNormal;
	}

	public ArrayList<FormaNormal> getFormaNormal3() {
		return formaNormal3;
	}

	public void setFormaNormal3(ArrayList<FormaNormal> formaNormal3) {
		this.formaNormal3 = formaNormal3;
	}

	public ArrayList<FormaNormal> getFormaNormalBC() {
		return formaNormalBC;
	}

	public void setFormaNormalBC(ArrayList<FormaNormal> formaNormalBC) {
		this.formaNormalBC = formaNormalBC;
	}

	// Constructor de la clase Relacion.
	public Relacion(int idRelacion, String nombre, ArrayList<String> atributos,
			ArrayList<DepFuncional> depFuncionales) {
		this.idRelacion = idRelacion;
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
					if (depFun.loTenesEnDeterminantes(arrayAuxiliar)) {
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
				}
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
		while (recorrer < this.fMin.size()) {
			fMin.get(recorrer).setMarca(true);
			for (DepFuncional dependencia : this.fMin) {
				if (!dependencia.getMarca()) {
					if (dependencia.loTenesEnDeterminantes(this.fMin.get(
							recorrer).getDeterminantes())
							&& dependencia.loTenesEnDeterminados(this.fMin.get(
									recorrer).getDeterminados()))
						this.fMin.remove(dependencia);
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
			ArrayList<String> clausura = calcularClausura(
					dependenciaATratar.getDeterminantes(), 1);
			// Si NO TODOS los determinados estan en la clausura. Quito la
			// marca.
			if (!clausura.containsAll(dependenciaATratar.getDeterminados()))
				dependenciaATratar.setMarca(false);
		}

		// A las dependencias marcadas las elimino.
		for (DepFuncional dependencia : this.fMin) {
			if (dependencia.getMarca())
				fMin.remove(dependencia);
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

	// TODO obteneSuperClave()

	/**
	 * EL metodo formara las relaciones necesarias para hallar la Tercera Forma
	 * Normal.
	 */
	public void calcular3FormaNormal() {
		// 1) Encontrar Fmin.
		if (this.fMin == null)
			obtenerFmin();
		/*
		 * 2) Crear esquemas de relacion Ri, cuyos atributos contengan las
		 * dependencias funcionales del Fmin del esquema R
		 */
		for (DepFuncional depFun : this.fMin) {
			// compongo una lista con los determiantes y los determinados.
			ArrayList<String> atrs = new ArrayList<String>();
			atrs.addAll(depFun.getDeterminados());
			atrs.addAll(depFun.getDeterminantes());
			// Creo la forma normal y se lo agrego a la 3raFN.
			FormaNormal formaNormal = new FormaNormal(atrs, depFun);
			formaNormal3.add(formaNormal);
		}
		/*
		 * 3) Si ningun subesquema contiene la superClave de R, crear un
		 * subesquema con los attributos necesarios y depFuncional vacia
		 */
		/*
		 * if (this.superClave == null) obtenerSuperclave();
		 */
		// Agrego la relacion de la superclave con dep. funcional vacia.
		FormaNormal formaNormal = new FormaNormal(superClave);
		if (!formaNormal3.contains(formaNormal))
			formaNormal3.add(formaNormal);
	}

	public void calcularFNBC() {
		ArrayList<DepFuncional> depPerdidas = new ArrayList<DepFuncional>();
		FormaNormal principal = new FormaNormal(this.atributos,
				this.depFuncionales);
		// 1) Encontrar un Fmin.
		if (this.fMin == null)
			obtenerFmin();
		/*
		 * 2)Mientras existan dependencias funcionales que violen la forma
		 * normal se proyecta R en R1 y R2
		 */

		int i = 1;// Esta variable se usa para recorrer las dependencias
					// funcionales dentro del foreach (omitiendo la primera).

		for (DepFuncional depFun : principal.getDepFuncionales()) {
			// compongo una lista con los determiantes y los determinados.
			ArrayList<String> atrs = new ArrayList<String>();
			// Creo la forma normal y se lo agrego a la lista.
			if (!depFun.getMarca()) {
				atrs.addAll(depFun.getDeterminados());
				atrs.addAll(depFun.getDeterminantes());
				// Creo la forma normal y se lo agrego a la FNBC.
				FormaNormal formaNormal = new FormaNormal(atrs, depFun);
				formaNormalBC.add(formaNormal);
				int aux = i;
				while (aux < principal.getDepFuncionales().size()) {
					/*
					 * Evaluo si los atributos del subesquema creado, lo
					 * contiene alguna dependencia funcional tanto en sus
					 * determinantes o sus determinados. En caso de ser
					 * afirmativo, esta dependencia se perdera, entonces se
					 * marca dicha dependencia y se la agrega a la lista de
					 * dependencias perdidas.
					 */
					if (!principal.getDepFuncionales().get(i).getMarca()) {
						if (principal
								.getDepFuncionales()
								.get(i)
								.loTenesEnDeterminados(
										formaNormal.getAtributos())) {
							principal.getDepFuncionales().get(i).setMarca(true);
							depPerdidas.add(principal.getDepFuncionales()
									.get(i));
						} else if (principal
								.getDepFuncionales()
								.get(i)
								.loTenesEnDeterminados(
										formaNormal.getAtributos())) {
							principal.getDepFuncionales().get(i).setMarca(true);
							depPerdidas.add(principal.getDepFuncionales()
									.get(i));
						}
						aux++;
					}
				}
				i++;
				atrs.clear();
			}
		}
	}
}
