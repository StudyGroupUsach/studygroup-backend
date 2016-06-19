package service;

//import java.util.List;
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

import adapters.ListSerializer;
import facade.LugarFacade;
import model.Lugar;

@Path("/lugares")
public class LugarService {
	
	@EJB 
	LugarFacade lugarFacadeEJB;
	
	Logger logger = Logger.getLogger(LugarService.class.getName());
	
	@GET
	@Produces({"application/json"})
	public String findAll(){
		ListSerializer serializer = new ListSerializer();
		String result = serializer.LugarListSerializer(lugarFacadeEJB.findAll());
		return result;
	}
	
	@GET
    @Path("{id}")
    @Produces({"application/json"})
    public String find(@PathParam("id") Integer id) {
		ListSerializer serializer = new ListSerializer();
		String result = serializer.LugarSerializer(lugarFacadeEJB.find(id));
		return result;
    }
	
	@POST
    @Consumes({"application/xml", "application/json"})
	@Produces({"application/xml", "application/json"})
    public Lugar create(Lugar entity) {
		if (entity.getLatitudLugar() != 0.0f && entity.getLongitudLugar() != 0.0f){
			lugarFacadeEJB.create(entity);
			return entity;
		}
		return new Lugar();
    }

    
	@PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Lugar entity) {
		Lugar lugar = lugarFacadeEJB.find(id);
		if (lugar != null){
			if (entity.getLatitudLugar() == 0.0f){
				entity.setLatitudLugar(lugar.getLatitudLugar());
			}
			if (entity.getLongitudLugar() == 0.0f){
				entity.setLongitudLugar(lugar.getLongitudLugar());
			}
			if (entity.getNombreLugar() == null){
				entity.setNombreLugar(lugar.getNombreLugar());
			}
		}
    	entity.setIdLugar(id.intValue());
    	lugarFacadeEJB.edit(entity);
    }
    
    
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Integer id){
    	Lugar lugar = lugarFacadeEJB.find(id); 
    	lugarFacadeEJB.remove(lugar);
    }
    
	

}
