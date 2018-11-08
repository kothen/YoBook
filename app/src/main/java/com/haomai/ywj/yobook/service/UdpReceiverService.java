package com.haomai.ywj.yobook.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

/**
 * Created by Administrator on 2018-11-06.
 */

public class UdpReceiverService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("tag", "进入service");
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        DatagramSocket dgSocket = null;
                        int port = 3779;
                        if (dgSocket == null) {
                            dgSocket = new DatagramSocket(null);
                            dgSocket.setReuseAddress(true);
                            dgSocket.bind(new InetSocketAddress(port));
                        }
                        byte[] by = new byte[1024];
                        DatagramPacket packet = new DatagramPacket(by, by.length);
                        dgSocket.receive(packet);
                        String str = new String(packet.getData(), 0, packet.getLength());
                        System.out.println("接收到的数据为：" + str);
                    } catch (SocketException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() { // TODO Auto-generated method stub
        super.onDestroy();
    }
}

