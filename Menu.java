import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;


public class Menu {
    private Scanner scanner;
    private SQLite database;
    private OMDBApi omdbAPI;

    private KeyReader keyReader = new KeyReader();

    public Menu() throws ClassNotFoundException, SQLException, IOException {
        scanner = new Scanner(System.in);
        database = new SQLite("movies.db");
        database.createMoviesTable();
        String apiKey = keyReader.getApiKey();
        omdbAPI = new OMDBApi(apiKey);
    }


    public void run() throws SQLException {
        System.out.println("Welcome to the Movie Database!");
        boolean quit = false;
        while (!quit) {
            System.out.println("\nPlease select an option:");
            System.out.println("1. Show all movies");
            System.out.println("2. Show all actors");
            System.out.println("3. Show all directors");
            System.out.println("4. Show all genres");
            System.out.println("5. Show all years");
            System.out.println("6. Search");
            System.out.println("7. Show information");
            System.out.println("8. Add a movie");
            System.out.println("9. Quit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline character

            switch (choice) {
            /*    case 1:
                    showAllMovies();
                    break;
                case 2:
                    showAllActors();
                    break;
             /*   case 3:
                    showAllDirectors();
                    break;
                case 4:
                    showAllGenres();
                    break;
                case 5:
                    showAllYears();
                    break;*/
                case 6:
                    search();
                    break;
              /*  case 7:
                    showInformation();
                    break;
                case 8:
                    addMovie();
                    break; */
                case 9:
                    quit = true;
                    break;
                default:
                    System.out.println("Invalid input");
            }
        }

    }

   private void showAllMovies() throws SQLException {
          Movie[] movies = database.getAllMovies();
          if (movies.length == 0) {
              System.out.println("No movies found");
          } else {
              for (Movie movie : movies) {
                  System.out.println(movie.getTitle());
              }
          }
      }

      private void showAllActors() throws SQLException {
          String[] actors = database.getAllActors();
          if (actors.length == 0) {
              System.out.println("No actors found");
          } else {
              for (String actor : actors) {
                  System.out.println(actor);
              }
          }

      }
   private void showAllDirectors() throws SQLException {
          String[] directors = database.getAllDirectors();
          if (directors.length == 0) {
              System.out.println("No directors found");
          } else {
              for (String director : directors) {
                  System.out.println(director);
              }
          }


      }

    private void showAllGenres() throws SQLException {
        String[] genres = database.getAllGenres();
        if (genres.length == 0) {
            System.out.println("No genres found");
        } else {
            for (String genre : genres) {
                System.out.println(genre);
            }
        }
    }

    private void showAllYears() throws SQLException {
        int[] years = database.getAllYears();
        if (years.length == 0) {
            System.out.println("No years found");
        } else {
            for (int year : years) {
                System.out.println(year);
            }
        }
    }

    private void addMovie () throws SQLException {
        System.out.println("Enter the title of the movie you want to add:");
        String title = scanner.nextLine();
        Movie movie = omdbAPI.getMovie(title);
        if (movie == null) {
            System.out.println("Movie not found");
        } else {
            database.addMovie(movie);
            System.out.println("Movie added");
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
       case 2:
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
                break;
            case 6:
                break;
            default:
                System.out.println("Invalid input");
        }


    }

    private void searchMovies() throws SQLException {
        System.out.println("\nEnter movie title:");
        String title = scanner.nextLine();

        Movie[] movies = database.getMovie(title);
        if (movies.length == 0) {
            // Movie not found in local database, make API request
            OMDBApi omdbApi = new OMDBApi(keyReader.getApiKey());
            Movie[] apiMovies = omdbApi.getMovie(title, keyReader.getApiKey());

            if (apiMovies.length == 0) {
                System.out.println("No movies found");
            } else {
                System.out.println("Results from OMDB API:");
                for (Movie movie : apiMovies) {
                    System.out.println(movie.toString());
                    database.addMovie(movie); // add movie to database
                }
            }
        } else {
            // Movie found in local database, display results
            System.out.println("Results from local database:");
            for (Movie movie : movies) {
                System.out.println(movie.toString());
            }
        }
    }




    private void searchActors() throws SQLException {
        System.out.println("\nEnter actor name:");
        String actor = scanner.nextLine();
        Actor[] actors = database.getActor(actor);
        if (actors.length == 0) {
            // Actor not found in local database, make API request
            OMDBApi omdbApi = new OMDBApi(keyReader.getApiKey());
            Actor[] apiActors = omdbApi.getActor(actor, keyReader.getApiKey());
            if (apiActors.length == 0) {
                System.out.println("No actors found");
            } else {
                System.out.println("Results from OMDB API:");
                for (Actor a : apiActors) {
                    System.out.println(a.toString());
                }
            }
        } else {
            // Actor found in local database, display results
            System.out.println("Results from local database:");
            for (Actor a : actors) {
                System.out.println(a.toString());
            }
        }
    }

}




