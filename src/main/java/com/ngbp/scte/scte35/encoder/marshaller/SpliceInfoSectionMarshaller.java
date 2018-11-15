package com.ngbp.scte.scte35.encoder.marshaller;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.zip.CRC32;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.google.common.primitives.Longs;
import com.ngbp.scte.scte35.encoder.model.SegmentationDescriptor;
import com.ngbp.scte.scte35.encoder.model.SpliceInfoSection;
import com.ngbp.scte.scte35.encoder.model.SpliceInsert;
import com.ngbp.scte.scte35.encoder.model.TimeSignal;
import com.ngbp.scte.scte35.utils.Crc32MPEG;

@Component
public class SpliceInfoSectionMarshaller {
	
    private static final Logger LOG = Logger.getLogger(SpliceInfoSectionMarshaller.class);

	ByteBuffer spliceInfoHeader = ByteBuffer.allocate(4096);
	ByteBuffer spliceInfoBody = ByteBuffer.allocate(4096);

	//Aggregation of models
	
	//TODO, this model may be more than just 1 descriptor
	
	SpliceInfoSection spliceInfoSection;
	public void setSpliceInfoSection(SpliceInfoSection spliceInfoSection) {
		this.spliceInfoSection = spliceInfoSection;
	}
	

	public String marshallToBase64() throws Exception {
		
		ByteBuffer finalByteBuffer = marshall();
		ByteBuffer base64Buffer = Base64.getEncoder().encode(finalByteBuffer);
    	
    	String myBase64Payload = new String(base64Buffer.array(), "UTF-8");
    	
    	LOG.debug(String.format("scte35 - base64 encoded is: %s",  myBase64Payload));
    	
		return myBase64Payload;
	}
	
