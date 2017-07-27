import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rahulroy.flighting.R;
import com.example.rahulroy.flighting.Registration;
import com.example.rahulroy.flighting.Roy;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rahul Roy on 20-03-2017.
 */
public class UserList extends AppCompatActivity implements View.OnClickListener {
    public static final String LOGIN_URL = "https://flighting.herokuapp.com/api/allUsers";

    public static final String KEY_EMAIL="email";
    public static final String KEY_PASSWORD="password";
    private EditText editTextMailid;
    private EditText editTextPassword;
    private Button buttonLogin;
    private String email;
    private String password;
    private boolean loggedIn = false;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        editTextMailid = (EditText) findViewById(R.id.emailid);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button)findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(this);
        TextView reg = (TextView)findViewById(R.id.reg);
        reg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent myIntent = new Intent(UserList.this,Registration.class);
                startActivity(myIntent);
            }
        });}

    private void userLogin() {
        email = editTextMailid.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            Log.i("hhh",response);
                            goHome();
                        }else{
                            Toast.makeText(UserList.this,response,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(UserList.this,error.toString(),Toast.LENGTH_LONG ).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(KEY_EMAIL,email);
                map.put(KEY_PASSWORD,password);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    public void goHome() {
        Intent intent = new Intent(UserList.this, Roy.class);
        intent.putExtra(KEY_EMAIL, email);
        startActivity(intent);}

    @Override
    public void onClick(View v) {
        userLogin();
    }
}

