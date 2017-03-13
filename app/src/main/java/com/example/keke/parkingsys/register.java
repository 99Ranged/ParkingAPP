package com.example.keke.parkingsys;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class register extends AppCompatActivity {
    private EditText rName;
    private EditText rDob;
    private EditText rEmail;
    private EditText rPassword;
    private UserRegisterTask mRegTask = null;
    private int[] focus = new int[4];
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

        rDob.setText(sdf.format(myCalendar.getTime()));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        rName = (EditText) findViewById(R.id.register_name);
        rDob = (EditText) findViewById(R.id.register_dob);
        rEmail = (EditText) findViewById(R.id.register_email);
        rPassword = (EditText) findViewById(R.id.register_password);




        rDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(register.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Button Btn_registerUser = (Button) findViewById(R.id.Btn_Register);
        Btn_registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerifyRegister();
            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }





    protected void VerifyRegister() {

        final String srName = rName.getText().toString();
        final String srDob = rDob.getText().toString();
        final String srEmail = rEmail.getText().toString();
        final String srPassword = rPassword.getText().toString();


        rPassword.setError(null);
        rName.setError(null);
        rDob.setError(null);
        rEmail.setError(null);
        if (TextUtils.isEmpty(srPassword) || srPassword.length() < 4) {
            rPassword.setError("A sua password deverá ter pelo menos 4 caracteres");
            focus[3] = 1;

        } else {
            rPassword.setError(null);
            focus[3] = 0;
        }


        if (!(srEmail.contains("@")) || (srEmail.isEmpty())) {
            rEmail.setError("Insira um e-mail válido");
            focus[2] = 1;

        } else {
            rEmail.setError(null);
            focus[2] = 0;
        }


        if ((srDob.isEmpty())) {
            rDob.setError("Insira a sua data de nascimento");
            focus[1] = 1;

        } else {
            rDob.setError(null);
            focus[1] = 0;
        }


        if ((srName.isEmpty()) || srName.length() < 2) {
            rName.setError("Insira o seu nome");
            focus[0] = 1;

        } else {
            rName.setError(null);
            focus[0] = 0;
        }


        if (focus[3] == 1) {
            rPassword.requestFocus();

        }


        if (focus[2] == 1) {
            rEmail.requestFocus();

        }

        if (focus[1] == 1) {
            rDob.requestFocus();

        }

        if (focus[0] == 1) {
            rName.requestFocus();

        }

        int focuscounter = 0;



        for(int i = 0;i<focus.length;i++)
        {
            if(focus[i] == 1)
            {
                focuscounter++;
            }
        }

        if(focuscounter==0)
        {
            Log.d("TAG","All good");
            mRegTask = new UserRegisterTask(srName, srDob, srEmail, srPassword);
            Boolean logins = mRegTask.doInBackground();
        }
        //Do the registration
        //mRegTask.doInBackground();


    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("register Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }




    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final String mName;
        private final String mDob;

        UserRegisterTask(String name, String dob, String email, String password) {
            mEmail = email;
            mName = name;
            mDob = dob;
            mPassword = password;
            Log.d(mEmail, "hey this is value of email ");
            Log.d(mName, "hey this is value of name ");
            Log.d(mDob, "hey this is value of dob ");
            Log.d(mPassword, "hey this is value of password");


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
            Log.d("TAG","Register link is :" + AppConfig.URL_REGISTER);
            String tag_string_req = "req_register";

            StringRequest strReq = new StringRequest(Request.Method.POST,
                    AppConfig.URL_REGISTER, new Response.Listener<String>() {

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

                            //debug
                            Log.d(response, "This is response:");


                            //debug end

                            // TO:DO Redirect to other main activity
                            //DO a POP-UP to let user know we entered a new user in DB!!!
                            AlertDialog.Builder builder1 = new AlertDialog.Builder(register.this);
                            builder1.setMessage("Registo completado com sucesso!");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Voltar",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            startActivity(new Intent(register.this, ParkingLogin.class));
                                        }
                                    });



                            AlertDialog alert11 = builder1.create();
                            alert11.show();


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
                    Log.d("TAG","We are inside HASH MAP");
                    Log.d("TAG","Email is:" + mEmail);
                    Log.d("TAG","Password is:" + mPassword);
                    Log.d("TAG","Dob is:" + mDob);
                    Log.d("TAG","Name is:" + mName);
                    Map<String, String> params = new HashMap<>();
                    params.put("Username", mEmail);
                    params.put("Password", mPassword);
                    params.put("Dob", mDob);
                    params.put("Name", mName);

                    return params;
                }

            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(strReq, tag_string_req);


            return true;
        }


        //new login cheking end


    }
}
