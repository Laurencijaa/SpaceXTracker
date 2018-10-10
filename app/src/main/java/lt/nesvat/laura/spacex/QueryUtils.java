package lt.nesvat.laura.spacex;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

class QueryUtils {

    private static final String LOG_TAG = "QueryUtils class issue";

    //Creating URL from String
    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error creating URL", e);
        }
        return url;
    }

    //Making HTTP request
    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";

        if(url == null){
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "There are issues from reading input stream");
        } finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    //Helper method to read from input stream
    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if(inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null){
                output.append(line);
                line  = reader.readLine();
            }
        }
        return output.toString();
    }


    //Extract data from JSON response
    private static List<Flight> extractFeatureFromJson (String jsonResponse) {
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        List<Flight> flights = new ArrayList<>();
        try {
            JSONArray rootJson = new JSONArray(jsonResponse);
            for(int i=0; i < rootJson.length(); i++){
                JSONObject currentObject = rootJson.getJSONObject(i);
                int flightDateUnix = currentObject.getInt("launch_date_unix");
                int flightNumber = currentObject.getInt("flight_number");

                JSONObject rocket = currentObject.getJSONObject("rocket");
                String rocketName = rocket.getString("rocket_name");

                JSONObject launchSite = currentObject.getJSONObject("launch_site");
                String launchSiteName = launchSite.getString("site_name_long");

                JSONObject fistStage = rocket.getJSONObject("first_stage");
                JSONArray cores = fistStage.getJSONArray("cores");
                boolean reuse = false;
                ArrayList<String> reusedCores = new ArrayList<>();
                for(int j=0; j<cores.length(); j++){
                    JSONObject currentCore = cores.getJSONObject(j);
                    String reusedCore = currentCore.getString("reused");
                    reusedCores.add(reusedCore);
                }
                //Todo: When reused is null, write message that there is "no information" instead of saying "false"
                if(reusedCores.contains("true")) {
                    reuse = true;
                }

                String flightDetails = currentObject.getString("details");

                JSONObject links = currentObject.getJSONObject("links");
                String videoUrl = links.getString("video_link");

                //Todo: When there is launch success is unknown do not assign to it false value (valid for upcoming flights, and not used)
                boolean launchSuccess;
                if (currentObject.isNull("launch_success")){
                    launchSuccess = false;
                } else {
                    launchSuccess = currentObject.getBoolean("launch_success");
                }
                boolean isUpcoming = currentObject.getBoolean("upcoming");

                flights.add(new Flight(flightDateUnix, flightNumber, rocketName, launchSiteName, launchSuccess, reuse, flightDetails, videoUrl, isUpcoming));
            }

        } catch (JSONException e){
            Log.e(LOG_TAG, "Issues proceeding JSON file", e);
        }
        return flights;
    }

    //Method that ties all above methods together
    public static List<Flight> extractFlights(String requestUrl) {
        //Generate URL from String using Utils helper method
        URL url = QueryUtils.createUrl(requestUrl);
        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }
        // Extract relevant fields from the JSON response and create and populate flights array with flight objects
        List<Flight> flights = extractFeatureFromJson(jsonResponse);
        return flights;

    }
}
