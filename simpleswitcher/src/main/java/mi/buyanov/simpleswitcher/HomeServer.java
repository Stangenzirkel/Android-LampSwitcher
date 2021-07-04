package mi.buyanov.simpleswitcher;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

public class HomeServer {
    public HomeServer() {
    }

    public void switchLamp(int num, boolean mode) {
        @Deprecated HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpPost request = new HttpPost("http://192.168.1.100:5000/cmd");

            JSONObject object = new JSONObject();
            object.put("name", mode ? "on" : "off");
            object.put("lamp", num);
            request.addHeader("content-type", "application/json");
            request.setEntity(new StringEntity(object.toString()));
            HttpResponse response = httpClient.execute(request);
            Log.d("HomeServer", response.toString());

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            httpClient.getConnectionManager().shutdown();
        }
    }

    public void switchLamp(int num) throws IOException, JSONException {
        Log.d("HomeServer", "switching lamp");
        switchLamp(num, !lampMode(num));
    }

    public boolean lampMode(int num) throws IOException, JSONException {
        JSONObject json = readJsonFromUrl("http://192.168.1.100:5000/lamps");
        Log.d("HomeServer", json.toString());

        return (Boolean) json.get(Integer.toString(num));
    }

    public boolean [] lampMode() throws IOException, JSONException {
        JSONObject json = readJsonFromUrl("http://192.168.1.100:5000/lamps");
        Log.d("HomeServer", json.toString());

        return new boolean[] {(boolean) json.get(Integer.toString(1)),
                              (boolean) json.get(Integer.toString(2)),
                              (boolean) json.get(Integer.toString(3)),
                              (boolean) json.get(Integer.toString(4)),
                              (boolean) json.get(Integer.toString(5))};
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }


}
