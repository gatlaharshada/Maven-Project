import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

public class HoenScannerApplication extends Application<HoenScannerConfiguration> {
    private static List<SearchResult> searchResults;

    @Override
    public void run(HoenScannerConfiguration configuration, Environment environment) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        
        try (InputStream carStream = getClass().getResourceAsStream("/rental_cars.json");
             InputStream hotelStream = getClass().getResourceAsStream("/hotels.json")) {
            
            List<SearchResult> carResults = mapper.readValue(carStream, new TypeReference<List<SearchResult>>() {});
            List<SearchResult> hotelResults = mapper.readValue(hotelStream, new TypeReference<List<SearchResult>>() {});

            searchResults = Stream.concat(carResults.stream(), hotelResults.stream()).collect(Collectors.toList());
        }

        environment.jersey().register(new SearchResource());
    }

    public static List<SearchResult> getSearchResults() {
        return searchResults;
    }
}
