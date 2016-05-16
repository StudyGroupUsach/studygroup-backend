package facade;

import java.util.List;

import javax.ejb.Local;

import model.Lugar;

@Local
public interface LugarFacade {

	public void create(Lugar entity);

	public void edit(Lugar entity);

	public void remove(Lugar entity);

	public Lugar find(Object id);

	public List<Lugar> findAll();

	public List<Lugar> findRange(int[] range);

	public int count();

}