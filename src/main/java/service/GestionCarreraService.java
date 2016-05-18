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

import com.mongodb.util.JSON;

import facade.RamoFacade;
import model.Ramo;
import facade.CarreraFacade;
import model.Carrera;

@Path("/gestion_carreras")
public class GestionCarreraService {
	
	@EJB 
	RamoFacade ramoFacadeEJB;
	
	@EJB
	CarreraFacade carreraFacadeEJB;
	
	Logger logger = Logger.getLogger(GestionCarreraService.class.getName());

	//REST Carrera
	
	@GET
	@Produces({"application/xml", "application/json"})
	public List<Carrera> findAllCarreras(){
	//public String findAllCarreras(){
	//	return JSON.serialize(carreraFacadeEJB.findAll());
		return carreraFacadeEJB.findAll();
	}
	
	@GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Carrera findCarrera(@PathParam("id") Integer id) {
        return carreraFacadeEJB.find(id);
    }
	
	@POST
    @Consumes({"application/xml", "application/json"})
    public void createCarrera(Carrera entity) {
		if (entity.getNombreCarrera() != null){
			boolean estado = true;
			List <Carrera> carreras = carreraFacadeEJB.findAll();
			for (int i = 0; i < carreras.size(); i++){
				if (carreras.get(i).getNombreCarrera().equals(entity.getNombreCarrera())){
					estado = false;
					break;
				}
			}
			if (estado){
				carreraFacadeEJB.create(entity);
			}
		}
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void editCarrera(@PathParam("id") Integer id, Carrera entity) {
    	entity.setCarreraId(id.intValue());
    	carreraFacadeEJB.edit(entity);
    }
    
    @DELETE
    @Path("{id}")
    public void deleteCarrera(@PathParam("id") Integer id){
    	Carrera carrera = carreraFacadeEJB.find(id);
    	int i;
    	
    	List<Ramo> carreras =carrera.getRamos();
    	for (i = 0; i < carreras.size() ; i++){
    		ramoFacadeEJB.remove(carreras.get(i));
    	}
    	carreraFacadeEJB.remove(carrera);
    }
    
    //REST Ramo
    
	@GET
    @Path("/ramos")
	@Produces({"application/xml", "application/json"})
	public List<Ramo> findAllRamos(){
		return ramoFacadeEJB.findAll();
	}
	
	@GET
    @Path("/ramos/{id}")
    @Produces({"application/xml", "application/json"})
    public Ramo findRamo(@PathParam("id") Integer id) {
        return ramoFacadeEJB.find(id);
    }
	
	@POST
    @Path("/carreras/{carrera_id}")
    @Consumes({"application/xml", "application/json"})
    public void createRamo(Ramo entity, @PathParam("carrera_id") Integer carrera_id) {
		if (entity.getNombreRamo() != null){
			if (carreraFacadeEJB.find(carrera_id) != null){
				entity.setCarrera(carreraFacadeEJB.find(carrera_id));
				ramoFacadeEJB.create(entity);
			}
		}
	}

    @PUT
    @Path("/ramo/{id}")
    @Consumes({"application/xml", "application/json"})
    public void editRamo(@PathParam("id") Integer id, Ramo entity) {
    	entity.setRamoId(id.intValue());
        ramoFacadeEJB.edit(entity);
    }
    
    @DELETE
    @Path("/ramo/{id}")
    public void deleteRamo(@PathParam("id") Integer id){
    	Ramo ramo = ramoFacadeEJB.find(id); 
    	ramoFacadeEJB.remove(ramo);
    }
}
