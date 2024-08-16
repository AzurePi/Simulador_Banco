package trabalho1;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Azure
 */
public class Banco {
    private String nome;
    private String cnpj;
    private String endereco;
    private Conta contaLogada;
    private ArrayList<Agencia> agencias = new ArrayList<>();

    public Banco(String nome, String cnpj, String endereco) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.endereco = endereco;
    }


    //setters & getters--------------------------------------------------------
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public Conta getContaLogada() {
        return contaLogada;
    }

    public void setContaLogada(Conta contaLogada) {
        this.contaLogada = contaLogada;
    }

    public ArrayList<Agencia> getAgencias() {
        return agencias;
    }

    public void setAgencias(ArrayList<Agencia> agencias) {
        this.agencias = agencias;
    }


    //métodos------------------------------------------------------------------
    /**
     * Atualiza <code>contaLogada</code> para se referir a uma conta específica. <code>contaLogada</code> aponta para null se os parâmetros não corresponderem a uma conta
     *
     * @param numAgencia número da <code>Agencia</code> do cliente
     * @param numConta   número da <code>Conta</code> do cliente
     * @param senha      senha do cliente
     */
    public void logarCliente(int numAgencia, int numConta, String senha) {
        Agencia auxAgencia = buscarAgencia(numAgencia);

        if (auxAgencia != null) {
            contaLogada = auxAgencia.buscarConta(numConta, senha);
            if (contaLogada == null)
                System.out.println("Conta não encontrada ou senha incorreta. Tente Novamente\n");
        } else
            System.out.println("Agência não encontrada. Tente Novamente\n");
    }

    /**
     * Realiza um saque para <code>contaLogada</code>
     *
     * @param valor valor a ser sacado
     * @return <code>true</code>, se o saque for possível, <code>falso</code> caso contrário
     */
    public boolean realizarSaque(double valor) {
        if (valor > contaLogada.getSaldo())
            return false;

        contaLogada.sacar(valor);
        contaLogada.novaOperacao("Saque", -valor);
        return true;
    }

    /**
     * Realiza um depósito em <code>contaLogada</code>
     *
     * @param valor valor a ser depositado
     */
    public void realizarDeposito(double valor) {
        contaLogada.depositar(valor);
        contaLogada.novaOperacao("Depósito", valor);
    }

    /**
     * @return valor do saldo em <code>contaLogada</code>
     */
    public double saldo() {
        return contaLogada.getSaldo();
    }

    public ArrayList<String> extrato() {
        return contaLogada.getExtrato();
    }

    /**
     * Cadastra uma <code>Agencia</code> no banco a partir dos parâmetros
     *
     * @param nome     nome da nova <code>Agencia</code>
     * @param codigo   nódigo da nova <code>Agencia</code>
     * @param endereco endereço da nova <code>Agencia</code>
     */
    public void cadastrarAgencia(String nome, int codigo, String endereco) {
        agencias.add(new Agencia(nome, codigo, endereco));
    }

    /**
     * Cadastra uma <code>Agencia</code> no <code>Banco</code> a partir de uma <code>Agencia</code> pré-existente
     *
     * @param agencia <code>Agencia</code> sendo cadastrada
     */
    public void cadastrarAgencia(Agencia agencia) {
        agencias.add(agencia);
    }

    /**
     * Cadastra uma nova <code>Conta</code> no <code>Banco</code> com base nos parâmetros
     *
     * @param numAgencia     Número da <code>Agencia</code>
     * @param numero         Número da <code>Conta</code>
     * @param nome           Nome do dono da <code>Conta</code>
     * @param saldo          Saldo na <code>Conta</code>
     * @param endereco       Endereço do dono da <code>Conta</code>
     * @param cpf            CPF do dono da <code>Conta</code>
     * @param dataNascimento Data de nascimento do dono da <code>Conta</code>
     * @param senha          Senha da <code>Conta</code>
     */
    public void cadastrarConta(int numAgencia,
                               int numero,
                               String nome,
                               double saldo,
                               String endereco,
                               String cpf,
                               String dataNascimento,
                               String senha) {
        Agencia auxAgencia = buscarAgencia(numAgencia);
        Conta auxConta = new Conta(numero, auxAgencia.getCodigo(), saldo, nome, endereco, cpf, dataNascimento, senha);
        auxAgencia.cadastrarConta(auxConta);
    }

    /**
     * Busca, na lista <code>Agencias</code> do <code>Banco</code>, aquela que possui o código informado
     *
     * @param codigo número da <code>Agencia</code> sendo procurada
     * @return objeto <code>Agencia</code> com o código informado, ou <code>null</code> caso não exista
     */
    public Agencia buscarAgencia(int codigo) {

        for (Agencia agencia : agencias) {
            if (agencia.getCodigo() == codigo)
                return agencia;
        }
        return null;
    }

    /**
     * Transfere dinheiro de <code>contaLogada</code> para uma conta especificada nos parâmetros.
     *
     * @param numAgencia número da <code>Agencia</code> da <code>Conta</code> para a qual a transferência é feita
     * @param numConta   número da <code>Conta</code> para a qual a transferência é feita
     * @param valor      valor de dinheiro sendo transferido
     * @return <code>true</code>, se a operação ocorre, <code>false</code> se há erro nas informações fornecidas
     */
    public boolean transferencia(int numAgencia, int numConta, float valor) {
        Agencia auxAgencia = buscarAgencia(numAgencia);
        if (auxAgencia == null)
            return false;

        Conta auxConta = auxAgencia.buscarConta(numConta);
        if (auxConta == null)
            return false;

        if (valor > contaLogada.getSaldo())
            return false;

        contaLogada.sacar(valor);
        auxConta.depositar(valor);
        contaLogada.novaOperacao("Transferência", -valor, auxConta.getNome());
        auxConta.novaOperacao("Transferência", valor, contaLogada.getNome());
        return true;
    }

    /**
     * Transfere dinheiro de <code>contaLogada</code> para uma conta especificada nos parâmetros.
     *
     * @param chaveCPF CPF da <code>Conta</code> para a qual a tranferência é feita
     * @param valor    valor de dinheiro sendo transferido
     * @return <code>true</code>, se a operação ocorre, <code>false</code> se há erro nas informações fornecidas
     */
    public boolean pix(String chaveCPF, float valor) {
        Conta auxConta = null;

        primeiroFor:
        for (Agencia agencia : agencias) {
            for (Conta conta : agencia.getContas()) {
                if (Objects.equals(chaveCPF, conta.getCpf())) {
                    auxConta = conta;
                    break primeiroFor;
                }
            }
        }

        if (auxConta == null)
            return false;

        if (valor > contaLogada.getSaldo())
            return false;

        contaLogada.sacar(valor);
        auxConta.depositar(valor);
        contaLogada.novaOperacao("PIX", -valor, auxConta.getNome());
        auxConta.novaOperacao("PIX", valor, contaLogada.getNome());
        return true;
    }

    /**
     * Faz com que a conta logada seja nula
     */
    public void deslogarConta() {
        contaLogada = null;
    }
}
