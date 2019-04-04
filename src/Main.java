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
    
    private int litrosRecipiente, c1, c2;
    
    private Scanner scanner = new Scanner(System.in);
    
    private ArrayList<No> nivelAtual = new ArrayList<No>();

    
    private boolean buscando;
    
    public void preencheEstado () {
        
        litrosRecipiente = 0;
        
        litrosRecipiente = scanner.nextInt();
        
        c1 = scanner.nextInt();

        c2 = scanner.nextInt();
        
        No no = new No(c1, c2);
    }
    
    public void iniciaBusca () {
        
        int resultado = 0;
        
        while (buscando) {
            LinkedHashSet<No> filhos = new LinkedHashSet<No>();

            

            for(No nivelAtual : nivelAtual) {
                //iterando pelos operadores disponiveis
                for (int k = 0; k < operadores.size(); k++) {
                    //iterando pelas teclas disponiveis (nao quebradas)
                     for (int j = 0; j < teclas.size(); j++) {
                        switch (operadores.get(k)) {
                            case "+":
                                resultado = objeto.getValor() + teclas.get(j);
                                break;
                            case "-":
                                resultado = objeto.getValor() - teclas.get(j);
                                break;
                            case "*":
                                resultado = objeto.getValor() * teclas.get(j);
                                break;
                            case "/":
                                resultado = objeto.getValor() / teclas.get(j);
                                break;
                        }
                        if (resultado != numeroAlvo) {
                            //Se nao achei o resultado, eu adiciono o resultado nos filhos
                            boolean flag = false;
                            for (int i = 0; i < jaUsados.size(); i++) {
                                if (jaUsados.get(i) == resultado) {
                                    flag = true;
                                }
                            }
                            if (!flag) {
                                No result = new No (resultado);
                                filhos.add(result);
                                jaUsados.add(resultado);
                            }
                           
                        } else {
                            //Se achei o resultado, saio do while
                            buscando = false;
                        }
                    }
                }
            }
            
            //adiciono mais um paço pra chegar no resultado
            numeroOperacoes++;
            
            //zero o nivel antigo pra poder
            nivelAtual.clear();
            
            //Apago o nivel que estava salvo e seto o novo nivel como os atuais filhos que acabei de gerar
            nivelAtual.addAll(filhos);
            

            filhos.clear();
        }
    }
}


class No {
    
    private int baldes[] = new int[2];
    
    private ArrayList<No> filhos;
    
    public No (int c1, int c2) {
        this.baldes[0] = c1;
        this.baldes[1] = c2;
        
        this.filhos = new ArrayList<No>();
    }
    
    public int[] getValor() {
        return this.baldes;
    }
}  

