package com.example.todolist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button addButton;
    Button cancelButton;
    ListView items;
    EditText contentText;
    ArrayList<String> itemsList = new ArrayList<>();
    ArrayAdapter<String> adapterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addButton = findViewById(R.id.addButton);
        contentText = findViewById(R.id.content);
        items = findViewById(R.id.list);
        cancelButton = findViewById(R.id.cancelButton);
        itemsList = FileHelper.readData(this);
        adapterList = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, itemsList);
        items.setAdapter(adapterList);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = contentText.getText().toString();
                if (text.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Enter the Todo Item", Toast.LENGTH_SHORT).show();
                } else {
                    itemsList.add(text);
                    contentText.setText("");
                    FileHelper.writeData(itemsList, getApplicationContext());
                    adapterList.notifyDataSetChanged();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contentText.setText("");
            }
        });
        items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Delete");
                alert.setMessage("Do you want to delete?");
                alert.setCancelable(false);
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }
                );
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        itemsList.remove(position);
                        adapterList.notifyDataSetChanged();
                        FileHelper.writeData(itemsList, getApplicationContext());
                    }
                });
                AlertDialog alertDialog = alert.create();
                alertDialog.show();
            }
        });


    }
}