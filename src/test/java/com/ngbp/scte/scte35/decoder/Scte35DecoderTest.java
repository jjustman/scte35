package com.ngbp.scte.scte35.decoder;

import static org.junit.Assert.assertEquals;

import java.util.Base64;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ngbp.scte.scte35.decoder.exception.DecodingException;
import com.ngbp.scte.scte35.decoder.model.BreakDuration;
import com.ngbp.scte.scte35.decoder.model.SpliceInfoSection;
import com.ngbp.scte.scte35.decoder.model.SpliceInsert;
import com.ngbp.scte.scte35.Scte35ModuleConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { Scte35ModuleConfig.class })

public class Scte35DecoderTest {

    private static final Logger LOG = Logger.getLogger(Scte35DecoderTest.class);

	@Autowired
	private Scte35Decoder scte35Decoder;

	@Test
	public void testBase64ToBinary() throws Exception {
		/**
		 * #EXT-OATCLS-SCTE35:/DAvAAAAAAAAAP/wBQb/ftuzOAAZAhdDVUVJAAAABX/PAABSZcABAwEjRTIAAMn60f4=
		 * #EXT-X-CUE-OUT:60.003 #EXTINF:4.638, master_1000/00013/master_1000_00902.ts
		 * [...] #EXTINF:5.339, master_1000/00013/master_1000_00908.ts
		 * #EXT-OATCLS-SCTE35:/DAvAAAAAAAAAP/wBQb/fy4aJwAZAhdDVUVJAAAABX/PAABSZcABAwEjRTMAAHxwMag=
		 * #EXT-X-CUE-IN #EXTINF:4.671, master_1000/00013/master_1000_00909.ts [...]
		 */
		byte myBytes[] = Base64.getDecoder().decode("/DAvAAAAAAAAAP/wBQb/ftuzOAAZAhdDVUVJAAAABX/PAABSZcABAwEjRTIAAMn60f4=");

		String bitStream = "";
		for (int i = 0; i < myBytes.length; i++) {

			// System.out.print(Integer.toBinaryString(byteBuffer.get(i)).substring(24,
			// 32));
			bitStream += Integer.toBinaryString((myBytes[i] & 0xFF) + 0x100).substring(1) + " ";
		}
		LOG.debug(String.format("bitstream: %s",  bitStream));

		// 11111100 00110000 00101111 00000000 00000000 00000000 00000000 00000000
		// 00000000 00000000 11111111 11110000 00000101 00000110 11111111 01111110
		// 11011011 10110011 00111000 00000000 00011001 00000010 00010111 01000011
		// 01010101 01000101 01001001 00000000 00000000 00000000 00000101 01111111
		// 11001111 00000000 00000000 01010010 01100101 11000000 00000001 00000011
		// 00000001 00100011 01000101 00110010 00000000 00000000 11001001 11111010
		// 11010001 11111110 PASSED: testBase64ToBinary

//    	SpliceInfoSection{tableID=252, sectionSyntaxIndicator=0, privateIndicator=0, reserved1=3, sectionLength=47, protocolVersion=0, encryptedPacket=0, encryptionAlgorithm=0, ptsAdjustment=0, cwIndex=0, tier=4095, spliceCommandLength=5, spliceCommandType=6, descriptorLoopLength=25, alignmentStuffing=0, eCRC32=0, CRC32=-906309122, spliceInsert=SpliceInsert{spliceEventID=0, spliceEventCancelIndicator=0, reserved1=0, outOfNetworkIndicator=0, programSpliceFlag=0, sisp=SpliceTime{timeSpecifiedFlag=0, reserved1=0, ptsTime=0, reserved2=0}, durationFlag=0, spliceImmediateFlag=0, breakDuration=com.nfl.scte35.decoder.model.BreakDuration@1d9b7cce, reserved2=0, uniqueProgramID=0, availNum=0, availsExpected=0}}

	}

