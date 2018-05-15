package soundeffects;
/*
import javafx.scene.media.AudioClip;
import java.applet.Applet;
import java.net.URL;
 
public class Sound {
    private static Sound staticSound=new Sound();
    public String name;
    public AudioClip sound;
    public int isPlayed;
    public boolean isOn;
    private Sound(){}
 
    public Sound(String name, URL url){
        this.name=name;
        this.isPlayed = 0;
        this.isOn = false;
        try{
            java.applet.AudioClip sound =  Applet.newAudioClip(url);
        }catch(Exception e){
            System.out.println(e);
        }
    }
    public void play(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(sound !=null)
                    sound.play();
            }
        }).start();
        this.isPlayed++;
        this.isOn = true;
    }
    public void loop() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (sound != null)
                    staticSound.loop();
            }
        }).start();
    }
    public void stop() {
       if (sound != null){
           sound.stop();
       }
       this.isOn = false;
    }
    public static URL getURL(String fileName){
        return staticSound.getClass().getResource(fileName);
    }
}
*/