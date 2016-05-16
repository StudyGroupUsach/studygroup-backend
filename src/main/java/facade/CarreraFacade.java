package facade;

import java.util.List;

import javax.ejb.Local;

import model.Carrera;

@Local
public interface CarreraFacade {

	public void create(Carrera entity);

	public void edit(Carrera entity);

	public void remove(Carrera entity);

	public Carrera find(Object id);

	public List<Carrera> findAll();

	public List<Carrera> findRange(int[] range);

	public int count();

}