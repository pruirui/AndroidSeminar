package com.xiao.musicplayer.util;

import com.xiao.musicplayer.model.MusicList;
import com.xiao.musicplayer.model.User;

import java.util.ArrayList;
import java.util.List;

public class ConstUtil {
    public static String TAG = "Road_APP";
    public static final String LocalReferenceName = "user";

    /**
     * URLs
     */
    public static final String SERVER_URL = "http://47.94.160.152:8000";
    public static final String LOGINURL = SERVER_URL+"/user/login/";
    public static final String REGISTERURL = SERVER_URL+"/user/addUser/";
    public static final String MUSICLISTURL = SERVER_URL+"/list/findAllByPhone/";

    public static final String GET_MUSIC_BY_LIST = SERVER_URL+"/music/findSongByList";

    public static final String MUSIC_UPLOAD_URL = SERVER_URL + "";
    public static final String DELETE_MUSIC = SERVER_URL+"";
    public static final String ADD_MUSIC_TO_LIST = SERVER_URL+"";

    public static final String ALTER_IMAGE_URL = SERVER_URL+"/user/changeImage/";
    public static final String ADD_MUSIC_URL = SERVER_URL+"/music/addMusic/";
    public static final String SEARCH_NAME_URL = SERVER_URL+"/music/searchByName/";
    public static final String SEARCH_SINGER_URL = SERVER_URL+"/music/searchBySinger/";

    public static final String ALTERINFOURL = SERVER_URL+"/user/alterUser/";
    public static final String ALTER_IMAGEURL = SERVER_URL+"/user/changeImage/";


    public static User user;
    public static List<MusicList> musicLists = new ArrayList<>();
}
