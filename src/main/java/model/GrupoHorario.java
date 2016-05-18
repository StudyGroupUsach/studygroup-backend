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

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="grupo_horario_id")
	private int grupoHorarioId;

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

	@Column(name="usuario_id")
	private int usuarioId;

	//bi-directional many-to-one association to Lugar
	@ManyToOne
	@JoinColumn(name="id_lugar")
	private Lugar lugar;

	//bi-directional many-to-one association to PerfilAyudante
	@ManyToOne
	@JoinColumn(name="perfil_ayudante_id")
	private PerfilAyudante perfilAyudante;

	//bi-directional many-to-one association to Ramo
	@ManyToOne
	@JoinColumn(name="ramo_id")
	private Ramo ramo;

	public GrupoHorario() {
	}

	public int getGrupoHorarioId() {
		return this.grupoHorarioId;
	}

	public void setGrupoHorarioId(int grupoHorarioId) {
		this.grupoHorarioId = grupoHorarioId;
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

	public int getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
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