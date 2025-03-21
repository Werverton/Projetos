public String repairJson(String badJson) {
    if (badJson == null || badJson.isEmpty()) {
        return badJson;
    }

    StringBuilder result = new StringBuilder(badJson.length() + 10);
    boolean inString = false;
    char lastChar = 0;
    
    for (int i = 0; i < badJson.length(); i++) {
        char currentChar = badJson.charAt(i);
        
        // Controla se estamos dentro de uma string
        if (currentChar == '"' && (i == 0 || badJson.charAt(i - 1) != '\\')) {
            inString = !inString;
        }
        
        // Se não estiver dentro de uma string, trata as chaves
        if (!inString) {
            // Identifica início de uma chave
            if (Character.isLetterOrDigit(currentChar) && 
                (i == 0 || lastChar == '{' || lastChar == ',')) {
                result.append('"');
            }
            
            // Adiciona o caractere atual
            result.append(currentChar);
            
            // Identifica fim de uma chave
            if (currentChar == ':' && !Character.isWhitespace(lastChar) && lastChar != '"') {
                result.setLength(result.length() - 1);
                result.append("\":");
            }
        } else {
            result.append(currentChar);
        }
        
        if (!Character.isWhitespace(currentChar)) {
            lastChar = currentChar;
        }
    }
    
    return result.toString();
}
