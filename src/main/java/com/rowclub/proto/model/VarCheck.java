package com.rowclub.proto.model;

import com.rowclub.proto.controller.ProtocolController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class VarCheck {


    private Scanner in = new Scanner(System.in).useDelimiter("\\n");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public int inputIntegerSvar(){
        int svar = -1;

        try {
            svar = in.nextInt();
        } catch (InputMismatchException exception) {
            System.out.println("Indtast venligst et nummer:");
            in.nextLine();
        }

        return svar;
    }

    public String calTime(int time){
        int hour = time / 60;
        int min = time % 60;
        String string;
        if(min < 10){
            string = hour + ":0" + min;
        }
        else if(min < 10){
            string = hour + ":0" + min;
        }
        else{
            string = hour + ":" + min;
        }
        return string;
    }


    public int DateDiff (Date t1, Date t2){

        int Diff = 0;



        return Diff;
    }



    public String tlfCheck(String tlf){

        String a = tlf.charAt(0) + "" + tlf.charAt(1) + "" + tlf.charAt(2);
        int b = ghostBusterStringToInt(a);
        tlf = ghostBusterStringToNum(tlf);
        //Evt. tilføj advarsel hvis linje oven over har brug for at køre

        System.out.println(a);
        if (b == 45) {
            tlf = tlf.substring(2);
        }



        if (tlf.length() > 8) {
            System.out.println("Too long phone number");
        }


        return tlf;

    }

    public static int ghostBusterStringToInt (String boo) {
        int busted = Integer.parseInt(boo.replaceAll("[^\\d]",""));
        //Who you gonna call?
        //♫♪ ♪♪ ♪ ♫♫♪♪♪

        return busted;
    }

    public static String ghostBusterStringToNum (String boo) {
        String busted = boo.replaceAll("[^\\d]","");
        //Who you gonna call?
        //♫♪ ♪♪ ♪ ♫♫♪♪♪

        return busted;
    }


}