package es.ucm.fdi.iw.gotour.model;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import java.time.LocalDate;

//@Entity
@Data
public class Tour {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/*private String lugar;
	private boolean activo;
	private String descripcion;
	
	@NotNull
	@OneToMany(mappedBy="toursofrecidos")
	private User guia;

	@NotNull
	private LocalDate fecha_ini;

	@NotNull
	private LocalDate fecha_fin;
;
	
	@NotNull
	

	@NotNull

	@NotNull

	@NotNull

	@NotNull
	private int precio;

	private String imagen;
	private String mapa;	
	@ManyToMany 
	private List<User> turistas = new ArrayList<>();


	@Override
	public String toString() {
		return "";
	}	*/
}
