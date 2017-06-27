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
import java.util.Arrays;

/**
 *
 * @author marcos
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    private static Tarefa[] tarefas;
    private static Maquina[] maquinas;

    public static void lerInstancia2(String arq) throws IOException {
        String text = new String(Files.readAllBytes(Paths.get(arq)), StandardCharsets.UTF_8);

        String[] line = text.split("\n");
        int i = 0;
        int qtdTarefas = Integer.parseInt(line[1].trim());
        int qtdMaquinas = Integer.parseInt(line[0].trim());
        tarefas = new Tarefa[qtdTarefas];
        maquinas = new Maquina[qtdMaquinas];
        for (int j = 0; j < maquinas.length; j++) {
            maquinas[j] = new Maquina(qtdTarefas, String.valueOf(j));
        }
        i = i + 2;
        int j = 0;
        while (j < qtdTarefas) {
            String[] tempos = line[i].split(" ");
            for (int l = 0; l < tempos.length; l++) {
                if (!tempos[l].equals("")) {
                    maquinas[l].adicionarTempoExecucao(j, Integer.parseInt(tempos[l].trim()));
                }
            }
            tarefas[j] = new Tarefa(j);
            i++;
            j++;
        }
        for (int k = 0; k < qtdMaquinas; k++) {
            for (int t = 0; t < qtdTarefas; t++) {
                String[] temposP = line[i].split(" ");
                for (int l = 0; l < temposP.length; l++) {
                    if (!temposP[l].equals("")) {
                        maquinas[k].adicionarTempoPreparacao(t, l, Integer.parseInt(temposP[l].trim()));
                    }
                }
                i++;
            }
        }
    }

    public static void lerInstacia1(String arq) throws IOException {
        String text = new String(Files.readAllBytes(Paths.get(arq)), StandardCharsets.UTF_8);
        int i = 0;
        String[] line = text.split("\n");
        String info[] = line[0].split("  ");
        int qtdTarefas = Integer.parseInt(info[0].trim());
        int qtdMaquinas = Integer.parseInt(line[1].trim());
        tarefas = new Tarefa[qtdTarefas];
        maquinas = new Maquina[qtdMaquinas];
        for (int j = 0; j < maquinas.length; j++) {
            maquinas[j] = new Maquina(qtdTarefas, String.valueOf(j));
        }
        i = i + 2;
        int j = 0;
        while (j < qtdTarefas) {
            String[] tempos = line[i].split("\t");
            for (int k = 1; k < 2 * qtdMaquinas + 1; k=k+2) {
                maquinas[Integer.parseInt(tempos[k].trim())].adicionarTempoExecucao(j, Integer.parseInt(tempos[k + 1].trim()));
            }
            tarefas[j] = new Tarefa(j);
            i++;
            j++;
        }
        i = i + 2;
        for (int l = 0; l < qtdMaquinas; l++) {
            for (int t = 0; t < qtdTarefas; t++) {
                String[] temposP = line[i].split("\t");
                for (int p = 0; p < temposP.length; p++) {
                    maquinas[l].adicionarTempoPreparacao(t, p, Integer.parseInt(temposP[p].trim()));
                }
                i++;
            }
            i++;
        }
//        
//        for(int l = 0; l < maquinas.length; l++){
//            System.out.println("maquina :"+l);
//            System.out.println("Tempos de execucao:");
//            System.out.println(Arrays.toString(maquinas[l].getTemposExecucao()));
//            System.out.println("Tempos de preparação:");
//            for(int k=0; k < qtdTarefas ; k++){
//                System.out.println(Arrays.toString(maquinas[l].getTemposPreparacao()[k]));
//            }
//            System.out.println("-----------------");
//        }
    }
    
    public static void clonarVetor(Maquina[] sol1, Maquina[] sol2) throws CloneNotSupportedException{
//        sol2 = null;
//        sol2 = new Maquina[sol1.length];
        for(int i =0; i < sol1.length; i++){
            sol2[i]=sol1[i].clone();
        }
    }

    public static void main(String[] args) throws IOException, CloneNotSupportedException {
        // TODO code application logic here
        lerInstacia1("I_250_20_S_1-9_1.txt");
        Metodo m = new Metodo();
        m.Construtivo(maquinas, tarefas);
        m.buscaLocalF1(maquinas);
        m.buscaLocalF2(maquinas);
        m.imprimeSolucao(maquinas,true);
//        m.buscaLocalF1(maquinas);
//        m.imprimeSolucao(maquinas,true);
//        m.buscaLocalF2(maquinas);
//        m.imprimeSolucao(maquinas,true);
//        m.perturbacao(maquinas, 1);
//        m.imprimeSolucao(maquinas,true);
//        m.buscaLocalF1(maquinas);
//        m.imprimeSolucao(maquinas,true);
//        m.buscaLocalF2(maquinas);
//        m.imprimeSolucao(maquinas,true);
        Maquina[] best = new Maquina[maquinas.length];
        clonarVetor(maquinas, best);
        for(int i = 0 ; i < 20; i++){
            System.out.println(i);
            m.perturbacao(maquinas, 1);
            m.buscaLocalF1(maquinas);
            m.buscaLocalF2(maquinas);
            if(m.makeSpan(maquinas) > m.makeSpan(best)){
                clonarVetor(best, maquinas);
            }
            else{
                clonarVetor(maquinas, best);
            }
        }
        m.imprimeSolucao(maquinas,true);
    }

}
