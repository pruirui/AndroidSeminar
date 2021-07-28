package com.xiao.musicplayer.ui.functions;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.xiao.musicplayer.R;
import com.xiao.musicplayer.SearchActivity;
import com.xiao.musicplayer.UploadActivity;

public class FunctionsFragment extends Fragment {
    int count = 0;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        count++;
        Log.e("a",String.valueOf(count));
        View root = inflater.inflate(R.layout.fragment_functions, container, false);

        root.findViewById(R.id.updata_lo_functions).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(getActivity(), UploadActivity.class);
                startActivity(intent);
            }
        });

        root.findViewById(R.id.upload_music_functions).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(getActivity(), UploadActivity.class);
                startActivity(intent);
            }
        });

        root.findViewById(R.id.search_img_search).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(getActivity(), SearchActivity.class);
                EditText editText = root.findViewById(R.id.search_text_search);
                intent.putExtra("key",editText.getText().toString());
                intent.putExtra("type","name");
                startActivity(intent);
            }
        });
        return root;
    }



}