package com.ngbp.scte.scte35.encoder.marshaller;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.google.common.primitives.Longs;
import com.ngbp.scte.scte35.encoder.model.SpliceInsert;
import com.ngbp.scte.scte35.encoder.model.SpliceTime;
import com.ngbp.scte.scte35.encoder.model.SpliceInsert.ComponentTagSpliceTime;

@Component
public class SpliceInsertMarshaller {
    private static final Logger LOG = Logger.getLogger(SpliceInsertMarshaller.class);

	ByteBuffer spliceInsertByteBuffer = ByteBuffer.allocate(1024);

	SpliceInsert spliceInsert;
	public void setSpliceInsert(SpliceInsert spliceInsert) {
		this.spliceInsert = spliceInsert;		
	}

	public ByteBuffer marshall() throws Exception {
		//start marshalling into spliceInsertByteBuffer for preamble
		LOG.trace(String.format("spliceEventId: %s", spliceInsert.spliceEventID.intValue()));
		
		spliceInsertByteBuffer.putInt(spliceInsert.spliceEventID.intValue());
		
		byte b1 = 0;
		b1 |= spliceInsert.spliceEventCancelIndicator.getByteRawValue();
		b1 |= spliceInsert.reserved1.getByteRawValue();
		spliceInsertByteBuffer.put(b1);
		
		LOG.trace(String.format("spliceEventCancelIndicator: %s", spliceInsert.spliceEventCancelIndicator.getValue()));
		
		if(spliceInsert.spliceEventCancelIndicator.getValue() == 0) {
			byte b3 = 0;
			b3 |= spliceInsert.outOfNetworkIndicator.getByteRawValue();
			b3 |= spliceInsert.programSpliceFlag.getByteRawValue();
			b3 |= spliceInsert.durationFlag.getByteRawValue();
			b3 |= spliceInsert.spliceImmediateFlag.getByteRawValue();
			b3 |= spliceInsert.reserved2.getByteRawValue();
			spliceInsertByteBuffer.put(b3);
			
			LOG.trace(String.format("outOfNetworkIndicator: %s, programSpliceFlag: %s, durationFlag: %s, spliceImmediateFlag: %s", 
					spliceInsert.outOfNetworkIndicator.getByteRawValue(),
					spliceInsert.programSpliceFlag.getByteRawValue(),
					spliceInsert.durationFlag.getByteRawValue(),
					spliceInsert.spliceImmediateFlag.getByteRawValue()));
			
			if(spliceInsert.programSpliceFlag.getValue() == 1 && spliceInsert.spliceImmediateFlag.getValue() == 0) {
				//marshall spliceTime here
				SpliceTime spliceTime = spliceInsert.sisp;
	
				spliceInsertByteBuffer.put(SpliceTimeMarshaller.Marshall(spliceTime));
			} 
			
			if(spliceInsert.programSpliceFlag.getValue() == 0) {
				//if programSpliceFlag is 0, we must have at least one component
				if(spliceInsert.getComponentCount().byteValue() == 0) {
					throw new Exception("componentCount cannot be 0 when programSpliceFlag is false");
				}
				
				spliceInsertByteBuffer.put(spliceInsert.getComponentCount().byteValue());
				
				for(ComponentTagSpliceTime componentTagSpliceTime :	spliceInsert.getComponentTagSpliceTimeContainer()) {
					spliceInsertByteBuffer.put(componentTagSpliceTime.getComponentTag().byteValue());
					if(spliceInsert.spliceImmediateFlag.getValue() == 0) {
						spliceInsertByteBuffer.put(SpliceTimeMarshaller.Marshall(componentTagSpliceTime.getSpliceTime()));
					}
				}
			}
		
			if(spliceInsert.durationFlag.getValue() == 1) {
				spliceInsertByteBuffer.put(BreakDurationMarshaller.Marshall(spliceInsert.breakDuration));
			}
			
			spliceInsertByteBuffer.putShort(spliceInsert.uniqueProgramID.shortValue());
			spliceInsertByteBuffer.put(spliceInsert.availNum.byteValue());
			spliceInsertByteBuffer.put(spliceInsert.availsExpected.byteValue());
		}
		
		spliceInsertByteBuffer.flip();
		return spliceInsertByteBuffer;
	}
}
	
