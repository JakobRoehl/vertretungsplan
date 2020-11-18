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
import android.os.Handler;
import android.os.Looper;
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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class Fragment_Plan extends Fragment {
    private ProgressDialog mainloaderdialog;
    private View view;
    private Spinner day_spinner, class_spinner;
    private String day1, day2;
    private String[] days = new String[2];
    private RecyclerView lesson_recyclerview;
    private List<LessonRecyclerviewSetterGetter> details;
    private LessonRecyclerviewAdapter lessonRecyclerviewAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plan, container,false);
        final Handler handler = new Handler(Looper.getMainLooper());
        mainloaderdialog = new ProgressDialog(getActivity(), R.style.black_specs);
        mainloaderdialog.setMessage("Empfange Daten");
        mainloaderdialog.setCancelable(false);
        mainloaderdialog.show();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms

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
                classesday1[j] = String.valueOf(JSONday1.names().get(j)).replace("-", "/");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        for(int k = 0; k < JSONday2.length(); k++) {
            try {
                classesday2[k] = String.valueOf(JSONday2.names().get(k)).replace("-", "/");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        ArrayAdapter<String> dayadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, days);
        dayspinner.setAdapter(dayadapter);


        final JSONObject finalJsonObject1 = jsonObject;
        final JSONObject finalJsonObject2 = jsonObject2;
        dayspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                switch(i) {
                    case 0:
                        ArrayAdapter<String> classadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, classesday1);
                        class_spinner.setAdapter(classadapter);
                        try {
                            prepareDetails(finalJsonObject1.getJSONObject(days[0]).getJSONObject(classesday1[class_spinner.getSelectedItemPosition()].replace("/", "-")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                        break;
                    case 1:
                        ArrayAdapter<String> classadapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, classesday2);
                        class_spinner.setAdapter(classadapter2);
                        try {
                            prepareDetails(finalJsonObject2.getJSONObject(days[1]).getJSONObject(classesday2[class_spinner.getSelectedItemPosition()].replace("/", "-")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                        }
                        break;
                    default:
                }



            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //ArrayAdapter<String> classadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, classesday1);
                //class_spinner.setAdapter(classadapter);
            }

        });
        class_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String s = (dayspinner.getSelectedItemPosition() == 0) ? days[0] : days[1];
                String[] sa = (dayspinner.getSelectedItemPosition() == 0) ? classesday1 : classesday2;
                JSONObject jo = (dayspinner.getSelectedItemPosition() == 0) ? finalJsonObject1 : finalJsonObject2;
                try {
                    prepareDetails(jo.getJSONObject(s).getJSONObject(sa[class_spinner.getSelectedItemPosition()].replace("/", "-")));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //ArrayAdapter<String> classadapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, classes);
        //dayspinner.setAdapter(classadapter);

        lesson_recyclerview = (RecyclerView)view.findViewById(R.id.lesson_recyclerview);

        details = new ArrayList<>();
        lessonRecyclerviewAdapter = new LessonRecyclerviewAdapter(getActivity(), details);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        lesson_recyclerview.setLayoutManager(layoutManager);
        lesson_recyclerview.setItemAnimator(new DefaultItemAnimator());

        //lesson_recyclerview.setAdapter(lessonRecyclerviewAdapter);


            }
        }, 500);


        return view;
    }
    private void prepareDetails(JSONObject jsonObjectORIG) throws JSONException {
        final String[] lessons = new String[jsonObjectORIG.length()];
        details.clear();
        //Toast.makeText(getActivity(), String.valueOf(jsonObjectORIG.length()), Toast.LENGTH_LONG).show();
        for(int j = 0; j < jsonObjectORIG.length(); j++) {
            try {
                lessons[j] = jsonObjectORIG.names().get(j).toString();
                JSONObject jsonObject = jsonObjectORIG.getJSONObject(lessons[j]);

                LessonRecyclerviewSetterGetter lessonRecyclerviewSetterGetter = new LessonRecyclerviewSetterGetter();
                lessonRecyclerviewSetterGetter.setElso(jsonObject.getString("elso"));
                lessonRecyclerviewSetterGetter.setExercises(jsonObject.getBoolean("exercises"));
                lessonRecyclerviewSetterGetter.setFree(jsonObject.getBoolean("free"));
                lessonRecyclerviewSetterGetter.setInfo(jsonObject.getString("info"));
                lessonRecyclerviewSetterGetter.setLesson(lessons[j].split("#")[0].replace(" ", ""));
                lessonRecyclerviewSetterGetter.setRoom(jsonObject.getString("room"));
                lessonRecyclerviewSetterGetter.setReplace(jsonObject.getBoolean("replace"));
                lessonRecyclerviewSetterGetter.setSubject(jsonObject.getString("subject"));
                //Toast.makeText(getActivity(), jsonObject.getString("subject"), Toast.LENGTH_LONG).show();
                details.add(lessonRecyclerviewSetterGetter);

            } catch (JSONException e) {

                e.printStackTrace();
            }
        }

        lesson_recyclerview.setAdapter(lessonRecyclerviewAdapter);
        lessonRecyclerviewAdapter.notifyDataSetChanged();

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
