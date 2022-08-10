package ipfs_demo.example.ipfs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class IpfsController {
    @Autowired
    private IpfsService ipfsService;

    @PostMapping(value = "uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        return ipfsService.save(file);
    }
}
