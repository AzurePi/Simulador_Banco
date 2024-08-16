package trabalho1;

import java.util.ArrayList;

/**
 * @author Azure
 */
public class Conta {
    private int numero;
    private int agencia; //número da agência, e não objeto
    private double saldo;
    private String nome;
    private String endereco;
    private String cpf;
    private String dataNascimento;
    private String senha;
    private ArrayList<String> extrato = new ArrayList<>(); //extrato simples, que lista operações e valores

    public Conta(int numero,
                 int agencia,
                 double saldo,
                 String nome,
                 String endereco,
                 String cpf,
                 String dataNascimento,
                 String senha) {
        this.numero = numero;
        this.agencia = agencia;
        this.saldo = saldo;
        this.nome = nome;
        this.endereco = endereco;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;
        this.senha = senha;
        extrato.add("Saldo inicial: " + saldo);
    }


    //setters & getters--------------------------------------------------------
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public int getAgencia() {
        return agencia;
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSenha() {
        return senha;
    }

    public ArrayList<String> getExtrato() {
        return extrato;
    }

    public void setExtrato(ArrayList<String> extrato) {
        this.extrato = extrato;
    }


    //métodos------------------------------------------------------------------
    /**
     * Deposita um valor na conta
     *
     * @param valor valor sendo depositado na conta
     */
    public void depositar(double valor) {
        saldo += valor;
    }

    /**
     * Saca um valor da conta
     *
     * @param valor valor sendo sacado da conta
     */
    public void sacar(double valor) {
        saldo -= valor;
    }

    /**
     * Valida uma senha fornecida
     *
     * @param senhaComparar senha fornecida
     * @return <code>true</code>, se a senha corresponder à senha cadastrada, <code>false</code> caso contrário
     */
    public boolean validarSenha(String senhaComparar) {
        return senhaComparar.equals(senha);
    }

    /**
     * Troca a senha associada à conta, mediante fornecimento da senha atual
     *
     * @param senhaAtual senha em uso atualmente
     * @param senhaNova  nova senha
     */
    public boolean setSenha(String senhaAtual, String senhaNova) {
        if (validarSenha(senhaAtual)) {
            senha = senhaNova;
            return true;
        }
        return false;
    }

    /**
     * Cria uma entrada na lista <code>extrato</code>, especificando a operação e o valor movimentado
     *
     * @param operacao String com o nome da operação sendo feita
     * @param valor    valor movimentado. Positivo para entradas de dinheiro, negativo para saídas
     */
    public void novaOperacao(String operacao, double valor) {
        String linha = operacao.toUpperCase() + ": ";
        linha += valor;

        extrato.add(linha);
    }

    /**
     * Cria uma entrada na lista <code>extrato</code>, especificando a operação, o valor movimentado, e o destino desse valor
     *
     * @param operacao String com o nome da operação sendo feita
     * @param valor    valor movimentado. Positivo para entradas de dinheiro, negativo para saídas
     * @param destino  String com o nome da pessoa para onde o valor movimentado foi, ou de onde o valor movimentado veio
     */
    public void novaOperacao(String operacao, double valor, String destino) {
        String linha = operacao.toUpperCase() + ": ";
        linha += valor;

        if (valor > 0) //se o valor vier como positivo, é porque entrou na conta vindo de alguém
            linha += " de ";
        if (valor < 0) //se o valor vier como negativo, é porque saiu da conta indo para alguém
            linha += " para ";

        linha += destino;

        extrato.add(linha);
    }
}
