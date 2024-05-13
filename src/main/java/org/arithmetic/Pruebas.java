package org.arithmetic;

import java.util.ArrayList;


public class Pruebas {
    public static void main(String[] args) {

        String expression = "( 1 + ( ( 2 + 3 ) * (4 * 5) ) )";

        String expressionTwo = "( 5 * ( 4 * ( 3 * ( 2 * ( 1 * 9 ) / 8 - 7 ) + 6 ) ) )";
        String[] expressionDivided = expression.split(" ");
        String[] expressionDividedTwo = expressionTwo.split(" ");
       for ( String input: expressionDivided){
           System.out.print(input + "/");
       }
    }
}


