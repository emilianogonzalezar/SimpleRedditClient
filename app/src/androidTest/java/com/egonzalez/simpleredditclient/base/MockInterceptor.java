package com.egonzalez.simpleredditclient.base;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class MockInterceptor implements Interceptor {

    private static class MockJson {
        @SerializedName("mock_status")
        @Expose
        public Integer mockStatus;

        @SerializedName("mock_body")
        @Expose
        public Object mockBody;
    }

    private final Context mContext;

    private final List<Uri> mResponses;
    private int mResponseIndex = 0;

    public MockInterceptor(final List<Uri> responses, final Context context) {
        mResponses = responses;
        mContext = context;
    }

    @Nullable
    @Override
    public Response intercept(final Chain chain) throws IOException {
        Response response = null;

        if (mResponseIndex < mResponses.size()) {
            final Uri mockResponse = mResponses.get(mResponseIndex);
            final InputStream stream = mContext.getAssets().open(mockResponse.toString());
            if (stream != null) {
                final String json = IOUtils.toString(stream);
                final MockJson mockJson = EspressoTest.GSON.fromJson(json, MockJson.class);
                final Integer mockStatus = mockJson.mockStatus;
                final String mockBody = EspressoTest.GSON.toJson(mockJson.mockBody);

                response = new Response.Builder()
                    .code(mockStatus)
                    .message(mockBody)
                    .request(chain.request())
                    .protocol(Protocol.HTTP_1_0)
                    .body(ResponseBody.create(MediaType.parse("application/json"), mockBody.getBytes()))
                    .addHeader("content-type", "application/json")
                    .build();

                mResponseIndex++;
            }
        }

        return response;
    }
}