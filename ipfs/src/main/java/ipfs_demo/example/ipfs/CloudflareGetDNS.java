package ipfs_demo.example.ipfs;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class CloudflareGetDNS implements CloudflareSetupImpl{
    
    @Value("${cloudflare.X-Auth-Email}")
    private String XAuthEmail;
    @Value("${cloudflare.X-Auth-Key}")
    private String XAuthKey;
    @Value("${cloudflare.Content-Type}")
    private String ContentType;

    @Override
    public JSONObject setup(String method, URL url, JSONObject content){        
        HttpURLConnection con;
        try {
            con = (HttpURLConnection) url.openConnection();                          
            con.setDoOutput(true);
            con.setDoInput(true);            
            con.setRequestMethod(method);           
            con.setRequestProperty("X-Auth-Email", XAuthEmail);
            con.setRequestProperty("X-Auth-Key", XAuthKey);
            con.setRequestProperty("Content-Type", ContentType);            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;           
            StringBuffer buffer = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                buffer.append(inputLine);
            }
            in.close();
            return new JSONObject(buffer.toString());             
        } catch (IOException ex) {
            throw new RuntimeException("Error", ex);
        }
    }
}
