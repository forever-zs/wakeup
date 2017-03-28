package com.wakeup.forever.wakeup.model.bean;

/**
 * Created by forever on 2016/11/26.
 */

public class Emotion {
    private FaceRectangle faceRectangle;

    private Score scores;

    public FaceRectangle getFaceRectangle() {
        return faceRectangle;
    }

    public void setFaceRectangle(FaceRectangle faceRectangle) {
        this.faceRectangle = faceRectangle;
    }

    public Score getScores() {
        return scores;
    }

    public void setScores(Score scores) {
        this.scores = scores;
    }
}
