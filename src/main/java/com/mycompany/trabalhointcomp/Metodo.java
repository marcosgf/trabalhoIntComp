/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.trabalhointcomp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 *
 * @author marcos
 */
public class Metodo {

    public void Construtivo(Maquina[] maquinas, Tarefa[] tarefas) {

        int preparacao = 0, execucao, alocaEm = -1, tPreparacao = 0, tExecucao = 0;
        for (Tarefa t : tarefas) {
            int menor = 999999999;
            for (int i = 0; i < maquinas.length; i++) {
                if (maquinas[i].getUltimaTarefaAlocada() == -1) {
                    preparacao = maquinas[i].getTempoDePreparacao(t.getId(), t.getId());
                } else {
                    preparacao = maquinas[i].getTempoDePreparacao(t.getId(), maquinas[i].getUltimaTarefaAlocada());
                }
                execucao = maquinas[i].getTempoDeExecucao(t.getId());
                if (execucao + preparacao < menor) {
                    tPreparacao = preparacao;
                    tExecucao = execucao;
                    menor = execucao + preparacao;
                    alocaEm = i;
                }
            }
            if (maquinas[alocaEm].getUltimaTarefaAlocada() != -1) {
                t.setTarefaAnterior(maquinas[alocaEm].getUltimaTarefaAlocada());
            } else {
                t.setTarefaAnterior(-1);
            }
            t.setTempoExecucao(tExecucao);
            t.setTempoPreparo(tPreparacao);
            t.setTempoTotal(menor);
            maquinas[alocaEm].alocarTarefa(t);
        }

    }

    public int makeSpan(Maquina[] maquinas) {
        int maior = 0;
        for (int i = 0; i < maquinas.length; i++) {
            if (maquinas[i].calculaCusto() > maior) {
                maior = maquinas[i].calculaCusto();
            }
        }
        return maior;
    }

    private void trocaTarefas(Maquina m1, Maquina m2, Tarefa t1, Tarefa t2) {
        int anterior2 = t2.getTarefaAnterior(), anterior1 = t1.getTarefaAnterior();

        t1.setTempoPreparo(m2.getTempoDePreparacao(t1.getId(), anterior2));
        t1.setTarefaAnterior(anterior2);
        t1.setTempoExecucao(m2.getTempoDeExecucao(t1.getId()));
        t1.setTempoTotal(t1.getTempoPreparo() + t1.getTempoExecucao());
        m2.getAlocadas().set(m2.getIndexTarefaAlocada(t2.getId()), t1);

        t2.setTempoPreparo(m1.getTempoDePreparacao(t2.getId(), anterior1));
        t2.setTarefaAnterior(anterior1);
        t2.setTempoExecucao(m1.getTempoDeExecucao(t2.getId()));
        t2.setTempoTotal(t2.getTempoPreparo() + t2.getTempoExecucao());
        m1.getAlocadas().set(m1.getIndexTarefaAlocada(t1.getId()), t2);

        int i, j;
        for (i = 0; i < m1.getAlocadas().size(); i++) {
            if (m1.getAlocadas().get(i).getId() == t2.getId()) {
                break;
            }
        }
        if (i != m1.getAlocadas().size() - 1) {
            Tarefa aux1 = m1.getAlocadas().get(i + 1);
            aux1.setTempoPreparo(m1.getTempoDePreparacao(aux1.getId(), t2.getId()));
            aux1.setTarefaAnterior(t2.getId());
            aux1.setTempoTotal(aux1.getTempoExecucao() + aux1.getTempoPreparo());
        }

        for (j = 0; j < m2.getAlocadas().size(); j++) {
            if (m2.getAlocadas().get(j).getId() == t1.getId()) {
                break;
            }
        }
        if (j != m2.getAlocadas().size() - 1) {
            Tarefa aux2 = m2.getAlocadas().get(j + 1);
            aux2.setTempoPreparo(m2.getTempoDePreparacao(aux2.getId(), t1.getId()));
            aux2.setTarefaAnterior(t1.getId());
            aux2.setTempoTotal(aux2.getTempoExecucao() + aux2.getTempoPreparo());
        }

    }

