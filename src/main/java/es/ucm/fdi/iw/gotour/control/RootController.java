package es.ucm.fdi.iw.gotour.control;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class RootController {
    
    public static class Persona {
        private String nombre;
        private String apellido;
        public Persona(String nombre, String apellido ) {
            this.nombre = nombre;
            this.apellido = apellido;
        }

        public String getNombre() { return nombre; }
        public String getApellido() { return apellido; }
    }
    

    @GetMapping("/")            // <-- en qué URL se expone, y por qué métodos (GET)        
    public String index(        // <-- da igual, sólo para desarrolladores
            Model model)        // <-- hay muchos, muchos parámetros opcionales
    {
        // lógica de control -- aquí actualizas el modelo

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

    @GetMapping("/admin")
    public String admin(Model model)
    {  
        return "admin";
    }

    /*@GetMapping("/registro")
    public String registro(Model model)
    {  
        return "registro";
    }*///Esta está comentada porque nos ha dicho que si queremos si pero no es obligatoria de momento



    

}
