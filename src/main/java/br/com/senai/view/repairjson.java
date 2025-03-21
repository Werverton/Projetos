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
            
            // Ignora vírgulas duplicadas
            if (currentChar == ',' && lastChar == ',') {
                continue;
            }
            
            if (currentChar == '"' && lastChar != '\\') {
                if (needsComma && !inString && afterValue && lastChar != ',') {
                    result.append(',');
                    needsComma = false;
                }
                inString = !inString;
                if (!inString) {
                    afterValue = true;
                }
                result.append(currentChar);
            } else if (inString && currentChar == ',') {
                // Ignora a vírgula dentro da string
                continue;
            } else if (!inString && currentChar == '"') {
                if (lastChar == '"' && afterValue && lastChar != ',') {
                    result.append(',').append(currentChar);
                    afterValue = false;
                } else {
                    result.append(currentChar);
                }
            } else {
                if (currentChar == ',' && !inString) {
                    // Adiciona vírgula apenas se não houver uma anterior
                    if (lastChar != ',') {
                        result.append(currentChar);
                    }
                } else {
                    result.append(currentChar);
                }
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
}

{"codigoSistemaOrigem": "DXC," "emissor": 1001,"filialEmissor": 1,"codigoProduto": 1,"numeroConta": 2497,"correlativo": 1,"numeroCartaoMascarado": "8682","identificadorMovimentacao": 10,"codigoCorte": 4,"periodoCorte": 202309,"dataProcessamento": 20240612,"dataTransacao": 20240612,"descricaoEstabelecimento": "","codigoMetodoPagamento": 1,"codigoRubrica": 2129,"identificadorTransacao": 0,"tipoMoeda": 1,"valorTransacaoReal": 1001.0,"parcelaAtual": 1,"quantidadeParcelas": 1,"codigoMoedaOrigem": 986,"valorTransacaoOrigem": 1001.0,"taxaDolar": 0,"codigoTipoPagamentoOrigem": "1","codigoBancoOrigem": null,"dataRetornoArquivo": "2024-06-12","qtdTentativasPersistencia": 0,"nomeFila": "QL.CART.CASHBACK_BV_DXC.INT"}
