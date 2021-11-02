package com.cyborck.dsbMobileAPI.dataReader;

import java.io.IOException;

public class Test {
    public static void main ( String[] args ) throws IOException {
        System.out.println( DataReader.getJSON("311441", "schuleisttoll") );
    }
}
