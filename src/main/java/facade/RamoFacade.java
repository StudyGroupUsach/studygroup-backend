package facade;

import java.util.List;

import javax.ejb.Local;

import model.Ramo;

@Local
public interface RamoFacade {

	public void create(Ramo entity);

	public void edit(Ramo entity);

	public void remove(Ramo entity);

	public Ramo find(Object id);

	public List<Ramo> findAll();

	public List<Ramo> findRange(int[] range);

	public int count();

}