package com.example.hospitalservicerequest;

import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    databaseHelper db;
    ListView lvReq, lvUsers;
    ArrayList<Integer> requestIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        db = new databaseHelper(this);
        lvReq = findViewById(R.id.lvRequests);
        lvUsers = findViewById(R.id.lvUsers);

        loadData();

        // Long Press to Delete Request
        lvReq.setOnItemLongClickListener((parent, view, position, id) -> {
            int requestId = requestIds.get(position);
            new AlertDialog.Builder(this).setTitle("Delete Request").setMessage("Complete this task?")
                    .setPositiveButton("Yes", (d, w) -> {
                        if(db.deleteRequest(requestId)) { loadData(); }
                    }).setNegativeButton("No", null).show();
            return true;
        });

        // Long Press to Delete User
        lvUsers.setOnItemLongClickListener((parent, view, position, id) -> {
            String userStr = (String) lvUsers.getItemAtPosition(position);
            String username = userStr.split("\n")[0].replace("User: ", "");
            new AlertDialog.Builder(this).setTitle("Delete User").setMessage("Remove " + username + "?")
                    .setPositiveButton("Delete", (d, w) -> {
                        if(db.deleteUser(username)) { loadData(); }
                    }).setNegativeButton("Cancel", null).show();
            return true;
        });
    }

    private void loadData() {
        // Load Requests
        ArrayList<String> reqs = new ArrayList<>();
        requestIds.clear();
        Cursor c1 = db.getAllRequests();
        while (c1.moveToNext()) {
            requestIds.add(c1.getInt(0));
            reqs.add("Service: " + c1.getString(1) + "\nWard: " + c1.getString(2) + " Bed: " + c1.getString(3));
        }
        lvReq.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, reqs));

        // Load Users
        ArrayList<String> usrs = new ArrayList<>();
        Cursor c2 = db.getAllUsers();
        while (c2.moveToNext()) {
            usrs.add("User: " + c2.getString(0) + "\nEmail: " + c2.getString(1));
        }
        lvUsers.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usrs));
    }
}