    private void realocaTarefa(Maquina mMais, Maquina mMenos, Tarefa t, Tarefa tAlterada) {
        if (mMais.getAlocadas().size() != 1) {
            if (mMais.getIndexTarefaAlocada(t.getId()) != mMais.getAlocadas().size() - 1) {
                int idAlterado = mMais.getIndexTarefaAlocada(t.getId()) + 1;
                mMais.getAlocadas().get(idAlterado).setTempoPreparo(mMais.getTempoDePreparacao(idAlterado, t.getTarefaAnterior()));
                mMais.getAlocadas().get(idAlterado).setTarefaAnterior(t.getTarefaAnterior());
                mMais.getAlocadas().get(idAlterado).setTempoTotal(mMais.getAlocadas().get(idAlterado).getTempoExecucao() + mMais.getAlocadas().get(idAlterado).getTempoPreparo());
            }
        }
        mMais.getAlocadas().remove(t);

        t.setTempoPreparo(mMenos.getTempoDePreparacao(t.getId(), tAlterada.getTarefaAnterior()));
        t.setTarefaAnterior(tAlterada.getTarefaAnterior());
        t.setTempoExecucao(mMenos.getTempoDeExecucao(t.getId()));
        t.setTempoTotal(t.getTempoPreparo() + t.getTempoExecucao());

        tAlterada.setTempoPreparo(mMenos.getTempoDePreparacao(tAlterada.getId(), t.getId()));
        tAlterada.setTarefaAnterior(t.getId());
        tAlterada.setTempoTotal(tAlterada.getTempoExecucao() + tAlterada.getTempoPreparo());

        mMenos.getAlocadas().add(mMenos.getIndexTarefaAlocada(tAlterada.getId()), t);
    }

    public void imprimeSolucao(Maquina[] maquinas) {
        for (int i = 0; i < maquinas.length; i++) {
            System.out.print("-------------------->Maquina " + maquinas[i].getId() + ": ");
            for (int j = 0; j < maquinas[i].getAlocadas().size(); j++) {
                maquinas[i].getAlocadas().get(j).imprimeTarefa();
            }
            System.out.println("");
        }
        System.out.println("makeSpan: " + makeSpan(maquinas));
        System.out.println("");
    }

    public void imprimeSolucao(Maquina[] maquinas, Boolean t) {
        System.out.println("------------------------");
        for (int i = 0; i < maquinas.length; i++) {
            System.out.println("Maquina " + maquinas[i].getId() + ": ");
            System.out.print("tarefas: ");
            for (int j = 0; j < maquinas[i].getAlocadas().size(); j++) {
                System.out.print(maquinas[i].getAlocadas().get(j).getId() + " ");
            }
            System.out.println("");
            System.out.println("custo: " + maquinas[i].calculaCusto());
            System.out.println("");
        }
        System.out.println("makeSpan: " + makeSpan(maquinas));
        System.out.println("");
    }

    public void buscaLocalF1(Maquina[] solucao) {

        int makeSpan = makeSpan(solucao);
        for (int i = solucao.length - 1; i > 0; i--) {
            Arrays.sort(solucao);
            for (int j = 0; j < solucao[0].getAlocadas().size(); j++) {
                for (int k = 0; k < solucao[i].getAlocadas().size(); k++) {
                    //System.out.println("1");
//                    System.out.println("m1: "+solucao[0].getId()+" m2: "+solucao[i].getId()+" i1: "+j +" i2: "+k);
                    trocaTarefas(solucao[0], solucao[i], solucao[0].getAlocadas().get(j), solucao[i].getAlocadas().get(k));
                    if (makeSpan(solucao) <= makeSpan) {
                        makeSpan = makeSpan(solucao);
                    } else {
                        trocaTarefas(solucao[0], solucao[i], solucao[0].getAlocadas().get(j), solucao[i].getAlocadas().get(k));
                    }
                }
            }
        }
        Boolean ver = false;
        makeSpan = makeSpan(solucao);
        for (int i = solucao.length - 1; i > 0; i--) {
            Arrays.sort(solucao);
            for (int j = 0; j < solucao[0].getAlocadas().size()-1; j++) {
                for (int k = 0; k < solucao[i].getAlocadas().size(); k++) {
                    //System.out.println("2");
                    if (solucao[i].getAlocadas().size() == 0 || j == solucao[0].getAlocadas().size() || k == solucao[i].getAlocadas().size()) {
                        break;
                    }
                    realocaTarefa(solucao[0], solucao[i], solucao[0].getAlocadas().get(j), solucao[i].getAlocadas().get(k));
                    if (makeSpan(solucao) <= makeSpan) {
                        makeSpan = makeSpan(solucao);
                        ver = true;
                        break;
                    } else {
                        realocaTarefa(solucao[i], solucao[0], solucao[i].getAlocadas().get(k), solucao[0].getAlocadas().get(j));
                    }
                }
                if (solucao[i].getAlocadas().size() == 0 || j == solucao[0].getAlocadas().size()) {
                    break;
                }
                if (ver) {
                    i++;
                    ver = false;
                    break;
                }
            }
        }

    }

