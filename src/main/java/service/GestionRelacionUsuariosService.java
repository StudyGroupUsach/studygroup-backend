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

import adapters.ListSerializer;
import model.GrupoTemporal;
import model.Ramo;
import model.Usuario;
import facade.RamoFacade;
import facade.CarreraFacade;
import facade.GrupoTemporalFacade;
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
	GrupoTemporalFacade grupoTemporalFacadeEJB;
	
	@EJB
	MongoEJB mongoEJB;
	
	//Personas con las que puede hacer un grupo, segun su listado de ramos de preferencia
	
	// ID de usuario buscar los usuarios con los que puede hacer grupo
	@GET
	@Path("/grupo_estudio_por_usuarios/{id}/{ramoId}")
	//@Consumes({"application/xml", "application/json"})
	@Produces({"application/json"})
	public String usuariosParaEstudioPorUsuarios(@PathParam("id") Integer id, @PathParam("ramoId") Integer ramoId){
		Usuario usuario = usuarioFacadeEJB.find(id);
		//Document buff = Document.parse(rawJson);
		if (usuario != null){
			//Document buff = Document.parse(rawJson);
			//Integer ramoId = buff.getInteger("ramoId");
			if (ramoId != null){
				MongoClient mongoClient = mongoEJB.getMongoClient();
				MongoDatabase database = mongoClient.getDatabase("mongostudygroup");
				MongoCollection<Document> collection = database.getCollection("usuario.preferencias");
				
				List<Document> foundDocuments = collection.find(Filters.and(Filters.ne("usuarioId", id+""),(Filters.in("ramo.ramoId",ramoId+"")))).into(new ArrayList<Document>());
				
				return JSON.serialize(foundDocuments);
				
			}
		}
		return "[ ]";
		//return "[{\"itdidnt\":\"work\" }]";
	}
	
	@GET
	@Path("/grupo_estudio_por_grupos/{id}/{ramoId}")
	//@Consumes({"application/xml", "application/json"})
	@Produces({"application/json"})
	public String usuariosParaEstudioPorGrupos(@PathParam("id") Integer id, @PathParam("ramoId") Integer ramoId){
		Usuario usuario = usuarioFacadeEJB.find(id);
		//Document buff = Document.parse(rawJson);
		if (usuario != null){
			//Document buff = Document.parse(rawJson);
			//Integer ramoId = buff.getInteger("ramoId");
			if (ramoId != null){
				
				List<GrupoTemporal> grupos = getGroups(ramoId);
				ListSerializer listSerializer = new ListSerializer();
				String result = listSerializer.GrupoTemporalListSerializer(grupos);
				return result;
				
			}
		}
		return "[ ]";
	}
	
	private List<GrupoTemporal> getGroups(Integer ramoId)
	{
		List<GrupoTemporal> allTemporaryGroups = grupoTemporalFacadeEJB.findAll();
		List<GrupoTemporal> groupFound = new ArrayList<GrupoTemporal>();

		for(int i = 0;i < allTemporaryGroups.size();i++){
			if(allTemporaryGroups.get(i) != null ){
				if(allTemporaryGroups.get(i).getRamo() != null){
					if (allTemporaryGroups.get(i).getRamo().getRamoId() == ramoId){
						groupFound.add(allTemporaryGroups.get(i));
					}
				}

			}

		}
		return groupFound;
	}
	
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
				//doc.get("ramo", aux.getClass());
				Object ob = doc.get("ramo");
				ArrayList<Ramo> ramos = (ArrayList<Ramo>)ob;
				foundDocument = collection.find(Filters.in("ramo", ramos)).into(new ArrayList<Document>());
				List<Document> usuarios = new ArrayList<Document>();
				for (int i = 0; i < foundDocument.size(); i++){
					Document aux = foundDocument.get(i);
					if (!aux.get("usuarioId").equals(id+"")){
						usuarios.add(aux);
					}
				}
				Document result = new Document("usuarioId", id).append("usuario", usuarios);
				
				//return JSON.serialize(ramos.get(0));
				//mongoClient.close();
				return JSON.serialize(result);
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
		//mongoClient.close();
		return JSON.serialize(foundDocument);
	}
	
	@POST
	@Path("/encuentros_previos/{id}")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/json"})
	public String addListadoDeEncuentrosPrevios(List<Usuario> entity,@PathParam("id") Integer id){
		Usuario usuario = usuarioFacadeEJB.find(id);
		if (usuario != null){
			MongoClient mongoClient = mongoEJB.getMongoClient();
			MongoDatabase database = mongoClient.getDatabase("mongostudygroup");
			MongoCollection<Document> collection = database.getCollection("usuario.previosEncuentros");
			//List<Document> foundDocument = collection.find(Filters.eq("usuarioId",id+"")).into(new ArrayList<Document>());
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
			}
			doc.append("usuario", docUsuarios);
			//collection.insertOne(doc);
			collection.findOneAndReplace(Filters.eq("usuarioId",id+""), doc);
			//mongoClient.close();
			return JSON.serialize(doc);
		}
		return "[ ]";
	}
	
	@POST
	@Path("/{id}")
    @Consumes({"application/xml", "application/json"})
    @Produces({"application/json"})
	public String addRamos(List<Ramo> entity,@PathParam("id") Integer id){
		Usuario usuario = usuarioFacadeEJB.find(id);
		if (usuario != null){
			MongoClient mongoClient = mongoEJB.getMongoClient();
			MongoDatabase database = mongoClient.getDatabase("mongostudygroup");
			MongoCollection<Document> collection = database.getCollection("usuario.preferencias");
			//List<Document> foundDocument = collection.find(Filters.eq("usuarioId",id+"")).into(new ArrayList<Document>());
			//if (foundDocument.isEmpty()){ //No se habilitara actualizacion, solo POST

			Document doc = new Document ("usuarioId", id+"");
			
			List<Document> docRamos = new ArrayList<Document>();
			for (int i = 0; i<entity.size();i++){
				Ramo ramoEntity = entity.get(i);
				if (ramoEntity != null){
					if(ramoEntity.getCarrera() != null){
						Document aux = new Document ("nombreRamo", ramoEntity.getNombreRamo())
								.append("ramoId", ramoEntity.getRamoId()+"")
								.append("carreraId", ramoEntity.getCarrera().getCarreraId()+"")
								.append("nombreCarrera", ramoEntity.getCarrera().getNombreCarrera());
						docRamos.add(aux);
					}
				}
			}
			doc.append("ramo", docRamos);
			//
			//collection.findOneAndDelete(Filters.eq("usuarioId",id+""));
			collection.findOneAndDelete(Filters.in("usuarioId",id+""));
			collection.insertOne(doc);
			//mongoClient.close();
			return JSON.serialize(doc);
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
			//mongoClient.close();
			return "{\"historialUsuarioEliminado\":\"" + "true" +  "\"}";
    	}
		return "{\"historialUsuarioEliminado\":\"" + "false" +  "\"}";
	}
	
}
