package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.AbstractFacade;
import facade.GrupoHorarioPKFacade;
import model.GrupoHorarioPK;

@Stateless
public class GrupoHorarioPKFacadeEJB extends AbstractFacade<GrupoHorarioPK> implements GrupoHorarioPKFacade {
	
	
	@PersistenceContext(unitName = "studygroupPU")
	private EntityManager em;
	
	public GrupoHorarioPKFacadeEJB() {
		super(GrupoHorarioPK.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return this.em;
	}

}