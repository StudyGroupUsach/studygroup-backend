package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.AbstractFacade;
import facade.PerfilAyudantePKFacade;
import model.PerfilAyudantePK;

@Stateless
public class PerfilAyudantePKFacadeEJB extends AbstractFacade<PerfilAyudantePK> implements PerfilAyudantePKFacade {
	
	
	@PersistenceContext(unitName = "studygroupPU")
	private EntityManager em;
	
	public PerfilAyudantePKFacadeEJB() {
		super(PerfilAyudantePK.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return this.em;
	}

}
 