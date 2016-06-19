package adapters;

import java.util.List;

import model.Carrera;
import model.Ramo;
import model.Usuario;
import model.GrupoTemporal;
import model.Lugar;
import model.PerfilAyudante;
import model.GrupoHorario;

public class ListSerializer {
	public ListSerializer(){
	}
	
	public String PerfilAyudanteSerializer(PerfilAyudante perfilAyudante){
		String perfilAyudanteId = perfilAyudante.getPerfilAyudanteId()+"";
		String nombre = "";
		String apellidos = "";
		String usuarioId = "";
		String estado = perfilAyudante.getEstado();
		String valoracionPromedio = perfilAyudante.getValoracionPromedio()+""; 
		String grupoHorarios = GrupoHorarioListSerializer(perfilAyudante.getGrupoHorarios());
		
		if (perfilAyudante.getUsuario() != null){
			nombre = perfilAyudante.getUsuario().getNombre();
			apellidos = perfilAyudante.getUsuario().getApellidos();
			usuarioId = perfilAyudante.getUsuario().getUsuarioId()+"";
		}
				
		String stringCollector = "{\n\"perfilAyudanteId\":\""+perfilAyudanteId+"\","
				+ "\n\"nombre\":\""+nombre+"\","
				+ "\n\"apellidos\":\""+apellidos+"\","
				+ "\n\"usuarioId\":\""+usuarioId+"\","
				+ "\n\"estado\":\""+estado+"\","
				+ "\n\"valoracionPromedio\":\""+valoracionPromedio+"\","
						+ "\n\"grupoHorarios\":"+grupoHorarios+"\n}";
		return stringCollector;
	}
	
	public String PerfilAyudanteListSerializer(List<PerfilAyudante> perfilAyudantes){
		String stringCollector = "";
		int size = perfilAyudantes.size();
		for (int i = 0; i < perfilAyudantes.size();i++){
			stringCollector = stringCollector+PerfilAyudanteSerializer(perfilAyudantes.get(i));
			if (size-i > 1){
				stringCollector=stringCollector+",\n";
			}
		}
		stringCollector = "["+stringCollector+"]";
		return stringCollector;
	}
	
	public String GrupoHorarioSerializer(GrupoHorario grupoHorario){
		String descripcionHorario = grupoHorario.getDescripcionHorario();
		String fechaInicio = grupoHorario.getFechaInicio().toString();
		String fechaTermino = grupoHorario.getFechaTermino().toString();
		String mediosPago = grupoHorario.getMediosPago();
		String tipoPago = grupoHorario.getTipoPago();
		String idLugar = "";
		String usuarioId = "";
		String perfilAyudanteId = "";
		String nombre = "";
		String apellidos = "";
		String nombreRamo = "";
		String ramoId = "";
		if ((Integer)grupoHorario.getUsuarioId() != null){
			usuarioId = grupoHorario.getUsuarioId()+"";
		}
		if (grupoHorario.getLugar() != null){
			idLugar = grupoHorario.getLugar().getIdLugar()+"";
		}
		if (grupoHorario.getPerfilAyudante() != null){
			perfilAyudanteId = grupoHorario.getPerfilAyudante().getPerfilAyudanteId()+"";
			nombre = grupoHorario.getPerfilAyudante().getUsuario().getNombre();
			apellidos = grupoHorario.getPerfilAyudante().getUsuario().getApellidos();
		}
		if (grupoHorario.getRamo() != null){
			nombreRamo = grupoHorario.getRamo().getNombreRamo();
			ramoId = grupoHorario.getRamo().getRamoId()+"";
		}
		
		String stringCollector = "{\n\"descripcionHorario\":\""+descripcionHorario+"\","
				+ "\n\"fechaInicio\":\""+fechaInicio+"\","
				+ "\n\"fechaTermino\":\""+fechaTermino+"\","
				+ "\n\"mediosPago\":\""+mediosPago+"\","
				+ "\n\"tipoPago\":\""+tipoPago+"\","
				+ "\n\"idLugar\":\""+idLugar+"\","
				+ "\n\"usuarioId\":\""+usuarioId+"\","
				+ "\n\"perfilAyudanteId\":\""+perfilAyudanteId+"\","
				+ "\n\"nombre\":\""+nombre+"\","
				+ "\n\"apellidos\":\""+apellidos+"\","
				+ "\n\"ramoId\":\""+ramoId+"\","
						+ "\n\"nombreRamo\":\""+nombreRamo+"\"\n}";
		return stringCollector;
	}
	
