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

import facade.GrupoHorarioFacade;
import model.GrupoHorario;

@Path("/grupos_horarios")
public class GrupoHorarioService {
	
	@EJB 
	GrupoHorarioFacade grupoHorarioFacadeEJB;
	
	Logger logger = Logger.getLogger(GrupoHorarioService.class.getName());
	
	@GET
	@Produces({"application/xml", "application/json"})
	public List<GrupoHorario> findAll(){
		return grupoHorarioFacadeEJB.findAll();
	}
	
	@GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public GrupoHorario find(@PathParam("id") Integer id) {
        return grupoHorarioFacadeEJB.find(id);
    }
	
	@POST
    @Consumes({"application/xml", "application/json"})
    public void create(GrupoHorario entity) {
		grupoHorarioFacadeEJB.create(entity);
    }

    
	@PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, GrupoHorario entity) {
    	entity.setGrupoHorarioId(id.intValue());
    	grupoHorarioFacadeEJB.edit(entity);
    }
    
    
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Integer id){
    	GrupoHorario grupoHorario = grupoHorarioFacadeEJB.find(id); 
    	grupoHorarioFacadeEJB.remove(grupoHorario);
    }
    
	

}
