package pilotageLampe_v2;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

public class Mqtt_sub {
	private static String topic;
	private final static String BROKER_URI = "tcp://localhost:1883";
	private static MqttClient mqttClient;
	private static MqttMessage messageMQTT;
	
	public MqttMessage getMessageMQTT(){
		return messageMQTT;
	}
		
	public static String getTopic() {
		return topic;
	}

	public static void setTopic(String topic) {
		Mqtt_sub.topic = topic;
	}
	
	public static MqttClient getMqttClient() {
		return mqttClient;
	}

	public static void setMqttClient(MqttClient mqttClient) {
		Mqtt_sub.mqttClient = mqttClient;
	}

	public static void main(String[] args)throws MqttSecurityException, MqttException {
		mqttClient = new MqttClient(BROKER_URI,	MqttClient.generateClientId());
		topic = "temp";
		mqttClient.setCallback(new MqttCallback() {

			@Override
			public void messageArrived(String topic, MqttMessage message) throws Exception {

				//TODO affichez les messages disponibles sur un topic
				try {
//					color = Integer.parseInt(message.toString());
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
				// TODO Auto-generated method stub
				try {
					System.out.println("delivery complete : " + arg0.isComplete());
				}
				catch (Exception e){
					e.printStackTrace();
				}
				
			}
			
		});
		
		mqttClient.connect();
		//TODO souscrivez au topic
		mqttClient.subscribe(topic);
	}
}
