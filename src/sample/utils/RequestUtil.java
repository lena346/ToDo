package sample.utils;

import sample.models.User;
import com.google.gson.Gson;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RequestUtil implements Runnable {

    private static final String ROOT_URL = "http://localhost:8080";
    private static final int TIMEOUT = 5000;
    private Boolean disconnect = false;
    private Map<String, String> params;
    private final String method;
    private String json;
    private String response;
    public Thread thread;
    private URL url;
    private static final Gson gson = new Gson();

    @Override
    public void run() {
        System.out.println("Name thread: " + thread.getName());
        response = send(thread.getName());
    }

    public RequestUtil(String url, String method) {
        this.method = method;
        this.thread = new Thread(this, url);
    }

    public Map<String, String> getParams() {
        return params;
    }

    public String send(String url) {
        try {
            System.out.println(ROOT_URL + url);
            this.url = new URL(ROOT_URL + url);
            HttpURLConnection conn = (HttpURLConnection) this.url.openConnection();
            conn.setRequestMethod(method);
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty( "Accept", "application/json");
            conn.setConnectTimeout(TIMEOUT);
            conn.setReadTimeout(TIMEOUT);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.connect();

            if (method.equals("POST") || method.equals("PUT")) {
                try(OutputStream os = conn.getOutputStream()) {
                    byte[] input = json.getBytes("utf-8");
                    os.write(input);
                }
            }


//            if (method.equals("POST")) {
//                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
//                wr.writeBytes(json);
//                wr.write(json);
//            }

            System.out.println(conn.getResponseCode());

            return readInputStream(conn);
        } catch (ConnectException e) {
            setDisconnect(true);
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public void setDisconnect(Boolean disconnect) {
        this.disconnect = disconnect;
    }

    public void setParams(Map<String, String> params) { this.params = params; }

    public String getResponse() { return response; }

    public Boolean getDisconnect() { return disconnect; }

    public static int getTIMEOUT() { return TIMEOUT; }

    public URL getUrl() { return url; }


    private static String readInputStream(HttpURLConnection conn) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String line;
            StringBuilder content = new StringBuilder();

            while ((line = in.readLine()) != null) {
                content.append(line);
            }
            conn.disconnect();
            return content.toString();
        } catch (IOException e) {
            conn.disconnect();
            return null;
        }
    }
    public static Map<String, Object> registration(String first_name, String second_name, String email, String password) {

        String URL = ROOT_URL +"/registration";
        Map<String, String> params = new HashMap<>();
        params.put("first_name",first_name);
        params.put("second_name", second_name);
        params.put("email", email);
        params.put("password", password);
        String response = RequestUtil.sendPOST(URL, params);
        Map<String, Object> resultMap = new HashMap<>();
        return new HashMap<>() {{
            put("response", response);
        }};
    }
    public static User auth(String email, String password) {
        String URL = ROOT_URL +"/auth";
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        String userString = RequestUtil.sendPOST( URL, params);
        System.out.println(userString);
        return gson.fromJson(userString, User.class);
    }
    public static String sendPOST(String urlString, Map<String, String> paramsMap) {
        try {
            URL obj = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestMethod("POST");

            Gson gsonObj = new Gson();

            String jsonStr = gsonObj.toJson(paramsMap);

            con.setDoOutput(true);
            OutputStream stream = con.getOutputStream();

            stream.write(jsonStr.getBytes(StandardCharsets.UTF_8));
            stream.flush();
            stream.close();

            int responseCode = con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();

            } else {
                System.out.println("POST получил код " + con.getResponseCode());
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }


}
