package customer.stake.helpers;

import customer.stake.properties.EnvConfig;
import org.aeonbits.owner.ConfigFactory;

public class HelpersConfig {
    private static EnvConfig envConfig;

    public static EnvConfig createConfiguration() {
        envConfig = ConfigFactory.create(EnvConfig.class);
        return envConfig;
    }
}
