/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sen.se;

/**
 *
 * @author moh
 */
import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHLightState;
import java.util.List;


/**
 *
 * @author moh
 */
public class PhilipsHue {       
    private PHHueSDK phHueSDK = PHHueSDK.create();
    private PHHueSDK phHueSDKM = PHHueSDK.create();
    private PHBridge bridge;        
    private String IpAdresse;
    private String UserName ; 
    private PHAccessPoint accessP = new PHAccessPoint() ;
    private PHSDKListener listener = new PHSDKListener() {                        
        @Override
        public void onAccessPointsFound(List accessPoint) {            
            PHHueSDK phHueSDK = PHHueSDK.create();
            phHueSDK.connect((PHAccessPoint) accessPoint.get(0));
        }
        
        @Override
        public void onCacheUpdated(List cacheNotificationsList, PHBridge bridge) {                         
            if (cacheNotificationsList.contains(PHMessageType.LIGHTS_CACHE_UPDATED)) {
               System.out.println("Lights Cache Updated ");
            }
        }

        @Override
        public void onBridgeConnected(PHBridge b, String username) {                                    
            System.out.println("connect !!");
            bridge = b ;             
        }

        @Override
        public void onAuthenticationRequired(PHAccessPoint accessPoint) {            
            phHueSDK.startPushlinkAuthentication(accessPoint);            
        }

        @Override
        public void onConnectionResumed(PHBridge bridge) {

        }

        @Override
        public void onConnectionLost(PHAccessPoint accessPoint) {
            System.out.println("------lost------------");            
        }
        
        @Override
        public void onError(int code, final String message) {
            System.out.println("err  "+message);            
        }

        @Override
        public void onParsingErrors(List parsingErrorsList) {

        }
    };         

    public void setHue(int Hue,int LampNumber){
        phHueSDK.setSelectedBridge(bridge);
        phHueSDK.enableHeartbeat(bridge, PHHueSDK.HB_INTERVAL);                                
        PHLightState lightState = new PHLightState();
        lightState.setHue(Hue);                                  
        if(LampNumber!=-1)
        bridge.updateLightState(bridge.getResourceCache().getAllLights().get(LampNumber), lightState);
        else {
            for (int i = 0; i < 3; i++) {
                bridge.updateLightState(bridge.getResourceCache().getAllLights().get(i), lightState);
            }
        }
    }
    public void setBrightness(int Brightness,int LampNumber){
        phHueSDK.setSelectedBridge(bridge);
        phHueSDK.enableHeartbeat(bridge, PHHueSDK.HB_INTERVAL);                                
        PHLightState lightState = new PHLightState();
        lightState.setBrightness(Brightness);                                  
        if(LampNumber!=-1)
        bridge.updateLightState(bridge.getResourceCache().getAllLights().get(LampNumber), lightState);
        else {
            for (int i = 0; i < 3; i++) {
                bridge.updateLightState(bridge.getResourceCache().getAllLights().get(i), lightState);
            }
        }
    }
    
    public void setBrightnessNHue(int Hue,int Brightness,int LampNumber){
        phHueSDK.setSelectedBridge(bridge);
        phHueSDK.enableHeartbeat(bridge, PHHueSDK.HB_INTERVAL);                                
        PHLightState lightState = new PHLightState();
        lightState.setHue(Hue);                                  
        lightState.setBrightness(Brightness);               
        if(LampNumber!=-1)
        bridge.updateLightState(bridge.getResourceCache().getAllLights().get(LampNumber), lightState);
        else {
            for (int i = 0; i < 3; i++) {
                bridge.updateLightState(bridge.getResourceCache().getAllLights().get(i), lightState);
            }
        }
    }
    
    public void setHue(int Hue,String IpAdresse,String UserName){
        accessP.setBridgeId(IpAdresse);
        accessP.setUsername(UserName);
        phHueSDKM.connect(accessP);
        PHLightState lightState = new PHLightState();
        lightState.setHue(Hue);         
        phHueSDKM.setCurrentLightState(lightState);        
    }
    
    public void searchBridge(){
        phHueSDK.getNotificationManager().registerSDKListener(listener);
        PHBridgeSearchManager sm = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        sm.search(true, true);  
    }
}
   




