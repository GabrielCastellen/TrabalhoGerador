package Ex13;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class AgendaMain {

	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		Agenda ag = new Agenda();
		
		PersistenciaCSV csv = new PersistenciaCSV();
		Persistencia pers = new Persistencia(csv);
		
		PersistenciaJSon json = new PersistenciaJSon();
		Persistencia pers2 = new Persistencia(json);
		
		//PersistenciaXML xml = new PersistenciaXML();
		//Persistencia pers3 = new Persistencia(xml);
		
		String op = "";
		ag.setLista(pers2.ler());
		do {
			System.out.print("Deseja importar(S/N): ");
			op = entrada.nextLine();
		}while(!op.equalsIgnoreCase("S") && !op.equalsIgnoreCase("N"));
		
		if(op.equalsIgnoreCase("S")) {
			ag.setLista(json.importar(ag.getLista()));
			pers.gravar(ag.getLista());
			pers2.gravar(ag.getLista());
		}else
			ag.setLista(pers2.ler());
		
		
		Calendar calendario = Calendar.getInstance();
		
		
		
		
		//json.importar();
		
		boolean aberto = true;
		
		while(aberto) {
		System.out.println("Escolha uma opção");
		System.out.println("1 - Incluir contato");
		System.out.println("2 - Alterar contato");
		System.out.println("3 - Excluir contato");
		System.out.println("4 - Consulta por nome");
		System.out.println("5 - Aniversariantes do mês");
		System.out.print("6 - Consulta por e-mail"
					   + "\n: ");
		
		
		int opcao = entrada.nextInt();
		String nomeConsulta;
		String emailConsulta;
		List<Pessoa> listaConsulta;
		
		
		switch (opcao) {
		case 1:
			Pessoa p = new Pessoa();
			System.out.print("Cod: ");
			p.setCodigo(entrada.nextInt());
			entrada.nextLine();
			System.out.print("Nome: ");
			p.setNome(entrada.nextLine());
			System.out.print("Email: ");
			p.setEmail(entrada.nextLine());
			System.out.print("Telefone: ");
			p.setTelefone(entrada.nextLine());
			System.out.print("DataDeNascimento(dd/mm/aaaa): ");
			p.setDatanascimento(DataUtil.StringToDate(entrada.nextLine()));
			ag.incluir(p);
			pers.gravar(ag.getLista());
			pers2.gravar(ag.getLista());
			//pers3.gravar(ag.getLista());
			break;
		case 2:
			System.out.print("Informe o nome: ");
			entrada.nextLine();
			nomeConsulta = entrada.nextLine();
			
			
			System.out.println("Escolha o contato a ser alterado: ");
			listaConsulta = ag.consultaPorNome(nomeConsulta);
			int ind = 1;
			for (Pessoa pessoa : listaConsulta) {
				
				System.out.printf("%d: %s \n",ind,pessoa.getNome());
				ind++;
			}
			int escolha = entrada.nextInt();
			
			Pessoa novaPessoa = new Pessoa();
			System.out.print("Cod: ");
			novaPessoa.setCodigo(entrada.nextInt());
			entrada.nextLine();
			System.out.print("Nome: ");
			novaPessoa.setNome(entrada.nextLine());
			System.out.print("Email: ");
			novaPessoa.setEmail(entrada.nextLine());
			System.out.print("Telefone: ");
			novaPessoa.setTelefone(entrada.nextLine());
			entrada.nextLine();
			System.out.print("DataDeNascimento(dd/mm/aaaa h:mm:ss): ");
			novaPessoa.setDatanascimento(DataUtil.StringToDate(entrada.nextLine()));
			
			ind = 1;
			for (Pessoa pessoa : listaConsulta) {
				if(ind == escolha) {
				ag.alterar(pessoa, novaPessoa);
				}
				ind++;
			}
			pers.gravar(ag.getLista());
			pers2.gravar(ag.getLista());
			//pers3.gravar(ag.getLista());	
			break;
		case 3:
			System.out.print("Informe o nome: ");
			entrada.nextLine();
			nomeConsulta = entrada.nextLine();
			
			
			System.out.println("Escolha o contato a ser excluido: ");
			listaConsulta = ag.consultaPorNome(nomeConsulta);
			int indc = 1;
			for (Pessoa pessoa : listaConsulta) {
				
				System.out.printf("%d: %s \n",indc,pessoa.getNome());
				indc++;
			}
			int escolhaEx = entrada.nextInt();
			
			
			indc = 1;
			for (Pessoa pessoa : listaConsulta) {
				if(indc == escolhaEx) {
				ag.excluir(pessoa);
				}
				indc++;
			}
			pers.gravar(ag.getLista());
			pers2.gravar(ag.getLista());
			//pers3.gravar(ag.getLista());	
			break;
		case 4:
			System.out.print("Informe o nome: ");
			entrada.nextLine();
			nomeConsulta = entrada.nextLine();
			
			listaConsulta = ag.consultaPorNome(nomeConsulta);
			
			if(listaConsulta.isEmpty())
				System.out.println("Não foram encontrados resultados.");
			else {
				for (Pessoa pessoa : listaConsulta) {
					System.out.println(pessoa);
				}
			}
			break;
		case 5:
			
			System.out.println(ag.aniversariantesDoMes(calendario.get(Calendar.MONTH)));
			break;
		case 6:
			System.out.print("Informe o email: ");
			entrada.nextLine();
			emailConsulta = entrada.nextLine();
			
			listaConsulta = ag.consultaPorEmail(emailConsulta);
			if(listaConsulta.isEmpty())
				System.out.println("Não foram encontrados resultados.");
			else {
				for (Pessoa pessoa : listaConsulta) {
					System.out.println(pessoa);
				}
			}
			break;

		default:
			aberto = false;
			break;
		}
		
		}
	entrada.close();
	}

}
