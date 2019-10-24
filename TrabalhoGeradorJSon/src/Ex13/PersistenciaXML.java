package Ex13;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;




import  org.jdom.Document;
import  org.jdom.Element;
import  org.jdom.input.SAXBuilder;
import  org.jdom.output.XMLOutputter;

public class PersistenciaXML implements Gravacao {

	@Override
	public boolean gravar(List<Pessoa> list) {

			Element  config = new  Element("Agenda");
			Document  documento = new  Document(config );
			Element  titulo = new  Element("titulo");
			titulo.setText("Agenda");
			Element  data = new  Element("data");
			data.setText(DataUtil.DataHoraForStringPadraoH(new  Date ()));
			config.addContent(titulo );
			config.addContent(data);
			for (Pessoa lista: list){
				Element  pessoa = new  Element("pessoa");
				pessoa.setAttribute("codigo", String.valueOf(lista.getCodigo()));
				Element  nome = new  Element("nome");
				nome.setText(lista.getNome());
				pessoa.addContent(nome);
				Element  email = new  Element("email");
				email.setText(lista.getEmail());
				pessoa.addContent(email);
				Element telefone = new Element("telefone");
				telefone.setText(lista.getTelefone());
				pessoa.addContent(telefone);
				
				Element datanascimento = new Element("datanascimento");
				System.out.println(lista.getDatanascimento());
				datanascimento.setText(DataUtil.dateToString(lista.getDatanascimento()));
				pessoa.addContent(datanascimento);
				
				config.addContent(pessoa);
			}
			XMLOutputter  xout = new  XMLOutputter ();
			try {
				BufferedWriter  arquivo = new  BufferedWriter(new  OutputStreamWriter(new  FileOutputStream("xml/agenda.xml")));
				xout.output(documento , arquivo);
				return true;
			} catch (IOException e) {
				e.printStackTrace ();
				return false;
			} 
	}

	@Override
	public List<Pessoa> ler() {
		List <Pessoa > lista = new  ArrayList <Pessoa >();
		Document doc = null;
		SAXBuilder builder = new SAXBuilder();	
		try { 
			doc = builder.build("xml/agenda.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}            
		Element config = doc.getRootElement();
		List ls = config.getChildren("pessoa");
		
		for (Iterator iter = ls.iterator(); iter.hasNext();) {
			Element element = (Element) iter.next();
			Pessoa  pes = new  Pessoa ();
			pes.setCodigo(Integer.parseInt(element.getAttributeValue("codigo")));
			pes.setNome(element.getChildText("nome"));
			pes.setEmail(element.getChildText("email"));
			pes.setTelefone(element.getChildText("telefone"));
			pes.setDatanascimento(DataUtil.StringToDate(element.getChildText("datanascimento")));
			lista.add(pes);
		}
		return lista;
	}

}
