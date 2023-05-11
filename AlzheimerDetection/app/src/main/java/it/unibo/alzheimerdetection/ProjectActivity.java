package it.unibo.alzheimerdetection;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import it.unibo.alzheimerdetection.ml.ModelInception;
import it.unibo.alzheimerdetection.ml.ResnetNoquantv2;

public class ProjectActivity extends AppCompatActivity {

    Button selectBtn, captureBtn, restartBtn, help;
    TextView result, confidence;
    ImageView imageView;

    ToggleButton toggleButton;

    int imageSize = 299;

    int c =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        setTheme(R.style.Theme_AlzheimerDetection);

        getPermission();

        selectBtn = findViewById(R.id.selectBtn);
        captureBtn = findViewById(R.id.captureBtn);
        result = findViewById(R.id.result);
        confidence = findViewById(R.id.confidence);
        imageView = findViewById(R.id.imageView);
        restartBtn = findViewById(R.id.restartBtn);
        toggleButton = findViewById(R.id.toggleButton);
        help = findViewById(R.id.help);


        selectBtn.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 10);
        });

        captureBtn.setOnClickListener(view -> {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 3);
            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
            }
        });

        help.setOnClickListener(v -> {
            Intent i = new Intent((getApplicationContext()), PopActivity.class);
            startActivity(i);
        });

       /* predictBtn.setOnClickListener(view -> {

            switch (c) {
                case 0:
                    try {
                        ResnetNoquantv2 model = ResnetNoquantv2.newInstance(ProjectActivity.this);

                        // Creates inputs for reference.

                        TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 299, 299, 3}, DataType.FLOAT32);
                        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
                        byteBuffer.order(ByteOrder.nativeOrder());

                        int[] intValues = new int[imageSize * imageSize];
                        image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
                        int pixel = 0;

                        for (int i = 0; i < imageSize; i++) {
                            for (int j = 0; j < imageSize; j++) {
                                int val = intValues[pixel++];
                                byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255));
                                byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255));
                                byteBuffer.putFloat((val & 0xFF) * (1.f / 255));
                            }
                        }

                        // Runs model inference and gets result.
                        ResnetNoquantv2.Outputs outputs = model.process(inputFeature0);

                        TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                        float[] confidences;
                        confidences = outputFeature0.getFloatArray();
                        int maxPos;
                        if (confidences[0] > 0.5) {
                            maxPos = 0;
                        } else {
                            maxPos = 1;
                        }

                        String[] classes = {"PT", "HC"};

                        result.setText(classes[maxPos]);

                        String s = "";
                        String p = "";
                        s += String.format("%s: %.1f%%\n", classes[0], confidences[0] * 100);
                        p += String.format("%s: %.1f%%\n", classes[1], (1 - confidences[0]) * 100);
                        if (maxPos == 0) {
                            confidence.setText(s + p);
                        } else {
                            confidence.setText(p + s);
                        }
                        // Releases model resources if no longer used.
                        model.close();
                    } catch (IOException e) {
                        // TODO Handle the exception
                    }
                    break;
                case 1:
                    try {
                        ResnetNoquantv2 model = ResnetNoquantv2.newInstance(ProjectActivity.this);

                        // Creates inputs for reference.

                        TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 299, 299, 3}, DataType.FLOAT32);
                        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
                        byteBuffer.order(ByteOrder.nativeOrder());

                        int[] intValues = new int[imageSize * imageSize];
                        image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
                        int pixel = 0;

                        for (int i = 0; i < imageSize; i++) {
                            for (int j = 0; j < imageSize; j++) {
                                int val = intValues[pixel++];
                                byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255));
                                byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255));
                                byteBuffer.putFloat((val & 0xFF) * (1.f / 255));
                            }
                        }

                        // Runs model inference and gets result.
                        ResnetNoquantv2.Outputs outputs = model.process(inputFeature0);

                        TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                        float[] confidences;
                        confidences = outputFeature0.getFloatArray();
                        int maxPos;
                        if (confidences[0] > 0.5) {
                            maxPos = 0;
                        } else {
                            maxPos = 1;
                        }

                        String[] classes = {"PT", "HC"};

                        result.setText(classes[maxPos]);

                        String s = "";
                        String p = "";
                        s += String.format("%s: %.1f%%\n", classes[0], confidences[0] * 100);
                        p += String.format("%s: %.1f%%\n", classes[1], (1 - confidences[0]) * 100);
                        if (maxPos == 0) {
                            confidence.setText(s + p);
                        } else {
                            confidence.setText(p + s);
                        }
                        // Releases model resources if no longer used.
                        model.close();
                    } catch (IOException e) {
                        // TODO Handle the exception
                    }
                    break;
                }
        });*/

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(toggleButton.isChecked()){
                    c=1;
                } else{
                    c=0;
                }
            }
        });

        restartBtn.setOnClickListener(v -> restartActivity());

    }

    public void restartActivity() {
        finish();
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        //TODO overridePendingTransition(0,0);
    }

    void getPermission() {
            if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ProjectActivity.this, new String[]{Manifest.permission.CAMERA}, 11);
            }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 11) {
            if (grantResults.length > 0) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    this.getPermission();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 3) {
                Bitmap image = (Bitmap) data.getExtras().get("data");
                int dimension = Math.min(image.getWidth(), image.getHeight());
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                imageView.setImageBitmap(image);

                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                classifyImage(image);
            } else { //if (requestCode == 10) {
                Uri uri = data.getData();
                Bitmap image = null;
                try {
                    image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageView.setImageBitmap(image);
                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                classifyImage(image);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void classifyImage(Bitmap image) {
        if(c==0) {
            try {
                ResnetNoquantv2 model = ResnetNoquantv2.newInstance(getApplicationContext());

                // Creates inputs for reference.
                TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 299, 299, 3}, DataType.FLOAT32);
                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
                byteBuffer.order(ByteOrder.nativeOrder());

                int[] intValues = new int[imageSize * imageSize];
                image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
                int pixel = 0;

                for (int i = 0; i < imageSize; i++) {
                    for (int j = 0; j < imageSize; j++) {
                        int val = intValues[pixel++];
                        byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255));
                        byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255));
                        byteBuffer.putFloat((val & 0xFF) * (1.f / 255));
                    }
                }
                inputFeature0.loadBuffer(byteBuffer);

                // Runs model inference and gets result.
                ResnetNoquantv2.Outputs outputs = model.process(inputFeature0);
                TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                float[] confidences;
                confidences = outputFeature0.getFloatArray();
                int maxPos;
                if (confidences[0] > 0.5) {
                    maxPos = 0;
                } else {
                    maxPos = 1;
                }

                String[] classes = {"PT", "HC"};

                result.setText(classes[maxPos]);

                String s = "";
                String p = "";
                s += String.format("%s: %.1f%%\n", classes[0], confidences[0] * 100);
                p += String.format("%s: %.1f%%\n", classes[1], (1 - confidences[0]) * 100);
                if (maxPos == 0) {
                    confidence.setText(s + p);
                } else {
                    confidence.setText(p + s);
                }

                // Releases model resources if no longer used.
                model.close();
            } catch (IOException e) {
                Log.e(TAG, "IOException " + e.getMessage());
                // TODO Handle the exception
            }
        }
        else {
            try {
                ModelInception model = ModelInception.newInstance(getApplicationContext());

                // Creates inputs for reference.
                TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 299, 299, 3}, DataType.FLOAT32);
                ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
                byteBuffer.order(ByteOrder.nativeOrder());

                int[] intValues = new int[imageSize * imageSize];
                image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
                int pixel = 0;

                for (int i = 0; i < imageSize; i++) {
                    for (int j = 0; j < imageSize; j++) {
                        int val = intValues[pixel++];
                        byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255));
                        byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255));
                        byteBuffer.putFloat((val & 0xFF) * (1.f / 255));
                    }
                }
                inputFeature0.loadBuffer(byteBuffer);

                // Runs model inference and gets result.
                ModelInception.Outputs outputs = model.process(inputFeature0);
                TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                float[] confidences;
                confidences = outputFeature0.getFloatArray();
                int maxPos;
                if (confidences[0] > 0.5) {
                    maxPos = 0;
                } else {
                    maxPos = 1;
                }

                String[] classes = {"PT", "HC"};

                result.setText(classes[maxPos]);

                String s = "";
                String p = "";
                s += String.format("%s: %.1f%%\n", classes[0], confidences[0] * 100);
                p += String.format("%s: %.1f%%\n", classes[1], (1 - confidences[0]) * 100);
                if (maxPos == 0) {
                    confidence.setText(s + p);
                } else {
                    confidence.setText(p + s);
                }

                // Releases model resources if no longer used.
                model.close();
            } catch (IOException e) {
                Log.e(TAG, "IOException " + e.getMessage());
                // TODO Handle the exception
            }
        }
    }

}