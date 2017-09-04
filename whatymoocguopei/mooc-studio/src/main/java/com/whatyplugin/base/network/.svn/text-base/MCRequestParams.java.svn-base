package com.whatyplugin.base.network;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class MCRequestParams implements Serializable {
    public class FileWrapper implements Serializable {
        private File file;
		private String contentType;
		private String customFileName;

		public FileWrapper(File file, String contentType, String customFileName) {
            super();
            this.file = file;
            this.contentType = contentType;
            this.customFileName = customFileName;
        }
    }

    public static class StreamWrapper {
        public final boolean autoClose;
		private InputStream inputStream;
		private String name;
		private String contentType;

        public StreamWrapper(InputStream inputStream, String name, String contentType, boolean autoClose) {
            super();
            this.inputStream = inputStream;
            this.name = name;
            this.contentType = contentType;
            this.autoClose = autoClose;
        }

        public static StreamWrapper newInstance(InputStream inputStream, String name, String contentType, boolean autoClose) {
            if(contentType == null) {
                contentType = "application/octet-stream";
            }

            return new StreamWrapper(inputStream, name, contentType, autoClose);
        }
    }

    public static final String APPLICATION_JSON = "application/json";
    public static final String APPLICATION_OCTET_STREAM = "application/octet-stream";
    protected static final String LOG_TAG = "RequestParams";
    protected boolean autoCloseInputStreams;
    protected String contentEncoding;
    protected final ConcurrentHashMap fileParams;
    protected boolean isRepeatable;
    protected final ConcurrentHashMap streamParams;
    protected final ConcurrentHashMap urlParams;
    protected final ConcurrentHashMap urlParamsWithObjects;
    protected boolean useJsonStreamer;


    public MCRequestParams(Map arg5) {
        super();
        this.urlParams = new ConcurrentHashMap();
        this.streamParams = new ConcurrentHashMap();
        this.fileParams = new ConcurrentHashMap();
        this.urlParamsWithObjects = new ConcurrentHashMap();
        this.contentEncoding = "UTF-8";
        if(arg5 != null) {
            Iterator iterator = arg5.entrySet().iterator();
            while(iterator.hasNext()) {
                Object item = iterator.next();
                this.put(((Map.Entry)item).getKey().toString(), ((Map.Entry)item).getValue());
            }
        }
    }

    public MCRequestParams(String key, String value) {
        this(new HashMap() {
        });
    }

    public MCRequestParams(Object[] keysAndValues) {
        super();
        this.urlParams = new ConcurrentHashMap();
        this.streamParams = new ConcurrentHashMap();
        this.fileParams = new ConcurrentHashMap();
        this.urlParamsWithObjects = new ConcurrentHashMap();
        this.contentEncoding = "UTF-8";
        int v2 = keysAndValues.length;
        if(v2 % 2 != 0) {
            throw new IllegalArgumentException("Supplied arguments must be even");
        }

        int i;
        for(i = 0; i < v2; i += 2) {
            this.put(String.valueOf(keysAndValues[i]), String.valueOf(keysAndValues[i + 1]));
        }
    }

    public void add(String key, String value) {
        if(key != null && value != null) {
            Object params = this.urlParamsWithObjects.get(key);
            if(params == null) {
                HashSet hashSet = new HashSet();
                this.put(key, hashSet);
            }

            if((params instanceof List)) {
                ((List)params).add(value);
                return;
            }

            if(!(params instanceof Set)) {
                return;
            }

            ((Set)params).add(value);
        }
    }

    private HttpEntity createFormEntity() {
        HttpEntity httpEntity = null;
        try {
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(this.getParamsList(), this.contentEncoding);
        }
        catch(UnsupportedEncodingException e) {
            Log.e("RequestParams", "createFormEntity failed", e);
            httpEntity = null;
        }

        return httpEntity;
    }

    private HttpEntity createMultipartEntity(MCResponseHandlerInterface progressHandler) throws IOException {
        MCSimpleMultipartEntity mcSimpleMultipartEntity = new MCSimpleMultipartEntity(progressHandler);
        mcSimpleMultipartEntity.setIsRepeatable(this.isRepeatable);
        Iterator v10 = this.urlParams.entrySet().iterator();
        while(v10.hasNext()) {
            Object item = v10.next();
            mcSimpleMultipartEntity.addPartWithCharset(((Map.Entry)item).getKey().toString(), ((Map.Entry)item).getValue().toString(), this.contentEncoding);
        }

        Iterator v8 = this.getParamsList(null, this.urlParamsWithObjects).iterator();
        while(v8.hasNext()) {
            Object item = v8.next();
            mcSimpleMultipartEntity.addPartWithCharset(((BasicNameValuePair)item).getName(), ((BasicNameValuePair)item).getValue(), this.contentEncoding);
        }

        Iterator v9 = this.streamParams.entrySet().iterator();
        while(v9.hasNext()) {
            Object item = v9.next();
            StreamWrapper streamWrapper = (StreamWrapper) ((Map.Entry)item).getValue();
            if(((StreamWrapper)streamWrapper).inputStream == null) {
                continue;
            }

            mcSimpleMultipartEntity.addPart((String) ((Map.Entry)item).getKey(), (streamWrapper).name, (streamWrapper).inputStream, (streamWrapper).contentType);
        }

        v9 = this.fileParams.entrySet().iterator();
        while(v9.hasNext()) {
            Object item = v9.next();
            FileWrapper fileWrapper = (FileWrapper) ((Map.Entry)item).getValue();
            mcSimpleMultipartEntity.addPart(((Map.Entry)item).getKey().toString(), ((FileWrapper)fileWrapper).file, ((FileWrapper)fileWrapper).contentType, ((FileWrapper)fileWrapper).customFileName);
        }

        return ((HttpEntity)mcSimpleMultipartEntity);
    }

    public HttpEntity getEntity(MCResponseHandlerInterface progressHandler) throws IOException {
        HttpEntity httpEntity = !this.streamParams.isEmpty() || !this.fileParams.isEmpty() ? this.createMultipartEntity(progressHandler) : this.createFormEntity();
        return httpEntity;
    }

    protected String getParamString() {
        return URLEncodedUtils.format(this.getParamsList(), this.contentEncoding);
    }

	protected List getParamsList() {
        LinkedList linkedList = new LinkedList();
        Iterator v4 = this.urlParams.entrySet().iterator();
        while(v4.hasNext()) {
            Object item = v4.next();
            ((List)linkedList).add(new BasicNameValuePair(((Map.Entry)item).getKey().toString(), ((Map.Entry)item).getValue().toString()));
        }

        ((List)linkedList).addAll(this.getParamsList(null, this.urlParamsWithObjects));
        return ((List)linkedList);
    }

    private List getParamsList(String key, Object value) {
        String v6_1 = null;
        Iterator iterator;
        LinkedList linkList = new LinkedList();
        if((value instanceof Map)) {
            Object v5 = value;
            ArrayList arrayList = new ArrayList(((Map)v5).keySet());
            if(((List)arrayList).size() > 0 && ((((List)arrayList).get(0) instanceof Comparable))) {
                Collections.sort(((List)arrayList));
            }

            iterator = ((List)arrayList).iterator();
            while(iterator.hasNext()) {
                Object item = iterator.next();
                if(!(item instanceof String)) {
                    continue;
                }

                Object v7 = ((Map)v5).get(item);
                if(v7 == null) {
                    continue;
                }

                if(key != null) {
                    v6_1 = String.format("%s[%s]", key, item);
                }

                ((List)linkList).addAll(this.getParamsList(v6_1, v7));
            }
        }
        else {
            if((value instanceof List)) {
                Object v3_1 = value;
                int size = ((List)v3_1).size();
                int i;
                for(i = 0; true; ++i) {
                    if(i >= size) {
                    	return ((List)linkList);
                    }

                    ((List)linkList).addAll(this.getParamsList(String.format("%s[%d]", key, Integer.valueOf(i)), ((List)v3_1).get(i)));
                }
            }

            if((value instanceof Object[])) {
                Object[] v1 = (Object[]) value;
                int v2 = v1.length;
                for(int v8 = 0; true; ++v8) {
                    if(v8 >= v2) {
                    	return ((List)linkList);
                    }

                    ((List)linkList).addAll(this.getParamsList(String.format("%s[%d]", key, Integer.valueOf(v8)), v1[v8]));
                }
            }

            if((value instanceof Set)) {
                iterator = ((Set) value).iterator();
                while(true) {
                    if(!iterator.hasNext()) {
                    	return ((List)linkList);
                    }

                    ((List)linkList).addAll(this.getParamsList(key, iterator.next()));
                }
            }

            ((List)linkList).add(new BasicNameValuePair(key, value.toString()));
        }

        return ((List)linkList);
    }

    public boolean has(String key) {
        boolean flag = this.urlParams.get(key) != null || this.streamParams.get(key) != null || this.fileParams.get(key) != null || this.urlParamsWithObjects.get(key) != null ? true : false;
        return flag;
    }

    public void put(String key, String value) {
        if(key != null && value != null) {
            this.urlParams.put(key, value);
        }
    }

    public void put(String key, Object value) {
        if(key != null && value != null) {
            this.urlParamsWithObjects.put(key, value);
        }
    }

    public void put(String key, int value) {
        if(key != null) {
            this.urlParams.put(key, String.valueOf(value));
        }
    }

    public void put(String key, long value) {
        if(key != null) {
            this.urlParams.put(key, String.valueOf(value));
        }
    }

    public void put(String key, File file) throws FileNotFoundException {
        this.put(key, file, null, null);
    }

    public void put(String key, File file, String contentType, String customFileName) throws FileNotFoundException {
        if(file != null && (file.exists())) {
            if(key != null) {
                this.fileParams.put(key, new FileWrapper(file, contentType, customFileName));
            }

            return;
        }

        throw new FileNotFoundException();
    }

    public void put(String key, File file, String contentType) throws FileNotFoundException {
        this.put(key, file, contentType, null);
    }

    public void put(String key, InputStream stream) {
        this.put(key, stream, null);
    }

    public void put(String key, InputStream stream, String name) {
        this.put(key, stream, name, null);
    }

    public void put(String key, InputStream stream, String name, String contentType) {
        this.put(key, stream, name, contentType, this.autoCloseInputStreams);
    }

    public void put(String key, InputStream stream, String name, String contentType, boolean autoClose) {
        if(key != null && stream != null) {
            this.streamParams.put(key, StreamWrapper.newInstance(stream, name, contentType, autoClose));
        }
    }

    public void put(String key, String customFileName, File file) throws FileNotFoundException {
        this.put(key, file, null, customFileName);
    }

    public void remove(String key) {
        this.urlParams.remove(key);
        this.streamParams.remove(key);
        this.fileParams.remove(key);
        this.urlParamsWithObjects.remove(key);
    }

    public void setAutoCloseInputStreams(boolean flag) {
        this.autoCloseInputStreams = flag;
    }

    public void setContentEncoding(String encoding) {
        if(encoding != null) {
            this.contentEncoding = encoding;
        }
        else {
            Log.d("RequestParams", "setContentEncoding called with null attribute");
        }
    }

    public void setHttpEntityIsRepeatable(boolean isRepeatable) {
        this.isRepeatable = isRepeatable;
    }

    public void setUseJsonStreamer(boolean useJsonStreamer) {
        this.useJsonStreamer = useJsonStreamer;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        Iterator iterator = this.urlParams.entrySet().iterator();
        while(iterator.hasNext()) {
            Object v2 = iterator.next();
            if(builder.length() > 0) {
                builder.append("&");
            }

            builder.append(((Map.Entry)v2).getKey());
            builder.append("=");
            builder.append(((Map.Entry)v2).getValue());
        }

        iterator = this.streamParams.entrySet().iterator();
        while(iterator.hasNext()) {
            Object v1 = iterator.next();
            if(builder.length() > 0) {
                builder.append("&");
            }

            builder.append(((Map.Entry)v1).getKey());
            builder.append("=");
            builder.append("STREAM");
        }

        iterator = this.fileParams.entrySet().iterator();
        while(iterator.hasNext()) {
            Object item = iterator.next();
            if(builder.length() > 0) {
                builder.append("&");
            }

            builder.append(((Map.Entry)item).getKey());
            builder.append("=");
            builder.append("FILE");
        }

        Iterator v6 = this.getParamsList(null, this.urlParamsWithObjects).iterator();
        while(v6.hasNext()) {
            Object v3 = v6.next();
            if(builder.length() > 0) {
                builder.append("&");
            }

            builder.append(((BasicNameValuePair)v3).getName());
            builder.append("=");
            builder.append(((BasicNameValuePair)v3).getValue());
        }

        return builder.toString();
    }
}

