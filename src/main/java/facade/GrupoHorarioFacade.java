package facade;

import java.util.List;

import javax.ejb.Local;

import model.GrupoHorario;

@Local
public interface GrupoHorarioFacade {

	public void create(GrupoHorario entity);

	public void edit(GrupoHorario entity);

	public void remove(GrupoHorario entity);

	public GrupoHorario find(Object id);

	public List<GrupoHorario> findAll();

	public List<GrupoHorario> findRange(int[] range);

	public int count();

}