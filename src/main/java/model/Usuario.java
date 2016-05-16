package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the usuario database table.
 * 
 */
@Entity
@Table(name="usuario")
@NamedQuery(name="Usuario.findAll", query="SELECT u FROM Usuario u")
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="usuario_id")
	private int usuarioId;

	private String apellidos;

	@Lob
	private String descripcion;

	private String mail;

	private String nombre;

	@Column(name="numero_movil")
	private String numeroMovil;

	//bi-directional many-to-one association to GrupoTemporal
	@OneToMany(mappedBy="usuario")
	private List<GrupoTemporal> grupoTemporals;

	//bi-directional many-to-one association to PerfilAyudante
	@OneToMany(mappedBy="usuario")
	private List<PerfilAyudante> perfilAyudantes;

	//bi-directional many-to-one association to Carrera
	@ManyToOne
	@JoinColumn(name="carrera_id")
	private Carrera carrera;

	public Usuario() {
	}

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getApellidos() {
		return this.apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getMail() {
		return this.mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNumeroMovil() {
		return this.numeroMovil;
	}

	public void setNumeroMovil(String numeroMovil) {
		this.numeroMovil = numeroMovil;
	}

	public List<GrupoTemporal> getGrupoTemporals() {
		return this.grupoTemporals;
	}

	public void setGrupoTemporals(List<GrupoTemporal> grupoTemporals) {
		this.grupoTemporals = grupoTemporals;
	}

	public GrupoTemporal addGrupoTemporal(GrupoTemporal grupoTemporal) {
		getGrupoTemporals().add(grupoTemporal);
		grupoTemporal.setUsuario(this);

		return grupoTemporal;
	}

	public GrupoTemporal removeGrupoTemporal(GrupoTemporal grupoTemporal) {
		getGrupoTemporals().remove(grupoTemporal);
		grupoTemporal.setUsuario(null);

		return grupoTemporal;
	}

	public List<PerfilAyudante> getPerfilAyudantes() {
		return this.perfilAyudantes;
	}

	public void setPerfilAyudantes(List<PerfilAyudante> perfilAyudantes) {
		this.perfilAyudantes = perfilAyudantes;
	}

	public PerfilAyudante addPerfilAyudante(PerfilAyudante perfilAyudante) {
		getPerfilAyudantes().add(perfilAyudante);
		perfilAyudante.setUsuario(this);

		return perfilAyudante;
	}

	public PerfilAyudante removePerfilAyudante(PerfilAyudante perfilAyudante) {
		getPerfilAyudantes().remove(perfilAyudante);
		perfilAyudante.setUsuario(null);

		return perfilAyudante;
	}

	public Carrera getCarrera() {
		return this.carrera;
	}

	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
	}

}