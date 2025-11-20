
import java.util.Random;
import java.util.UUID;

/**
 * Classe para simular envio de emails
 * Em produção, integrar com JavaMail API ou serviço como SendGrid
 */
public class EmailService {
    
    // Gerar token de validação
    public static String gerarTokenValidacao() {
        return UUID.randomUUID().toString().replace("-", "");
    }
    
    // Simular envio de email de validação
    public static boolean enviarEmailValidacao(String email, String token) {
        System.out.println("========================================");
        System.out.println("SIMULAÇÃO DE ENVIO DE EMAIL");
        System.out.println("========================================");
        System.out.println("Para: " + email);
        System.out.println("Assunto: Validação de Email - Rede Mais Social");
        System.out.println("\nOlá,\n");
        System.out.println("Bem-vindo à Rede Mais Social!");
        System.out.println("Para validar seu email, use o código:");
        System.out.println("\n>>> " + token + " <<<\n");
        System.out.println("Ou acesse o link:");
        System.out.println("http://localhost:8080/validar?token=" + token);
        System.out.println("\nAtenciosamente,");
        System.out.println("Equipe Rede Mais Social");
        System.out.println("========================================\n");
        
        // Simular sucesso
        return true;
    }
    
    // Simular envio de email de aprovação
    public static boolean enviarEmailAprovacao(String email, String nome) {
        System.out.println("========================================");
        System.out.println("SIMULAÇÃO DE ENVIO DE EMAIL");
        System.out.println("========================================");
        System.out.println("Para: " + email);
        System.out.println("Assunto: Afiliação Aprovada - Rede Mais Social");
        System.out.println("\nOlá " + nome + ",\n");
        System.out.println("Sua afiliação foi aprovada!");
        System.out.println("Você já pode acessar a plataforma e começar a fazer a diferença.");
        System.out.println("\nAtenciosamente,");
        System.out.println("Equipe Rede Mais Social");
        System.out.println("========================================\n");
        
        return true;
    }
    
    // Simular envio de email de rejeição
    public static boolean enviarEmailRejeicao(String email, String nome, String motivo) {
        System.out.println("========================================");
        System.out.println("SIMULAÇÃO DE ENVIO DE EMAIL");
        System.out.println("========================================");
        System.out.println("Para: " + email);
        System.out.println("Assunto: Afiliação não aprovada - Rede Mais Social");
        System.out.println("\nOlá " + nome + ",\n");
        System.out.println("Infelizmente sua afiliação não foi aprovada.");
        System.out.println("Motivo: " + motivo);
        System.out.println("\nVocê pode entrar em contato conosco para mais informações.");
        System.out.println("\nAtenciosamente,");
        System.out.println("Equipe Rede Mais Social");
        System.out.println("========================================\n");
        
        return true;
    }
    
    // Simular envio de notificação de recomendação
    public static boolean enviarEmailRecomendacao(String email, String nomeVoluntario, String nomeOng) {
        System.out.println("========================================");
        System.out.println("SIMULAÇÃO DE ENVIO DE EMAIL");
        System.out.println("========================================");
        System.out.println("Para: " + email);
        System.out.println("Assunto: Nova Recomendação - Rede Mais Social");
        System.out.println("\nOlá " + nomeVoluntario + ",\n");
        System.out.println("Temos uma recomendação especial para você!");
        System.out.println("A ONG " + nomeOng + " pode ser uma ótima parceira.");
        System.out.println("\nAcesse a plataforma para ver mais detalhes.");
        System.out.println("\nAtenciosamente,");
        System.out.println("Equipe Rede Mais Social");
        System.out.println("========================================\n");
        
        return true;
    }
}
