package es.ucm.fdi.iw.gotour.model;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import javax.validation.constraints.Size;
import javax.persistence.JoinColumn;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
@NoArgsConstructor
@NamedQueries({
	@NamedQuery(name="AllTours", query="Select t from Tour t"),
	@NamedQuery(name="ToursBySearch", query="Select t from Tour t where t.Datos.Pais=:paisParam and "+
																		"t.Datos.Ciudad=:ciudadParam and "+
																		"t.Datos.Lugar=:lugarParam and "+
																		"t.FechaIni=:fechaIniParam and "+
																		"t.FechaFin=:fechaFinParam"),
	@NamedQuery(name="Tour.getTour",
		query="SELECT u FROM Tour u "
				+ "WHERE u.Id = :id ")
})

public class Tour {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;

	 @NotNull
	 @ManyToOne (targetEntity=TourOfertado.class)
	 private TourOfertado Datos;

	 @NotNull
	private LocalDateTime FechaIni;

	@NotNull
	private LocalDateTime FechaFin;

	@NotNull
	private int ActTuristas;
	
	@ManyToMany (mappedBy="ToursAsistidos")
	private List<User>  Turistas = new ArrayList<>();
    
	@OneToMany (mappedBy="TourValorado")
	private List<Review>  Reviews = new ArrayList<>();


	@Override
	public String toString() {
		return "";
	}
}
