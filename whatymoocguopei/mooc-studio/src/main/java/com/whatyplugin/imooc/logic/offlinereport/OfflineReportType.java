package com.whatyplugin.imooc.logic.offlinereport;



public enum OfflineReportType {

	MC_OFFLINE_REPORT_LEARN("MC_OFFLINE_REPORT_LEARN", 0, 1);

	int value;

	private OfflineReportType(String arg1, int arg2, int value) {
		this.value = value;
	}

	public int value() {
		return this.value;
	}
}
