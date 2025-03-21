public String repairJson(String badJson) {
    if (badJson == null || badJson.isEmpty()) {
        return badJson;
    }

    StringBuilder result = new StringBuilder(badJson.length() + 10);
    boolean inString = false;
    boolean afterColon = false;
    boolean needsComma = false;
    char previousChar = 0;
    
    for (int i = 0; i < badJson.length(); i++) {
        char currentChar = badJson.charAt(i);
        
        // Controla se estamos dentro de uma string
        if (currentChar == '"' && previousChar != '\\') {
            inString = !inString;
        }
        
        // Se não estiver dentro de uma string, aplica as regras de correção
        if (!inString) {
            // Remove espaços extras entre aspas e vírgulas
            if (Character.isWhitespace(currentChar)) {
                if (i > 0 && i < badJson.length() - 1) {
                    char nextChar = badJson.charAt(i + 1);
                    if (previousChar == '"' && nextChar == ',') {
                        continue;
                    }
                }
            }
            
            // Adiciona aspas em chaves que não as têm
            if (Character.isLetterOrDigit(currentChar) && !afterColon && previousChar != '"') {
                if (i == 0 || previousChar == '{' || previousChar == ',') {
                    result.append('"');
                }
            }
            
            // Adiciona vírgula faltante entre pares chave-valor
            if (currentChar == '{' || currentChar == '}') {
                needsComma = false;
            } else if (needsComma && currentChar != ',' && currentChar != '}') {
                if (!Character.isWhitespace(currentChar)) {
                    result.append(',');
                    needsComma = false;
                }
            }
            
            // Atualiza flag após encontrar dois pontos
            if (currentChar == ':') {
                afterColon = true;
            } else if (!Character.isWhitespace(currentChar)) {
                afterColon = false;
            }
            
            // Remove vírgulas consecutivas
            if (currentChar == ',' && previousChar == ',') {
                continue;
            }
            
            // Remove vírgula antes de }
            if (currentChar == '}' && previousChar == ',') {
                result.setLength(result.length() - 1);
            }
        }
        
        result.append(currentChar);
        
        // Atualiza flags
        if (!Character.isWhitespace(currentChar)) {
            previousChar = currentChar;
            if (currentChar == '"' || currentChar == '}') {
                needsComma = true;
            }
        }
    }
    
    return result.toString();
}
