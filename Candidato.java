
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Candidato {
    private Integer idCandidato;
    private String tipo; // "VOLUNTARIO" ou "ONG"
    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private String cnpj;
    private LocalDateTime dataSolicitacao;
    private String endereco;
    private String telefone;
    private String situacao; // "AGUARDANDO_VALIDACAO", "APROVADO", "REJEITADO"
    private Boolean aceiteTermo;
    private LocalDateTime dataValidacaoEmail;
    private String tokenValidacao;
    private String tipoPerfil;
    private LocalDate dataNascimento;
    private String sexo;
    private String nacionalidade;

    // Construtor vazio
    public Candidato() {
        this.dataSolicitacao = LocalDateTime.now();
        this.situacao = "AGUARDANDO_VALIDACAO";
        this.aceiteTermo = false;
    }

    // Construtor completo
    public Candidato(String tipo, String nome, String email, String senha) {
        this();
        this.tipo = tipo;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    // Getters e Setters
    public Integer getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(Integer idCandidato) {
        this.idCandidato = idCandidato;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public LocalDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public Boolean getAceiteTermo() {
        return aceiteTermo;
    }

    public void setAceiteTermo(Boolean aceiteTermo) {
        this.aceiteTermo = aceiteTermo;
    }

    public LocalDateTime getDataValidacaoEmail() {
        return dataValidacaoEmail;
    }

    public void setDataValidacaoEmail(LocalDateTime dataValidacaoEmail) {
        this.dataValidacaoEmail = dataValidacaoEmail;
    }

    public String getTokenValidacao() {
        return tokenValidacao;
    }

    public void setTokenValidacao(String tokenValidacao) {
        this.tokenValidacao = tokenValidacao;
    }

    public String getTipoPerfil() {
        return tipoPerfil;
    }

    public void setTipoPerfil(String tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    @Override
    public String toString() {
        return "Candidato{" +
                "idCandidato=" + idCandidato +
                ", tipo='" + tipo + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", situacao='" + situacao + '\'' +
                '}';
    }
}