	@Test
	public void testCRC() throws DecodingException { 
		
		/**
		a6cb-4100-8330-4a2524dd0d7d] Scte35Encoder:30 - marshalling message for commandType: 6
	2018-09-29 10:11:03.232 TRACE [Scte35Encoder.txnId: f579c287-a6cb-4100-8330-4a2524dd0d7d] SpliceInfoSectionMarshaller:91 - spliceInfoSection.spliceCommandLength is: 5
	2018-09-29 10:11:03.232 TRACE [Scte35Encoder.txnId: f579c287-a6cb-4100-8330-4a2524dd0d7d] SegmentationDescriptorMarshaller:26 - segmentationEventId: 1
	2018-09-29 10:11:03.233 TRACE [Scte35Encoder.txnId: f579c287-a6cb-4100-8330-4a2524dd0d7d] SegmentationDescriptorMarshaller:35 - segmentationEventCancelIndicator: 0
	2018-09-29 10:11:03.233 TRACE [Scte35Encoder.txnId: f579c287-a6cb-4100-8330-4a2524dd0d7d] SegmentationDescriptorMarshaller:43 - programSegmentationFlag: -128, segmentationDurationFlag: 0, deliveryNotRestricted: 32
	2018-09-29 10:11:03.233 TRACE [Scte35Encoder.txnId: f579c287-a6cb-4100-8330-4a2524dd0d7d] SegmentationDescriptorMarshaller:72 - segmentationUPIDtype: 1, segmentationUPIDlength: 3
	2018-09-29 10:11:03.233 TRACE [Scte35Encoder.txnId: f579c287-a6cb-4100-8330-4a2524dd0d7d] SegmentationDescriptorMarshaller:83 - segmentationTypeID: 1, segmentNum: 0, segmentsExpected: 0
	2018-09-29 10:11:03.234 TRACE [Scte35Encoder.txnId: f579c287-a6cb-4100-8330-4a2524dd0d7d] SegmentationDescriptorMarshaller:108 - spliceDescriptorTag: 2, descriptorLength: 14
	2018-09-29 10:11:03.234 TRACE [Scte35Encoder.txnId: f579c287-a6cb-4100-8330-4a2524dd0d7d] SegmentationDescriptorMarshaller:116 - completed SegmentationDescriptorMarshalling, size is: 20
	2018-09-29 10:11:03.234 TRACE [Scte35Encoder.txnId: f579c287-a6cb-4100-8330-4a2524dd0d7d] SpliceInfoSectionMarshaller:135 - spliceInfoSection.sectionLength. is: 42
	2018-09-29 10:11:03.236 TRACE [Scte35Encoder.txnId: f579c287-a6cb-4100-8330-4a2524dd0d7d] SpliceInfoSectionMarshaller:173 - finalByteBuffer used: 45
	2018-09-29 10:11:03.236 TRACE [Scte35Encoder.txnId: f579c287-a6cb-4100-8330-4a2524dd0d7d] SpliceInfoSectionMarshaller:182 - bitStream is: 11111100 00110000 00101010 00000000 00000000 00000000 00000000 00000000 00000000 00000000 11111111 11110000 00000101 00000110 11111111 01001011 11011011 11100110 11110000 00000000 00010100 00000010 00001110 01000011 01010101 01000101 01001001 00000000 00000000 00000000 00000001 01111111 10111111 00000001 00000011 01101010 01100100 01101010 00000001 00000000 00000000 01010100 11010111 01000110 01100110 
	2018-09-29 10:11:03.236 DEBUG [Scte35Encoder.txnId: f579c287-a6cb-4100-8330-4a2524dd0d7d] SpliceInfoSectionMarshaller:41 - base64 encoded is: /DAqAAAAAAAAAP/wBQb/S9vm8AAUAg5DVUVJAAAAAX+/AQNqZGoBAABU10Zm
	2018-09-29 10:11:03.237 INFO  [] Scte35EncoderTest:53 - spliceCommand: /DAqAAAAAAAAAP/wBQb/S9vm8AAUAg5DVUVJAAAAAX+/AQNqZGoBAABU10Zm
	2018-09-29 10:11:03.241 INFO  [] GenericApplicationContext:960 - Closing org.springframework.context.support.GenericApplicationContext@64cd705f: startup date [Sat Sep 29 10:11:02 PDT 2018]; root of context hierarchy

	**/
		

		
		//new with proper crc
		SpliceInfoSection spliceInfoSection = scte35Decoder.base64Decode("/DA0AAAAAAAAAP/wBQb/smp60AAeAhxDVUVJv7neXH+/AQ1EYWlseSBNYWlsIFRWEAAAPWFiFA==");
		
	}
	
