package com.ngbp.scte.scte35.encoder.marshaller;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.google.common.primitives.Longs;
import com.ngbp.scte.scte35.encoder.model.SegmentationDescriptor;

@Component
public class SegmentationDescriptorMarshaller {
    private static final Logger LOG = Logger.getLogger(SegmentationDescriptorMarshaller.class);

	ByteBuffer spliceDescriptorByteBuffer = ByteBuffer.allocate(1024);
	ByteBuffer segmentationDescriptorByteBuffer = ByteBuffer.allocate(1024);

	SegmentationDescriptor segmentationDescriptor;
	
	public void setSegmentationDescriptor(SegmentationDescriptor segmentationDescriptor) {
		this.segmentationDescriptor = segmentationDescriptor;
	}
	
	public ByteBuffer marshall() throws Exception {
		//start marshalling into spliceDescriptor for preamble, output this as a long value as java string.format doesn't know what an unsigned int is
		LOG.trace(String.format("segmentationEventId: %s", segmentationDescriptor.segmentationEventID.longValue()));
		
		segmentationDescriptorByteBuffer.putInt(segmentationDescriptor.identifier.intValue());

		segmentationDescriptorByteBuffer.putInt(segmentationDescriptor.segmentationEventID.intValue());
		
		byte b2 = 0;
		b2 |= segmentationDescriptor.segmentationEventCancelIndicator.getByteRawValue();
		b2 |= segmentationDescriptor.reserved1.getByteRawValue();
		segmentationDescriptorByteBuffer.put(b2);
		
		LOG.trace(String.format("segmentationEventCancelIndicator: %s", segmentationDescriptor.segmentationEventCancelIndicator.getValue()));
		
		if(segmentationDescriptor.segmentationEventCancelIndicator.getValue() == 0) {
			byte b3 = 0;
			b3 |= segmentationDescriptor.programSegmentationFlag.getByteRawValue();
			b3 |= segmentationDescriptor.segmentationDurationFlag.getByteRawValue();
			b3 |= segmentationDescriptor.deliveryNotRestricted.getByteRawValue();
			
			LOG.trace(String.format("programSegmentationFlag: %s, segmentationDurationFlag: %s, deliveryNotRestricted: %s", 
					segmentationDescriptor.programSegmentationFlag.getByteRawValue(),
					segmentationDescriptor.segmentationDurationFlag.getByteRawValue(),
					segmentationDescriptor.deliveryNotRestricted.getByteRawValue()));

			if(segmentationDescriptor.deliveryNotRestricted.getValue() == 0) {
				b3 |= segmentationDescriptor.webDeliveryAllowedFlag.getByteRawValue();
				b3 |= segmentationDescriptor.noRegionalBlackoutFlag.getByteRawValue();
				b3 |= segmentationDescriptor.archiveAllowed.getByteRawValue();
				b3 |= segmentationDescriptor.deviceRestriction.getByteRawValue();
			} else {
				b3 |= segmentationDescriptor.reserved2.getByteRawValue();
			}
			segmentationDescriptorByteBuffer.put(b3);
			
			if(segmentationDescriptor.programSegmentationFlag.getValue() == 0) {
				throw new Exception("Component Segmentation Mode not implemented");
			} 
			
			if(segmentationDescriptor.segmentationDurationFlag.getValue() == 1) {
				LOG.trace(String.format("segmentationDuration: %s", segmentationDescriptor.segmentationDuration.longValue()));

				long l2 = 0L;
				l2 |= segmentationDescriptor.segmentationDuration.longValue();
				
				//long is 64 bits, but this concat is only 40, so offset by 64-40 = 24/8 = 3
				segmentationDescriptorByteBuffer.put(Longs.toByteArray(l2), 3, 5);
			}
			
			LOG.trace(String.format("segmentationUPIDtype: %s, segmentationUPIDlength: %s", 
					segmentationDescriptor.segmentationUPIDtype.byteValue(), 
					segmentationDescriptor.segmentationUPIDlength.byteValue()));
			
			segmentationDescriptorByteBuffer.put(segmentationDescriptor.segmentationUPIDtype.byteValue());
			segmentationDescriptorByteBuffer.put(segmentationDescriptor.segmentationUPIDlength.byteValue());
			
			//close this out before concatenating
			segmentationDescriptor.segmentationUPID.flip();		
			segmentationDescriptorByteBuffer.put(segmentationDescriptor.segmentationUPID);
			
			LOG.trace(String.format("segmentationTypeID: %s, segmentNum: %s, segmentsExpected: %s", 
					segmentationDescriptor.segmentationTypeID.byteValue(),
					segmentationDescriptor.segmentNum.byteValue(),
					segmentationDescriptor.segmentsExpected.byteValue()));
			
			segmentationDescriptorByteBuffer.put(segmentationDescriptor.segmentationTypeID.byteValue());
			segmentationDescriptorByteBuffer.put(segmentationDescriptor.segmentNum.byteValue());
			segmentationDescriptorByteBuffer.put(segmentationDescriptor.segmentsExpected.byteValue());

			if(segmentationDescriptor.segmentationTypeID.intValue() == 0x34 || segmentationDescriptor.segmentationTypeID.intValue() == 0x36) {
				segmentationDescriptorByteBuffer.putInt(segmentationDescriptor.sub_segment_num.intValue());
				segmentationDescriptorByteBuffer.putInt(segmentationDescriptor.sub_segments_expected.intValue());
			}
		}
		


		//write out preamble of SpliceDescriptor/SegmentationDescriptor, set in ::cctor for segmentationDescriptor
		spliceDescriptorByteBuffer.put(segmentationDescriptor.spliceDescriptorTag.byteValue());
		
		segmentationDescriptor.descriptorLength.setValue(segmentationDescriptorByteBuffer.position());
		
		spliceDescriptorByteBuffer.put(segmentationDescriptor.descriptorLength.byteValue());

		LOG.trace(String.format("spliceDescriptorTag: %s, descriptorLength: %s", 
				segmentationDescriptor.spliceDescriptorTag.byteValue(), 
				segmentationDescriptor.descriptorLength.byteValue()));

		//put segmentationDescriptorByteBuffer 
		segmentationDescriptorByteBuffer.flip();
		spliceDescriptorByteBuffer.put(segmentationDescriptorByteBuffer);
		spliceDescriptorByteBuffer.flip();
		LOG.trace(String.format("completed SegmentationDescriptorMarshalling, size is: %s", spliceDescriptorByteBuffer.limit()));
		
		return spliceDescriptorByteBuffer;
	}

}
