package service;

import java.util.ArrayList;
import java.util.Date;
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
import facade.GrupoHorarioFacade;
import facade.LugarFacade;
import facade.PerfilAyudanteFacade;
import facade.RamoFacade;
import facade.UsuarioFacade;
import model.GrupoHorario;
import model.Lugar;
import model.PerfilAyudante;
import model.Ramo;
import model.Usuario;
import mongo.MongoEJB;

@Path("/grupos_horarios")
public class GrupoHorarioService {
	
	@EJB 
	GrupoHorarioFacade grupoHorarioFacadeEJB;
	
	@EJB
	UsuarioFacade usuarioFacadeEJB;
	
	@EJB
	PerfilAyudanteFacade perfilAyudanteFacadeEJB;
	
	@EJB
	RamoFacade ramoFacadeEJB;
	
	@EJB
	LugarFacade lugarFacadeEJB;
	
	@EJB
	MongoEJB mongoEJB;
	
	Logger logger = Logger.getLogger(GrupoHorarioService.class.getName());
	
	//GET LISTADO DE USUARIOS
	// Id del grupo Horario
	@GET
	@Path("/integrantes/{id}")
	@Produces({"application/json"})
	public String usuariosDeUnGrupo(@PathParam("id") Integer id){
		MongoClient mongoClient = mongoEJB.getMongoClient();
		MongoDatabase database = mongoClient.getDatabase("mongostudygroup");
		MongoCollection<Document> collection = database.getCollection("grupoHorario.integrantes");
		
		Document doc = collection.find(Filters.in("grupoHorarioId", id+"")).first();
		if (doc != null){
			List<Document> docUsuarios = (ArrayList<Document>)doc.get("usuario");
			return JSON.serialize(docUsuarios);
		}
		
		
		return null;
	}
	
	@GET
	@Produces({"application/json"})
	public String findAll(){
		ListSerializer serializer = new ListSerializer();
		String result = serializer.GrupoHorarioListSerializer(grupoHorarioFacadeEJB.findAll());
		return result;
	}
	
	@GET
    @Path("{id}")
    @Produces({"application/json"})
    public String find(@PathParam("id") Integer id) {
		ListSerializer serializer = new ListSerializer();
		String result = serializer.GrupoHorarioSerializer(grupoHorarioFacadeEJB.find(id));
		return result;
    }
	
