public class JsonRepairer {

    public static String repairJson(String badJson) {
        if (badJson == null || badJson.isEmpty()) {
            return badJson;
        }

        StringBuilder result = new StringBuilder(badJson.length());
        boolean inString = false;
        boolean needsComma = false;
        boolean afterValue = false;
        char lastChar = 0;

        for (int i = 0; i < badJson.length(); i++) {
            char currentChar = badJson.charAt(i);

            if (shouldIgnoreComma(currentChar, lastChar)) {
                continue;
            }

            if (isQuote(currentChar, lastChar)) {
                handleQuote(result, currentChar, needsComma, inString, afterValue, lastChar);
                inString = !inString;
                if (!inString) {
                    afterValue = true;
                }
            } else if (inString && currentChar == ',') {
                continue;
            } else if (!inString && currentChar == '"') {
                handleClosingQuote(result, currentChar, afterValue, lastChar);
                afterValue = false;
            } else {
                handleOtherCharacters(result, currentChar, lastChar, inString);
                if (!inString && currentChar == ':') {
                    afterValue = false;
                }
            }

            if (!inString && currentChar == '"') {
                needsComma = true;
            }

            lastChar = currentChar;
        }

        return result.toString();
    }

    private static boolean shouldIgnoreComma(char currentChar, char lastChar) {
        return currentChar == ',' && lastChar == ',';
    }

    private static boolean isQuote(char currentChar, char lastChar) {
        return currentChar == '"' && lastChar != '\\';
    }

    private static void handleQuote(StringBuilder result, char currentChar, boolean needsComma, boolean inString, boolean afterValue, char lastChar) {
        if (needsComma && !inString && afterValue && lastChar != ',') {
            result.append(',');
        }
        result.append(currentChar);
    }

    private static void handleClosingQuote(StringBuilder result, char currentChar, boolean afterValue, char lastChar) {
        if (lastChar == '"' && afterValue && lastChar != ',') {
            result.append(',').append(currentChar);
        } else {
            result.append(currentChar);
        }
    }

    private static void handleOtherCharacters(StringBuilder result, char currentChar, char lastChar, boolean inString) {
        if (currentChar == ',' && !inString) {
            if (lastChar != ',') {
                result.append(currentChar);
            }
        } else {
            result.append(currentChar);
        }
    }

    public static void main(String[] args) {
        String complexJson = "{\"codigoSistemaOrigem\": \"DXC\" \"emissor\": 1001,\"filialEmissor\": 1,\"codigoProduto\": 1,\"numeroConta\": 2497,\"correlativo\": 1,\"numeroCartaoMascarado\": \"8682\",,\"identificadorMovimentacao\": 10,\"codigoCorte\": 4,\"periodoCorte\": 202309,\"dataProcessamento\": 20240612,\"dataTransacao\": 20240612,\"descricaoEstabelecimento\": \"\",,\"codigoMetodoPagamento\": 1,\"codigoRubrica\": 2129,\"identificadorTransacao\": 0,\"tipoMoeda\": 1,\"valorTransacaoReal\": 1001.0,\"parcelaAtual\": 1,\"quantidadeParcelas\": 1,\"codigoMoedaOrigem\": 986,\"valorTransacaoOrigem\": 1001.0,\"taxaDolar\": 0,\"codigoTipoPagamentoOrigem\": \"1\",,\"codigoBancoOrigem\": null,\"dataRetornoArquivo\": \"2024-06-12\",,\"qtdTentativasPersistencia\": 0,\"nomeFila\": \"QL.CART.CASHBACK_BV_DXC.INT\"}";
        String fixedJson = repairJson(complexJson);
        System.out.println("JSON Original:");
        System.out.println(complexJson);
        System.out.println("\nJSON Corrigido:");
        System.out.println(fixedJson);
    }
}
