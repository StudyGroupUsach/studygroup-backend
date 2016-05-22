package service;

import java.util.ArrayList;
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

//Dependencias para Mongo

import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.util.JSON;

//import com.mongodb.util.JSON;

import adapters.ListSerializer;
import facade.UsuarioFacade;
import model.Ramo;
import model.Usuario;
import facade.CarreraFacade;
import mongo.MongoEJB;

//Dependencias adicionales para hash MD5 en la contrasena
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Path("/usuarios")
public class UsuarioService {
	
	@EJB 
	UsuarioFacade usuarioFacadeEJB;
	
	@EJB
	CarreraFacade carreraFacadeEJB;
	
	@EJB
	MongoEJB mongoEJB;
	
	Logger logger = Logger.getLogger(UsuarioService.class.getName());
	
	@GET
	@Produces({"application/json"})
	//public List<Usuario> findAll(){
	public String findAll(){
		ListSerializer serializer = new ListSerializer();
		String result = serializer.UsuarioListSerializer(usuarioFacadeEJB.findAll());
		return result;
		//return usuarioFacadeEJB.findAll();
	}
	
	@GET
    @Path("{id}")
    @Produces({"application/json"})
    //public Usuario find(@PathParam("id") Integer id) {
	public String find(@PathParam("id") Integer id) {
		ListSerializer serializer = new ListSerializer();
		String result = serializer.UsuarioSerializer(usuarioFacadeEJB.find(id));
		return result;
        //return usuarioFacadeEJB.find(id);
    }
	
