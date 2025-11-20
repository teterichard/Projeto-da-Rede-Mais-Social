
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;


import java.io.*;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class WebServer {
    private static final int PORT = 8080;
    private static final String WEB_DIR = "web";
    
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        
        // Servir arquivos estáticos (HTML, CSS, JS)
        server.createContext("/", new StaticFileHandler());
        
        // API endpoints
        server.createContext("/api/solicitar-afiliacao", new SolicitarAfiliacaoHandler());
        server.createContext("/api/validar-email", new ValidarEmailHandler());
        server.createContext("/api/verificar-email", new VerificarEmailHandler());
        
        server.setExecutor(null);
        server.start();
        
        System.out.println("===============================================");
        System.out.println("   REDE MAIS SOCIAL - Servidor Web Iniciado");
        System.out.println("===============================================");
        System.out.println();
        System.out.println("Acesse o sistema em: http://localhost:" + PORT);
        System.out.println();
        System.out.println("Páginas disponíveis:");
        System.out.println("  - http://localhost:" + PORT + "/index.html");
        System.out.println("  - http://localhost:" + PORT + "/login.html");
        System.out.println("  - http://localhost:" + PORT + "/tipo-afiliacao.html");
        System.out.println();
        System.out.println("Pressione Ctrl+C para parar o servidor");
        System.out.println("===============================================");
    }
    
    // Handler para arquivos estáticos
    static class StaticFileHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String path = exchange.getRequestURI().getPath();
            
            // Redirecionar / para /index.html
            if (path.equals("/")) {
                path = "/index.html";
            }
            
            File file = new File(WEB_DIR + path);
            
            if (file.exists() && !file.isDirectory()) {
                String contentType = getContentType(path);
                byte[] content = Files.readAllBytes(file.toPath());
                
                exchange.getResponseHeaders().set("Content-Type", contentType);
                exchange.sendResponseHeaders(200, content.length);
                
                OutputStream os = exchange.getResponseBody();
                os.write(content);
                os.close();
            } else {
                String response = "404 - Arquivo não encontrado";
                exchange.sendResponseHeaders(404, response.length());
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }
        
        private String getContentType(String path) {
            if (path.endsWith(".html")) return "text/html; charset=UTF-8";
            if (path.endsWith(".css")) return "text/css; charset=UTF-8";
            if (path.endsWith(".js")) return "application/javascript; charset=UTF-8";
            if (path.endsWith(".json")) return "application/json; charset=UTF-8";
            if (path.endsWith(".png")) return "image/png";
            if (path.endsWith(".jpg") || path.endsWith(".jpeg")) return "image/jpeg";
            return "text/plain";
        }
    }
    
    // Handler para solicitar afiliação
    static class SolicitarAfiliacaoHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                // Habilitar CORS
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
                
                try {
                    // Ler o corpo da requisição
                    String body = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))
                            .lines().collect(Collectors.joining("\n"));
                    
                    System.out.println("📨 Recebido JSON: " + body);
                    Map<String, String> dados = parseJSON(body);
                    System.out.println("📦 Dados parseados: " + dados);
                    
                    // Criar candidato
                    Candidato candidato = new Candidato();
                    candidato.setNome(dados.get("nome"));
                    candidato.setEmail(dados.get("email"));
                    
                    // CPF e CNPJ - garantir que não sejam null
                    String cpf = dados.get("cpf");
                    String cnpj = dados.get("cnpj");
                    
                    if (cpf != null && !cpf.equals("null") && !cpf.trim().isEmpty()) {
                        candidato.setCpf(cpf);
                        System.out.println("✅ CPF recebido: " + cpf);
                    }
                    
                    if (cnpj != null && !cnpj.equals("null") && !cnpj.trim().isEmpty()) {
                        candidato.setCnpj(cnpj);
                        System.out.println("✅ CNPJ recebido: " + cnpj);
                    }
                    
                    candidato.setSexo(dados.get("sexo"));
                    
                    // Converter data de nascimento String para LocalDate
                    try {
                        String dataNascStr = dados.get("dataNascimento");
                        if (dataNascStr != null && !dataNascStr.isEmpty()) {
                            candidato.setDataNascimento(java.time.LocalDate.parse(dataNascStr));
                        }
                    } catch (Exception e) {
                        System.err.println("Erro ao converter data: " + e.getMessage());
                    }
                    
                    candidato.setNacionalidade(dados.get("nacionalidade"));
                    candidato.setEndereco(dados.get("endereco"));
                    candidato.setTelefone(dados.get("telefone"));
                    candidato.setTipoPerfil(dados.get("apresentacao"));
                    candidato.setTipo("VOLUNTARIO");
                    candidato.setSenha("temp123"); // Senha temporária
                    candidato.setAceiteTermo(true); // Usuário aceitou os termos
                    
                    // Usar o controller e DAO
                    AfiliacaoController controller = new AfiliacaoController();
                    CandidatoDAO dao = new CandidatoDAO();
                    
                    // Verificar se email já existe
                    Candidato existente = dao.buscarPorEmail(candidato.getEmail());
                    if (existente != null) {
                        String response = "{\"success\": false, \"message\": \"E-mail já cadastrado no sistema.\"}";
                        exchange.sendResponseHeaders(400, response.getBytes("UTF-8").length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes("UTF-8"));
                        os.close();
                        return;
                    }
                    
                    // Criar candidato
                    Candidato criado = controller.criarCandidato(candidato);
                    
                    if (criado != null) {
                        // Aceitar termos e enviar validação
                        boolean enviado = controller.aceitarTermosEEnviarValidacao(criado);
                        
                        if (enviado) {
                            // Retornar token na resposta para mostrar ao usuário
                            String response = "{\"success\": true, \"message\": \"Candidato criado com sucesso. E-mail de validação enviado.\", \"token\": \"" + criado.getTokenValidacao() + "\"}";
                            exchange.sendResponseHeaders(200, response.getBytes("UTF-8").length);
                            OutputStream os = exchange.getResponseBody();
                            os.write(response.getBytes("UTF-8"));
                            os.close();
                        } else {
                            String response = "{\"success\": false, \"message\": \"Erro ao enviar e-mail de validação.\"}";
                            exchange.sendResponseHeaders(500, response.getBytes("UTF-8").length);
                            OutputStream os = exchange.getResponseBody();
                            os.write(response.getBytes("UTF-8"));
                            os.close();
                        }
                    } else {
                        String response = "{\"success\": false, \"message\": \"Erro ao criar candidato.\"}";
                        exchange.sendResponseHeaders(500, response.getBytes("UTF-8").length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes("UTF-8"));
                        os.close();
                    }
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    String response = "{\"success\": false, \"message\": \"Erro interno: " + e.getMessage() + "\"}";
                    exchange.sendResponseHeaders(500, response.getBytes("UTF-8").length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes("UTF-8"));
                    os.close();
                }
            } else {
                exchange.sendResponseHeaders(405, 0);
                exchange.getResponseBody().close();
            }
        }
    }
    
    // Handler para validar email
    static class ValidarEmailHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
                
                try {
                    String body = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))
                            .lines().collect(Collectors.joining("\n"));
                    
                    Map<String, String> dados = parseJSON(body);
                    String email = dados.get("email");
                    String token = dados.get("token");
                    
                    AfiliacaoController controller = new AfiliacaoController();
                    boolean valido = controller.validarEmail(email, token);
                    
                    if (valido) {
                        String response = "{\"success\": true, \"message\": \"E-mail validado com sucesso!\"}";
                        exchange.sendResponseHeaders(200, response.getBytes("UTF-8").length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes("UTF-8"));
                        os.close();
                    } else {
                        String response = "{\"success\": false, \"message\": \"Token inválido ou expirado.\"}";
                        exchange.sendResponseHeaders(400, response.getBytes("UTF-8").length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes("UTF-8"));
                        os.close();
                    }
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    String response = "{\"success\": false, \"message\": \"Erro interno: " + e.getMessage() + "\"}";
                    exchange.sendResponseHeaders(500, response.getBytes("UTF-8").length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes("UTF-8"));
                    os.close();
                }
            } else {
                exchange.sendResponseHeaders(405, 0);
                exchange.getResponseBody().close();
            }
        }
    }
    
    // Handler para verificar se email já existe
    static class VerificarEmailHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
                exchange.getResponseHeaders().add("Content-Type", "application/json; charset=UTF-8");
                
                try {
                    String body = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))
                            .lines().collect(Collectors.joining("\n"));
                    
                    Map<String, String> dados = parseJSON(body);
                    String email = dados.get("email");
                    
                    CandidatoDAO dao = new CandidatoDAO();
                    Candidato candidato = dao.buscarPorEmail(email);
                    
                    if (candidato != null) {
                        String response = "{\"exists\": true}";
                        exchange.sendResponseHeaders(200, response.getBytes("UTF-8").length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes("UTF-8"));
                        os.close();
                    } else {
                        String response = "{\"exists\": false}";
                        exchange.sendResponseHeaders(200, response.getBytes("UTF-8").length);
                        OutputStream os = exchange.getResponseBody();
                        os.write(response.getBytes("UTF-8"));
                        os.close();
                    }
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    String response = "{\"success\": false, \"message\": \"Erro interno: " + e.getMessage() + "\"}";
                    exchange.sendResponseHeaders(500, response.getBytes("UTF-8").length);
                    OutputStream os = exchange.getResponseBody();
                    os.write(response.getBytes("UTF-8"));
                    os.close();
                }
            } else {
                exchange.sendResponseHeaders(405, 0);
                exchange.getResponseBody().close();
            }
        }
    }
    
    // Parser JSON simples
    private static Map<String, String> parseJSON(String json) {
        Map<String, String> map = new HashMap<>();
        json = json.trim().replaceAll("^\\{|\\}$", "");
        
        String[] pairs = json.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":", 2);
            if (keyValue.length == 2) {
                String key = keyValue[0].trim().replaceAll("^\"|\"$", "");
                String value = keyValue[1].trim().replaceAll("^\"|\"$", "");
                // Não adicionar valores null como string
                if (!value.equals("null")) {
                    map.put(key, value);
                }
            }
        }
        return map;
    }
}
