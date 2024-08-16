package trabalho1;

import java.util.ArrayList;

/**
 * @author Azure
 */
public class Agencia {
    private int codigo;
    private String nome;
    private String endereco;
    private final ArrayList<Conta> contas = new ArrayList<>();

    public Agencia(String nome, int codigo, String endereco) {
        this.nome = nome;
        this.codigo = codigo;
        this.endereco = endereco;
    }


    //setters & getters--------------------------------------------------------
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
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

    public ArrayList<Conta> getContas() {
        return contas;
    }


    //métodos------------------------------------------------------------------
    /**
     * Adiciona um objeto <code>Conta</code> à lista de contas da <code>Agencia</code>
     *
     * @param conta <code>Conta</code> que será adicionado à <code>Agencia</code>
     */
    public void cadastrarConta(Conta conta) {
        contas.add(conta);
    }

    /**
     * Busca uma <code>Conta</code> dentre as listadas na agência com base em seu número. Só fornece acesso se a senha fornecida
     * equivalente à senha cadastrada na conta.
     *
     * @param numConta número da conta sendo buscada
     * @param senha    String com a senha da conta sendo buscada
     * @return uma <code>Conta</code>, se o número e senha informados estiverem corretos, ou <code>null</code> caso contrário
     */
    public Conta buscarConta(int numConta, String senha) {

        for (Conta conta : contas) {
            if (conta.getNumero() == numConta && conta.validarSenha(senha))
                return conta;
        }

        return null;
    }

    /**
     * Busca uma <code>Conta</code> dentre as listadas na agência com base em seu número.
     *
     * @param numConta número (int) da conta sendo buscada
     * @return uma <code>Conta</code>, se o número corresponder a uma <code>Conta</code> existente, ou <code>null</code> caso contrário
     */
    public Conta buscarConta(int numConta) {

        for (Conta conta : contas) {
            if (conta.getNumero() == numConta)
                return conta;
        }

        return null;
    }
}
