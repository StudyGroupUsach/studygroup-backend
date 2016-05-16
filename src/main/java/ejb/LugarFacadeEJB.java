package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.AbstractFacade;
import facade.LugarFacade;
import model.Lugar;

@Stateless
public class LugarFacadeEJB extends AbstractFacade<Lugar> implements LugarFacade {
	
	
	@PersistenceContext(unitName = "studygroupPU")
	private EntityManager em;
	
	public LugarFacadeEJB() {
		super(Lugar.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return this.em;
	}

}