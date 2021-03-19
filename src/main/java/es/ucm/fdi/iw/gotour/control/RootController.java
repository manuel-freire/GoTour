package es.ucm.fdi.iw.gotour.control;

import java.util.ArrayList;
//import org.springframework.web.bind.annotation.PathVariable;

// import java.beans.Introspector;
// import java.beans.PropertyDescriptor;
// import java.lang.reflect.Field;
// import java.lang.reflect.Method;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;

// import javax.persistence.EntityManager;
// import javax.persistence.ManyToMany;
// import javax.persistence.OneToMany;
// import javax.servlet.http.HttpServletRequest;
// import javax.transaction.Transactional;
// import javax.validation.Valid;

// import org.apache.logging.log4j.LogManager;
// import org.apache.logging.log4j.Logger;
// import org.springframework.beans.PropertyAccessor;
// import org.springframework.beans.PropertyAccessorFactory;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.ui.ModelMap;
// import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestParam;

// import es.ucm.fdi.iw.gotour.model.Car;
// import es.ucm.fdi.iw.gotour.model.Wheel;

// /**
//  * Controlador para probar cosas con la BD.
//  * 
//  * OJO: Sólo para probar cosas. En una aplicación real, debería haber
//  * - Control de accesos. No todo el mundo debe poder tocar todo.
//  * - Validación de campos. No nos podemos fiar de que los usuarios no intenten
//  *   introducir valores "malignos" en la aplicación, y hay que revisarlos tanto
//  *   en el cliente (=js en su navegador), por si lo hacen sin querer, como en el 
//  *   servidor (=aquí), por si los meten queriendo (y entonces pueden pasar
//  *   fácilmente de las validaciones del navegador)
//  * - Paginación. Mostrar toda la BD a la vez es caro, lento, e innecesario:
//  *   mejor mostrar sólo lo que se necesita en cada momento.
//  * 
//  * @author mfreire
//  */

// @Controller
// public class RootController {

// 	@Autowired 
// 	private EntityManager entityManager;
	
// 	private static final Logger log = LogManager.getLogger(RootController.class);

// 	@PostMapping("/addCar1")
// 	@Transactional 
// 	public String addCar1(
// 			@RequestParam String company,
// 			@RequestParam String model, Model m) {
// 		Car car = new Car();
// 		/*car.setCompany(company);
// 		car.setModel(model);*/
// 		entityManager.persist(car);
	     
// 	    //Do Something
// 	    return dump(m);
// 	}
	
// 	@PostMapping("/addCar2")
// 	@Transactional 
// 	public String addCar2(@Valid @ModelAttribute Car car, 
// 			BindingResult result,  ModelMap model, Model m) {
// 		if (result.hasErrors()) {
// 			log.warn("Validation errors: {}", 
// 					result.getAllErrors());
// 	        return "error";
// 	    }
// 		entityManager.persist(car);
	     
// 	    //Do Something
// 	    return dump(m);
// 	}
		
	
// 	@GetMapping("/car")
// 	public String getCar(@RequestParam long id, Model model ) {
// 		log.info("Requesting info about car {}",  id);
// 		model.addAttribute("car", entityManager.find(Car.class, id));
// 		return "car";
// 	}
	
// 	/**
// 	 * Añade, modifica o elimina un nuevo objeto del tipo especificado con los campos especificados.
// 	 * 
// 	 * Usa introspección para nombre de tabla y campos: esto es sólo para no escribir más
// 	 * código de la cuenta, y porque no estoy validando el contenido de estos campos. 
// 	 * Si estuviese validando, las comprobaciones de validación habría que hacerlas 
// 	 * caso a caso.
// 	 * 
// 	 * Es además feo y poco legible: NO USEIS INTROSPECCIÓN SIN MUY BUENA EXCUSA
// 	 * 
// 	 * @param model
// 	 * @return
// 	 */
// 	@PostMapping("/")
// 	@Transactional
// 	public String mod(Model model, 
// 			@RequestParam String tableName, 
// 			@RequestParam(required=false) Long id, 
// 			HttpServletRequest request) {
			
// 		if (id >= 0) {
// 			boolean isNewObject = (id == 0);
// 			Object o = isNewObject ? 
// 					newObjectByName(tableName) : 
// 					existingObjectById(tableName, id);
			
// 			for (Map.Entry<String, String[]> e : request.getParameterMap().entrySet()) {
// 				if (e.getKey().equals("tableName|id")) {
// 					continue; // not a valid field name
// 				}
// 				setObjectProperty(o, e.getKey(), String.join(",", e.getValue()));		
// 			}
// 			if (isNewObject) {
// 				entityManager.persist(o); // tells the entityManager to actively manage this object
// 			}
// 		} else {
// 			// I am using negative numbers to erase stuff. So id==10 modifies #10, id==-10 deletes it.
// 			entityManager.remove(existingObjectById(tableName, -id));
// 		}

// 		entityManager.flush();    // make the change immediately visible (so 'dump()' can see it)
// 		return dump(model);
// 	}

