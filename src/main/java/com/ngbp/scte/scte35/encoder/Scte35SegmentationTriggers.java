package com.ngbp.scte.scte35.encoder;

import java.nio.ByteBuffer;
import java.util.UUID;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.NDC;
import org.springframework.util.DigestUtils;

import com.ngbp.scte.scte35.encoder.model.SegmentationDescriptor;
import com.ngbp.scte.scte35.encoder.model.SpliceInfoSection;
import com.ngbp.scte.scte35.encoder.model.TimeSignal;

public class Scte35SegmentationTriggers {

	private static Scte35Encoder scte35Encoder = new Scte35Encoder();
    private static final Logger LOG = Logger.getLogger(Scte35SegmentationTriggers.class);
	
	public static String createDistributorAdvertisementStart(String uuid, String programTitle, int segmentNum) {
		return createSegment(SegmentationDescriptor.SEGMENTATION_TYPE_DISTRIBUTOR_ADVERTISEMENT_START, uuid, programTitle, segmentNum);
	}

	public static String createDistributorAdvertisementEnd(String uuid, String programTitle, int segmentNum) {
		return createSegment(SegmentationDescriptor.SEGMENTATION_TYPE_DISTRIBUTOR_ADVERTISEMENT_END, uuid, programTitle, segmentNum);
	}
	
	public static String createProgramStart(String uuid, String programTitle) {
		return createSegment(SegmentationDescriptor.SEGMENTATION_TYPE_PROGRAM_START, uuid, programTitle, 1);
	}
	
	public static String createProgramEnd(String uuid, String programTitle) {
		return createSegment(SegmentationDescriptor.SEGMENTATION_TYPE_PROGRAM_END, uuid, programTitle, 1);
	}
	
	/** TODO: add alternative segmentationType for upid marshalling **/
	
	private static String createSegment(int segmentationTypeId, String uuid, String programTitle, int segmentNum) {
		NDC.push("createSegmentTxn: "+UUID.randomUUID().toString());
		
		LOG.info(String.format("createSegment createSegment FROM segmentationTypeId: %s, uuid: %s, programTitle: %s, segmentNum: %s",
				segmentationTypeId, uuid, programTitle, segmentNum));
		
		SpliceInfoSection spliceInfoSection = new SpliceInfoSection();
		spliceInfoSection.spliceCommandType = SpliceInfoSection.TIME_SIGNAL;
		spliceInfoSection.setTimeSignal(new TimeSignal());

		SegmentationDescriptor segmentationDescriptor = new SegmentationDescriptor();
		segmentationDescriptor.segmentationTypeID.setValue(segmentationTypeId);

		segmentationDescriptor.segmentationDurationFlag.set(0);
		
		//Try to map guid into %%TIME_SIGNAL_EVENT_ID%%, use long as we can't print out unsigned int's in logs...

		Long myEventId = 0l;
		try {
			myEventId = Long.parseLong((uuid.split("-")[0]), 16);
		} catch (Exception e1) {
			try {
				String leftDigest = DigestUtils.md5DigestAsHex(uuid.getBytes()).substring(0, 8);
				myEventId = Long.parseLong(leftDigest, 16);
				
				LOG.info(String.format("remapping %s to md5.substring(0,8) %s into %s", uuid, leftDigest, myEventId));
			} catch (Exception ex) {
				LOG.error(String.format("unable to remap %s, ex is: %s, %s", uuid, ex, ExceptionUtils.getStackTrace(ex)));
			}
			//unable to map 
		}
		segmentationDescriptor.segmentationEventID.setValue(myEventId.intValue()); // event id
		segmentationDescriptor.segmentationUPIDtype.setValue(SegmentationDescriptor.SEGMENTATION_UPID_TYPE_USER_DEFINED);

		//set to the char values for text to hex mapping into %%TIME_SIGNAL_UPID%%
		segmentationDescriptor.segmentationUPID  = ByteBuffer.allocate(128);
		String myTitle = StringUtils.substring(programTitle.trim(),0,128);
		segmentationDescriptor.segmentationUPID.put(myTitle.getBytes());
		segmentationDescriptor.segmentationUPIDlength.setValue(segmentationDescriptor.segmentationUPID.position());
		
		spliceInfoSection.setSpliceDescriptor(segmentationDescriptor);

		String mySpliceCommand = "";
		try {
			mySpliceCommand = scte35Encoder.encodeToBase64(spliceInfoSection);
			LOG.info(String.format("trigger created - eventId: %s mapped to: %s, upid: %s mapped to: %s, segmentNum: %s", uuid, myEventId, programTitle, DatatypeConverter.printHexBinary(programTitle.getBytes()), segmentNum));
		} catch (Exception ex) {
			LOG.error(String.format("unable to encode scte35 payload: ex is: %s, %s",  ex, ExceptionUtils.getStackTrace(ex)));
		}
		NDC.pop();
		
		return mySpliceCommand;

	}
}
