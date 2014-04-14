import java.util.Scanner;

import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;

public class RegistraGeradorNoTopico {

	public static void main(String[] args) throws Exception {

		InitialContext initialContext = new InitialContext();
		TopicConnectionFactory factory = (TopicConnectionFactory) initialContext
				.lookup("ConnectionFactory");

		TopicConnection cnx = factory.createTopicConnection();
		cnx.setClientID("GeradorEbook");

		Topic topico = (Topic) initialContext.lookup("livraria");
		TopicSession session = cnx.createTopicSession(false,
				Session.AUTO_ACKNOWLEDGE);

		TopicSubscriber subscriber = session.createDurableSubscriber(topico,
				"AssinaturaEbook", "formato='ebook'", false);
		subscriber.setMessageListener(new TratadorDeMensagem());

		cnx.start();

		Scanner keyb = new Scanner(System.in);
		System.out.println("Gerador esperando as mensagens do topico...");
		keyb.nextLine();

		keyb.close();
		cnx.close();
	}
}
