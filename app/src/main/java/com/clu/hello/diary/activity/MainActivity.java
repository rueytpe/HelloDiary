package com.clu.hello.diary.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.clu.hello.diary.R;
import com.clu.hello.diary.adapter.DiaryRecViewAdapter;
import com.clu.hello.diary.databinding.ActivityMainBinding;
import com.clu.hello.diary.db.DatabaseHelper;
import com.clu.hello.diary.model.DiaryModel;
import com.clu.hello.diary.util.Utils;
import com.clu.hello.diary.vo.Diary;

import java.util.ArrayList;
import java.util.List;

/**
 * Server: Pixel 6 Pro API 33
 *
 * 1) Deploying to Android Play Store
 *
 * 2) Design the home page
 *
 * 3) Create Add button on Home Page
 *
 * 4) Create daily, weekly, monthly, and yearly page
 *
 */
public class MainActivity extends AppCompatActivity {

    private String signature;

    private RecyclerView diariesRecView;
    private DiaryRecViewAdapter adapter;

    private ActivityMainBinding binding;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        signature = prefs.getString("signature", "");
        this.setTitle("Hello " + signature);
        setContentView(binding.getRoot());
        //////////////

        adapter = new DiaryRecViewAdapter(this, "allDiaries");
        diariesRecView = findViewById(R.id.diariesRecView);
        diariesRecView.setAdapter(adapter);
        diariesRecView.setLayoutManager(new LinearLayoutManager(this));

        //////////////

        Spinner spinner = findViewById(R.id.filterDropdown);

        String[] items = Utils.getDiaryFilterItems();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                Toast.makeText(MainActivity.this, "SelectedItem = " + selectedItem, Toast.LENGTH_SHORT).show();
                // Do something with the selected item

                updateDiaries(selectedItem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Handle the case when nothing is selected
            }
        });

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

//        MenuItem viewAll = menu.findItem(R.id.view_all_menu);
//        viewAll.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                Intent intent = new Intent(MainActivity.this, ViewAllActivity.class);
//                startActivity(intent);
//                return false;
//            }
//        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_menu:
                Toast.makeText(this, "Setting selected", Toast.LENGTH_SHORT).show();
                return true;
//            case R.id.view_all_menu:
//                Toast.makeText(this, "View All selected", Toast.LENGTH_SHORT).show();
//                return true;
            case R.id.add_menu:
                Intent intent = new Intent(this, EditActivity.class);
                intent.putExtra(EditActivity.SIGNATURE_KEY, signature);
                this.startActivity(intent);
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    final void updateDiaries(String criteria) {
        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
        List<DiaryModel> allDiaryRecords= databaseHelper.getEveryOne();
        ArrayList<Diary> allDiaries = new ArrayList<>();
        for (int i=0; i<= allDiaryRecords.size() - 1; i++) {
            DiaryModel rec = allDiaryRecords.get(i);
            Diary diary = new Diary();
            diary.setId(rec.getId());
            if (rec.getDiaryDate().indexOf(criteria) < 0) {
                continue;
            }
            String dateStr = Utils.changeDateFormat(rec.getDiaryDate(), Utils.DB_DATE_FORMAT, Utils.DISPLAY_DATE_FORMAT);
            diary.setDiaryDate(dateStr);
            diary.setDiaryWeather(rec.getDiaryWeather());
            diary.setFullName(rec.getFullName());
            diary.setDiaryContent(rec.getDiaryContent());
            allDiaries.add(diary);
        }

        adapter.setDiaries(allDiaries);

    }

}