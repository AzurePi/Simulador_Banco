package trabalho1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Azure
 */
public class DisplayBanco {
    private Banco meuBanco;

    public DisplayBanco(String banco, String agencias, String contas) {
        Scanner sc = null;
        String linha;
        String[] campos;

        //leitura de banco.txt -------------------------------------------
        try {
            sc = new Scanner(new File(banco));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DisplayBanco.class.getName()).log(Level.SEVERE, null, ex);
        }

        linha = sc.nextLine();
        campos = linha.split("#"); //divide a linha em campos separados por "#"

        meuBanco = new Banco(campos[0], campos[1], campos[2]);

        sc.close();

        //leitura de agencias.txt -----------------------------------------
        try {
            sc = new Scanner(new File(agencias));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DisplayBanco.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (sc.hasNextLine()) {
            linha = sc.nextLine();
            campos = linha.split("#"); //divide a linha em campos separados por "#"

            meuBanco.cadastrarAgencia(campos[0], Integer.parseInt(campos[1]), campos[2]);
        }
        sc.close();

        //leitura de contas.txt ------------------------------------------
        try {
            sc = new Scanner(new File(contas));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DisplayBanco.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (sc.hasNextLine()) {

            linha = sc.nextLine();
            campos = linha.split("#"); //divide a linha em campos separados por "#"

            meuBanco.cadastrarConta(Integer.parseInt(campos[5]),
                    Integer.parseInt(campos[6]),
                    campos[0],
                    Double.parseDouble(campos[4]),
                    campos[2],
                    campos[3],
                    campos[1],
                    campos[7]);
        }
        sc.close();
    }


    //métodos------------------------------------------------------------------
    /**
     * Cria uma estrutura no console para fazer o login de uma conta
     */
    public void login() {
        Scanner sc = new Scanner(System.in);

        do {
            System.out.println("-------LOGIN-------");
            System.out.println("Agência: ");
            int a = sc.nextInt();
            System.out.println("Número da Conta: ");
            int n = sc.nextInt();
            System.out.println("Senha: ");
            sc.nextLine(); //para lidar com problemas de buffer
            String senha = sc.nextLine();
            meuBanco.logarCliente(a, n, senha);

            System.out.println(); //pula uma linha
        } while (meuBanco.getContaLogada() == null);
    }

    /**
     * Cria uma estrutura de controle para determinar a operação a ser realizada pelo usuário
     */
    public void telaUsuario() {
        int op; //variável para controlar a operação
        int continuar = 1; //variável para controlar a continuidade do programa
        Scanner sc = new Scanner(System.in);

        do {
            login();
            operacaoSaldo();

            do {
                System.out.println(); //pula uma linha
                System.out.println("-------- OPERAÇÕES --------");
                System.out.println("1 - Depósito");
                System.out.println("2 - Saque");
                System.out.println("3 - PIX");
                System.out.println("4 - Transferência");
                System.out.println("5 - Saldo");
                System.out.println("6 - Extrato");
                System.out.println("7 - Alterar senha");
                System.out.println("\n0 - Sair");

                op = sc.nextInt();

                switch (op) {
                    case 1:
                        operacaoDeposito();
                        break;
                    case 2:
                        operacaoSaque();
                        break;
                    case 3:
                        operacaoPix();
                        break;
                    case 4:
                        operacaoTransferencia();
                        break;
                    case 5:
                        operacaoSaldo();
                        break;
                    case 6:
                        operacaoExtrato();
                        break;
                    case 7:
                        alterarSenha();
                        break;
                }
            } while (op != 0);
            operacaoSair();

            System.out.println(); //pula uma linha
            System.out.println("0 - Encerrar o programa");
            System.out.println("1 - Login");
            continuar = sc.nextInt();
            System.out.println(); //pula uma linha
        } while (continuar != 0);
    }

    /**
     * Realiza um depósito para a <code>contaLogada</code>. Pede, no console, o valor a ser depositado
     */
    private void operacaoDeposito() {
        Scanner sc = new Scanner(System.in);

        System.out.println("------ DEPÓSITO ------");
        System.out.println("Valor a depositar: ");
        meuBanco.realizarDeposito(Math.abs(sc.nextDouble()));
    }

    /**
     * Realiza um saque da <code>contaLogada</code>. Pede, no console, o valor a ser sacado
     */
    private void operacaoSaque() {
        Scanner sc = new Scanner(System.in);
        boolean funcionou;

        System.out.println("------ SAQUE ------");
        System.out.println("Valor a sacar: ");
        funcionou = meuBanco.realizarSaque(Math.abs(sc.nextDouble()));

        if (!funcionou)
            System.out.println("ERRO: Valor excede o saldo em conta. Tente novamente");
        else
            System.out.println("Saque realizado com sucesso");
    }

    /**
     * Realiza uma transferência via pix a partir da <code>contaLogada</code>. Pede, no console, o CPF do destinatário
     */
    private void operacaoPix() {
        Scanner sc = new Scanner(System.in);
        String cpf;
        float valor;

        System.out.println("------ PIX ------");
        System.out.println("CPF: ");
        cpf = sc.nextLine();
        System.out.println("Valor: ");
        valor = sc.nextFloat();

        boolean b = meuBanco.pix(cpf, valor);
        if (!b)
            System.out.println("ERRO: Tente novamente");
        else
            System.out.println("PIX realizado com sucesso");
    }

    /**
     * Realiza uma transferência a partir da <code>contaLogada</code>. Pede, no console, os dados da <code>Conta</code> de destino e o valor a ser transferido
     */
    private void operacaoTransferencia() {
        Scanner sc = new Scanner(System.in);
        int agencia, conta;
        float valor;

        System.out.println("------ TRANSFERÊNCIA ------");
        System.out.println("Agência à qual transferir: ");
        agencia = sc.nextInt();
        System.out.println("Conta à qual transferir: ");
        conta = sc.nextInt();
        System.out.println("Valor a transferir: ");
        valor = sc.nextFloat();

        boolean b = meuBanco.transferencia(agencia, conta, valor);
        if (!b)
            System.out.println("ERRO: Verifique os dados e tente novamente");
        else
            System.out.println("Transferência realizada com sucesso");
    }

    /**
     * Imprime na tela o saldo em conta da <code>contaLogada</code>
     */
    private void operacaoSaldo() {
        System.out.println("Saldo em conta: " + meuBanco.getContaLogada().getSaldo());
    }

    private void operacaoExtrato() {
        ArrayList<String> extrato = meuBanco.extrato();

        for (String linha : extrato)
            System.out.println(linha);

        System.out.println("Saldo: " + meuBanco.saldo());
    }

    /**
     * Dá ao usuário a oportunidade de alterar a senha de <code>contaLogada</code> mediante confirmação da senha atual
     */
    private void alterarSenha() {
        Scanner sc = new Scanner(System.in);
        String nova, atual;
        boolean funcionou;

        System.out.println("------ ALTERAÇÃO DE SENHA ------");
        System.out.println("Senha nova: ");
        nova = sc.nextLine();
        System.out.println("Senha atual: ");
        atual = sc.nextLine();

        funcionou = meuBanco.getContaLogada().setSenha(atual, nova);
        if (!funcionou)
            System.out.println("Senha atual incorreta. Alteração cancelada");
        else
            System.out.println("Alteração feita com sucesso");
    }

    /**
     * Desloga a conta logada do meuBanco
     */
    private void operacaoSair() {
        meuBanco.deslogarConta();
        System.out.println("Conta deslogada");
    }
}
