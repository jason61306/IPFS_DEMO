package ipfs_demo.example.ipfs;

import org.json.JSONObject;
import java.net.URL;

public interface CloudflareSetupImpl {

    JSONObject setup(String method, URL url, JSONObject content);
}
