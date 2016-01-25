package utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by tigran on 11/29/15.
 */
public class MyHttpClient {

    private final static Logger logger = LoggerFactory.getLogger(MyHttpClient.class);

    private static DefaultHttpClient client = new DefaultHttpClient();
    private final static String USER_AGENT = "Mozilla/5.0";


    public static HttpResponse sendPost(String url, String body, String authorizationHeader) throws IOException {
        HttpPost post = new HttpPost(url);
        HttpResponse response = null;

        // add headers
        post.setHeader("User-Agent", USER_AGENT);
        post.setHeader("Content-type", "application/json");
        post.setHeader("authorization", authorizationHeader);

        try {
            logger.info("Send post request to [{}] endpoint using the [{}] body.",
                    new Object[]{url, body});
            HttpEntity entity = new ByteArrayEntity(body.getBytes("UTF-8"));
            post.setEntity(entity);
            response = client.execute(post);
            String result = EntityUtils.toString(response.getEntity());

            logger.info("The response code is [{}], and the response body is \n [{}]",
                    new Object[] {response.getStatusLine().getStatusCode(), result});

        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException error occurred during post request\n, {}",
                    new Object[]{e.getMessage()});
        } catch (ClientProtocolException e) {
            logger.error("ClientProtocolException error occurred during post request\n, {}",
                    new Object[]{e.getMessage()});
        } catch (IOException e) {
            logger.error("ClientProtocolException error occurred during post request\n, {}",
                    new Object[]{e.getMessage()});
        }
        return response;
    }

}