// 	@GetMapping("/")
// 	public String dump(Model model) {
// 		// list of all Objects to scan. 
// 		for (String tableName : "Car Driver Wheel".split(" ")) {
// 			// queries all objects
// 			List<?> results = entityManager.createQuery(
// 					"select x from " + tableName + " x").getResultList();
			
// 			// dumps them via log
// 			log.info("Dumping table {}", tableName);
// 			for (Object o : results) {
// 				log.info("\t{}", o);
// 			}
			
// 			// adds them to model
// 			model.addAttribute(tableName, results);
// 			// adds id-to-text map to model, too
// 			Map<String, String> idsToText = new HashMap<>();
// 			for (Object o : results) {
// 				idsToText.put(getObjectId(o), o.toString());
// 			}
// 			model.addAttribute(tableName+"Map", idsToText);
// 		}
				
// 		return "index";
// 	}
	
// 	private Object existingObjectById(String className, long id) {
// 		try {
// 			Class<?> clazz = getClass().getClassLoader().loadClass(className); 
// 			return entityManager.find(clazz, id);
// 		} catch (Exception e) {
// 			log.warn("Error retrieving object of class " + className + " with ID " + id, e);
// 			return null;
// 		}
// 	}
	
// 	private Object newObjectByName(String className) {
// 		try {
// 			Class<?> clazz = getClass().getClassLoader().loadClass(className);
// 			return clazz.getDeclaredConstructor().newInstance();
// 		} catch (Exception e) {
// 			log.warn("Error instantiating object of class " + className, e);
// 			return null;
// 		}
// 	}
		
// 	/**
// 	 * Sets any property of an object.
// 	 * @param o object to write to
// 	 * @param propertyName to use. For references, use '_id' at the end. 
// 	 * @param propertyValue to use. For references, use the id(s) of the object(s) to reference
// 	 *     Only knows how to handle a few literals. To add more, convert them from String 
// 	 */
// 	@SuppressWarnings({ "rawtypes", "unchecked" })
// 	private void setObjectProperty(Object o, String propertyName, String propertyValue) {
// 		boolean ok = true;
// 		try {
// 			Class<?> clazz = o.getClass();
// 			if ("tableName".equals(propertyName)) {
// 				return;
// 			}
// 			if (propertyName.endsWith("_id")) {
// 				propertyName = propertyName.substring(
// 						0, propertyName.length()-"_id".length()); // ignore the trailing '_id'
// 				Field f = o.getClass().getDeclaredField(propertyName);
// 				if (List.class.isAssignableFrom(f.getType())) {
// 					// add a list of references
// 					Method getter = getAccessor(clazz, true, propertyName);
// 					Class<?> inner = getter.getAnnotation(OneToMany.class) != null ?
// 							getter.getAnnotation(OneToMany.class).targetEntity() : 
// 							getter.getAnnotation(ManyToMany.class).targetEntity();
// 					List list = (List)getter.invoke(o);
// 					list.clear(); // remove previous values
// 					for (String id : propertyValue.split(",")) {
// 						list.add(entityManager.find(inner, 
// 								Long.parseLong(id))); 
// 					}
// 				} else {
// 					// set one reference
// 					Method setter = getAccessor(clazz, false, propertyName);
// 					setter.invoke(o, entityManager.find(f.getType(), 
// 							Long.parseLong(propertyValue))); 
// 				}
// 			} else {
// 				// set a literal value
// 				Method setter = getAccessor(clazz, false, propertyName);
// 				Class<?> type = setter.getParameters()[0].getType();
// 				if (type.equals(String.class)) {
// 					setter.invoke(o, propertyValue);
// 				} else if (type.isPrimitive()) {
// 					// rely on Spring - as per https://stackoverflow.com/a/15973019/15472
// 					PropertyAccessor accessor = PropertyAccessorFactory.forBeanPropertyAccess(o);
// 					accessor.setPropertyValue(propertyName, propertyValue);
// 				} else if (type.isEnum()) {
// 					setter.invoke(o, Wheel.Position.valueOf(propertyValue));
// 				} else {
// 					throw new UnsupportedOperationException("do not know how to set a " + type);
// 				}
// 			}
// 		} catch (Exception e) {
// 			log.warn("Error setting property {} to {} in a {}", propertyName, 
// 					propertyValue, o.getClass().getSimpleName());
// 			log.warn("... exception was:",  e);
// 			ok = false;
// 		}
// 		if (ok) {
// 			log.info("Correctly set property {} to {} in a {}", propertyName, 
// 				propertyValue, o.getClass().getSimpleName());
// 		}
// 	}
	
// 	private Method getAccessor(Class<?> clazz, boolean read, String propertyName) throws Exception {
// 		for (PropertyDescriptor prop : Introspector.getBeanInfo(clazz).getPropertyDescriptors()) {
// 			if (prop.getName().equals(propertyName)) {
// 				return read ? prop.getReadMethod() : prop.getWriteMethod();
// 			}
// 		}
// 		throw new IllegalArgumentException(
// 				"No " + (read?"read":"write") + " accessor for " 
// 						+ propertyName + " in " + clazz.getSimpleName());
// 	}
	
