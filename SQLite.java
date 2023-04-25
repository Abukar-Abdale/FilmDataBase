import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;




public class SQLite {

    private Connection connection;
    private List<Movie> database;

    public SQLite(String dbName) throws SQLException {
        String url = "jdbc:sqlite:" + database;
        connection = DriverManager.getConnection(url);
    }
    public void createMoviesTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS movies (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, year INTEGER, imdbID TEXT, type TEXT, poster TEXT, runtime TEXT, genre TEXT, director TEXT, writer TEXT, actors TEXT, plot TEXT, language TEXT, country TEXT, awards TEXT, imdbRating REAL, imdbVotes INTEGER, boxOffice REAL)");
    }


    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }


    public Movie[] getMovie(String title) throws SQLException {
        String query = "SELECT * FROM movies WHERE title LIKE ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, "%" + title + "%");
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Movie> movies = new ArrayList<Movie>();
        while (resultSet.next()) {
            String movieTitle = resultSet.getString("title");
            String director = resultSet.getString("director");
            int year = resultSet.getInt("year");
            String genre = resultSet.getString("genre");
            Movie movie = new Movie(movieTitle, director, year, genre);
            movies.add(movie);

        }
        return movies.toArray(new Movie[movies.size()]);
    }

    public Movie[] getMovie(String title, String year) throws SQLException {
        String query = "SELECT * FROM movies WHERE title LIKE ? AND year = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, "%" + title + "%");
        statement.setInt(2, Integer.parseInt(year));
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Movie> movies = new ArrayList<Movie>();
        while (resultSet.next()) {
            String movieTitle = resultSet.getString("title");
            String director = resultSet.getString("director");
            int movieYear = resultSet.getInt("year");
            String genre = resultSet.getString("genre");
            Movie movie = new Movie(movieTitle, director, movieYear, genre);
            movies.add(movie);
        }
        return movies.toArray(new Movie[movies.size()]);
    }

    public Movie[] getMovie(String title, String year, String type) throws SQLException {
        String query = "SELECT * FROM movies WHERE title LIKE ? AND year = ? AND type LIKE ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, "%" + title + "%");
        statement.setInt(2, Integer.parseInt(year));
        statement.setString(3, "%" + type + "%");
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Movie> movies = new ArrayList<Movie>();
        while (resultSet.next()) {
            String movieTitle = resultSet.getString("title");
            String director = resultSet.getString("director");
            int movieYear = resultSet.getInt("year");
            String genre = resultSet.getString("genre");
            Movie movie = new Movie(movieTitle, director, movieYear, genre);
            movies.add(movie);
        }
        return movies.toArray(new Movie[movies.size()]);
    }

    public Movie[] getActor(String actor) throws SQLException {
        String query = "SELECT * FROM movies WHERE actor LIKE ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, "%" + actor + "%");
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Movie> movies = new ArrayList<Movie>();
        while (resultSet.next()) {
            String movieTitle = resultSet.getString("title");
            String director = resultSet.getString("director");
            int movieYear = resultSet.getInt("year");
            String genre = resultSet.getString("genre");
            Movie movie = new Movie(movieTitle, director, movieYear, genre);
            movies.add(movie);
        }
        return movies.toArray(new Movie[movies.size()]);
    }

    public Movie[] getDirector(String director) throws SQLException {
        String query = "SELECT * FROM movies WHERE director LIKE ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, "%" + director + "%");
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Movie> movies = new ArrayList<Movie>();
        while (resultSet.next()) {
            String movieTitle = resultSet.getString("title");
            String movieDirector = resultSet.getString("director");
            int movieYear = resultSet.getInt("year");
            String genre = resultSet.getString("genre");
            Movie movie = new Movie(movieTitle, movieDirector, movieYear, genre);
            movies.add(movie) ;
        }
        return movies.toArray(new Movie[movies.size()]);
    }

    public Movie[] getGenre(String genre) throws SQLException {
        String query = "SELECT * FROM movies WHERE genre LIKE ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, "%" + genre + "%");
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Movie> movies = new ArrayList<Movie>();
        while (resultSet.next()) {
            String movieTitle = resultSet.getString("title");
            String director = resultSet.getString("director");
            int movieYear = resultSet.getInt("year");
            String movieGenre = resultSet.getString("genre");
            Movie movie = new Movie (movieTitle, director, movieYear, movieGenre);
            movies.add(movie);
        }
        return movies.toArray(new Movie[movies.size()]);
    }

    public Movie[] getYear(String year) throws SQLException {
        String query = "SELECT * FROM movies WHERE year = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, Integer.parseInt(year));
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Movie> movies = new ArrayList<Movie>();
        while (resultSet.next()) {
            String movieTitle = resultSet.getString("title");
            String director = resultSet.getString("director");
            int movieYear = resultSet.getInt("year");
            String genre = resultSet.getString("genre");
            Movie movie = new Movie(movieTitle, director, movieYear, genre);
            movies.add(movie);
        }
        return movies.toArray(new Movie[movies.size()]);
    }
    public void addMovie(Movie movie) {
        // Lägg till filmen i databasen
        database.add(movie);

        // Bekräfta att filmen har lagts till
        System.out.println("Filmen " + movie.getTitle() + " har lagts till i databasen.");
    }

    public Movie[] getAllMovies() {
        // Returnera alla filmer i databasen
        return database.toArray(new Movie[database.size()]);

    }

    /*public Director[] getAllDirectors() {
        // Returnera alla regissörer i databasen
        return database.toArray(new Director[database.size()]);
    } */

    public String[] getAllGenres() {
        // Returnera alla genrer i databasen
        return database.toArray(new String[database.size()]);
    }

    public int[] getAllYears() {
        List<Integer> years = new ArrayList<Integer>();
        for (Movie movie : database) {
            years.add(Integer.parseInt(movie.getYear()));
        }
        return years.stream().mapToInt(i -> i).toArray();
    }


    public String[] getAllActors() {
        // Returnera alla skådespelare i databasen
        return database.toArray(new String[database.size()]);
    }
}