	@Test
	public void testSideBySideDfpSample() throws DecodingException {
		
		//#EXT-OATCLS-SCTE35:
		//String playoutEnginePayload = "/DBYAAAAAAAAAP/wCgUAAAABf98ABwAAAD0CN0NVRUkAAAADf/8AAZv8wAEnc2N0ZSAzNSBwbGF5b3V0IGVuZ2luZSB0ZXN0IHByb2dyYW1taW5nMgAAWHMU0g==";
		//String playoutEnginePayload = "/DBTAAAAAAAAAP/wBQb/x/AfkAA9AjdDVUVJAAAAAX//AAGb/MABJ3NjdGUgMzUgcGxheW91dCBlbmdpbmUgdGVzdCBwcm9ncmFtbWluZzIAAOa2bG0=";
		//String playoutEnginePayload = "/DAsAAAAAAAAAP/wBQb/yq8/kAAWAhBDVUVJAAAAAX//AAGb/MABADIAAC8Ih0g=";
		
		//String playoutEnginePayload = "/DAsAAAAAAAAAP/wBQb/zHBQgAAWAhBDVUVJAAAAAX//AAGb/MABAzIAAIy3/Aw=";
		//String playoutEnginePayload = "/DAvAAAAAAAAAP/wBQb+GJyowAAZAhNDVUVJAAAAAX//AAGb/MABAwEXLTIAAHh5TKA=";
		//String playoutEnginePayload = "/DAvAAAAAAAAAP/wBQb+Gf734AAZAhNDVUVJAAAAAX//AAGb/MABAwEjRTIAADATvHo=";
		/***
		 * TODO: capture under size difference between descriptor loop length adn segmentation descriptior.description length 
		 * description loop length: 25				description loop length: 25
		 * Segmentation Descriptor - Length=19		Segmentation Descriptor - Length=23
		 */
		
		//String playoutEnginePayload = "/DAvAAAAAAAAAP/wBQb+Iie+cAAZAhdDVUVJAAAAAX//AAGb/MABAwEjRTIAAM7VWPE=";
		String playoutEnginePayload = "/DAvAAAAAAAAAP/wBQb+JL8LIAAZAhdDVUVJAAAAAn//AAGb/MABAwEjRTIAAK0aIHQ=";
		
		
		SpliceInfoSection spliceInfoSectionPE = scte35Decoder.base64Decode(playoutEnginePayload);
		
		String DFPSampleTag = 		  "/DAvAAAAAAAAAP/wBQb/ftuzOAAZAhdDVUVJAAAABX/PAABSZcABAwEjRTIAAMn60f4=";
		SpliceInfoSection spliceInfoSectionDFP = scte35Decoder.base64Decode(DFPSampleTag);

	}	
	
