package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the grupo_temporal database table.
 * 
 */
@Embeddable
public class GrupoTemporalPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="usuario_id", insertable=false, updatable=false)
	private int usuarioId;

	@Column(name="grupo_temporal_id")
	private int grupoTemporalId;

	public GrupoTemporalPK() {
	}
	public int getUsuarioId() {
		return this.usuarioId;
	}
	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}
	public int getGrupoTemporalId() {
		return this.grupoTemporalId;
	}
	public void setGrupoTemporalId(int grupoTemporalId) {
		this.grupoTemporalId = grupoTemporalId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GrupoTemporalPK)) {
			return false;
		}
		GrupoTemporalPK castOther = (GrupoTemporalPK)other;
		return 
			(this.usuarioId == castOther.usuarioId)
			&& (this.grupoTemporalId == castOther.grupoTemporalId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.usuarioId;
		hash = hash * prime + this.grupoTemporalId;
		
		return hash;
	}
}