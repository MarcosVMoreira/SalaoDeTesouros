import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author Marcos
 */

class Main {
    public static void main(String[] args) {
        
        EstadoCalculadora calculadora = new EstadoCalculadora();
        calculadora.preencheEstado();
        
    }
} 

class EstadoCalculadora {
        
    private int c1, c2, objetivoC2 = 0, numeroOperacoes = 0;
    
    private Scanner scanner = new Scanner(System.in);
    
    private ArrayList<No> nivelAtual = new ArrayList<No>();

    LinkedHashSet<String> jaUsados = new LinkedHashSet<String>();

    private boolean buscando = true;
    
    public void preencheEstado () {
        
        this.objetivoC2 = scanner.nextInt();
        
        this.c1 = scanner.nextInt();

        this.c2 = scanner.nextInt();
        
        No no = new No(0, 0);
        
        nivelAtual.add(no);

        No solucao = iniciaBusca();
        
        Stack<No> passos = new Stack<No>();
        while(solucao != null) {
            passos.push(solucao);
            solucao = solucao.getPai();
        }
         
        int numPassos = passos.size();
        passos.pop();
        System.out.println(numeroOperacoes+" acoes:");
        for(int i = 0; i < numPassos-1; ++i) {
            System.out.print(i+1+") ");passos.pop().mostrarMensagem();
        }
    }
    
    public No iniciaBusca () {
        int resultado = 0, balde1 = 0, balde2 = 0;
        int vetorAuxiliar[] = new int[2];
        int baldes[] = new int[2];
        
        while (buscando) {
            ArrayList<No> filhos = new ArrayList<No>();
            
            String str = null;

            //iterando pelos nós do nível atual
            for(No nivelAtual : nivelAtual) {
                //iterando pelas acoes que podem ser feitas com os baldes
                for (int i = 0; i < 6; i++) {
                    switch (i) {
                        case 0:
                            str = "Encher o recipiente 0;";
                            
                            if (balde1 != this.c1) {
                                balde1 = encheC1();
                                balde2 = nivelAtual.getValor()[1];
                            } else {
                                balde1 = nivelAtual.getValor()[0];
                                balde2 = nivelAtual.getValor()[1];
                            }
                            break;
                        case 1:
                            str = "Encher o recipiente 1;";

                            if (balde2 != this.c2) {
                                balde2 = encheC2();
                                balde1 = nivelAtual.getValor()[0];
                            }   else {
                                balde1 = nivelAtual.getValor()[0];
                                balde2 = nivelAtual.getValor()[1];
                            }
                            break;
                        case 2:
                            str = "Transferir do recipiente 0 para o recipiente 1;";
                             
                            vetorAuxiliar = transfereConteudoC1C2(nivelAtual.getValor()[0], nivelAtual.getValor()[1]);
                            balde1 = vetorAuxiliar[0];
                            balde2 = vetorAuxiliar[1];
                            break;
                        case 3:
                            str = "Transferir do recipiente 1 para o recipiente 0;";
                            
                            vetorAuxiliar = transfereConteudoC2C1(nivelAtual.getValor()[0], nivelAtual.getValor()[1]);
                            balde1 = vetorAuxiliar[0];
                            balde2 = vetorAuxiliar[1];
                            break;
                        case 4: 
                             str = "Remover agua do recipiente 0;";
                            
                            // jogando agua do balde1 fora
                            balde1 = 0;
                            balde2 = nivelAtual.getValor()[1];
                            break;
                        case 5:
                            str = "Remover agua do recipiente 1;";

                            balde1 = nivelAtual.getValor()[0];
                            balde2 = 0;
                            break;

                    }
                    baldes[0] = balde1;
                    baldes[1] = balde2;
                    if ((balde1 == 0) && (balde2 == objetivoC2)) {
                        buscando = false;
                        numeroOperacoes++;
                        No solucao = new No (balde1, balde2);
                        solucao.setarMensagem(str);
                        nivelAtual.addFilho(solucao);
                        return solucao;
                        
                    } else {
                        if (jaUsados.add(Integer.toString(baldes[0])+","+Integer.toString(baldes[1]))) {
                            
                            No result = new No (balde1, balde2);
                            result.setarMensagem(str);
                            
                            filhos.add(result);
                            nivelAtual.addFilho(result);
                
                        } 
                    }
                }
            }
            //adiciono mais um passo pra chegar no resultado
            numeroOperacoes++;
            
            //zero o nivel antigo para gerar um novo nivel
            nivelAtual.clear();
            
            //Seto o novo nivel como os atuais filhos que acabei de gerar
            nivelAtual.addAll(filhos);
            
            filhos.clear();
        }
        return null;
    }

    public int encheC1 () {
        return this.c1;
    }
    
    public int encheC2 () {
        return this.c2;
    }
    
    public int[] transfereConteudoC1C2 (int balde1, int balde2) {
        
        int vetor[] = new int[2];
        int diferenca;
        
        balde2 = balde2+balde1;
                
        balde1 = 0;
        
        diferenca =  balde2-this.c2;
        
        if (balde2 > this.c2) {
            balde2 = balde2 - diferenca;
            balde1 = diferenca;
        }
        
        vetor[0] = balde1;
        vetor[1] = balde2;
        
       return vetor;
    }
    
    public int[] transfereConteudoC2C1 (int balde1, int balde2) {
        
        int vetor[] = new int[2];
        int diferenca;
        
        balde1 = balde1+balde2;
        balde2 = 0;
        
        diferenca =  balde1-this.c1;
        
        if (balde1 > this.c1) {
            balde1 = balde1 - diferenca;
            balde2 = diferenca;
        }
        
        vetor[0] = balde1;
        vetor[1] = balde2;

       return vetor;
    }
    
}


class No {
    
    private int baldes[] = new int[2];
    private ArrayList<No> filhos;
    private No pai;
    String msgAtual;

    
    public No (int c1, int c2) {
        this.baldes[0] = c1;
        this.baldes[1] = c2;
        this.pai = null;
        
        this.filhos = new ArrayList<No>();
    }
    
    public int[] getValor() {
        return this.baldes;
    }
    
    public void addFilho(No filho) {
        No pai = this;
        filho.pai = this;
        this.filhos.add(filho);
    }
    
    public No getPai() {
        return pai;
    }
    
    public void mostrarMensagem() {
        System.out.println(msgAtual);
    }
    
    public void setarMensagem (String msg) {
        this.msgAtual = msg;
    }
    
}  