    public void buscaLocalF1Antiga(Maquina[] sol) {
        Arrays.sort(sol);
        //Maquina[] maquinas = sol.clone();
        int makeSpan = makeSpan(sol);
        for (int i = sol.length - 1; i > 1; i--) {

            for (int j = 0; j < sol[0].getAlocadas().size(); j++) {
                for (int k = 0; k < sol[i].getAlocadas().size(); k++) {
                    if (sol[0].getAlocadas().size() == 0) {
                        break;
                    }
//                    System.out.println("solucao anterior:");
//                    imprimeSolucao(sol);
                    if (k == sol[i].getAlocadas().size() || j == sol[i].getAlocadas().size() || k == sol[0].getAlocadas().size() || j == sol[0].getAlocadas().size()) {
                        break;
                    }
                    trocaTarefas(sol[0], sol[i], sol[0].getAlocadas().get(j), sol[i].getAlocadas().get(k));
//                    System.out.println("Nova solucao TROCA:");
//                    imprimeSolucao(sol);
                    if (makeSpan(sol) <= makeSpan) {
                        //maquinas = sol.clone();
                        makeSpan = makeSpan(sol);
                        //Arrays.sort(sol);
                        //continue mudou;
                    } else {
                        if (k == sol[i].getAlocadas().size() || j == sol[i].getAlocadas().size() || k == sol[0].getAlocadas().size() || j == sol[0].getAlocadas().size()) {
                            break;
                        }
                        trocaTarefas(sol[i], sol[0], sol[i].getAlocadas().get(k), sol[0].getAlocadas().get(j));
                        //sol = maquinas.clone();
//                        System.out.println("solucao anterior:");
//                        imprimeSolucao(sol);
//                        if(k == sol[i].getAlocadas().size() || j == sol[i].getAlocadas().size() || k == sol[0].getAlocadas().size() || j == sol[0].getAlocadas().size() )break;
//                        realocaTarefa(sol[0], sol[i], sol[0].getAlocadas().get(j), sol[i].getAlocadas().get(k));
////                        System.out.println("Nova solucao REALOCA:");
////                        imprimeSolucao(sol);
//                        if (makeSpan(sol) <= makeSpan) {
//                            //maquinas = sol.clone();
//                            makeSpan = makeSpan(sol);
//                            //Arrays.sort(sol);
//                          // continue mudou;
//                        } else {
//                            //sol = maquinas.clone();
//                            if(k == sol[i].getAlocadas().size() || j == sol[i].getAlocadas().size() || k == sol[0].getAlocadas().size() || j == sol[0].getAlocadas().size() )break;
//                            realocaTarefa(sol[i], sol[0], sol[i].getAlocadas().get(k), sol[0].getAlocadas().get(j));
//                        }
                    }
                }
            }
            Arrays.sort(sol);
        }
    }

