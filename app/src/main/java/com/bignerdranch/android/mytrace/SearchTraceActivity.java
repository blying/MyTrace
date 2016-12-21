package com.bignerdranch.android.mytrace;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by bly on 2016/12/15.
 */

public class SearchTraceActivity extends Activity{
    private Button searchButton;
    private EditText searchText;
    private Spinner mSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_trace);

    }
}
