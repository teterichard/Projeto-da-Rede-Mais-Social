

/**
 * Controller para gerenciar o processo de afiliação de voluntários
 * Baseado no diagrama de sequência UC002
 */
public class AfiliacaoController {
    
    private CandidatoDAO candidatoDAO;
    private AfiliacaoDAO afiliacaoDAO;
    
    public AfiliacaoController() {
        this.candidatoDAO = new CandidatoDAO();
        this.afiliacaoDAO = new AfiliacaoDAO();
    }
    
    /**
     * Passo 1-3: Buscar candidato por email e CPF
     */
    public Candidato buscarCandidatoPorCadastroAnterior(String email, String cpf) {
        // Buscar por email primeiro
        Candidato candidato = candidatoDAO.buscarPorEmail(email);
        
        if (candidato != null && cpf.equals(candidato.getCpf())) {
            return candidato;
        }
        
        return null;
    }
    
    /**
     * Passo 4-5: Validar e criar novo candidato (formulário de identificação)
     */
    public Candidato criarCandidato(Candidato candidato) {
        // Validações básicas
        if (!Validador.validarEmail(candidato.getEmail())) {
            System.out.println("❌ ERRO: Email inválido!");
            return null;
        }
        
        // Validar CPF ou CNPJ dependendo do que está preenchido
        boolean temCPF = candidato.getCpf() != null && !candidato.getCpf().isEmpty();
        boolean temCNPJ = candidato.getCnpj() != null && !candidato.getCnpj().isEmpty();
        
        if (temCPF && !temCNPJ) {
            // Validar CPF
            if (!Validador.validarCPF(candidato.getCpf())) {
                System.out.println("❌ ERRO: CPF inválido!");
                return null;
            }
        } else if (temCNPJ && !temCPF) {
            // Validar CNPJ
            if (!Validador.validarCNPJ(candidato.getCnpj())) {
                System.out.println("❌ ERRO: CNPJ inválido!");
                return null;
            }
        } else {
            System.out.println("❌ ERRO: Preencha CPF ou CNPJ!");
            return null;
        }
        
        // Verificar se já existe cadastro
        if (candidatoDAO.buscarPorEmail(candidato.getEmail()) != null) {
            System.out.println("❌ ERRO: Email já cadastrado!");
            return null;
        }
        
        // Criptografar senha
        candidato.setSenha(Validador.criptografarSenha(candidato.getSenha()));
        candidato.setSituacao("CADASTRANDO");
        
        // Inserir candidato
        if (candidatoDAO.inserir(candidato)) {
            return candidato;
        }
        
        return null;
    }
    
    /**
     * Passo 10-13: Aceitar termos e enviar email de validação
     */
    public boolean aceitarTermosEEnviarValidacao(Candidato candidato) {
        if (!candidato.getAceiteTermo()) {
            System.out.println("❌ ERRO: É necessário aceitar os termos!");
            return false;
        }
        
        // Gerar token de validação
        String token = EmailService.gerarTokenValidacao();
        candidato.setTokenValidacao(token);
        candidato.setSituacao("AGUARDANDO_VALIDACAO");
        
        // Atualizar candidato no banco com o token
        candidatoDAO.atualizarSituacao(candidato.getIdCandidato(), "AGUARDANDO_VALIDACAO");
        candidatoDAO.atualizarToken(candidato.getIdCandidato(), token);
        
        // Simular envio de email
        boolean emailEnviado = EmailService.enviarEmailValidacao(candidato.getEmail(), token);
        
        if (emailEnviado) {
            System.out.println("✅ Email de validação enviado! Verifique sua caixa de entrada.");
            return true;
        }
        
        return false;
    }
    
    /**
     * Passo 14-16: Validar email através do token
     */
    public boolean validarEmail(String email, String token) {
        Candidato candidato = candidatoDAO.buscarPorEmail(email);
        
        if (candidato == null) {
            System.out.println("❌ ERRO: Candidato não encontrado!");
            return false;
        }
        
        if (!token.equals(candidato.getTokenValidacao())) {
            System.out.println("❌ ERRO: Token inválido!");
            return false;
        }
        
        // Atualizar status para "Aguardando Aprovação"
        candidatoDAO.atualizarSituacao(candidato.getIdCandidato(), "AGUARDANDO_APROVACAO");
        
        // Criar registro de afiliação
        Afiliacao afiliacao = new Afiliacao(candidato.getIdCandidato());
        afiliacao.setStatus("AGUARDANDO_APROVACAO");
        afiliacaoDAO.inserir(afiliacao);
        
        System.out.println("✅ Email validado com sucesso! Aguarde a aprovação da sua afiliação.");
        
        return true;
    }
    
    /**
     * Aprovar afiliação (usado pelo admin/ONG)
     */
    public boolean aprovarAfiliacao(int idAfiliacao, int idCandidato) {
        // Atualizar status da afiliação
        if (!afiliacaoDAO.atualizarStatus(idAfiliacao, "APROVADO")) {
            return false;
        }
        
        // Atualizar status do candidato
        if (!candidatoDAO.atualizarSituacao(idCandidato, "APROVADO")) {
            return false;
        }
        
        // Buscar candidato para enviar email
        Candidato candidato = candidatoDAO.buscarPorId(idCandidato);
        if (candidato != null) {
            EmailService.enviarEmailAprovacao(candidato.getEmail(), candidato.getNome());
        }
        
        return true;
    }
    
    /**
     * Rejeitar afiliação
     */
    public boolean rejeitarAfiliacao(int idAfiliacao, int idCandidato, String motivo) {
        // Atualizar status
        afiliacaoDAO.atualizarStatus(idAfiliacao, "REJEITADO");
        candidatoDAO.atualizarSituacao(idCandidato, "REJEITADO");
        
        // Enviar email
        Candidato candidato = candidatoDAO.buscarPorId(idCandidato);
        if (candidato != null) {
            EmailService.enviarEmailRejeicao(candidato.getEmail(), candidato.getNome(), motivo);
        }
        
        return true;
    }
}
