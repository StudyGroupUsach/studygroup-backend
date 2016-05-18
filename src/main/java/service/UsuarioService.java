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

import facade.UsuarioFacade;
import model.Usuario;

//Dependencias adicionales para hash MD5 en la contrasena
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Path("/usuarios")
public class UsuarioService {
	
	@EJB 
	UsuarioFacade usuarioFacadeEJB;
	
	Logger logger = Logger.getLogger(UsuarioService.class.getName());
	
	@GET
	@Produces({"application/xml", "application/json"})
	public List<Usuario> findAll(){
		return usuarioFacadeEJB.findAll();
	}
	
	@GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Usuario find(@PathParam("id") Integer id) {
        return usuarioFacadeEJB.find(id);
    }
	
	@POST
    @Consumes({"application/xml", "application/json"})
    public void create(Usuario entity) {
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
				|| entity.getNombre() == null || entity.getPass() == null ){
			//logger.finer("Null, user:"+entity.getNombre() +" -->Mail:"+entity.getMail());
		}	
		else if(part.length == 2){
			//Verificar que cumple con el formato @usach.cl
			if(part[1].equals("usach.cl")){
				//Anadimos una password
				entity.setPass(md5(pass));
				
				//Crear usuario a la Base de datos
				usuarioFacadeEJB.create(entity);
			}
			
		}
		
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
    
	

}