
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

public class Validador {
    
    // Validar email
    public static boolean validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }
    
    // Validar CPF (formato básico)
    public static boolean validarCPF(String cpf) {
        if (cpf == null) {
            return false;
        }
        cpf = cpf.replaceAll("[^0-9]", "");
        return cpf.length() == 11;
    }
    
    // Validar CNPJ (formato básico)
    public static boolean validarCNPJ(String cnpj) {
        if (cnpj == null) {
            return false;
        }
        cnpj = cnpj.replaceAll("[^0-9]", "");
        return cnpj.length() == 14;
    }
    
    // Validar senha (mínimo 6 caracteres)
    public static boolean validarSenha(String senha) {
        return senha != null && senha.length() >= 6;
    }
    
    // Validar telefone
    public static boolean validarTelefone(String telefone) {
        if (telefone == null) {
            return false;
        }
        telefone = telefone.replaceAll("[^0-9]", "");
        return telefone.length() >= 10 && telefone.length() <= 11;
    }
    
    // Criptografar senha com SHA-256
    public static String criptografarSenha(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(senha.getBytes());
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao criptografar senha", e);
        }
    }
    
    // Formatar CPF
    public static String formatarCPF(String cpf) {
        if (cpf == null) return "";
        cpf = cpf.replaceAll("[^0-9]", "");
        if (cpf.length() == 11) {
            return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + 
                   cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
        }
        return cpf;
    }
    
    // Formatar CNPJ
    public static String formatarCNPJ(String cnpj) {
        if (cnpj == null) return "";
        cnpj = cnpj.replaceAll("[^0-9]", "");
        if (cnpj.length() == 14) {
            return cnpj.substring(0, 2) + "." + cnpj.substring(2, 5) + "." + 
                   cnpj.substring(5, 8) + "/" + cnpj.substring(8, 12) + "-" + 
                   cnpj.substring(12, 14);
        }
        return cnpj;
    }
}
