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

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.util.JSON;

import adapters.ListSerializer;
import facade.PerfilAyudanteFacade;
import model.PerfilAyudante;
import model.Usuario;
import mongo.MongoEJB;
import facade.UsuarioFacade;

@Path("/perfiles_ayudantes")
public class PerfilAyudanteService {
	
	@EJB 
	PerfilAyudanteFacade perfilAyudanteFacadeEJB;
	
	@EJB
	UsuarioFacade usuarioFacadeEJB;
	
	@EJB
	MongoEJB mongoEJB;
	
	Logger logger = Logger.getLogger(PerfilAyudanteService.class.getName());
	
	@GET
	@Produces({"application/json"})
	public String findAll(){
		ListSerializer serializer = new ListSerializer();
		String result = serializer.PerfilAyudanteListSerializer(perfilAyudanteFacadeEJB.findAll());
		return result;
	}
	
    //id del perfil_usuario
	@GET
    @Path("{id}")
    @Produces({"application/json"})
    public String find(@PathParam("id") Integer id) {
		ListSerializer serializer = new ListSerializer();
		String result = serializer.PerfilAyudanteSerializer(perfilAyudanteFacadeEJB.find(id));
		return result;
    }
	
	// id del usuario, solo cuando esta pagado.
	@POST
	@Path("{id}")
    @Consumes({"application/xml", "application/json"})
	@Produces({"application/json"})
    public String create(@PathParam("id") Integer id, PerfilAyudante entity) {
		Usuario usuario = usuarioFacadeEJB.find(id);
		if (usuario != null){
			if (encontrarPorIdDeUsuario(id) == null){
				if (entity.getEstado().equals("Pagado")){
					entity.setUsuario(usuario);
					entity.setValoracionPromedio(0);
					perfilAyudanteFacadeEJB.create(entity);
					
					entity = encontrarPorIdDeUsuario(id);
					MongoClient mongoClient = mongoEJB.getMongoClient();
					MongoDatabase database = mongoClient.getDatabase("mongostudygroup");
					MongoCollection<Document> collection = database.getCollection("perfilAyudante.valoraciones");
				
					Document foundDocument = collection.find(Filters.eq("perfilAyudanteId",id+"")).first();
					if (foundDocument == null){
						Document doc = new Document().append("perfilAyudanteId", entity.getPerfilAyudanteId()+"")
								.append("usuarioId", id+"")
								.append("valoracion", new ArrayList<Document>());
						collection.insertOne(doc);
						return JSON.serialize(doc);
					}
					 
				}
			}
		}
		return "[ ]";
    }

    //id del perfil_usuario
	//RawJson = {"valoracionAyudante":integer, "usuarioId":integer}
	@PUT
    @Path("/valoraciones/{id}")
    @Consumes({"application/xml", "application/json"})
    public void agregarValoracion(@PathParam("id") Integer id, String RawJson) {
		PerfilAyudante perfilAyudante = perfilAyudanteFacadeEJB.find(id);
    	if (perfilAyudante != null){
    		MongoClient mongoClient = mongoEJB.getMongoClient();
			MongoDatabase database = mongoClient.getDatabase("mongostudygroup");
			MongoCollection<Document> collection = database.getCollection("perfilAyudante.valoraciones");
			
			Document doc = collection.find(Filters.eq("perfilAyudanteId",id+"")).first();
			if (doc != null){
				Document valoracionNueva = Document.parse(RawJson);
				Integer valoracionAyudante = valoracionNueva.getInteger("valoracionAyudante");
				Integer usuarioId = valoracionNueva.getInteger("usuarioId");
				if (valoracionAyudante != null && usuarioId != null){
					// if doc.get("valoracion") instanceof java.util.ArrayList
					List <Document> docValoraciones = (ArrayList<Document>)doc.get("valoracion");
					int n = docValoraciones.size();
					int valoraciones = 0;
					boolean usuarioAunNoAgregado = true; 
					for (int i = 0; i< n; i++){
						Document docValoracion = docValoraciones.get(i);
						if (Integer.parseInt(docValoracion.getString("usuarioId")) != usuarioId){
							valoraciones += Integer.parseInt(docValoracion.getString("valoracionAyudante"));
						} else {
							docValoracion.put("valoracionAyudante", valoracionAyudante+"");
							valoraciones += valoracionAyudante;
							usuarioAunNoAgregado = false;
						}
					}
					if (usuarioAunNoAgregado){
						valoraciones = valoraciones + valoracionAyudante;
						n = n+1;
						Document nuevoUsuario = new Document()
								.append("usuarioId", usuarioId+"")
								.append("valoracionAyudante", valoracionAyudante+"");
						docValoraciones.add(nuevoUsuario);
					}
					perfilAyudante.setValoracionPromedio(valoraciones/n);
					perfilAyudanteFacadeEJB.edit(perfilAyudante);
					
					collection.findOneAndDelete(Filters.eq("perfilAyudanteId",id+""));
					doc.put("valoracion", docValoraciones);
					collection.insertOne(doc);
				}
				
				
				
			}
    		
    		
    	}
    }
	
