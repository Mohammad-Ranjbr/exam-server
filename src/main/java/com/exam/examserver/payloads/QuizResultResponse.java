package com.exam.examserver.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Getter
@Setter
@NoArgsConstructor
public class QuizResultResponse {

    private AtomicReference<Double> marksGot;
    private AtomicInteger correctAnswers;
    private AtomicInteger attempted;

    public QuizResultResponse(AtomicReference<Double> marksGot, AtomicInteger correctAnswers, AtomicInteger attempted) {
        this.attempted = attempted;
        this.correctAnswers = correctAnswers;
        this.marksGot = marksGot;
    }
}
