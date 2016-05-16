package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the grupo_horario database table.
 * 
 */
@Entity
@Table(name="grupo_horario")
@NamedQuery(name="GrupoHorario.findAll", query="SELECT g FROM GrupoHorario g")
public class GrupoHorario implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GrupoHorarioPK id;

	@Lob
	@Column(name="descripcion_horario")
	private String descripcionHorario;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_inicio")
	private Date fechaInicio;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_termino")
	private Date fechaTermino;

	@Column(name="medios_pago")
	private String mediosPago;

	@Column(name="tipo_pago")
	private String tipoPago;

	//bi-directional many-to-one association to Lugar
	@ManyToOne
	@JoinColumn(name="id_lugar")
	private Lugar lugar;

	//bi-directional many-to-one association to PerfilAyudante
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="perfil_ayudante_id", referencedColumnName="perfil_ayudante_id"),
		@JoinColumn(name="usuario_id", referencedColumnName="usuario_id")
		})
	private PerfilAyudante perfilAyudante;

	//bi-directional many-to-one association to Ramo
	@ManyToOne
	@JoinColumn(name="ramo_id")
	private Ramo ramo;

	public GrupoHorario() {
	}

	public GrupoHorarioPK getId() {
		return this.id;
	}

	public void setId(GrupoHorarioPK id) {
		this.id = id;
	}

	public String getDescripcionHorario() {
		return this.descripcionHorario;
	}

	public void setDescripcionHorario(String descripcionHorario) {
		this.descripcionHorario = descripcionHorario;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaTermino() {
		return this.fechaTermino;
	}

	public void setFechaTermino(Date fechaTermino) {
		this.fechaTermino = fechaTermino;
	}

	public String getMediosPago() {
		return this.mediosPago;
	}

	public void setMediosPago(String mediosPago) {
		this.mediosPago = mediosPago;
	}

	public String getTipoPago() {
		return this.tipoPago;
	}

	public void setTipoPago(String tipoPago) {
		this.tipoPago = tipoPago;
	}

	public Lugar getLugar() {
		return this.lugar;
	}

	public void setLugar(Lugar lugar) {
		this.lugar = lugar;
	}

	public PerfilAyudante getPerfilAyudante() {
		return this.perfilAyudante;
	}

	public void setPerfilAyudante(PerfilAyudante perfilAyudante) {
		this.perfilAyudante = perfilAyudante;
	}

	public Ramo getRamo() {
		return this.ramo;
	}

	public void setRamo(Ramo ramo) {
		this.ramo = ramo;
	}

}