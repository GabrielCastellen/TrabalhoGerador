package Ex13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class PersistenciaJSon implements Gravacao {

	@Override
	public boolean gravar(List<Pessoa> list) {
		try {
		GsonBuilder builder = new GsonBuilder();
	    Gson gson = builder.create();
	    FileWriter writer = new FileWriter("json/agenda.json");
	    writer.write(gson.toJson(list));
	    writer.close();
	    return true;
		}catch (IOException e) {
			return false;
		}
	    
	}

	@Override
	public List<Pessoa> ler() {
		try {
		//GsonBuilder builder = new GsonBuilder();
	    //Gson  gson= builder.create();
	    BufferedReader bufferedReader = new BufferedReader(
	    								new FileReader("json/agenda.json"));

	    Type listType = new TypeToken<ArrayList<Pessoa>>(){}.getType();

	    List<Pessoa> lista = new ArrayList<Pessoa>();
	    
	    lista = new ArrayList<Pessoa>();
	   
	    lista = new Gson().fromJson(bufferedReader, listType);
	    
	    return lista;
		}catch (IOException e) {
			return null;
		}
	}
	
	public List<Pessoa> importar(List<Pessoa> p) {
		try {
			PersistenciaJSon json = new PersistenciaJSon();
			Persistencia pers2 = new Persistencia(json);
			//GsonBuilder builder = new GsonBuilder();
		    //Gson gson = builder.create();
			URL url = new URL("http://www.curvello.com/gerador/pessoa/1000");
			URI uri = url.toURI();
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			Type listType = new TypeToken<ArrayList<Pessoa>>(){}.getType();

		    List<Pessoa> lista = new ArrayList<Pessoa>();
		    
		    lista = new ArrayList<Pessoa>();
		   
		    lista = new Gson().fromJson(br, listType);
		    if(p!=null) {
		    	for (Pessoa pessoa : p) {
		    		lista.add(pessoa);
		    	}
		    }
		    
		    return lista;
		} catch (URISyntaxException excecao) {
			excecao.printStackTrace();
			 return null;
		} catch (IOException excecao) {
			excecao.printStackTrace();
			 return null;
		}
	}

}
