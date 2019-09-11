package no.kristiania.httpsetup;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class httpClient {
    private String host;
    private int statusCode = 200;
    private String requestTarget;
    private String statusLine;

    public httpClient(String host, String requestTarget){
        this.host = host;
        this.requestTarget = requestTarget;
    }

    public static void main(String[] args) throws IOException {
        new httpClient("urlecho.appspot.com", "/echo?status=200&Content-Type=text%2Fhtml&body=Hello%20world!").executeRequest();

    }

    public HttpClientResponse executeRequest() throws IOException {
        try  (Socket socket = new Socket(host,80 )) {

            socket.getOutputStream().write(("GET " + requestTarget + " HTTP/1.1\n").getBytes());
            socket.getOutputStream().write("Host: urlecho.appspot.com\n".getBytes());
            socket.getOutputStream().write(("Connection: close\n".getBytes()));
            socket.getOutputStream().write("\n".getBytes());
            socket.getOutputStream().flush();

            HttpClientResponse httpClientResponse = new HttpClientResponse(socket);
            httpClientResponse.invoke();
            return httpClientResponse;
        }


    }


    public class HttpClientResponse {
        private Socket socket;

        public HttpClientResponse(Socket socket) {
            this.socket = socket;
        }

        public void invoke() throws IOException {
            InputStream input = socket.getInputStream();
            int c = input.read();

            StringBuilder statusLine = new StringBuilder();
            while ((c = input.read()) != -1 && c != '\r') {
                statusLine.append((char) c);
            }
            httpClient.this.statusLine = statusLine.toString();


            while ((c = input.read()) != -1) {
                System.out.print((char)c);
            }
        }

        public int getStatusCode() {
            return Integer.parseInt(statusLine.split(" ")[1]);
        }
    }
}
