/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sen.se;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHHueSDK;
import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/** @author 
 * Hassainia Mohamed
 * Souissi Oussama
 * Jommetti Leevan
 * Vouillamoz Fred
 * Ouali-Alami Mohamed
 * Pasquier Pierre
 * Petre Rémy
 * Charbonnier Jonathan
 * Priscoglio Florent
 * Ziadeh Mohamad
 */

public class SenSe {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    /*
    public static void main(String[] args) throws InterruptedException, IOException {        
        PhilipsHue ph = new PhilipsHue() ;
        //ph.getBridge().findNewLights(null);
        //PHHueSDK p = PHHueSDK.create();
        //PHAccessPoint acp = new PHAccessPoint();
        
        //acp.setIpAddress("localhost");
        //acp.setUsername("newdeveloper");
        //ph.searchBridge();
        //p.connect(acp);
        
        
        //ph.setHue(10000, "localhost", "newdeveloper");
        //ph.searchBridge();
        //while(!ph.isConnected())
        //    Thread.sleep(200);
        //ph.searchBridge();
        //ph.connectToLastKnownIP("/home/moh/NetBeansProjects/Sen.se/LastKnownIP");
        ph.connect("192.168.1.2:80", "newdeveloper");        
        //while(!ph.isConnected());
        Thread.sleep(7000);
        System.out.println(ph.isConnected());
        Color c = new Color(230,000,160);
        ph.setRGBtoRangeOL(3, c);
        //ph.setXY((float)138/1000, (float)80/1000, 1);
        //ph.setHue(30000, 1);
        ph.setBrightness(255, 0);
        //ph.getPhHueSDK().getNotificationManager().unregisterSDKListener(ph.getListener());
        //ph.getPhHueSDK().disableAllHeartbeat();
        //ph.getPhHueSDK().disconnect(ph.getBridge());
        
    }
    */
}
