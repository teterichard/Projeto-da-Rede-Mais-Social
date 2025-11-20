

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AfiliacaoDAO {
    
    // Inserir nova afiliação
    public boolean inserir(Afiliacao afiliacao) {
        String sql = "INSERT INTO afiliacao (id_candidato, status) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, afiliacao.getIdCandidato());
            stmt.setString(2, afiliacao.getStatus());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        afiliacao.setIdAfiliacao(rs.getInt(1));
                    }
                }
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao inserir afiliação: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Buscar afiliação por ID do candidato
    public Afiliacao buscarPorIdCandidato(int idCandidato) {
        String sql = "SELECT * FROM afiliacao WHERE id_candidato = ? ORDER BY data_solicitacao DESC LIMIT 1";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, idCandidato);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extrairAfiliacao(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar afiliação: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    // Atualizar status da afiliação
    public boolean atualizarStatus(int idAfiliacao, String novoStatus) {
        String sql = "UPDATE afiliacao SET status = ?, data_aprovacao = ? WHERE id_afiliacao = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, novoStatus);
            
            if ("APROVADO".equals(novoStatus)) {
                stmt.setTimestamp(2, Timestamp.valueOf(java.time.LocalDateTime.now()));
            } else {
                stmt.setTimestamp(2, null);
            }
            
            stmt.setInt(3, idAfiliacao);
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar status da afiliação: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Listar afiliações pendentes
    public List<Afiliacao> listarPendentes() {
        List<Afiliacao> afiliacoes = new ArrayList<>();
        String sql = "SELECT * FROM afiliacao WHERE status = 'AGUARDANDO_APROVACAO' ORDER BY data_solicitacao ASC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                afiliacoes.add(extrairAfiliacao(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao listar afiliações pendentes: " + e.getMessage());
            e.printStackTrace();
        }
        
        return afiliacoes;
    }
    
    // Método auxiliar para extrair afiliação do ResultSet
    private Afiliacao extrairAfiliacao(ResultSet rs) throws SQLException {
        Afiliacao afiliacao = new Afiliacao();
        afiliacao.setIdAfiliacao(rs.getInt("id_afiliacao"));
        afiliacao.setIdCandidato(rs.getInt("id_candidato"));
        afiliacao.setStatus(rs.getString("status"));
        
        Timestamp dataSolicitacao = rs.getTimestamp("data_solicitacao");
        if (dataSolicitacao != null) {
            afiliacao.setDataSolicitacao(dataSolicitacao.toLocalDateTime());
        }
        
        Timestamp dataAprovacao = rs.getTimestamp("data_aprovacao");
        if (dataAprovacao != null) {
            afiliacao.setDataAprovacao(dataAprovacao.toLocalDateTime());
        }
        
        afiliacao.setMotivoRejeicao(rs.getString("motivo_rejeicao"));
        
        return afiliacao;
    }
}
