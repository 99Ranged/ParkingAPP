package com.example.keke.parkingsys;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

public class infolegal extends AppCompatActivity {
    private ProgressDialog pDialog;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    private infolegal.UserRegisterTask mGetVei = null;

    String[] parque;
    String[] parquedesc;
    String[] tipoveiculo;
    int numvei;
    ListView list;
    LinearLayout lnrLayout;
    LinearLayout lnrLayoutalert;
    String useremailvei;
    String setMatricula;
    String setMarca;
    String setTipoVeiculo;
    Spinner spinnervei;
    EditText inputMatricula;
    EditText inputMarca;
    String[] itemname ={
            "Home",
            "Veículos",
            "Reservas",
            "Nova Reserva",
            "Serviço Shuttle",
            "Histórico",
            "Parques",
            "Definições",

    };

    Integer[] imgid={
            R.drawable.house   ,
            R.drawable.car,
            R.drawable.creditcard,
            R.drawable.add,
            R.drawable.bus,
            R.drawable.history,
            R.drawable.park,
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

        setContentView(R.layout.activity_infolegal);
        setTitle("Informação legal");


        //Iniciar pdialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        //Estamosa ir buscar o utilizador, que veio do outro intent
        if(getIntent().getStringExtra("email") != null)
        {
            useremailvei = getIntent().getStringExtra("email");
        }

        //Barra lateral
        mDrawerList = (ListView)findViewById(R.id.navList);mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        //Criar o layout que vai conter os textedit









        addDrawerItems();
        setupDrawer();
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                switch (position)
                {

                    case 0: Intent govei = new Intent(getBaseContext(), homev3.class);
                        govei.putExtra("email",useremailvei);

                        startActivity(govei);
                        break;

                    case 1: Intent govei1 = new Intent(getBaseContext(), veiculos.class);
                        govei1.putExtra("email",useremailvei);

                        startActivity(govei1);
                        break;


                    case 2:
                        Intent govei2 = new Intent(getBaseContext(), reservastivas.class);
                        govei2.putExtra("email",useremailvei);

                        startActivity(govei2);
                        break;

                    case 3:
                        Intent govei3 = new Intent(getBaseContext(), novareserva.class);
                        govei3.putExtra("email",useremailvei);

                        startActivity(govei3);
                        break;
                    case 4:
                        Intent govei4 = new Intent(getBaseContext(), shuttle.class);
                        govei4.putExtra("email",useremailvei);

                        startActivity(govei4);
                        break;
                    case 5:
                        Intent govei5 = new Intent(getBaseContext(), historico.class);
                        govei5.putExtra("email",useremailvei);

                        startActivity(govei5);
                        break;

                    case 6:
                        Intent govei6 = new Intent(getBaseContext(), parques.class);
                        govei6.putExtra("email",useremailvei);

                        startActivity(govei6);
                        break;
                    case 7:
                        Intent govei7 = new Intent(getBaseContext(), definicoes.class);
                        govei7.putExtra("email",useremailvei);

                        startActivity(govei7);
                        break;
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        // Set up the user interaction to manually show or hide the system UI.


        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.

    }
    private boolean attemptgetvei() {

        mGetVei = new infolegal.UserRegisterTask(useremailvei);
        mGetVei.doInBackground();
        return true;
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


    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;


        UserRegisterTask(String email) {
            mEmail = email;



        }

        @Override
        protected Boolean doInBackground(Void... params) {

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }
            Log.d("TAG","After Sleep");
            Log.d("TAG","Register link is :" + AppConfig.URL_GETPARQUES);
            String tag_string_req = "req_register";

            StringRequest strReq = new StringRequest(Request.Method.POST,
                    AppConfig.URL_GETPARQUES, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(response, "This is response:");


                    try {
                        Log.d("TAG", "Register Response: " + response.toString());
                        JSONObject jObj = new JSONObject(response);
                        boolean error = jObj.getBoolean("error");
                        Log.d("TAG","Here is the object1337" + jObj);
                        // Check for error node in json
                        if (!error) {
                            // user successfully registred

                            numvei = jObj.getInt("numpar");
                            parque = new String[numvei];
                            parquedesc = new String[numvei];


                            //Adicionar todas as matriculas e marcas recebidas
                            //TO:DO caso nao haja nada para receber
                            TextView[] t2 = new TextView[numvei];
                            for (int i = 0; i<numvei;i++)
                            {


                                parque[i] = jObj.getString("parque" + (i+1));
                               parquedesc[i] = jObj.getString("parquedesc" + (i+1));



                            //Criar textviews
                                buildtexttest(i,t2);

                            }
                            //debug
                            Log.d(response, "This is response:");


                            //debug end

                            // Mensagem para debugs
                            /*AlertDialog.Builder builder1 = new AlertDialog.Builder(veiculos.this);
                            builder1.setMessage("Voce tem " + numvei + " veiculos. Matricula n1: " + matriculas[0]);
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Ok",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();

                                        }
                                    });



                            AlertDialog alert11 = builder1.create();
                            alert11.show();*/


                        } else {
                            // Error in registration. Get the error message
                            String errorMsg = jObj.getString("error_msg");

                            TextView terror = new TextView(getBaseContext());
                            terror.setText("\n\n\n\n\n\n\n\n\n\n\n\n\n\n" + errorMsg);
                            terror.setTextSize(14);
                            terror.setTextColor(Color.WHITE);
                            terror.setPaintFlags(terror.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                            terror.setPaintFlags(terror.getPaintFlags() | Paint.FAKE_BOLD_TEXT_FLAG);
                            terror.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                            lnrLayout.addView(terror);

                        }
                    } catch (JSONException e) {
                        // JSON error
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();

                }
            }) {

                @Override
                protected Map<String, String> getParams() {
                    // Posting parameters to register url
                    Log.d("TAG","We are inside HASH MAP");
                    Log.d("TAG","Email is:" + mEmail);

                    Map<String, String> params = new HashMap<>();
                    params.put("Username", mEmail);


                    return params;
                }

            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


            return true;
        }





        //new login cheking end


    }








    public void buildtexttest(int i,TextView[] t2)
    {
        Log.d("TAG"," We are inside the textview loop");
        t2[i] = new TextView(this);
        t2[i].setText("\n" + parque[i] + "\n" + parquedesc[i]);
        lnrLayout.addView(t2[i]);
    }

    @Override
    public void onBackPressed() {
    }


    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */

}
