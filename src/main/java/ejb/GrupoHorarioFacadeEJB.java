package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.AbstractFacade;
import facade.GrupoHorarioFacade;
import model.GrupoHorario;

@Stateless
public class GrupoHorarioFacadeEJB extends AbstractFacade<GrupoHorario> implements GrupoHorarioFacade {
	
	
	@PersistenceContext(unitName = "studygroupPU")
	private EntityManager em;
	
	public GrupoHorarioFacadeEJB() {
		super(GrupoHorario.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return this.em;
	}

}