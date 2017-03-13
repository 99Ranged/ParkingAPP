package com.example.keke.parkingsys;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

public class shuttle extends AppCompatActivity   {
    private ProgressDialog pDialog;
    private ListView mDrawerList;
    private ArrayAdapter<String> mAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;
    TextView mNome;
    TextView preco;
    double precofinal = 0;
    //private novareserva.UserRegisterTask mGetVei = null;
    private UserAddCarTask mAddCar = null;
    private GetParquesTask mGetParques = null;

    private TextView Dinicio;
    private TextView Dfim;
    private TextView Tinicio;
    private TextView Tfim;
    String[] matriculas;
    String[] marcas;
    String[] tipoveiculo;
    String[] parques;
    String[] parquesdesc;
    String[] parques2;
    String[] parquesdesc2;
    int numvei;
    ListView list;
    LinearLayout lnrLayout;
    LinearLayout lnrLayoutalert;
    String useremailvei;
    String setMatricula;
    String setMarca;
    String setTipoVeiculo;
    Spinner spinnervei;
    Spinner spinnerdestino;
    EditText inputMatricula;
    EditText inputMarca;
    CheckBox premium;
    static final int DATE_DIALOG_ID = 0;

    private TextView activeDateDisplay;
    private Calendar activeDate;
    private Calendar startDate;
    private Calendar endDate;
    String[] itemname = {
            "Home",
            "Veículos",
            "Reservas",
            "Nova Reserva",

            "Histórico",
            "Parques",
            "Info Legal",
            "Definições",

    };

