import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class KeyReader {
    private String apiKey;

    public KeyReader() throws IOException {
        // Get the path to the user's documents folder
        String userHome = System.getProperty("user.home");

        // Read the API key from the OMDB.txt file in the APIKeys folder in the user's documents folder
        Path keyFilePath = Paths.get(userHome, "Documents", "APIKeys", "OMDB.txt");
        if (!Files.exists(keyFilePath)) {
            throw new IOException("OMDB API key file not found");
        }

        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(keyFilePath.toFile())) {
            props.load(fis);
            apiKey = props.getProperty("apiKey");
        }
    }

    public String getApiKey() {
        return apiKey;
    }



    }

