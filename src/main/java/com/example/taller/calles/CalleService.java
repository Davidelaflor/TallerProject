package com.example.taller.calles;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Service;

@Service
public class CalleService {
    private static final String NOMINATIM_URL = "https://nominatim.openstreetmap.org/search";

    public String buscarDireccion(String direccion){//, String barrio, String ciudad, String pais) {
        try {
            // Construir la consulta con los parámetros direccion, barrio, ciudad y pais
            String query = String.join(", ", direccion);//, barrio, ciudad, pais);

            // Codificar la consulta para asegurarnos de que los caracteres especiales estén correctamente codificados
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());

            // Construir la URL completa para la API de Nominatim
            String urlStr = NOMINATIM_URL + "?q=" + encodedQuery + "&format=json&limit=1";
            System.out.println("URL generada para Nominatim: " + urlStr); // Imprimir para depurar

            // Crear un objeto URL con la URL que hemos generado
            URL url = new URL(urlStr);

            // Abrir la conexión HTTP
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            // Configurar el User-Agent
            con.setRequestProperty("User-Agent", "TallerProject/1.0 (mi-email@ejemplo.com)");

            // Verificar el código de respuesta
            int responseCode = con.getResponseCode();
            if (responseCode != 200) {
                return "Error al consultar la API de Nominatim: Código de respuesta " + responseCode;
            }

            // Leer la respuesta
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Retornar la respuesta en formato JSON
            return response.toString();

        } catch (Exception e) {
            return "Error al consultar la API de Nominatim: " + e.getMessage();
        }
    }
     // Método solo para validar la existencia de la dirección
     public boolean validarDireccion(String direccion) {
        try {
            String encodedQuery = URLEncoder.encode(direccion, StandardCharsets.UTF_8.toString());
            String urlStr = NOMINATIM_URL + "?q=" + encodedQuery + "&format=json&limit=1";
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "TallerProject/1.0 (mi-email@ejemplo.com)");

            int responseCode = con.getResponseCode();
            if (responseCode != 200) {
                throw new Exception("Error al consultar la API de Nominatim: Código de respuesta " + responseCode);
            }

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.length() > 0;  // Si hay respuesta, es válida
        } catch (Exception e) {
            return false;  // Si hay error, la dirección no es válida
        }
    }
}
