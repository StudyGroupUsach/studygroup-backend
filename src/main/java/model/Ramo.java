package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ramo database table.
 * 
 */
@Entity
@Table(name="ramo")
@NamedQuery(name="Ramo.findAll", query="SELECT r FROM Ramo r")
public class Ramo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ramo_id")
	private int ramoId;

	@Lob
	@Column(name="nombre_ramo")
	private String nombreRamo;

	//bi-directional many-to-one association to GrupoHorario
	@OneToMany(mappedBy="ramo")
	private List<GrupoHorario> grupoHorarios;

	//bi-directional many-to-one association to GrupoTemporal
	@OneToMany(mappedBy="ramo")
	private List<GrupoTemporal> grupoTemporals;

	//bi-directional many-to-one association to GrupoHorario
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="grupo_horario_id", referencedColumnName="grupo_horario_id"),
		@JoinColumn(name="id_lugar", referencedColumnName="id_lugar"),
		@JoinColumn(name="perfil_ayudante_id", referencedColumnName="perfil_ayudante_id"),
		@JoinColumn(name="ramo_id", referencedColumnName="ramo_id"),
		@JoinColumn(name="usuario_id", referencedColumnName="usuario_id")
		})
	private GrupoHorario grupoHorario;

	//bi-directional many-to-many association to Carrera
	@ManyToMany
	@JoinTable(
		name="ramos_carrera"
		, joinColumns={
			@JoinColumn(name="ramo_id")
			}
		, inverseJoinColumns={
			@JoinColumn(name="carrera_id")
			}
		)
	private List<Carrera> carreras;

	public Ramo() {
	}

	public int getRamoId() {
		return this.ramoId;
	}

	public void setRamoId(int ramoId) {
		this.ramoId = ramoId;
	}

	public String getNombreRamo() {
		return this.nombreRamo;
	}

	public void setNombreRamo(String nombreRamo) {
		this.nombreRamo = nombreRamo;
	}

	public List<GrupoHorario> getGrupoHorarios() {
		return this.grupoHorarios;
	}

	public void setGrupoHorarios(List<GrupoHorario> grupoHorarios) {
		this.grupoHorarios = grupoHorarios;
	}

	public GrupoHorario addGrupoHorario(GrupoHorario grupoHorario) {
		getGrupoHorarios().add(grupoHorario);
		grupoHorario.setRamo(this);

		return grupoHorario;
	}

	public GrupoHorario removeGrupoHorario(GrupoHorario grupoHorario) {
		getGrupoHorarios().remove(grupoHorario);
		grupoHorario.setRamo(null);

		return grupoHorario;
	}

	public List<GrupoTemporal> getGrupoTemporals() {
		return this.grupoTemporals;
	}

	public void setGrupoTemporals(List<GrupoTemporal> grupoTemporals) {
		this.grupoTemporals = grupoTemporals;
	}

	public GrupoTemporal addGrupoTemporal(GrupoTemporal grupoTemporal) {
		getGrupoTemporals().add(grupoTemporal);
		grupoTemporal.setRamo(this);

		return grupoTemporal;
	}

	public GrupoTemporal removeGrupoTemporal(GrupoTemporal grupoTemporal) {
		getGrupoTemporals().remove(grupoTemporal);
		grupoTemporal.setRamo(null);

		return grupoTemporal;
	}

	public GrupoHorario getGrupoHorario() {
		return this.grupoHorario;
	}

	public void setGrupoHorario(GrupoHorario grupoHorario) {
		this.grupoHorario = grupoHorario;
	}

	public List<Carrera> getCarreras() {
		return this.carreras;
	}

	public void setCarreras(List<Carrera> carreras) {
		this.carreras = carreras;
	}

}