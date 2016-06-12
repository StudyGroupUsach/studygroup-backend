package service;

import java.util.List;
import java.util.logging.Logger;
import java.sql.Time;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.sql.Date;
import java.util.Date;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import adapters.ListSerializer;
import facade.GrupoTemporalFacade;
import facade.LugarFacade;
import facade.RamoFacade;
import facade.UsuarioFacade;
import model.GrupoTemporal;
import model.Lugar;
import model.Ramo;
import model.Usuario;
import mongo.MongoEJB;

@Path("/grupos_temporales")
public class GrupoTemporalService {
	
	@EJB 
	GrupoTemporalFacade grupoTemporalFacadeEJB;
	
	@EJB
	UsuarioFacade usuarioFacadeEJB;
	
	@EJB
	RamoFacade ramoFacadeEJB;
	
	@EJB
	LugarFacade lugarFacadeEJB;
	
	@EJB
	MongoEJB mongoEJB;
	
	Logger logger = Logger.getLogger(GrupoTemporalService.class.getName());
	
	@GET
	@Produces({"application/json"})
	public String findAll(){
		ListSerializer serializer = new ListSerializer();
		String result = serializer.GrupoTemporalListSerializer(grupoTemporalFacadeEJB.findAll());
		return result;
	}
	
	@GET
    @Path("{id}")
    @Produces({"application/json"})
    public String find(@PathParam("id") Integer id) {
		ListSerializer serializer = new ListSerializer();
		String result = serializer.GrupoTemporalSerializer(grupoTemporalFacadeEJB.find(id));
        return result;
    }
	
	@POST
	@Path("{id}")
    @Consumes({"application/xml", "application/json"})
//	@Produces("text/plain")
    public void create(@PathParam("id") Integer id, String rawJson) {
		Usuario usuario = usuarioFacadeEJB.find(id);
		if (usuario != null){
			GrupoTemporal entity = new GrupoTemporal();
			entity.setUsuario(usuario);
			Document buff = Document.parse(rawJson);
			Integer ramoId = (Integer) buff.get("ramoId");
			if (ramoId != null){
				Ramo ramo = ramoFacadeEJB.find(ramoId);
				if (ramo != null) {
					entity.setRamo(ramo);
					Integer idLugar = buff.getInteger("idLugar");
					if (idLugar != null){
						Lugar lugar = lugarFacadeEJB.find(idLugar);
						if(lugar != null){
							entity.setLugar(lugar);
							
							//DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
							Date date = new Date();
							java.sql.Date sqlDate = new java.sql.Date(date.getTime());
							//String current = dateFormat.format(date);
							//long currentTime = System.currentTimeMillis();
							Time time = new Time(3600000);
							entity.setDuracionTemporal(time);
							entity.setInicioTemporal(sqlDate);
							entity.setDescripcionTemporal(buff.getString("descripcionTemporal"));
							grupoTemporalFacadeEJB.create(entity);
							
							MongoClient mongoClient = mongoEJB.getMongoClient();
							MongoDatabase database = mongoClient.getDatabase("mongostudygroup");
							MongoCollection<Document> collection = database.getCollection("grupoTemporal.integrantes");
							
							ListSerializer listSerializer = new ListSerializer();
							
							Document docUsuario = Document.parse(listSerializer.UsuarioSerializer(usuario));
							List <Document> docUsuarios = new ArrayList<Document>();
							docUsuarios.add(docUsuario);
							Document doc = new Document();
							doc.append("grupoTemporalId",entity.getGrupoTemporalId()+"").append("usuario", docUsuarios);
							
							collection.insertOne(doc);
							
						}
					}
				}
				
			}

		}
	}
	
	//Cambiar la locacion
	@PUT
    @Path("/cambiar_locacion/{id}")
    @Consumes({"application/xml", "application/json"})
    public void editLocacionDeGrupoTemporal(@PathParam("id") Integer id, Lugar entity) {
		GrupoTemporal grupoTemporal = grupoTemporalFacadeEJB.find(id);
		if (grupoTemporal != null){
			if (entity.getIdLugar() != 0){ 
				Lugar lugar = lugarFacadeEJB.find(entity.getIdLugar());
				grupoTemporal.setLugar(lugar);
				grupoTemporalFacadeEJB.edit(grupoTemporal);
			}
		}
    }

	//Id del grupo temporal ya en la BDD, entity son los cambios propuestos

