/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetL3;

import com.philips.lighting.hue.listener.PHLightListener;
import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.hue.sdk.bridge.impl.PHBridgeImpl;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHBridgeResource;
import com.philips.lighting.model.PHHueError;
import com.philips.lighting.model.PHLight;
import com.philips.lighting.model.PHLightState;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.paho.client.mqttv3.*;

/**
 *
 * @author moh
 */
public class Main {

   // PHHueSDK phHueSDK = PHHueSDK.create();  // or call .getInstance() effectively the same.
        
    // Register the PHSDKListener to receive callbacks from the bridge.

		
		
    // Local SDK Listener
    
	private static final String BROKER_URI = "tcp://localhost:1883";
	private static int color = 0;
    
    
    public static void main(String[] args)throws MqttSecurityException, MqttException{
    	//mqtt subscriber
		final MqttClient mqttClient = new MqttClient(BROKER_URI,	MqttClient.generateClientId());

		mqttClient.setCallback(new MqttCallback() {

			@Override
			public void messageArrived(String topic, MqttMessage message) throws Exception {

				//TODO affichez les messages disponibles sur un topic
				try {
					System.out.println("Message arrived : \"" + message.toString() + "\" on topic \""+ topic +"\"" );
					color = Integer.parseInt(message.toString());// changement de couleur
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
		MqttMessage message = new MqttMessage();
		message.setQos(0);
		message.setPayload("message from eclipse".getBytes());
		//TODO connectez le client
		mqttClient.connect();
		//TODO souscrivez au topic
		//mqttClient.subscribe("temp");
		mqttClient.subscribe("lampe");
		//TODO souscrivez au topic
		System.out.println(mqttClient.isConnected());
		
		//application hue
		
		final PHHueSDK phHueSDK =PHHueSDK.create();
        PHSDKListener listener = new PHSDKListener() {
            
            
        @Override
        public void onAccessPointsFound(List accessPoint) {
            System.out.println("1----------------------");
            PHHueSDK phHueSDK = PHHueSDK.create();
            phHueSDK.connect((PHAccessPoint) accessPoint.get(0));
            //System.out.println(phHueSDK.toString());
           // PHLightState ls = new  PHLightState();
           // ls.setX(Float.NaN);
            //phHueSDK.getSelectedBridge().updateLightState(phHueSDK.getSelectedBridge().getResourceCache().getAllLights().get(0).toString(), ls, null);
            // Handle your bridge search results here.  Typically if multiple results are returned you will want to display them in a list 
             // and let the user select their bridge.   If one is found you may opt to connect automatically to that bridge.            
        }
        
        @Override
        public void onCacheUpdated(List cacheNotificationsList, PHBridge bridge) {
             // Here you receive notifications that the BridgeResource Cache was updated. Use the PHMessageType to   
             // check which cache was updated, e.g.
            System.out.println("2----------------------");
            if (cacheNotificationsList.contains(PHMessageType.LIGHTS_CACHE_UPDATED)) {
               System.out.println("Lights Cache Updated ");
            }
        }

        @Override
        public void onBridgeConnected(PHBridge b, String username) {            
            phHueSDK.setSelectedBridge(b);
            phHueSDK.enableHeartbeat(b, PHHueSDK.HB_INTERVAL);            
            //System.out.println("4---"+b.getResourceCache().getBridgeConfiguration().getIpAddress());                       
            PHBridge bridge = PHHueSDK.getInstance().getSelectedBridge();            
            
            PHLightState lightState = new PHLightState();
            //Random rand = new Random();
            for(;;){
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            System.out.println("valeur couleur : "+ color);
            lightState.setHue(color);
            bridge.updateLightState(bridge.getResourceCache().getAllLights().get(0), lightState);
            }
 // Note, you can also call updateLightState with no PHLightListener.  Typically you should aim to use the PHLIghtLIstener, however for 
 // sporadic calls (when you are not intereMAX_HUEted in the bridge response) this is ok.
            //bridge.updateLightState(bridge.getResourceCache().getAllLights().get(1), lightState);    // light being a PHLight object obtained from the cache as in the above example.
            
// Here it is recommended to set your connected bridge in your sdk object (as above) and start the heartbeat.
            // At this point you are connected to a bridge so you should pass control to your main program/activity.
            // The username is generated randomly by the bridge.
            // Also it is recommended you store the connected IP Address/ Username in your app here.  This will allow easy automatic connection on subsequent use. 
        }

        @Override
        public void onAuthenticationRequired(PHAccessPoint accessPoint) {
            //System.out.println("5----------------------");
            phHueSDK.startPushlinkAuthentication(accessPoint);
            // Arriving here indicates that Pushlinking is required (to prove the User has physical access to the bridge).  Typically here
            // you will display a pushlink image (with a timer) indicating to to the user they need to push the button on their bridge within 30 seconds.
        }

        @Override
        public void onConnectionResumed(PHBridge bridge) {

        }

        @Override
        public void onConnectionLost(PHAccessPoint accessPoint) {
            System.out.println("------lost------------");
            // Here you would handle the loss of connection to your bridge.
        }
        
        @Override
        public void onError(int code, final String message) {
            System.out.println("err  "+message);
            // Here you can handle events such as Bridge Not Responding, Authentication Failed and Bridge Not Found
        }

        @Override
        public void onParsingErrors(List parsingErrorsList) {

// Any JSON parsing errors are returned here.  Typically your program should never return these.      
        }
    };
        
        phHueSDK.getNotificationManager().registerSDKListener(listener);
        PHBridgeSearchManager sm = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        sm.search(true, true);    
       phHueSDK.setDeviceName("Dyali");
   
    while(true);
    }
}
    
   



