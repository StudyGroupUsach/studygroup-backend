package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.AbstractFacade;
import facade.GrupoTemporalFacade;
import model.GrupoTemporal;

@Stateless
public class GrupoTemporalFacadeEJB extends AbstractFacade<GrupoTemporal> implements GrupoTemporalFacade {
	
	
	@PersistenceContext(unitName = "studygroupPU")
	private EntityManager em;
	
	public GrupoTemporalFacadeEJB() {
		super(GrupoTemporal.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return this.em;
	}

}