import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class Agente1 extends Agent {

	public void setup() {
		System.out.println("Hola. Soy el agente "+getLocalName());
		addBehaviour(new TickerBehaviour(this, 1000) {
 
			public void onTick() {
				
				ACLMessage msg = receive();
		
				if (msg != null) {
					if(msg.getPerformative() == ACLMessage.REQUEST) {
						System.out.println(msg);
						ACLMessage msgReply = msg.createReply();
						msgReply.setContent(msg.getContent());
						send(msgReply);
					} else {
						block();
					}
					
					if(msg.getPerformative() == ACLMessage.INFORM) {
						String ruta = "mensajes.txt";
						File archivo = new File(ruta);
						BufferedWriter bw = null;
						if(archivo.exists()) {
						      try {
								bw = new BufferedWriter(new FileWriter(archivo));
								bw.write(msg.getContent());
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else {
				            try {
								bw = new BufferedWriter(new FileWriter(archivo));
								bw.write(msg.getContent());
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						try {
							bw.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						block();
					}
				}
			}
		});
	}
}