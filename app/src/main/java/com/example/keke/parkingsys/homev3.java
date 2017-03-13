package com.example.keke.parkingsys;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

public class homev3 extends AppCompatActivity {

    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

    String useremail;
    ListView list;
    String[] itemname ={
            "Veículos",
            "Reservas",
            "Nova Reserva",
            "Serviço Shuttle",
            "Histórico",
            "Parques",
            "Info Legal",
            "Definições",

    };

    Integer[] imgid={
            R.drawable.car,
            R.drawable.creditcard,
            R.drawable.add,
            R.drawable.bus,
            R.drawable.history,
            R.drawable.park,
            R.drawable.hammer,
            R.drawable.settings,
    };
    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */


    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */



    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private void addDrawerItems() {

        CustomListAdapter mAdapter=new CustomListAdapter(this, itemname, imgid);

        mDrawerList.setAdapter(mAdapter);

    }

    private void setupDrawer() {

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Menu");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_homev3);
        setTitle("Home");

        mDrawerList = (ListView)findViewById(R.id.navList);mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();
        useremail = getIntent().getExtras().getString("email");
        addDrawerItems();
        setupDrawer();
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(homev3.this, "Time for an upgrade!", Toast.LENGTH_SHORT).show();

                switch (position)
                {

                    case 0: Intent govei = new Intent(getBaseContext(), veiculos.class);
                        govei.putExtra("email",useremail);

                        startActivity(govei);
                        break;

                    case 1: Intent govei1 = new Intent(getBaseContext(), reservastivas.class);
                        govei1.putExtra("email",useremail);

                        startActivity(govei1);
                        break;


                    case 2:
                        Intent govei2 = new Intent(getBaseContext(), novareserva.class);
                        govei2.putExtra("email",useremail);

                        startActivity(govei2);
                        break;

                    case 3:
                        Intent govei3 = new Intent(getBaseContext(), shuttle.class);
                        govei3.putExtra("email",useremail);

                        startActivity(govei3);
                        break;
                case 4:
                        Intent govei4 = new Intent(getBaseContext(), historico.class);
                        govei4.putExtra("email",useremail);

                        startActivity(govei4);
                    break;
                case 5:
                    Intent govei5 = new Intent(getBaseContext(), parques.class);
                    govei5.putExtra("email",useremail);

                    startActivity(govei5);
                    break;

                    case 6:
                    Intent govei6 = new Intent(getBaseContext(), infolegal.class);
                    govei6.putExtra("email",useremail);

                    startActivity(govei6);
                    break;

                    case 7:
                        Intent govei7 = new Intent(getBaseContext(), definicoes.class);
                        govei7.putExtra("email",useremail);

                        startActivity(govei7);
                        break;
                }
            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Button mveiButton = (Button) findViewById(R.id.Btn_Veiculos);

        mveiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent govei = new Intent(homev3.this, veiculos.class);
                govei.putExtra("email",useremail);

                startActivity(govei);



            }
        });

        Button mnovareservaButton = (Button) findViewById(R.id.Btn_novareserva);

        mnovareservaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent govei = new Intent(homev3.this, novareserva.class);
                govei.putExtra("email",useremail);

                startActivity(govei);



            }
        });

        Button parquesButton = (Button) findViewById(R.id.Btn_parques);

        parquesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent govei = new Intent(homev3.this, parques.class);
                govei.putExtra("email",useremail);

                startActivity(govei);



            }
        });


        Button shuttleButton = (Button) findViewById(R.id.Btn_Shuttle);
        shuttleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent govei = new Intent(homev3.this, shuttle.class);
                govei.putExtra("email",useremail);

                startActivity(govei);



            }
        });

        Button reservastivaButton = (Button) findViewById(R.id.Btn_reservastiva);
        reservastivaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent govei = new Intent(homev3.this, reservastivas.class);
                govei.putExtra("email",useremail);

                startActivity(govei);



            }
        });
        // Set up the user interaction to manually show or hide the system UI.


        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
       mDrawerToggle.syncState();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.manu_main, menu);
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

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }



        return super.onOptionsItemSelected(item);
    }






    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */

}
