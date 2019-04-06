import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Scanner;

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

    LinkedHashSet<String> jaUsados = new LinkedHashSet<>();

    private boolean buscando = true;
    
    public void preencheEstado () {
        
        this.objetivoC2 = scanner.nextInt();
        
        this.c1 = scanner.nextInt();

        this.c2 = scanner.nextInt();
        
        No no = new No(0, 0);
        
        nivelAtual.add(no);
        
//        No no2 = new No(0, c2);
//        
//        nivelAtual.add(no2);
//        
//        jaUsados.add(Integer.toString(c1)+",0");
//                
//        jaUsados.add("0,"+Integer.toString(c2));

        iniciaBusca();
        
        System.out.println("Num op: "+numeroOperacoes);
    }
    
    public void iniciaBusca () {
        //3
//15
//4
        int resultado = 0, balde1 = 0, balde2 = 0;
        int vetorAuxiliar[] = new int[2];
        int baldes[] = new int[2];
        while (buscando) {
            
//            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ começando a avaliacao do seguinte nivelAtual: ************");
//            for(No nivelAtual : nivelAtual) {
//                System.out.println("balde1 do nivel atual "+nivelAtual.getValor()[0]);
//                System.out.println("balde2 do nivel atua "+nivelAtual.getValor()[1]);
//            }
            
            ArrayList<No> filhos = new ArrayList<>();

            //iterando pelos nós do nível atual
            for(No nivelAtual : nivelAtual) {
                //iterando pelas acoes que podem ser feitas com os baldes
//                System.out.println("******************** to no nó "+nivelAtual.getValor()[0]+" e "+nivelAtual.getValor()[1]+" ********************");
                
                for (int i = 0; i < 6; i++) {
                    switch (i) {
                        case 0:
                            if (balde1 != this.c1) {
                                balde1 = encheC1();
                                balde2 = nivelAtual.getValor()[1];
//                                System.out.println("\nenchendo c1\n");
                            } else {
                                balde1 = nivelAtual.getValor()[0];
                                balde2 = nivelAtual.getValor()[1];
                            }
                            break;
                        case 1:
                            if (balde2 != this.c2) {
                                balde2 = encheC2();
                                balde1 = nivelAtual.getValor()[0];
//                                System.out.println("\nenchendo c2\n");
                            }   else {
                                balde1 = nivelAtual.getValor()[0];
                                balde2 = nivelAtual.getValor()[1];
                            }
                            break;
                        case 2:
                            vetorAuxiliar = transfereConteudoC1C2(nivelAtual.getValor()[0], nivelAtual.getValor()[1]);
                            balde1 = vetorAuxiliar[0];
                            balde2 = vetorAuxiliar[1];
                            break;
                        case 3:
                            vetorAuxiliar = transfereConteudoC2C1(nivelAtual.getValor()[0], nivelAtual.getValor()[1]);
                            balde1 = vetorAuxiliar[0];
                            balde2 = vetorAuxiliar[1];
                            break;
                        case 4: 
                            // jogando agua do balde1 fora
                            balde1 = 0;
                            balde2 = nivelAtual.getValor()[1];
//                            System.out.println("\nesvaziando c1\n");
                            break;
                        case 5:
                            // jogando agua do balde2 fora
                            balde1 = nivelAtual.getValor()[0];
                            balde2 = 0;
//                            System.out.println("\nesvaziando c2\n");
                            break;

                    }
                    baldes[0] = balde1;
                    baldes[1] = balde2;
                    if ((balde1 == 0) && (balde2 == objetivoC2)) {
                        buscando = false;
                    } else {
                        //System.out.println("Adicionando os seguintes valores, se possivel: "+baldes[0]+" e "+baldes[1]);
                        if (jaUsados.add(Integer.toString(baldes[0])+","+Integer.toString(baldes[1]))) {
                            //System.out.println("Ok, foi possivel. Adicionando "+baldes[0]+" e "+baldes[1]);
                            No result = new No (balde1, balde2);
                            //System.out.println("Adicionando "+result.getValor()[0]+" e "+result.getValor()[1]);
                            filhos.add(result);
                            No teste = this;
                            //System.out.println("filhos depois de eu adicionar esses baldes ditos acima");
                            //System.out.println("size do filhos "+filhos.size());
//                            for (int j = 0; j < filhos.size(); j++) {
//                                System.out.println("filho "+j);
//                                System.out.println("filhos "+filhos.get(j).getValor()[0]);
//                                System.out.println("filhos "+filhos.get(j).getValor()[1]);
//                            }
                
                        } 
//                        else {
//                            System.out.println("Nao foi possivel porque ja tinha");
//                        }
                    }
                }
                
//                System.out.println("********************** filhos desse nó");
//                for (int i = 0; i < filhos.size(); i++) {
//                    System.out.println("filho "+i);
//                    System.out.println("filhos "+filhos.get(i).getValor()[0]);
//                    System.out.println("filhos "+filhos.get(i).getValor()[1]);
//
//                }
                
            }
            
//            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ imprimindo os filhos depois de gerar todos");
//            for (int i = 0; i < filhos.size(); i++) {
//                System.out.println("filho "+i);
//                System.out.println("balde1 dos filhos "+filhos.get(i).getValor()[0]);
//                System.out.println("balde2 dos filhos "+filhos.get(i).getValor()[1]);
//
//            }
            //adiciono mais um passo pra chegar no resultado
            numeroOperacoes++;
            
            //zero o nivel antigo para gerar um novo nivel
            nivelAtual.clear();
            
            //Seto o novo nivel como os atuais filhos que acabei de gerar
            nivelAtual.addAll(filhos);
            
            filhos.clear();
            

        }
    }

    public int encheC1 () {
        return this.c1;
    }
    
    public int encheC2 () {
        return this.c2;
    }
    
    public int[] transfereConteudoC1C2 (int balde1, int balde2) {
//                System.out.println("\n");

//        System.out.println("Antes de transferir conteudo c1 para c2. Balde 1: "+balde1+" Balde 2: "+balde2);
        
        int vetor[] = new int[2];
        int diferenca;
        
        balde2 = balde2+balde1;
//        System.out.println("balde2 "+balde2);
                
        balde1 = 0;
        
        diferenca =  balde2-this.c2;
        
//        System.out.println("diferenca "+diferenca);
//        
//        System.out.println("balde2 "+balde2+" this.c2 "+this.c2);
        
        if (balde2 > this.c2) {
            balde2 = balde2 - diferenca;
            balde1 = diferenca;
        }
        
        vetor[0] = balde1;
        vetor[1] = balde2;
        
//        System.out.println("Depois de transferir conteudo c1 para c2. Balde 1: "+balde1+" Balde 2: "+balde2);

//                System.out.println("\n");

       return vetor;
    }
    
    public int[] transfereConteudoC2C1 (int balde1, int balde2) {
//        System.out.println("\n");
//        
//        System.out.println("Antes de transferir conteudo c2 para c1. Balde 1: "+balde1+" Balde 2: "+balde2);
        
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
        
//        System.out.println("Depois de transferir conteudo c2 para c1. Balde 1: "+balde1+" Balde 2: "+balde2);
//        System.out.println("\n");

       return vetor;
    }
    
}


class No {
    
    private int baldes[] = new int[2];
    private ArrayList<No> filhos;
    private No pai;
    
    public No (int c1, int c2) {
        this.baldes[0] = c1;
        this.baldes[1] = c2;
        this.pai = null;
        
        this.filhos = new ArrayList<No>();
    }
    
    public int[] getValor() {
        return this.baldes;
    }
}  

