/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.trabalhointcomp;

/**
 *
 * @author marcos
 */
public class Tarefa implements Cloneable{
    private int id;
    private int tempoExecucao;
    private int tempoPreparo;
    private int tarefaAnterior; // -1 caso seja a primeira
    private int tempoTotal;
    
    @Override
    public Tarefa clone() throws CloneNotSupportedException {
        return (Tarefa) super.clone();
    }
    
    public Tarefa(int id){
        this.id = id;
    }

    public void imprimeTarefa(){
        System.out.println("id: "+id);
        System.out.println("execucao: "+tempoExecucao);
        System.out.println("preparo: "+tempoPreparo);
        System.out.println("anterior: "+tarefaAnterior);
        System.out.println("total: "+tempoTotal);
        System.out.println("");
    }
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the tempoExecucao
     */
    public int getTempoExecucao() {
        return tempoExecucao;
    }

    /**
     * @param tempoExecucao the tempoExecucao to set
     */
    public void setTempoExecucao(int tempoExecucao) {
        this.tempoExecucao = tempoExecucao;
    }

    /**
     * @return the tempoPreparo
     */
    public int getTempoPreparo() {
        return tempoPreparo;
    }

    /**
     * @param tempoPreparo the tempoPreparo to set
     */
    public void setTempoPreparo(int tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    }

    /**
     * @return the tarefaAnterior
     */
    public int getTarefaAnterior() {
        return tarefaAnterior;
    }

    /**
     * @param tarefaAnterior the tarefaAnterior to set
     */
    public void setTarefaAnterior(int tarefaAnterior) {
        this.tarefaAnterior = tarefaAnterior;
    }

    /**
     * @return the tempoTotal
     */
    public int getTempoTotal() {
        return tempoTotal;
    }

    /**
     * @param tempoTotal the tempoTotal to set
     */
    public void setTempoTotal(int tempoTotal) {
        this.tempoTotal = tempoTotal;
    }
    
    
    
    
}