	@POST
    @Consumes({"application/xml", "application/json"})
	@Produces({"application/json"})
	//@Produces("text/plain")
    public String create(Usuario entity) {
		//
		boolean mailExist = false;
		
		//Get Email
		String mail = entity.getMail();
		//Separar mail por "@"
		String[]part = mail.split("@");
		
		//Obtener pass
		String pass = entity.getPass();
		
		//Verificar si existe una cuenta de Email igual a esta
		List<Usuario> usuarios = usuarioFacadeEJB.findAll();
		
		for(int i = 0; i < usuarios.size();i++){
			if(usuarios.get(i).getMail() != null){
				if(usuarios.get(i).getMail().equals(mail))
				{
					mailExist = true;
					i = usuarios.size();
				}
			}
		}
		
		if(mailExist)
		{
			//print something
		}
		else if(entity.getApellidos() == null || entity.getMail() == null 
				|| entity.getNombre() == null || entity.getPass() == null 
				|| entity.getCarrera() == null){
			//logger.finer("Null, user:"+entity.getNombre() +" -->Mail:"+entity.getMail());
		}
		else if(entity.getCarrera().getCarreraId() == 0){
			
		}
		else if(part.length == 2){
			//Verificar que cumple con el formato @usach.cl
			if(part[1].equals("usach.cl")){
				//Anadimos una password
				entity.setPass(md5(pass));
				
				//Para agregar la carrera
				int carreraId = entity.getCarrera().getCarreraId();
				if (carreraFacadeEJB.find(carreraId) != null){
					entity.setCarrera(carreraFacadeEJB.find(entity.getCarrera().getCarreraId()));
					
					//Crear usuario a la Base de datos
					usuarioFacadeEJB.create(entity);
					//return JSON.serialize(true);
					return "{ \"usuarioAgregado\":\"true\"}";
				}
			}
			
		}
		
		//return JSON.serialize(false);
		return "{ \"usuarioAgregado\":\"false\"}";		
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Usuario entity) {
    	
    	Usuario original = usuarioFacadeEJB.find(id);
    	
    	entity.setUsuarioId(id.intValue());
    	entity.setMail(original.getMail());
    	entity.setApellidos(original.getApellidos());
    	entity.setNombre(original.getNombre());
    	entity.setGrupoTemporals(original.getGrupoTemporals());
    	
    	if(entity.getDescripcion() == null){
    		entity.setDescripcion(original.getDescripcion());
    	}
    	
    	if(entity.getNumeroMovil() == null){
    		entity.setNumeroMovil(original.getNumeroMovil());
    	}
    	
    	if(entity.getPass() == null){ //Pass Null, es decir, no hay nuevo pass
    		entity.setPass(original.getPass());
    		
    	}else if( ( md5(entity.getPass()).equals(original.getPass()) ) ){ //si el pass es igual al anterior
    		entity.setPass(original.getPass());
    	}else//Pass es distinto al pass anterior
    	{
    		entity.setPass(md5(entity.getPass()));
    	}
    	if (entity.getCarrera() == null){
    		entity.setCarrera(original.getCarrera());
    	} else 
    	{
    		entity.setCarrera(carreraFacadeEJB.find(entity.getCarrera().getCarreraId()));
    	}
    	
        usuarioFacadeEJB.edit(entity);
        
    }
    
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Integer id){
    	Usuario usuario = usuarioFacadeEJB.find(id); 
    	usuarioFacadeEJB.remove(usuario);
    }
    
    private String md5(String input) {
        
        String md5 = null;
         
        if(null == input) return null;
         
        try {
             
        //Create MessageDigest object for MD5
        MessageDigest digest = MessageDigest.getInstance("MD5");
         
        //Update input string in message digest
        digest.update(input.getBytes(), 0, input.length());
 
        //Converts message digest value in base 16 (hex) 
        md5 = new BigInteger(1, digest.digest()).toString(16);
 
        } catch (NoSuchAlgorithmException e) {
 
            e.printStackTrace();
        }
        return md5;
    }
    
    @GET
	@Path("/{id}/ramos")
    @Produces({"application/json"})
	public String ramosSeleccionados(@PathParam("id") Integer id){
		MongoClient mongoClient = mongoEJB.getMongoClient();
		MongoDatabase database = mongoClient.getDatabase("mongostudygroup");
		MongoCollection<Document> collection = database.getCollection("usuario.preferencias");
		List<Document> foundDocument = collection.find(Filters.eq("usuarioId",id+"")).into(new ArrayList<Document>());
		mongoClient.close();
		return JSON.serialize(foundDocument);
	}
    
    @GET
	@Path("/ramos")
    @Produces({"application/json"})
	public String ramosSeleccionadosUsuarios(@PathParam("id") Integer id){
		MongoClient mongoClient = mongoEJB.getMongoClient();
		MongoDatabase database = mongoClient.getDatabase("mongostudygroup");
		MongoCollection<Document> collection = database.getCollection("usuario.preferencias");
		List<Document> foundDocument = collection.find().into(new ArrayList<Document>());
		mongoClient.close();
		return JSON.serialize(foundDocument);
	}          

    
    @POST
	@Path("/{id}/ramos")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/json"})
	public String addRamosSeleccionados(List<Ramo> entity,@PathParam("id") Integer id){
		Usuario usuario = usuarioFacadeEJB.find(id);
		if (usuario != null){
			MongoClient mongoClient = mongoEJB.getMongoClient();
			MongoDatabase database = mongoClient.getDatabase("mongostudygroup");
			MongoCollection<Document> collection = database.getCollection("usuario.preferencias");
			List<Document> foundDocument = collection.find(Filters.eq("usuarioId",id+"")).into(new ArrayList<Document>());
			if (foundDocument.isEmpty()){

			Document doc = new Document ("usuarioId", id+"");
			
			List<Document> docRamos = new ArrayList<Document>();
			for (int i = 0; i<entity.size();i++){
				Ramo ramo = entity.get(i);
				if (ramo != null){
					if(ramo.getCarrera() != null){
						Document aux = new Document ("nombreRamo", ramo.getNombreRamo())
								.append("ramoId", ramo.getRamoId()+"")
								.append("carreraId", ramo.getCarrera().getCarreraId()+"")
								.append("nombreCarrera", ramo.getCarrera().getNombreCarrera());
						docRamos.add(aux);
					}
				}
			}
			doc.append("ramo", docRamos);
			collection.insertOne(doc);
			mongoClient.close();
			return JSON.serialize(doc);
			}
		}
		return "[ ]";
	}
    
    @PUT
	@Path("/{id}/ramos")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/json"})
	public String editRamosSeleccionados(List<Ramo> entity,@PathParam("id") Integer id){
		Usuario usuario = usuarioFacadeEJB.find(id);
		if (usuario != null){
			MongoClient mongoClient = mongoEJB.getMongoClient();
			MongoDatabase database = mongoClient.getDatabase("mongostudygroup");
			MongoCollection<Document> collection = database.getCollection("usuario.preferencias");
			List<Document> foundDocument = collection.find(Filters.eq("usuarioId",id+"")).into(new ArrayList<Document>());
			if (!foundDocument.isEmpty()){

			Document doc = new Document ("usuarioId", id+"");
			
			List<Document> docRamos = new ArrayList<Document>();
			for (int i = 0; i<entity.size();i++){
				Ramo ramo = entity.get(i);
				if (ramo != null){
					if(ramo.getCarrera() != null){
						Document aux = new Document ("nombreRamo", ramo.getNombreRamo())
								.append("ramoId", ramo.getRamoId()+"")
								.append("carreraId", ramo.getCarrera().getCarreraId()+"")
								.append("nombreCarrera", ramo.getCarrera().getNombreCarrera());
						docRamos.add(aux);
					}
				}
			}
			doc.append("ramo", docRamos);
			collection.findOneAndReplace(Filters.eq("usuarioId", id+""), doc);
			mongoClient.close();
			return JSON.serialize(doc);
			}
		}
		return "[ ]";
	}
    
    @DELETE
	@Path("/{id}/ramos")
    @Produces({"application/json"})
	public String deleteRamosSeleccionados(@PathParam("id") Integer id){
    	if (usuarioFacadeEJB.find(id) == null){
    		MongoClient mongoClient = mongoEJB.getMongoClient();
			MongoDatabase database = mongoClient.getDatabase("mongostudygroup");
			MongoCollection<Document> collection = database.getCollection("usuario.preferencias");
			collection.findOneAndDelete(Filters.eq("usuarioId",id+""));
			mongoClient.close();
			return "{\"historialUsuarioEliminado\":\"" + "true" +  "\"}";
    	}
		return "{\"historialUsuarioEliminado\":\"" + "false" +  "\"}";
	}
    
    
}