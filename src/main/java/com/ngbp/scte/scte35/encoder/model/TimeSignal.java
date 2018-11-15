package com.ngbp.scte.scte35.encoder.model;

/**

 */
public class TimeSignal {

    public SpliceTime spliceTime = new SpliceTime();
    {{
    	spliceTime.timeSpecifiedFlag.set(1);
		long ptsTime = (System.currentTimeMillis() % (24*60*60*1000) / 1000) * 90000;
		spliceTime.ptsTime.set(ptsTime);
    }}
   
    public SpliceTime getSpliceTime() {
    	return spliceTime;
    }
    
}
