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
import java.time.LocalDate;


@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
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
	public TourOfertado getDatos() {
		return datos;
	}
	public LocalDate getFechaIni() {
		return fechaIni;
	}
	public LocalDate getFechaFin() {
		return fechaFin;
	}
	public int getActTuristas() {
		return actTuristas;
	}
}
