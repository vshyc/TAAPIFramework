package customer.stake.helpers;

import customer.stake.enums.CountryCodes;
import customer.stake.dto.helpers.Payment;
import customer.stake.properties.EnvConfig;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class PaymentHelper {
    private String iOvationBlackbox = "0400mTR4Z7+BQcwNf94lis1ztlYPSRNJ9+Z71sWGH1YgmA8vLho9WHMkbDW/QQyRgqWz6PCtvZahjn1b" +
            "d06+3MpDhXYpPgQiEVyiFhADfeGQZO28FbUrrO5N6T/KhUgGeNqKfeXqFvOptJPXhuTAPI8MW/1AdkiD01/5S8Yxor2ghGe3vqFNns8qxhn" +
            "q5+cU0zh7+N9BVU8sN5ydDWSbfq2226tWOlIv+2+1qcesP69MTO+CuJoygDKlC7I+vS3zO1UWKyNRXh0mw7Kb1x4cMpEKIrr1bNFEQS2w" +
            "pXjqvRKtNIku7P4czcXXKf0uE/n/nVncn4Ur8wejOpTqT3LYd4+PRRAQa6HsZBJWP4L8Vo6OFGIWIUEZ02TLZWdPkPG4KtJbUlxmheCt" +
            "4oW5Nd5TQYAdY6D0ay5Bj1vO2/Ec1nc2SuEaTRn0G1NCZdqF8c1RfaOfdOIYgHC/3QY1fZwSVtWNySwAqo7XEZwLdYgf2acO/aHvUSbJ+" +
            "Ltz3R3+1lLYqHZE/WP/gKuIDtVj3EvpfhQWBVbrfzJFk+CKKULPxBs5mS+drssE9JDxLa72ZUe9tkMg1BRAmVFr2di5AUf1ExG2N7VNM6" +
            "E+MU6gT+HoQVdeZcSg5shREINBckcOjS9hgMLfJMLraAXmI9htrNHzt95ePgXs+oQQc60trrGto44dFZ8zqbwpmWa8IyMAAWnl4iPM+F9+" +
            "VNHlcz0waZWk5d3ocrOgSQ0Fzflca1WxiZ1a0z33/rTYqQrmLD1SgAMgvGuiISKiq4yvoHcgBVMDdOYV885IvsRSnt/S1YYITyzRwv8PXXp" +
            "Oa/Fh/Q7T6v45cktLrI9wc9lbzgdLOyJAeImdJ+shaHYP8Oq6k+cVXJiV2yKAmeNHH6+w6hxMG7DoNMZnexhdfmUWNRDh9gCI/kQ+E057z4" +
            "vjGvD/eoIUVuEAOTWvj85YFHCM4u6cl1e7RBG2utaSRlnW6xEmNLiVb9yeQyKPrXtOAtq5Es4riAHFTy+bPrXOHMUHV07u3A0Lndru5VcJh" +
            "b/GJh05ixvNZW12rJw1QU6Cq/s0iuv6Xcf4KsKejrJQ3TmN7MTFWlEaiSCED5K3d//fiQ7giuQ3jY8zakdHG/wAa4UosCoTsInSNrmbt1mV" +
            "TbhWyLt5a/h+QQ11JTq+F9A702BTZSXdOArnDzWecUuee8sz3PKdFXWeFopwPpPV0ZSMvweyEcL9bDNtp5KCvUhvIhkVyF0lbrxL9m9yYip" +
            "cIGdPhRrtzbR61jU4LMmyetn+Q5xuroXiCstGIyJUkimf3o0=";
    private EnvConfig envConfig = HelpersConfig.createConfiguration();

    public Response payIn(String sessionId, String jSessionId, String slaveId, String language, double value, String origin) {
        String cookie = String.format("SESSION_ID=%s;JSESSIONID=%s;SLAVE_ID=%s;language=%s",
                sessionId, jSessionId, slaveId, language);

        return given().baseUri(envConfig.paymentDeUrl()).basePath(envConfig.paymentPath())
                .contentType("application/vnd.tipico.sofort.payin-v1+json").header("mobileProductClientType", "DESKTOP_WEB_RETH")
                .header("OriginRefer", envConfig.paymentDeUrl())
                .header("X-Forwarded-For", "10.2.7.9")
                .header("X-Forwarded-Host", envConfig.paymentDeUrl())
                .header("TPAPI-Domain", CountryCodes.GERMANY.getCountryCode())
                .header("TPAPI-Client-Type", "DESKTOP_WEB_RETH")
                .header("Product-Platform", "desktop")
                .header("Product-Id", origin)
                .header("Cookie", cookie)
                .body(Payment.builder().amount(value)
                        .bonusId("no-bonus").iovationBlackbox(iOvationBlackbox).sofortShDetails(null)
                        .origin(origin).build()
                ).post("/sofort");
    }
}
