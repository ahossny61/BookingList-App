package com.ahmedhossny61.booklisting;
import android.content.AsyncTaskLoader;
import android.content.Context;
import java.util.List;
public class BooakLoader extends AsyncTaskLoader<List<Book>>   {

    private static final String LOG_TAG = BooakLoader.class.getName();

    /** Query URL */
    private String mUrl;
    /**
     * Constructs a new {@link Book}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public BooakLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Book> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<Book> earthquakes = QueryUtils.fetchEarthquakeData(mUrl);
        return earthquakes;
    }
}
