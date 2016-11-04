package com.google.engedu.ghost;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends AppCompatActivity {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    private Random random = new Random();

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        char c = Character.toLowerCase((char)event.getUnicodeChar());
        if( (c >= 'a' && c <= 'z')){
            TextView textView = (TextView) findViewById(R.id.ghostText);
            textView.setText("" + textView.getText() + c);
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            dictionary = new SimpleDictionary(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
        onStart(null);

        Log.d("TESTING CH", dictionary.getAnyWordStartingWith("ch"));
        Log.d("TESTING pi", dictionary.getAnyWordStartingWith("pi"));
        Log.d("TESTING g", dictionary.getAnyWordStartingWith("g"));
        Log.d("TESTING se", dictionary.getAnyWordStartingWith("se"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
        TextView text = (TextView) findViewById(R.id.ghostText);
        text.setText("");
        TextView label = (TextView) findViewById(R.id.gameStatus);
        if (userTurn) {
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }

    private void computerTurn() {
        TextView label = (TextView) findViewById(R.id.gameStatus);
        // Do computer turn stuff then make it the user's turn again
        TextView textView = (TextView) findViewById(R.id.ghostText);
        String fragment = textView.getText().toString();
        //Check if the fragment is a word with at least 4 chars;user wins
        if(fragment.length() >= 4){
            label.setText("User wins!!");
            return;
        }
        //Use getAnyWordStartingWith
        String nextWord = dictionary.getAnyWordStartingWith(fragment);
        if(nextWord == null){
            label.setText("Computer Wins!!");
            return;
        }

        textView.setText(fragment + nextWord.charAt(fragment.length()));

        userTurn = true;
        label.setText(USER_TURN);
    }
    public void onChallenge (View view){
        TextView textView = (TextView) findViewById(R.id.ghostText);
        String fragment = textView.getText().toString();
        TextView label = (TextView) findViewById(R.id.gameStatus);
        textView.getText();
        if(fragment.length() >= 4 && dictionary.isWord(fragment)){
           label.setText("User Wins!!");
            return;
        }
        String nextWord = dictionary.getAnyWordStartingWith(fragment);

        if(nextWord != null){
            label.setText("Computer wins!!");
            return;
        }

        label.setText("User Wins!");
    }
    public void onRestart (View view){
        TextView textView = (TextView) findViewById(R.id.ghostText);
        textView.setText("");
    }
}
