package crawlertokoh;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchObjectTokoh {
	
    public void RegexKetua(String line, String file_result){
        
        /**
         * PR 
         */
        String reg          =  "(?<=Ketua Umum: )(.*)(?=<br />)";
        File file           =  new File(file_result);
        try {
            Pattern r       =  Pattern.compile(reg);
            Matcher m       =  r.matcher(line);
            
            if(m.find()){
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileWriter fileWritter = new FileWriter(file.getAbsoluteFile(),true);
                try (BufferedWriter bufferWritter = new BufferedWriter(fileWritter)) 
                {
                    bufferWritter.write(m.group()+"\n");
                }
            }else{
                System.out.println("NO MATCH");
            }

        } catch (IOException e) {
        }
    }
}
