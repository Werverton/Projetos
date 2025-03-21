import java.util.regex.Matcher;
import java.util.regex.Pattern;

public String repairJson(String badJson) {
    if (badJson == null || badJson.isEmpty()) {
        return badJson;
    }

    // Primeiro passo: remover aspas duplas consecutivas, exceto quando estão no final antes de }
    String cleanJson = badJson.replaceAll("\"\"(?!\\s*})", "\"");

    // Remove trailing commas inside string values
    cleanJson = cleanJson.replaceAll("\"([^\"]+),\"", "\"$1\"");

    // Adiciona vírgula faltante entre pares chave-valor
    cleanJson = cleanJson.replaceAll("(\"[^\"]+\":\\s*[^,}\\]]*\\s*)(?=(\"[^\"]+\":))", "$1,");

    StringBuilder result = new StringBuilder(cleanJson.length() + 10);
    boolean inString = false;
    boolean afterColon = false;
    boolean needsComma = false;
    char previousChar = 0;

    for (int i = 0; i < cleanJson.length(); i++) {
        char currentChar = cleanJson.charAt(i);

        // Controla se estamos dentro de uma string
        if (currentChar == '"' && previousChar != '\\') {
            inString = !inString;
        }

        if (!inString) {
            // Remove espaços entre aspas e vírgulas
            if (Character.isWhitespace(currentChar)) {
                if (previousChar == '"' && i < cleanJson.length() - 1 && cleanJson.charAt(i + 1) == ',') {
                    continue;
                }
            }

            // Adiciona aspas em chaves sem aspas
            if (Character.isLetterOrDigit(currentChar) && !afterColon && previousChar != '"') {
                if (i == 0 || previousChar == '{' || previousChar == ',') {
                    result.append('"');
                }
            }

            // Atualiza flag de após dois pontos
            if (currentChar == ':') {
                afterColon = true;
                // Adiciona aspas antes do valor se necessário
                if (i < cleanJson.length() - 1) {
                    char nextChar = cleanJson.charAt(i + 1);
                    if (!Character.isWhitespace(nextChar) && nextChar != '"' && nextChar != '{' && nextChar != '[') {
                        result.append(currentChar).append('"');
                        continue;
                    }
                }
            } else if (!Character.isWhitespace(currentChar)) {
                afterColon = false;
            }
        } else {
            // Remove vírgulas dentro de strings
            if (currentChar == ',' && previousChar == '"') {
                continue;
            }
        }

        result.append(currentChar);

        if (!Character.isWhitespace(currentChar)) {
            previousChar = currentChar;
            if (currentChar == '"' || currentChar == '}') {
                needsComma = true;
            }
        }
    }

    // Adiciona vírgula faltante no final se necessário
    if (needsComma && result.charAt(result.length() - 1) != ',') {
        result.append(',');
    }

    return result.toString();
}

{"codigoSistemaOrigem": "DXC," "emissor": 1001,"filialEmissor": 1,"codigoProduto": 1,"numeroConta": 2497,"correlativo": 1,"numeroCartaoMascarado": "8682","identificadorMovimentacao": 10,"codigoCorte": 4,"periodoCorte": 202309,"dataProcessamento": 20240612,"dataTransacao": 20240612,"descricaoEstabelecimento": "","codigoMetodoPagamento": 1,"codigoRubrica": 2129,"identificadorTransacao": 0,"tipoMoeda": 1,"valorTransacaoReal": 1001.0,"parcelaAtual": 1,"quantidadeParcelas": 1,"codigoMoedaOrigem": 986,"valorTransacaoOrigem": 1001.0,"taxaDolar": 0,"codigoTipoPagamentoOrigem": "1","codigoBancoOrigem": null,"dataRetornoArquivo": "2024-06-12","qtdTentativasPersistencia": 0,"nomeFila": "QL.CART.CASHBACK_BV_DXC.INT"}
