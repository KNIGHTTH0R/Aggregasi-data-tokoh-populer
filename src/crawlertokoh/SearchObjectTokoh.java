package crawlertokoh;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchObjectTokoh {
    public void Kontekstual(String line, String file_result){
        String reg0          =  "\\s* Gubernur \\s*([^\\s]*)";
//        String reg1          =  "\\s* Presiden \\s*([^\\s]*)";
//        String reg2          =  "\\s* Wakil Presiden \\s*([^\\s]*)";
//        String reg3          =  "\\s* Walikota \\s*([^\\s]*)";
//        String reg4          =  "\\s* Mentri \\s*([^\\s]*)";
//        String reg5          =  "\\s* Ketua \\s*([^\\s]*)";
//        String reg6          =  "\\s* Wakil Ketua \\s*([^\\s]*)";
//        String reg7          =  "\\s* Bendahara \\s*([^\\s]*)";
        File file           =  new File(file_result);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWritter = new FileWriter(file.getAbsoluteFile(),true);
            
            Pattern r       =  Pattern.compile(reg0);
            Matcher m       =  r.matcher(line);
            if(m.find()){
                try (BufferedWriter bufferWritter = new BufferedWriter(fileWritter)) {
                    bufferWritter.write(m.group()+"\n");
                }
            }

        } catch (IOException e) {
        }
    }
}