package com.clu.hello.diary.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.clu.hello.diary.R;
import com.clu.hello.diary.activity.EditActivity;
import com.clu.hello.diary.db.DiaryDbHelper;
import com.clu.hello.diary.util.Utils;
import com.clu.hello.diary.vo.Diary;

import java.util.ArrayList;

public class DiaryRecViewAdapter extends RecyclerView.Adapter<DiaryRecViewAdapter.ViewHolder> {

    private static final String TAG = "DiaryRecViewAdapter";
    private static final String DIARY_ID_KEY = "id";
    private ArrayList<Diary> diaries = new ArrayList<>();
    private Context mContext;
    private String parentAcrivity;

    public DiaryRecViewAdapter(Context mContext, String parentAcrivity) {
        this.mContext = mContext;
        this.parentAcrivity = parentAcrivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_diary, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    public void setDiaries(ArrayList<Diary> diaries) {
        this.diaries = diaries;
        notifyDataSetChanged();
    }

    public ArrayList<Diary> getDiaries() {
        return diaries;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");
        holder.txtName.setText(diaries.get(position).getFullName());
        holder.txtDate.setText(diaries.get(position).getDiaryDate());
        holder.txtWeather.setText(diaries.get(position).getDiaryWeather());
        holder.txtContent.setText(diaries.get(position).getDiaryContent());

        String todayStr = Utils.getTodayStrforDatabase();
        String txtDateStr = diaries.get(position).getDiaryDate();
        holder.btnDelete.setVisibility(View.VISIBLE);
        if (todayStr.equals(txtDateStr)) {
            holder.txtToday.setVisibility(View.VISIBLE);
        } else {
            holder.txtToday.setVisibility(View.GONE);
        }

        if (diaries.get(position).isExpanded()) {
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedDiaryLayout.setVisibility(View.VISIBLE);
            holder.downArrow.setVisibility(View.GONE);

        } else {
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedDiaryLayout.setVisibility(View.GONE);
            holder.downArrow.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return diaries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parent;

        private TextView txtDate, txtContent, txtWeather, txtName, txtToday, btnDelete, btnEdit;

        private ImageView downArrow, upArrow;
        private RelativeLayout expandedDiaryLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            expandedDiaryLayout = itemView.findViewById(R.id.expandedDiaryLayout);
            txtName = itemView.findViewById(R.id.txtName);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtContent = itemView.findViewById(R.id.txtContent);
            txtWeather = itemView.findViewById(R.id.txtWeather);
            txtToday = itemView.findViewById(R.id.txtToday);
            downArrow = itemView.findViewById(R.id.btnDownArrow);
            upArrow = itemView.findViewById(R.id.btnUpArrow);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnEdit = itemView.findViewById(R.id.btnEdit);

            downArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Diary diary = diaries.get(getAdapterPosition());
                    diary.setExpanded(!diary.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            upArrow.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Diary diary = diaries.get(getAdapterPosition());
                    diary.setExpanded(!diary.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Diary diary = diaries.get(getAdapterPosition());
                    DiaryDbHelper databaseHelper = new DiaryDbHelper(mContext);
                    boolean success = databaseHelper.deleteOne(diary.getId());
                    diaries.remove(diary);
                    notifyDataSetChanged();
                    Toast.makeText(mContext, "The diary on " + diary.getDiaryDate() + " has been successfully deleted!", Toast.LENGTH_SHORT).show();

                }
            });

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Diary diary = diaries.get(getAdapterPosition());
                    Intent intent = new Intent(mContext, EditActivity.class);
                    intent.putExtra(DIARY_ID_KEY, diary.getId());
                    mContext.startActivity(intent);
                }
            });


        }
    }
}
