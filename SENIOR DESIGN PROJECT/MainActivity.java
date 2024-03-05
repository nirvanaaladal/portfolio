package com.example.intellicoach;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;


import com.example.intellicoach.ml.ModelSvc;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import com.huawei.hms.maps.CameraUpdateFactory;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.MapView;
import com.huawei.hms.maps.MapsInitializer;
import com.huawei.hms.maps.OnMapReadyCallback;
import com.huawei.hms.maps.SupportMapFragment;
import com.huawei.hms.maps.model.LatLng;
import com.huawei.hms.maps.model.MarkerOptions;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, UdpReceiver.OnMessageReceivedListener {

    private static final String TAG = "MapViewDemoActivity";
    // Huawei map.
    private HuaweiMap hMap;

    private MapView mMapView;

    private float latitude;
    private float longitude;
    private EditText ageEditText;
    private EditText nameEditText;
    private TextView messageTextView;

    private UdpReceiver udpReceiver;

    private LineChart chart;

    private Thread thread;
    private boolean plotData = true;


    private final int count = 0;
    private LineDataSet dataSet;

    private float lastX = 0;

    //to get zone
    private int userAge = 0;

    private long prevTime;
    private Location previousLocation;
    private Location currentLocation;

    float accelx = 0;
    float accely = 0;
    float accelz = 0;
    float gyrox = 0;
    float gyroy = 0;
    float gyroz = 0;

    int processpacket = 0;


    //for speaker
    private Button buttonPlayMelody1;
    private Button buttonPlayMelody2;
    private Button buttonPlayMelody3;

    private static final String ARDUINO_IP = "192.168.43.40";
    private static final int ARDUINO_PORT = 8888;


    private static final String SERVER_IP = "0.0.0.0"; // Replace with your tablet IP
    private static final int SERVER_PORT = 8888;

    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";

    private TextView machineLearningTextView;

    final int MPU_AC_GYRO_DATA_SIZE = 6;

    List<Float> mpuDataListFloatGlobal = new ArrayList<>(MPU_AC_GYRO_DATA_SIZE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ageEditText = findViewById(R.id.ageEditText);
        nameEditText = findViewById(R.id.nameEditText);
        messageTextView = findViewById(R.id.messageTextView);
        machineLearningTextView = findViewById(R.id.MachineLearning);


        // Allow network operations on the main thread
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        buttonPlayMelody1 = findViewById(R.id.button_play_melody1);
        buttonPlayMelody2 = findViewById(R.id.button_play_melody2);
        buttonPlayMelody3 = findViewById(R.id.button_play_melody3);

        buttonPlayMelody1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUdpMessage("1");
            }
        });

        buttonPlayMelody2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUdpMessage("2");
            }
        });

        buttonPlayMelody3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendUdpMessage("3");
            }
        });

        chart = findViewById(R.id.chart);

        // enable description text
        chart.getDescription().setEnabled(true);
        chart.getDescription().setText("Beats Per Minute");

        // enable touch gestures
        chart.setTouchEnabled(true);

        // enable scaling and dragging
        chart.setDragEnabled(true);
        chart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(true);

        // customize legend
        Legend legend = chart.getLegend();
        legend.setForm(Legend.LegendForm.LINE);
        legend.setTextColor(Color.WHITE);
        legend.setEnabled(true);

        // customize x-axis
        XAxis xAxis = chart.getXAxis();
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawGridLines(true);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setEnabled(true);

        // customize left y-axis
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setTextColor(Color.WHITE);
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMaximum(200f);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);

        // customize right y-axis
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        // customize data
        LineData data = new LineData();
        data.setValueTextColor(Color.WHITE);

        // add empty data
        chart.setData(data);
        chart.setDrawBorders(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.setDrawGridBackground(false);

//previous time
     //   prevTime = System.currentTimeMillis();


        feedMultiple();

        //call method for zone
        askUserForAge();
        // call method for name
        askUserForName();

        udpReceiver = new UdpReceiver(this);
        udpReceiver.startReceiver(SERVER_IP, SERVER_PORT);


        // Obtain a MapView instance.
        mMapView = findViewById(R.id.mapView);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        // Please replace Your API key with the API key in
        // agconnect-services.json.
        mMapView.onCreate(mapViewBundle);
        // Obtain a map instance.
        mMapView.getMapAsync(this);

        //DebugFullPacketDecode(); // For Debugging
    }

    



    private void askUserForName() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter your name");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String userName = input.getText().toString();
                nameEditText.setText("Athlete name: " + userName);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }



    private void askUserForAge() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter your age");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                userAge = Integer.parseInt(input.getText().toString());
                //  String userAge = input.getText().toString();
                ageEditText.setText("Athlete age: " + userAge);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    private int calculateMaxHeartRate(int age) {
        return (int)(208.5 - (0.8*age));
    }
    private String getHeartRateZone(float bpm) {
        if (userAge == 0) {
            return "N/A";
        }

        int maxHeartRate = calculateMaxHeartRate(userAge);
        float percentageOfMax = bpm / maxHeartRate;

        if (percentageOfMax < 0.6) {
            return "Zone 1 (Warm-up)";
        } else if (percentageOfMax < 0.7) {
            return "Zone 2 (Fat burn)";
        } else if (percentageOfMax < 0.8) {
            return "Zone 3 (Cardio)";
        } else if (percentageOfMax < 0.9) {
            return "Zone 4 (Peak)";
        } else {
            return "Zone 5 (Max)";
        }
    }


    private void sendUdpMessage(String message) {
        try {
            DatagramSocket socket = new DatagramSocket();
            byte[] buf = message.getBytes();
            InetAddress address = InetAddress.getByName(ARDUINO_IP);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, ARDUINO_PORT);
            socket.send(packet);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    static int counter = 0;
    static float sum = 0;

    private void addEntry(final float bpm) {

        synchronized (chart.getData()) {

            LineData data = chart.getData();

            if (data != null) {

                ILineDataSet set = data.getDataSetByIndex(0);
                // set.addEntry(...); // can be called as well

                if (set == null) {
                    set = createSet();
                    data.addDataSet(set);
                }

                counter++;
                sum += bpm;

                // calculate average every 5 readings
                if (counter % 10 == 0) {
                    float average = sum / 10;
                    sum = 0;
                    counter = 0;
                    data.addEntry(new Entry(set.getEntryCount(), average), 0); // add average to the chart
                }

                data.addEntry(new Entry(set.getEntryCount(), bpm), 0);
                data.notifyDataChanged();

                // let the chart know it's data has changed
                chart.notifyDataSetChanged();

                // limit the number of visible entries
                chart.setVisibleXRangeMaximum(150);
                chart.setVisibleXRangeMinimum(20);

                // move to the latest entry
                chart.moveViewToX(data.getEntryCount());

            }

        }
    }


    private LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "BPM");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setLineWidth(3f);
        set.setColor(Color.WHITE);
        set.setHighlightEnabled(false);
        set.setDrawValues(false);
        set.setDrawCircles(false);
        set.setMode(LineDataSet.Mode.LINEAR);
        set.setCubicIntensity(0.2f);

        return set;
    }


    private void feedMultiple() {

        if (thread != null) {
            thread.interrupt();
        }

        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    plotData = true;
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();
    }



    @Override
    protected void onStart() {
        super.onStart();
        mMapView.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMapView.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
        udpReceiver.stopReceiver();

    }



    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    private static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onMessageReceived(String message) {
        String[] data = message.split(",");


        if (message.contains(",")) {
            data = message.split(",");
        }

        long irValue = 0;
        float beatsPerMinute = 0;
        int beatAvg = 0;

        long milliseconds = 0;


        float latitude = 0;
        float longitude = 0;

            float speed = 0;
            float speed_mps = 0;
        float distance = 0;

        if (processpacket == 0) {
            for (String item : data) {
                if (item.startsWith("IR=")) {
                    irValue = Long.parseLong(item.substring(3));
                } else if (item.startsWith("BPM=")) {
                    beatsPerMinute = Float.parseFloat(item.substring(4));
                    if (beatsPerMinute >= 60 && beatsPerMinute <= 100) {
                        addEntry(beatsPerMinute);
                    }
                } else if (item.startsWith("Avg BPM=")) {
                    beatAvg = Integer.parseInt(item.substring(8));
                } else if (item.startsWith("Time=")) {
                    milliseconds = Long.parseLong(item.substring(5));
                } else if (item.startsWith("AccelX=")) {
                    accelx = Float.parseFloat(item.substring(7));
                } else if (item.startsWith("AccelY=")) {
                    accely = Float.parseFloat(item.substring(7));
                } else if (item.startsWith("AccelZ=")) {
                    accelz = Float.parseFloat(item.substring(7));
                } else if (item.startsWith("GyroX=")) {
                    gyrox = Float.parseFloat(item.substring(6));
                } else if (item.startsWith("GyroY=")) {
                    gyroy = Float.parseFloat(item.substring(6));
                } else if (item.startsWith("GyroZ=")) {
                    gyroz = Float.parseFloat(item.substring(6));
                } else if (item.startsWith("Latitude=")) {
                    // Convert latitude from DDMM.MMMM to decimal degrees
                    float ddmm = Float.parseFloat(item.substring(9));
                    float degrees = (float) Math.floor(ddmm / 100.0f);
                    float minutes = ddmm - degrees * 100.0f;
                    latitude = degrees + minutes / 60.0f;
                } else if (item.startsWith("Longitude=")) {
                    // Convert longitude from DDDMM.MMMM to decimal degrees
                    float dddmm = Float.parseFloat(item.substring(10));
                    float degrees = (float) Math.floor(dddmm / 100.0f);
                    float minutes = dddmm - degrees * 100.0f;
                    longitude = degrees + minutes / 60.0f;
                    processpacket = 1;
                }
            }
        }

        if (processpacket == 1) {
            final long finalIrValue = irValue;
            final float finalBeatsPerMinute = beatsPerMinute;
            final int finalBeatAvg = beatAvg;


            final long finalMilliseconds = milliseconds;
            final float finalAccelx = accelx;
            final float finalAccely = accely;
            final float finalAccelz = accelz;
            final float finalGyrox = gyrox;
            final float finalGyroy = gyroy;
            final float finalGyroz = gyroz;

            final float finalLatitude = latitude;
            final float finalLongitude = longitude;
                   final float finalSpeed = speed_mps;
                   final float finalDistance = distance;

            mpuDataListFloatGlobal.add(finalAccelx);
            mpuDataListFloatGlobal.add(finalAccely);
            mpuDataListFloatGlobal.add(finalAccelz);
            mpuDataListFloatGlobal.add(finalGyrox);
            mpuDataListFloatGlobal.add(finalGyroy);
            mpuDataListFloatGlobal.add(finalGyroz);


            runOnUiThread(() -> {
                messageTextView.setText(
                        "\nBeats Per Minute: " + finalBeatsPerMinute +
                                "\nHeart Rate Zone: " + getHeartRateZone(finalBeatsPerMinute)+
                          "\nSpeed (m/s): " + finalSpeed +
                            "\nDistance (m): " + finalDistance
                );
            });

            Log.d("In", "MPU Data List Float Global - Message Decode" + mpuDataListFloatGlobal);
            if (mpuDataListFloatGlobal.size() > 6) {
                Log.d("In", "MPU Size" + mpuDataListFloatGlobal.size());
            }
            classifyActivity();
            if (mpuDataListFloatGlobal.size() > 6) {
                Log.d("In", "MPU Size" + mpuDataListFloatGlobal.size());
            }
            mpuDataListFloatGlobal.clear();
            processpacket = 0;
        }
    }

    @Override
    public void onMapReady(HuaweiMap map) {
        // Obtain a map instance from callback.
        Log.d(TAG, "onMapReady: ");
        hMap = map;

        // Create a LatLng object with the latitude and longitude data.
       LatLng location = new LatLng(latitude, longitude);
       // LatLng location = new LatLng(25.2600644, 51.4767002); // for testing the map

        // Add a marker to the map with the location.
        MarkerOptions markerOptions = new MarkerOptions().position(location);
        hMap.addMarker(markerOptions);
        hMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 10f));

    }

    public void classifyActivity() {
        try {
            ModelSvc model = ModelSvc.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 6}, DataType.FLOAT32);
            inputFeature0.loadArray(FloatListToFloatArray(mpuDataListFloatGlobal)); // The shape of data needs to be [1, 6]

            // Runs model inference and gets result.
            ModelSvc.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();

            // Find the index of Class with the biggest confidence
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidences.length; i++) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }

            String[] classes = {"Sitting", "Standing", "Walking"};
            String Activity = classes[maxPos];

            Log.d("out", "Predicted Activity: " + Activity);

            // Show Activity on Activity Text View
            machineLearningTextView.setText("Predicted Activity:" + Activity);

            // Releases model resources if no longer used.
            model.close();

            //clear the list for the next prediction

        } catch (IOException e) {
            // TODO Handle the exception
        }

    }
    private float[] FloatListToFloatArray(List<Float> data){
        int i = 0;
        float[] array = new float[data.size()];
        for (Float f: data){
            array[i++] = (f !=null ? f: Float.NaN);
        }
        return array;
    }


    // For Debugging
    public void messageDecode() {

        String message = "Time=151,AccelX=-0.43,AccelY=9.86,AccelZ=-2.42,GyroX=-0.5,GyroY=0.05,GyroZ=0.17";
        String[] data = message.split(",");

        long milliseconds = 0;
        float accelx = 0;
        float accely = 0;
        float accelz = 0;
        float gyrox = 0;
        float gyroy = 0;
        float gyroz = 0;

        for (String item : data) {
            if (item.startsWith("Time=")) {
                milliseconds = Long.parseLong(item.substring(5));
            } else if (item.startsWith("AccelX=")) {
                accelx = Float.parseFloat(item.substring(7));
            } else if (item.startsWith("AccelY=")) {
                accely = Float.parseFloat(item.substring(7));
            } else if (item.startsWith("AccelZ=")) {
                accelz = Float.parseFloat(item.substring(7));
            } else if (item.startsWith("GyroX=")) {
                gyrox = Float.parseFloat(item.substring(6));
            } else if (item.startsWith("GyroY=")) {
                gyroy = Float.parseFloat(item.substring(6));
            } else if (item.startsWith("GyroZ=")) {
                gyroz = Float.parseFloat(item.substring(6));
            }
        }

        final long finalMilliseconds = milliseconds;
        final float finalAccelx = accelx;
        final float finalAccely = accely;
        final float finalAccelz = accelz;
        final float finalGyrox = gyrox;
        final float finalGyroy = gyroy;
        final float finalGyroz = gyroz;

//        runOnUiThread(() -> {
//            messageTextView.setText(
//                    "Milliseconds: " + finalMilliseconds + "\n" +
//                            "Acceleration X: " + finalAccelx + "\n" +
//                            "Acceleration Y: " + finalAccely + "\n" +
//                            "Acceleration Z: " + finalAccelz + "\n" +
//                            "Gyroscope X: " + finalGyrox + "\n" +
//                            "Gyroscope Y: " + finalGyroy + "\n" +
//                            "Gyroscope Z: " + finalGyroz + "\n"
//            );
//
//        });

        mpuDataListFloatGlobal.add(finalAccelx);
        mpuDataListFloatGlobal.add(finalAccely);
        mpuDataListFloatGlobal.add(finalAccelz);
        mpuDataListFloatGlobal.add(finalGyrox);
        mpuDataListFloatGlobal.add(finalGyroy);
        mpuDataListFloatGlobal.add(finalGyroz);

        Log.d("In", "MPU Data List Float Global - Message Decode" + mpuDataListFloatGlobal);
        classifyActivity();
        mpuDataListFloatGlobal.clear();
    }


    // For Debugging
    public void DebugFullPacketDecode ()
    {
        String message = "IR=1320,BPM=8.09,Avg BPM=42,Time=6927927,AccelX=1.50,AccelY=-0.15,AccelZ=10.64,GyroX=-0.59,GyroY=0.03,GyroZ=-0.03,Latitude=0.0000,Longitude=0.0000";
        String[] data = message.split(",");


        if (message.contains(",")) {
            data = message.split(",");
        }

        long irValue = 0;
        float beatsPerMinute = 0;
        int beatAvg = 0;

        long milliseconds = 0;


        float latitude = 0;
        float longitude = 0;

        //    float speed = 0;
        //    float speed_mps = 0;
        //    float distance = 0;

        if (processpacket == 0)
        {
            for (String item : data)
            {
                if (item.startsWith("IR=")) {
                    irValue = Long.parseLong(item.substring(3));
                } else if (item.startsWith("BPM=")) {
                    beatsPerMinute = Float.parseFloat(item.substring(4));
                    if (beatsPerMinute >= 60 && beatsPerMinute <= 100) {
                        addEntry(beatsPerMinute);
                    }
                }else if (item.startsWith("Avg BPM=")) {
                    beatAvg = Integer.parseInt(item.substring(8));
                } else if (item.startsWith("Time=")) {
                    milliseconds = Long.parseLong(item.substring(5));
                } else if (item.startsWith("AccelX=")) {
                    accelx = Float.parseFloat(item.substring(7));
                } else if (item.startsWith("AccelY=")) {
                    accely = Float.parseFloat(item.substring(7));
                } else if (item.startsWith("AccelZ=")) {
                    accelz = Float.parseFloat(item.substring(7));
                } else if (item.startsWith("GyroX=")) {
                    gyrox = Float.parseFloat(item.substring(6));
                } else if (item.startsWith("GyroY=")) {
                    gyroy = Float.parseFloat(item.substring(6));
                } else if (item.startsWith("GyroZ=")) {
                    gyroz = Float.parseFloat(item.substring(6));
                } else if (item.startsWith("Latitude=")) {
                    // Convert latitude from DDMM.MMMM to decimal degrees
                    float ddmm = Float.parseFloat(item.substring(9));
                    float degrees = (float) Math.floor(ddmm / 100.0f);
                    float minutes = ddmm - degrees * 100.0f;
                    latitude = degrees + minutes / 60.0f;
                } else if (item.startsWith("Longitude=")) {
                    // Convert longitude from DDDMM.MMMM to decimal degrees
                    float dddmm = Float.parseFloat(item.substring(10));
                    float degrees = (float) Math.floor(dddmm / 100.0f);
                    float minutes = dddmm - degrees * 100.0f;
                    longitude = degrees + minutes / 60.0f;
                    processpacket = 1;
                }
            }
        }

        if (processpacket == 1)
        {
            final long finalIrValue = irValue;
            final float finalBeatsPerMinute = beatsPerMinute;
            final int finalBeatAvg = beatAvg;


            final long finalMilliseconds = milliseconds;
            final float finalAccelx = accelx;
            final float finalAccely = accely;
            final float finalAccelz = accelz;
            final float finalGyrox = gyrox;
            final float finalGyroy = gyroy;
            final float finalGyroz = gyroz;

            final float finalLatitude = latitude;
            final float finalLongitude = longitude;
            //       final float finalSpeed = speed_mps;
            //       final float finalDistance = distance;

            mpuDataListFloatGlobal.add(finalAccelx);
            mpuDataListFloatGlobal.add(finalAccely);
            mpuDataListFloatGlobal.add(finalAccelz);
            mpuDataListFloatGlobal.add(finalGyrox);
            mpuDataListFloatGlobal.add(finalGyroy);
            mpuDataListFloatGlobal.add(finalGyroz);


            runOnUiThread(() -> {
                messageTextView.setText(
                        "\nBeats Per Minute: " + finalBeatsPerMinute +
                                "\nHeart Rate Zone: " + getHeartRateZone(finalBeatsPerMinute)
                        //  "\nSpeed (m/s): " + finalSpeed +
                        //      "\nDistance (m): " + finalDistance
                );
            });

            Log.d("In", "MPU Data List Float Global - Message Decode"+ mpuDataListFloatGlobal);
            if(mpuDataListFloatGlobal.size() > 6)
            {
                Log.d("In", "MPU Size"+ mpuDataListFloatGlobal.size());
            }
            classifyActivity();
            if(mpuDataListFloatGlobal.size() > 6)
            {
                Log.d("In", "MPU Size"+ mpuDataListFloatGlobal.size());
            }
            mpuDataListFloatGlobal.clear();
            processpacket = 0;
        }

    }


}
