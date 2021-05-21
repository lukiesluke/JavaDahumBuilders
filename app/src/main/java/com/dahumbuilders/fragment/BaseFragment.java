package com.dahumbuilders.fragment;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

public class BaseFragment extends Fragment {
    Gson gson = new Gson();
    Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
        Log.d("lwg", "onAttach BaseFragment");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d("lwg", "onDetach BaseFragment");
    }
}
