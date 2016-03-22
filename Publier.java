import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;


public class Publier {
		private String topic ; 
		private String BROKER_URI ; 
		private MqttClient mqttClient;
		
		
		public MqttClient getMqttClient() {
			return mqttClient;
		}


		public void setMqttClient(String uri) throws MqttException {
			this.mqttClient = new MqttClient(uri, MqttClient.generateClientId());
		}


		public String getTopic() {
			return topic;
		}


		public void setTopic(String topic) {
			this.topic = topic;
		}


		public String getBROKER_URI() {
			return BROKER_URI;
		}


		public void setBROKER_URI(String bROKER_URI) {
			BROKER_URI = bROKER_URI;
		}

		
		/*constructeur*/
				
	public Publier(String topic, String uRI) {			
			this.topic = topic;
			BROKER_URI = uRI;
			try {
				mqttClient = new MqttClient(
						BROKER_URI,	MqttClient.generateClientId());
			} catch (MqttException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			mqttClient.setCallback(new MqttCallback() {

				
				public void messageArrived(String topic, MqttMessage message) throws Exception {
							//System.out.println(message.toString());

					}

				
				public void connectionLost(Throwable cause) {
					System.out.println("Connection lost: "
							+ cause.getLocalizedMessage());
				}

				
				public void deliveryComplete(IMqttDeliveryToken arg0) {
					// TODO Auto-generated method stub
					/*
					System.out.println("id="+arg0.getMessageId());
					System.out.println("client="+arg0.getClient().getClientId());
					System.out.println("topic="+arg0.getTopics().toString());										
					*/
			}
		});
	}
	
	public Publier(){}
		
		/*publier un string dans topic*/
	
	void publierString(String s) throws MqttSecurityException, MqttException{
		mqttClient.connect();
		mqttClient.subscribe(this.topic);						
		MqttMessage msg = new MqttMessage(s.getBytes());
		mqttClient.publish(this.topic, msg);
		this.mqttClient.disconnect();
	}
	
	
	void publierString(String s,String topic) throws MqttSecurityException, MqttException{
		mqttClient.connect();
		mqttClient.subscribe(topic);						
		MqttMessage msg = new MqttMessage(s.getBytes());
		mqttClient.publish(this.topic, msg);
		this.mqttClient.disconnect();
	}
	
	
	void publierString(String s,String topic,String uri) throws MqttSecurityException, MqttException{
		mqttClient = new MqttClient(uri, MqttClient.generateClientId());
		mqttClient.connect();
		mqttClient.subscribe(topic);						
		MqttMessage msg = new MqttMessage(s.getBytes());
		mqttClient.publish(this.topic, msg);
		this.mqttClient.disconnect();
	}
	
	/*mettre le contenu d'un fichier dans un string et le publier*/
	
	void publierFichierS(String filePath) throws FileNotFoundException, MqttSecurityException, MqttException{
		this.publierString(new Scanner(new File(filePath)).useDelimiter("\\Z").next());
	}
	
	void publierFichierS(String filePath,String topic) throws FileNotFoundException, MqttSecurityException, MqttException{
		this.publierString(new Scanner(new File(filePath)).useDelimiter("\\Z").next(),topic);
	}
	
	void publierFichierS(String filePath,String topic,String uri) throws FileNotFoundException, MqttSecurityException, MqttException{
		this.publierString(new Scanner(new File(filePath)).useDelimiter("\\Z").next(),topic,uri);
	}
	
	/*afficher le contenu d'un fichier ligne par ligne*/
	
	void publierFichierL(String filePath) throws MqttPersistenceException, IOException, MqttException{
		
		
		File file = new File(filePath);
		String stg ;
		BufferedReader buffer = new BufferedReader(new FileReader(file));			
			
		
		while((stg=buffer.readLine())!=null){
					this.publierString(stg);				
				}
	
		this.publierString("Fin");
	}
	
	void publierFichierL(String filePath,String topic) throws MqttPersistenceException, IOException, MqttException{
		
		
		File file = new File(filePath);
		String stg ;
		BufferedReader buffer = new BufferedReader(new FileReader(file));			
			
		
		while((stg=buffer.readLine())!=null){
					this.publierString(stg,topic);				
				}
	
		this.publierString("Fin");
	}	


	void publierFichierL(String filePath,String topic,String uri) throws MqttPersistenceException, IOException, MqttException{
	
	
		File file = new File(filePath);
		String stg ;
		BufferedReader buffer = new BufferedReader(new FileReader(file));			
		
	
		while((stg=buffer.readLine())!=null){
				this.publierString(stg,topic,uri);				
		}

		this.publierString("Fin");
	}	
}