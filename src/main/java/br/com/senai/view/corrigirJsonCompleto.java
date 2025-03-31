import org.json.JSONObject;

public class JsonCorrector {

    public static String corrigirJsonCompleto(String jsonString) {
        try {
            // Tenta criar um JSONObject diretamente para verificar se o JSON é válido
            new JSONObject(jsonString);
            return jsonString; // Se for válido, retorna o JSON original
        } catch (Exception e) {
            // Caso o JSON seja inválido, aplica as correções
            System.err.println("Corrigindo JSON inválido...");

            // Remove vírgula no início do JSON, se existir
            jsonString = jsonString.replaceFirst("\\{\\s*,", "{");

            // Remove vírgula no final do JSON, se existir
            jsonString = jsonString.replaceFirst(",\\s*\\}", "}");

            // Corrige a vírgula mal colocada em qualquer valor
            jsonString = jsonString.replaceAll("([^\"]+),\"", "$1\"");

            // Adiciona vírgulas ausentes entre propriedades
            jsonString = jsonString.replaceAll("(\\s*\"[^\"]+\"\\s*:\\s*(?:\"[^\"]*\"|[0-9.]+|null)\\s*)(\\s*\"[^\"]+\"\\s*:)", "$1,$2");

            // Remove possíveis vírgulas duplas criadas
            jsonString = jsonString.replaceAll(",\\s*,", ",");

            // Retorna o JSON corrigido
            return jsonString;
        }
    }

    public static void main(String[] args) {
        String jsonProblematico = "{\n" +
                "    \"codigoSistemaOrigem\": \"DXC,\"\n" +
                "    \"emissor\": 1001,\n" +
                "    \"filialEmissor\": 1,\n" +
                "    \"codigoProduto\": 1,\n" +
                "    \"numeroConta\": 2497,\n" +
                "    \"correlativo\": 1,\n" +
                "    \"numeroCartaoMascarado\": \"8682\",\n" +
                "    \"identificadorMovimentacao\": 10,\n" +
                "    \"codigoCorte\": 4,\n" +
                "    \"periodoCorte\": 202309,\n" +
                "    \"dataProcessamento\": 20240612,\n" +
                "    \"dataTransacao\": 20240612,\n" +
                "    \"descricaoEstabelecimento\": \"\",\n" +
                "    \"codigoMetodoPagamento\": 1,\n" +
                "    \"codigoRubrica\": 2129,\n" +
                "    \"identificadorTransacao\": 0,\n" +
                "    \"tipoMoeda\": 1,\n" +
                "    \"valorTransacaoReal\": 1001.0,\n" +
                "    \"parcelaAtual\": 1,\n" +
                "    \"quantidadeParcelas\": 1,\n" +
                "    \"codigoMoedaOrigem\": 986,\n" +
                "    \"valorTransacaoOrigem\": 1001.0,\n" +
                "    \"taxaDolar\": 0,\n" +
                "    \"codigoTipoPagamentoOrigem\": \"1\",\n" +
                "    \"codigoBancoOrigem\": null,\n" +
                "    \"dataRetornoArquivo\": \"2024-06-12\",\n" +
                "    \"qtdTentativasPersistencia\": 0,\n" +
                "    \"nomeFila\": \"QL.CART.CASHBACK_BV_DXC.INT\"\n" +
                "}";

        try {
            System.out.println("JSON original problemático:");
            System.out.println(jsonProblematico);

            // Aplica as correções
            String jsonCorrigido = corrigirJsonCompleto(jsonProblematico);

            System.out.println("\nJSON corrigido:");
            System.out.println(jsonCorrigido);

            // Parse do JSON corrigido
            JSONObject jsonObj = new JSONObject(jsonCorrigido);
            System.out.println("\nObjeto JSON parseado com sucesso!");
            System.out.println(jsonObj.toString(2));

        } catch (Exception e) {
            System.err.println("\nErro ao processar JSON: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
