package com.berfinilik.javaproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.nfc.NfcAdapter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.berfinilik.javaproject.R;
import java.text.BreakIterator;
public class MainActivity extends AppCompatActivity {
    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private SensorEventListener proximitySensorEventListener;

    private Button btnWifi, btnBluetooth, btnUcG, btnSim, btnKulaklik, btnNfc, btnTitresim, btnOnKamera, btnArkaKamera, btnSarjSoketi, btnUcRenk, btnFlash, btnHoparlor, btnProximity;
    private TextView txtWifi, txtBluetooth, txtUcG, txtSim, txtKulaklik, txtNfc, txtTitresim, txtOnKamera, txtArkaKamera, txtSarjSoketi, txtUcRenk, txtFlash, txtHoparlor, txtProximity;
    private Vibrator vibrator;
    private WifiManager wifiManager;
    private BluetoothAdapter bluetoothAdapter;
    private Camera camera;
    private CameraManager cameraManager;
    private boolean isFlashOn = false;

    //ilgili iznleri almak için sabit istek kodları

    private static final int REQUEST_CODE_WIFI_PERMISSION = 1;
    private static final int REQUEST_BLUETOOTH_PERMISSION = 2;
    private static final int REQUEST_CODE_3G_PERMISSION = 3;
    private static final int REQUEST_CODE_SIM_PERMISSION = 4;
    private static final int REQUEST_CODE_KULAKLIK_PERMISSION = 5;
    private static final int REQUEST_CODE_NFC_PERMISSION = 6;
    private static final int REQUEST_CODE_TITRESİM_PERMİSSİON = 7;
    private static final int REQUEST_CODE_ARKA_KAMERA_PERMISSION = 8;
    private static final int REQUEST_CODE_FLASH_PERMISSION = 9;
    private static final int REQUEST_3RENK_PERMISSION = 10;
    private static final int REQUEST_CODE_COLOR_SETTINGS = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnWifi = findViewById(R.id.btnWifi);
        txtWifi = findViewById(R.id.txtWifi);
        btnBluetooth = findViewById(R.id.btnBluetooth);
        txtBluetooth = findViewById(R.id.txtBluetooth);
        btnUcG = findViewById(R.id.btnUcG);
        txtUcG = findViewById(R.id.txtUcG);
        btnSim = findViewById(R.id.btnSim);
        txtSim = findViewById(R.id.txtSim);
        btnKulaklik = findViewById(R.id.btnKulaklik);
        txtKulaklik = findViewById(R.id.txtKulaklik);
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        btnNfc = findViewById(R.id.btnNfc);
        txtNfc = findViewById(R.id.txtNfc);
        btnTitresim = findViewById(R.id.btnTitresim);
        txtTitresim = findViewById(R.id.txtTitresim);
        btnOnKamera = findViewById(R.id.btnOnKamera);
        txtOnKamera = findViewById(R.id.txtOnKamera);
        btnArkaKamera = findViewById(R.id.btnArkaKamera);
        txtArkaKamera = findViewById(R.id.txtArkaKamera);
        btnUcRenk = findViewById(R.id.btnUcRenk);
        txtUcRenk = findViewById(R.id.txtUcRenk);
        txtSarjSoketi = findViewById(R.id.txtSarjSoketi);
        btnSarjSoketi = findViewById(R.id.btnSarjSoketi);
        btnFlash = findViewById(R.id.btnFlash);
        txtFlash = findViewById(R.id.txtFlash);
        btnHoparlor = findViewById(R.id.btnHoparlor);
        txtHoparlor = findViewById(R.id.txtHoparlor);
        btnProximity = findViewById(R.id.btnProximity);
        txtProximity = findViewById(R.id.txtProximity);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        checkBatteryCharging();
        checkFlashlightState();

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        boolean hasFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!hasFlash) {
            btnFlash.setEnabled(false);
        }
        // Proximity sensörünün durumunu dinlemek için SensorEventListener oluşturun
        proximitySensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                // Proximity sensöründeki değişiklikleri işleyin
                if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                    float distance = event.values[0];
                    // Distance değerine göre yakınlığı kontrol edin
                    if (distance < proximitySensor.getMaximumRange()) {

                    } else {

                    }
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };

        btnWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CHANGE_WIFI_STATE) == PackageManager.PERMISSION_GRANTED) {

                    checkWifiState();
                } else {

                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CHANGE_WIFI_STATE}, REQUEST_CODE_WIFI_PERMISSION);
                }
            }
        });

        btnProximity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleProximitySensor(true); // Proximity sensörünü açar


                txtProximity.setText("Açık");
            }
        });

        btnUcRenk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Telefonun ekran ayarlarına yönlendir
                Intent intent = new Intent(android.provider.Settings.ACTION_DISPLAY_SETTINGS);
                startActivityForResult(intent, 0);
            }
        });

        btnBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED) {

                    checkBluetoothState();

                } else {

                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.BLUETOOTH}, REQUEST_BLUETOOTH_PERMISSION);
                }
            }
        });


        btnSarjSoketi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBatteryCharging();
            }
        });


        btnHoparlor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkHoparlorState();
            }
        });

        btnArkaKamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRearCameraOpen) {
                    askToCloseRearCamera();
                } else {
                    askToOpenRearCamera();
                }
            }
        });


        btnFlash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFlashOn) {
                    showTurnOffFlashDialog();
                } else {
                    toggleFlash();
                }
            }
        });

        updateFlashStatus();

        btnTitresim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {

                    checkTitresimState();
                } else {

                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.VIBRATE}, REQUEST_CODE_TITRESİM_PERMİSSİON);
                }
            }
        });

        btnOnKamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFrontCameraAvailable()) {
                    if (isFrontCameraOpen()) {
                        txtOnKamera.setText("Açık");
                    } else {
                        askToOpenFrontCamera();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Ön kamera bulunamadı.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnUcG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) {

                    check3GState();
                } else {

                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_CODE_3G_PERMISSION);
                }
            }
        });

        btnSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {

                    checkSimState();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE_SIM_PERMISSION);
                }
            }
        });

        btnKulaklik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isKulaklikTakili = isKulaklikTakili();
                if (isKulaklikTakili) {
                    txtKulaklik.setText("Takılı Değil");
                } else {
                    txtKulaklik.setText("Takılı Değil");
                }
            }
        });

        btnNfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.NFC) == PackageManager.PERMISSION_GRANTED) {
                    checkNfcState();
                } else {

                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.NFC}, REQUEST_CODE_NFC_PERMISSION);
                }
            }
        });

        checkWifiState();
        checkBluetoothState();
        check3GState();
        checkSimState();
        checkKulaklikState();
        checkNfcState();
        checkTitresimState();
        checkHoparlorState();
        checkFrontCameraState();
        checkFlashlightState();

    }
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


    }

    @Override
    protected void onResume() {
        super.onResume();
        // Proximity sensörünün dinlemeyi başlat
        sensorManager.registerListener(proximitySensorEventListener, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Proximity sensörünün dinlemeyi durdur
        sensorManager.unregisterListener(proximitySensorEventListener);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_WIFI_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkWifiState();
            } else {

                txtWifi.setText("Wi-Fi Kapalı");
                wifiManager.setWifiEnabled(false);
                Toast.makeText(MainActivity.this, "Wi-Fi izni verilmedi, izin isteyiniz.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_BLUETOOTH_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                checkBluetoothState();
            } else {

                txtBluetooth.setText("Bluetooth Kapalı");
                Toast.makeText(MainActivity.this, "Bluetooth izni verilmedi, izin isteyiniz.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_CODE_3G_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                check3GState();
            } else {
                txtUcG.setText("3G Kapalı");
                Toast.makeText(MainActivity.this, "3G izni verilmedi, izin isteyiniz.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_CODE_SIM_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkSimState();
            } else {
                txtSim.setText("SIM Kart Yok");
                Toast.makeText(MainActivity.this, "SIM kart izni verilmedi, izin isteyiniz.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_CODE_KULAKLIK_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkKulaklikState();
            } else {
                txtKulaklik.setText("Takılı Değil");
                Toast.makeText(MainActivity.this, "Kulaklık izni verilmedi, izin isteyiniz.", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == REQUEST_CODE_TITRESİM_PERMİSSİON) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                vibrator.vibrate(1000);
                txtTitresim.setText("Açık");
            } else {
                txtTitresim.setText("Kapalı");

            }
        } else if (requestCode == REQUEST_CODE_NFC_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkNfcState();
            } else {

                txtNfc.setText("Kapalı");
                Toast.makeText(MainActivity.this, "NFC izni verilmedi.", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == REQUEST_CODE_ARKA_KAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openRearCamera();
            } else {
                txtArkaKamera.setText("Kapalı");
                Toast.makeText(MainActivity.this, "Arka kamera izni verilmedi.", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private boolean isRearCameraOpen = false;

    private boolean isRearCameraAvailable() {
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                return true;
            }
        }
        return false;
    }
    private void toggleFlash() {
        try {
            String cameraId = cameraManager.getCameraIdList()[0];
            cameraManager.setTorchMode(cameraId, !isFlashOn);
            isFlashOn = !isFlashOn;
            updateFlashStatus();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void updateFlashStatus() {
        if (isFlashOn) {
            txtFlash.setText("Açık");
        } else {
            txtFlash.setText("Kapalı");
        }
    }
    private void askToCloseRearCamera() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Arka Kamera Kapatılsın mı?")
                .setMessage("Arka kamera açık görünüyor. Kapatmak ister misiniz?")
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        closeRearCamera();
                    }
                })
                .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void closeRearCamera() {

        txtArkaKamera.setText("Kapalı");
        isRearCameraOpen = false;
    }


    private void openRearCamera() {
        if (!isRearCameraOpen) {
            if (isRearCameraAvailable()) {

                txtArkaKamera.setText("Açık");
                isRearCameraOpen = true;

            } else {

                Toast.makeText(this, "Arka kamera açılamadı.", Toast.LENGTH_SHORT).show();
            }
        } else {

            Toast.makeText(this, "Arka kamera zaten açık.", Toast.LENGTH_SHORT).show();
        }
    }


    private void checkHoparlorState() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            if (audioManager.isSpeakerphoneOn()) {
                txtHoparlor.setText("Açık");
            } else {
                txtHoparlor.setText("Kapalı");
            }
        }
    }
    private void toggleProximitySensor(boolean enable) {
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (enable) {
            sensorManager.registerListener(proximityListener, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        } else {
            sensorManager.unregisterListener(proximityListener);
        }
    }

    private SensorEventListener proximityListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
                float distance = event.values[0];
                // Proximity değerini kontrol edebilir veya gerekli işlemleri yapabilirsiniz

                float proximityThreshold = 0;
                if (distance < proximityThreshold) {
                    txtProximity.setText("Açık");
                } else {
                    txtProximity.setText("Kapalı");
                }
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    private void checkFrontCameraState() {
        if (isFrontCameraAvailable()) {
            if (isFrontCameraOpen()) {
                txtOnKamera.setText("Açık");
            } else {
                txtOnKamera.setText("Kapalı");
            }
        } else {
            txtOnKamera.setText("Ön Kamera Yok");
        }
    }

    private void askToOpenRearCamera() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Arka Kamera Açılsın mı?")
                .setMessage("Arka kamera kapalı görünüyor. Açmak ister misiniz?")
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Open the camera application
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(cameraIntent);
                        }
                    }
                })
                .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        txtArkaKamera.setText("Kapalı");
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean isFrontCameraAvailable() {
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                return true;
            }
        }
        return false;
    }

    private boolean isFrontCameraOpen() {
        return camera != null;
    }

    private void openFrontCamera() {
        try {
            camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            txtOnKamera.setText("Açık");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Ön kamera açılamadı:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void askToOpenFrontCamera() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Ön Kamera Açılsın mı?")
                .setMessage("Ön kamera kapalı görünüyor. Açmak ister misiniz?")
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        openFrontCamera();
                    }
                })
                .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        txtOnKamera.setText("Kapalı");
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void checkWifiState() {
        if (isNetworkAvailable()) {
            txtWifi.setText("Açık");
        } else {
            txtWifi.setText("Kapalı");

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Wi-Fi Açılsın mı?")
                    .setMessage("Wi-Fi kapalı görünüyor. Açmak ister misiniz?")
                    .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.dismiss();
                        }
                    });

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
    private void showTurnOffFlashDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Fener Kapat")
                .setMessage("Feneri kapatmak istiyor musunuz?")
                .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        toggleFlash();
                    }
                })
                .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void checkBluetoothState() {
        if (bluetoothAdapter.isEnabled()) {
            txtBluetooth.setText("Açık");
        } else {
            txtBluetooth.setText("Kapalı");

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Bluetooth'u açmak ister misiniz?")
                    .setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            openBluetoothSettings();
                        }
                    })
                    .setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
    private void openBluetoothSettings() {
        Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
        startActivity(intent);
    }
    private void requestBluetoothPermission() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_BLUETOOTH_PERMISSION);
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.BLUETOOTH}, REQUEST_BLUETOOTH_PERMISSION);
        }
    }


    private void check3GState() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobileNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if (mobileNetworkInfo != null && mobileNetworkInfo.isConnected()) {
            txtUcG.setText("Açık");
        } else {
            txtUcG.setText("Kapalı");
        }
    }

    private void checkSimState() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (telephonyManager.getSimState() == TelephonyManager.SIM_STATE_READY) {
            String simOperatorName = telephonyManager.getSimOperatorName();
            if (simOperatorName != null && !simOperatorName.isEmpty()) {
                txtSim.setText(simOperatorName);
            } else {
                txtSim.setText("Yok");
            }
        } else {
            txtSim.setText("Yok");
        }
    }
    private void checkHeadsetStatus() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        if (audioManager.isWiredHeadsetOn()) {
            txtKulaklik.setText("Takılı Değil");
        } else {
            txtKulaklik.setText("Takılı Değil");
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiNetworkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return wifiNetworkInfo != null && wifiNetworkInfo.isConnected();
    }
    private void checkTitresimState() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {

            if (isVibrationEnabled()) {
                vibrator.vibrate(1000);
                txtTitresim.setText("Açık");
            } else {
                txtTitresim.setText("Kapalı");
            }
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.VIBRATE}, REQUEST_CODE_TITRESİM_PERMİSSİON);
        }
    }
    private boolean isVibrationEnabled() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        return audioManager != null && audioManager.getRingerMode() == AudioManager.RINGER_MODE_VIBRATE;
    }
    private boolean isKulaklikTakili() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            return audioManager.isWiredHeadsetOn();
        }
        return false;
    }
    private void checkNfcState() {
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter != null && nfcAdapter.isEnabled()) {

            txtNfc.setText("Açık");
            if (nfcAdapter != null) {
                nfcAdapter.enableReaderMode(this, null, NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK, null);
            }
        } else {

            txtNfc.setText("Kapalı");
            if (nfcAdapter != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("NFC Açılsın mı?");
                builder.setMessage("NFC kapalı görünüyor. Açmak ister misiniz?");
                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // NFC'yi etkinleştirmek için NFC ayarlarını açın
                        Intent intent = new Intent(Settings.ACTION_NFC_SETTINGS);
                        startActivity(intent);
                    }
                });

                builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Kullanıcı NFC'yi etkinleştirmemeyi seçti, hiçbir şey yapmayın
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }
    private void checkKulaklikState() {
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (audioManager != null) {
            boolean kulaklikTakili = audioManager.isWiredHeadsetOn() || audioManager.isBluetoothA2dpOn();

            if (kulaklikTakili) {

                txtKulaklik.setText("Takılı Değil ");
            } else {

                txtKulaklik.setText("Takılı Değil");
            }
        }
    }

    private void checkBatteryCharging() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);

        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        if (isCharging) {
            txtSarjSoketi.setText("Şarjda");
        } else {
            txtSarjSoketi.setText("Şarjda " +
                    "Değil");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (camera != null) {
            camera.release();
            camera = null;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_BLUETOOTH_PERMISSION) {
            if (resultCode == RESULT_OK) {
                // Bluetooth, kullanıcı tarafından başarıyla etkinleştirildi
                txtBluetooth.setText(" Açık");
            } else {
                // Bluetooth etkinleştirme isteği kullanıcı tarafından reddedildi
                txtBluetooth.setText("Kapalı");
            }
        }
        else if (requestCode == REQUEST_3RENK_PERMISSION) {
            if (resultCode == RESULT_OK) {
                int selectedColorScheme = 1; // Ekran ayarlarından alınan renk şeması (canlı, doygun, standart)

                if (selectedColorScheme == 1) {
                    txtUcRenk.setText("Canlı");
                } else if (selectedColorScheme == 2) {
                    txtUcRenk.setText("Doygun");
                } else if (selectedColorScheme == 3) {
                    txtUcRenk.setText("Standart");
                }
            }
        }
    }

    private boolean isFlashlightOn() {
        return false;
    }
    private void checkFlashlightState() {
        if (isFlashlightOn()) {
            txtFlash.setText("Açık");
        } else {
            txtFlash.setText("Kapalı");
        }
    }
}

















