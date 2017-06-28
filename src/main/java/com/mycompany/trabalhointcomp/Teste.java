/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.trabalhointcomp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author bavi
 */
public class Teste {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        String text1 = new String(Files.readAllBytes(Paths.get("/home/zumbi/Área de Trabalho/TrabIntComp/resultados")), StandardCharsets.UTF_8);
        String text2 = new String(Files.readAllBytes(Paths.get("/home/zumbi/Área de Trabalho/TrabIntComp/RSDSTSol(1)/BoundsP_SDST_Large.csv")), StandardCharsets.UTF_8);
        String[] result = text1.split("\n");
        String[] ref = text2.split("\n");
        HashMap<String,String> resultados = new HashMap<>();
        HashMap<String,String> reff = new HashMap<>();
        for(int i = 0 ; i < ref.length; i++){
             reff.put(ref[i].split(",")[0], ref[i].split(",")[3]);
        }
        
        for(int i = 0 ; i < result.length; i++){
            System.out.println(result[i].split(",")[0]+ " "+ result[i].split(",")[1]+ " " + reff.get(result[i].split(",")[0]));
        }
    }
    
    
}
