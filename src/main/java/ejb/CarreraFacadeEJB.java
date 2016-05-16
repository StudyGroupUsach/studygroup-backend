package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.AbstractFacade;
import facade.CarreraFacade;
import model.Carrera;

@Stateless
public class CarreraFacadeEJB extends AbstractFacade<Carrera> implements CarreraFacade {
	
	
	@PersistenceContext(unitName = "studygroupPU")
	private EntityManager em;
	
	public CarreraFacadeEJB() {
		super(Carrera.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return this.em;
	}

}
