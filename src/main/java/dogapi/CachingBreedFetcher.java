package dogapi;

import java.io.IOException;
import java.util.*;
import java.util.HashMap;
import java.util.Map;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    // TODO Task 2: Complete this class
    private int callsMade = 0;
    private BreedFetcher fetcher;
    private Map<String, List<String>> subBreedsCache = new HashMap<>();

    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.fetcher = fetcher;
    }

    @Override
    public List<String> getSubBreeds(String breed) throws IOException {
        // return statement included so that the starter code can compile and run.
        try {
            if (subBreedsCache.containsKey(breed)) {
                return subBreedsCache.get(breed);
            } else {
                this.callsMade++;
                List<String> subBreeds = this.fetcher.getSubBreeds(breed);
                subBreedsCache.put(breed, subBreeds);
                return subBreeds;
            }
        } catch (BreedNotFoundException bnfe) {
                throw new BreedNotFoundException(breed);
            }
        }

    public int getCallsMade () {
            return callsMade;
        }
    }