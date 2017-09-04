package com.whatyplugin.imooc.logic.model;


import java.util.Timer;
import java.util.TimerTask;

import android.widget.VideoView;

import com.whaty.mediaplayer.WhatyMediaPlayer;

public class MCTimerModel {
	private long startTime;
	private String courseId;
	private Timer timer;
	private TimerTask task;
	
	private Object player;
	private long totalTime;
	public long getStartTime() {
		return startTime;
	}
	
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	
	public long getStopTime() {
		return System.currentTimeMillis();
	}
	
	public Timer getTimer() {
		return timer;
	}
	
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	
	public TimerTask getTask() {
		return task;
	}
	
	public void setTask(TimerTask task) {
		this.task = task;
	}
	
	public long getDelay() {
		return 1000 * 60*2;
	}

	public long getTotalTime() {
		if (this.player == null) {
			return totalTime;
		} else if (player instanceof WhatyMediaPlayer) {
			return ((WhatyMediaPlayer) player).getDuration();
		} else if (player instanceof VideoView) {
			return ((VideoView) player).getDuration();
		} else {
			return 0;
		}
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public Object getPlayer() {
		return player;
	}

	public void setPlayer(Object player) {
		this.player = player;
	}

	public void setTotalTime(long totalTime) {
		this.totalTime = totalTime;
	}
	
	
}

