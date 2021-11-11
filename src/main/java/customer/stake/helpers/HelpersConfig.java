package customer.stake.helpers;

import customer.stake.properties.EnvConfig;
import org.aeonbits.owner.ConfigFactory;

import java.util.HashMap;
import java.util.Map;

public class HelpersConfig {
    static EnvConfig envConfig;

    public static EnvConfig createConfiguration() {
        if (!System.getenv("env").equals("staging")) {
            ConfigFactory.setProperty("file", "EnvConfig.properties");
        } else {
            ConfigFactory.setProperty("file", "EnvConfigStaging.properties");
        }

        Map myVars = new HashMap();
        myVars.put("env", System.getenv("env"));
        envConfig = ConfigFactory.create(EnvConfig.class, myVars);
        return envConfig;
    }
}
