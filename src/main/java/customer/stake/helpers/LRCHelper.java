package customer.stake.helpers;

import customer.stake.enums.LabelEnums;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class LRCHelper {

    public String createLRC(String product , LabelEnums label) {
        String channel = "mobile";
        String application = "account";
        String location = "MT";
        String registrationLocation = "DE-SH";
        String domain = "games.tipico.de";
        String accepanceChannelId = "119";
        String json = "{\"channel\":\"" + channel + "\",\"application\":\"" + application + "\",\"product\":\"" + product
                + "\",\"location\":\"" + location + "\"," + "\"registrationLocation\":\"" + registrationLocation +
                "\",\"label\":\"" + label.getLabel() + "\",\"domain\":\"" + domain + "\",\"acceptChannelId\":" + accepanceChannelId + "}";
        return new String(Base64.getUrlEncoder().encode(json.getBytes(StandardCharsets.UTF_8)), StandardCharsets.ISO_8859_1);
    }
}
