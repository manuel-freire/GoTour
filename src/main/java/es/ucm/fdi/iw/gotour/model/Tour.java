package es.ucm.fdi.iw.gotour.model;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.JoinColumn;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Tour {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	 @NotNull
	 @ManyToOne
	 private TourOfertado datos;

	@NotNull
	private LocalDate fechaIni;

	@NotNull
	private LocalDate fechaFin;
    
	@NotNull
	private int actTuristas;

	@ManyToMany (mappedBy="toursasistidos")
	private List<User>  turistas = new ArrayList<>();
    
	@OneToMany (mappedBy="tourvalorado")
	private List<Review>  reviews = new ArrayList<>();

	@Override
	public String toString() {
		return "";
	}
}
