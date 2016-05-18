package service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import facade.PerfilAyudanteFacade;
import model.PerfilAyudante;

@Path("/perfiles_ayudantes")
public class PerfilAyudanteService {
	
	@EJB 
	PerfilAyudanteFacade perfilAyudanteFacadeEJB;
	
	Logger logger = Logger.getLogger(PerfilAyudanteService.class.getName());
	
	@GET
	@Produces({"application/xml", "application/json"})
	public List<PerfilAyudante> findAll(){
		return perfilAyudanteFacadeEJB.findAll();
	}
	
	@GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public PerfilAyudante find(@PathParam("id") Integer id) {
        return perfilAyudanteFacadeEJB.find(id);
    }
	
	@POST
    @Consumes({"application/xml", "application/json"})
    public void create(PerfilAyudante entity) {
		perfilAyudanteFacadeEJB.create(entity);
    }

    
	@PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, PerfilAyudante entity) {
    	entity.setPerfilAyudanteId(id.intValue());
    	perfilAyudanteFacadeEJB.edit(entity);
    }
    
    
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Integer id){
    	PerfilAyudante perfilAyudante = perfilAyudanteFacadeEJB.find(id); 
    	perfilAyudanteFacadeEJB.remove(perfilAyudante);
    }
    
	

}
