package com.xiao.musicplayer.ui.my_information;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.xiao.musicplayer.AlterActivity;
import com.xiao.musicplayer.MusicActivity;
import com.xiao.musicplayer.R;
import com.xiao.musicplayer.model.Music;
import com.xiao.musicplayer.util.ConstUtil;
import com.xiao.musicplayer.util.LoadImagesTask;


public class MyImformationFragment extends Fragment {

    //private MyInformationViewModel myInformationViewModel;
    private TextView user_name_my_info;
    private TextView user_id_my_info;
    private String imagePath = " ";
    private Uri imageUri;
    private ImageView user_image_my_info;
    private Bitmap imageBitmap;
    private View root;


    /******************************************
     * warning: user cameIn
     * */

    /****
     * Warning: URL WRONG excute
     */
    private void imageUrlWrong(){

        Log.e("user Not go", imagePath);
    }


    private void updateShowMessage(){
//        user.updateInfo(Ober_Main_ac.activity);
        Log.e("a", ConstUtil.user.getUsername());
        this.user_name_my_info.setText(ConstUtil.user.getUsername());
        this.user_id_my_info.setText(ConstUtil.user.getPhone());

        this.imagePath = ConstUtil.user.getImage();
        //获取头像
        if(ConstUtil.user.getImage()!=null){
            ImageView imageView = root.findViewById(R.id.user_image_my_info);
            new LoadImagesTask(imageView).execute(ConstUtil.user.getImage());
        }

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        //myInformationViewModel = ViewModelProviders.of(this).get(MyInformationViewModel.class);
        root = inflater.inflate(R.layout.fragment_my_information, container, false);

        /***************************
         * warning: User cameIn
         * */

        user_name_my_info = root.findViewById(R.id.user_name_my_info);
        user_id_my_info = root.findViewById(R.id.user_id_my_info);
        user_image_my_info = root.findViewById(R.id.user_image_my_info);
        this.updateShowMessage();

        root.findViewById(R.id.set_info).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(getActivity(), AlterActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        root.findViewById(R.id.all_upload_music).setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent=new Intent(getActivity(), MusicActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("musiclist",ConstUtil.musicLists.get(0));
                intent.putExtras(bundle);
                startActivityForResult(intent, 1);
            }
        });

        return root;
    }

    class MyThread extends Thread{
        @Override
        public void run() {
            try {
                sleep(1500);
                updateShowMessage();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

//        new MyThread().run();
        updateShowMessage();

        super.onActivityResult(requestCode, resultCode, data);

    }
    private Bitmap bimapRound(Bitmap mBitmap,float index){
        Bitmap bitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_4444);

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);

        //设置矩形大小
        Rect rect = new Rect(0,0,mBitmap.getWidth(),mBitmap.getHeight());
        RectF rectf = new RectF(rect);

        // 相当于清屏
        canvas.drawARGB(0, 0, 0, 0);
        //画圆角
        canvas.drawRoundRect(rectf, index, index, paint);
        // 取两层绘制，显示上层
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        // 把原生的图片放到这个画布上，使之带有画布的效果
        canvas.drawBitmap(mBitmap, rect, rect, paint);
        return bitmap;

    }


}