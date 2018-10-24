package lt.nesvat.laura.spacex;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Flight>> {

    private static final String SPACEX_URL_FUTURE = "https://api.spacexdata.com/v3/launches/upcoming";
    private static final int FLIGHT_LOADER_ID = 1;
    private FlightAdapter flightAdapter;
    private TextView noFlightsTextView;
    private ProgressBar progressBar;
    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume.
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };


    //Variable used to check if file finished playing, releasing the resources used for MediaPlayer
    private MediaPlayer.OnCompletionListener completionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create audio manager so that we could use audiofocus
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        //So that the name next to icon is different than activity label
        this.setTitle(getResources().getString(R.string.main_label));

        //Find TextView responsible for showing no data information
        noFlightsTextView = findViewById(R.id.empty_view);

        //Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        //Check for internet connection before initializing loader
        ConnectivityManager cm =(ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

            //If user is connected to network Update Loader
            if(isConnected){
                loaderManager.initLoader(FLIGHT_LOADER_ID, null, this);
            } else {
                progressBar = findViewById(R.id.progress_bar);
                progressBar.setVisibility(View.GONE);
                noFlightsTextView.setText(R.string.no_internet);
            }
        }

        //Find reference to the ListView in the layout
        ListView listview = findViewById(R.id.list);
        //MakeSure that in case no data is available user will be informed
        listview.setEmptyView(noFlightsTextView);

        //Create new flight adapter and set it on ListView
        flightAdapter = new FlightAdapter(this, new ArrayList<Flight>());
        listview.setAdapter(flightAdapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                //Find current Flight that was clicked
                Flight currentFlight = flightAdapter.getItem(position);
                Intent detailsIntent = new Intent(MainActivity.this, DetailsActivity.class);
                detailsIntent.putExtra("FlightObject", currentFlight);
                MainActivity.this.startActivity(detailsIntent);
            }
        });


        Button historyButton = findViewById(R.id.button_history);
        historyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), History.class);
                view.getContext().startActivity(intent);

            }
        });

        ImageView catImage = findViewById(R.id.image_vega);
        catImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                releaseMediaPlayer();

                //Request audio focus just before playing the file
                int result = audioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        // Use the music stream.
                        AudioManager.STREAM_MUSIC,
                        // Request permanent focus.
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {


                    //Create media player that will be used to play sound when clicking on picture
                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.cat_meow);
                    mediaPlayer.start();
                    //follow when the file finished to play and release media player
                    mediaPlayer.setOnCompletionListener(completionListener);
                }
            }
        } );
    }

    //When LoadManager determines that loader with our specified loader ID is not running,
    //new one will be created
    @Override
    public Loader<List<Flight>> onCreateLoader(int i, Bundle bundle) {
        return new SpaceXLoader(this, SPACEX_URL_FUTURE);
    }

    @Override
    public void onLoadFinished(Loader<List<Flight>> loader, List<Flight> flights) {

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        noFlightsTextView.setText(R.string.no_flights);

        //Clear adapter if there is previous flight data
        flightAdapter.clear();
        //If there is valid list of flights then add them to adapter
        if (flights != null && !flights.isEmpty()) {
            flightAdapter.addAll(flights);
        }
    }

    //When data on our loader is no longer valid
    @Override
    public void onLoaderReset(Loader<List<Flight>> loader) {
        flightAdapter.clear();
    }

    // If the media player is not null, then it may be currently playing a sound.
    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
            //Abandon audio focus, when playing of the file is not needed any more
            audioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    //In case user left the app, release media player
    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }



}

//Todo: make cat meow
//Todo: Fix landscape mode
//Todo: When app is inslalled it should appear on desktop - https://stackoverflow.com/questions/16873256/how-to-add-shortcut-to-home-screen-in-android-programmatically
//Todo: Make youtube link more readable