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
import facade.CarreraFacade;
import model.Carrera;
import adapters.ListSerializer;

@Path("/gestion_carreras")
public class GestionCarreraService {
	
	@EJB 
	RamoFacade ramoFacadeEJB;
	
	@EJB
	CarreraFacade carreraFacadeEJB;
	
	
	Logger logger = Logger.getLogger(GestionCarreraService.class.getName());

	//REST Carrera
	
	@GET
	@Produces({"application/json"})
	public String findAllCarreras(){
	//public String findAllCarreras(){
	//	return JSON.serialize(carreraFacadeEJB.findAll());
		List<Carrera> carreras = carreraFacadeEJB.findAll(); 
	//	ArrayList<Carrera>  carrerasAsArray = new ArrayList<>(carreras.size());
	//	carrerasAsArray = (ArrayList<Carrera>) carreras;
		ListSerializer serializer = new ListSerializer();
		return serializer.CarreraListSerializer(carreras);
	}
	
	@GET
    @Path("{id}")
    @Produces({"application/json"})
    public String findCarrera(@PathParam("id") Integer id) {
		ListSerializer serializer = new ListSerializer();
		return serializer.CarreraSerializer(carreraFacadeEJB.find(id));
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
	@Produces({"application/json"})
	//public List<Ramo> findAllRamos(){
	public String findAllRamos(){
		ListSerializer serializer = new ListSerializer();
		//JSONObject json = new JSONObject(serializer.RamoListSerializer(ramoFacadeEJB.findAll()));
		//return JSON.serialize(serializer.RamoListSerializer(ramoFacadeEJB.findAll()));
		return serializer.RamoListSerializer(ramoFacadeEJB.findAll());
		//return ramoFacadeEJB.findAll();
	}
	
    
    /*
    @GET
    @Path("/ramos")
	@Produces({"application/json"})
	//public List<Ramo> findAllRamos(){
	public ArrayList<Ramo> findAllRamos(){
    	List<Ramo> ramosLista = ramoFacadeEJB.findAll();
    	ArrayList<Ramo> ramos = new ArrayList<Ramo>();
    	for (int i = 0; i<ramosLista.size(); i++){
    		ramos.add(ramosLista.get(i));
    	}
		return ramos;
	}
	*/
    
	@GET
    @Path("/ramos/{id}")
    @Produces({"application/json"})
    public String findRamo(@PathParam("id") Integer id) {
		ListSerializer serializer = new ListSerializer();
		String result = serializer.RamoSerializer(ramoFacadeEJB.find(id));
		return result;
		//return ramoFacadeEJB.find(id);
    }
	
	//Verificar cambio para otros services.
	
	@POST
    @Path("/carreras/{carrera_id}")
    @Consumes({"application/xml", "application/json"})
    public void createRamo(Ramo entity, @PathParam("carrera_id") Integer carrera_id) {
		if (entity.getNombreRamo() != null){
			Carrera carrera = carreraFacadeEJB.find(carrera_id);
			if (carrera != null){
				entity.setCarrera(carrera);
				//carrera.addRamo(entity);
				//carreraFacadeEJB.edit(carrera);
				ramoFacadeEJB.create(entity);
			}
		}
	}

    @PUT
    @Path("/ramo/{id}")
    @Consumes({"application/xml", "application/json"})
    public void editRamo(@PathParam("id") Integer id, Ramo entity) {
    	if (entity.getNombreRamo() != null || ramoFacadeEJB.find(id) != null){
    		entity.setRamoId(id.intValue());
    		entity.setCarrera(ramoFacadeEJB.find(id).getCarrera());
    		ramoFacadeEJB.edit(entity);
    	}
    }
    
    @DELETE
    @Path("/ramo/{id}")
    public void deleteRamo(@PathParam("id") Integer id){
    	Ramo ramo = ramoFacadeEJB.find(id); 
    	ramoFacadeEJB.remove(ramo);
    }
    
}
