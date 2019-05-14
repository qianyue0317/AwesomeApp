package com.qy.j4u.model.http;

import com.qy.j4u.utils.JLog;

import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;
import okio.BufferedSource;

public class JHttpLoggingInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();


        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;

        Connection connection = chain.connection();
        StringBuilder requestMessage = new StringBuilder("--> "
                + request.method()
                + ' ' + request.url()
                + (connection != null ? " " + connection.protocol() : ""));
        if (hasRequestBody) {
            requestMessage.append(" (").append(requestBody.contentLength()).append("-byte body)");
        }
//        JLog.i(requestMessage);

        if (hasRequestBody) {
            // Request body headers are only present when installed as a network interceptor. Force
            // them to be included (when available) so there values are known.
            if (requestBody.contentType() != null) {
                requestMessage.append("\n" + "Content-Type: ").append(requestBody.contentType());
            }
            if (requestBody.contentLength() != -1) {
                requestMessage.append("\n" + "Content-Length: ").append(requestBody.contentLength());
            }
        }

        requestMessage.append("\n");

        Headers headers = request.headers();
        for (int i = 0, count = headers.size(); i < count; i++) {
            String name = headers.name(i);
            // Skip headers from the request body as they are explicitly logged above.
            if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                requestMessage.append(name).append(": ").append(headers.value(i));
            }
        }

        if (!hasRequestBody) {
            requestMessage.append("--> END ").append(request.method());
        } else if (bodyEncoded(request.headers())) {
            requestMessage.append("--> END ").append(request.method()).append(" (encoded body " +
                    "omitted)");
        } else {
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);

            Charset charset = UTF8;
            MediaType contentType = requestBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }

            if (isPlaintext(buffer)) {
                requestMessage.append(buffer.readString(charset));
                requestMessage.append("--> END ").append(request.method()).append(" (").append(requestBody.contentLength()).append("-byte body)");
            } else {
                requestMessage.append("--> END ").append(request.method()).append(" (binary ").append(requestBody.contentLength()).append("-byte body omitted)");
            }
        }

        JLog.i(requestMessage);

        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            JLog.i("<-- HTTP FAILED: " + e);
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        StringBuilder responseMessage = new StringBuilder();
        ResponseBody responseBody = response.body();
        long contentLength = responseBody.contentLength();
        String bodySize = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
        responseMessage.append("<-- ").append(response.code()).append(response.message().isEmpty() ? "" : ' ' + response.message()).append(' ').append(response.request().url()).append(" (").append(tookMs).append("ms").append(", ").append(bodySize).append(" body").append(')');


        Headers responseHeaders = response.headers();
        for (int i = 0, count = responseHeaders.size(); i < count; i++) {
            responseMessage.append(responseHeaders.name(i)).append(": ").append(responseHeaders.value(i));
        }

        if (!HttpHeaders.hasBody(response)) {
            responseMessage.append("<-- END HTTP");
        } else if (bodyEncoded(response.headers())) {
            responseMessage.append("<-- END HTTP (encoded body omitted)");
        } else {
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE); // Buffer the entire body.
            Buffer buffer = source.buffer();

            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                charset = contentType.charset(UTF8);
            }

            if (!isPlaintext(buffer)) {
                responseMessage.append("");
                responseMessage.append("<-- END HTTP (binary ").append(buffer.size()).append(
                        "-byte body omitted)");
                return response;
            }

            if (contentLength != 0) {
                responseMessage.append("");
                responseMessage.append(buffer.clone().readString(charset));
            }

            responseMessage.append("<-- END HTTP (").append(buffer.size()).append("-byte body)");
        }

        JLog.i(responseMessage);

        return response;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small
     * sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    static boolean isPlaintext(Buffer buffer) {
        try {
            Buffer prefix = new Buffer();
            long byteCount = buffer.size() < 64 ? buffer.size() : 64;
            buffer.copyTo(prefix, 0, byteCount);
            for (int i = 0; i < 16; i++) {
                if (prefix.exhausted()) {
                    break;
                }
                int codePoint = prefix.readUtf8CodePoint();
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
                    return false;
                }
            }
            return true;
        } catch (EOFException e) {
            return false; // Truncated UTF-8 sequence.
        }
    }

    private boolean bodyEncoded(Headers headers) {
        String contentEncoding = headers.get("Content-Encoding");
        return contentEncoding != null && !contentEncoding.equalsIgnoreCase("identity");
    }
}
