
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class pythonCall {

    public static void CallPython() {
        // TODO Auto-generated method stub
        Process process = null;
        String shpath="/Users/junyipeng/apache-tomcat-7.0.34/webapps/Hotel/test.sh";
        String command = "/bin/sh " + shpath;

        List<String> processList = new ArrayList<String>();

        try {
            process = Runtime.getRuntime().exec(command);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = input.readLine()) != null) {
                processList.add(line);
            }
            input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (String line : processList) {
            System.out.println(line);
        }
    }
}