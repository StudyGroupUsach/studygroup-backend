package model;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Time;
import java.util.Date;


/**
 * The persistent class for the grupo_temporal database table.
 * 
 */
@Entity
@Table(name="grupo_temporal")
@NamedQuery(name="GrupoTemporal.findAll", query="SELECT g FROM GrupoTemporal g")
public class GrupoTemporal implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GrupoTemporalPK id;

	@Lob
	@Column(name="descripcion_temporal")
	private String descripcionTemporal;

	@Column(name="duracion_temporal")
	private Time duracionTemporal;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="inicio_temporal")
	private Date inicioTemporal;

	//bi-directional many-to-one association to Lugar
	@ManyToOne
	@JoinColumn(name="id_lugar")
	private Lugar lugar;

	//bi-directional many-to-one association to Ramo
	@ManyToOne
	@JoinColumn(name="ramo_id")
	private Ramo ramo;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="usuario_id")
	private Usuario usuario;

	public GrupoTemporal() {
	}

	public GrupoTemporalPK getId() {
		return this.id;
	}

	public void setId(GrupoTemporalPK id) {
		this.id = id;
	}

	public String getDescripcionTemporal() {
		return this.descripcionTemporal;
	}

	public void setDescripcionTemporal(String descripcionTemporal) {
		this.descripcionTemporal = descripcionTemporal;
	}

	public Time getDuracionTemporal() {
		return this.duracionTemporal;
	}

	public void setDuracionTemporal(Time duracionTemporal) {
		this.duracionTemporal = duracionTemporal;
	}

	public Date getInicioTemporal() {
		return this.inicioTemporal;
	}

	public void setInicioTemporal(Date inicioTemporal) {
		this.inicioTemporal = inicioTemporal;
	}

	public Lugar getLugar() {
		return this.lugar;
	}

	public void setLugar(Lugar lugar) {
		this.lugar = lugar;
	}

	public Ramo getRamo() {
		return this.ramo;
	}

	public void setRamo(Ramo ramo) {
		this.ramo = ramo;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}