	@PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void cambiarEstado(@PathParam("id") Integer id, PerfilAyudante entity) {
		PerfilAyudante perfilAyudante = perfilAyudanteFacadeEJB.find(id);
		if (perfilAyudante != null){
			if (entity.getEstado().equals("Pagado") || entity.getEstado().equals("Caducado")){
				perfilAyudante.setEstado(entity.getEstado());
				perfilAyudanteFacadeEJB.edit(perfilAyudante);
			}
		}
    	
    }
    
    //id del perfil_usuario    
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Integer id){
    	PerfilAyudante perfilAyudante = perfilAyudanteFacadeEJB.find(id);
    	
    	if (perfilAyudante != null){
    		MongoClient mongoClient = mongoEJB.getMongoClient();
			MongoDatabase database = mongoClient.getDatabase("mongostudygroup");
			MongoCollection<Document> collection = database.getCollection("perfilAyudante.valoraciones");
			
			collection.findOneAndDelete(Filters.eq("perfilAyudanteId",id+""));			
    		
    		perfilAyudanteFacadeEJB.remove(perfilAyudante);
    	}
    	
    	
    }
    
	private PerfilAyudante encontrarPorIdDeUsuario(Integer usuarioId){
		List <PerfilAyudante> perfilesAyudantes = perfilAyudanteFacadeEJB.findAll();
		for (int i = 0; i < perfilesAyudantes.size(); i++){
			PerfilAyudante perfilAyudante = perfilesAyudantes.get(i);
			Usuario usuario = perfilAyudante.getUsuario();
			if (usuario != null){
				if (usuario.getUsuarioId() == usuarioId){
					return perfilAyudante;
				}
			}
		}
		return null;
	}
	@GET
    @Path("horario_libre/{id}")
	@Produces({"application/json"})
    public String getHorarioLibre(@PathParam("id") Integer id){
		if (perfilAyudanteFacadeEJB.find(id) != null){
				MongoClient mongoClient = mongoEJB.getMongoClient();
				MongoDatabase database = mongoClient.getDatabase("mongostudygroup");
				MongoCollection<Document> collection = database.getCollection("perfilAyudante.horariosLibres");
				Document doc = collection.find(Filters.eq("perfilAyudanteId", id+"")).first();
				if (doc != null){					
					return JSON.serialize(doc);
				}
			
		}
		return "[ ]";
	}
	
	
	@POST
    @Path("horario_libre/{id}")
	@Consumes({"application/xml", "application/json"})
    public void addHorarioLibre(@PathParam("id") Integer id, String rawJson){
		if (perfilAyudanteFacadeEJB.find(id) != null){
			Document horario = Document.parse(rawJson);
			String stringHorario = horario.getString("horario");
			if (stringHorario != null){
				MongoClient mongoClient = mongoEJB.getMongoClient();
				MongoDatabase database = mongoClient.getDatabase("mongostudygroup");
				MongoCollection<Document> collection = database.getCollection("perfilAyudante.horariosLibres");
				Document doc = collection.find(Filters.eq("perfilAyudanteId", id+"")).first();
				if (doc == null){
					Document newHorario = new Document().append("perfilAyudanteId", id+"").
							append("horario", stringHorario);
					collection.insertOne(newHorario);
				}
			}
			
		}
	}
	
	@PUT
    @Path("horario_libre/{id}")
	@Consumes({"application/xml", "application/json"})
    public void editHorarioLibre(@PathParam("id") Integer id, String rawJson){
		if (perfilAyudanteFacadeEJB.find(id) != null){
			Document horario = Document.parse(rawJson);
			String stringHorario = horario.getString("horario");
			if (stringHorario != null){
				MongoClient mongoClient = mongoEJB.getMongoClient();
				MongoDatabase database = mongoClient.getDatabase("mongostudygroup");
				MongoCollection<Document> collection = database.getCollection("perfilAyudante.horariosLibres");
				Document doc = collection.find(Filters.eq("perfilAyudanteId", id+"")).first();
				if (doc != null){
					Document newHorario = new Document().append("perfilAyudanteId", id+"").
							append("horario", stringHorario);
					collection.replaceOne(Filters.eq("perfilAyudanteId", id+""), newHorario);
				}
			}
			
		}
	}
    
	@DELETE
    @Path("horario_libre/{id}")
    public void removeHorarioLibre(@PathParam("id") Integer id){
		if (perfilAyudanteFacadeEJB.find(id) != null){
				MongoClient mongoClient = mongoEJB.getMongoClient();
				MongoDatabase database = mongoClient.getDatabase("mongostudygroup");
				MongoCollection<Document> collection = database.getCollection("perfilAyudante.horariosLibres");
				collection.findOneAndDelete(Filters.eq("perfilAyudanteId", id+""));			
		}
	}


}
