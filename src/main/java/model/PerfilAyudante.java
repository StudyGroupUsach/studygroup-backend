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

	@EmbeddedId
	private PerfilAyudantePK id;

	private String estado;

	@Column(name="valoracion_promedio")
	private int valoracionPromedio;

	//bi-directional many-to-one association to GrupoHorario
	@OneToMany(mappedBy="perfilAyudante")
	private List<GrupoHorario> grupoHorarios;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="usuario_id")
	private Usuario usuario;

	public PerfilAyudante() {
	}

	public PerfilAyudantePK getId() {
		return this.id;
	}

	public void setId(PerfilAyudantePK id) {
		this.id = id;
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

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}