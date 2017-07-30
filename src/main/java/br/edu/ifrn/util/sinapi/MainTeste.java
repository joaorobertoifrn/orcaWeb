package br.edu.ifrn.util.sinapi;

import java.io.IOException;
import java.time.LocalDateTime;

public class MainTeste {

    public static void main(String[] args) throws IOException {

        String str = new DownloadSINAPI().go();
        
        LocalDateTime lv = LocalDateTime.now();
        
        System.out.println("Tempo: " + lv.getSecond());
        ImportaTabelaSINAPI importacao = new ImportaTabelaSINAPI();
        importacao.importar(str);

    }  
}
