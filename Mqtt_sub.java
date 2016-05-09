package sen.se;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

public class Mqtt_sub {
	private static String topic;
	private static String BROKER_URI = "tcp://localhost:1883";
	private static Translator t;
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

	public static void main(String[] args) {
            topic = "temp";
            t = new Translator(0);
            /*le 1e argument correspond a l'adresse ip de Mosquitto et le */
            if(args.length!=0){
                    if (args[0].equals("help")){
                        System.out.println("Sen.se [protocol://@ip:port] [topic]");
                        System.exit(0);
                    }
                    else
                        BROKER_URI = args[0];
            }   
            if(args.length>1)
                    topic = args[1];
                
            try {
                mqttClient = new MqttClient(BROKER_URI,	MqttClient.generateClientId());
            } catch (MqttException ex) {
                Logger.getLogger(Mqtt_sub.class.getName()).log(Level.SEVERE, null, ex);
            }
		
                
		mqttClient.setCallback(new MqttCallback() {
                    
			@Override
			public void messageArrived(String topic, MqttMessage message) throws Exception {

				//TODO affichez les messages disponibles sur un topic
				try {
//					color = Integer.parseInt(message.toString());
					messageMQTT = message;
                                        
					System.out.println("Message arrived : \"" + message.toString() + "\" on topic \""+ topic +"\"" );
                                        t.Translate(message.toString());
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
                                        //System.exit(0);
				}
				catch (Exception e){
					e.printStackTrace();
				}
				
			}
			
		});
		try {
                    mqttClient.connect();
                } catch (Exception e) {
                    System.out.println("connection faild !!");
                    System.out.println("invalid ip addresse !!");
                    System.out.println("type \"Sense help\" for help");
                    System.exit(0);
                }
		
            try {
                //TODO souscrivez au topic
                mqttClient.subscribe(topic);
            } catch (MqttException ex) {
                Logger.getLogger(Mqtt_sub.class.getName()).log(Level.SEVERE, null, ex);
            }
	}
}
