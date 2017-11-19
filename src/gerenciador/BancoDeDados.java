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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * banco de dados disperso usando JSON como armazenamento 
 * @author BarelyAliveMau5
 */
public class BancoDeDados {
    private static final Logger LOGGER = Logger.getLogger(BancoDeDados.class.getName());
    
    private class data_json {
        public ArrayList<String> arquivos;  // arquivos de perfis
        public Integer last_id;
        
        public data_json() {
            last_id = 1;
            arquivos = new ArrayList<>();
        }
    }

    private final File arquivo_db;
    private final File pasta_db;
    private data_json dados_arquivos;
    
    /**
     * cria/usa arquivo de banco de dados
     * @param caminho diretorio usado na criação/uso do arquivo de dados do banco de dados
     */
    public BancoDeDados(File caminho) {
        arquivo_db = new File(caminho, "index.json");
        dados_arquivos = new data_json();
        this.pasta_db = caminho;
        try {
            if (!arquivo_db.exists()) {
                LOGGER.warning("Arquivo de banco de dados inexistente. Tentando criar um..");
                Gson gson = new GsonBuilder().create();
                Writer writer = new FileWriter(arquivo_db);
                gson.toJson(dados_arquivos,writer);
                writer.flush();
                dados_arquivos.last_id = 1;
            }
            else {
                Gson gson = new Gson();
                data_json t =  gson.fromJson(new FileReader(arquivo_db), data_json.class);
                if (t != null)
                    dados_arquivos = t;
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Erro criando database", ex);
        }
    }
    
    private String fazerNomeArquivo(int id) {
        return "perfil_" + String.valueOf(id) + ".json";
    }
 
    /**
     * salva os dados do obj perfil num arquivo .json e inclui na lista data.json
     * @param perfil dados do perfil
     * @return id do perfil salvo, 0 caso ocorra erro
     */
    public int salvarPerfil(Perfil perfil) {
        try {
            Gson gson = new GsonBuilder().create();
            
            Writer writer = new FileWriter(new File(pasta_db, fazerNomeArquivo(perfil.getID())));
            gson.toJson(perfil, writer);
            writer.flush();
            
            dados_arquivos.last_id++;
            dados_arquivos.arquivos.add(String.valueOf(fazerNomeArquivo(perfil.getID())));
            
            writer = new FileWriter(arquivo_db.getPath());
            gson.toJson(dados_arquivos, writer);
            writer.flush();
            
            return perfil.getID();
            
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Erro salvando perfil. ", ex);
        }
        return 0;
    }
    
    public ArrayList<Perfil> perfis(){
        Gson gson = new Gson();
        ArrayList<Perfil> perfis = new ArrayList<>();
        this.dados_arquivos.arquivos.forEach((String arquivo) -> {
            try {
                Perfil perfil = gson.fromJson(new FileReader(new File(pasta_db, arquivo).getPath()), Perfil.class);
                perfis.add(perfil);
            } catch (FileNotFoundException ex) {
                LOGGER.log(Level.SEVERE, "arquivo não encontrado: ", arquivo);
            }
        });
        return perfis;
    }
    
    public Perfil getPerfil(int id) {
        for (Perfil perfil : perfis()) {
            if (perfil.getID() == id)
                return perfil;
        }
        return null;
    }
    
    public int getProximoID() {
        return dados_arquivos.last_id;
    } 
}
