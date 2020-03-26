package app.edikodik.com.edikodik.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import app.edikodik.com.edikodik.R;
import app.edikodik.com.edikodik.utilities.Constants;
import okhttp3.OkHttpClient;

public class NetworkUtility {
    public static Picasso picasso;

//    public static String readUrl(String service, List<NameValuePair> nameValuePairs) {
//        return fetchFromServer(service, nameValuePairs, true);
//    }
//
//	public static Object readUrl(String service, List<NameValuePair> nameValuePairs, Class returnClass) {
//        String response = fetchFromServer(service, nameValuePairs, true);
//        return processResponse(response, returnClass);
//	}
//
//    public static String readUrlSansFormat(String service, List<NameValuePair> nameValuePairs) {
//        return fetchFromServer(service, nameValuePairs, false);
//    }

    public static String readSecuredUrl(Context context, String service, List<NameValuePair> nameValuePairs) {
        return fetchFromSecuredServer(context, service, nameValuePairs, true);
    }

    public static Object readSecuredUrl(Context context, String service, List<NameValuePair> nameValuePairs, Class returnClass) {
        String response = fetchFromSecuredServer(context, service, nameValuePairs, true);
        return processResponse(response, returnClass);
    }

    public static String readSecuredUrlSansFormat(Context context, String service, List<NameValuePair> nameValuePairs) {
        return fetchFromSecuredServer(context, service, nameValuePairs, false);
    }



    private static String fetchFromSecuredServer (Context context, String service, List<NameValuePair> nameValuePairs, boolean specifyFormat) {


        // TODO: Fetch from secure server begins:
        String urlString;
        if (specifyFormat) {
            urlString = Constants.baseUrl + service + Constants.format;
        } else {
            urlString = Constants.baseUrl + service;
        }
        Log.d("wid", "url: " + urlString);
        StringBuilder builder = new StringBuilder();

        // Add self-signed certificate to
        CertificateFactory cf = null;
        // Create an SSLContext that uses our TrustManager
        SSLContext sslContext = null;

        InputStream caInput = null;

        try {
            cf = CertificateFactory.getInstance("X.509");
            // Load CAs from an InputStream
            // (could be from a resource or ByteArrayInputStream or ...)
            caInput = context.getResources().openRawResource(R.raw.edikodik);// new BufferedInputStream(new FileInputStream("edikodik.cer")); // "load-der.crt"
            Certificate ca;

            ca = cf.generateCertificate(caInput);
            System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());

            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            // Create a TrustManager that trusts the CAs in our KeyStore
            String trustManagerFactoryAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(trustManagerFactoryAlgorithm);
            trustManagerFactory.init(keyStore);

            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } finally {
            try {
                caInput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Https request
        URL url = null;
        String response = "";
        try {
            url = new URL(urlString);
            HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();

            connection.setReadTimeout(15000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setSSLSocketFactory(sslContext.getSocketFactory());

            OutputStream outputStream = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            writer.write(getPostDataString(nameValuePairs));
            writer.flush();
            writer.close();
            outputStream.close();

            InputStream in = connection.getInputStream();

            int responseCode = connection.getResponseCode();


            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("wid", "RESPONSE (Secured connection): " + response);

        return response;
    }

    private static String fetchFromServer(String service, List<NameValuePair> nameValuePairs, boolean specifyFormat) {
        String url;
        if (specifyFormat) {
            url = Constants.baseUrl + service + Constants.format;
        } else {
            url = Constants.baseUrl + service;
        }
        Log.d("wid", "url: " + url);
        StringBuilder builder = new StringBuilder();

        HttpClient client = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);

        // HttpGet httpGet = new HttpGet(url);

        try {


            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            Log.d("wid", "execute");
            HttpResponse response = client.execute(httpPost);
            Log.d("wid", "got response");
            StatusLine statusLine = response.getStatusLine();
            Log.d("wid", "Status line");
            int statusCode = statusLine.getStatusCode();
            Log.d("wid", "Status code:" + statusCode);
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } else {
                //	CommonUtilities.showAlertDialog(this, "Error!", "Failed to load. Check Internet Connection", false);
            }
        } catch (ClientProtocolException e) {
            //CommonUtilities.showAlertDialog(this, "Error!!", "Failed to load. Check Internet Connection", false);
            e.printStackTrace();
        } catch (IOException e) {
//			Toast toast = Toast.makeText(CseListActivity.this, "Please Check Internet Connection", 5000);
            e.printStackTrace();
        }
        // return builder.toString();
        String response = builder.toString();
        Log.d("wid", "RESPONSE: " + response);
        return response;
    }


	public static Object processResponse(String response, Class returnClass) {
        try {
            return GsonProcessor.convertToObject(response, returnClass);
        } catch (Exception e) {
            Log.d("wid", "Couldn't convert response. " + e.getMessage());
            return null;
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        return ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE))
                .getActiveNetworkInfo() != null;
    }


    private static String getPostDataString(List<NameValuePair> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(NameValuePair entry : params){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        Log.d("wid", "Requested post data: " + result.toString());

        return result.toString();
    }



    public static Picasso getPicassoInstance(Context context) {

        int a = 1;
        if (a == 1) return null;
        // SSL Context

        // Add self-signed certificate to
        CertificateFactory cf = null;
        // Create an SSLContext that uses our TrustManager
        SSLContext sslContext = null;

        InputStream caInput = null;

        try {
            cf = CertificateFactory.getInstance("X.509");
            // Load CAs from an InputStream
            // (could be from a resource or ByteArrayInputStream or ...)
            caInput = context.getResources().openRawResource(R.raw.edikodik);// new BufferedInputStream(new FileInputStream("edikodik.cer")); // "load-der.crt"
            Certificate ca;

            ca = cf.generateCertificate(caInput);
            System.out.println("ca=" + ((X509Certificate) ca).getSubjectDN());

            String keyStoreType = KeyStore.getDefaultType();
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);

            // Create a TrustManager that trusts the CAs in our KeyStore
            String trustManagerFactoryAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(trustManagerFactoryAlgorithm);
            trustManagerFactory.init(keyStore);

            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } finally {
            try {
                caInput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (picasso == null) {
            InputStream keyStore = context.getResources().openRawResource(R.raw.edikodik);
            Picasso.Builder builder = new Picasso.Builder(context);


            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .sslSocketFactory(sslContext.getSocketFactory())
                    .build();

            try {
                OkHttp3Downloader okHttpDownloader = new OkHttp3Downloader(okHttpClient);
                builder.downloader(okHttpDownloader);
                picasso = builder.build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            Picasso.setSingletonInstance(picasso);
        } catch (IllegalStateException e) {
            Log.d("wid", "Picasso singleton: " + e.getMessage());
            // Picasso instance was already set
            // cannot set it after Picasso.with(Context) was already in use
        }

        return picasso;
    }

}