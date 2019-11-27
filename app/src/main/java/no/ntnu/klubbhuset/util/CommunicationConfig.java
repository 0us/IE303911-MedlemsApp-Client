package no.ntnu.klubbhuset.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CommunicationConfig {

    private final Properties prop = new Properties();
    private static CommunicationConfig single_instance = null;
    private final Context context;

    private String host;
    public static String API_URL;
    public static String ORGANIZATION = "organization";
    public static final String LOGIN = "auth";
    public static final String JOIN = "join";
    public static final String USER = "user";
    public static final String MEMBERSHIP = "membership";
    public static final String PUBLIC_KEY = "publickey.pem";
    private static final String ADMIN = "admin";
    private static final String VIPPS_URL = "https://apitest.vipps.no/";
    private static final String ACCESS_TOKEN = "accessToken/get";

    private int port;
    private String clientID;
    private String clientSecret;
    private String merchantSerialNumber;
    private String ocpApimSubscriptionKey;


    public static CommunicationConfig getInstance() {
        return single_instance;
    }

    public static CommunicationConfig getInstance(Context context) {
        if (single_instance == null) {
            single_instance = new CommunicationConfig(context);
        }
        return single_instance;
    }

    private CommunicationConfig(Context context) {
        this.context = context;
        AssetManager assetManager = context.getAssets();
        try {
            InputStream inputStream = assetManager.open("connection.properties");
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        initializeValues();
    }

    public static String joinClub(long id) {
        return API_URL + ORGANIZATION + "/" +  id + "/" + JOIN;
    }

    public static String getVippsTokenURL() {
        return VIPPS_URL + ACCESS_TOKEN;
    }

    public static String checkHasPaid(long id) {
        return API_URL + ORGANIZATION + "/" + id + "/" + ADMIN;
    }

    private void initializeValues() {
        this.host = retrieveHost();
        this.port = retrievePort();
        this.clientID = retrieveClientID();
        this.clientSecret = retrieveClientSecret();
        this.ocpApimSubscriptionKey = retrieveOcpApimSubscriptionKey();
        this.merchantSerialNumber = retrieveMerchantSerialNumber();
        API_URL = "http://" + host + ":" + port + "/api/";
    }

    private int retrievePort() {
        return Integer.parseInt(prop.getProperty("port"));
    }

    private String retrieveHost() {
        return prop.getProperty("host");
    }

    public String retrieveClientID() {
     return prop.getProperty("client_id");
    }

    public String retrieveClientSecret() {
        return prop.getProperty("client_secret");
    }

    public String retrieveOcpApimSubscriptionKey() {
        return prop.getProperty("Ocp-Apim-Subscription-Key");
    }

    public String retrieveMerchantSerialNumber() {
        return prop.getProperty("merchantSerialNumber");
    }
}
