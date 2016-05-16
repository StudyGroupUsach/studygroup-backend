package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the lugar database table.
 * 
 */
@Entity
@Table(name="lugar")
@NamedQuery(name="Lugar.findAll", query="SELECT l FROM Lugar l")
public class Lugar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_lugar")
	private int idLugar;

	@Column(name="latitud_lugar")
	private float latitudLugar;

	@Column(name="longitud_lugar")
	private float longitudLugar;

	//bi-directional many-to-one association to GrupoHorario
	@OneToMany(mappedBy="lugar")
	private List<GrupoHorario> grupoHorarios;

	//bi-directional many-to-one association to GrupoTemporal
	@OneToMany(mappedBy="lugar")
	private List<GrupoTemporal> grupoTemporals;

	public Lugar() {
	}

	public int getIdLugar() {
		return this.idLugar;
	}

	public void setIdLugar(int idLugar) {
		this.idLugar = idLugar;
	}

	public float getLatitudLugar() {
		return this.latitudLugar;
	}

	public void setLatitudLugar(float latitudLugar) {
		this.latitudLugar = latitudLugar;
	}

	public float getLongitudLugar() {
		return this.longitudLugar;
	}

	public void setLongitudLugar(float longitudLugar) {
		this.longitudLugar = longitudLugar;
	}

	public List<GrupoHorario> getGrupoHorarios() {
		return this.grupoHorarios;
	}

	public void setGrupoHorarios(List<GrupoHorario> grupoHorarios) {
		this.grupoHorarios = grupoHorarios;
	}

	public GrupoHorario addGrupoHorario(GrupoHorario grupoHorario) {
		getGrupoHorarios().add(grupoHorario);
		grupoHorario.setLugar(this);

		return grupoHorario;
	}

	public GrupoHorario removeGrupoHorario(GrupoHorario grupoHorario) {
		getGrupoHorarios().remove(grupoHorario);
		grupoHorario.setLugar(null);

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
		grupoTemporal.setLugar(this);

		return grupoTemporal;
	}

	public GrupoTemporal removeGrupoTemporal(GrupoTemporal grupoTemporal) {
		getGrupoTemporals().remove(grupoTemporal);
		grupoTemporal.setLugar(null);

		return grupoTemporal;
	}

}