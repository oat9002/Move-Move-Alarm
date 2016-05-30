package com.fatel.mamtv1;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;

import java.util.ArrayList;
import java.util.Arrays;

public class ShareActivity extends AppCompatActivity {


    private ImageView imgPreview;
        private Button postPicBtn;
        Uri Imguri = null;
        private  static final String PERMISSION = "publish_actions";


        //ชนิดของการโพสต์ที่รอดำเนินการ
        private enum PendingAction{
            NONE,
            //POST_LINK,
            POST_PICTURE
        }

        //การโพสต์ที่รอดำเนินการซึ่งจะดำเนินการหลังจากได้รับสิทธิ์ publish_actions แล้ว
        private PendingAction pendingAction = PendingAction.NONE;

        //method เริ่มต้นกระบวนการโพสต์

        private void performPublish(PendingAction action){
            AccessToken accessToken = AccessToken.getCurrentAccessToken();

            if(accessToken != null){
                pendingAction = action;
                handlePendingAction();
            }
        }



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_share);
            //Second Activity get a Uri path
            Bundle b = getIntent().getExtras();
            if (b != null) {
                //String uri_Str= b.getString("uri_Str");
                //Imguri = Uri.parse(uri_Str);
                Imguri = getIntent().getParcelableExtra("uri");
            }

            imgPreview = (ImageView) findViewById(R.id.imgNxtPreview);
            previewCapturedImage(Imguri);

            //add for activity_share facebook
            postPicBtn = (Button) findViewById(R.id.btn_share);  //btn_postPic    btn_share

            postPicBtn.setOnClickListener(new View.OnClickListener() {
                //@override
                public void onClick(View v) {
                    //showPickPictureDialog();
                    performPublish(PendingAction.POST_PICTURE);
                    Intent intent = new Intent(ShareActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            });


        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_share, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle action bar item clicks here. The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            int id = item.getItemId();

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_settings) {
                return true;
            }

            return super.onOptionsItemSelected(item);
        }


        /**
         * Display image from a path to ImageView
         */
        private void previewCapturedImage(Uri img) {
            try {
                // hide video preview
                //videoPreview.setVisibility(View.GONE);

                imgPreview.setVisibility(View.VISIBLE);

                // bimatp factory
                BitmapFactory.Options options = new BitmapFactory.Options();

                // downsizing image as it throws OutOfMemory Exception for larger
                // images
                options.inSampleSize = 2;

                final Bitmap bitmap = BitmapFactory.decodeFile(img.getPath(),
                        options);

                imgPreview.setImageBitmap(bitmap);
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }



        //add for activity_share facebook(picture)
        private void postPicture(){
            Profile profile = Profile.getCurrentProfile();
            Bitmap picture = BitmapFactory.decodeFile(Imguri.getPath());
            //Drawable drawable = getResources().getDrawable(getResources().getIdentifier("cameraicon", "drawable", getPackageName()));
            //Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier("cameraicon", "drawable", getPackageName()));
            String res = findImage();
            Bitmap bitmap2 =  BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(res, "drawable", getPackageName()));
            Bitmap test = combineImages(picture, bitmap2);
            SharePhoto pictureToShare = new SharePhoto.Builder()
                    .setBitmap(test)
                    .setCaption("Test Application Move Alarm from Computer Engineer Ladkrabang")
                    .build();

            ArrayList<SharePhoto> pictureList = new ArrayList<>();
            pictureList.add(pictureToShare);

            SharePhotoContent content = new SharePhotoContent.Builder()
                    .setPhotos(pictureList)
                    .build();

            if(profile != null && hasPublishPermission()){
                ShareApi.share(content, shareCallback);
                
            }else{
                pendingAction = pendingAction.POST_PICTURE;
                LoginManager.getInstance().logInWithPublishPermissions(
                        this, Arrays.asList(PERMISSION));
            }
        }


        //add for activity_share facebook
        private boolean hasPublishPermission(){
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            return accessToken != null &&
                    accessToken.getPermissions().contains("publish_actions");
        }



        //add for activity_share facebook
        private  void handlePendingAction(){
            PendingAction oldPendingAction = pendingAction;
            pendingAction = PendingAction.NONE;

            switch (oldPendingAction){
                case NONE:
                    break;
                case POST_PICTURE:
                    postPicture();
                    break;
            }
        }



        //add for activity_share facebook
        private FacebookCallback<Sharer.Result> shareCallback =
                new FacebookCallback<Sharer.Result>(){
                    @Override
                    public void onCancel(){

                    }

                    @Override
                    public void onError(FacebookException error){
                        String title = "เกิดข้อผิดพลาดในการโพสต์";
                        String msg = error.getMessage();
                        //showResult(title,msg);
                    }

                    @Override
                    public void onSuccess(Sharer.Result result){
                        if(result.getPostId() != null){
                            String title = "โพสต์ลง Facebook สำเร็จ";
                            String id = result.getPostId();
                            String msg = String.format("Post ID: %s",id);
                           // showResult(title,msg);
                        }
                    }

//                    private  void showResult(String title,String alertMessage){
//                        new AlertDialog.Builder(get)
//                                .setTitle(title)
//                                .setMessage(alertMessage)
//                                .setPositiveButton("OK",null)
//                                .show();
//                    }
                };
    public void linkHome(View view){
        Intent i1 = new Intent(ShareActivity.this, MainActivity.class);
        i1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i1);
        Intent i = new Intent(getBaseContext(), AlarmReceiver.class);
        Bundle b = new Bundle();
        b.putString("key", "first");
        i.putExtras(b);
        sendBroadcast(i);
    }
    public Bitmap combineImages(Bitmap c, Bitmap s) { // can add a 3rd parameter 'String loc' if you want to save the new image - left some code to do that at the bottom

        int width, height = 0;

        if(c.getWidth() > s.getWidth()) {
            width = c.getWidth();
            height = c.getHeight() + s.getHeight();
        } else {
            width = s.getWidth();
            height = c.getHeight() + s.getHeight();
        }

        Bitmap cs = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);

        Canvas comboImage = new Canvas(cs);

        comboImage.drawBitmap(c, 0f, 0f, null);
        comboImage.drawBitmap(s, 0f, c.getHeight(), null);

        // this is an extra bit I added, just incase you want to save the new image somewhere and then return the location
    /*String tmpImg = String.valueOf(System.currentTimeMillis()) + ".png";

    OutputStream os = null;
    try {
      os = new FileOutputStream(loc + tmpImg);
      cs.compress(CompressFormat.PNG, 100, os);
    } catch(IOException e) {
      Log.e("combineImages", "problem combining images", e);
    }*/

        return cs;
    }
    public String findImage(){
        int id = Activity.getCurrentActivityHandle().getRandomPosture().get(0).getIdPosture();
        int mode[] = Activity.getCurrentActivityHandle().getModeSelect();
        if(mode[0]==1){
            if(id==0) return "p1_1_1";
            else if(id==1) return "p1_2_1";
            else if(id==2) return "p1_3_1";
        }
        else if(mode[0]==2){
            if(id==3) return "p2_1_1";
            else if(id==4) return "p2_2_1";
        }
        else if(mode[0]==3){
            if(id==5) return "p3_1_1";
            else if(id==6) return "p3_2_1";
            else if(id==7) return "p3_3_1";
            else if(id==8) return "p3_4_1";
            else if(id==9) return "p3_5_1";
        }
        else if(mode[0]==4){
            if(id==10) return "p4_1_1";
            else if(id==11) return "p4_2_1";
            else if(id==12) return "p4_3_1";
        }
        else if(mode[0]==5){
            if(id==13) return "p5_1_1";
            else if(id==14) return "p5_2_1";
        }
        else if(mode[0]==6){
            if(id==15) return "p6_1_1";
            else if(id==16) return "p6_2_1";
            else if(id==17)return "p6_3_1";
            else if(id==18) return "p6_4_1";
            else if(id==19) return "p6_5_1";
            else if(id==20) return "p6_6_1";
            else if(id==21) return "p6_7_1";
            else if(id==22) return "p6_8_1";
        }
        return "p1_1_1";
    }


}
