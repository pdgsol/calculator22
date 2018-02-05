package com.example.pdg.calulatorapi22;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.support.design.widget.Snackbar;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;



public class CalculatorActivity  extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    //private CoordinatorLayout coordinatorLayout;
    String content = "";
    TextView screen;
    String lastResult = "";
    boolean startNewScreen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        Button keyOne = (Button) findViewById(R.id.keyOne);
        Button keyTwo = (Button) findViewById(R.id.keyTwo);
        Button keyThree = (Button) findViewById(R.id.keyThree);
        Button keyFour = (Button) findViewById(R.id.keyFour);
        Button keyFive = (Button) findViewById(R.id.keyFive);
        Button keySix = (Button) findViewById(R.id.keySix);
        Button keySeven = (Button) findViewById(R.id.keySeven);
        Button keyEigth = (Button) findViewById(R.id.keyEigth);
        Button keyNine = (Button) findViewById(R.id.keyNine);
        Button keyPlus = (Button) findViewById(R.id.keyPlus);
        Button keyMinus = (Button) findViewById(R.id.keyMinus);
        Button keyANS = (Button) findViewById(R.id.keyANS);
        Button keyMul = (Button) findViewById(R.id.keyMul);
        Button keyEqual = (Button) findViewById(R.id.keyEqual);
        Button keyDiv = (Button) findViewById(R.id.keyDiv);
        Button keyDot = (Button) findViewById(R.id.keyDot);
        Button keyZero = (Button) findViewById(R.id.keyZero);
        Button keyCE = (Button) findViewById(R.id.keyCE);
        Button keySave = (Button) findViewById(R.id.keySave);
        Button keyRestore = (Button) findViewById(R.id.keyRestore);


        screen = (TextView) findViewById(R.id.screen);
        screen.setText(content);

        
        keyOne.setOnClickListener(this);
        keyTwo.setOnClickListener(this);
        keyThree.setOnClickListener(this);
        keyFour.setOnClickListener(this);
        keyFive.setOnClickListener(this);
        keySix.setOnClickListener(this);
        keySeven.setOnClickListener(this);
        keyEigth.setOnClickListener(this);
        keyNine.setOnClickListener(this);
        keyPlus.setOnClickListener(this);
        keyMinus.setOnClickListener(this);
        keyMul.setOnClickListener(this);
        keyDot.setOnClickListener(this);
        keyZero.setOnClickListener(this);
        keyDiv.setOnClickListener(this);
        keyCE.setOnClickListener(this);
        keySave.setOnClickListener(this);
        keyRestore.setOnClickListener(this);



        keyEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double resultado = null;
                try {
                    resultado = parseContent(content);
                    screen.setText(String.valueOf(resultado));
                    throwToastNotification(content);
                    lastResult = content;
                    startNewScreen = true;
                } catch (Exception e) {
                    throwToastNotification(getString(R.string.calculationError));
                    content = getString(R.string.error);
                    startNewScreen = true;
                    e.printStackTrace();
                }
            }
        });

        keyCE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                screen.setText("");
                content = "";
            }
        });

        registerForContextMenu(keyEqual);

        keyEqual.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                //String numToCall = "tel:" + screen.getText().toString();
                //Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse(numToCall) );
                //startActivity(i);
                return false;
            }
        });


        keySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content = screen.getText().toString();
                throwToastNotification("Save screen value");
                SharedPreferences sharedPref = getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.preference_file_key), content);
                editor.apply();
            }
        });

        keyRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                throwToastNotification("Restore screen value saved");
                SharedPreferences sharedPref = getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                content = sharedPref.getString(getString(R.string.preference_file_key), "");
                screen.setText(content);
            }
        });

    }

    @Override
    public void onClick(View view) {
        Button b = (Button) view;
        String buttonText = b.getText().toString();
        content = screen.getText().toString();
        content.length();
        if(startNewScreen && isDigit(buttonText.charAt(0))) {
            content = buttonText;
        }
        else if(isOperator(buttonText.charAt(0)) && isOperator(buttonText.charAt(0))) {
            if(isOperator(content.charAt(content.length()-1)))
            {
                content = content.replace(content.charAt(content.length()-1),buttonText.charAt(0));
            } else {
                content = content + buttonText;
            }
        }else {
            content = content + buttonText;
        }

        screen.setText(content);
        startNewScreen = false;
    }

    double parseContent(String content) {

        // Create an Expression (A class from exp4j library)
        Expression expression = new ExpressionBuilder(content).build();
        return expression.evaluate();
    }

    @Override
    public boolean onLongClick(View view) {
        if (screen.getText().toString().equals("42.0")) {
            screen.setText("");
            content = "";
        }

        Button b = (Button) view;
        String buttonText = b.getText().toString();
        content = content + buttonText;
        screen.setText(content);
        return false;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.simple_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch (item.getItemId())
        {
            case R.id.call:
                doCall();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void doCall() {
        String numToCall = "tel:" + screen.getText().toString();
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse(numToCall) );
        startActivity(i);
    }

    private void throwToastNotification(String notificationMessage)
    {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, notificationMessage, duration);
        toast.show();
    }


    @Override
    protected void onStart() {
        super.onStart();

        //SharedPreferences sharedPref = getSharedPreferences(
        //      getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        //screen.setText(sharedPref.getString(getString(R.string.preference_file_key), ""));
    }

    @Override
    protected void onStop() {
        super.onStop();

        //SharedPreferences sharedPref = getSharedPreferences(
                //      getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedPref.edit();
        //editor.putString(getString(R.string.preference_file_key), screen.getText().toString());
        //editor.apply();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("screenContent", screen.getText().toString() );
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState)
    {
        super.onRestoreInstanceState(inState);
        screen.setText(inState.getString("screenContent"));
    }

    boolean isDigit(char value)
    {
        boolean isDigit = false;
        if(value >= '0' && value <= '9')
        {
            isDigit = true;
        }

        return isDigit;
    }

    boolean isOperator(char value)
    {
        boolean isOperator = false;
        if(value == '+' || value == '-' || value == '/' || value == '*' || value == '.')
        {
            isOperator = true;
        }

        return isOperator;
    }

}
