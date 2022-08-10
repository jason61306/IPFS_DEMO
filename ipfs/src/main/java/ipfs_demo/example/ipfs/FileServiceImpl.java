package ipfs_demo.example.ipfs;

import org.springframework.web.multipart.MultipartFile;

public interface FileServiceImpl {

    String save(MultipartFile file);
}
