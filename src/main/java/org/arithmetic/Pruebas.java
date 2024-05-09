package org.arithmetic;

import java.util.Arrays;
import java.util.List;

public class Pruebas {
    public static void main(String[] args) {
        String expression = "( 1 - ( 1 + 1 ) * 2 )";
        String[] prueba = expression.split(" ");

        for (String it : prueba) {
            System.out.print(it + "/");
        }
        List<String> algo = Arrays.stream(prueba).filter((it) -> it.equals("(") || it.equals(")")).toList();
        System.out.println("\n" + algo.size());
    }
}