	public String GrupoHorarioListSerializer(List<GrupoHorario> gruposHorario){
		String stringCollector = "";
		int size = gruposHorario.size();
		for (int i = 0; i < gruposHorario.size();i++){
			stringCollector = stringCollector+GrupoHorarioSerializer(gruposHorario.get(i));
			if (size-i > 1){
				stringCollector=stringCollector+",\n";
			}
		}
		stringCollector = "["+stringCollector+"]";
		return stringCollector;
	}
	
	public String LugarSerializer(Lugar lugar){
		String idLugar = lugar.getIdLugar()+"";
		String latitudLugar = lugar.getLatitudLugar()+"";
		String longitudLugar = lugar.getLongitudLugar()+"";
		String nombreLugar = lugar.getNombreLugar();
		String grupoTemporals = GrupoTemporalListSerializer(lugar.getGrupoTemporals());
		String grupoHorarios = GrupoHorarioListSerializer(lugar.getGrupoHorarios());		
		
		String stringCollector = "{\n\"idLugar\":\""+idLugar+"\","
				+ "\n\"latitudLugar\":\""+latitudLugar+"\","
				+ "\n\"longitudLugar\":\""+longitudLugar+"\","
				+ "\n\"nombreLugar\":\""+nombreLugar+"\","
				+ "\n\"grupoTemporals\":"+grupoTemporals+","
						+ "\n\"grupoHorarios\":"+grupoHorarios+"\n}";
		return stringCollector;
	}
	
	public String LugarListSerializer(List<Lugar> lugares){
		String stringCollector = "";
		int size = lugares.size();
		for (int i = 0; i < lugares.size();i++){
			stringCollector = stringCollector+LugarSerializer(lugares.get(i));
			if (size-i > 1){
				stringCollector=stringCollector+",\n";
			}
		}
		stringCollector = "["+stringCollector+"]";
		return stringCollector;
	}
	
	public String GrupoTemporalSerializer(GrupoTemporal grupoTemporal){
		String grupoTemporalId = grupoTemporal.getGrupoTemporalId()+"";
		String descripcionTemporal = grupoTemporal.getDescripcionTemporal();
		String duracionTemporal = grupoTemporal.getDuracionTemporal().toString(); //Time
		String inicioTemporal = grupoTemporal.getInicioTemporal().toString(); //Date
		String idLugar = "";
		String ramoId = "";
		String nombreRamo = "";
		String usuarioId = "";
		String nombreUsuario = "";
		if (grupoTemporal.getLugar() != null){
			idLugar = grupoTemporal.getLugar().getIdLugar()+""; 
		}
		if (grupoTemporal.getRamo() != null){
			if ((Integer)grupoTemporal.getRamo().getRamoId() != null){
				ramoId = grupoTemporal.getRamo().getRamoId()+"";
				nombreRamo = grupoTemporal.getRamo().getNombreRamo();				
			}
		}
		if (grupoTemporal.getUsuario() != null){
			if ((Integer)grupoTemporal.getUsuario().getUsuarioId() != null){
				usuarioId = grupoTemporal.getUsuario().getUsuarioId()+"";
				nombreUsuario = grupoTemporal.getUsuario().getNombre();				
			}
		}
		String stringCollector = "{\n\"grupoTemporalId\":\""+grupoTemporalId+"\","
				+ "\n\"descripcionTemporal\":\""+descripcionTemporal+"\","
				+ "\n\"duracionTemporal\":\""+duracionTemporal+"\","
				+ "\n\"inicioTemporal\":\""+inicioTemporal+"\","
				+ "\n\"idLugar\":\""+idLugar+"\","
				+ "\n\"ramoId\":\""+ramoId+"\","
				+ "\n\"nombreRamo\":\""+nombreRamo+"\","
				+ "\n\"usuarioId\":\""+usuarioId+"\","
						+ "\n\"nombre\":\""+nombreUsuario+"\"\n}";
		return stringCollector;
	}
	
