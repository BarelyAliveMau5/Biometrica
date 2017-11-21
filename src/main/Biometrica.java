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
package main;

import gerenciador.Controle;
import ui.frmAutenticar;
import ui.frmNovoPerfil;

/**
 *
 * @author BarelyAliveMau5
 */
public class Biometrica {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        gerenciador.Controle.inicializar();
        Controle ctl = new Controle();
        
        if (args.length > 0) 
            if (args[0].equals("-c")) {
                System.out.println("Executando menu de criação");
                frmNovoPerfil novo_perf = new frmNovoPerfil(ctl);
                novo_perf.setVisible(true);
                return;
            }
        frmAutenticar autent = new frmAutenticar(ctl);
        autent.setVisible(true);
    }
    
}
