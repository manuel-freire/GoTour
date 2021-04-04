package es.ucm.fdi.iw.gotour.model;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
public class TourOfertado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	private String descripcion;

	private String portada;

	@NotNull
	private String ciudad;

	@NotNull
	private String titulo;

	private String lugar;

	@NotNull
	private String pais;

	private String mapa;

	private boolean disponible;
	
	@NotNull
	private double precio;

	@NotNull
	private int maxTuristas;

	@ElementCollection
	private List<String>  tags = new ArrayList<>();

	@NotNull
	@ManyToOne
	private User guia;

	@Override
	public String toString() {
		return "";
	}
	
}