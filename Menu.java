import org.json.JSONException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;


public class Menu {
    private Scanner scanner;
    private SQLite database;
    private OMDBApi omdbAPI;

    private KeyReader keyReader = new KeyReader();

    public Menu() throws ClassNotFoundException, SQLException, IOException {
        scanner = new Scanner(System.in);
        database = new SQLite( "film.db");
        database.createMoviesTable();
        String apiKey = keyReader.getApiKey();
        omdbAPI = new OMDBApi(apiKey);

    }


    public void run() throws SQLException {
        System.out.println("Welcome to the Movie Database!");
        boolean quit = false;
        while (!quit) {
            System.out.println("\nPlease select an option:");
            System.out.println("1. Show all movies ");
            System.out.println("2. Show all actors");
            System.out.println("3. Show all directors");
            System.out.println("4. Show all genres");
            System.out.println("5. Show all years");
            System.out.println("6. Search for a movie by title ");
            System.out.println("7. Show information");
            System.out.println("8. Add a movie");
            System.out.println("9. Quit");

            int choice = 0;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 9.");
                scanner.nextLine();
                continue;
            }

            switch (choice) {
                case 1:
                    showAllMovies();
                  break;
                 case 2:
                    showAllActors();
                    break;
                case 3:
                    showAllDirectors();
                    break;
                case 4:
                    showAllGenres();
                    break;
                case 5:
                    showAllYears();
                    break;
                case 6:
                    search();
                    break;
                case 7:
                    showInformation();
                    break;
                case 8:
                    addMovie();
                    break;
                case 9:
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid input. Please enter a number between 1 and 9.");
                    break;
            }
        }

    }

    public void showAllMovies() {
        try {
            // Retrieve all movies from the database
            Movie[] movies = database.getMovie("", "");

            // Display the movies to the user
            if (movies.length == 0) {
                System.out.println("No movies found");
            } else {
                System.out.println("All movies:");
                for (Movie movie : movies) {
                    System.out.println(movie.getTitle() + " (" + movie.getYear() + ")");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving movies: " + e.getMessage());
        }
    }


    public void showAllActors() {
        try {
            // Retrieve all movies from the database
            Movie[] movies = database.getMovie("", "");

            // Create a map to store actors and their corresponding movies
            Map<String, List<Movie>> actorsMap = new HashMap<>();

            // Loop through all movies and add each actor to the map along with their corresponding movie
            for (Movie movie : movies) {
                String[] actors = new String[]{movie.getActors()};
                for (String actor : actors) {
                    if (actorsMap.containsKey(actor)) {
                        actorsMap.get(actor).add(movie);
                    } else {
                        List<Movie> actorMovies = new ArrayList<>();
                        actorMovies.add(movie);
                        actorsMap.put(actor, actorMovies);
                    }
                }
            }

            // Display the actors and their corresponding movies
            for (Map.Entry<String, List<Movie>> entry : actorsMap.entrySet()) {
                System.out.println(entry.getKey() + ":");
                List<Movie> actorMovies = entry.getValue();
                for (Movie movie : actorMovies) {
                    System.out.println("\t" + movie.getTitle() + " (" + movie.getYear() + ")");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving movies: " + e.getMessage());
        }
    }



    public void showAllDirectors() {
        try {
            // Retrieve all movies from the database
            Movie[] movies = database.getMovie("", "");

            // Create a map to store directors and their corresponding movies
            Map<String, List<Movie>> directorsMap = new HashMap<>();

            // Loop through all movies and add each director to the map along with their corresponding movie
            for (Movie movie : movies) {
                String director = movie.getDirector();
                if (directorsMap.containsKey(director)) {
                    directorsMap.get(director).add(movie);
                } else {
                    List<Movie> directorMovies = new ArrayList<>();
                    directorMovies.add(movie);
                    directorsMap.put(director, directorMovies);
                }
            }

            // Display the directors and their corresponding movies
            for (Map.Entry<String, List<Movie>> entry : directorsMap.entrySet()) {
                System.out.println(entry.getKey() + ":");
                List<Movie> directorMovies = entry.getValue();
                for (Movie movie : directorMovies) {
                    System.out.println("\t" + movie.getTitle() + " (" + movie.getYear() + ")");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving movies: " + e.getMessage());
        }
    }





    public void showAllGenres() {
        try {
            // Retrieve all movies from the database
            Movie[] movies = database.getMovie("", "");

            // Create a map to store genres and their corresponding movies
            Map<String, List<Movie>> genresMap = new HashMap<>();

            // Loop through all movies and add each genre to the map along with its corresponding movie
            for (Movie movie : movies) {
                String[] genres = movie.getGenre().split(",");
                for (String genre : genres) {
                    if (genresMap.containsKey(genre)) {
                        genresMap.get(genre).add(movie);
                    } else {
                        List<Movie> genreMovies = new ArrayList<>();
                        genreMovies.add(movie);
                        genresMap.put(genre, genreMovies);
                    }
                }
            }

            // Display the genres and their corresponding movies
            for (Map.Entry<String, List<Movie>> entry : genresMap.entrySet()) {
                System.out.println(entry.getKey() + ":");
                List<Movie> genreMovies = entry.getValue();
                for (Movie movie : genreMovies) {
                    System.out.println("\t" + movie.getTitle() + " (" + movie.getYear() + ")");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving movies: " + e.getMessage());
        }
    }


    public void showAllYears() {
        try {
            // Retrieve all movies from the database
            Movie[] movies = database.getMovie("", "");

            // Create a map to store years and their corresponding movies
            Map<String, List<Movie>> yearsMap = new HashMap<>();

            // Loop through all movies and add each year to the map along with its corresponding movie
            for (Movie movie : movies) {
                String year = movie.getYear();
                if (yearsMap.containsKey(year)) {
                    yearsMap.get(year).add(movie);
                } else {
                    List<Movie> yearMovies = new ArrayList<>();
                    yearMovies.add(movie);
                    yearsMap.put(year, yearMovies);
                }
            }

            // Display the years and their corresponding movies
            for (Map.Entry<String, List<Movie>> entry : yearsMap.entrySet()) {
                System.out.println(entry.getKey() + ":");
                List<Movie> yearMovies = entry.getValue();
                for (Movie movie : yearMovies) {
                    System.out.println("\t" + movie.getTitle() + " (" + movie.getYear() + ")");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving movies: " + e.getMessage());
        }
    }


    private void addMovie() throws SQLException {
        System.out.println("\nEnter movie title:");
        String title = scanner.nextLine();

        Movie[] movies = database.getMovie(title);
        if (movies.length == 0) {
            // Movie not found in local database
            System.out.println("Movie not found in database. Searching online...");
            try {
                OMDBApi api = new OMDBApi(keyReader.getApiKey());
                Movie movie = api.getMovie(title);
                if (movie == null) {
                    System.out.println("Movie not found online. Please try again later.");
                    return;
                }
                System.out.println(movie);

                System.out.println("Do you want to add this movie to the database? (y/n)");
                String answer = scanner.nextLine();
                if (answer.equals("y")) {
                    database.addMovie(movie);
                    System.out.println("Movie added to database");
                } else {
                    System.out.println("Movie not added");
                }

            } catch (JSONException e) {
                System.err.println("Error fetching movie from OMDb API: " + e.getMessage());
                System.out.println("Could not find movie online. Please try again later.");
            }
        } else {
            // Movie found in local database
            Movie movie = movies[0];
            System.out.println(movie.toString());
            System.out.println("Movie already in database");
        }
    }



    private void search() throws SQLException {
        System.out.println("\nWhat do you want to search for?");
        System.out.println("1. Movies");
        System.out.println("2. Actors");
        System.out.println("3. Directors");
        System.out.println("4. Genres");
        System.out.println("5. Years");
        System.out.println("6. Back");

        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline character

        switch (choice) {
            case 1:
                searchMovies();
               break;
          /* case 2:
                searchActors();
                break;
           case 3:
                searchDirectors();
                break;
            case 4:
                searchGenres();
                break;
            case 5:
                searchYears();
                break;  */
            case 6:
                break;
            default:
                System.out.println("Invalid input");
        }


    }

    public void searchMovies() throws SQLException {
        System.out.println("\nEnter movie title:");
        String title = scanner.nextLine();
        Movie[] movies = Movie.getMovie(title);
        if (movies.length == 0) {
            // Movie not found in local database, make API request
            OMDBApi omdbApi = new OMDBApi(keyReader.getApiKey());
            Movie[] apiMovies = omdbApi.getMovie(title, keyReader.getApiKey());

            if (apiMovies.length == 0) {
                System.out.println("No movies found");
            } else {
                System.out.println("Results from OMDB API:");
                displayResult(apiMovies);
                for (Movie movie : apiMovies) {
                    database.addMovie(movie); // add each movie to database
                }
            }
        } else {
            // Movie found in local database, display results
            System.out.println("Results from local database:");
            displayResult(movies);
        }
    }



    private void displayResult(Movie[] movies) {
        System.out.println("Results:\n");

        // Add each movie to the table as a row
        for (Movie movie : movies) {
            System.out.print("Title: ");
            typeOut(movie.getTitle());
            System.out.print("Year: ");
            typeOut(movie.getYear());
            System.out.print("Genre: ");
            typeOut(movie.getGenre());

        }
    }

    private void typeOut(String text) {
        for (int i = 0; i < text.length(); i++) {
            System.out.print(text.charAt(i));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.err.println("Interrupted while typing: " + e.getMessage());
                Thread.currentThread().interrupt();
                return;
            }
        }
        System.out.println();
    }






   /* private void searchActors() throws SQLException{

        // search for actors from local database



    }

    private void searchDirectors(){
     // eve


    }

    private void searchGenres(){
     // only from local database

    }

    private void searchYears(){
     // only from local database

    }   */


}