	//Id del perfil de ayudante
	@POST
	@Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void create(@PathParam("id") Integer id, String rawJson) {
		PerfilAyudante perfilAyudante = perfilAyudanteFacadeEJB.find(id);
		if (perfilAyudante != null){
			Usuario usuario = perfilAyudante.getUsuario();
			GrupoHorario entity = new GrupoHorario();
			entity.setUsuarioId(id);
			entity.setPerfilAyudante(perfilAyudante);
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
							String descripcionHorario = buff.getString("descripcionHorario");
							Date fechaInicio = buff.getDate("fechaInicio");
							Date fechaTermino = buff.getDate("fechaTermino");
							if (descripcionHorario != null && fechaInicio != null && fechaTermino != null){
								
							
								//DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
								//Date date = new Date();
								//java.sql.Date sqlDate = new java.sql.Date(date.getTime());
								//String current = dateFormat.format(date);
								//long currentTime = System.currentTimeMillis();
								//Time time = new Time(3600000);
								entity.setFechaInicio(fechaInicio);
								entity.setFechaTermino(fechaTermino);
								entity.setDescripcionHorario(descripcionHorario);
								entity.setMediosPago("Efectivo");
								entity.setTipoPago("Acordar con el Vendedor");
								//entity.setDuracionTemporal(time);
								//entity.setInicioTemporal(sqlDate);
								//entity.setDescripcionTemporal(buff.getString("descripcionTemporal"));
								grupoHorarioFacadeEJB.create(entity);
							
								MongoClient mongoClient = mongoEJB.getMongoClient();
								MongoDatabase database = mongoClient.getDatabase("mongostudygroup");
								MongoCollection<Document> collection = database.getCollection("grupoHorario.integrantes");
							
								ListSerializer listSerializer = new ListSerializer();
							
								Document docUsuario = Document.parse(listSerializer.UsuarioSerializer(usuario));
								List <Document> docUsuarios = new ArrayList<Document>();
								docUsuarios.add(docUsuario);
								Document doc = new Document();
								doc.append("grupoHorarioId",entity.getGrupoHorarioId()+"").append("usuario", docUsuarios);
							
								collection.insertOne(doc);
							}
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
    public void editLocacionDeGrupoHorario(@PathParam("id") Integer id, Lugar entity) {
		GrupoHorario grupoHorario = grupoHorarioFacadeEJB.find(id);
		if (grupoHorario != null){
			if (entity.getIdLugar() != 0){ 
				Lugar lugar = lugarFacadeEJB.find(entity.getIdLugar());
				grupoHorario.setLugar(lugar);
				grupoHorarioFacadeEJB.edit(grupoHorario);
			}
		}
    }
	
    // Cambio de Ramo, de Descripcion, de PerfilAyudante, Fecha inicio y termino, tipo y medio de pago.
	@PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, GrupoHorario entity) {
		GrupoHorario grupoHorario = grupoHorarioFacadeEJB.find(id);
		if (entity.getDescripcionHorario() != null){
			grupoHorario.setDescripcionHorario(entity.getDescripcionHorario());
		}
		if(entity.getRamo() != null){
			if (entity.getRamo().getRamoId() != 0){
				grupoHorario.setRamo(ramoFacadeEJB.find(entity.getRamo().getRamoId()));
			}
		}
		if(entity.getPerfilAyudante() != null){
			if (entity.getPerfilAyudante().getPerfilAyudanteId() != 0){
				PerfilAyudante perfilAyudante = perfilAyudanteFacadeEJB.find(entity.getPerfilAyudante().getPerfilAyudanteId());
				grupoHorario.setPerfilAyudante(perfilAyudante);
				grupoHorario.setUsuarioId(perfilAyudante.getUsuario().getUsuarioId());
			}
		}
		if(entity.getFechaInicio() != null && entity.getFechaTermino() != null){ //Verificacion de que fecha es superior en FrontEnd
			grupoHorario.setFechaInicio(entity.getFechaInicio());
			grupoHorario.setFechaTermino(entity.getFechaTermino());
		}
		if(entity.getTipoPago() != null){
			grupoHorario.setTipoPago(entity.getTipoPago());
		}
		if(entity.getMediosPago() != null){
			grupoHorario.setMediosPago(entity.getMediosPago());
		}
    	grupoHorarioFacadeEJB.edit(grupoHorario);
    }
	
    //El id es del grupo horario
	@PUT
	@Path("/agregar_integrantes/{id}")
	@Consumes({"application/xml", "application/json"})
	public void agregarIntegrantes(@PathParam("id") Integer id, List<Usuario> usuarios){
		if (usuarios != null){
			int size = usuarios.size();
			ListSerializer listSerializer = new ListSerializer();
			MongoClient mongoClient = mongoEJB.getMongoClient();
			MongoDatabase database = mongoClient.getDatabase("mongostudygroup");
			MongoCollection<Document> collection = database.getCollection("grupoHorario.integrantes");
			List<Document> foundDocument = collection.find(Filters.eq("grupoHorarioId",id+"")).into(new ArrayList<Document>());
			Document doc = foundDocument.get(0);
			
			if (doc != null){
				Document newDoc = new Document().append("grupoHorarioId",id+"");
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
				collection.findOneAndReplace(Filters.eq("grupoHorarioId", id+""), doc);
			}
		}
	}
    
    
    //Elimina grupo de mongo y mysql y agrega los usuarios al listado de encuentros previos
    @DELETE
    @Path("{id}")
    public void delete(@PathParam("id") Integer id){
    	GrupoHorario grupoHorario = grupoHorarioFacadeEJB.find(id); 
		MongoClient mongoClient = mongoEJB.getMongoClient();
		MongoDatabase database = mongoClient.getDatabase("mongostudygroup");
		MongoCollection<Document> collection = database.getCollection("grupoHorario.integrantes");
		MongoCollection<Document> collectionSalida = database.getCollection("usuario.previosEncuentros");
		Document doc = collection.find(Filters.eq("grupoHorarioId", id+"")).first();
		Object ob = doc.get("usuario");
		List<Document> usuarios = (ArrayList<Document>)ob;
		//Document docSalida = new Document();
		
		
		for (int i = 0; i < usuarios.size(); i++){
			Document usuario = usuarios.get(i);
			Document docAux = new Document();
			List<Document> docUsuarios = new ArrayList<Document>();
			docAux.append("usuarioId", usuario.getString("usuarioId")+"");
			for (int j = 0; j < i; j++){
				Document usuarioAux = usuarios.get(j);
				Document docUsuario = new Document();
				docUsuario.append("usuarioId", usuarioAux.getString("usuarioId")+"")
					.append("mail", usuarioAux.getString("mail"))
					.append("nombre", usuarioAux.getString("nombre"))
					.append("apellidos", usuarioAux.getString("apellidos"))
					.append("numeroMovil", usuarioAux.getString("numeroMovil"));
				docUsuarios.add(docUsuario);
			}
			for (int j = i+1; j < usuarios.size(); j++){
				Document usuarioAux = usuarios.get(j);
				Document docUsuario = new Document();
				docUsuario.append("usuarioId", usuarioAux.getString("usuarioId")+"")
					.append("mail", usuarioAux.getString("mail"))
					.append("nombre", usuarioAux.getString("nombre"))
					.append("apellidos", usuarioAux.getString("apellidos"))
					.append("numeroMovil", usuarioAux.getString("numeroMovil"));
				docUsuarios.add(docUsuario);
			}
			collectionSalida.findOneAndDelete(Filters.eq("usuarioId", usuario.getString("usuarioId")+""));
			docAux.append("usuario", docUsuarios);
			collectionSalida.insertOne(docAux);
		}
		collection.findOneAndDelete(Filters.eq("grupoHorarioId",id+""));
		if (grupoHorario != null){
			grupoHorarioFacadeEJB.remove(grupoHorario);
		}
    }
    
    //Eliminar solo un usuario por su id, el id del link es el del grupoHorario
    @DELETE
    @Path("/eliminar_usuario/{id}")
    public void deleteUsuarioDeGrupo(@PathParam("id") Integer id, String rawJson){
    	String usuarioId = Document.parse(rawJson).getInteger("usuarioId")+""; //"usuarioId": id
    	MongoClient mongoClient = mongoEJB.getMongoClient();
		MongoDatabase database = mongoClient.getDatabase("mongostudygroup");
		MongoCollection<Document> collection = database.getCollection("grupoHorario.integrantes");
		List<Document> foundDocument = collection.find(Filters.eq("grupoHorarioId",id+"")).into(new ArrayList<Document>());
		Document doc = foundDocument.get(0);
		
		if (doc != null){
			List<Document> docUsuarios = (List<Document>) doc.get("usuario");
			for (int j = 1; j < docUsuarios.size(); j++){ // Se puede cambiar a 0 y asi el primero si se puede eliminar.
				if (docUsuarios.get(j).getString("usuarioId").equals(usuarioId)){
					docUsuarios.remove(j);
					break;
				}
			}
			
			Document newDoc = new Document().append("grupoHorarioId",id+"");
			newDoc.append("usuario", docUsuarios);
			collection.findOneAndReplace(Filters.eq("grupoHorarioId", id+""), doc);
			
		}
    }

}
