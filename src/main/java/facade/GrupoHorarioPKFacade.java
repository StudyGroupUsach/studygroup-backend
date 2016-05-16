package facade;

import java.util.List;

import javax.ejb.Local;

import model.GrupoHorarioPK;

@Local
public interface GrupoHorarioPKFacade {

	public void create(GrupoHorarioPK entity);

	public void edit(GrupoHorarioPK entity);

	public void remove(GrupoHorarioPK entity);

	public GrupoHorarioPK find(Object id);

	public List<GrupoHorarioPK> findAll();

	public List<GrupoHorarioPK> findRange(int[] range);

	public int count();

}