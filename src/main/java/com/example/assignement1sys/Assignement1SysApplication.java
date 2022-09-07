package com.example.assignement1sys;

import mypackage.GeoIPServiceLocator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.rpc.ServiceException;
import java.rmi.RemoteException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
@SpringBootApplication
public class Assignement1SysApplication {

    public static void main(String[] args) throws ServiceException, RemoteException {
        GeoIPServiceLocator geoIPServiceLocator = new GeoIPServiceLocator();
        String location =geoIPServiceLocator.getGeoIPServiceSoap().getIpLocation("176.20.21.192");
        System.out.println(location);
    }

    String generateMail(String ip, String name, String mail) throws ServiceException, RemoteException {
        boolean isMale=false;
        GeoIPServiceLocator geoIPServiceLocator = new GeoIPServiceLocator();
        String location =geoIPServiceLocator.getGeoIPServiceSoap().getIpLocation(ip);
        String countryID=getTagValue(location,"Country");
        System.out.println(countryID);
        return null;
    }
    public static String getTagValue(String xml, String tagName){
        return xml.split("<"+tagName+">")[1].split("</"+tagName+">")[0];
    }

    String findOutGender(){

        return "Male";
    }
}
