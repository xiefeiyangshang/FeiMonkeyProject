package com.whatyplugin.base.network;


public class MCNetworkDefine {
    public enum MCMimeType {

            MC_MIME_TYPE_UNKNOWN("MC_MIME_TYPE_UNKNOWN", 0),
            MC_MIME_TYPE_JPEG("MC_MIME_TYPE_JPEG", 1),
            MC_MIME_TYPE_GIF("MC_MIME_TYPE_GIF", 2);

        private MCMimeType(String arg1, int arg2) {
        }

    }

    public enum MCNetworkStatus {
        MC_NETWORK_STATUS_NONE("MC_NETWORK_STATUS_NONE", 0, 0),
        MC_NETWORK_STATUS_WIFI("MC_NETWORK_STATUS_WIFI", 1, 1),
        MC_NETWORK_STATUS_WWAN("MC_NETWORK_STATUS_WWAN", 2, 2);
        int _value;
        private MCNetworkStatus(String arg1, int arg2, int value) {
            this._value = value;
        }

        public int value() {
            return this._value;
        }
    }

    public MCNetworkDefine() {
        super();
    }
}