	/**
	 * Given input:
	 * {@code "/DAlAAAAAAAAAP/wFAUAAAPof+//SVqZrP4Ae5igAAEBAQAAQcfnVA=="} Expected
	 * result: {@code
	 * <SpliceInfoSection tableID="252" sectionSyntaxIndicator="0" privateIndicator=
	"0" reserved_0="3" sectionLength="37" protocolVersion="0" ptsAdjustment=
	"0" cwIndex="0" tier="4095" spliceCommandLength="20">
	 * <SpliceInsert spliceEventId="1000" spliceEventCancelIndicator="0" reserved_0=
	"127" outOfNetworkIndicator="1" spliceImmediateFlag="0" reserved_1=
	"15" uniqueProgramId="1" availNum="1" availsExpected="1">
	 * <Program><SpliceTime ptsTime="5525641644"/></Program>
	 * <BreakDuration autoReturn="1" reserved="63" duration="8100000"/>
	 * </SpliceInsert>
	 * <descriptorLoopLength>0</descriptorLoopLength>
	 * <AlignmentStuffing>0</AlignmentStuffing>
	 * <Crc_32>1103619924</Crc_32>
	 * </SpliceInfoSection>
	 * }
	 * <p>
	 * NOTE: reserved fields indices are shifted by 1, i.e. reserved_0 => reserved1
	 *
	 * @throws Exception
	 */
	@Test
	public void testExample1() throws Exception {
		SpliceInfoSection spliceInfoSection = scte35Decoder.base64Decode("/DAlAAAAAAAAAP/wFAUAAAPof+//SVqZrP4Ae5igAAEBAQAAQcfnVA==");
		assertEquals(252, spliceInfoSection.tableID);
		assertEquals(3, spliceInfoSection.reserved1);
		assertEquals(37, spliceInfoSection.sectionLength);
		assertEquals(4095, spliceInfoSection.tier);
		assertEquals(20, spliceInfoSection.spliceCommandLength);
		SpliceInsert spliceInsert = spliceInfoSection.spliceInsert;
		assertEquals(1000, spliceInsert.spliceEventID);
		// assertEquals(127,spliceInsert.reserved1); //Not set in decoder
		assertEquals(1, spliceInsert.outOfNetworkIndicator);
		// assertEquals(15,spliceInsert.reserved2); //Not set in decoder
		assertEquals(1, spliceInsert.uniqueProgramID);
		assertEquals(1, spliceInsert.availNum);
		assertEquals(1, spliceInsert.availsExpected);
		BreakDuration breakDuration = spliceInsert.breakDuration;
		assertEquals(1, breakDuration.autoReturn);
		// assertEquals(63, breakDuration.reserved1); //Not set in decoder
		assertEquals(8100000L, breakDuration.duration);
		assertEquals(1103619924L, spliceInfoSection.CRC32);

		LOG.debug(spliceInfoSection);
	}

	/**
	 * Given input:
	 * {@code "/DAlAAAAAAAAAP/wFAUAACtnf+/+s9z9LP4Ae5igAAEBAQAAwWSPdQ=="} Expected
	 * result: {@code
	 * <SpliceInfoSection tableID="252" sectionSyntaxIndicator="0" privateIndicator="0" reserved_0="3" sectionLength="37" protocolVersion="0" ptsAdj ustment="0" cwIndex="0" tier="4095" spliceCommandLength="20">
	 * <SpliceInsert spliceEventId="11111" spliceEventCancelIndicator="0" reserved_0
	="127" outOfNetworkIndicator="1" spliceImmediateFlag="0" reserved_1=
	"15" uniqueProgramId="1" availNum="1" availsExpected="1">
	 * <Program>
	 * <SpliceTime ptsTime="3017604396"/>
	 * </Program>
	 * <BreakDuration autoReturn="1" reserved="63" duration="8100000"/>
	 * </SpliceInsert>
	 * <descriptorLoopLength>0</descriptorLoopLength>
	 * <AlignmentStuffing>0</AlignmentStuffing>
	 * <Crc_32>-1050374283</Crc_32>
	 * </SpliceInfoSection>
	 * }
	 * <p>
	 * NOTE: reserved fields indices are shifted by 1, i.e. reserved_0 => reserved1
	 *
	 * @throws Exception
	 */
	@Test
	public void testExample2() throws Exception {
		SpliceInfoSection spliceInfoSection = scte35Decoder.base64Decode("/DAlAAAAAAAAAP/wFAUAACtnf+/+s9z9LP4Ae5igAAEBAQAAwWSPdQ==");
		assertEquals(252, spliceInfoSection.tableID);
		assertEquals(3, spliceInfoSection.reserved1);
		assertEquals(37, spliceInfoSection.sectionLength);
		assertEquals(4095, spliceInfoSection.tier);
		assertEquals(20, spliceInfoSection.spliceCommandLength);
		SpliceInsert spliceInsert = spliceInfoSection.spliceInsert;
		assertEquals(11111, spliceInsert.spliceEventID);
		// assertEquals(127,spliceInsert.reserved1); //Not set in decoder
		assertEquals(1, spliceInsert.outOfNetworkIndicator);
		// assertEquals(15,spliceInsert.reserved2); //Not set in decoder
		assertEquals(1, spliceInsert.uniqueProgramID);
		assertEquals(1, spliceInsert.availNum);
		assertEquals(1, spliceInsert.availsExpected);
		BreakDuration breakDuration = spliceInsert.breakDuration;
		assertEquals(1, breakDuration.autoReturn);
		// assertEquals(63, breakDuration.reserved1); //Not set in decoder
		assertEquals(8100000L, breakDuration.duration);
		assertEquals(-1050374283L, spliceInfoSection.CRC32);

		LOG.debug(spliceInfoSection);
	}

