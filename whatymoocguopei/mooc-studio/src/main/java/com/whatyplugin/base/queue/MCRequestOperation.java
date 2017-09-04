package com.whatyplugin.base.queue;



import android.content.Context;
import android.os.Looper;

import com.whatyplugin.base.define.MCBaseDefine.MCNetworkRequestType;
import com.whatyplugin.base.network.MCBaseRequest;
import com.whatyplugin.base.network.MCNetwork;

public class MCRequestOperation extends MCOperationNode {
    private Context mContext;
    private MCBaseRequest request;

    private MCRequestOperation(MCBaseRequest request, Context context) {
        super();
        this.request = request;
        this.mContext = context;
    }

    public static MCRequestOperation operationWithData(MCBaseRequest request, Context context) {
        return new MCRequestOperation(request, context);
    }

    public void start() {
        Looper.prepare();
        if(MCNetworkRequestType.MC_NETWORK_REQUEST_GET != this.request.type) {
            if(MCNetworkRequestType.MC_NETWORK_REQUEST_POST == this.request.type) {
                MCNetwork.post(this.request, this.mContext);
            }
            else {
                MCNetwork.post(this.request, this.mContext);
            }
        }

        Looper.loop();
    }
}

