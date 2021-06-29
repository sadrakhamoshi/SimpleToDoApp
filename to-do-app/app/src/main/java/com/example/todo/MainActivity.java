package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.todo.models.Item;

public class MainActivity extends AppCompatActivity {

    private Item mItem;
    private EditText mTitle_editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize
        mItem = new Item();
        mTitle_editText = findViewById(R.id.editText_item);
        ArrayAdapter mItemAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mItem.getItemsList());
        ListView mItems_listView = findViewById(R.id.listView_item);
        mItems_listView.setAdapter(mItemAdapter);
    }

    public void AddTask(View view) {
        if (mTitle_editText.getText().length() > 0) {
            mItem.addItem(mTitle_editText.getText().toString());
            mTitle_editText.setText("");
        }
    }
}