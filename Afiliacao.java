
import java.time.LocalDateTime;

public class Afiliacao {
    private Integer idAfiliacao;
    private Integer idCandidato;
    private LocalDateTime dataSolicitacao;
    private String status; // "AGUARDANDO_VALIDACAO", "AGUARDANDO_APROVACAO", "APROVADO", "REJEITADO", "BLOQUEADO"
    private LocalDateTime dataAprovacao;
    private String motivoRejeicao;

    // Construtor vazio
    public Afiliacao() {
        this.dataSolicitacao = LocalDateTime.now();
        this.status = "AGUARDANDO_VALIDACAO";
    }

    // Construtor com idCandidato
    public Afiliacao(Integer idCandidato) {
        this();
        this.idCandidato = idCandidato;
    }

    // Getters e Setters
    public Integer getIdAfiliacao() {
        return idAfiliacao;
    }

    public void setIdAfiliacao(Integer idAfiliacao) {
        this.idAfiliacao = idAfiliacao;
    }

    public Integer getIdCandidato() {
        return idCandidato;
    }

    public void setIdCandidato(Integer idCandidato) {
        this.idCandidato = idCandidato;
    }

    public LocalDateTime getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDateTime dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDataAprovacao() {
        return dataAprovacao;
    }

    public void setDataAprovacao(LocalDateTime dataAprovacao) {
        this.dataAprovacao = dataAprovacao;
    }

    public String getMotivoRejeicao() {
        return motivoRejeicao;
    }

    public void setMotivoRejeicao(String motivoRejeicao) {
        this.motivoRejeicao = motivoRejeicao;
    }

    @Override
    public String toString() {
        return "Afiliacao{" +
                "idAfiliacao=" + idAfiliacao +
                ", idCandidato=" + idCandidato +
                ", status='" + status + '\'' +
                ", dataSolicitacao=" + dataSolicitacao +
                '}';
    }
}
