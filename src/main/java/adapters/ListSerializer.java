package adapters;

import java.util.List;

import model.Carrera;
import model.Ramo;

public class ListSerializer {
	public ListSerializer(){
	}
	
	public String CarreraSerializer(Carrera carrera){
		String id = carrera.getCarreraId()+"";
		String nombreCarrera = carrera.getNombreCarrera();
		String ramos = RamoListSerializer(carrera.getRamos());
		String stringCollector = "{\n\"carreraId\":\""+id+"\",\n\"nombreCarrera\":\""+nombreCarrera+"\",\n\"ramo\":\""+ramos+"\n}";
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
