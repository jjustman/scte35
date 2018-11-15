package com.ngbp.scte.scte35.encoder.model;

public class SegmentationUPIDType {
	/**
	 * 
	 * segmentation_upid_type - A value from the following table. There are multiple types allowed to insure
that programmers will be able to use an id that their systems support. It is expected that the consumers of
these ids will have an out-of-band method of collecting other data rel ated to these numbers and therefore
they do not need to be of identical types. These ids may be in other descriptors in the program and, where
the same identifier is used (ISAN for example), it shall match between programs.



	 * 0x00 0 Not Used The segmentation_upid is not
defined and is not present in the
descriptor.
0x01 variable User Defined Deprecated: use type 0x0C; The
segmentation_upid does not
follow a standard naming scheme.
0x02 8 ISCI Deprecated: use type 0x03 8
characters; 4 alpha characters
followed by 4 numbers.
0x03 12 Ad-ID Defined by the Advertising Digital
Identification, LLC group. 12
characters; 4 alpha characters
(company identification prefix)
followed by 8 alphanumeric
characters. (See [Ad Id])
0x04 32 UMID See [SMPTE 330M]
0x05 8 ISAN Deprecated: use type 0x06 ISO
15706 binary encoding.
0x06 12 ISAN Formerly known as V-ISAN. ISO
15706-2 binary encoding
(“versioned” ISAN). See [ISO
15706-2 ].
0x07 12 TID Tribune Media Systems Program
identifier. 12 characters; 2 alpha
characters followed by 10
numbers.
0x08 8 TI AiringID (Formerly Turner ID)
used to indicate a specific airing
of a program that is unique within
a network.
0x09 variable ADI CableLabs metadata identifier as
defined in Section 10.3.3.2.
0x0A 12 EIDR An EIDR (see [EIDRA])
represented in Compact Binary
encoding as defined in Section
2.1.1 in EIDR ID Format (see
[EIDR ID FORMAT])
0x0B variable ATSC Content
Identifier
ATSC_content_identifier()
structure as defined in [ATSC
A/57B].
0x0C variable MPU() Managed Private UPID structure
as defined in section Error!
Reference source not found..
0x0D variable MID() Multiple UPID types structure as
defined in section 10.3.3.4
0x0E variable ADS Information Advertising information. The
specific usage is out of scope of
this standard.
0x0F variable URI Universal Resource Identifier (see
[RFC 3986]).
0x10-0xFF variable Reserved Reserved for future
standardization

segmentation_upid() - Length and identification from Table 21 - segmentation_upid_type. This
structure’s contents and length are determined by the segmentation_upid_type and
segmentation_upid_length fields. An example would be a type of 0x06 for ISAN and a length of 12 bytes.
This field would then contain the ISAN identifier for the content to which this descriptor refers.
SegmentationUpid [Optional, xsd:SegmenationUpidType] Zero, one or more SegmentationUpid
Elements may be specified. If multiple SegmentationUpid Elements are present in an XML
representation, the MID() structure shall be generated per Section 10.3.3.4 See Section 11.4 for additional
details on SegmentationUpidType. 


	 */

}
