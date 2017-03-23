/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subsmerger;

import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

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
        return null;
    }
    
    public static String getStyles(String font, int sizeFont){
        return null;
    }
    
    public static String getScriptInfoAndStyles(){
        return null;
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
        text.replace("<i>","");
        
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
        }
        return res;
    }
    
}
