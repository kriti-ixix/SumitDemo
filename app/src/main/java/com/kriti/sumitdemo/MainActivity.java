package com.kriti.sumitdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText nameEditText, subjectEditText, marksEditText;
    Button addValueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.editTextName);
        subjectEditText = findViewById(R.id.editTextSubject);
        marksEditText = findViewById(R.id.editTextMarks);
        addValueButton = findViewById(R.id.addValueButton);

        addValueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                String subject = subjectEditText.getText().toString();
                int marks = Integer.parseInt(marksEditText.getText().toString());
                sendValues(name, subject, marks);
            }
        });

    }

    void sendValues(final String name, final String subject, final int marks)
    {
        //ipconfig
        String url = "http://192.168.29.2/sumitdemo/inserting.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try
                {
                    JSONObject json = new JSONObject(String.valueOf(response));
                    String jsonResponse = json.getString("response");

                    if (jsonResponse.equals("SUCCESS"))
                    {
                        Toast.makeText(MainActivity.this, "Values inserted", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "Values not inserted", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley Error", error.toString());
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("name", name);
                map.put("subject", subject);
                map.put("marks", String.valueOf(marks));
                return map;
            }
        };

        queue.add(request);
    }

}