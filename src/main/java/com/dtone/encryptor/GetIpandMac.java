package com.dtone.encryptor;


import java.net.InetAddress;
import java.net.NetworkInterface;

public class GetIpandMac {
    public String getClientMACAddress(){
        try{
            InetAddress inetaddress= InetAddress.getLocalHost(); //Get LocalHost reference

            //get Network interface Reference by InetAddress Reference
            NetworkInterface network = NetworkInterface.getByInetAddress(inetaddress);
            byte[] macArray = network.getHardwareAddress();  //get Hardware address Array
            StringBuilder str = new StringBuilder();

            // Convert Array to String
            for (int i = 0; i < macArray.length; i++) {
                str.append(String.format("%02X%s", macArray[i], (i < macArray.length - 1) ? "-" : ""));
            }
            String macAddress=str.toString();

            return macAddress; //return MAc Address
        }
        catch(Exception E){
            E.printStackTrace();  //print Exception StackTrace
            return null;
        }
    }
}
