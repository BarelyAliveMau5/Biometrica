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

import com.machinezoo.sourceafis.FingerprintTemplate;

/**
 *
 * @author BarelyAliveMau5
 */
public class Perfil {
    public enum Posicoes {
        funcionario ,
        diretor,
        ministro_do_meio_ambiente
    }
    
    private final int id;
    private final String nome;
    private final Posicoes posicao;
    private byte[] imagem_perfil;
    private final FingerprintTemplate template;
    private final String digital_str;
            
    public Perfil(int id, String nome, Posicoes posicao, FingerprintTemplate digital) {
        this.id = id;
        this.nome = nome;
        this.posicao = posicao;
        this.imagem_perfil = null;
        this.template = digital;
        digital_str = digital.json();
    }
    
    public String getNome() {
        return nome;
    }

    public Posicoes getPosicao() {
        return posicao;
    }

    public byte[] getImagem_perfil() {
        return imagem_perfil;
    }
    
    public int getID() {
        return id;
    }
    
    public FingerprintTemplate getTemplate() {
        return template;
    }
    
    public void setImagemPerfil(byte[] imagem) {
        imagem_perfil = imagem;
    }
}
