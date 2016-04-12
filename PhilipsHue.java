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
import java.awt.Color;
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
public  double[] getRGBtoXY(Color c) {
        // For the hue bulb the corners of the triangle are:
        // -Red: 0.675, 0.322
        // -Green: 0.4091, 0.518
        // -Blue: 0.167, 0.04
        double[] normalizedToOne = new double[3];
        float cred, cgreen, cblue;
        cred = c.getRed();
        cgreen = c.getGreen();
        cblue = c.getBlue();
        normalizedToOne[0] = (cred / 255);
        normalizedToOne[1] = (cgreen / 255);
        normalizedToOne[2] = (cblue / 255);
        float red, green, blue;

        // Make red more vivid
        if (normalizedToOne[0] > 0.04045) {
            red = (float) Math.pow(
                    (normalizedToOne[0] + 0.055) / (1.0 + 0.055), 2.4);
        } else {
            red = (float) (normalizedToOne[0] / 12.92);
        }

        // Make green more vivid
        if (normalizedToOne[1] > 0.04045) {
            green = (float) Math.pow((normalizedToOne[1] + 0.055)
                    / (1.0 + 0.055), 2.4);
        } else {
            green = (float) (normalizedToOne[1] / 12.92);
        }

        // Make blue more vivid
        if (normalizedToOne[2] > 0.04045) {
            blue = (float) Math.pow((normalizedToOne[2] + 0.055)
                    / (1.0 + 0.055), 2.4);
        } else {
            blue = (float) (normalizedToOne[2] / 12.92);
        }

        float X = (float) (red * 0.649926 + green * 0.103455 + blue * 0.197109);
        float Y = (float) (red * 0.234327 + green * 0.743075 + blue * 0.022598);
        float Z = (float) (red * 0.0000000 + green * 0.053077 + blue * 1.035763);

        float x = X / (X + Y + Z);
        float y = Y / (X + Y + Z);

        double[] xy = new double[2];
        xy[0] = x;
        xy[1] = y;
        
        return xy;
    }

    public void setRGB(Color c , int LampNumber){
        double[] xy  = getRGBtoXY(c);
        setXY((float)xy[0], (float)xy[1], LampNumber);
    }
    
    public void setXY(float x,float y , int LampNumber){
        phHueSDK.setSelectedBridge(bridge);
        phHueSDK.enableHeartbeat(bridge, PHHueSDK.HB_INTERVAL);                                
        PHLightState lightState = new PHLightState();
        lightState.setY(y);                                  
        lightState.setX(x);
        if(LampNumber!=-1)
        bridge.updateLightState(bridge.getResourceCache().getAllLights().get(LampNumber), lightState);
        else {
            for (int i = 0; i < 3; i++) {
                bridge.updateLightState(bridge.getResourceCache().getAllLights().get(i), lightState);
            }
        }
    }
    
    
}
   




