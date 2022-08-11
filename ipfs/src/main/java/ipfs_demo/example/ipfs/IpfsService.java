package ipfs_demo.example.ipfs;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@Service
public class IpfsService implements FileServiceImpl {
    
    @Autowired
    IpfsConfig ipfsConfig;

    @Autowired
    CloudflareGetDNS cloudflareGetDNS;

    @Autowired
    CloudflareSetDNS cloudflareSetDNS;

    @Value("${cloudflare.Zone}")
    private String zone;
    @Value("${cloudflare.URL}")    
    private String cfURL;

    @Override
    public String save(MultipartFile file) {
        try {            
            IPFS ipfs = ipfsConfig.ipfs;
            InputStream inputStream = new ByteArrayInputStream(file.getBytes());
            NamedStreamable.InputStreamWrapper is = new NamedStreamable.InputStreamWrapper(inputStream);
            MerkleNode response = ipfs.add(is).get(0);
            
            // Cloudflare Setup
            // get dns record id
            URL url= new URL("https://api.cloudflare.com/client/v4/zones/"+ zone +"/dns_records?type=TXT&name=_dnslink." + cfURL + "&match=all");
            JSONObject getDNSResult = cloudflareGetDNS.setup("GET", url, null);            
            String id = getDNSResult.getJSONArray("result").getJSONObject(0).getString("id"); 
            // update dns
            url= new URL("https://api.cloudflare.com/client/v4/zones/"+ zone +"/dns_records/" + id);
            JSONObject data = new JSONObject();            
            data.put("type","TXT");
            data.put("name","_dnslink" + cfURL);
            data.put("content","dnslink=/ipfs/" + response.hash.toBase58());
            JSONObject setDNSResult = cloudflareSetDNS.setup("PUT", url, data);                                                    

            return setDNSResult.toString();
        } catch (IOException ex) {
            throw new RuntimeException("Error", ex);
        }
    }
}
