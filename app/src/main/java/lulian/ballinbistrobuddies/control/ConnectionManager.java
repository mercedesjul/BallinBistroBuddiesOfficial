package lulian.ballinbistrobuddies.control;

import static android.content.Context.WIFI_P2P_SERVICE;
import static android.os.Looper.getMainLooper;
import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;

import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest;
import android.net.wifi.p2p.nsd.WifiP2pServiceRequest;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import lulian.ballinbistrobuddies.view.TablesView;

public class ConnectionManager implements ConnectionManagerInterface {
    private static final String TAG = "ConnectionManager";
    private static ConnectionManager instance;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private WifiP2pManager manager;
    private Channel channel;
    private List<WifiP2pDevice> peerList = new ArrayList<>();
    private WifiP2pDevice connectedDevice;
    private Context context;
    private Activity activity;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private final TableController tableController;
    WifiP2pDnsSdServiceInfo serviceInfo;
    HashMap<String, Map<String, String>> users = new HashMap<>();

    WifiP2pManager.DnsSdServiceResponseListener servListener = (instanceName, registrationType, device) -> {
        Log.d(TAG, "Service available - " + instanceName);
    };
    WifiP2pManager.DnsSdTxtRecordListener txtListener = (fullDomainName, record, device) -> {
        Log.d(TAG, "DnsSdTxtRecord available - " + record.toString());
        parseDnsSdTxtRecord(record, device);
    };

    private static final String SERVICE_TYPE = "_ballinservice._tcp";
    public static final String SERVICE_NAME = "BallinService";

    private ConnectionManager(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
        tableController = TableController.getInstance(((TablesView)activity).getUserModelFromPreferences());
        manager = (WifiP2pManager) context.getSystemService(WIFI_P2P_SERVICE);
        channel = manager.initialize(context, context.getMainLooper(), () -> Log.w(TAG, "Channel disconnected"));
    }

    private void parseDnsSdTxtRecord(Map<String, String> record, WifiP2pDevice device) {
        users.put(device.deviceAddress, record);
        tableController.addUsers(users);
    }

    public static ConnectionManager getInstance(Context context, Activity activity) {
        if (instance == null) {
            instance = new ConnectionManager(context, activity);
        }
        return instance;
    }

    public void initialize() {
        Log.i(TAG, "Initializing Connection Manager");
        if (checkPermissions()) {
            Log.i(TAG, "Permissions granted");
        } else {
            Log.i(TAG, "Permissions not granted, requesting permissions");
            requestPermissions();
        }
        serviceLoop();
    }

    public void serviceLoop() {

        setupService();
        scheduler.scheduleWithFixedDelay(this::discoverServices,0, 5,TimeUnit.SECONDS);
        scheduler.scheduleWithFixedDelay(this::advertiseService,0, 5,TimeUnit.SECONDS);
    }


    private boolean checkPermissions() {
        int locationPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        return locationPermission == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_CODE);
    }


    @SuppressLint("MissingPermission")
    public void setupService() {
        WifiP2pServiceRequest serviceRequest = WifiP2pDnsSdServiceRequest.newInstance();
        manager.addServiceRequest(channel, serviceRequest, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Added service request");
            }

            @Override
            public void onFailure(int reason) {
                Log.e(TAG, "Failed to add service request: " + reason);
                scheduler.schedule(ConnectionManager.this::setupService, 1, TimeUnit.SECONDS);
            }
        });
    }

    @SuppressLint("MissingPermission")
    public void discoverServices() {
        manager.setDnsSdResponseListeners(channel, servListener, txtListener);
        manager.discoverServices(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Service discovery initiated");
            }

            @Override
            public void onFailure(int reason) {
                Log.e(TAG, "Service discovery failed: " + reason);
                scheduler.schedule(ConnectionManager.this::discoverServices, 1, TimeUnit.SECONDS);
            }
        });
    }


    @SuppressLint("MissingPermission")
    public void advertiseService() {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "Service Advertisement Initiated");
            }

            @Override
            public void onFailure(int reason) {
                Log.e(TAG, "Failed to advertise service: " + reason);
                scheduler.schedule(ConnectionManager.this::advertiseService, 1, TimeUnit.SECONDS);
            }
        };
        Map<String, String> txtMap = tableController.getUserRecord();
        if (serviceInfo != null) {
             manager.removeLocalService(channel, serviceInfo, null);
        }
        serviceInfo = WifiP2pDnsSdServiceInfo.newInstance(SERVICE_NAME, SERVICE_TYPE, txtMap);

        manager.addLocalService(channel, serviceInfo, actionListener);
    }
}
