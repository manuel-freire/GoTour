package es.ucm.fdi.iw.gotour.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.ArrayList;

import lombok.Data;


@Entity
@Data
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

    @NotNull
	@Size(max=244)
	private String nombre;

	@NotNull
	@Size(max=244)
	private String apellidos;
	
	
	@NotNull
	private String email;
	private String rol;

	@NotNull
	@Size(max=244)
	private String password;
    private int numtarjeta;
	private String caducidad_tarjeta;

	@NotNull
	private int numtelefono;

	@NotNull
	@Size(max=100)
	private String preguntaseguridad;

	@NotNull
	@Size(max=100)
	
	private String respuestaseguridad;

	@OneToMany(targetEntity=Tour.class)
	@JoinColumn(name="guia_id")
	private List<Tour> tourofrecidos=new ArrayList<>();

	@ManyToMany(targetEntity=Tour.class)
	private List<Tour> toursasistidos=new ArrayList<>(); 

	@OneToMany(targetEntity=Review.class)
	@JoinColumn(name="creador_id")
	private List<Review> reviewshechas=new ArrayList<>();

	@OneToMany
	@JoinColumn(name="creador_id")
	private List<Mensajes> mensajes = new ArrayList<>();
	
	@OneToMany(targetEntity=Review.class)
	@JoinColumn(name="destinatario_id")
	private List<Review> reviewsrecibidas=new ArrayList<>();

	@ElementCollection
	private List<String> idiomashablados=new ArrayList<>();
	

	@Override
	public String toString() {
		return "";
	}
}
