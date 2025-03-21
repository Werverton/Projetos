public static String repairJson(String badJson) {
        if (badJson == null || badJson.isEmpty()) {
            return badJson;
        }

        StringBuilder result = new StringBuilder(badJson.length() + 10);
        boolean inString = false;
        boolean afterColon = false;
        char previousChar = 0;

        for (int i = 0; i < badJson.length(); i++) {
            char currentChar = badJson.charAt(i);

            // Controla se estamos dentro de uma string
            if (currentChar == '"' && previousChar != '\\') {
                inString = !inString;
            }

            if (!inString) {
                // Adiciona aspas em chaves sem aspas
                if (Character.isLetterOrDigit(currentChar) && !afterColon && previousChar != '"') {
                    if (i == 0 || previousChar == '{' || previousChar == ',') {
                        result.append('"');
                    }
                }

                // Atualiza flag de após dois pontos
                if (currentChar == ':') {
                    afterColon = true;
                } else if (!Character.isWhitespace(currentChar)) {
                    afterColon = false;
                }

                // Move vírgula para o lugar correto
                if (currentChar == ',' && previousChar == '"') {
                    // Remove a vírgula errada
                    result.setLength(result.length() - 1);
                    // Adiciona a vírgula no lugar correto
                    result.append('"').append(currentChar);
                    continue;
                }
            }

            result.append(currentChar);

            if (!Character.isWhitespace(currentChar)) {
                previousChar = currentChar;
            }
        }

        return result.toString();
    }

{"codigoSistemaOrigem": "DXC," "emissor": 1001,"filialEmissor": 1,"codigoProduto": 1,"numeroConta": 2497,"correlativo": 1,"numeroCartaoMascarado": "8682","identificadorMovimentacao": 10,"codigoCorte": 4,"periodoCorte": 202309,"dataProcessamento": 20240612,"dataTransacao": 20240612,"descricaoEstabelecimento": "","codigoMetodoPagamento": 1,"codigoRubrica": 2129,"identificadorTransacao": 0,"tipoMoeda": 1,"valorTransacaoReal": 1001.0,"parcelaAtual": 1,"quantidadeParcelas": 1,"codigoMoedaOrigem": 986,"valorTransacaoOrigem": 1001.0,"taxaDolar": 0,"codigoTipoPagamentoOrigem": "1","codigoBancoOrigem": null,"dataRetornoArquivo": "2024-06-12","qtdTentativasPersistencia": 0,"nomeFila": "QL.CART.CASHBACK_BV_DXC.INT"}
