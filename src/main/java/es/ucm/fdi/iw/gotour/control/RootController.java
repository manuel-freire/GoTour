package es.ucm.fdi.iw.gotour.control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;


import org.springframework.beans.PropertyAccessor;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import es.ucm.fdi.iw.gotour.model.Mensajes;
import es.ucm.fdi.iw.gotour.model.User;
import es.ucm.fdi.iw.gotour.model.Chat;
import es.ucm.fdi.iw.gotour.model.TourOfertado;
/**
 * Landing-page controller
 * 
 * @author mfreire
 */
@Controller
public class RootController {
	
	private static final Logger log = LogManager.getLogger(RootController.class);
    @Autowired 
	private EntityManager entityManager;
    private AuthenticationManager authman;

	@GetMapping("/chat")
	public String chat(Model model, HttpServletRequest request) {
		return "chat";
	}
	
	@GetMapping("/error")
	public String error(Model model) {
		return "error";
	}
	
	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}

    @GetMapping("/registro")
	public String registro(Model model) {
		return "registro";
	}

    @GetMapping(value="tour/{id}")
	public String tourOfertado(@PathVariable long id, Model model) {
        TourOfertado u = entityManager.createNamedQuery("TourOfertado.getTour", TourOfertado.class)
		        .setParameter("id", id)
		        .getSingleResult();
        model.addAttribute("tour",u);
		return "tour";
	}

	public class Tour{
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

        /*public Guia getGuia() {
            return this.guia;
        }
        public void setGuia(Guia guia) {
            this.guia = guia;
        }     */  

    }
    

    @GetMapping("/")            // <-- en qué URL se expone, y por qué métodos (GET)        
    public String index(        // <-- da igual, sólo para desarrolladores
            Model model)        // <-- hay muchos, muchos parámetros opcionales
    {
        // lógica de control -- aquí actualizas el modelo
        ArrayList<Tour> tours= new ArrayList<>();
        tours.add(new Tour("01/07/2021","Madrid",10,50));
        tours.add(new Tour("21/07/2021","Londres",20,100));
        tours.add(new Tour("15/04/2021","Barcelona",50,200));
        tours.add(new Tour("31/08/2021","Paris",60,210));
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
    public String datosPrivados(Model model, HttpSession session)
    {   
        // model.addAttribute("user", entityManager.createNamedQuery("userByLogin", User.class).setParameter("loginParam", "email").getSingleResult());
        return "datosPrivados";
    }
}


