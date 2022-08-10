package ipfs_demo.example.ipfs;

import io.ipfs.api.IPFS;
import io.ipfs.api.MerkleNode;
import io.ipfs.api.NamedStreamable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class IpfsService implements FileServiceImpl {
    
    @Autowired
    IpfsConfig ipfsConfig;

    @Override
    public String save(MultipartFile file) {
        try {            
            IPFS ipfs = ipfsConfig.ipfs;
            InputStream inputStream = new ByteArrayInputStream(file.getBytes());
            NamedStreamable.InputStreamWrapper is = new NamedStreamable.InputStreamWrapper(inputStream);
            MerkleNode response = ipfs.add(is).get(0);            
            return response.hash.toBase58();
        } catch (IOException ex) {
            throw new RuntimeException("Error", ex);
        }
    }
}
