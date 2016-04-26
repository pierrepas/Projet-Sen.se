package philips.hue;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import static com.philips.lighting.hue.sdk.utilities.impl.PHLog.i;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mohamedouali
 */





public class Translator {
 
   

 
 
    public List<PhilipsHue> philipsHue;
    public Mqtt_pub mqtt_pub ;
    public Mqtt_sub mqtt_sub ;
    public int id;
    
    
/*
    
 Le constructeur de la classe   Translator
    
 */ 
    public Translator(int idd)
    {
      this.philipsHue = new ArrayList<PhilipsHue>();
      this.mqtt_pub =new Mqtt_pub();
      this.mqtt_sub =new Mqtt_sub();
      this.id  = idd;
      
    }
 

    Translator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void Translate(String msg) throws IOException{
        
        //-l = lamp
    // -i = ip
    // -c = color
    // -t = topic
    // -b = brightness
    // -h = help 
    
    String lampe = null;
    String ip = null ;
    String color = null;
    String topic = null;
    String brightness = null ;
    String r = null;
    String g = null;
    String b = null;
    Color co = new Color(Integer.parseInt(r), Integer.parseInt(g), Integer.parseInt(b));;
    String help= null ;
        
    String indiceUtile[]=new String[msg.split("\\s").length -1];
    indiceUtile = msg.split("\\s");
    for (int j = 0; j < indiceUtile.length; j++) {
        System.out.println(indiceUtile[j]);
                 
        switch (indiceUtile[j]) {
        case "-l":
            lampe =indiceUtile[j+1];
            break;    
        
        case "-i":
            ip =indiceUtile[j+1];
            break;    
        case "-c":
            color =indiceUtile[j+1];
            break;    
        case "-t":
            topic =indiceUtile[j+1];
            break;    
        case "-b":
            brightness =indiceUtile[j+1];
            break;    
        case "-rgb":
            int z=0;
            for (int i = 0; i <indiceUtile[j+1].length() -1 ; i++) {
                if (indiceUtile[j+1].charAt(i) != '/' ){
                    if (z==0)
                        r = r + indiceUtile[j+1].charAt(i);
                    if(z==1)
                        g = g + indiceUtile[j+1].charAt(i);
                    if(z==2)
                        b = b +indiceUtile[j+1].charAt(i);
                }
                else
                    z++;
            }
                
        break;        
        default:
            System.err.println("option  invalide  choissisait entre: \n -l (lampe) \n -i (ip) \n -c (couleur)  \n -t (topic)\n -b (brightness)\n -rgb (color rgb) \n -h(help) ");          
            
        }
    }
    
    switch (indiceUtile[0]) {
        
        case "searchBridge":
          
             this.philipsHue.get(this.id).searchBridge();
             System.out.println("recherche Bridge effectuer" );
           
            ;   
        break;    
        case "setIpAdress":
       
            this.philipsHue.get(id).setIpAdresse(ip);
            System.out.println("mettre adresse Ip "+ip);
            
        break;    
        case "setHue":
           
            philipsHue.get(this.id).setHue(this.id,Integer.parseInt(lampe));
          
           System.out.println("met la couleur "+color+" a la lampe"+lampe);
         
        break;    
    
        case "setBrightness":
         
           philipsHue.get(this.id).setBrightness(Integer.parseInt(brightness), Integer.parseInt(lampe));
           System.out.println("change la  luminosite en "+brightness+" de  la lampe "+ lampe);
      
        break;
        
        case "setBrightnessNHue":
            philipsHue.get(this.id).setBrightnessNHue(this.id, Integer.parseInt(brightness), Integer.parseInt(lampe));
        break;   
        
        case "connectToLastKnownIP":
            philipsHue.get(this.id).connectToLastKnownIP() ;
        break;
        
        case "setRGB":
            philipsHue.get(this.id).setRGB(co,Integer.parseInt(lampe));
        break; 
        
        
        default:
            System.err.println("message invalide ");             
    } 
               
   }
}
    
  
	
			
			
		

			
	