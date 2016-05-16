package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.AbstractFacade;
import facade.GrupoTemporalPKFacade;
import model.GrupoTemporalPK;

@Stateless
public class GrupoTemporalPKFacadeEJB extends AbstractFacade<GrupoTemporalPK> implements GrupoTemporalPKFacade {
	
	
	@PersistenceContext(unitName = "studygroupPU")
	private EntityManager em;
	
	public GrupoTemporalPKFacadeEJB() {
		super(GrupoTemporalPK.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return this.em;
	}

}