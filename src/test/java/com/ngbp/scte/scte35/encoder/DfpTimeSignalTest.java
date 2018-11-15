package com.ngbp.scte.scte35.encoder;

import java.nio.ByteBuffer;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ngbp.scte.scte35.decoder.Scte35Decoder;
import com.ngbp.scte.scte35.encoder.model.SegmentationDescriptor;
import com.ngbp.scte.scte35.encoder.model.SpliceInfoSection;
import com.ngbp.scte.scte35.encoder.model.TimeSignal;
import com.ngbp.scte.scte35.Scte35ModuleConfig;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Scte35ModuleConfig.class })
public class DfpTimeSignalTest {

    private static final Logger LOG = Logger.getLogger(DfpTimeSignalTest.class);

    /**
     * baseline pass with dfp 
    %%TIME_SIGNAL_EVENT_ID%%
    %%TIME_SIGNAL_UPID%%
    **/
    
    @Test
	public void dfpTimeSignalPayloadWithDuration() throws Exception {
		int SEGMENTATION_TYPE = SegmentationDescriptor.SEGMENTATION_TYPE_DISTRIBUTOR_ADVERTISEMENT_START;
		String myProgramTitle = "TESTPROGRAM".trim().toLowerCase();

		int adBreakCount = 10;
		
		Scte35Encoder scte35Encoder = new Scte35Encoder();
		
		
		LOG.info(String.format("creating scte35Payload for TIME_SIGNAL program: %s, adBreak: %s, segmentationType: %s",  myProgramTitle, adBreakCount, SEGMENTATION_TYPE));
		
		SpliceInfoSection spliceInfoSection = new SpliceInfoSection();
		spliceInfoSection.spliceCommandType = SpliceInfoSection.TIME_SIGNAL;
		spliceInfoSection.setTimeSignal(new TimeSignal());

		SegmentationDescriptor segmentationDescriptor = new SegmentationDescriptor();
		segmentationDescriptor.segmentationDurationFlag.set(1);
		/**
		 *unsigned integer that specifies the duration of the segment in terms of ticks of the program’s 90 kHz clock. It may be used to give the splicer an indication of when the segment will be over and when the next segmentation message will occur. Shall be 0 for end messag
		 * assume a 300s break
		 */
		segmentationDescriptor.segmentationDuration.setValue(90000*300);
		segmentationDescriptor.segmentationEventID.setValue(adBreakCount); // event id
		segmentationDescriptor.segmentationUPIDtype.setValue(SegmentationDescriptor.SEGMENTATION_UPID_TYPE_USER_DEFINED);
		/** set to DFP sample values:
		 * 
		 * 
			2018-09-29 16:37:49.123 DEBUG [] Scte35Decoder:647 - 01. ()
			2018-09-29 16:37:49.124 DEBUG [] Scte35Decoder:647 - 23. (#)
			2018-09-29 16:37:49.124 DEBUG [] Scte35Decoder:647 - 45. (E)
		 */
	
		segmentationDescriptor.segmentationUPID  = ByteBuffer.allocate(128);
		Boolean useDFPSampleData = false;
		if(useDFPSampleData) {
			byte[] segmentationUPIDValue = { 0x01, 0x23, 0x45 };
			segmentationDescriptor.segmentationUPID.put(segmentationUPIDValue);
		} else {
			segmentationDescriptor.segmentationUPID.put(myProgramTitle.getBytes());
		}
		segmentationDescriptor.segmentationUPIDlength.setValue(segmentationDescriptor.segmentationUPID.position());

		
		//original use for content_identifiction
		//segmentationDescriptor.segmentationTypeID.setValue(SegmentationDescriptor.SEGMENTATION_TYPE_CONTENT_IDENTIFICATION);

		segmentationDescriptor.segmentationTypeID.setValue(SEGMENTATION_TYPE);
		spliceInfoSection.setSpliceDescriptor(segmentationDescriptor);

		String mySpliceCommand = scte35Encoder.encodeToBase64(spliceInfoSection);
		LOG.info(String.format("scte35 - final payload is: %s", mySpliceCommand));
	}
	
	@Test
	public void DfpTimeSignalPayloadWithWrapper() throws Exception {
		
		String linearFeedUuid = UUID.randomUUID().toString();
		//by default, coerce the first - to segmentation_event_id
		String programTitle = "TESTPROGRAM";
		int segmentNum = 4;

		
		String programStart = Scte35SegmentationTriggers.createProgramStart(linearFeedUuid, programTitle);
		String adBreakStart = Scte35SegmentationTriggers.createDistributorAdvertisementStart(linearFeedUuid, programTitle, segmentNum);
		String adBreakEnd = Scte35SegmentationTriggers.createDistributorAdvertisementEnd(linearFeedUuid, programTitle, segmentNum);
		String programEnd = Scte35SegmentationTriggers.createProgramEnd(linearFeedUuid, programTitle);
				
		Scte35Decoder decoder = new Scte35Decoder();
		LOG.debug("programStart");
		decoder.base64Decode(programStart);
		
		LOG.debug("adBreakStart");
		decoder.base64Decode(adBreakStart);
		
		LOG.debug("adBreakEnd");
		decoder.base64Decode(adBreakEnd);
		
		LOG.debug("programEnd");
		decoder.base64Decode(programEnd);
		
		
	}
	
}
