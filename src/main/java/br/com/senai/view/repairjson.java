import java.util.regex.Matcher;
import java.util.regex.Pattern;

public static String repairJson(String badJson) {
        if (badJson == null || badJson.isEmpty()) {
            return badJson;
        }

        // Primeiro passo: remover aspas duplas consecutivas
        String cleanJson = badJson.replaceAll("\"\"", "\"");

        // Remove trailing commas inside string values
        cleanJson.replaceAll("\"([^\"]+),\"", "\"$1\"");

        // Add missing comma between key-value pairs
        cleanJson.replaceAll("(\"[^\"]+\":\\s*[^,}\\]]*\\s*)(?=(\"[^\"]+\":))", "$1,");

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

        return result.toString();
    }