	public String GrupoTemporalListSerializer(List<GrupoTemporal> grupoTemporal){
		String stringCollector = "";
		int size = grupoTemporal.size();
		for (int i = 0; i < grupoTemporal.size();i++){
			stringCollector = stringCollector+GrupoTemporalSerializer(grupoTemporal.get(i));
			if (size-i > 1){
				stringCollector=stringCollector+",\n";
			}
		}
		stringCollector = "["+stringCollector+"]";
		return stringCollector;
	}
		
	public String UsuarioSerializer(Usuario usuario){
		String id = usuario.getUsuarioId()+"";
		String nombre = usuario.getNombre();
		String apellidos = usuario.getApellidos();
		String descripcion = usuario.getDescripcion();
		String mail = usuario.getMail();
		String numeroMovil = usuario.getNumeroMovil();
		String pass = usuario.getPass();
		Carrera carrera = usuario.getCarrera();
		String carreraId = "";
		String nombreCarrera = "";
		if (carrera != null){
			carreraId = carrera.getCarreraId()+"";
			nombreCarrera = carrera.getNombreCarrera();
		}
		String grupoTemporals = GrupoTemporalListSerializer(usuario.getGrupoTemporals());
		String stringCollector = "{\n\"usuarioId\":\""+id+"\","
				+ "\n\"nombre\":\""+nombre+"\","
				+ "\n\"apellidos\":\""+apellidos+"\","
				+ "\n\"descripcion\":\""+descripcion+"\","
				+ "\n\"mail\":\""+mail+"\","
				+ "\n\"numeroMovil\":\""+numeroMovil+"\","
				+ "\n\"pass\":\""+pass+"\","
				+ "\n\"carreraId\":\""+carreraId+"\","
				+ "\n\"nombreCarrera\":\""+nombreCarrera+"\","
						+ "\n\"grupoTemporals\":"+grupoTemporals+"\n}";
		return stringCollector;
	}
	
	public String UsuarioListSerializer(List<Usuario> usuario){
		String stringCollector = "";
		int size = usuario.size();
		for (int i = 0; i < usuario.size();i++){
			stringCollector = stringCollector+UsuarioSerializer(usuario.get(i));
			if (size-i > 1){
				stringCollector=stringCollector+",\n";
			}
		}
		stringCollector = "["+stringCollector+"]";
		return stringCollector;
	}
	
	public String CarreraSerializer(Carrera carrera){
		String id = carrera.getCarreraId()+"";
		String nombreCarrera = carrera.getNombreCarrera();
		String ramos = RamoListSerializer(carrera.getRamos());
		String stringCollector = "{\n\"carreraId\":\""+id+"\",\n\"nombreCarrera\":\""+nombreCarrera+"\",\n\"ramo\":"+ramos+"\n}";
		return stringCollector;
	}
	
	public String CarreraListSerializer(List<Carrera> carrera){
		String stringCollector = "";
		int size = carrera.size();
		for (int i = 0; i < carrera.size();i++){
			stringCollector = stringCollector+CarreraSerializer(carrera.get(i));
			if (size-i > 1){
				stringCollector=stringCollector+",\n";
			}
		}
		stringCollector = "["+stringCollector+"]";
		return stringCollector;
	}
	
	public String RamoSerializer (Ramo ramo){
		String nombreRamo = ramo.getNombreRamo();
		String ramoId = ramo.getRamoId() + "";
		String carreraId = ramo.getCarrera().getCarreraId()+"";
		String nombreCarrera = ramo.getCarrera().getNombreCarrera()+"";
		return "{\n\"nombreRamo\":\""+nombreRamo+"\",\n\"ramoId\":\""+ramoId+"\",\n\"carreraId\":\""+carreraId+"\",\n\"nombreCarrera\":\""+nombreCarrera+"\"\n}";
	}
	
	public String RamoListSerializer(List<Ramo> ramos){
		String stringCollector = "";
		int size = ramos.size();
		for (int i = 0; i < ramos.size();i++){
			stringCollector = stringCollector+RamoSerializer(ramos.get(i));
			if (size-i > 1){
				stringCollector=stringCollector+",\n";
			}
		}
		stringCollector = "["+stringCollector+"]";
		return stringCollector;
	}
}
