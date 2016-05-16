package model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the perfil_ayudante database table.
 * 
 */
@Embeddable
public class PerfilAyudantePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="usuario_id", insertable=false, updatable=false)
	private int usuarioId;

	@Column(name="perfil_ayudante_id")
	private int perfilAyudanteId;

	public PerfilAyudantePK() {
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PerfilAyudantePK)) {
			return false;
		}
		PerfilAyudantePK castOther = (PerfilAyudantePK)other;
		return 
			(this.usuarioId == castOther.usuarioId)
			&& (this.perfilAyudanteId == castOther.perfilAyudanteId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.usuarioId;
		hash = hash * prime + this.perfilAyudanteId;
		
		return hash;
	}
}