package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the carrera database table.
 * 
 */
@Entity
@Table(name="carrera")
@NamedQuery(name="Carrera.findAll", query="SELECT c FROM Carrera c")
public class Carrera implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="carrera_id")
	private int carreraId;

	@Lob
	@Column(name="nombre_carrera")
	private String nombreCarrera;

	//bi-directional many-to-one association to Ramo
	@OneToMany(mappedBy="carrera")
	private List<Ramo> ramos;

	//bi-directional many-to-one association to Usuario
	@OneToMany(mappedBy="carrera")
	private List<Usuario> usuarios;

	public Carrera() {
	}

	public int getCarreraId() {
		return this.carreraId;
	}

	public void setCarreraId(int carreraId) {
		this.carreraId = carreraId;
	}

	public String getNombreCarrera() {
		return this.nombreCarrera;
	}

	public void setNombreCarrera(String nombreCarrera) {
		this.nombreCarrera = nombreCarrera;
	}

	public List<Ramo> getRamos() {
		return this.ramos;
	}

	public void setRamos(List<Ramo> ramos) {
		this.ramos = ramos;
	}

	public Ramo addRamo(Ramo ramo) {
		getRamos().add(ramo);
		ramo.setCarrera(this);

		return ramo;
	}

	public Ramo removeRamo(Ramo ramo) {
		getRamos().remove(ramo);
		ramo.setCarrera(null);

		return ramo;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario addUsuario(Usuario usuario) {
		getUsuarios().add(usuario);
		usuario.setCarrera(this);

		return usuario;
	}

	public Usuario removeUsuario(Usuario usuario) {
		getUsuarios().remove(usuario);
		usuario.setCarrera(null);

		return usuario;
	}

}