package ipfs_demo.example.ipfs;

import io.ipfs.api.IPFS;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class IpfsConfig {

    IPFS ipfs;

    public IpfsConfig() {
        ipfs = new IPFS("/dnsaddr/ipfs.infura.io/tcp/5001/https");
    }

}