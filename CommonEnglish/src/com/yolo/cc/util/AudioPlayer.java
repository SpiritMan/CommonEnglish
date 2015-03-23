package com.yolo.cc.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;

public class AudioPlayer {

	private MediaPlayer mediaPlayer;

	public AudioPlayer() {
		mediaPlayer = new MediaPlayer();
	};

	/**
	 * 播放听力题目
	 * 
	 * @param index
	 */
	public void playAudio(final String quizSentence, final Context context) {
		final int quizSentenceEndIndex = quizSentence.indexOf(".");
		new Thread(new Runnable() {

			@Override
			public void run() {
				mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				mediaPlayer = MediaPlayer.create(context, ResourceIdUtil
						.getAudioResourceId(quizSentence.substring(0,
								quizSentenceEndIndex)));
				mediaPlayer.start();
			}
		}).start();
	}

	/**
	 * 判断是否处于播放状态
	 * 
	 * @return
	 */
	public boolean isPlaying() {
		return mediaPlayer.isPlaying();
	}

	/**
	 * 如果处于播放状态，停止播放
	 */
	public void stop() {
		if (mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
		}
	}
}
