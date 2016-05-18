package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the perfil_ayudante database table.
 * 
 */
@Entity
@Table(name="perfil_ayudante")
@NamedQuery(name="PerfilAyudante.findAll", query="SELECT p FROM PerfilAyudante p")
public class PerfilAyudante implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="perfil_ayudante_id")
	private int perfilAyudanteId;

	private String estado;

	@Column(name="valoracion_promedio")
	private int valoracionPromedio;

	//bi-directional many-to-one association to GrupoHorario
	@OneToMany(mappedBy="perfilAyudante")
	private List<GrupoHorario> grupoHorarios;

	//bi-directional one-to-one association to Usuario
	@OneToOne
	@JoinColumn(name="usuario_id")
	private Usuario usuario1;

	public PerfilAyudante() {
	}

	public int getPerfilAyudanteId() {
		return this.perfilAyudanteId;
	}

	public void setPerfilAyudanteId(int perfilAyudanteId) {
		this.perfilAyudanteId = perfilAyudanteId;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public int getValoracionPromedio() {
		return this.valoracionPromedio;
	}

	public void setValoracionPromedio(int valoracionPromedio) {
		this.valoracionPromedio = valoracionPromedio;
	}

	public List<GrupoHorario> getGrupoHorarios() {
		return this.grupoHorarios;
	}

	public void setGrupoHorarios(List<GrupoHorario> grupoHorarios) {
		this.grupoHorarios = grupoHorarios;
	}

	public GrupoHorario addGrupoHorario(GrupoHorario grupoHorario) {
		getGrupoHorarios().add(grupoHorario);
		grupoHorario.setPerfilAyudante(this);

		return grupoHorario;
	}

	public GrupoHorario removeGrupoHorario(GrupoHorario grupoHorario) {
		getGrupoHorarios().remove(grupoHorario);
		grupoHorario.setPerfilAyudante(null);

		return grupoHorario;
	}

	public Usuario getUsuario1() {
		return this.usuario1;
	}

	public void setUsuario1(Usuario usuario1) {
		this.usuario1 = usuario1;
	}

}