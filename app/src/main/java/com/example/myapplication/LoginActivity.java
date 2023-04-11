package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUser, edtPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Init();
        Act();
    }

    private void Act() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUser.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "User or password is empty. Please fill in the information", Toast.LENGTH_SHORT).show();
                }else{
                    Login("http://192.168.55.126/KhoaLuan/admin/element/mUser/userLoginApp.php", username, password);

                }
            }
        });
    }

    private void Init() {
        edtUser = findViewById(R.id.edtUser);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }
    private void Login(String LOGIN_URL, String username, String password) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Login", response.toString());
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                Toast.makeText(LoginActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Login", error.toString());
                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                return params;
            }
        };
        queue.add(stringRequest);
    }

}
