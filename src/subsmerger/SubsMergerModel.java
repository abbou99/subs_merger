/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subsmerger;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author marcos
 */
public class SubsMergerModel {
    final static String EOL = "\n";
    final static String CODIFICATION = "Cp1252";
    
    private static String getScriptInfo(){
        String ret =  "ScriptType: v4.00+" + EOL + 
            "Collisions: Normal" + EOL + 
            "PlayDepth: 0" + EOL + 
            "Timer: 100,0000" + EOL + 
            "Video Aspect Ratio: 0" + EOL + 
            "WrapStyle: 0" + EOL + 
            "ScaledBorderAndShadow: no";
        return ret;
    }
    
    private static String getStyles(String font, int sizeFont, Color cTop, Color cBot){
        //Get hex value of colour
        String hexTop = getHexBGR(cTop);
        String hexBot = getHexBGR(cBot);
        
        String ret = "Format: Name,Fontname,Fontsize,PrimaryColour,SecondaryColour,OutlineColour,BackColour,Bold,Italic,Underline,StrikeOut,ScaleX,ScaleY,Spacing,Angle,BorderStyle,Outline,Shadow,Alignment,MarginL,MarginR,MarginV,Encoding" + EOL;
        ret = ret + "Style: Default," + font + "," + sizeFont + ",&H00FFFFFF,&H00FFFFFF,&H00000000,&H00000000,-1,0,0,0,100,100,0,0,1,3,0,2,10,10,10,0" + EOL;
        ret = ret + "Style: Top," + font + "," + sizeFont + ",&H00"+hexTop+",&H00FFFFFF,&H00000000,&H00000000,-1,0,0,0,100,100,0,0,1,3,0,8,10,10,10,0" + EOL;
        ret = ret + "Style: Mid," + font + "," + sizeFont + ",&H0000FFFF,&H00FFFFFF,&H00000000,&H00000000,-1,0,0,0,100,100,0,0,1,3,0,5,10,10,10,0" + EOL;
        ret = ret + "Style: Bot," + font + "," + sizeFont + ",&H00"+hexBot+",&H00FFFFFF,&H00000000,&H00000000,-1,0,0,0,100,100,0,0,1,3,0,2,10,10,10,0";
        
        return ret;
    }
    
    public static String getHexBGR(Color c){
        String blue = Integer.toHexString(c.getBlue()).toString();
        String green = Integer.toHexString(c.getGreen()).toString();
        String red = Integer.toHexString(c.getRed()).toString();
        if(blue.length() == 1){
            blue = "0" + blue;
        }
        if(green.length() == 1){
            green = "0" + green;
        }
        if(red.length() == 1){
            red = "0" + red;
        }
        return blue + green + red;
    }
    
    private static String getAllContent(File fTop, File fBot, String font, int sizeFont, Color cTop, Color cBot) throws IOException{
        String ret = "[Script Info]" + EOL;
        ret = ret + getScriptInfo() + EOL + EOL;
        ret = ret + "[V4+ Styles]" + EOL;
        ret = ret + getStyles(font, sizeFont, cTop, cBot) + EOL + EOL;
        
        ret = ret + "[Events]" + EOL;
        ret = ret + "Format: Layer, Start, End, Style, Name, MarginL, MarginR, MarginV, Effect, Text" + EOL;
        ArrayList<String> al = getEventsMerged(fTop, fBot);
        for(String s : al){
            ret = ret + s + EOL;
        }
        
        return ret;
    }
    
    public static void fillMergedFile(File fTop, File fBot, String font, int sizeFont, Color cTop, Color cBot,File fDest) throws IOException{
        fDest.delete();
        fDest.createNewFile();
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(fDest, true), CODIFICATION);
        
        String content = getAllContent(fTop, fBot, font, sizeFont, cTop, cBot);
        writer.append(content);
        writer.close();
    }
    
    private static ArrayList<String> getEventsMerged(File fTop, File fBot) throws IOException{
        ArrayList<String> alTop = getEvents(fTop, "Top");
        ArrayList<String> alBot = getEvents(fBot, "Bot");
        alTop.addAll(alBot);
        alTop.sort(Comparator.naturalOrder());
        return alTop;
    }
    
    private static ArrayList<String> getEvents(File f, String pos) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f), CODIFICATION));
        String line, sub, time, text, time_start, time_end, aux;
        ArrayList<String> list = new ArrayList<>();
        sub = reader.readLine();
        time = reader.readLine();
        text = reader.readLine();
        line = reader.readLine();
        while(null!=line && !line.equals("")){
            text = text + "\\N" + line;
            line = reader.readLine();
        }
        if(text != null){
            text = text.replace("<i>","");
        }
        
        while(sub!=null && time!=null && text!=null && line!=null){
            time = time.replace(",",".");
            time_start = time.substring(1, 11);
            time_end = time.substring(18, time.length()-1);
            aux = "Dialogue: 0," + time_start + "," + time_end + "," + pos + ",,0000,0000,0000,," + text;
            list.add(aux);
            sub = reader.readLine();
            time = reader.readLine();
            text = reader.readLine();
            line = reader.readLine();
            while(null!=line && !line.equals("")){
                text = text + "\\N" + line;
                line = reader.readLine();
            }
            if(text != null){
                text = text.replace("<i>","");
            }
            
        }
        
        reader.close();
        return list;
    }
    
}
