package com.ngbp.scte.scte35.encoder.marshaller;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

import com.google.common.primitives.Longs;
import com.ngbp.scte.scte35.encoder.model.BreakDuration;

public class BreakDurationMarshaller {
	
    private static final Logger LOG = Logger.getLogger(SpliceTimeMarshaller.class);
    

    //super hack one shot marshall
    public static ByteBuffer Marshall(BreakDuration breakDuration) throws Exception {
    	BreakDurationMarshaller breakDurationMarshaller = new BreakDurationMarshaller();
    	breakDurationMarshaller.setBreakDuration(breakDuration);
    	ByteBuffer breakDurationByteBuffer = breakDurationMarshaller.marshall();
    	breakDurationByteBuffer.flip();
		return breakDurationByteBuffer;
    }
   
	ByteBuffer breakDurationByteBuffer = ByteBuffer.allocate(128); 
	BreakDuration breakDuration;
    
    private void setBreakDuration(BreakDuration breakDuration) {
    	this.breakDuration = breakDuration;
    }

	public ByteBuffer marshall() throws Exception {
		//40 bits total
		long l5 = 0;
		
		//hack for highest bit
		l5 |= breakDuration.autoReturn.getLongRawValue();
		l5 |= breakDuration.reserved1.getLongRawValue();
    	l5 |= breakDuration.duration.getLongRawValue();
    	
    	//long is 64 bits, but this concat is only 40, so offset by 24/8 = 3
    	breakDurationByteBuffer.put(Longs.toByteArray(l5), 3, 5);		
    	breakDurationByteBuffer.flip();
    	
		return breakDurationByteBuffer;
	}

}
