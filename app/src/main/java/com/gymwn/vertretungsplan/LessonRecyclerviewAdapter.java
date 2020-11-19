package com.gymwn.vertretungsplan;

import android.accessibilityservice.GestureDescription;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class LessonRecyclerviewAdapter extends RecyclerView.Adapter<LessonRecyclerviewAdapter.viewholder> {

    private Context context;
    private List<LessonRecyclerviewSetterGetter> details;

    public LessonRecyclerviewAdapter(Context context, List<LessonRecyclerviewSetterGetter> details) {
        this.context = context;
        this.details = details;
    }

    public class viewholder extends RecyclerView.ViewHolder {
        public TextView case0tv, subjecttv, lessontv;
        public viewholder(View view) {
            super(view);
            case0tv = view.findViewById(R.id.case0);
            subjecttv = view.findViewById(R.id.subject);
            lessontv = view.findViewById(R.id.lesson);
        }
        public String classfiller(String s) {
            String[][] dims = {{"Eng", "Englisch"},
                    {"Erd", "Erdkunde"},
                    {"Bio", "Biologie"},
                    {"Ver", "Verfügung"},
                    {"Inf", "Informatik"},
                    {"Mat", "Mathe"},
                    {"Deu", "Deutsch"},
                    {"Che", "Chemie"},
                    {"Spo", "Sport"},
                    {"Swi", "Schwimmen"},
                    {"Ges", "Geschichte"},
                    {"Pol", "Politik"},
                    {"Kun", "Kunst"},
                    {"Aufs", "Aufsicht"},
                    {"Aufg", "Aufgaben"},
                    {"Spa", "Spanisch"},
                    {"Fra", "Französisch"},
                    {"Lat", "Latein"},
                    {"Mus", "Musik"},
                    {"SGSE", "Steuergruppe für Schulentwicklung"},
                    {"Phy", "Physik"},
                    {"&nbsp;", ""}
            };
            if(!s.matches(".*\\d.*"))
                for (int i = 0; 0 < dims.length; i++)
                    try {if (dims[i][0].equals(s))
                        return dims[i][1]; }
                    catch (Exception e) { return s;}
            return s;
        }
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_plan, parent, false);
        return new viewholder(itemView);
    }


    @SuppressLint("UseCompatLoadingForColorStateLists")
    @Override
    public void onBindViewHolder(final viewholder holder, int position) {
        LessonRecyclerviewSetterGetter lessonRecyclerviewSetterGetter = details.get(position);
        holder.subjecttv.setText(holder.classfiller(lessonRecyclerviewSetterGetter.getSubject()));
        holder.lessontv.setText(lessonRecyclerviewSetterGetter.getLesson());
        if(lessonRecyclerviewSetterGetter.isFree() && lessonRecyclerviewSetterGetter.getElso().equals("") && lessonRecyclerviewSetterGetter.getRoom().equals("---")) {
            holder.case0tv.setText("Fällt aus");
            holder.case0tv.setBackgroundTintList(context.getResources().getColorStateList(R.color.red));
        } else if(lessonRecyclerviewSetterGetter.isReplace()) {
            holder.case0tv.setText("Vertretung");
            holder.case0tv.setBackgroundTintList(context.getResources().getColorStateList(R.color.blue));
        } else if(lessonRecyclerviewSetterGetter.isExercises()) {
            holder.case0tv.setText("Aufgaben");
            holder.case0tv.setBackgroundTintList(context.getResources().getColorStateList(R.color.gray));
        }
        if(!lessonRecyclerviewSetterGetter.getRoom().equals("---"))
            holder.subjecttv.setText(holder.subjecttv.getText() + ", " + ((lessonRecyclerviewSetterGetter.getRoom().toLowerCase().contains("?".toLowerCase())) ? lessonRecyclerviewSetterGetter.getRoom().split("\\?")[1] : lessonRecyclerviewSetterGetter.getRoom()));
        //holder.case0tv.setText(lessonRecyclerviewSetterGetter.get);
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}
