/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.trabalhointcomp;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author bavi
 */
public class Teste {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        ArrayList<String> teste  = new ArrayList<>();
//        teste.add("valor 1");
//        teste.add("valor 2");
//        teste.add("valor 3");
//        teste.add("valor 4");
//        teste.add("valor 5");
//        System.out.println(Arrays.toString(teste.toArray()));
//        teste.add(2,"valor teste");
//        System.out.println(Arrays.toString(teste.toArray()));
//        teste.remove("valor teste");
//        System.out.println(Arrays.toString(teste.toArray()));
//        int t = (int) ((int)8*0.3);
//        System.out.println((int) ((int)8*0.3));
        int a[] = new int[5];
        a[0]=1;a[1]=2;a[2]=3;a[3]=4;a[4]=5;
        int b[] = a.clone();
        
        a[3]=4354;
        
        System.out.println(Arrays.toString(a));
        System.out.println("-------------");
        System.out.println(Arrays.toString(b));
        
        b  = a.clone();
        
        a[4]=3222;
        System.out.println(Arrays.toString(a));
        System.out.println("-------------");
        System.out.println(Arrays.toString(b));
    }
    
}
