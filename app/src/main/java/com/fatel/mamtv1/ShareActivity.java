package com.fatel.mamtv1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.fatel.mamtv1.Service.AlarmReceiver;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class ShareActivity extends AppCompatActivity {


    private ImageView imgPreview;
    private Button postPicBtn;
    private Bitmap mainpic = null;
    private Bitmap pictureuser = null;
    private Bitmap pictureexercise = null;
    Uri Imguri = null;
    private static final String PERMISSION = "publish_actions";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Share Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.fatel.mamtv1/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Share Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.fatel.mamtv1/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    //ชนิดของการโพสต์ที่รอดำเนินการ
    private enum PendingAction {
        NONE,
        //POST_LINK,
        POST_PICTURE
    }

    //การโพสต์ที่รอดำเนินการซึ่งจะดำเนินการหลังจากได้รับสิทธิ์ publish_actions แล้ว
    private PendingAction pendingAction = PendingAction.NONE;

    //method เริ่มต้นกระบวนการโพสต์

    private void performPublish(PendingAction action) throws ExecutionException, InterruptedException {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        if (accessToken != null) {
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
        Toast.makeText(getApplicationContext(), "กำลังเตรียมรูปภาพสำหรับการแชร์", Toast.LENGTH_SHORT).show();
        imgPreview = (ImageView) findViewById(R.id.imgNxtPreview);
        previewCapturedImage(Imguri);
        //add for activity_share facebook
        postPicBtn = (Button) findViewById(R.id.btn_share);  //btn_postPic    btn_share
        postPicBtn.setVisibility(View.GONE);
        postPicBtn.setOnClickListener(new View.OnClickListener() {
            //@override
            public void onClick(View v) {
                //showPickPictureDialog();
                try {
                    performPublish(PendingAction.POST_PICTURE);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(ShareActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        //Picture from Glide
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Looper.prepare();
                try {
                    mainpic = Glide.
                            with(getApplicationContext()).
                            load(getResources().getIdentifier("share", "drawable", getPackageName())).
                            asBitmap().
                            into(-1,-1).
                            get();
                    String res = findImage();
                    //220,289
                    pictureexercise = Glide.
                            with(getApplicationContext()).
                            load(getResources().getIdentifier(res, "drawable", getPackageName())).
                            asBitmap().
                            into(-1,-1).
                            get();
                    //557,738
                    pictureuser = Glide.
                            with(getApplicationContext()).
                            load(Imguri.getPath()).
                            asBitmap().
                            into(-1,-1).
                            get();

                } catch (final ExecutionException e) {
                    Log.i("error", "bitmap");
                } catch (final InterruptedException e) {
                    Log.i("error", "bitmap");
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void dummy) {
                if (null != mainpic && null != pictureexercise && null!=pictureuser) {
                    // The full bitmap should be available here
                    pictureuser = Bitmap.createScaledBitmap(pictureuser,557,738,true);
                    pictureexercise = Bitmap.createScaledBitmap(pictureexercise,220,289,true);
                    mainpic = combineImages(mainpic, pictureuser, pictureexercise);
                    postPicBtn.setVisibility(View.VISIBLE);
                    Toast.makeText(getApplicationContext(), "พร้อมส่งรูปภาพ", Toast.LENGTH_SHORT).show();
                };
            }
        }.execute();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
    private void postPicture() throws ExecutionException, InterruptedException {
        Profile profile = Profile.getCurrentProfile();
        SharePhoto pictureToShare = new SharePhoto.Builder()
                .setBitmap(mainpic)
                .setCaption("มาออกกำลังกายกับแอปพลิเคชัน Move Alarm กันเถอะ")
                .build();

        ArrayList<SharePhoto> pictureList = new ArrayList<>();
        pictureList.add(pictureToShare);

        SharePhotoContent content = new SharePhotoContent.Builder()
                .setPhotos(pictureList)
                .build();

        if (profile != null && hasPublishPermission()) {
            ShareApi.share(content, shareCallback);

        } else {
            pendingAction = pendingAction.POST_PICTURE;
            LoginManager.getInstance().logInWithPublishPermissions(
                    this, Arrays.asList(PERMISSION));
        }
    }


    //add for activity_share facebook
    private boolean hasPublishPermission() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null &&
                accessToken.getPermissions().contains("publish_actions");
    }


    //add for activity_share facebook
    private void handlePendingAction() throws ExecutionException, InterruptedException {
        PendingAction oldPendingAction = pendingAction;
        pendingAction = PendingAction.NONE;

        switch (oldPendingAction) {
            case NONE:
                break;
            case POST_PICTURE:
                postPicture();
                break;
        }
    }


    //add for activity_share facebook
    private FacebookCallback<Sharer.Result> shareCallback =
            new FacebookCallback<Sharer.Result>() {
                @Override
                public void onCancel() {

                }

                @Override
                public void onError(FacebookException error) {
                    String title = "เกิดข้อผิดพลาดในการโพสต์";
                    String msg = error.getMessage();
                    makeToast(title);
                    //showResult(title,msg);
                }

                @Override
                public void onSuccess(Sharer.Result result) {
                    if (result.getPostId() != null) {
                        String title = "โพสต์ลง Facebook สำเร็จ";
                        String id = result.getPostId();
                        String msg = String.format("Post ID: %s", id);
                        makeToast(title);
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

    public void linkHome(View view) {
        Intent i1 = new Intent(ShareActivity.this, MainActivity.class);
        i1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i1);
        Intent i = new Intent(getBaseContext(), AlarmReceiver.class);
        Bundle b = new Bundle();
        b.putString("key", "first");
        i.putExtras(b);
        sendBroadcast(i);
    }

    public Bitmap combineImages(Bitmap main, Bitmap picuser, Bitmap picexercise) {

        int width, height = 0;
        width = main.getWidth();
        height = main.getHeight();
        Bitmap cs = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas comboImage = new Canvas(cs);
        comboImage.drawBitmap(main, 0f, 0f, null);
        comboImage.drawBitmap(picuser, new Rect(0, 0, 556, 737), new Rect(9, 12, 565, 750), null);
        comboImage.drawBitmap(picexercise, new Rect(0, 0, 219, 288), new Rect(571, 457, 790, 745), null);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(12);
        String nameex = nameofExercise();
        int start = (594+110)-((nameex.length()/2)*7);
        comboImage.drawText(nameex,start,451,paint);
        return cs;
    }

    public String findImage() {
        int id = Activity.getCurrentActivityHandle().getRandomPosture().get(0).getIdPosture();
        int mode[] = Activity.getCurrentActivityHandle().getModeSelect();
        if (mode[0] == 1) {
            if (id == 0) return "p1_1_1";
            else if (id == 1) return "p1_2_1";
            else if (id == 2) return "p1_3_1";
        } else if (mode[0] == 2) {
            if (id == 3) return "p2_1_1";
            else if (id == 4) return "p2_2_1";
        } else if (mode[0] == 3) {
            if (id == 5) return "p3_1_1";
            else if (id == 6) return "p3_2_1";
            else if (id == 7) return "p3_3_1";
            else if (id == 8) return "p3_4_1";
            else if (id == 9) return "p3_5_1";
        } else if (mode[0] == 4) {
            if (id == 10) return "p4_1_1";
            else if (id == 11) return "p4_2_1";
            else if (id == 12) return "p4_3_1";
        } else if (mode[0] == 5) {
            if (id == 13) return "p5_1_1";
            else if (id == 14) return "p5_2_1";
        } else if (mode[0] == 6) {
            if (id == 15) return "p6_1_1";
            else if (id == 16) return "p6_2_1";
            else if (id == 17) return "p6_3_1";
            else if (id == 18) return "p6_4_1";
            else if (id == 19) return "p6_5_1";
            else if (id == 20) return "p6_6_1";
            else if (id == 21) return "p6_7_1";
            else if (id == 22) return "p6_8_1";
        }
        return "p1_1_1";
    }
    public String nameofExercise(){
        String name = "ท่าออกกำลังกายส่วน";
        int mode[] = Activity.getCurrentActivityHandle().getModeSelect();
        if (mode[0] == 1)
            name += "คอ";
        else if (mode[0] == 2)
            name += "ไหล่";
        else if (mode[0] == 3)
            name += "อกและหลัง";
        else if (mode[0] == 4)
            name += "ข้อมือ";
        else if (mode[0] == 5)
            name += "เอว";
        else if (mode[0] == 6)
            name += "สะโพก ขา และน่อง";
        return name;
    }
    public void makeToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }

}
