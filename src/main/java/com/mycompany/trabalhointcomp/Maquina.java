/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.trabalhointcomp;

import java.util.ArrayList;

/**
 *
 * @author marcos
 */
public class Maquina implements Comparable<Maquina>, Cloneable{

    private String id;
    private int[] temposExecucao;
    private int[][] temposPreparacao;
    private ArrayList<Tarefa> alocadas;
    Integer tempoTotal;

    public Maquina(int qtdTarefas, String id) {
        this.id = id;
        this.temposExecucao = new int[qtdTarefas];
        this.temposPreparacao = new int[qtdTarefas][qtdTarefas];
        this.alocadas = new ArrayList<>();
        this.tempoTotal = 0;
    }

    @Override
    public int compareTo(Maquina o) {
        return -this.tempoTotal.compareTo(o.tempoTotal);//ordena decrescente
    }
    
    @Override
    public Maquina clone() throws CloneNotSupportedException {
        try {
            return (Maquina)super.clone();
        }
        catch (CloneNotSupportedException e) {
            // This should never happen
            throw new InternalError(e.toString());
        }
    }

    public int getUltimaTarefaAlocada() {
        if (this.alocadas.size() != 0) {
            int i;
            for (i = 0; i < this.alocadas.size(); i++);
            return this.alocadas.get(i - 1).getId();
        } else {
            return -1;
        }
    }

    public int calculaCusto() {
        int soma = 0;
        for (int i = 0; i < this.alocadas.size(); i++) {
            soma = soma + this.alocadas.get(i).getTempoTotal();
        }
        tempoTotal = soma;
        return soma;
    }

    public void alocarTarefa(Tarefa t) {
        this.alocadas.add(t);
        this.tempoTotal = this.tempoTotal + t.getTempoTotal();
    }
    
    public int getIndexTarefaAlocada(int id){
        for(int i = 0 ; i < alocadas.size(); i++){
            if(alocadas.get(i).getId() == id){
                return i;
            }
        }
        return -1;
    }

    public void adicionarTempoExecucao(int tarefa, int tempo) {
        this.temposExecucao[tarefa] = tempo;
    }

    public void adicionarTempoPreparacao(int tarefaI, int tarefa_anterior, int tempo) {
        this.temposPreparacao[tarefaI][tarefa_anterior] = tempo;
    }

    public int getTempoDePreparacao(int tarefa, int anterior) {
        if(anterior == -1) anterior = tarefa;
        return this.temposPreparacao[tarefa][anterior];
    }

    public int getTempoDeExecucao(int tarefa) {
        return this.temposExecucao[tarefa];
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the temposExecucao
     */
    public int[] getTemposExecucao() {
        return temposExecucao;
    }

    /**
     * @return the temposPreparacao
     */
    public int[][] getTemposPreparacao() {
        return temposPreparacao;
    }

    /**
     * @return the alocadas
     */
    public ArrayList<Tarefa> getAlocadas() {
        return alocadas;
    }

    /**
     * @return the tempoTotal
     */
    public int getTempoTotal() {
        return tempoTotal;
    }

}
