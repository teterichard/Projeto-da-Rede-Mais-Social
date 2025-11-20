

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CandidatoDAO {
    
    // Inserir novo candidato
    public boolean inserir(Candidato candidato) {
        String sql = "INSERT INTO candidato (tipo, nome, email, senha, cpf, cnpj, endereco, " +
                     "telefone, situacao, aceite_termo, data_nascimento, sexo, nacionalidade) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, candidato.getTipo());
            stmt.setString(2, candidato.getNome());
            stmt.setString(3, candidato.getEmail());
            stmt.setString(4, candidato.getSenha());
            stmt.setString(5, candidato.getCpf());
            stmt.setString(6, candidato.getCnpj());
            stmt.setString(7, candidato.getEndereco());
            stmt.setString(8, candidato.getTelefone());
            stmt.setString(9, candidato.getSituacao());
            stmt.setBoolean(10, candidato.getAceiteTermo());
            stmt.setDate(11, candidato.getDataNascimento() != null ? 
                         Date.valueOf(candidato.getDataNascimento()) : null);
            stmt.setString(12, candidato.getSexo());
            stmt.setString(13, candidato.getNacionalidade());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        candidato.setIdCandidato(rs.getInt(1));
                    }
                }
                return true;
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao inserir candidato: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Buscar candidato por ID
    public Candidato buscarPorId(int id) {
        String sql = "SELECT * FROM candidato WHERE id_candidato = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extrairCandidato(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar candidato: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    // Buscar candidato por email
    public Candidato buscarPorEmail(String email) {
        String sql = "SELECT * FROM candidato WHERE email = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return extrairCandidato(rs);
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao buscar candidato por email: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }
    
    // Listar todos os candidatos
    public List<Candidato> listarTodos() {
        List<Candidato> candidatos = new ArrayList<>();
        String sql = "SELECT * FROM candidato ORDER BY data_solicitacao DESC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                candidatos.add(extrairCandidato(rs));
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao listar candidatos: " + e.getMessage());
            e.printStackTrace();
        }
        
        return candidatos;
    }
    
    // Atualizar situação do candidato
    public boolean atualizarSituacao(int id, String novaSituacao) {
        String sql = "UPDATE candidato SET situacao = ? WHERE id_candidato = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, novaSituacao);
            stmt.setInt(2, id);
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar situação: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Atualizar token de validação do candidato
    public boolean atualizarToken(int id, String token) {
        String sql = "UPDATE candidato SET token_validacao = ? WHERE id_candidato = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, token);
            stmt.setInt(2, id);
            
            return stmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar token: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }
    
    // Método auxiliar para extrair candidato do ResultSet
    private Candidato extrairCandidato(ResultSet rs) throws SQLException {
        Candidato candidato = new Candidato();
        candidato.setIdCandidato(rs.getInt("id_candidato"));
        candidato.setTipo(rs.getString("tipo"));
        candidato.setNome(rs.getString("nome"));
        candidato.setEmail(rs.getString("email"));
        candidato.setSenha(rs.getString("senha"));
        candidato.setCpf(rs.getString("cpf"));
        candidato.setCnpj(rs.getString("cnpj"));
        candidato.setEndereco(rs.getString("endereco"));
        candidato.setTelefone(rs.getString("telefone"));
        candidato.setSituacao(rs.getString("situacao"));
        candidato.setAceiteTermo(rs.getBoolean("aceite_termo"));
        
        Timestamp timestamp = rs.getTimestamp("data_solicitacao");
        if (timestamp != null) {
            candidato.setDataSolicitacao(timestamp.toLocalDateTime());
        }
        
        Date dataNasc = rs.getDate("data_nascimento");
        if (dataNasc != null) {
            candidato.setDataNascimento(dataNasc.toLocalDate());
        }
        
        candidato.setSexo(rs.getString("sexo"));
        candidato.setNacionalidade(rs.getString("nacionalidade"));
        candidato.setTokenValidacao(rs.getString("token_validacao"));
        
        return candidato;
    }
}
