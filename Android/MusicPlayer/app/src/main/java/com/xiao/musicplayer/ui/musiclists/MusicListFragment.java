package com.xiao.musicplayer.ui.musiclists;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.xiao.musicplayer.MainActivity;
import com.xiao.musicplayer.MusicActivity;
import com.xiao.musicplayer.R;
import com.xiao.musicplayer.SearchActivity;
import com.xiao.musicplayer.model.adapter.MusicAdapter;
import com.xiao.musicplayer.model.adapter.MusicListAdapter;
import com.xiao.musicplayer.util.ConstUtil;

public class MusicListFragment extends Fragment {



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_music_list, container, false);
        ListView music_lists = root.findViewById(R.id.music_list_view_fragment_music_list);
        MusicListAdapter adapter = new MusicListAdapter(getContext(),R.layout.music_list_item, ConstUtil.musicLists);

        music_lists.setAdapter(adapter);

        music_lists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), MusicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("musiclist",ConstUtil.musicLists.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        root.findViewById(R.id.search_img_search).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(getActivity(), SearchActivity.class);
                EditText editText = root.findViewById(R.id.search_text_search);
                intent.putExtra("key",editText.getText().toString());
                intent.putExtra("type","singer");
                startActivity(intent);
            }
        });

        return root;
    }
}