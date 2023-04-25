import java.net.*;
import java.io.*;
import java.sql.SQLException;


public class OMDBApi {

    private String apiKey;

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
            movie = Movie.fromJson(content.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (movie == null) {
            System.out.println("Movie not found");
        } else {
            System.out.println(movie.toString());
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


