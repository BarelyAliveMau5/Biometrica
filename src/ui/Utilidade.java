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
package ui;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * guarda métodos genéricos usados com frequencia
 * @author BarelyAliveMau5
 */
public class Utilidade {
    private static File ultimo_dir = null;
    
    public static File carregarImagem(javax.swing.JLabel lbl) {
        JFileChooser escolhedor = new JFileChooser();
        if (Utilidade.ultimo_dir == null) {
            escolhedor.setCurrentDirectory(new File(System.getProperty("user.dir")));
        }
        else
            escolhedor.setCurrentDirectory(Utilidade.ultimo_dir);
        
        FileNameExtensionFilter filtro; 
        filtro = new FileNameExtensionFilter("Imagens (*.jpg, *.png)", "png","jpg","jpeg");
      
        escolhedor.addChoosableFileFilter(filtro);
        escolhedor.removeChoosableFileFilter(escolhedor.getFileFilter());
        escolhedor.setFileFilter(filtro);
        
        escolhedor.setMultiSelectionEnabled(false);
        int resultado = escolhedor.showOpenDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File arquivo = escolhedor.getSelectedFile();
            lbl.setIcon(new ImageIcon(new ImageIcon(arquivo.getAbsolutePath())
                    .getImage()
                    .getScaledInstance(lbl.getWidth(), lbl.getHeight(), Image.SCALE_DEFAULT)));
            Utilidade.ultimo_dir = arquivo.getParentFile();
            return arquivo;
        }
        return null;
    }
}