    Integer[] imgid = {
            R.drawable.house   ,
            R.drawable.car,
            R.drawable.creditcard,
            R.drawable.add,
            R.drawable.history,
            R.drawable.park,
            R.drawable.hammer,
            R.drawable.settings,

    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
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

        CustomListAdapter mAdapter = new CustomListAdapter(this, itemname, imgid);

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

        setContentView(R.layout.activity_shuttle);
        setTitle("Nova Reserva de shuttle");

        preco = (TextView) findViewById(R.id.Txt_Preco);

        //Calendarios
        Dinicio = (TextView) findViewById(R.id.Txt_DataInicio);

        startDate = Calendar.getInstance();

        Dinicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(Dinicio, startDate);
            }
        });




        //tempos
        Tinicio = (TextView) findViewById(R.id.Tempoinicio);


        Tinicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);


                TimePickerDialog timePickerDialog = new TimePickerDialog(shuttle.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                Tinicio.setText(getText(R.string.Hora).toString() +" "+ hourOfDay + ":" + minute);
                                if(hourOfDay>18)
                                {
                                    precofinal = 6 * (1 + hourOfDay/3);
                                }else
                                    precofinal = 6 * (1 + hourOfDay/6);

                                preco.setText(precofinal + "€");
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });





        updateDisplay(Dinicio, startDate);

        Log.d("TAG", "Data fim é:" + Dfim  );

        //Iniciar pdialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        //Estamosa ir buscar o utilizador, que veio do outro intent
        if (getIntent().getStringExtra("email") != null) {
            useremailvei = getIntent().getStringExtra("email");
        }

        //Barra lateral
        mDrawerList = (ListView) findViewById(R.id.navList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        //Criar o layout que vai conter os textedit
        lnrLayout = (LinearLayout) findViewById(R.id.mainlayout2);
        lnrLayout.getLayoutParams().height = DrawerLayout.LayoutParams.WRAP_CONTENT;


        //Layout para add car
        lnrLayoutalert = new LinearLayout(getBaseContext());
        lnrLayoutalert.setOrientation(LinearLayout.VERTICAL);


        //Spinner para parque
        String colors[] = {"Estação Ferroviária de Lisboa-Santa Apolónia","Aeroporto Humberto Delgado","Estação Ferroviária do Rossio", "Terminal Fluvial - Cais do Sodré"};
        mGetParques = new GetParquesTask(useremailvei);

        getParques();

        // Selection of the spinner
        spinnervei = (Spinner) findViewById(R.id.Spn_parques);
        spinnerdestino = (Spinner) findViewById(R.id.Spn_destinos);
        // Log.d("ARRRAY parque is:", "INSIDE escapevars " + parques[0]);

       // Application of the Array to the Spinner
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, colors);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        spinnerdestino.setAdapter(spinnerArrayAdapter);





        addDrawerItems();
        setupDrawer();
        //attemptgetvei();
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
                        Intent govei4 = new Intent(getBaseContext(), historico.class);
                        govei4.putExtra("email",useremailvei);

                        startActivity(govei4);
                        break;
                    case 5:
                        Intent govei5 = new Intent(getBaseContext(), parques.class);
                        govei5.putExtra("email",useremailvei);

                        startActivity(govei5);
                        break;

                    case 6:
                        Intent govei6 = new Intent(getBaseContext(), infolegal.class);
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
        Button mAddReservaButton = (Button) findViewById(R.id.Btn_addreserva);

        mAddReservaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkshuttle();
            }
        });
        //checkbox


       /* mAddCarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptAddCar();

            }
        });*/

        // Set up the user interaction to manually show or hide the system UI.


        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /* private boolean attemptgetvei() {

         mGetVei = new novareserva.UserRegisterTask(useremailvei);
         mGetVei.doInBackground();
         return true;
     }*/
    private boolean getParques() {

        mGetParques.doInBackground();
        return true;
    }



        public void checkshuttle()
        {

            String tempoinicio = Tinicio.getText().toString();
            tempoinicio = tempoinicio.replace("Hora:", "");
            if(TextUtils.isEmpty(tempoinicio))
            {
                Tinicio.setError("Seleccione uma hora");

            }else {
                Tinicio.setError(null);
                Toast.makeText(getApplicationContext(), "Shuttle reservado!" + tempoinicio, Toast.LENGTH_LONG).show();
                Intent govei = new Intent(getBaseContext(), homev3.class);
                govei.putExtra("email",useremailvei);

                startActivity(govei);



            }



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
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("novareserva Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }






    public class GetParquesTask extends AsyncTask<Void, Void, Boolean> {


        private final String mEmail;

        GetParquesTask(String email) {

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
            Log.d("TAG", "After Sleep");
            Log.d("TAG", "Register link is :" + AppConfig.URL_GETPARQUES);
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
                        Log.d("TAG", "Here is the object1337" + jObj);
                        // Check for error node in json
                        if (!error) {
                            // user successfully registred

                            numvei = jObj.getInt("numpar");


                            //Adicionar todas as matriculas e marcas recebidas
                            //TO:DO caso nao haja nada para receber

                            parques = new String[numvei];
                            parquesdesc = new String[numvei];
                            for (int i = 0; i < numvei; i++) {


                                parques[i] = jObj.getString("parque" + (i + 1));
                                parquesdesc[i] = jObj.getString("parquedesc" + (i + 1));


                            }

                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, parques);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                            spinnervei.setAdapter(spinnerArrayAdapter);

                            escapevars(parques, parquesdesc);
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
                            Toast.makeText(getApplicationContext(), "Json error: " + errorMsg, Toast.LENGTH_LONG).show();


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
                    Log.d("TAG", "We are inside HASH MAP");
                    Log.d("TAG", "Email is:" + mEmail);

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

    public class UserAddCarTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;


        UserAddCarTask(String email) {
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
            Log.d("TAG", "After Sleep");
            Log.d("TAG", "Register link is :" + AppConfig.URL_ADDVEICULOS);
            String tag_string_req = "req_register";
            pDialog.setMessage("Registrando veículo ...");
            showDialog();

            StringRequest strReq = new StringRequest(Request.Method.POST,
                    AppConfig.URL_ADDVEICULOS, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d(response, "This is response:");


                    try {
                        Log.d("TAG", "Register Response: " + response.toString());
                        JSONObject jObj = new JSONObject(response);
                        boolean error = jObj.getBoolean("error");
                        Log.d("TAG", "Here is the object1337" + jObj);
                        // Check for error node in json
                        if (!error) {
                            // user successfully registred


                            Log.d(response, "This is response:");
                            Intent gohome = new Intent(shuttle.this, shuttle.class);
                            gohome.putExtra("email", useremailvei);
                            Toast.makeText(getApplicationContext(),
                                    "Veículo inserido com sucesso!", Toast.LENGTH_LONG).show();
                            startActivity(gohome);

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

                            Toast.makeText(getApplicationContext(),
                                    errorMsg, Toast.LENGTH_LONG).show();

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
                    Log.d("TAG", "We are inside HASH MAP");
                    Log.d("TAG", "Email is:" + mEmail);

                    Map<String, String> params = new HashMap<>();
                    params.put("Username", mEmail);
                    params.put("Marca", setMarca);
                    params.put("Matricula", setMatricula);
                    params.put("TipoVeiculo", setTipoVeiculo);


                    return params;
                }

            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


            return true;
        }

        private void showDialog() {
            if (!pDialog.isShowing())
                pDialog.show();
        }

        private void hideDialog() {
            if (pDialog.isShowing())
                pDialog.dismiss();
        }


        //new login cheking end


    }


    public void getDadosVei(final AlertDialog dialog) {


        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Do stuff, possibly set wantToCloseDialog to true then...
                if (TextUtils.isEmpty(inputMarca.getText().toString())) {
                    inputMarca.setError("Insira uma marca");
                } else if (TextUtils.isEmpty(inputMatricula.getText().toString()) || !(inputMatricula.getText().toString()).matches("[A-Z]{2}[-][A-Z]{2}[-][A-Z]{2}")) {
                    inputMatricula.setError("Insira uma matricula válida");
                    inputMatricula.requestFocus();
                } else {
                    setMarca = inputMarca.getText().toString();
                    setMatricula = inputMatricula.getText().toString();
                    setTipoVeiculo = String.valueOf(spinnervei.getSelectedItemPosition() + 1);
                    dialog.dismiss();

                    mAddCar.doInBackground();
                }

                //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
            }
        });

    }


    @Override
    public void onBackPressed() {
    }


    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */


    private void updateDisplay(TextView dateDisplay, Calendar date) {
        /*precofinal = 7 * (1 + date.get(Calendar.DAY_OF_MONTH)/10 );
       
        preco.setText(precofinal + "€");*/

        dateDisplay.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(date.get(Calendar.DAY_OF_MONTH)).append("/")
                        .append(date.get(Calendar.MONTH) + 1));

    }

    public void showDateDialog(TextView dateDisplay, Calendar date) {
        activeDateDisplay = dateDisplay;
        activeDate = date;
        showDialog(DATE_DIALOG_ID);
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            activeDate.set(Calendar.YEAR, year);
            activeDate.set(Calendar.MONTH, monthOfYear);
            activeDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDisplay(activeDateDisplay, activeDate);


            unregisterDateDisplay();
        }
    };
    private void unregisterDateDisplay() {
        activeDateDisplay = null;
        activeDate = null;
    }



    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, dateSetListener, activeDate.get(Calendar.YEAR), activeDate.get(Calendar.MONTH), activeDate.get(Calendar.DAY_OF_MONTH));
        }
        return null;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        super.onPrepareDialog(id, dialog);
        switch (id) {
            case DATE_DIALOG_ID:
                ((DatePickerDialog) dialog).updateDate(activeDate.get(Calendar.YEAR), activeDate.get(Calendar.MONTH), activeDate.get(Calendar.DAY_OF_MONTH));

                break;
        }
    }

    public void escapevars(String[] parques, String[] parquesdesc) {

        parques2 = new String[parques.length];
        parquesdesc2 = new String[parques.length];
        for (int i = 0; i < parques.length; i++) {


            parques2[i] = parques[i];
            parquesdesc2[i] = parquesdesc[i];


        }

        Log.d("ARRRAY parque is:", "INSIDE escapevars " + parques2[0]);
    }
}
