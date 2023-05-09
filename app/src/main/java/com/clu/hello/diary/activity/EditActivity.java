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

    public static final String DIARY_ID_KEY = "id";
    public static final String SIGNATURE_KEY = "signature";
    private EditText edtTxtWeather;
    private EditText edtTxtNote;
    private TextView txtDate;
    private int diaryId;
    private String signature;
 //   private DiaryModel diaryModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        txtDate = findViewById(R.id.txtDate);
        if (null != intent) {
            diaryId = intent.getIntExtra(DIARY_ID_KEY, -1);

            signature = intent.getStringExtra(SIGNATURE_KEY);
            if (diaryId != -1) {
                DatabaseHelper databaseHelper = new DatabaseHelper(EditActivity.this);
                DiaryModel diaryModel = databaseHelper.findRecById(String.valueOf(diaryId));
                edtTxtNote = findViewById(R.id.edtTxtNote);
                edtTxtWeather = findViewById(R.id.edtTxtWeather);
                txtDate.setText(Utils.changeDateFormat(diaryModel.getDiaryDate(), Utils.DB_DATE_FORMAT, Utils.DISPLAY_DATE_FORMAT));
                edtTxtNote.setText(diaryModel.getDiaryContent());
                edtTxtWeather.setText(diaryModel.getDiaryWeather());
                diaryId = diaryModel.getId();

            } else {
                this.setTitle("Add a daily entry");
                txtDate.setText(Utils.getTodayStrforDatabase());

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

        edtTxtWeather = findViewById(R.id.edtTxtWeather);
        edtTxtNote = findViewById(R.id.edtTxtNote);
//        diaryModel.setDiaryWeather(editTextWeather.getText().toString());
//        diaryModel.setDiaryContent(editTxtNote.getText().toString());

        DatabaseHelper databaseHelper = new DatabaseHelper(EditActivity.this);
//        List<DiaryModel> diaries = databaseHelper.findRecsByDate(Utils.getTodayStrforDatabase());
        if (diaryId != -1) {
            boolean success = databaseHelper.updateOne(diaryId, edtTxtWeather.getText().toString(), edtTxtNote.getText().toString());
            Toast.makeText(this, "Diary has been successfully updated", Toast.LENGTH_SHORT).show();
        } else {
            DiaryModel diaryModel = new DiaryModel();

            diaryModel.setDiaryDate(Utils.getTodayStrforDatabase());
            diaryModel.setFullName(signature);
            diaryModel.setDiaryWeather(edtTxtWeather.getText().toString());
            diaryModel.setDiaryContent(edtTxtNote.getText().toString());

            boolean success = databaseHelper.addOne(diaryModel);
            Toast.makeText(this, "Today's Diary has been successfully created", Toast.LENGTH_SHORT).show();

        }

        return;


    }
}