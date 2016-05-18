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

import facade.RamoFacade;
import model.Ramo;

@Path("/ramos")
public class RamoService {
	
	@EJB 
	RamoFacade ramoFacadeEJB;
	
	Logger logger = Logger.getLogger(RamoService.class.getName());
	
	@GET
	@Produces({"application/xml", "application/json"})
	public List<Ramo> findAll(){
		return ramoFacadeEJB.findAll();
	}
	
	@GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Ramo find(@PathParam("id") Integer id) {
        return ramoFacadeEJB.find(id);
    }
	
	@POST
    @Consumes({"application/xml", "application/json"})
    public void create(Ramo entity) {
        ramoFacadeEJB.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Ramo entity) {
    	entity.setRamoId(id.intValue());
        ramoFacadeEJB.edit(entity);
    }
    
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Integer id){
    	Ramo ramo = ramoFacadeEJB.find(id); 
    	ramoFacadeEJB.remove(ramo);
    }
    
	

}