	public ByteBuffer marshall() throws Exception {
    	spliceInfoHeader.put(spliceInfoSection.tableID.byteValue());
    	spliceInfoBody.put(spliceInfoSection.protocolVersion.byteValue());

    	long l1 = 0L;
    	
    	l1 |= spliceInfoSection.encryptedPacket.getLongRawValue();
    	l1 |= spliceInfoSection.encryptionAlgorithm.getLongRawValue();
    	l1 |= spliceInfoSection.ptsAdjustment.getLongRawValue();
    	
    	//long is 64 bits, but this concat is only 40, so offset by 24/8 = 3
    	spliceInfoBody.put(Longs.toByteArray(l1), 3, 5);
    	spliceInfoBody.put(spliceInfoSection.cwIndex.byteValue());
    
       	//to compute spliceCommandLength, push everything to an inner buffer
    	ByteBuffer spliceCommandBuffer = ByteBuffer.allocate(4096);
    	
    	//TODO - refactor this into spliceInfoSection for spliceCommand

    	
    	//Table 5 - Splice Info Section
    	
    	
    	if(spliceInfoSection.spliceCommandType == SpliceInfoSection.SPLICE_INSERT) {
    		SpliceInsertMarshaller spliceInsertMarshaller = new SpliceInsertMarshaller();
    		spliceInsertMarshaller.setSpliceInsert(spliceInfoSection.getSpliceInsert());
    		ByteBuffer spliceInsertByteBuffer = spliceInsertMarshaller.marshall();
    		
    		spliceCommandBuffer.put(spliceInsertByteBuffer);
    		
    	} else if(spliceInfoSection.spliceCommandType == SpliceInfoSection.TIME_SIGNAL) {
    		if(spliceInfoSection.getTimeSignal() == null || spliceInfoSection.getTimeSignal().getSpliceTime() == null) {
    			throw new Exception("SpliceInfoSection.TIME_SIGNAL - getTimeSignal is null");
    		}
    		SpliceTimeMarshaller spliceTimeMarshaller = new SpliceTimeMarshaller();
    		spliceTimeMarshaller.setSpliceTime(spliceInfoSection.getTimeSignal().getSpliceTime());
    		ByteBuffer spliceTimeByteBuffer = spliceTimeMarshaller.marshall();
    		
    		spliceCommandBuffer.put(spliceTimeByteBuffer);
    	} else {
    		throw new Exception(String.format("spliceCommandType: %s, not yet supported", spliceInfoSection.spliceCommandType.getByteRawValue()));
    	}
    	
    	
    	spliceInfoSection.spliceCommandLength.set(spliceCommandBuffer.position());
    	LOG.trace(String.format("spliceInfoSection.spliceCommandLength is: %s", spliceInfoSection.spliceCommandLength.getValue()));
	    	
		int i1 = 0;
    	
    	i1 |= spliceInfoSection.tier.getRawValue();
    	i1 |= spliceInfoSection.spliceCommandLength.getRawValue();
    	i1 |= spliceInfoSection.spliceCommandType.getRawValue();
    	
    	spliceInfoBody.putInt(i1);

    	//close out spliceCommandLength payload
    	spliceCommandBuffer.flip();
    	spliceInfoBody.put(spliceCommandBuffer);
    	
    	//Build out the splice descriptor payload, if applicable
    	
    	ByteBuffer spliceDescriptorByteBuffer = ByteBuffer.allocate(1024);
    	
    	if(spliceInfoSection.getSpliceDescriptor() != null && (spliceInfoSection.spliceCommandType == SpliceInfoSection.SPLICE_INSERT || spliceInfoSection.spliceCommandType == SpliceInfoSection.TIME_SIGNAL)) {
	    	//TODO -- segmentationDescriptorMarshaller for N descriptors
	    	SegmentationDescriptorMarshaller segmentationDescriptorMarshaller = new SegmentationDescriptorMarshaller();
			segmentationDescriptorMarshaller.setSegmentationDescriptor((SegmentationDescriptor)spliceInfoSection.getSpliceDescriptor());
			spliceDescriptorByteBuffer.put(segmentationDescriptorMarshaller.marshall());
    		
    	}
    	
    	//add segmentationDescriptorByteBuffer into byteBuffer
    	spliceInfoSection.descriptorLoopLength.setValue(spliceDescriptorByteBuffer.position());
    	//descriptorLoopLength is 16bits -> short
    	
    	spliceInfoBody.putShort(spliceInfoSection.descriptorLoopLength.shortValue());

    	spliceDescriptorByteBuffer.flip();
    	spliceInfoBody.put(spliceDescriptorByteBuffer);
	
    	if(spliceInfoSection.encryptedPacket.getValue() == 1) {
    		throw new Exception("encrypted packet not yet supported");
    	} 
    	
    	//update the sectionLength size + 32bits for CRC payload
    	//FIXME
    	spliceInfoSection.sectionLength.set(spliceInfoBody.position() + 4);
    	
    	LOG.trace(String.format("spliceInfoSection.sectionLength. is: %s", spliceInfoSection.sectionLength.getValue()));

    	short s1 = 0;
    	
    	s1 |= spliceInfoSection.sectionSyntaxIndicator.getShortRawValue();
    	s1 |= spliceInfoSection.privateIndicator.getShortRawValue();
    	s1 |= spliceInfoSection.reserved1.getShortRawValue();
    	s1 |= spliceInfoSection.sectionLength.getShortRawValue();
    	
    	spliceInfoHeader.putShort(s1);
    	
    	//combine spliceInfoHeader and byteBuffer for CRC32 computation
    	
    	spliceInfoHeader.flip();
    	spliceInfoBody.flip();

    	ByteBuffer finalByteBuffer = ByteBuffer.allocate(4096);
    	finalByteBuffer.put(spliceInfoHeader);
    	finalByteBuffer.put(spliceInfoBody);
    	
    	//java zip crc32 doesn't match mpeg2 crc, use a proper crc32 lookup
   
    	spliceInfoSection.CRC32 = (int) Crc32MPEG.Crc32(finalByteBuffer.array(), 0, finalByteBuffer.position());
    	
    	finalByteBuffer.putInt(spliceInfoSection.CRC32);
    	finalByteBuffer.flip();

    	//do one last check to validate 0 CRC32 result, make sure to flip the mask
    	
    	int validateCRC32 = (int)Crc32MPEG.Crc32(finalByteBuffer.array(), 0, finalByteBuffer.position()) ^ 0xFFFFFFFF;
    	
    	if(validateCRC32 != 0) {
    		throw new Exception(String.format("CRC32 paddindg failed, got:  0x%08x, expectected 0x00000000", validateCRC32));
    	}
    	
    	LOG.trace(String.format("finalByteBuffer used: %s", finalByteBuffer.limit()));
    
    	if(LOG.isTraceEnabled()) {
    		String bitStream = "";
	    	for(int i=0; i < finalByteBuffer.limit(); i++) {
	    		bitStream += Integer.toBinaryString((finalByteBuffer.get(i) & 0xFF) + 0x100).substring(1) + " ";
	    	}
	    	LOG.trace(String.format("bitStream is: %s", bitStream));
    	}
    	
    	finalByteBuffer.rewind();
    	
    	return finalByteBuffer;
	}
}
