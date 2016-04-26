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
package philips.hue;

import java.io.IOException;

/**
 *
 * @author Oussqq
 */
public class app {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        //String msg="searchBridge";
         PhilipsHue p = new PhilipsHue();
         
        Translator trans =new Translator();
        trans.philipsHue.add(p);
        //trans.philipsHue.get(0).setIpAdresse("192.168.0.5");
  
        //trans.philipsHue.get(0)
        //trans.Translate(msg);
        String msg1="searchBridge";
        trans.Translate(msg1);
      
    }
    
}


