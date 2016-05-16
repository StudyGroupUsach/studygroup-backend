package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the grupo_horario database table.
 * 
 */
@Embeddable
public class GrupoHorarioPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="id_lugar", insertable=false, updatable=false)
	private int idLugar;

	@Column(name="usuario_id", insertable=false, updatable=false)
	private int usuarioId;

	@Column(name="perfil_ayudante_id", insertable=false, updatable=false)
	private int perfilAyudanteId;

	@Column(name="grupo_horario_id")
	private int grupoHorarioId;

	public GrupoHorarioPK() {
	}
	public int getIdLugar() {
		return this.idLugar;
	}
	public void setIdLugar(int idLugar) {
		this.idLugar = idLugar;
	}
	public int getUsuarioId() {
		return this.usuarioId;
	}
	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}
	public int getPerfilAyudanteId() {
		return this.perfilAyudanteId;
	}
	public void setPerfilAyudanteId(int perfilAyudanteId) {
		this.perfilAyudanteId = perfilAyudanteId;
	}
	public int getGrupoHorarioId() {
		return this.grupoHorarioId;
	}
	public void setGrupoHorarioId(int grupoHorarioId) {
		this.grupoHorarioId = grupoHorarioId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GrupoHorarioPK)) {
			return false;
		}
		GrupoHorarioPK castOther = (GrupoHorarioPK)other;
		return 
			(this.idLugar == castOther.idLugar)
			&& (this.usuarioId == castOther.usuarioId)
			&& (this.perfilAyudanteId == castOther.perfilAyudanteId)
			&& (this.grupoHorarioId == castOther.grupoHorarioId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.idLugar;
		hash = hash * prime + this.usuarioId;
		hash = hash * prime + this.perfilAyudanteId;
		hash = hash * prime + this.grupoHorarioId;
		
		return hash;
	}
}