import org.json.JSONObject;
import java.util.regex.*;

public class JsonCorrector {

    public static String corrigirJson(String jsonString) {
        // 1. Remove vírgulas dentro de strings (preserva vírgulas normais)
        jsonString = jsonString.replaceAll("\"([^\"]*),\"", "\"$1\"");
        
        // 2. Corrige números com vírgula (mas preserva decimais)
        jsonString = jsonString.replaceAll("(\\d),([^0-9])", "$1$2");
        
        // 3. Adiciona vírgulas faltantes entre propriedades
        jsonString = jsonString.replaceAll("(\"[^\"]*\"|[0-9.]+|null)\\s*(\"[^\"]*\"|\\d)", "$1, $2");
        
        // 4. Remove vírgulas antes de fechamentos
        jsonString = jsonString.replaceAll(",\\s*([}\\])])", "$1");
        
        // 5. Remove vírgulas duplas
        jsonString = jsonString.replaceAll(",(\\s*,)", ",");
        
        // 6. Garante que cada propriedade tenha : entre chave e valor
        jsonString = jsonString.replaceAll("(\"[^\"]*\")\\s*([^:])", "$1: $2");
        
        return jsonString;
    }

    public static JSONObject parseJsonCorrigido(String jsonString) throws Exception {
        // Aplica correções em etapas
        String jsonCorrigido = jsonString;
        
        // Primeira passada para corrigir os problemas mais graves
        jsonCorrigido = corrigirJson(jsonCorrigido);
        
        // Segunda passada para garantir a formatação final
        jsonCorrigido = jsonCorrigido
            .replaceAll("\"\\s*:\"", "\":\"")
            .replaceAll(":\"\\s*\"", ":\"\"");
        
        return new JSONObject(jsonCorrigido);
    }

    public static void main(String[] args) {
        String jsonProblematico = "{\n" +
                "  \"codigoSistemaOrigem\": \"DXC,\"\n" +
                "  \"emissor\": 1001,\n" +
                "  \"filialEmissor\": 1,\n" +
                "  \"codigoProduto\": 1,\n" +
                "  \"numeroConta\": 2497,\n" +
                "  \"correlativo\": 1,\n" +
                "  \"numeroCartaoMascarado\": \"8682\",,\n" +
                "  \"identificadorMovimentacao\": 10,\n" +
                "  \"codigoCorte\": 4,\n" +
                "  \"periodoCorte\": 202309,\n" +
                "  \"dataProcessamento\": 20240612,\n" +
                "  \"dataTransacao\": 20240612,\n" +
                "  \"descricaoEstabelecimento\": \"\",,\n" +
                "  \"codigoMetodoPagamento\": 1,\n" +
                "  \"codigoRubrica\": 2129,\n" +
                "  \"identificadorTransacao\": 0,\n" +
                "  \"tipoMoeda\": 1,\n" +
                "  \"valorTransacaoReal\": 1001.0,\n" +
                "  \"parcelaAtual\": 1,\n" +
                "  \"quantidadeParcelas\": 1,\n" +
                "  \"codigoMoedaOrigem\": 986,\n" +
                "  \"valorTransacaoOrigem\": 1001.0,\n" +
                "  \"taxaDolar\": 0,\n" +
                "  \"codigoTipoPagamentoOrigem\": \"1\",,\n" +
                "  \"codigoBancoOrigem\": null,\n" +
                "  \"dataRetornoArquivo\": \"2024-06-12\",,\n" +
                "  \"qtdTentativasPersistencia\": 0,\n" +
                "  \"nomeFila\": \"QL.CART.CASHBACK_BV_DXC.INT\"\n" +
                "}";

        try {
            System.out.println("JSON original problemático:");
            System.out.println(jsonProblematico);
            
            System.out.println("\nJSON corrigido:");
            String jsonCorrigido = corrigirJson(jsonProblematico);
            System.out.println(jsonCorrigido);
            
            System.out.println("\nTentando parsear...");
            JSONObject jsonObj = parseJsonCorrigido(jsonProblematico);
            System.out.println("Objeto JSON parseado com sucesso!");
            System.out.println(jsonObj.toString(2));
            
        } catch (Exception e) {
            System.err.println("\nErro ao processar JSON: " + e.getMessage());
            System.err.println("Última tentativa com correção agressiva...");
            
            try {
                // Correção agressiva final
                String ultimaTentativa = jsonProblematico
                    .replaceAll("\",\"", "\", \"")
                    .replaceAll(",,", ",")
                    .replaceAll("\"\\s*\"", "\", \"")
                    .replaceAll("(\"[^\"]*\")\\s*(\"[^\"]*\")", "$1: $2")
                    .replaceAll("(\\d),([^0-9])", "$1$2");
                
                JSONObject jsonObj = new JSONObject(ultimaTentativa);
                System.out.println("Sucesso na tentativa final!");
                System.out.println(jsonObj.toString(2));
            } catch (Exception ex) {
                System.err.println("Falha definitiva: " + ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}


<dependency>
    <groupId>org.json</groupId>
    <artifactId>json</artifactId>
    <version>20231013</version>
</dependency>
