import java.util.Scanner;

import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;


public class RegistraTratadorNaFila {

	public static void main(String[] args) throws Exception {
		
		InitialContext initialContext = new InitialContext();
		QueueConnectionFactory factory =
				(QueueConnectionFactory) initialContext.lookup("ConnectionFactory");
		QueueConnection connection = factory.createQueueConnection();
		
		Queue fila = (Queue) initialContext.lookup("gerador");
		QueueSession session = 
				connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		
		QueueReceiver subscriber = session.createReceiver(fila);
		subscriber.setMessageListener(new TratadorDeMensagem());
		
		connection.start();
		
		Scanner keyb = new Scanner(System.in);
		System.out.println("Tratador esperando as mensagens na fila JMS.");
		
		keyb.nextLine();
		keyb.close();
		connection.close();
	
	}
}
