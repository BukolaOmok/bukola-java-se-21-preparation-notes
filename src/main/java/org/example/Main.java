package org.example;

public class Main {
        public static void main(String[] args){
            outerLoop:
            for(var i = 0; i< 10; i++){
                for (var j = 0; j< 10; j++){
                    if ( i+ j > 10 )  break outerLoop;
                }
                System.out.println( "hello");
            }
        }
}