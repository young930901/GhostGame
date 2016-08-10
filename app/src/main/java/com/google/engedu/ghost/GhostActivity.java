package com.google.engedu.ghost;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


public class GhostActivity extends ActionBarActivity implements View.OnClickListener {
    private static final String COMPUTER_TURN = "Computer's turn";
    private static final String USER_TURN = "Your turn";
    private static final String USER_Win = "You Win!";
    private static final String Computer_Win = "Computer WinS!";
    private GhostDictionary dictionary;
    private boolean userTurn = false;
    //private SimpleDictionary sd;
    private FastDictionary fd;

    private Random random = new Random();
    private Button challenge;
    private Button restart;
    private TextView text;
    private TextView label;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost);

        text = (TextView)findViewById(R.id.ghostText);
        label = (TextView)findViewById(R.id.gameStatus);

        challenge = (Button)findViewById(R.id.challenge);
        challenge.setOnClickListener(this);
        restart = (Button)findViewById(R.id.restart);
        restart.setOnClickListener(this);


        try {
            InputStream is = getAssets().open("words.txt");
            //sd = new SimpleDictionary(is);
            fd = new FastDictionary(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        onStart(null);
    }
    public boolean onKeyUp(int keyCode, KeyEvent event)
    {
        char pressedKey = (char) event.getUnicodeChar();
        if(pressedKey >= 'a' && pressedKey <= 'z') {
            text.append(Character.toString(pressedKey));
            userTurn = false;
            computerTurn();
            return true;
        }
        return super.onKeyUp(keyCode, event);
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

    private void computerTurn() {
        String word =  text.getText().toString();
        challenge.setEnabled(true);
        if(fd.getAnyWordStartingWith(word)==null)
        {
            label.setText(Computer_Win);
            userTurn= true;
            return;
        }
        if(word==""||word==null)
        {
            String alphabet = "abcdefghijklmnopqrstuvxzyz";
            int i = random.nextInt(26);
            text.setText(alphabet.substring(i,i+1));

            userTurn = true;

            label.setText(USER_TURN);
            return;
        }

        if(fd.isWord(word)&&word.length()>=4) {
            label.setText(Computer_Win);
            userTurn = true;
            return;
        }
        else {
            String nWord= null;
            nWord= fd.getAnyWordStartingWith(text.getText().toString());

            if(nWord==null) {
                label.setText("Computer Win!!");
                challenge.setEnabled(false);
            }
            else {
                word += Character.toString(fd.getAnyWordStartingWith(word).charAt(word.length()));
                text.setText(word);

            }
        }
        // Do computer turn stuff then make it the user's turn again
        userTurn = true;
        label.setText(USER_TURN);
    }

    /**
     * Handler for the "Reset" button.
     * Randomly determines whether the game starts with a user turn or a computer turn.
     * @param view
     * @return true
     */
    public boolean onStart(View view) {
        userTurn = random.nextBoolean();
        text.setText("");
        if (userTurn) {
            challenge.setEnabled(false);
            label.setText(USER_TURN);
        } else {
            label.setText(COMPUTER_TURN);
            computerTurn();
        }
        return true;
    }
    public void challenge() {
        String s = text.getText().toString();
        if(fd.isWord(s)&&s.length()>=4)
        {
            challenge.setEnabled(false);
            label.setText(USER_Win);
            return;
        }
        if(fd.getAnyWordStartingWith(s)==null){
            label.setText(USER_Win);

            challenge.setEnabled(false);
        }
        else if(s==""||s==null)
        {
            return;
        }
        else if(fd.getAnyWordStartingWith(s)!=null)
        {
            String correctWord = fd.getAnyWordStartingWith(s);
            text.setText(correctWord);
            label.setText(Computer_Win);

            challenge.setEnabled(false);
        }
        userTurn =false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.challenge:
                challenge();
                break;
            case R.id.restart:
                onStart(null);
                challenge.setEnabled(true);
                break;
        }
    }


}
