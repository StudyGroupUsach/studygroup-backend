package facade;

import java.util.List;

import javax.ejb.Local;

import model.GrupoTemporalPK;

@Local
public interface GrupoTemporalPKFacade {

	public void create(GrupoTemporalPK entity);

	public void edit(GrupoTemporalPK entity);

	public void remove(GrupoTemporalPK entity);

	public GrupoTemporalPK find(Object id);

	public List<GrupoTemporalPK> findAll();

	public List<GrupoTemporalPK> findRange(int[] range);

	public int count();

}
