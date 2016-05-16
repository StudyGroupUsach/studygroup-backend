package facade;

import java.util.List;

import javax.ejb.Local;

import model.PerfilAyudantePK;

@Local
public interface PerfilAyudantePKFacade {

	public void create(PerfilAyudantePK entity);

	public void edit(PerfilAyudantePK entity);

	public void remove(PerfilAyudantePK entity);

	public PerfilAyudantePK find(Object id);

	public List<PerfilAyudantePK> findAll();

	public List<PerfilAyudantePK> findRange(int[] range);

	public int count();

}
