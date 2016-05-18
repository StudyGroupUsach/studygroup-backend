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

import facade.GrupoTemporalFacade;
import model.GrupoTemporal;

@Path("/grupos_temporales")
public class GrupoTemporalService {
	
	@EJB 
	GrupoTemporalFacade grupoTemporalFacadeEJB;
	
	Logger logger = Logger.getLogger(GrupoTemporalService.class.getName());
	
	@GET
	@Produces({"application/xml", "application/json"})
	public List<GrupoTemporal> findAll(){
		return grupoTemporalFacadeEJB.findAll();
	}
	
	@GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public GrupoTemporal find(@PathParam("id") Integer id) {
        return grupoTemporalFacadeEJB.find(id);
    }
	
	@POST
    @Consumes({"application/xml", "application/json"})
    public void create(GrupoTemporal entity) {
		grupoTemporalFacadeEJB.create(entity);
    }

    
	@PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, GrupoTemporal entity) {
    	entity.setGrupoTemporalId(id.intValue());
    	grupoTemporalFacadeEJB.edit(entity);
    }
    
    
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Integer id){
    	GrupoTemporal grupoTemporal = grupoTemporalFacadeEJB.find(id); 
    	grupoTemporalFacadeEJB.remove(grupoTemporal);
    }
    
	

}
