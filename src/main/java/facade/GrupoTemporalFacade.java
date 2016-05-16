package facade;

import java.util.List;

import javax.ejb.Local;

import model.GrupoTemporal;

@Local
public interface GrupoTemporalFacade {

	public void create(GrupoTemporal entity);

	public void edit(GrupoTemporal entity);

	public void remove(GrupoTemporal entity);

	public GrupoTemporal find(Object id);

	public List<GrupoTemporal> findAll();

	public List<GrupoTemporal> findRange(int[] range);

	public int count();

}
