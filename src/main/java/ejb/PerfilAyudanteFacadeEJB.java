package ejb;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import facade.AbstractFacade;
import facade.PerfilAyudanteFacade;
import model.PerfilAyudante;

@Stateless
public class PerfilAyudanteFacadeEJB extends AbstractFacade<PerfilAyudante> implements PerfilAyudanteFacade {
	
	
	@PersistenceContext(unitName = "studygroupPU")
	private EntityManager em;
	
	public PerfilAyudanteFacadeEJB() {
		super(PerfilAyudante.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return this.em;
	}

}