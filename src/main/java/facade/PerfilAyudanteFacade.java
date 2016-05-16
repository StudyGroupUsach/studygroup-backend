package facade;

import java.util.List;

import javax.ejb.Local;

import model.PerfilAyudante;

@Local
public interface PerfilAyudanteFacade {

	public void create(PerfilAyudante entity);

	public void edit(PerfilAyudante entity);

	public void remove(PerfilAyudante entity);

	public PerfilAyudante find(Object id);

	public List<PerfilAyudante> findAll();

	public List<PerfilAyudante> findRange(int[] range);

	public int count();

}