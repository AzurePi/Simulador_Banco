package trabalho1;

/**
 * @author Azure
 */
public class Main {

    public static void main(String[] args) {
        final String arquivoBanco = "POO/trabalho1/files/banco.txt";
        final String arquivoAgencias = "POO/trabalho1/files/agencias.txt";
        final String arquivoContas = "POO/trabalho1/files/contas.txt";

        final DisplayBanco display = new DisplayBanco(arquivoBanco, arquivoAgencias, arquivoContas);

        display.telaUsuario();
    }
}
