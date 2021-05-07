package customer.stake.properties;


import org.aeonbits.owner.Config;

@Config.Sources("classpath:${file}")
public interface EnvConfig extends Config {


    @DefaultValue("OCEANS11")
    String env();

    @Key("BASE_O11_URI")
    String baseUri();

    @Key("BASE_PATH")
    String basePath();

    @Key("LIMITS_PATH")
    String limitsPath();

    @Key("AUTH_BASE_URI")
    String authBaseUri();

    @Key("AUTH_BASE_PATH")
    String authBasePath();

    @Key("BASIC_USER")
    String basicUser();

    @Key("BASIC_PASSWORD")
    String basicPassword();

    @Key("AUTH_USER")
    String authUser();

    @Key("AUTH_PASSWORD")
    String authPassword();

    @Key("ACCOUNT_DE_URI")
    String accountDeUrl();

    @Key("CRFES_PATH")
    String crfesPath();

    @Key("WEB_HOLDER_URL")
    String holderUrl();

    @Key("WEB_TEST_API_PATH")
    String webTestApiPath();



}
