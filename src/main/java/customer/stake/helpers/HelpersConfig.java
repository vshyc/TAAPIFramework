package customer.stake.helpers;

import customer.stake.properties.EnvConfig;
import org.aeonbits.owner.ConfigFactory;

public class HelpersConfig {
    public static EnvConfig envConfig = ConfigFactory.create(EnvConfig.class);

}
