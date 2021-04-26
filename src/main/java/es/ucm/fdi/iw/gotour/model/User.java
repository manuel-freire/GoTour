package es.ucm.fdi.iw.gotour.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.ManyToMany;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.ElementCollection;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * A user; can be an Admin, a User, or a Moderator
 *
 * Users can log in and send each other messages.
 *
 * @author mfreire
 */
/**
 * An authorized user of the system.
 */
@Entity
@Data
@NoArgsConstructor
@NamedQueries({
        @NamedQuery(name="User.byUsername",
                query="SELECT u FROM User u "
                        + "WHERE u.Username = :username AND u.Enabled = 1"),
		@NamedQuery(name="User.byId",
				query="SELECT u FROM User u "
						+ "WHERE u.Id = :id AND u.Enabled = 1"),
        @NamedQuery(name="User.hasUsername",
                query="SELECT COUNT(u) "
                        + "FROM User u "
                        + "WHERE u.Username = :username"),
		@NamedQuery(name="userByLogin",
				query="select u from User u where u.Email = :loginParam"),
		@NamedQuery(name="AllUsers", query="Select u from User u"),
		@NamedQuery(name="User.byTour",
				query="select u FROM User u JOIN User_Tours_Asistidos t WHERE t.Tours_Asistidos_Id= :tourParam")
})

@NamedNativeQueries({
	@NamedNativeQuery(name="User.getToursOfrecidos",
		query="SELECT * from tour_ofertado WHERE Guia_id = :guia_id"),
		@NamedNativeQuery(name="User.getIdToursOfrecidos",
		query="SELECT Id from tour_ofertado WHERE Guia_id = :guia_id"),
	@NamedNativeQuery(name="User.getToursConcretos",
		query="SELECT * from tour WHERE Datos_id = :datos_id"),
	@NamedNativeQuery(name="User.getReviewsRecibidas",
		query="SELECT * FROM Review WHERE Destinatario_id = :dest"),
	@NamedNativeQuery(name="User.haslanguajes",
	query="SELECT idiomas_hablados from user_idiomas_hablados WHERE user_idiomas_hablados.User_id = :user_id")
})
public class User implements Transferable<User.Transfer> {


	private static Logger log = LogManager.getLogger(User.class);	

	public enum Role {
		USER,			// used for logged-in, non-priviledged users
		ADMIN,			// used for maximum priviledged users
		MODERATOR,		// remove or add roles as needed
	}
	
	// do not change these fields

	/** 
	 * not a DB column, but very useful to handle passwords; 
	 * see passwordMatches & encodePassword 
	 * All those annotations prevent persistence and Lombok-generated getters & setters
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long Id;
	/** username for login purposes; must be unique */
	@Column(nullable = false, unique = true)
	private String Username;
	/** encoded password; use setPassword(SecurityConfig.encode(plaintextPassword)) to encode it  */
	@Column(nullable = false)
	private String Password;
	@Column(nullable = false)
	private String Roles; // split by ',' to separate roles
	private int Enabled;

	// application-specific fields
	@NotNull
	@Size(max=244)
	private String Nombre;

	@NotNull
	@Size(max=244)
	private String Apellidos;
	
	private String Email;
	private String Foto;
	@Size(max=4)
    private int NumTarjeta;
	private String CaducidadTarjeta;
	private int NumSecreto;

	@NotNull
	private long NumTelefono;

	@NotNull
	@Size(max=100)
	private String PreguntaSeguridad;

	@NotNull
	@Size(max=100)
	private String RespuestaSeguridad;

	private int Puntuacion;

	@OneToMany(targetEntity=Tour.class)
	@JoinColumn(name="creador_id")
	private List<Tour> TourOfertados=new ArrayList<>();

	@ManyToMany(targetEntity=Tour.class, fetch=FetchType.EAGER)
	private List<Tour> ToursAsistidos=new ArrayList<>(); 

	@OneToMany(targetEntity=Review.class)
	@JoinColumn(name="Creador_id")
	private List<Review> ReviewsHechas=new ArrayList<>();

	@OneToMany
	@JoinColumn(name="Creador_id")
	private List<Mensajes> Mensajes = new ArrayList<>();
	
	@OneToMany(targetEntity=Review.class)
	@JoinColumn(name="destinatario_id")
	private List<Review> ReviewsRecibidas=new ArrayList<>();
	@ElementCollection
	private List<String> IdiomasHablados=new ArrayList<>();
	

	@OneToMany
	@JoinColumn(name = "Sender_id")
	private List<Message> Sent = new ArrayList<>();
	@OneToMany
	@JoinColumn(name = "Recipient_id")	
	private List<Message> Received = new ArrayList<>();	
	
	// utility methods
	
	/**
	 * Checks whether this user has a given role.
	 * @param role to check
	 * @return true iff this user has that role.
	 */
	public boolean hasRole(Role Role) {
		String roleName = Role.name();
		return Arrays.stream(Roles.split(","))
				.anyMatch(r -> r.equals(roleName));
	}
	
	public void addTour(Tour t){
		this.ToursAsistidos.add(t);
	}

    @Getter
    @AllArgsConstructor
    public static class Transfer {
		private long Id;
		private String Apellidos;
		private String Nombre;
        private String Username;
		private long Numtelefono;
		private int Puntuacion;
		private List<Tour> TourOfrecidos;
		private List<Tour> ToursAsistidos;
		private List<Review> ReviewsHechas;
		private List<Message> Sent;
		private List<Message> Received;
		private List<Review> ReviewsRecibidas;
		private List<String> IdiomasHablados;

    }

	@Override
    public Transfer toTransfer() {
		return new Transfer(Id, Apellidos, Nombre,	Username, NumTelefono, Puntuacion, TourOfertados, ToursAsistidos, ReviewsHechas, Sent,  Received, ReviewsRecibidas, IdiomasHablados);
    }

	@Override
	public String toString() {
		return toTransfer().toString();
	}
}
