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


}