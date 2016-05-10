package projetL3;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;


/* 
 * La classe Mqtt_sub se connecte a un client MQTT(mosquitto dans notre cas)
 * souscrit a un topic MQTT et recupere les messages recus sur ce topic. 
 */
public class Mqtt_sub {
	private String topic; //topic au quel on sera inscrit
	private final static String BROKER_URI = "tcp://localhost:1883"; //uri du client MQTT
	private static MqttClient mqttClient; // client mqtt
	private static MqttMessage messageMQTT; //message recu
	
	//getters and setters
	public MqttMessage getMessageMQTT(){
		return messageMQTT;
	}
		
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	public static MqttClient getMqttClient() {
		return mqttClient;
	}

	public static void setMqttClient(MqttClient mqttClient) {
		Mqtt_sub.mqttClient = mqttClient;
	}
	
	//constructeur
	public Mqtt_sub (String topic) throws MqttSecurityException, MqttException{
		
		mqttClient = new MqttClient(BROKER_URI,	MqttClient.generateClientId());
		this.topic = topic;
		mqttClient.setCallback(new MqttCallback() {

			@Override
			public void messageArrived(String topic, MqttMessage message) throws Exception {

				
				try {
					messageMQTT = message;
					
					System.out.println("Message arrived : \"" + message.toString() + "\" on topic \""+ topic +"\"" );
				}
				catch (Exception e){
					e.printStackTrace();
				}
				
			}

			@Override
			public void connectionLost(Throwable cause) {
				System.out.println("Connection lost: "
						+ cause.getLocalizedMessage());
			}

			@Override
			public void deliveryComplete(IMqttDeliveryToken arg0) {
				try {
					System.out.println("delivery complete : " + arg0.isComplete());
				}
				catch (Exception e){
					e.printStackTrace();
				}
				
			}
			
		});
		
		mqttClient.connect();
		mqttClient.subscribe(topic);
		
		
	}

}
