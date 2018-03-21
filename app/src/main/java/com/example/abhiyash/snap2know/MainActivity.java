package com.example.abhiyash.snap2know;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;
import com.google.api.services.vision.v1.model.TextAnnotation;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button b1,b2,b3;
    ImageView iv;
    Vision vis;
    Bitmap image;
    private static int RESULT_LOAD_IMG = 1;
    String imgDecodableString;
    String readtext;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
    private Uri fileUri; // file url to store image/video
    byte[] imagebytes;
    Image inputimage=new Image();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1=(Button)findViewById(R.id.button);
        b2=(Button)findViewById(R.id.button2);
        b3=(Button)findViewById(R.id.button3);
        iv=(ImageView)findViewById(R.id.imageView);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        if(!isDeviceSupportCamera())
        {
            Toast.makeText(this, "Your Device does not have a camera", Toast.LENGTH_SHORT).show();
            finish();
        }
        Vision.Builder vb=new Vision.Builder(new NetHttpTransport(),new AndroidJsonFactory(),null);
        vb.setVisionRequestInitializer(new VisionRequestInitializer("AIzaSyDLtOTQ4uuyR_q1pocqxwRe-0PAby3B3NM"));
        vis=vb.build();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button:
                captureImage();
                break;
            case R.id.button2:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                // Start the Intent
                System.out.println("2");
                startActivityForResult(galleryIntent,RESULT_LOAD_IMG);
                System.out.println("3");
                break;
            case R.id.button3:
                try{
                    Feature des=new Feature();
                    des.setType("TEXT_DETECTION");
                    AnnotateImageRequest request=new AnnotateImageRequest();
                    request.setImage(inputimage);
                    request.setFeatures(Arrays.asList(des));
                    BatchAnnotateImagesRequest batchRequest=new BatchAnnotateImagesRequest();
                    batchRequest.setRequests(Arrays.asList(request));
                    BatchAnnotateImagesResponse batchResponse=vis.images().annotate(batchRequest).execute();
                    TextAnnotation text= batchResponse.getResponses().get(0).getFullTextAnnotation();
                    //Toast.makeText(this, "Vision Api Succesful"+text.getText(), Toast.LENGTH_LONG).show();
                    readtext=text.getText();
                    Log.d("Success",text.getText());
                }
                catch(Exception e)
                {
                    Toast.makeText(this, "Error "+e, Toast.LENGTH_SHORT).show();
                }
                Intent it=new Intent(this,Main2Activity.class);
                it.putExtra("text",readtext);
                startActivity(it);
                break;
        }
    }
    private boolean isDeviceSupportCamera()
    {
        if(getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)){
            return true;
        }
        else
        {
            return false;
        }
    }
    private void captureImage()
    {
        Intent it=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri=getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        it.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);

        startActivityForResult(it,CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
    }
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("file_uri",fileUri);

    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CAMERA_CAPTURE_IMAGE_REQUEST_CODE){
            if(resultCode==RESULT_OK)
            {
                previewImage();
            }
            else if(resultCode==RESULT_CANCELED)
            {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Failed to cancel image", Toast.LENGTH_SHORT).show();
            }
        }
        try {
            System.out.println("5");
            // When an Image is picked

            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK && null != data) {
                System.out.println("6");
                // Get the Image from data
                Uri selectedImage = data.getData();
                System.out.println("7");
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                System.out.println("8");
                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                System.out.println("9");
                // Move to first row

                cursor.moveToFirst();
                System.out.println("10");
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                System.out.println("11");
                imgDecodableString = cursor.getString(columnIndex);
                System.out.println("12");
                cursor.close();
                System.out.println("13");

                iv.setVisibility(View.VISIBLE);
                System.out.println("14");
                // Set the Image in ImageView after decoding the String
                final Bitmap bitmap=BitmapFactory.decodeFile(imgDecodableString);
                iv.setImageBitmap(bitmap);
                Image base64EncodedImage=new Image();
                ByteArrayOutputStream bos=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,90,bos );
                imagebytes=bos.toByteArray();
                base64EncodedImage.encodeContent(imagebytes);
                inputimage.encodeContent(imagebytes);

                System.out.println("15");

            } else {

                Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong" + e, Toast.LENGTH_LONG).show();
        }
        //super.onActivityResult(requestCode, resultCode, data);
    }
    public Bitmap convertImageViewToBitmap(ImageView v) {

        image = ((BitmapDrawable) v.getDrawable()).getBitmap();
        return image;
    }
    private void previewImage(){
        try{
            iv.setVisibility(View.VISIBLE);
            BitmapFactory.Options options=new BitmapFactory.Options();
            options.inSampleSize=8;
            final Bitmap bitmap=BitmapFactory.decodeFile(fileUri.getPath(),options);
            iv.setImageBitmap(bitmap);
            Image base64EncodedImage=new Image();
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,90,bos );
            imagebytes=bos.toByteArray();
            base64EncodedImage.encodeContent(imagebytes);
            inputimage.encodeContent(imagebytes);

            //callCloudVision(bitmap, feature);
        }
        catch (Exception e){
            Toast.makeText(this, "ERROR"+e, Toast.LENGTH_LONG).show();
        }
    }
    public Uri getOutputMediaFileUri(int type)

    {
        return Uri.fromFile(getOutputMediaFile(type));
    }
    private static File getOutputMediaFile(int type)
    {
        File mediaStorageDir=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),IMAGE_DIRECTORY_NAME);
        if(!mediaStorageDir.exists())
        {
            if(!mediaStorageDir.mkdirs()){
                Log.d("Error","Failed to create a new directory"+IMAGE_DIRECTORY_NAME);
                return null;
            }
        }

        String timestamp=new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File mediaFile;
        if(type==MEDIA_TYPE_IMAGE)
        {
            mediaFile=new File(mediaStorageDir.getPath()+File.separator+"IMG_"+timestamp+".jpg");

        }
        else{
            return null;
        }
        return mediaFile;
    }
}
