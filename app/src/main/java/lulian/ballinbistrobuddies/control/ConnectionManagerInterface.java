package lulian.ballinbistrobuddies.control;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceInfo;
import android.net.wifi.p2p.nsd.WifiP2pDnsSdServiceRequest;
import android.util.Log;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

public interface ConnectionManagerInterface {

    static ConnectionManager getInstance(Context context, Activity activity) {
        return ConnectionManager.getInstance(context, activity);
    }

    void initialize();

    void serviceLoop();

    void setupService();

    void discoverServices();

    void advertiseService();

}
