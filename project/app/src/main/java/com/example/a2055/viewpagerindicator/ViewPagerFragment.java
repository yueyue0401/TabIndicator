package com.example.a2055.viewpagerindicator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewPagerFragment extends Fragment {
    public static String STRING_OBJ = "stringObject";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.viewpager_item, container, false);
        Bundle args = getArguments();
        String text = args.getString(STRING_OBJ);
        TextView textView = (TextView) view.findViewById(R.id.viewPagerText);
        textView.setText(text);

        return view;
    }
}
