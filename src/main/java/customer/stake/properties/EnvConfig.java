package customer.stake.properties;


import org.aeonbits.owner.Config;

@Config.Sources("classpath:${file}")
public interface EnvConfig extends Config {


    @DefaultValue("OCEANS11")
    String env();

    @Key("BASE_URI")
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

    @Key("PAYMENT_DE_URI")
    String paymentDeUrl();

    @Key("CRFES_PATH")
    String crfesPath();

    @Key("WEB_HOLDER_URL")
    String holderUrl();

    @Key("WEB_TEST_API_PATH")
    String webTestApiPath();

    @Key("CAFES_PATH")
    String cafesPath();

    @Key("PAYMENT_PATH")
    String paymentPath();

    @Key("RGFES_PATH")
    String rgfesPath();

    @Key("TAC_PATH")
    String tacPath();

    @Key("TAC_PASS")
    String tacPass();

    @Key("TAC_BASE_URI")
    String tacBaseUri();

}
