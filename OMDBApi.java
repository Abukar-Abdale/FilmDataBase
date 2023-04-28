import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class OMDBApi {

    private final String apiKey;

    public OMDBApi(String apiKey) {
        this.apiKey = apiKey;
    }
    public Movie getMovie(String title) {
        String url = "http://www.omdbapi.com/?apikey=" + apiKey + "&t=" + title;
        Movie movie = null;
        try {
            URL api = new URL(url);
            HttpURLConnection con = (HttpURLConnection) api.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();

            JsonObject response = JsonParser.parseString(content.toString()).getAsJsonObject();

            if (response.get("Response").getAsString().equals("False")) {
                // Movie not found
                System.out.println("Movie not found");
                return null;
            }
            movie = Movie.fromJson(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movie;
    }



    public Movie[] getMovie(String title, String year) {
        String url = "http://www.omdbapi.com/?apikey=" + apiKey + "&t=" + title + "&y=" + year;
        Movie movie = null;
        try {
            URL api = new URL(url);
            HttpURLConnection con = (HttpURLConnection) api.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            movie = Movie.fromJson(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Movie[] movies = new Movie[1];
        movies[0] = movie;
        return movies;
    }


    public Movie getMovie(String title, String year, String type) {
        String url = "http://www.omdbapi.com/?apikey=" + apiKey + "&t=" + title + "&y=" + year + "&type=" + type;
        Movie movie = null;
        try {
            URL api = new URL(url);
            HttpURLConnection con = (HttpURLConnection) api.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            con.disconnect();
            movie = Movie.fromJson(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movie;
    }
}