	@PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, GrupoTemporal entity) {
		GrupoTemporal grupoTemporal = grupoTemporalFacadeEJB.find(id);
		if (entity.getDescripcionTemporal() != null){
			grupoTemporal.setDescripcionTemporal(entity.getDescripcionTemporal());
		}
		if(entity.getRamo() != null){
			if (entity.getRamo().getRamoId() != 0){
				grupoTemporal.setRamo(ramoFacadeEJB.find(entity.getRamo().getRamoId()));
			}
		}
		if(entity.getUsuario() != null){
			if (entity.getUsuario().getUsuarioId() != 0){
				grupoTemporal.setUsuario(usuarioFacadeEJB.find(entity.getUsuario().getUsuarioId()));
			}
		}
    	grupoTemporalFacadeEJB.edit(grupoTemporal);
    }
    //El id es del grupo temporal
	@PUT
	@Path("/agregar_integrantes/{id}")
	@Consumes({"application/xml", "application/json"})
	public void agregarIntegrantes(@PathParam("id") Integer id, List<Usuario> usuarios){
		if (usuarios != null){
			int size = usuarios.size();
			ListSerializer listSerializer = new ListSerializer();
			MongoClient mongoClient = mongoEJB.getMongoClient();
			MongoDatabase database = mongoClient.getDatabase("mongostudygroup");
			MongoCollection<Document> collection = database.getCollection("grupoTemporal.integrantes");
			List<Document> foundDocument = collection.find(Filters.eq("grupoTemporalId",id+"")).into(new ArrayList<Document>());
			Document doc = foundDocument.get(0);
			
			if (doc != null){
				Document newDoc = new Document().append("grupoTemporalId",id+"");
				Document aux = new Document();
				List<Document> docUsuarios = (List<Document>) doc.get("usuario");
				
				for (int i = 0; i < size; i++){
					Usuario usuario = usuarios.get(i);
					if (usuario.getUsuarioId() != 0) {
						usuario = usuarioFacadeEJB.find(usuario.getUsuarioId());
						if (usuario != null){
							int test = 0;
							for (int j = 0; j < docUsuarios.size(); j++){
								if (docUsuarios.get(j).getString("usuarioId").equals(""+(usuario.getUsuarioId()))){
									test = 1;
									break;
								}
							}if (test == 0){
								aux = Document.parse(listSerializer.UsuarioSerializer(usuario));
							} else{
								continue;
							}
							docUsuarios.add(aux);
						}
					}
			
				}
				
				newDoc.append("usuario", docUsuarios);
				collection.findOneAndReplace(Filters.eq("grupoTemporalId", id+""), doc);
			}
		}
	}
    
    //Elimina grupo de mongo y mysql //FALTA AGREGAR LISTADO DE USUARIOS A LISTADO DE ULTIMOS USUARIOS
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Integer id){
    	GrupoTemporal grupoTemporal = grupoTemporalFacadeEJB.find(id); 
		MongoClient mongoClient = mongoEJB.getMongoClient();
		MongoDatabase database = mongoClient.getDatabase("mongostudygroup");
		MongoCollection<Document> collection = database.getCollection("grupoTemporal.integrantes");
		collection.findOneAndDelete(Filters.eq("grupoTemporalId",id+""));
    	grupoTemporalFacadeEJB.remove(grupoTemporal);
    }
    
    //Eliminar solo un usuario por su id, el id del link es el del grupoTemporal
    @DELETE
    @Path("/eliminar_usuario/{id}")
    public void deleteUsuarioDeGrupo(@PathParam("id") Integer id, String rawJson){
    	String usuarioId = Document.parse(rawJson).getInteger("usuarioId")+""; //"usuarioId": id
    	MongoClient mongoClient = mongoEJB.getMongoClient();
		MongoDatabase database = mongoClient.getDatabase("mongostudygroup");
		MongoCollection<Document> collection = database.getCollection("grupoTemporal.integrantes");
		List<Document> foundDocument = collection.find(Filters.eq("grupoTemporalId",id+"")).into(new ArrayList<Document>());
		Document doc = foundDocument.get(0);
		
		if (doc != null){
			List<Document> docUsuarios = (List<Document>) doc.get("usuario");
			for (int j = 1; j < docUsuarios.size(); j++){ // Se puede cambiar a 0 y asi el primero si se puede eliminar.
				if (docUsuarios.get(j).getString("usuarioId").equals(usuarioId)){
					docUsuarios.remove(j);
					break;
				}
			}
			
			Document newDoc = new Document().append("grupoTemporalId",id+"");
			newDoc.append("usuario", docUsuarios);
			collection.findOneAndReplace(Filters.eq("grupoTemporalId", id+""), doc);
			
		}
    }
    
	

}
