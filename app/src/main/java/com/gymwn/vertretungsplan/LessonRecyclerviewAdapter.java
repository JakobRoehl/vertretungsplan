package com.gymwn.vertretungsplan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_plan, parent, false);
        return new viewholder(itemView);
    }

    @Override
    public void onBindViewHolder(final viewholder holder, int position) {
        LessonRecyclerviewSetterGetter lessonRecyclerviewSetterGetter = details.get(position);
        holder.subjecttv.setText(lessonRecyclerviewSetterGetter.getSubject());
        holder.lessontv.setText(lessonRecyclerviewSetterGetter.getLesson());
        //holder.case0tv.setText(lessonRecyclerviewSetterGetter.get);
    }

    @Override
    public int getItemCount() {
        return details.size();
    }
}