	/**
	 * From Example on Python decoder:
	 * https://gist.github.com/use-sparingly/6517a8b94a52746af028
	 *
	 *
	 * Given input:
	 * {@code "/DAlAAAAAAAAAP/wFAUAAAABf+/+LRQrAP4BI9MIAAEBAQAAfxV6SQ=="} Expected
	 * result: {@code
	 * <SpliceInfoSection tableID="252" sectionSyntaxIndicator="0" privateIndicator=
	"0" reserved_0="3" sectionLength="37" protocolVersion="0" ptsAdjustment=
	"0" cwIndex="0" tier="4095" spliceCommandLength="20">
	 * <SpliceInsert spliceEventId="1" spliceEventCancelIndicator="0" reserved_0=
	"127" outOfNetworkIndicator="1" spliceImmediateFlag="0" reserved_1=
	"15" uniqueProgramId="1" availNum="1" availsExpected="1">
	 * <Program><SpliceTime ptsTime="756296448"/></Program>
	 * <BreakDuration autoReturn="1" reserved="63" duration="19125000"/>
	 * </SpliceInsert>
	 * <descriptorLoopLength>0</descriptorLoopLength>
	 * <AlignmentStuffing>0</AlignmentStuffing>
	 * <Crc_32>2132113993</Crc_32>
	 * </SpliceInfoSection>
	 * }
	 * <p>
	 * NOTE: reserved fields indices are shifted by 1, i.e. reserved_0 => reserved1
	 *
	 * @throws Exception
	 */
	@Test
	public void testExample3() throws Exception {
		SpliceInfoSection spliceInfoSection = scte35Decoder.base64Decode("/DAlAAAAAAAAAP/wFAUAAAABf+/+LRQrAP4BI9MIAAEBAQAAfxV6SQ==");
		assertEquals(252, spliceInfoSection.tableID);
		assertEquals(3, spliceInfoSection.reserved1);
		assertEquals(37, spliceInfoSection.sectionLength);
		assertEquals(4095, spliceInfoSection.tier);
		assertEquals(20, spliceInfoSection.spliceCommandLength);
		SpliceInsert spliceInsert = spliceInfoSection.spliceInsert;
		assertEquals(1, spliceInsert.spliceEventID);
		// assertEquals(127,spliceInsert.reserved1); //Not set in decoder
		assertEquals(1, spliceInsert.outOfNetworkIndicator);
		// assertEquals(15,spliceInsert.reserved2); //Not set in decoder
		assertEquals(1, spliceInsert.uniqueProgramID);
		assertEquals(1, spliceInsert.availNum);
		assertEquals(1, spliceInsert.availsExpected);
		BreakDuration breakDuration = spliceInsert.breakDuration;
		assertEquals(1, breakDuration.autoReturn);
		// assertEquals(63, breakDuration.reserved1); //Not set in decoder
		assertEquals(19125000L, breakDuration.duration);
		assertEquals(2132113993L, spliceInfoSection.CRC32);

		LOG.debug(spliceInfoSection);
	}

}