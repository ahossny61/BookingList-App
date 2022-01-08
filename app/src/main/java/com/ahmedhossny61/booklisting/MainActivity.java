package com.ahmedhossny61.booklisting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.inputmethodservice.Keyboard;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {
    private String Book_Url;
    private LoaderManager loaderManager;
    private Button search;
    private boolean start;
    private ListView BookListItem;
    private EditText input;
    private BookAdapter mAdapter;
    private static int Book_LOADER_ID = 1;
    private TextView mEmptyStateTextView;
    private ProgressBar progress;
    public static final String LOG_TAG = MainActivity.class.getName();
    private static String URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static String Base_url = "https://www.googleapis.com/books/v1/volumes?q=android";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Book_Url = "";
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        start = true;
        progress = (ProgressBar) findViewById(R.id.progress_bar);
        progress.setVisibility(View.INVISIBLE);
        ArrayList<Book> books = new ArrayList<>();
        mAdapter = new BookAdapter(this, books);
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);


        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager = getLoaderManager();
            loaderManager.initLoader(Book_LOADER_ID, null, MainActivity.this);
            // Find a reference to the {@link ListView} in the layout
        } else {
            progress.setVisibility(View.GONE);
            mEmptyStateTextView.setText("No internet connection");
        }
        BookListItem = (ListView) findViewById(R.id.CustomList);
        //BookListItem.setEmptyView(mEmptyStateTextView);
        BookListItem.setAdapter(mAdapter);
        search = (Button) findViewById(R.id.btn_search);
        input = (EditText) findViewById(R.id.search_input);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start = false;
                mEmptyStateTextView.setVisibility(View.GONE);
                // input.onEditorAction(EditorInfo.IME_ACTION_DONE);
                InputMethodManager imm = (InputMethodManager) MainActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

                loaderManager.destroyLoader(Book_LOADER_ID);
                Book_Url = "";
                Book_Url = URL + input.getText();
                progress.setVisibility(View.VISIBLE);
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);


                // Get details on the currently active default data network
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                // If there is a network connection, fetch data
                if (networkInfo != null && networkInfo.isConnected()) {
                    // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                    // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                    // because this activity implements the LoaderCallbacks interface).
                    loaderManager = getLoaderManager();
                    loaderManager.initLoader(Book_LOADER_ID, null, MainActivity.this);
                    // Find a reference to the {@link ListView} in the layout
                } else {
                    progress.setVisibility(View.GONE);
                    mEmptyStateTextView.setText("No internet connection");
                    mEmptyStateTextView.setVisibility(View.VISIBLE);
                }

            }
        });
        BookListItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Book currentEarthquake = mAdapter.getItem(i);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri BookUrl = Uri.parse(currentEarthquake.getInfo_url());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, BookUrl);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });
    }

    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new BooakLoader(this, Book_Url);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        progress.setVisibility(View.INVISIBLE);
        //  BookListItem.setVisibility(View.VISIBLE);

        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            mAdapter.addAll(books);
            BookListItem.invalidateViews();
        } else {
            mEmptyStateTextView.setVisibility(View.VISIBLE);
            if (start)
                mEmptyStateTextView.setText("Welcome ,you can search for books");
            else
                mEmptyStateTextView.setText("No books !");
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }


}
