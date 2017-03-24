/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subsmerger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author marcos
 */
public class SubsMerger {
    final static String EOL = "\n";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        File f = new File("C:\\Users\\marco\\OneDrive\\Documents\\NetBeansProjects\\subs_merger\\resources\\kubo.srt");
        ArrayList<String> s1 = getEvents(f, "Top");
        ArrayList<String> s2 = getEvents(f, "Bot");
        s1.addAll(s2);
        s1.sort(Comparator.naturalOrder());
        System.out.println(s1);
    }
    
    public static File merge(String s1, String s2){
        return null;
    }
    
    public static String getScriptInfo(){
        String ret =  "ScriptType: v4.00+" + EOL + 
            "Collisions: Normal" + EOL + 
            "PlayDepth: 0" + EOL + 
            "Timer: 100,0000" + EOL + 
            "Video Aspect Ratio: 0" + EOL + 
            "WrapStyle: 0" + EOL + 
            "ScaledBorderAndShadow: no";
        return ret;
    }
    
    public static String getStyles(String font, int sizeFont){
        String ret = "Format: Name,Fontname,Fontsize,PrimaryColour,SecondaryColour,OutlineColour,BackColour,Bold,Italic,Underline,StrikeOut,ScaleX,ScaleY,Spacing,Angle,BorderStyle,Outline,Shadow,Alignment,MarginL,MarginR,MarginV,Encoding" + EOL;
        ret = ret + "Style: Default," + font + "," + sizeFont + ",&H00FFFFFF,&H00FFFFFF,&H00000000,&H00000000,-1,0,0,0,100,100,0,0,1,3,0,2,10,10,10,0" + EOL;
        ret = ret + "Style: Top," + font + "," + sizeFont + ",&H00FFFFFF,&H00FFFFFF,&H00000000,&H00000000,-1,0,0,0,100,100,0,0,1,3,0,2,10,10,10,0" + EOL;
        ret = ret + "Style: Mid," + font + "," + sizeFont + ",&H00FFFFFF,&H00FFFFFF,&H00000000,&H00000000,-1,0,0,0,100,100,0,0,1,3,0,2,10,10,10,0" + EOL;
        ret = ret + "Style: Bot," + font + "," + sizeFont + ",&H00FFFFFF,&H00FFFFFF,&H00000000,&H00000000,-1,0,0,0,100,100,0,0,1,3,0,2,10,10,10,0";
        return ret;
    }
    
    public static String getAll(File fTop, File fBot, String font, int sizeFont, File dest) throws IOException{
        
        String ret = "[Script Info]\n";
        ret = ret + getScriptInfo() + EOL + EOL;
        ret = ret + "[V4+ Styles]" + EOL;
        ret = ret + getStyles(font, sizeFont) + EOL + EOL;
        
        ArrayList<String> al = getEventsMerged(fTop, fBot);
        for(String s : al){
            
        }
        
        return null;
    }
    
    public static ArrayList<String> getEventsMerged(File fTop, File fBot) throws IOException{
        ArrayList<String> alTop = getEvents(fTop, "Top");
        ArrayList<String> alBot = getEvents(fBot, "Bot");
        alTop.addAll(alBot);
        alTop.sort(Comparator.naturalOrder());
        //alTop.add(0, "[Events]");
        return alTop;
    }
    
    public static ArrayList<String> getEvents(File f, String pos) throws FileNotFoundException, IOException{
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String line, sub, time, text, time_start, time_end, aux;
        ArrayList<String> res = new ArrayList<String>();
        sub = br.readLine();
        time = br.readLine();
        text = br.readLine();
        line = br.readLine();
        while(null!=line && !line.equals("")){
            text = text + "\\N" + line;
            line = br.readLine();
        }
        text = text.replace("<i>","");
        
        while(sub!=null && time!=null && text!=null && line!=null){
            //TODO Make magic
            time = time.replace(",",".");
            time_start = time.substring(1, 11);
            time_end = time.substring(18, time.length()-1);
            aux = "Dialogue: 0," + time_start + "," + time_end + "," + pos + ",,0000,0000,0000,," + text +"\n";
            res.add(aux);
            sub = br.readLine();
            time = br.readLine();
            text = br.readLine();
            line = br.readLine();
            while(null!=line && !line.equals("")){
                text = text + "\\N" + line;
                line = br.readLine();
            }
            text = text.replace("<i>","");
        }
        return res;
    }
    
}
