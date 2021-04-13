package es.ucm.fdi.iw.gotour.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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
                        + "WHERE u.username = :username AND u.enabled = 1"),
		@NamedQuery(name="User.byId",
				query="SELECT u FROM User u "
						+ "WHERE u.id = :id AND u.enabled = 1"),
        @NamedQuery(name="User.hasUsername",
                query="SELECT COUNT(u) "
                        + "FROM User u "
                        + "WHERE u.username = :username"),
		@NamedQuery(name="userByLogin",
				query="select u from User u where u.email = :loginParam")
		
})

@NamedNativeQueries({
	@NamedNativeQuery(name="User.getToursOfrecidos",
		query="SELECT * from tour_ofertado WHERE guia_id = :guia_id"),
		@NamedNativeQuery(name="User.getIdToursOfrecidos",
		query="SELECT Id from tour_ofertado WHERE guia_id = :guia_id"),
	@NamedNativeQuery(name="User.getToursConcretos",
		query="SELECT * from tour WHERE datos_id = :datos_id"),
	@NamedNativeQuery(name="User.getReviewsRecibidas",
		query="SELECT * FROM Review WHERE destinatario_id = :dest"),
	@NamedNativeQuery(name="User.haslanguajes",
	query="SELECT idiomashablados from user_idiomashablados WHERE user_idiomashablados.user_id = :user_id")
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
	private long id;
	/** username for login purposes; must be unique */
	@Column(nullable = false, unique = true)
	private String username;
	/** encoded password; use setPassword(SecurityConfig.encode(plaintextPassword)) to encode it  */
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String roles; // split by ',' to separate roles
	private int enabled;

	// application-specific fields
	@NotNull
	@Size(max=244)
	private String nombre;

	@NotNull
	@Size(max=244)
	private String apellidos;
	
	private String email;
	private String foto;
	@NotNull
	@Size(max=244)
    private long numtarjeta;
	private String caducidadtarjeta;
	private int numsecreto;

	@NotNull
	private long numtelefono;

	@NotNull
	@Size(max=100)
	private String preguntaseguridad;

	@NotNull
	@Size(max=100)
	
	private String respuestaseguridad;

	@NotNull
	private int puntuacion;

	@OneToMany(targetEntity=Tour.class)
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
	

	@OneToMany
	@JoinColumn(name = "sender_id")
	private List<Message> sent = new ArrayList<>();
	@OneToMany
	@JoinColumn(name = "recipient_id")	
	private List<Message> received = new ArrayList<>();	
	
	// utility methods
	
	/**
	 * Checks whether this user has a given role.
	 * @param role to check
	 * @return true iff this user has that role.
	 */
	public boolean hasRole(Role role) {
		String roleName = role.name();
		return Arrays.stream(roles.split(","))
				.anyMatch(r -> r.equals(roleName));
	}
	

    @Getter
    @AllArgsConstructor
    public static class Transfer {
		private long id;
		private String apellidos;
		private String nombre;
        private String username;
		private long numtelefono;
		private int puntuacion;
		private List<Tour> tourofrecidos;
		private List<Tour> toursasistidos;
		private List<Review> reviewshechas;
		private List<Message> sent;
		private List<Message> received;
		private List<Review> reviewsrecibidas;
		private List<String> idiomashablados;

    }

	@Override
    public Transfer toTransfer() {
		return new Transfer(id, apellidos, nombre,	username, numtelefono, puntuacion, tourofrecidos, toursasistidos, reviewshechas, sent,  received, reviewsrecibidas, idiomashablados);
    }

	@Override
	public String toString() {
		return toTransfer().toString();
	}
}
