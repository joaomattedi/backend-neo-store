package org.desafio.neo.utils;

public class CNPJUtils {
    public static boolean isValidCNPJ(String cnpj) {
        cnpj = cnpj.replaceAll("\\D", "");

        if (cnpj.length() != 14) {
            return false;
        }

        try {
            Long.parseLong(cnpj);

            int[] firstWeight = new int[]{5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int firstCheckDigit = calculateCheckDigit(cnpj.substring(0, 12), firstWeight);
            if (firstCheckDigit != Character.getNumericValue(cnpj.charAt(12))) {
                return false;
            }

            int[] secondWeight = new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
            int secondCheckDigit = calculateCheckDigit(cnpj.substring(0, 13), secondWeight);
            return secondCheckDigit == Character.getNumericValue(cnpj.charAt(13));
        } catch (Exception e) {
            return false;
        }
    }

    private static int calculateCheckDigit(String base, int[] weight) {
        int sum = 0;
        for (int i = 0; i < base.length(); i++) {
            sum += Character.getNumericValue(base.charAt(i)) * weight[i];
        }
        int remainder = sum % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }
}
