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


        SharedPreferences sharedPref = getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);


        keyEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double resultado = parseContent(content);
                //Toast.makeText(getApplicationContext(),"Calculando...",Toast.LENGTH_SHORT).show();
                screen.setText(String.valueOf(resultado));
                throwToastNotification(content);
                lastResult = content;
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



    }

    @Override
    public void onClick(View view) {
        Button b = (Button) view;
        String buttonText = b.getText().toString();
        content = content + buttonText;
        screen.setText(content);
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
        //menu.setHeaderTitle("Select The Action");
        //menu.add(0, v.getId(), 0, "call");//groupId, itemId, order, title
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

}
