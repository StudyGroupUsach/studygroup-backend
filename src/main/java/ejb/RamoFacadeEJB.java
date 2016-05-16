package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.AbstractFacade;
import facade.RamoFacade;
import model.Ramo;

@Stateless
public class RamoFacadeEJB extends AbstractFacade<Ramo> implements RamoFacade {
	
	
	@PersistenceContext(unitName = "studygroupPU")
	private EntityManager em;
	
	public RamoFacadeEJB() {
		super(Ramo.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return this.em;
	}

}
