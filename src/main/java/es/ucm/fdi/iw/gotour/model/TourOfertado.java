package es.ucm.fdi.iw.gotour.model;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.persistence.FetchType;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
//INSERT INTO TOUR_OFERTADO VALUES (1, 'Alcala', 'tour1', 1, '2021-12-12 20:00:00', '2021-12-12 20:00:00', 'tour1', 'adsa', 2, 'adas', 'dada', 8, 'adas', 2);

@Entity
@Data
@NamedQueries({
	@NamedQuery(name="TourOfertado.getTour",
			query="SELECT u FROM TourOfertado u "
					+ "WHERE u.Id = :id ")
})
@NamedNativeQueries({
	@NamedNativeQuery(name="TourOfertado.getEtiquetas",
	query="SELECT ETIQUETAS FROM TOUR_OFERTADO_ETIQUETAS WHERE TOUR_OFERTADO_ID = :id")
})
public class TourOfertado {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;

	@NotNull
	private String Descripcion;

	private String Portada;

	@NotNull
	private String Ciudad;

	@NotNull
	private String Titulo;

	private String Lugar;
    
	@NotNull
	private String Pais;

	private String Mapa;

	private boolean Disponible;
	
	@NotNull
	private double Precio;

	@NotNull
	private int MaxTuristas;

	@OneToMany (mappedBy="Datos", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Tour> Instancias = new ArrayList<>();

	@NotNull
	@ManyToOne
	private User Guia;
	
	@ElementCollection
	@CollectionTable(joinColumns = @JoinColumn(name="TourOfertado_id"))
	private List<String> Etiquetas = new ArrayList<String>();

	@Override
	public String toString() {
		return "";
	}
	
}