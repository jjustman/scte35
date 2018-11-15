package com.ngbp.scte.scte35.encoder;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ngbp.scte.scte35.decoder.Scte35Decoder;
import com.ngbp.scte.scte35.decoder.exception.DecodingException;
import com.ngbp.scte.scte35.encoder.marshaller.SpliceInfoSectionMarshaller;
import com.ngbp.scte.scte35.encoder.model.SegmentationDescriptor;
import com.ngbp.scte.scte35.encoder.model.SpliceInfoSection;
import com.ngbp.scte.scte35.encoder.model.SpliceInsert;
import com.ngbp.scte.scte35.encoder.model.TimeSignal;
import com.ngbp.scte.scte35.Scte35ModuleConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Scte35ModuleConfig.class })

public class SpliceInfoSectionMarshallerTest {

    private static final Logger LOG = Logger.getLogger(SpliceInfoSectionMarshallerTest.class);

	@Test
	public void testSpliceInsert() throws Exception {
		
    	SpliceInfoSectionMarshaller spliceInfoSectionMarshaller = new SpliceInfoSectionMarshaller();
    	
    	SpliceInfoSection spliceInfoSection = new SpliceInfoSection();
    	spliceInfoSection.spliceCommandType = SpliceInfoSection.SPLICE_INSERT; 
		
    	SpliceInsert spliceInsert = new SpliceInsert();
    	spliceInsert.spliceEventID.setValue(0x1);
    	spliceInsert.outOfNetworkIndicator.set(1);
    	spliceInsert.programSpliceFlag.set(1);
    	spliceInsert.spliceImmediateFlag.set(1);
    	
    	spliceInfoSection.setSpliceInsert(spliceInsert);
    	
    	spliceInfoSectionMarshaller.setSpliceInfoSection(spliceInfoSection);
    	String mySpliceCommand = spliceInfoSectionMarshaller.marshallToBase64();
		
    	LOG.debug("::splice: "+mySpliceCommand);
	
    	testDemarshall(mySpliceCommand);
	}
	
	
	
	@Test
	public void testTimeSignal() throws Exception {
		
    	SpliceInfoSectionMarshaller spliceInfoSectionMarshaller = new SpliceInfoSectionMarshaller();
    	
    	SpliceInfoSection spliceInfoSection = new SpliceInfoSection();
    	//todo, tie these together
    	spliceInfoSection.spliceCommandType = SpliceInfoSection.TIME_SIGNAL; 
    	spliceInfoSection.setTimeSignal(new TimeSignal());
		
    	
    	SegmentationDescriptor segmentationDescriptor = new SegmentationDescriptor();
		segmentationDescriptor.segmentationEventID.setValue(0x1); //event id
		segmentationDescriptor.segmentationUPIDtype.setValue(SegmentationDescriptor.SEGMENTATION_UPID_TYPE_USER_DEFINED);
		segmentationDescriptor.segmentationUPID = ByteBuffer.allocate(128).put("jdj".getBytes());
		segmentationDescriptor.segmentationUPIDlength.setValue(segmentationDescriptor.segmentationUPID.position());
		segmentationDescriptor.segmentationTypeID.setValue(SegmentationDescriptor.SEGMENTATION_TYPE_CONTENT_IDENTIFICATION);
    	
		
		spliceInfoSection.setSpliceDescriptor(segmentationDescriptor);
		
		spliceInfoSectionMarshaller.setSpliceInfoSection(spliceInfoSection);

		
    	String mySpliceCommand = spliceInfoSectionMarshaller.marshallToBase64();
		
    	LOG.debug("::splice: "+mySpliceCommand);
	
    	testDemarshall(mySpliceCommand);
	}
	
	
	public void testDemarshall(String myBase64Payload) throws DecodingException {
		//run thru decoder to see if we are sane
    	LOG.debug("Decoding:");
    	
    	Scte35Decoder scte35Decoder = new Scte35Decoder();
    	com.ngbp.scte.scte35.decoder.model.SpliceInfoSection spliceInfoSectionDecode = scte35Decoder.base64Decode(myBase64Payload);
    	
    	LOG.debug(spliceInfoSectionDecode);
	}
}
