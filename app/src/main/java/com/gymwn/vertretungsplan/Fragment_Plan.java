package com.gymwn.vertretungsplan;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;


public class Fragment_Plan extends Fragment {
    private ProgressDialog mainloaderdialog;
    private View view;
    private Spinner day_spinner, class_spinner;
    private String day1, day2;
    private String[] days = new String[2];

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plan, container,false);
        final TextView tv = (TextView)view.findViewById(R.id.text1);
        final Spinner dayspinner = view.findViewById(R.id.day_spinner);
        final Spinner class_spinner = view.findViewById(R.id.class_spinner);
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject2 = new JSONObject();
        try {
            day1 = JSONgetter("https://h2903870.stratoserver.net/schueler/schueler.htm");
            day2 = JSONgetter("https://h2903870.stratoserver.net/schueler/schueler2.htm");
            jsonObject = new JSONObject(day1);
            jsonObject2 = new JSONObject(day2);
            try {
                days[0] = jsonObject.names().get(0).toString();
                days[1] = jsonObject2.names().get(0).toString();

            } catch (JSONException e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JSONObject JSONday1 = null;
        JSONObject JSONday2 = null;


        try {
            JSONday1 = jsonObject.getJSONObject(days[0]);
            JSONday2 = jsonObject2.getJSONObject(days[1]);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String[] classesday1 = new String[JSONday1.length()];
        final String[] classesday2 = new String[JSONday2.length()];

        for(int j = 0; j < JSONday1.length(); j++) {
            try {
                classesday1[j] = String.valueOf(JSONday1.names().get(j));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for(int k = 0; k < JSONday2.length(); k++) {
            try {
                classesday2[k] = String.valueOf(JSONday2.names().get(k));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, days);
        dayspinner.setAdapter(dayadapter);

        tv.setText(day1);

        dayspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter<String> classadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, classesday1);
                switch(i) {
                    case 0:
                        classadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, classesday1);
                        break;
                    case 1:
                        classadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, classesday2);
                        break;
                    default:
                }
                class_spinner.setAdapter(classadapter);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                ArrayAdapter<String> classadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, classesday1);
                class_spinner.setAdapter(classadapter);
            }

        });
        //ArrayAdapter<String> classadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, classes);
        //dayspinner.setAdapter(classadapter);



        return view;
    }
    public class myAsyncTask extends AsyncTask<String, String, String> {
        public String html;

        @Override
        public String doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(params[0]);
            HttpResponse response = null;
            try {
                response = client.execute(request);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String html = "";
            InputStream in = null;
            try {
                in = response.getEntity().getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder str = new StringBuilder();
            String line = null;
            while(true)
            {
                try {
                    if (!((line = reader.readLine()) != null)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                str.append(line);
            }
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            html = str.toString();
           // Toast.makeText(getActivity(), html, Toast.LENGTH_LONG).show();
            mainloaderdialog.dismiss();
            return html;
        }

    }
    public String JSONgetter(final String surl) throws IOException {

        if (isNetworkAvailable()) {
            mainloaderdialog = new ProgressDialog(getActivity(), R.style.black_specs);
            mainloaderdialog.setMessage("Empfange Daten");
            mainloaderdialog.setCancelable(false);
            mainloaderdialog.show();


            myAsyncTask task = new myAsyncTask();
            try {

                // Toast.makeText(getActivity(), task.execute(surl).get(), Toast.LENGTH_LONG).show();
                //    Log.v("", task.execute(surl).get());
                //TextView tv = (TextView)view.findViewById(R.id.text1);
                HTMLtoJSONparser htmLtoJSONparser = new HTMLtoJSONparser();
                return (htmLtoJSONparser.main(task.execute(surl).get()));

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //Toast.makeText(getActivity(), html, Toast.LENGTH_LONG).show();

        } else {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity(), R.style.black_specs);
            builder1.setMessage("Es besteht keine Internetverbindung!");
            builder1.setTitle("Keine Verbindung");
            builder1.setIcon(R.drawable.ic_baseline_wifi_off_24);

            builder1.setCancelable(false);
            builder1.setPositiveButton(
                    "Erneut versuchen",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            try {
                                JSONgetter(surl);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
            ImageView imageView = alert11.findViewById(android.R.id.icon);
            if (imageView != null)
                imageView.setColorFilter(Color.BLACK, android.graphics.PorterDuff.Mode.SRC_IN);
        }
        return "";
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
