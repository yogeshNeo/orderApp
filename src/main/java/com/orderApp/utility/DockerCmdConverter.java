package com.orderApp.utility;

import java.util.StringJoiner;

public class DockerCmdConverter {

    public static void main(String[] args) {
        StringJoiner joinNames = new StringJoiner(",", "[", "]");

        String sample = "  ./gradlew clean bootjar  ";

        String trim = sample.trim();
        String[] arrOfStr = trim.split(" ");

        for (String a : arrOfStr)
            joinNames.add("\"" + a + "\"");

        System.out.println("CMD " + joinNames);
    }
}
