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
	@NamedQuery(name="ToursBySearch", query="Select t from Tour t where t.datos.pais=:paisParam and "+
																		"t.datos.ciudad=:ciudadParam and "+
																		"t.datos.lugar=:lugarParam and "+
																		"t.fechaIni=:fechaIniParam and "+
																		"t.fechaFin=:fechaFinParam"),
	@NamedQuery(name="Tour.getTour",
		query="SELECT u FROM Tour u "
				+ "WHERE u.id = :id ")
})

public class Tour {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	 @NotNull
	 @ManyToOne (targetEntity=TourOfertado.class)
	 private TourOfertado datos;

	 @NotNull
	private LocalDateTime fechaIni;

	@NotNull
	private LocalDateTime fechaFin;

	@NotNull
	private int actTuristas;
	
	@ManyToMany (mappedBy="toursasistidos")
	private List<User>  turistas = new ArrayList<>();
    
	@OneToMany (mappedBy="tourvalorado")
	private List<Review>  reviews = new ArrayList<>();

	public void addTurista(User u,int numero){
		if(datos.getMaxTuristas() >= (actTuristas+numero)){
			turistas.add(u);
			actTuristas+=numero;
			u.addTour(this);
		}
	}

	@Override
	public String toString() {
		return "";
	}
}
