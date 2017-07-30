package br.edu.ifrn.util.sinapi;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class DownloadSINAPI {

    // TODO:  
    String urlArquivo = "http://www.caixa.gov.br/Downloads/sinapi-a-partir-jul-2009-rn/SINAPI_ref_Insumos_Composicoes_RN_052017_Desonerado.zip";
    URL url = null;
    InputStream inputStream = null;

    public String go() {

        try {
            url = new URL(urlArquivo);
            
            // Para não ter problemas com autenticação
            CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));

            inputStream = url.openStream();

            try (ZipInputStream zin = new ZipInputStream(inputStream)) {
                ZipEntry ze = null;
                
                while ((ze = zin.getNextEntry()) != null) {
                    if (ze.getName().toUpperCase().contains("INSUMOS_RN_052017_DESONERADO.XLS")) {
                        
                        System.out.println("Unzipping " + ze.getName());
                        
                        try (FileOutputStream fout = new FileOutputStream(ze.getName())) {
                            
                            System.out.println("JUST ONE: " + ze.getName());
                            
                            for (int c = zin.read(); c != -1; c = zin.read()) {
                                fout.write(c);
                            }
                            zin.closeEntry();
                        }
                        
                        return ze.getName();
                    }

                }
            }

        } catch (FileNotFoundException e) {
        } catch (IOException x) {
        }
        return null;

    }

}