// 	private String getObjectId(Object o) {
// 		try {
// 			Field f = o.getClass().getDeclaredField("id");
// 			f.setAccessible(true);
// 			return ""+f.get(o);
// 		} catch (Exception e) {
// 			log.warn("Error retrieving id of class " + o.getClass().getSimpleName(), e);
// 			return null;
// 		}
// 	}
// }

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import es.ucm.fdi.iw.gotour.model.Tour;

@Controller
public class RootController {
    
    /*public static class Usuario {
        private String nombre;
        private String apellidos;
        private String rol;
        private Date membresia;
        private List<Tour> ToursDisp;
        private List<Tour> ToursCerrados;



        public Usuario(String nombre, String apellidos, String rol) {
            this.nombre = nombre;
            this.apellidos = apellidos;
            this.rol = rol;
        }

        public String getNombre() { return nombre; }
        public String getApellido() { return apellidos; }
        public String getRol() { return rol; }
    }*/
    /*public class Tour{
        private String fecha;
        private String lugar;
        private int personas;
        private int precio;
        //private Guia guia;

        public Tour(String fecha,String lugar,int personas,int precio){
            this.fecha=fecha;
            this.lugar=lugar;
            this.personas=personas;
            this.precio=precio;
            //this.guia=guia;
        }

        public String getFecha() {
            return this.fecha;
        }

        public void setFecha(String fecha) {
            this.fecha = fecha;
        }

        public String getLugar() {
            return this.lugar;
        }

        public void setLugar(String lugar) {
            this.lugar = lugar;
        }

        public int getPersonas() {
            return this.personas;
        }

        public void setPersonas(int personas) {
            this.personas = personas;
        }

        public int getPrecio() {
            return this.precio;
        }

        public void setPrecio(int precio) {
            this.precio = precio;
        }

        public Guia getGuia() {
            return this.guia;
        }

        public void setGuia(Guia guia) {
            this.guia = guia;
        }       

    }*/
    

    @GetMapping("/")            // <-- en qué URL se expone, y por qué métodos (GET)        
    public String index(        // <-- da igual, sólo para desarrolladores
            Model model)        // <-- hay muchos, muchos parámetros opcionales
    {
        // lógica de control -- aquí actualizas el modelo
        ArrayList<Tour> tours=new ArrayList();
        /*tours.add(new Tour("01/07/2021","Madrid",10,50));
        tours.add(new Tour("21/07/2021","Londres",20,100));
        tours.add(new Tour("15/04/2021","Barcelona",50,200));
        tours.add(new Tour("31/08/2021","Paris",60,210));*/
        model.addAttribute("tours",tours);
        return "index";
    }


    // Un getMapping por vista que queramos en el proyecto. Y un template por vista

    @GetMapping("/guia")
    public String guia(Model model)
    {
        model.addAttribute("user", "");   
        return "guia";
    }

    @GetMapping("/perfil")
    public String perfil(Model model)
    {  
        Date membresia = new Date();
        int id_usuario = 1;
        int id_sesion = 1;
        String[] tourD1 = {"Visita guiada del Coliseo", "Roma", "4 huecos disponibles"};
        String[] tourD2 = {"Madrid del siglo XIX", "Madrid", "9 huecos disponibles"};
        String[] tourC1 = {"Paseo cultural por Santillana del Mar", "Santillana del Mar"};
        String[] resenya1 = {"3 estrellas", "pedro", "Visita guiada del Coliseo", "Muy buen tour. El guía es muy agradable."};
        model.addAttribute("nombre", "Juan");
        model.addAttribute("apellidos", "Shánchez Pisuerga");
        if (id_usuario == id_sesion) model.addAttribute("propio", true);
        else model.addAttribute("propio", false);
        model.addAttribute("membresia", membresia);
        model.addAttribute("rol", "guia");
        model.addAttribute("tourD1",tourD1);
        model.addAttribute("tourD2",tourD2);
        model.addAttribute("tourC1",tourC1);
        model.addAttribute("resenya1",resenya1);
        model.addAttribute("estrellas", 3);
        return "perfil";
    }

    @GetMapping("/busqueda")
    public String busqueda(Model model)
    {  
        return "busqueda";
    }

    @GetMapping("/tour")
    public String tour(Model model)
    {  
        return "tour";
    }

    @GetMapping("/administracion")
    public String administracion(Model model)
    {  
        return "administracion";
    }

    @GetMapping("/leeme")
    public String leeme(Model model)
    {  
        return "leeme";
    }

    @GetMapping("/datosPrivados")
    public String datosPrivados(Model model)
    {  
        return "datosPrivados";
    }

    /*@GetMapping("/registro")
    public String registro(Model model)
    {  
        return "registro";
    }*///Esta está comentada porque nos ha dicho que si queremos si pero no es obligatoria de momento



    

}
