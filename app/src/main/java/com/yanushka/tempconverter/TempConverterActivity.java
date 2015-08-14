package com.yanushka.tempconverter;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.view.View;
import android.widget.EditText;


public class TempConverterActivity extends ActionBarActivity
        implements OnEditorActionListener {

    // define global variables for the widgets
    private EditText degreeEditText;
    private TextView celsiusTextView;

    // need to save shared preferences
    private SharedPreferences savedValues;

    // instance variables
    private String degreeEditString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_converter);

        // get widget references
        degreeEditText = (EditText) findViewById(R.id.degreeEditText);
        celsiusTextView = (TextView) findViewById(R.id.celsiusTextView);

        // set action listener
        degreeEditText.setOnEditorActionListener(this);

        // save preferences
        savedValues = getSharedPreferences("SavedValues", MODE_PRIVATE);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_UNSPECIFIED){
            calculateAndDisplay();
        }

        return false;
    }

    private void calculateAndDisplay() {
        if(degreeEditText.equals("")){
            
        }
        degreeEditString = degreeEditText.getText().toString();
        float celsiusDisplay = Float.parseFloat(degreeEditString);
        celsiusDisplay = ((celsiusDisplay - 32) * 5 / 9);
        celsiusTextView.setText(String.valueOf(celsiusDisplay));
    }

    @Override
    protected void onPause() {
        // need to save the instance variable
        Editor editor = savedValues.edit();
        editor.putString("degreeString", degreeEditString);
        // need to commit to save
        editor.commit();

        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // retrieve the same variable again
        degreeEditString = savedValues.getString("degreeString", "");

        // set the temp on the widget
        degreeEditText.setText(degreeEditString);

        // call calculate and display
        calculateAndDisplay();
    }
}
