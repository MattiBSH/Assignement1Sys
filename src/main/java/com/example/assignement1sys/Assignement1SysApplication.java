package com.example.assignement1sys;

import mypackage.GeoIPServiceLocator;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.rpc.ServiceException;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@SpringBootApplication
public class Assignement1SysApplication {

    public static void main(String[] args) throws ServiceException, RemoteException {
        List<Person> people= new ArrayList<>();
        people.add(new Person("176.20.21.192","Bob","bob@gmail.com"));
        people.add(new Person("216.73.162.35","Justin","hoho@gmail.com"));

        for (int i = 0; i < people.size(); i++) {
            generateMail(people.get(i).ip,people.get(i).name);
        }
    }

    static String generateMail(String ip, String name) throws ServiceException, RemoteException {
        String finishedMail="";
        GeoIPServiceLocator geoIPServiceLocator = new GeoIPServiceLocator();
        String location =geoIPServiceLocator.getGeoIPServiceSoap().getIpLocation(ip);
        String countryID=getTagValue(location,"Country");
        String gender=getInfo(name,countryID);
        String title;

        if(gender.contains("male")){
            title="Mr";
        }else{
            title="Ms";
        }
        finishedMail="Dear "+title+" "+name+" how are you doing."+" Its so nice that you can get the message all the way from "+countryID;

        try {
            FileWriter myWriter = new FileWriter("mail to "+name);
            myWriter.write(finishedMail);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return finishedMail;
    }
    public static String getTagValue(String xml, String tagName){
        return xml.split("<"+tagName+">")[1].split("</"+tagName+">")[0];
    }

        static  String getInfo(String name, String countryID){
        /*
        Maven dependency for JSON-simple:
            <dependency>
                <groupId>com.googlecode.json-simple</groupId>
                <artifactId>json-simple</artifactId>
                <version>1.1.1</version>
            </dependency>
         */

            try {

                URL url = new URL("https://api.genderize.io?name="+name+"&country_id="+countryID);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                //Check if connect is made
                int responseCode = conn.getResponseCode();

                // 200 OK
                if (responseCode != 200) {
                    throw new RuntimeException("HttpResponseCode: " + responseCode);
                } else {

                    StringBuilder informationString = new StringBuilder();
                    Scanner scanner = new Scanner(url.openStream());

                    while (scanner.hasNext()) {
                        informationString.append(scanner.nextLine());
                    }
                    //Close the scanner
                    scanner.close();

                    //JSON simple library Setup with Maven is used to convert strings to JSON
                    JSONParser parse = new JSONParser();
                    JSONObject dataObject = (JSONObject) parse.parse(String.valueOf(informationString));
                    return dataObject.get("gender").toString();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }



}
