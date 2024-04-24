package com.filescripts.ipgather;

import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class Main {
 static    String hostIp,hostname,macAdress;
 static byte[] mac;

    public static void main(String[] args) {
        System.out.println("Hello world!");
        macAdress = "";
        try {

            InetAddress ip =  InetAddress.getLocalHost();
            hostIp = "IP adress: "+ip.getHostAddress();
            hostname = "hostname: "+ip.getHostName();
        }catch (UnknownHostException ex){
            ex.printStackTrace();
        }
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface interface1 = interfaces.nextElement();
                Enumeration<InetAddress> addresses = interface1.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (!address.isLoopbackAddress()) {
                         mac = interface1.getHardwareAddress();
                        if (mac == null) {
                            macAdress = "null";
                        }
                    }
                }
            }
        } catch (SocketException e) {
            System.err.println("Failed to get network interfaces.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Failed to get MAC address.");
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : mac) {
            sb.append(String.format("%02X:", b));
        }

        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        macAdress = sb.toString();
        sb =  new StringBuilder();
        sb.append(hostIp+"\n"+hostname+"\n"+"MAC address: "+macAdress);
        String desktopDir = System.getProperty("user.home");
        try {


            FileUtils.write(new File(desktopDir + "\\" + "ip.txt"), sb.toString(), "UTF-8");
        }catch (IOException ex){
            ex.printStackTrace();
        }
        System.out.println(desktopDir);

    }
}