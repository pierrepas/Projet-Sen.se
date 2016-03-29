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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


/**
 *
 * @author moh
 */
public class PhilipsHue {       
    private PHHueSDK phHueSDK ;    
    private PHBridge bridge;        
    private String IpAdresse;
    private String UserName ; 
    private String FileName  ; 
    private boolean  connected ;
    private PHAccessPoint accessP ;            
    private PHSDKListener listener ;

    public void wirteInFile(String[] str , int size) throws IOException{
        PrintWriter pr = new PrintWriter(new FileWriter(FileName));
        for (int i = 0; i < size; i++) {
            pr.println(str[i]);
        }
        pr.close();
    }
    
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
    
    public void setHue(int Hue,String IpAdresse,String UserName) throws InterruptedException{
        accessP.setIpAddress(IpAdresse);
        accessP.setUsername(UserName);
        try{
        while(!phHueSDK.isAccessPointConnected(accessP)){
            Thread.sleep(500);
            phHueSDK.connect(accessP);   
        }
        }
        catch(Exception e) {
        PHLightState lightState = new PHLightState();
        lightState.setHue(Hue);         
        
        PHBridge b = phHueSDK.getAllBridges().get(0);
        for (int i = 0; i < 3; i++) {
                b.updateLightState(b.getResourceCache().getAllLights().get(i), lightState);
            }
        }
    }
    
    
    public void getIpFromFile() throws FileNotFoundException, IOException{        
        File file = new File(FileName);
        BufferedReader br = new BufferedReader(new FileReader(file));        
        IpAdresse=br.readLine();        
        UserName=br.readLine();                
        br.close();
    }
    
    public void connectToLastKnownIP() throws IOException{
        getIpFromFile();
        accessP.setIpAddress(IpAdresse);
        accessP.setUsername(UserName);
        try{
        while(!phHueSDK.isAccessPointConnected(accessP)){
            Thread.sleep(500);
            phHueSDK.connect(accessP);   
        }
        }
        catch(Exception e) {                        
        bridge = phHueSDK.getAllBridges().get(0);
        connected = true ;
        }
    }
    
    public void searchBridge(){
        phHueSDK.getNotificationManager().registerSDKListener(listener);
        PHBridgeSearchManager sm = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        sm.search(true, true);  
    }

    public PHBridge getBridge() {
        return bridge;
    }

    public String getIpAdresse() {
        return IpAdresse;
    }

    public String getUserName() {
        return UserName;
    }

    public void setBridge(PHBridge bridge) {
        this.bridge = bridge;
    }

    public void setIpAdresse(String IpAdresse) {
        this.IpAdresse = IpAdresse;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }
    
    public boolean isConnected(){
        return connected ;
    }

    public void setFileName(String FileName) {
        this.FileName = FileName;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public String getFileName() {
        return FileName;
    }

    public PhilipsHue() {
        phHueSDK = PHHueSDK.create();
        FileName = "LastKnownIP";
        connected = false ; 
        accessP = new PHAccessPoint();
        listener = new PHSDKListener() {                        
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
            connected = true;            
            try {
                PrintWriter pr = new PrintWriter(new FileWriter(FileName));                
                pr.println(b.getResourceCache().getBridgeConfiguration().getIpAddress());                
                pr.println(b.getResourceCache().getBridgeConfiguration().getUsername());                
                pr.close();
            }
            catch(Exception e){
                e.printStackTrace();                
            }
                    
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
        try{
            getIpFromFile();
        }
        catch(Exception e){
            System.out.println("file doesn't exist");
            e.printStackTrace();
        }
    }
    
}
   




