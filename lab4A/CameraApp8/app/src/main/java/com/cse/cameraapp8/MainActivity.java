package com.example.cameraapp3;

import java.io.File;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // label our logs "CameraApp3"
    private static String logtag = "CameraApp3";
    // tells us which camera to take a picture from
    private static int TAKE_PICTURE = 1;
    // empty variable to hold our image Uri once we store it
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // look for the button we set in the view
        Button cameraButton = (Button)findViewById(R.id.button_camera);
        // set a listener on the button
        cameraButton.setOnClickListener(cameraListener);
    }

    // set a new listener
    private OnClickListener cameraListener = new OnClickListener() {
        public void onClick(View v) {
            // open the camera and pass in the current view
            takePhoto(v);
        }
    };

    public void takePhoto(View v) {
        // tell the phone we want to use the camera
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // create a new temp file called pic.jpg in the "pictures" storage area of the phone
        File photo = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "pic.jpg");
        // take the return data and store it in the temp file "pic.jpg"
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
        // stor the temp photo uri so we can find it later
        imageUri = Uri.fromFile(photo);
        // start the camera
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // override the original activity result function
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // call the parent
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            // if the requestCode was equal to our camera code (1) then...
            case 1:
                // if the user took a photo and selected the photo to use
                if(resultCode == Activity.RESULT_OK) {
                    // get the image uri from earlier
                    Uri selectedImage = imageUri;
                    // notify any apps of any changes we make
                    getContentResolver().notifyChange(selectedImage, null);
                    // get the imageView we set in our view earlier
                    ImageView imageView = (ImageView)findViewById(R.id.image_view_camera);
                    // create a content resolver object which will allow us to access the image file at the uri above
                    ContentResolver cr = getContentResolver();
                    // create an empty bitmap object
                    Bitmap bitmap;
                    try {
                        // get the bitmap from the image uri using the content resolver api to get the image
                        bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, selectedImage);
                        // set the bitmap to the image view
                        imageView.setImageBitmap(bitmap);
                        // notify the user
                        Toast.makeText(MainActivity.this, selectedImage.toString(), Toast.LENGTH_LONG).show();
                    } catch(Exception e) {
                        // notify the user
                        Toast.makeText(MainActivity.this, "failed to load", Toast.LENGTH_LONG).show();
                        Log.e(logtag, e.toString());
                    }
                }
        }
    }

}