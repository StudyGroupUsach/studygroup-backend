package service;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.util.JSON;

import model.Ramo;
import model.Usuario;
import facade.RamoFacade;
import facade.CarreraFacade;
import facade.UsuarioFacade;
import mongo.MongoEJB;

@Path("/gestion_relacion_usuarios")
public class GestionRelacionUsuariosService {
	
	@EJB 
	UsuarioFacade usuarioFacadeEJB;
	
	@EJB
	RamoFacade ramoFacadeEJB;
	
	@EJB
	CarreraFacade carreraFacadeEJB;
	
	@EJB
	MongoEJB mongoEJB;
	
	//Personas con las que puede hacer un grupo, segun su listado de ramos de preferencia
	
	@GET
	@Path("/grupo_estudio/{id}")
	@Produces({"application/json"})
	public String getusuariosConLosQueHacerGrupo(@PathParam("id") Integer id){
		Usuario usuario = usuarioFacadeEJB.find(id);
		if (usuario != null){
			MongoClient mongoClient = mongoEJB.getMongoClient();
			MongoDatabase database = mongoClient.getDatabase("mongostudygroup");
			MongoCollection<Document> collection = database.getCollection("usuario.preferencias");

			List<Document> foundDocument = collection.find(Filters.eq("usuarioId",id+"")).into(new ArrayList<Document>());
			
			if (!foundDocument.isEmpty()){
				Document doc = foundDocument.get(0);
//				return "{ \"hell\": \"yeah\"}";
				List <Ramo> aux = ramoFacadeEJB.findAll();
				//doc.get("ramo", aux.getClass());
				Object ob = doc.get("ramo");
				ArrayList<Ramo> ramos = (ArrayList<Ramo>)ob;
				
				
				return JSON.serialize(ramos.get(0));
				//return doc.get("ramo",aux.getClass()).toString();
			}
		}
		return "[ ]";
	}

	//Historial de usuarios con los que se ha visto un usuario en su ultimo grupo
	
	@GET
	@Path("/{id}")
    @Produces({"application/json"})
	public String getRamosSeleccionados(@PathParam("id") Integer id){
		MongoClient mongoClient = mongoEJB.getMongoClient();
		MongoDatabase database = mongoClient.getDatabase("mongostudygroup");
		MongoCollection<Document> collection = database.getCollection("usuario.previosEncuentros");
		List<Document> foundDocument = collection.find(Filters.eq("usuarioId",id+"")).into(new ArrayList<Document>());
		mongoClient.close();
		return JSON.serialize(foundDocument);
	}
	
	@POST
	@Path("/{id}")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/json"})
	public String addRamosSeleccionados(List<Usuario> entity,@PathParam("id") Integer id){
		Usuario usuario = usuarioFacadeEJB.find(id);
		if (usuario != null){
			MongoClient mongoClient = mongoEJB.getMongoClient();
			MongoDatabase database = mongoClient.getDatabase("mongostudygroup");
			MongoCollection<Document> collection = database.getCollection("usuario.previosEncuentros");
			List<Document> foundDocument = collection.find(Filters.eq("usuarioId",id+"")).into(new ArrayList<Document>());
			//if (foundDocument.isEmpty()){ //No se habilitara actualizacion, solo POST

			Document doc = new Document ("usuarioId", id+"");
			
			List<Document> docUsuarios = new ArrayList<Document>();
			for (int i = 0; i<entity.size();i++){
				Usuario usuarioEntity = entity.get(i);
				if (usuarioEntity != null){
					if(usuarioEntity.getCarrera() != null){
						Document aux = new Document ("nombre", usuarioEntity.getNombre())
								.append("usuarioId", usuarioEntity.getUsuarioId()+"")
								.append("apellidos", usuarioEntity.getApellidos())
								.append("mail", usuarioEntity.getMail());
						docUsuarios.add(aux);
					}
				}
			//}
			doc.append("ramo", docUsuarios);
			//collection.insertOne(doc);
			collection.findOneAndReplace(Filters.eq("usuarioId",id+""), doc);
			mongoClient.close();
			return JSON.serialize(doc);
			}
		}
		return "[ ]";
	}
	
    @DELETE
	@Path("/{id}")
    @Produces({"application/json"})
	public String deleteRamosSeleccionados(@PathParam("id") Integer id){
    	if (usuarioFacadeEJB.find(id) == null){
    		MongoClient mongoClient = mongoEJB.getMongoClient();
			MongoDatabase database = mongoClient.getDatabase("mongostudygroup");
			MongoCollection<Document> collection = database.getCollection("usuario.previosEncuentros");
			collection.findOneAndDelete(Filters.eq("usuarioId",id+""));
			mongoClient.close();
			return "{\"historialUsuarioEliminado\":\"" + "true" +  "\"}";
    	}
		return "{\"historialUsuarioEliminado\":\"" + "false" +  "\"}";
	}
	
}
