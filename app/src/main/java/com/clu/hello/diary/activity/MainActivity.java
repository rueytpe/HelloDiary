package com.clu.hello.diary.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.clu.hello.diary.R;
import com.clu.hello.diary.databinding.ActivityMainBinding;
import com.clu.hello.diary.db.DatabaseHelper;
import com.clu.hello.diary.model.DiaryModel;
import com.clu.hello.diary.util.Utils;

import java.util.List;

/**
 * 11:37
 */
public class MainActivity extends AppCompatActivity {

    private String signature;

    private ActivityMainBinding binding;
    private EditText editTxtNote;
    private DatabaseHelper databaseHelper;
    private DiaryModel todayDiaryModel;
    private TextView txtDate;
    private EditText editTextWeather;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
   //     setContentView(R.layout.activity_main);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        signature = prefs.getString("signature", "");
        this.setTitle("Hello " + signature);
        setContentView(binding.getRoot());

        mySettings();
    }

    private void mySettings() {

        databaseHelper = new DatabaseHelper(this);

        List<DiaryModel> diaryModels = databaseHelper.findRecsByDate(Utils.getTodayStrforDatabase());
        editTxtNote = findViewById(R.id.edtTxtNote);
        editTextWeather = findViewById(R.id.edtTxtWeather);
        if (diaryModels != null && diaryModels.size() > 0) {
            this.todayDiaryModel = diaryModels.get(0);
            editTxtNote.setText(todayDiaryModel.getDiaryContent());
            editTextWeather.setText(todayDiaryModel.getDiaryWeather());
        }

        txtDate = findViewById(R.id.txtDate);
        txtDate.setText(Utils.getTodayStrforView());

        Toast.makeText(this, "signature = " + signature, Toast.LENGTH_SHORT).show();

    }

    public void onBtnHelloClick(View view) {

        DiaryModel diaryModel = new DiaryModel();

        diaryModel.setDiaryDate(Utils.getTodayStrforDatabase());
        diaryModel.setFullName(signature);
        diaryModel.setDiaryWeather(editTextWeather.getText().toString());
        diaryModel.setDiaryContent(editTxtNote.getText().toString());

        List<DiaryModel> diaries = databaseHelper.findRecsByDate(Utils.getTodayStrforDatabase());

        if (todayDiaryModel != null) {
            boolean success = databaseHelper.updateOne(todayDiaryModel.getId(), signature, editTextWeather.getText().toString(), editTxtNote.getText().toString());
            Toast.makeText(this, "Today's Diary is has been successfully updated", Toast.LENGTH_SHORT).show();
            return;
        } else {
            boolean success = databaseHelper.addOne(diaryModel);
            Toast.makeText(this, "Today's Diary has been successfully created", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem setting = menu.findItem(R.id.settings_menu);
        setting.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                return false;
            }
        });

        MenuItem viewAll = menu.findItem(R.id.view_all_menu);
        viewAll.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(MainActivity.this, ViewAllActivity.class);
                startActivity(intent);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_menu:
                Toast.makeText(this, "Setting selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.view_all_menu:
                Toast.makeText(this, "View All selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}