    public void trocaTarefas(Maquina m, Tarefa t1, Tarefa t2) throws CloneNotSupportedException {
        Tarefa aux = t1.clone();

        t1.setTempoPreparo(m.getTempoDePreparacao(t1.getId(), t2.getTarefaAnterior()));
        if (t2.getTarefaAnterior() == t1.getId()) {
            t1.setTarefaAnterior(t2.getId());
        } else {
            t1.setTarefaAnterior(t2.getTarefaAnterior());
        }

        t1.setTempoTotal(t1.getTempoPreparo() + t1.getTempoExecucao());

        if (m.getAlocadas().size() - 1 != m.getIndexTarefaAlocada(t2.getId())) {
            int prox1 = m.getIndexTarefaAlocada(t2.getId()) + 1;
            Tarefa px1 = m.getAlocadas().get(prox1);
            if (px1.getId() != t2.getId()) {
                px1.setTempoPreparo(m.getTempoDePreparacao(px1.getId(), t1.getId()));
                px1.setTarefaAnterior(t1.getId());
                px1.setTempoTotal(px1.getTempoPreparo() + px1.getTempoExecucao());
            }
        }

        t2.setTempoPreparo(m.getTempoDePreparacao(t2.getId(), aux.getTarefaAnterior()));
        t2.setTarefaAnterior(aux.getTarefaAnterior());
        t2.setTempoTotal(t2.getTempoPreparo() + t2.getTempoExecucao());

        if (m.getAlocadas().size() - 1 != m.getIndexTarefaAlocada(aux.getId())) {
            int prox1 = m.getIndexTarefaAlocada(aux.getId()) + 1;
            Tarefa px2 = m.getAlocadas().get(prox1);
            if (px2.getId() != t2.getId()) {
                px2.setTempoPreparo(m.getTempoDePreparacao(px2.getId(), t2.getId()));
                px2.setTarefaAnterior(t2.getId());
                px2.setTempoTotal(px2.getTempoPreparo() + px2.getTempoExecucao());
            }
        }

        Collections.swap(m.getAlocadas(), m.getIndexTarefaAlocada(t1.getId()), m.getIndexTarefaAlocada(t2.getId()));
    }

    public void buscaLocalF2(Maquina[] sol) throws CloneNotSupportedException {
        Arrays.sort(sol);
        //Maquina[] maquinas = sol.clone();
        int makeSpan = makeSpan(sol);
        for (int i = 0; i < (int) ((int) sol.length * 0.3) + 1; i++) {
            int tempo = sol[i].calculaCusto();
            for (int j = 0; j < sol[i].getAlocadas().size(); j++) {
                for (int k = j + 1; k < sol[i].getAlocadas().size(); k++) {
                    trocaTarefas(sol[i], sol[i].getAlocadas().get(j), sol[i].getAlocadas().get(k));
                    //imprimeSolucao(sol, true);
                    if ((makeSpan(sol) < makeSpan) || ((makeSpan(sol) <= makeSpan) && (tempo < sol[i].calculaCusto()))) {
                        //maquinas = sol.clone();
                        makeSpan = makeSpan(sol);
                    } else {
                        trocaTarefas(sol[i], sol[i].getAlocadas().get(j), sol[i].getAlocadas().get(k));
                    }
                }
            }
        }
    }

    public void perturbacao(Maquina[] solucao, int forca) {
       try
       {
            Random gerador = new Random();
            for (int i = 0; i < forca; i++) {
            
            int m1 = gerador.nextInt(solucao.length);
            int t = gerador.nextInt(solucao[m1].getAlocadas().size());
            int m2 = gerador.nextInt(solucao.length);
            while (m2 == m1) {
                m2 = gerador.nextInt(solucao.length);
            }
            //System.out.println("m1: "+solucao[m1].getId()+" m2: "+solucao[m2].getId()+" t: "+solucao[m1].getAlocadas().get(t).getId());
            int custo = solucao[m2].calculaCusto();
            int j = gerador.nextInt(solucao[m2].getAlocadas().size()); 
            realocaTarefa(solucao[m1], solucao[m2], solucao[m1].getAlocadas().get(t), solucao[m2].getAlocadas().get(j));
            //System.out.println("makespan: "+makeSpan(solucao));
        }
       }catch(Exception e){}
    }

}
