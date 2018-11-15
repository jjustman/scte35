package com.ngbp.scte.scte35.encoder;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ngbp.scte.scte35.decoder.Scte35Decoder;
import com.ngbp.scte.scte35.encoder.Scte35Encoder;
import com.ngbp.scte.scte35.encoder.model.SegmentationDescriptor;
import com.ngbp.scte.scte35.encoder.model.SpliceInfoSection;
import com.ngbp.scte.scte35.encoder.model.SpliceInsert;
import com.ngbp.scte.scte35.encoder.model.TimeSignal;
import com.ngbp.scte.scte35.Scte35ModuleConfig;

/**
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Scte35ModuleConfig.class })
public class Scte35EncoderTest {

    private static final Logger LOG = Logger.getLogger(Scte35EncoderTest.class);

	@Autowired
	private Scte35Encoder scte35Encoder;
	
	@Autowired
	private Scte35Decoder scte35Decoder;

	@Test
	public void testExampleTimeSignal() throws Exception {

		SpliceInfoSection spliceInfoSection = new SpliceInfoSection();
		spliceInfoSection.spliceCommandType = SpliceInfoSection.TIME_SIGNAL;
    	spliceInfoSection.setTimeSignal(new TimeSignal());

		SegmentationDescriptor segmentationDescriptor = new SegmentationDescriptor();
		segmentationDescriptor.segmentationEventID.setValue(0x1); // event id
		segmentationDescriptor.segmentationUPIDtype.setValue(SegmentationDescriptor.SEGMENTATION_UPID_TYPE_USER_DEFINED);
		segmentationDescriptor.segmentationUPID = ByteBuffer.allocate(128).put("jdj".getBytes());
		segmentationDescriptor.segmentationUPIDlength.setValue(segmentationDescriptor.segmentationUPID.position());
		segmentationDescriptor.segmentationTypeID.setValue(SegmentationDescriptor.SEGMENTATION_TYPE_CONTENT_IDENTIFICATION);

		spliceInfoSection.setSpliceDescriptor(segmentationDescriptor);

		String mySpliceCommand = scte35Encoder.encodeToBase64(spliceInfoSection);

		LOG.info(String.format("spliceCommand: %s",  mySpliceCommand));
	}
	
	@Test
	public void testSpliceInsertWithSegmentationDescriptor() throws Exception {
		
		//todo - refactor me out into scte
		String myProgramTitle = "Paddy's Pub: Home of the Original Kitten Mittens";
		int adBreakCount=2;
		
		
		
		LOG.info(String.format("creating scte35Payload for spliceInsert program: %s, adBreak: %s",  myProgramTitle, adBreakCount));
		
		SpliceInfoSection spliceInfoSection = new SpliceInfoSection();
		spliceInfoSection.spliceCommandType = SpliceInfoSection.SPLICE_INSERT;
		

    	SpliceInsert spliceInsert = new SpliceInsert();
    	spliceInsert.spliceEventID.setValue(0x1);
    	spliceInsert.outOfNetworkIndicator.set(1);
    	spliceInsert.programSpliceFlag.set(1);
    	spliceInsert.spliceImmediateFlag.set(1);
    	
    	spliceInfoSection.setSpliceInsert(spliceInsert);

		SegmentationDescriptor segmentationDescriptor = new SegmentationDescriptor();
		segmentationDescriptor.segmentationDurationFlag.set(1);
		/**
		 * nsigned integer that specifies the duration of the segment in terms of ticks of the program’s 90 kHz clock. It may be used to give the splicer an indication of when the segment will be over and when the next segmentation message will occur. Shall be 0 for end messag
		 */
		segmentationDescriptor.segmentationDuration.setValue(90000*300);
		segmentationDescriptor.segmentationEventID.setValue(adBreakCount); // event id
		segmentationDescriptor.segmentationUPIDtype.setValue(SegmentationDescriptor.SEGMENTATION_UPID_TYPE_USER_DEFINED);
		segmentationDescriptor.segmentationUPID = ByteBuffer.allocate(128).put(myProgramTitle.getBytes());
		segmentationDescriptor.segmentationUPIDlength.setValue(segmentationDescriptor.segmentationUPID.position());
		//original use for content_identifiction
		//segmentationDescriptor.segmentationTypeID.setValue(SegmentationDescriptor.SEGMENTATION_TYPE_CONTENT_IDENTIFICATION);

		segmentationDescriptor.segmentationTypeID.setValue(SegmentationDescriptor.SEGMENTATION_TYPE_CONTENT_IDENTIFICATION);
		spliceInfoSection.setSpliceDescriptor(segmentationDescriptor);

		String mySpliceCommand = scte35Encoder.encodeToBase64(spliceInfoSection);
		
		LOG.info(String.format("spliceCommand is: %s",  mySpliceCommand));
		scte35Decoder.base64Decode(mySpliceCommand);
		
	}

}