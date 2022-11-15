package com.example.tarea5;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;
public class MainActivity extends AppCompatActivity {
    private VideoView video;
    private static final int REQUESTCODECAMARA=100;
    private static final int REQUESTTAKEVIDEO=101;
    private int control_video=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        video=(VideoView) findViewById(R.id.video_view);
    }
    public void grabar_video(View view){
        permisos();
    }
    public void permisos(){
        if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},REQUESTCODECAMARA);
        }
        else{
            dispatchTakePictureIntent();
        }
    }
    public void dispatchTakePictureIntent(){
        Intent takePictureIntent=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if(takePictureIntent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(takePictureIntent,REQUESTTAKEVIDEO);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==REQUESTCODECAMARA){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                dispatchTakePictureIntent();
            }
            else{
                Toast.makeText(getApplicationContext(),"Permiso Denegado",Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUESTTAKEVIDEO && resultCode==RESULT_OK){
            Toast.makeText(this,"Guardado en disco almacenamiento",Toast.LENGTH_LONG).show();
            Uri video_grabado = data.getData();
            if(video_grabado!=null) {
                video.setVideoURI(video_grabado);
                video.start();
            }
        }
    }
    public void reproducir(View view){
        if(control_video==0) {
            video.start();
            control_video=1;
        }
        else {
            video.pause();
            control_video=0;
        }
    }
}