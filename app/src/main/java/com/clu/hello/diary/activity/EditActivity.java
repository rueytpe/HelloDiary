package com.clu.hello.diary.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.clu.hello.diary.R;
import com.clu.hello.diary.db.DatabaseHelper;
import com.clu.hello.diary.model.DiaryModel;
import com.clu.hello.diary.util.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class EditActivity extends AppCompatActivity {

    public static final String DIARY_ID_KEY = "id";
    public static final String SIGNATURE_KEY = "signature";
    private EditText edtTxtWeather;
    private EditText edtTxtNote;
    private EditText edtTxtDate;
    private int diaryId;
    private String signature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        edtTxtDate = findViewById(R.id.edtTxtDate);
        if (null != intent) {
            diaryId = intent.getIntExtra(DIARY_ID_KEY, -1);

            signature = intent.getStringExtra(SIGNATURE_KEY);
            if (diaryId != -1) {
                DatabaseHelper databaseHelper = new DatabaseHelper(EditActivity.this);
                DiaryModel diaryModel = databaseHelper.findRecById(String.valueOf(diaryId));
                edtTxtNote = findViewById(R.id.edtTxtNote);
                edtTxtWeather = findViewById(R.id.edtTxtWeather);
                edtTxtDate.setText(Utils.changeDateFormat(diaryModel.getDiaryDate(), Utils.DB_DATE_FORMAT, Utils.DISPLAY_DATE_FORMAT));
                edtTxtNote.setText(diaryModel.getDiaryContent());
                edtTxtWeather.setText(diaryModel.getDiaryWeather());
                diaryId = diaryModel.getId();

            } else {
                this.setTitle("Add a daily entry");
                edtTxtDate.setText(Utils.getTodayStrforDatabase());

            }

        }

        edtTxtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTxtDate.setText("");
                showDatePickerDialog();
            }
        });
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
        edtTxtDate = findViewById(R.id.edtTxtDate);

        if (!validateInput(edtTxtDate.getText().toString())) {
            return;
        }

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

        Intent intent = new Intent(EditActivity.this, MainActivity.class);
        startActivity(intent);
        return;
    }

    public boolean validateInput(String dateStr) {


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(dateStr);

        } catch (ParseException e) {
            Toast.makeText(this, "The date format should be yyyy-MM-dd", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void showDatePickerDialog() {
        // Get current date
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        // Create DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Update the EditText with the selected date
                        edtTxtDate.setText(Utils.createDateByDbFormat(year, month + 1, dayOfMonth));
                    }
                },
                year,
                month,
                dayOfMonth
        );

        // Show the dialog
        datePickerDialog.show();
    }
}