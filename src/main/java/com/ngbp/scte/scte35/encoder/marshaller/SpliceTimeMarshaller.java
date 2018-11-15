package com.ngbp.scte.scte35.encoder.marshaller;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

import com.google.common.primitives.Longs;
import com.ngbp.scte.scte35.encoder.model.SpliceTime;

public class SpliceTimeMarshaller {
	
    private static final Logger LOG = Logger.getLogger(SpliceTimeMarshaller.class);

    //super hack one shot marshall
    public static ByteBuffer Marshall(SpliceTime spliceTime) throws Exception {
		SpliceTimeMarshaller spliceTimeMarshaller = new SpliceTimeMarshaller();
		spliceTimeMarshaller.setSpliceTime(spliceTime);
		ByteBuffer spliceTimeByteBuffer = spliceTimeMarshaller.marshall();
		spliceTimeByteBuffer.flip();
		return spliceTimeByteBuffer;
    }
    
    ByteBuffer spliceTimeByteBuffer = ByteBuffer.allocate(128);
    
    SpliceTime spliceTime;

	public void setSpliceTime(SpliceTime spliceTime) {
		this.spliceTime = spliceTime;
	}
    
	public ByteBuffer marshall() throws Exception {

		if(spliceTime.timeSpecifiedFlag.getValue() == 1) {			
			//40 bits total
			long l5 = 0;
			
			//hack for highest bit
			l5 |= 0x8000000000L;
    		l5 |= spliceTime.reserved1.getLongRawValue();
	    	l5 |= spliceTime.ptsTime.getLongRawValue();
	    	
	    	//long is 64 bits, but this concat is only 40, so offset by 24/8 = 3
	    	spliceTimeByteBuffer.put(Longs.toByteArray(l5), 3, 5);		    
    	} else {
        	//timesignal() payload here - with 1bit of 0, 7bits of 1 for reserved

    		byte b1 = 0;
    		
    		b1 |= spliceTime.timeSpecifiedFlag.getByteRawValue();
    		b1 |= spliceTime.reserved2.getByteRawValue();
    		spliceTimeByteBuffer.put(b1);
    	}
		spliceTimeByteBuffer.flip();
		return spliceTimeByteBuffer;
	}
}
