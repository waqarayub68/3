package com.example.waqarayub.taskasyntask;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.network_call_bt).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        HttpPostAsyncTask httpPostAsyncTask = new HttpPostAsyncTask();
        httpPostAsyncTask.execute("https://api.foursquare.com/v2/venues/explore/?near=karachi&venuePhotos=1&section=beaches&client_id=Z0XAYFIPBLPEAXCKENIQMB5HCAEGEC0VB3ZGVPRDHLT0XVYZ&client_secret=UTCRQV3AKBNQGY10LNNBNI35V4SO30VMDOZPL2JY5FJSNUOY&v=20131124");
    }


    public class HttpPostAsyncTask extends AsyncTask<String, Void, String> {


        public HttpPostAsyncTask() {
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                // This is getting the url from the string we passed in
                URL url = new URL(strings[0]);

                // Create the urlConnection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();



                    InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

                    String response = convertInputStreamToString(inputStream);

                    // From here you can convert the string to JSON with whatever JSON parser you like to use

                    return response;



            } catch (Exception e) {
                Log.d("Http Call", e.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s != null)
            {
                try
                {
                    //Log.d("String", s);
                    JSONObject obj1=new JSONObject(s);
                    JSONObject obj2=obj1.getJSONObject("response");
                    JSONArray arr1 = obj2.getJSONArray("groups");
                    JSONObject obj3 = arr1.getJSONObject(0);
                    JSONArray arr2 = obj3.getJSONArray("items");

                    //Log.d("String2", "Number of entries " + jsonArray.length());
                    ArrayList<ApiInfo> MyData_list ;
                    MyData_list= new ArrayList<>();
                    for (int i = 0; i < arr2.length(); i++) {
                        JSONObject currObject = arr2.getJSONObject(i);
                        JSONObject obj4 = currObject.getJSONObject("venue");


                        ApiInfo data = new ApiInfo(obj4.getString("id") , obj4.getString("name"));
                        MyData_list.add(data);


                    }
                    Log.d("Size01",String.valueOf(MyData_list.size()));

                    Intent intent = new Intent(MainActivity.this,RecyclerViewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("data", (Serializable) MyData_list);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
                catch (final JSONException e) {
                    System.out.println("Json parsing error: " + e.getMessage());
                }

            }else {

            }
        }

        private String convertInputStreamToString(InputStream inputStream) {

            BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                while((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }//end

    } // end Http Post Async task
}

