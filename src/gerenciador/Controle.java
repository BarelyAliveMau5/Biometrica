/*
 * Copyright 2017 BarelyAliveMau5.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package gerenciador;

import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * interage com o banco de dados
 * @author BarelyAliveMau5
 */
public class Controle {
    private static final Logger LOGGER = Logger.getLogger(Controle.class.getName());
    private static String dir;
    private final BancoDeDados banco;

    public static String getDir() {
        return dir;
    }
    
    public static void inicializar() {
        LOGGER.setLevel(Level.ALL);
        Controle.dir = System.getProperty("user.dir");  // current dir
    }

    /**
     * inicializa o banco de dados, cria as pastas se necessario, etc.
     */
    public Controle() {
        File dados = new File(Controle.dir, "dados");
        try {
            if (!dados.isDirectory()) {
                LOGGER.warning("Diretório dedados inexistente, tentando criar pasta..");
                dados.mkdirs();
            } 
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Erro criando pasta de dados: {0}", ex.toString());
            banco = null;
            return;
        }
        banco = new BancoDeDados(dados);
    }
    
    /**
     * testa a digital fornecida e retorna a id do usuario mais provavel
     * @param digital arquivo com a digital
     * @return perfil do usuario mais provavel
     */
    public Perfil testeUsuario(File digital) {
        return testeUsuario(digital, null);
    }
    
    /**
     * testa a digital fornecida e retorna a id do usuario mais provavel
     * @param digital arquivo com a digital
     * @param dpi densidade de pixels da imagem
     * @return perfil do usuario mais provavel
     */
    public Perfil testeUsuario(File digital, Double dpi) {
        byte[] probe;
        try {
            probe = Files.readAllBytes(digital.toPath());
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Erro ao ler arquivo da digital: ", ex);
            return null;
        }
        
        FingerprintMatcher matcher;
        if (dpi != null)
            matcher = new FingerprintMatcher(new FingerprintTemplate(probe, dpi));
        else
            matcher = new FingerprintMatcher(new FingerprintTemplate(probe));
        
        Perfil usuario = null;
        double maior = 0;
        for (Perfil perfil : banco.perfis()) {
            double pontuacao = matcher.match(perfil.getTemplate());
            if (pontuacao > maior) {
                maior = pontuacao;
                usuario = perfil;
            }
        }
        double consideravel = 40;
        if (maior >= consideravel && usuario != null) 
            return usuario;
        else
            return null;
    }
    
    /**
     * Cria um novo usuario no banco de dados
     * @param digital imagem da digital
     * @param dpi pixels por polegada da digital
     * @param nome
     * @param posicao cargo
     * @param imagem_perfil imagem usada no perfil do usuairo
     * @return id do novo usuario
     */
    protected int criarUsuario(File digital, Double dpi, String nome, 
            Perfil.Posicoes posicao, byte[] imagem_perfil) {
        
        int id = banco.getProximoID();
        byte[] img;
        
        try {
            img = Files.readAllBytes(digital.getAbsoluteFile().toPath());
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Erro ao ler imagem. ", ex);
            return 0;
        }
        
        if (testeUsuario(digital, dpi) != null) {
            LOGGER.log(Level.WARNING, "Digital já cadastrado.");
            return -1;
        }
        
        Perfil novo_pefil;
        if (dpi != null)
            novo_pefil = new Perfil(id, nome, posicao, new FingerprintTemplate(img, dpi));
        else
            novo_pefil = new Perfil(id, nome, posicao, new FingerprintTemplate(img));
        
        if (imagem_perfil != null) 
            novo_pefil.setImagemPerfil(imagem_perfil);
        
        return banco.salvarPerfil(novo_pefil);
    }
}
