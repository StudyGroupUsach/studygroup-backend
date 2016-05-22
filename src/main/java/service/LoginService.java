package service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import facade.UsuarioFacade;
import model.Usuario;


@Path("/login")
public class LoginService {

	@EJB
	UsuarioFacade usuarioFacade;

	Logger logger = Logger.getLogger(LoginService.class.getName());

	@GET
	@Produces({"application/xml", "application/json"})
	public boolean logout(){
		return true;
	}

	@POST
    @Consumes({"application/xml", "application/json"})
	@Produces({"application/json"})
    public boolean login(Usuario entity) {
		boolean loginUser = false;

		if( !aFieldIsNull(entity) ){
			Usuario queryUser = getUser(entity.getMail());

			if(queryUser != null){
				loginUser = logUser(entity,queryUser);
			}
		}
		return loginUser;

    }



	private boolean logUser(Usuario dest ,Usuario src){
		boolean isLog = false;

		if(md5(dest.getPass()).equals(src.getPass()) ){
			isLog = true;
		}
		
		return isLog;
	}

	private boolean aFieldIsNull(Usuario entity){
		boolean isNull = false;

		if(entity.getMail() == null || entity.getPass() == null){
			isNull = true;
		}

		return isNull;
	}

	private Usuario getUser(String mail)
	{
		List<Usuario> allUsers = usuarioFacade.findAll();
		Usuario userFound = null;

		for(int i = 0;i < allUsers.size();i++){
			if(allUsers.get(i) != null ){
				if(allUsers.get(i).getMail() != null){

					if( (allUsers.get(i).getMail()).equals(mail) ){
						userFound = allUsers.get(i);
						i = allUsers.size();
					}
				}

			}

		}
		return userFound;
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
