package com.clu.hello.diary.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.clu.hello.diary.adapter.DiaryRecViewAdapter;
import com.clu.hello.diary.R;
import com.clu.hello.diary.db.DatabaseHelper;
import com.clu.hello.diary.model.DiaryModel;
import com.clu.hello.diary.util.Utils;
import com.clu.hello.diary.vo.Diary;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is no longer used
 * @deprecated
 */
public class ViewAllActivity extends AppCompatActivity {

    private RecyclerView diariesRecView;
    private DiaryRecViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        adapter = new DiaryRecViewAdapter(this, "allDiaries");
        diariesRecView = findViewById(R.id.diariesRecView);
        diariesRecView.setAdapter(adapter);
        diariesRecView.setLayoutManager(new LinearLayoutManager(this));
        this.updateDiaries();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.updateDiaries();

    }

    private void updateDiaries() {
        DatabaseHelper databaseHelper = new DatabaseHelper(ViewAllActivity.this);
        List<DiaryModel> allDiaryRecords= databaseHelper.getEveryOne();
        ArrayList<Diary> allDiaries = new ArrayList<>();
        for (int i=0; i<= allDiaryRecords.size() - 1; i++) {
            DiaryModel rec = allDiaryRecords.get(i);
            Diary diary = new Diary();
            diary.setId(rec.getId());
            String dateStr = Utils.changeDateFormat(rec.getDiaryDate(), Utils.DB_DATE_FORMAT, Utils.DISPLAY_DATE_FORMAT);
            diary.setDiaryDate(dateStr);
            diary.setDiaryWeather(rec.getDiaryWeather());
            diary.setFullName(rec.getFullName());
            diary.setDiaryContent(rec.getDiaryContent());
            allDiaries.add(diary);
        }

        adapter.setDiaries(allDiaries);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem setting = menu.findItem(R.id.settings_menu);
        setting.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(ViewAllActivity.this, SettingsActivity.class);
                startActivity(intent);
                return false;
            }
        });

//        MenuItem viewAll = menu.findItem(R.id.view_all_menu);
//        viewAll.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                Intent intent = new Intent(ViewAllActivity.this, ViewAllActivity.class);
//                startActivity(intent);
//                return false;
//            }
//        });
        return true;
    }
}