package com.kriti.sumitdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GettingValues extends AppCompatActivity {

    ListView listView;
    ArrayList<DbInfo> dataList = new ArrayList<DbInfo>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getting_values);

        listView = findViewById(R.id.listView);
        String url = "http://192.168.29.2/sumitdemo/getting-values.php";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try
                {
                    JSONObject json = new JSONObject(String.valueOf(response));
                    String res = json.getString("res");

                    if (res.equals("OK"))
                    {
                        JSONArray rollNoArray = json.getJSONArray("rollno");
                        JSONArray nameArray = json.getJSONArray("name");
                        JSONArray marksArray = json.getJSONArray("marks");

                        for (int i = 0; i<=rollNoArray.length(); i++)
                        {
                            int rollno = rollNoArray.getInt(i);
                            String name = nameArray.getString(i);
                            int marks = marksArray.getInt(i);

                            DbInfo info = new DbInfo(rollno, name, marks);
                            dataList.add(info);
                        }
                    }
                    else
                    {
                        Log.d("Error", "JSON Object is empty");
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
                Log.d("Error", error.toString());
            }
        });

        queue.add(request);

    }
}