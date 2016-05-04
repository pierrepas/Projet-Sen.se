/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 
package philips.hue;


import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

/**
 *
 * @author moh
 
public class app {
   public static void main(String[] args){               
        PhilipsHue p = new PhilipsHue();
        p.setBROKER_URI("tcp://localhost:1883");
        p.setTopic("topic");
        p.phHueSDK.getNotificationManager().registerSDKListener(p.listener);
       // PHBridgeSearchManager sm = (PHBridgeSearchManager) p.phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        //sm.ipAddressSearch();           
    } 
}

*/

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetL3;

import java.awt.Color;
import java.io.IOException;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.model.PHBridge;

/**
 *
 * @author Oussqq
 */
public class App {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     * @throws InterruptedException 
     * @throws MqttException 
     * @throws MqttSecurityException 
     */
    public static void main(String[] args) throws IOException, InterruptedException, MqttSecurityException, MqttException {
        //String msg="searchBridge";
    	//final PHHueSDK phHueSDK =PHHueSDK.create();
        PhilipsHue p = new PhilipsHue();
        Translator t = new Translator(p,0);
        
        
        //boolean x = p.getIpUserFromFile();
        //System.out.println(x);
        //p.connect("127.0.1.1:80", "newdeveloper");
        System.out.println("adresse ip : " + p.getIpAdresse()+ " user : "+ p.getUserName());
        System.out.println("connexion etablie : " + p.isConnected());
//        if(! p.connectToLastKnownIP()) {
//			System.out.println("le fichier n'existe pas !");
//		}
        
        //p.searchBridge();
        
        //p.connectToLastKnownIP();
        
        
        //p.searchBridge();
        //System.out.println("connexion etablie : " + p.isConnected());
        Mqtt_sub subscriber = new Mqtt_sub();
        
   
        MqttMessage firstMessage = subscriber.getMessageMQTT();
        String color;
        while (!p.isConnected()){
        	if(firstMessage == null){
        		Thread.sleep(1000);
        		System.out.println(" message " + firstMessage + " connected : " + p.isConnected());
        		firstMessage = subscriber.getMessageMQTT();
        	}
        	else{
        		t.Translate(firstMessage.toString());
        	}
        	
    	
        }
        System.out.println("connexion etablie : " + p.isConnected());

    	
    	MqttMessage oldMessage = firstMessage;
        
        while (true) {
        	//Thread.sleep(1000);
        	MqttMessage newMessage = subscriber.getMessageMQTT();
       	
        	//si l'ancien message n'est pas pareil que le nouveau, 
        	//alors on change l'etat de la lampe sinon on attend que les messages soient differents
        	//cela voudra dire qu'un nouveau message est arrivé
        	
        	if (!(oldMessage.toString().contentEquals(newMessage.toString()))) {
//        		color = newMessage.toString();
//        		 
        		oldMessage = newMessage;
//        		 
//        		switch (color) {
//        		case "red":
//        			for (int i = 0; i < 3; i++) {
//        				p.setRGB(Color.red,i);
//        			}
//        			break;
//        		case "blue":
//        			for (int i = 0; i < 3; i++) {
//        				p.setRGB(Color.blue,i);
//        			}
//        			break;
//        		default:
//        			for (int i = 0; i < 3; i++) {
//						p.setRGB(Color.white,i);
//					}
//		           	break;
//				}
        		t.Translate(newMessage.toString());
        	}
        	
 //       	else {
//        	    for (int i = 0; i < 3; i++) {
//        			Color c = new Color(0,0,0);
//        			p.setRGB(c, i);
//        	    }
//			}
        }
        
         
//        Translator trans = new Translator(0);
//        trans.philipsHue.add(p);
//        
        //trans.philipsHue.get(0).setIpAdresse("192.168.0.5");
  
        //trans.philipsHue.get(0)
        //trans.Translate(msg);
        //String msg1="searchBridge";
        //trans.Translate(msg1);
      
    }
    
}



