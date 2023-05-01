package com.clu.hello.diary.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.clu.hello.diary.R;
import com.clu.hello.diary.db.DatabaseHelper;
import com.clu.hello.diary.model.DiaryModel;
import com.clu.hello.diary.util.Utils;

import java.util.List;

public class EditActivity extends AppCompatActivity {

    private static final String DIARY_ID_KEY = "id";
    private EditText editTextWeather;
    private EditText editTxtNote;
    private TextView txtDate;
    DiaryModel diaryModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        if (null != intent) {

            int diaryId = intent.getIntExtra(DIARY_ID_KEY, -1);
            if (diaryId != -1) {
                DatabaseHelper databaseHelper = new DatabaseHelper(EditActivity.this);

                diaryModel = databaseHelper.findRecById(String.valueOf(diaryId));
                editTxtNote = findViewById(R.id.edtTxtNote);
                editTextWeather = findViewById(R.id.edtTxtWeather);
                txtDate = findViewById(R.id.txtDate);
                txtDate.setText(Utils.changeDateFormat(diaryModel.getDiaryDate(), Utils.DB_DATE_FORMAT, Utils.DISPLAY_DATE_FORMAT));
                editTxtNote.setText(diaryModel.getDiaryContent());
                editTextWeather.setText(diaryModel.getDiaryWeather());

            }

        }
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


    public void onBtnUpdateClick(View view) {

        diaryModel.setDiaryWeather(editTextWeather.getText().toString());
        diaryModel.setDiaryContent(editTxtNote.getText().toString());

        DatabaseHelper databaseHelper = new DatabaseHelper(EditActivity.this);
        List<DiaryModel> diaries = databaseHelper.findRecsByDate(Utils.getTodayStrforDatabase());
        boolean success = databaseHelper.updateOne(diaryModel.getId(), editTextWeather.getText().toString(), editTxtNote.getText().toString());
        Toast.makeText(this, "Diary " + diaryModel.getDiaryDate() + " has been successfully updated", Toast.LENGTH_SHORT).show();

        return;


    }
}