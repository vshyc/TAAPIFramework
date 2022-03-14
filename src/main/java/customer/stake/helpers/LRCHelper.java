package customer.stake.helpers;

import customer.stake.enums.Label;

import customer.stake.enums.Product;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class LRCHelper {
    /*
    LRC - Licence Region Context = encoded with Base64 json String with information about application , product, label,
    channel, location, registration Location, domain and acceptance channel id.
     */

    private final String channel = "mobile";
    private final String application = "account";
    private final String location = "MT";
    private final String registrationLocation = "DE-SH";
    private final String domain = "games.tipico.de";
    private final String accepanceChannelId = "119";

    public String createLRC(Product product, Label label) {
        String json = "{\"channel\":\"" + channel + "\",\"application\":\"" + application + "\",\"product\":\"" + product.getProduct()
                + "\",\"location\":\"" + location + "\"," + "\"registrationLocation\":\"" + registrationLocation +
                "\",\"label\":\"" + label.getLabel() + "\",\"domain\":\"" + domain + "\",\"acceptChannelId\":" + accepanceChannelId + "}";
        return new String(Base64.getUrlEncoder().encode(json.getBytes(StandardCharsets.UTF_8)), StandardCharsets.ISO_8859_1);
    }

    public String createLRC(Product product, Label label, String channel) {
        String json = "{\"channel\":\"" + channel + "\",\"application\":\"" + application + "\",\"product\":\"" + product.getProduct()
                + "\",\"location\":\"" + location + "\"," + "\"registrationLocation\":\"" + registrationLocation +
                "\",\"label\":\"" + label.getLabel() + "\",\"domain\":\"" + domain + "\",\"acceptChannelId\":" + accepanceChannelId + "}";
        return new String(Base64.getUrlEncoder().encode(json.getBytes(StandardCharsets.UTF_8)), StandardCharsets.ISO_8859_1);
    }

    public String createLRC(Product product, Label label, String channel, String application) {
        String json = "{\"channel\":\"" + channel + "\",\"application\":\"" + application + "\",\"product\":\"" + product.getProduct()
                + "\",\"location\":\"" + location + "\"," + "\"registrationLocation\":\"" + registrationLocation +
                "\",\"label\":\"" + label.getLabel() + "\",\"domain\":\"" + domain + "\",\"acceptChannelId\":" + accepanceChannelId + "}";
        return new String(Base64.getUrlEncoder().encode(json.getBytes(StandardCharsets.UTF_8)), StandardCharsets.ISO_8859_1);
    }

    public String createLRC(Product product, Label label, String channel, String application, String location) {
        String json = "{\"channel\":\"" + channel + "\",\"application\":\"" + application + "\",\"product\":\"" + product.getProduct()
                + "\",\"location\":\"" + location + "\"," + "\"registrationLocation\":\"" + registrationLocation +
                "\",\"label\":\"" + label.getLabel() + "\",\"domain\":\"" + domain + "\",\"acceptChannelId\":" + accepanceChannelId + "}";
        return new String(Base64.getUrlEncoder().encode(json.getBytes(StandardCharsets.UTF_8)), StandardCharsets.ISO_8859_1);
    }

    public String createLRC(Product product, Label label, String channel, String application, String location,
                            String registrationLocation) {
        String json = "{\"channel\":\"" + channel + "\",\"application\":\"" + application + "\",\"product\":\"" + product.getProduct()
                + "\",\"location\":\"" + location + "\"," + "\"registrationLocation\":\"" + registrationLocation +
                "\",\"label\":\"" + label.getLabel() + "\",\"domain\":\"" + domain + "\",\"acceptChannelId\":" + accepanceChannelId + "}";
        return new String(Base64.getUrlEncoder().encode(json.getBytes(StandardCharsets.UTF_8)), StandardCharsets.ISO_8859_1);
    }

    public String createLRC(Product product, Label label, String channel, String application, String location,
                            String registrationLocation, String domain) {
        String json = "{\"channel\":\"" + channel + "\",\"application\":\"" + application + "\",\"product\":\"" + product.getProduct()
                + "\",\"location\":\"" + location + "\"," + "\"registrationLocation\":\"" + registrationLocation +
                "\",\"label\":\"" + label.getLabel() + "\",\"domain\":\"" + domain + "\",\"acceptChannelId\":" + accepanceChannelId + "}";
        return new String(Base64.getUrlEncoder().encode(json.getBytes(StandardCharsets.UTF_8)), StandardCharsets.ISO_8859_1);
    }

    public String createLRC(Product product, Label label, String channel, String application, String location,
                            String registrationLocation, String domain, String accepanceChannelId) {
        String json = "{\"channel\":\"" + channel + "\",\"application\":\"" + application + "\",\"product\":\"" + product.getProduct()
                + "\",\"location\":\"" + location + "\"," + "\"registrationLocation\":\"" + registrationLocation +
                "\",\"label\":\"" + label.getLabel() + "\",\"domain\":\"" + domain + "\",\"acceptChannelId\":" + accepanceChannelId + "}";
        return new String(Base64.getUrlEncoder().encode(json.getBytes(StandardCharsets.UTF_8)), StandardCharsets.ISO_8859_1);
    }
}
