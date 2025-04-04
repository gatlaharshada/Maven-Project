import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/search")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SearchResource {
    @POST
    public List<SearchResult> search(Search search) {
        return HoenScannerApplication.getSearchResults().stream()
            .filter(result -> result.getCity().equalsIgnoreCase(search.getCity()))
            .collect(Collectors.toList());
    }
}
