package customer.stake.helpers;

import customer.stake.properties.EnvConfig;
import org.aeonbits.owner.ConfigFactory;

import java.util.HashMap;
import java.util.Map;

public class HelpersConfig {
    private static EnvConfig envConfig;
    private static final String staging = "staging";

    public static EnvConfig createConfiguration() {

        if (!isStaging()) {
            ConfigFactory.setProperty("file", "EnvConfig.properties");
        } else {
            ConfigFactory.setProperty("file", "EnvConfigStaging.properties");
        }

        Map myVars = new HashMap();
        myVars.put("env", System.getenv("env"));
        envConfig = ConfigFactory.create(EnvConfig.class, myVars);
        return envConfig;
    }

    public static boolean isStaging(){
        return System.getenv("env").equals(staging);
    }
}
