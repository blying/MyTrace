package com.bignerdranch.android.model;

import java.util.List;

/**
 * Created by bly on 2016/12/9.
 */

public class Traces {
    private List<Trace> mTraces;

    public Traces(List<Trace> Traces) {
        this.mTraces = Traces;
    }

    public List<Trace> getTraces() {
        return mTraces;
    }

    public void setTraces(List<Trace> Traces) {
        this.mTraces = Traces;
    }

}
