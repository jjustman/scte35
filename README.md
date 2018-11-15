2018-11-14 - jason@jasonjustman.com

next generation broadcast platform (ngbp) - sct35 binary injector/encoder and decoder for java

overview:

	built for extending SCTE-35 payload support into HLS Live Linear and VOD streaming manifest decoration

use cases:

DFP DAI programmatic targeting:
	- validated against DFP DAI with #EXT-OATCLS-SCTE35:{cue} for time_signal inserts via custom macros %%TIME_SIGNAL_UPID%% and %%TIME_SIGNAL_EVENT_ID%%
	- for more information, see: https://support.google.com/admanager/answer/7506166?hl=en

Downstream SSAI signaling of HLS:
	- tested with baseline SCTE-35 payload injection via 2017 spec recommendation of #EXT-X-SCTE35:CUE={cue} for distributor advertisement start and end cadences
	
HLS mezzaine in the cloud with in-band metadata and ad avails, break, and progamming cues.
	- ...write your own	
	- ...integrate with scte-224
	
want to see some payloads?  see com.ngbp.scte.scte35.encoder.Scte35SegmentationTriggersTest.createCoreTriggersWithWrapper()
	

test run output for creating a cadence of time signal commands with:
	
	Program Start
	Base64=/DBXAAAAAAAAAP/wBQb+FeXGIABBAj9DVUVJZGGHl3+/ATBQYWRkeSdzIFB1YjogSG9tZSBvZiB0aGUgT3JpZ2luYWwgS2l0dGVuIE1pdHRlbnMQAADeCx2n
	
	Distributor Advertisement Start
	Base64=/DBXAAAAAAAAAP/wBQb+FeXGIABBAj9DVUVJZGGHl3+/ATBQYWRkeSdzIFB1YjogSG9tZSBvZiB0aGUgT3JpZ2luYWwgS2l0dGVuIE1pdHRlbnMzAADnd3jO
	
	Distributor Advertisement End:
	Base64=/DBXAAAAAAAAAP/wBQb+FeXGIABBAj9DVUVJZGGHl3+/ATBQYWRkeSdzIFB1YjogSG9tZSBvZiB0aGUgT3JpZ2luYWwgS2l0dGVuIE1pdHRlbnMRAADf07Eg
	
	Program End:
	Base64=/DBXAAAAAAAAAP/wBQb+FeXGIABBAj9DVUVJZGGHl3+/ATBQYWRkeSdzIFB1YjogSG9tZSBvZiB0aGUgT3JpZ2luYWwgS2l0dGVuIE1pdHRlbnMRAADf07Eg
	


2018-11-14 17:08:02.385 DEBUG [] Scte35SegmentationTriggersTest:39 - programStart
2018-11-14 17:08:02.397 DEBUG [] Scte35Decoder:641 - Hex=0x
2018-11-14 17:08:02.399 DEBUG [] Scte35Decoder:641 - FC305700000000000000FFF00506FE15E5C6200041023F43554549646187977FBF013050616464792773205075623A20486F6D65206F6620746865204F726967696E616C204B697474656E204D697474656E73100000DE0B1DA7
Base64=/DBXAAAAAAAAAP/wBQb+FeXGIABBAj9DVUVJZGGHl3+/ATBQYWRkeSdzIFB1YjogSG9tZSBvZiB0aGUgT3JpZ2luYWwgS2l0dGVuIE1pdHRlbnMQAADeCx2n
2018-11-14 17:08:02.399 DEBUG [] Scte35Decoder:641 - Decoded length = 90
2018-11-14 17:08:02.399 DEBUG [] Scte35Decoder:641 - Table ID = 0xFC
2018-11-14 17:08:02.400 DEBUG [] Scte35Decoder:641 - MPEG Short Section
2018-11-14 17:08:02.400 DEBUG [] Scte35Decoder:641 - Not Private
2018-11-14 17:08:02.400 DEBUG [] Scte35Decoder:641 - Reserved = 0x3
2018-11-14 17:08:02.400 DEBUG [] Scte35Decoder:641 - Section Length = 87
2018-11-14 17:08:02.400 DEBUG [] Scte35Decoder:641 - Protocol Version = 0
2018-11-14 17:08:02.401 DEBUG [] Scte35Decoder:641 - unencrypted Packet
2018-11-14 17:08:02.401 DEBUG [] Scte35Decoder:641 - PTS Adjustment = 0x000000000
2018-11-14 17:08:02.401 DEBUG [] Scte35Decoder:641 - Tier = 0xfff
2018-11-14 17:08:02.401 DEBUG [] Scte35Decoder:641 - Splice Command Length = 0x5
2018-11-14 17:08:02.403 DEBUG [] Scte35Decoder:641 - Time Signal
2018-11-14 17:08:02.404 DEBUG [] Scte35Decoder:641 - Time = 0x015e5c620
2018-11-14 17:08:02.404 DEBUG [] Scte35Decoder:641 - Descriptor Loop Length = 65
2018-11-14 17:08:02.405 DEBUG [] Scte35Decoder:641 - Segmentation Descriptor - Length=63
2018-11-14 17:08:02.405 DEBUG [] Scte35Decoder:641 - Segmentation Event ID = 0x64618797
2018-11-14 17:08:02.405 DEBUG [] Scte35Decoder:641 - Segmentation Event Cancel Indicator NOT set
2018-11-14 17:08:02.405 DEBUG [] Scte35Decoder:641 - Delivery Not Restricted flag = 1
2018-11-14 17:08:02.406 DEBUG [] Scte35Decoder:641 - Program Segmentation flag SET
2018-11-14 17:08:02.406 DEBUG [] Scte35Decoder:641 - UPID Type = User Defined (Deprecated) length =48
Hex=0x
2018-11-14 17:08:02.407 DEBUG [] Scte35Decoder:641 - 50. (P)
2018-11-14 17:08:02.407 DEBUG [] Scte35Decoder:641 - 61. (a)
2018-11-14 17:08:02.407 DEBUG [] Scte35Decoder:641 - 64. (d)
2018-11-14 17:08:02.408 DEBUG [] Scte35Decoder:641 - 64. (d)
2018-11-14 17:08:02.408 DEBUG [] Scte35Decoder:641 - 79. (y)
2018-11-14 17:08:02.408 DEBUG [] Scte35Decoder:641 - 27. (')
2018-11-14 17:08:02.409 DEBUG [] Scte35Decoder:641 - 73. (s)
2018-11-14 17:08:02.409 DEBUG [] Scte35Decoder:641 - 20. ( )
2018-11-14 17:08:02.409 DEBUG [] Scte35Decoder:641 - 50. (P)
2018-11-14 17:08:02.409 DEBUG [] Scte35Decoder:641 - 75. (u)
2018-11-14 17:08:02.410 DEBUG [] Scte35Decoder:641 - 62. (b)
2018-11-14 17:08:02.410 DEBUG [] Scte35Decoder:641 - 3A. (:)
2018-11-14 17:08:02.410 DEBUG [] Scte35Decoder:641 - 20. ( )
2018-11-14 17:08:02.410 DEBUG [] Scte35Decoder:641 - 48. (H)
2018-11-14 17:08:02.412 DEBUG [] Scte35Decoder:641 - 6F. (o)
2018-11-14 17:08:02.412 DEBUG [] Scte35Decoder:641 - 6D. (m)
2018-11-14 17:08:02.412 DEBUG [] Scte35Decoder:641 - 65. (e)
2018-11-14 17:08:02.412 DEBUG [] Scte35Decoder:641 - 20. ( )
2018-11-14 17:08:02.413 DEBUG [] Scte35Decoder:641 - 6F. (o)
2018-11-14 17:08:02.413 DEBUG [] Scte35Decoder:641 - 66. (f)
2018-11-14 17:08:02.413 DEBUG [] Scte35Decoder:641 - 20. ( )
2018-11-14 17:08:02.413 DEBUG [] Scte35Decoder:641 - 74. (t)
2018-11-14 17:08:02.414 DEBUG [] Scte35Decoder:641 - 68. (h)
2018-11-14 17:08:02.414 DEBUG [] Scte35Decoder:641 - 65. (e)
2018-11-14 17:08:02.414 DEBUG [] Scte35Decoder:641 - 20. ( )
2018-11-14 17:08:02.415 DEBUG [] Scte35Decoder:641 - 4F. (O)
2018-11-14 17:08:02.415 DEBUG [] Scte35Decoder:641 - 72. (r)
2018-11-14 17:08:02.415 DEBUG [] Scte35Decoder:641 - 69. (i)
2018-11-14 17:08:02.415 DEBUG [] Scte35Decoder:641 - 67. (g)
2018-11-14 17:08:02.415 DEBUG [] Scte35Decoder:641 - 69. (i)
2018-11-14 17:08:02.416 DEBUG [] Scte35Decoder:641 - 6E. (n)
2018-11-14 17:08:02.416 DEBUG [] Scte35Decoder:641 - 61. (a)
2018-11-14 17:08:02.416 DEBUG [] Scte35Decoder:641 - 6C. (l)
2018-11-14 17:08:02.416 DEBUG [] Scte35Decoder:641 - 20. ( )
2018-11-14 17:08:02.416 DEBUG [] Scte35Decoder:641 - 4B. (K)
2018-11-14 17:08:02.417 DEBUG [] Scte35Decoder:641 - 69. (i)
2018-11-14 17:08:02.417 DEBUG [] Scte35Decoder:641 - 74. (t)
2018-11-14 17:08:02.417 DEBUG [] Scte35Decoder:641 - 74. (t)
2018-11-14 17:08:02.417 DEBUG [] Scte35Decoder:641 - 65. (e)
2018-11-14 17:08:02.418 DEBUG [] Scte35Decoder:641 - 6E. (n)
2018-11-14 17:08:02.418 DEBUG [] Scte35Decoder:641 - 20. ( )
2018-11-14 17:08:02.418 DEBUG [] Scte35Decoder:641 - 4D. (M)
2018-11-14 17:08:02.418 DEBUG [] Scte35Decoder:641 - 69. (i)
2018-11-14 17:08:02.418 DEBUG [] Scte35Decoder:641 - 74. (t)
2018-11-14 17:08:02.419 DEBUG [] Scte35Decoder:641 - 74. (t)
2018-11-14 17:08:02.419 DEBUG [] Scte35Decoder:641 - 65. (e)
2018-11-14 17:08:02.419 DEBUG [] Scte35Decoder:641 - 6E. (n)
2018-11-14 17:08:02.419 DEBUG [] Scte35Decoder:641 - 73. (s)
2018-11-14 17:08:02.419 DEBUG [] Scte35Decoder:641 - 
2018-11-14 17:08:02.420 DEBUG [] Scte35Decoder:641 - Type = Program Start
2018-11-14 17:08:02.420 DEBUG [] Scte35Decoder:641 - Segment num = 0 Segments Expected = 0
2018-11-14 17:08:02.420 DEBUG [] Scte35Decoder:641 - CRC32 = 0xde0b1da7
2018-11-14 17:08:02.420 DEBUG [] Scte35Decoder:641 - calc CRC32 = 0x00000000 --- Should = 0x00000000
2018-11-14 17:08:02.421 DEBUG [] Scte35SegmentationTriggersTest:42 - adBreakStart
2018-11-14 17:08:02.422 DEBUG [] Scte35Decoder:641 - Hex=0x
2018-11-14 17:08:02.423 DEBUG [] Scte35Decoder:641 - FC305700000000000000FFF00506FE15E5C6200041023F43554549646187977FBF013050616464792773205075623A20486F6D65206F6620746865204F726967696E616C204B697474656E204D697474656E73320000E6AFD449
Base64=/DBXAAAAAAAAAP/wBQb+FeXGIABBAj9DVUVJZGGHl3+/ATBQYWRkeSdzIFB1YjogSG9tZSBvZiB0aGUgT3JpZ2luYWwgS2l0dGVuIE1pdHRlbnMyAADmr9RJ
2018-11-14 17:08:02.424 DEBUG [] Scte35Decoder:641 - Decoded length = 90
2018-11-14 17:08:02.424 DEBUG [] Scte35Decoder:641 - Table ID = 0xFC
2018-11-14 17:08:02.424 DEBUG [] Scte35Decoder:641 - MPEG Short Section
2018-11-14 17:08:02.424 DEBUG [] Scte35Decoder:641 - Not Private
2018-11-14 17:08:02.424 DEBUG [] Scte35Decoder:641 - Reserved = 0x3
2018-11-14 17:08:02.425 DEBUG [] Scte35Decoder:641 - Section Length = 87
2018-11-14 17:08:02.425 DEBUG [] Scte35Decoder:641 - Protocol Version = 0
2018-11-14 17:08:02.425 DEBUG [] Scte35Decoder:641 - unencrypted Packet
2018-11-14 17:08:02.425 DEBUG [] Scte35Decoder:641 - PTS Adjustment = 0x000000000
2018-11-14 17:08:02.425 DEBUG [] Scte35Decoder:641 - Tier = 0xfff
2018-11-14 17:08:02.426 DEBUG [] Scte35Decoder:641 - Splice Command Length = 0x5
2018-11-14 17:08:02.426 DEBUG [] Scte35Decoder:641 - Time Signal
2018-11-14 17:08:02.426 DEBUG [] Scte35Decoder:641 - Time = 0x015e5c620
2018-11-14 17:08:02.426 DEBUG [] Scte35Decoder:641 - Descriptor Loop Length = 65
2018-11-14 17:08:02.426 DEBUG [] Scte35Decoder:641 - Segmentation Descriptor - Length=63
2018-11-14 17:08:02.427 DEBUG [] Scte35Decoder:641 - Segmentation Event ID = 0x64618797
2018-11-14 17:08:02.427 DEBUG [] Scte35Decoder:641 - Segmentation Event Cancel Indicator NOT set
2018-11-14 17:08:02.427 DEBUG [] Scte35Decoder:641 - Delivery Not Restricted flag = 1
2018-11-14 17:08:02.427 DEBUG [] Scte35Decoder:641 - Program Segmentation flag SET
2018-11-14 17:08:02.427 DEBUG [] Scte35Decoder:641 - UPID Type = User Defined (Deprecated) length =48
Hex=0x
2018-11-14 17:08:02.427 DEBUG [] Scte35Decoder:641 - 50. (P)
2018-11-14 17:08:02.428 DEBUG [] Scte35Decoder:641 - 61. (a)
2018-11-14 17:08:02.428 DEBUG [] Scte35Decoder:641 - 64. (d)
2018-11-14 17:08:02.428 DEBUG [] Scte35Decoder:641 - 64. (d)
2018-11-14 17:08:02.428 DEBUG [] Scte35Decoder:641 - 79. (y)
2018-11-14 17:08:02.428 DEBUG [] Scte35Decoder:641 - 27. (')
2018-11-14 17:08:02.428 DEBUG [] Scte35Decoder:641 - 73. (s)
2018-11-14 17:08:02.429 DEBUG [] Scte35Decoder:641 - 20. ( )
2018-11-14 17:08:02.429 DEBUG [] Scte35Decoder:641 - 50. (P)
2018-11-14 17:08:02.429 DEBUG [] Scte35Decoder:641 - 75. (u)
2018-11-14 17:08:02.429 DEBUG [] Scte35Decoder:641 - 62. (b)
2018-11-14 17:08:02.430 DEBUG [] Scte35Decoder:641 - 3A. (:)
2018-11-14 17:08:02.430 DEBUG [] Scte35Decoder:641 - 20. ( )
2018-11-14 17:08:02.430 DEBUG [] Scte35Decoder:641 - 48. (H)
2018-11-14 17:08:02.430 DEBUG [] Scte35Decoder:641 - 6F. (o)
2018-11-14 17:08:02.430 DEBUG [] Scte35Decoder:641 - 6D. (m)
2018-11-14 17:08:02.431 DEBUG [] Scte35Decoder:641 - 65. (e)
2018-11-14 17:08:02.431 DEBUG [] Scte35Decoder:641 - 20. ( )
2018-11-14 17:08:02.431 DEBUG [] Scte35Decoder:641 - 6F. (o)
2018-11-14 17:08:02.431 DEBUG [] Scte35Decoder:641 - 66. (f)
2018-11-14 17:08:02.432 DEBUG [] Scte35Decoder:641 - 20. ( )
2018-11-14 17:08:02.432 DEBUG [] Scte35Decoder:641 - 74. (t)
2018-11-14 17:08:02.432 DEBUG [] Scte35Decoder:641 - 68. (h)
2018-11-14 17:08:02.432 DEBUG [] Scte35Decoder:641 - 65. (e)
2018-11-14 17:08:02.432 DEBUG [] Scte35Decoder:641 - 20. ( )
2018-11-14 17:08:02.433 DEBUG [] Scte35Decoder:641 - 4F. (O)
2018-11-14 17:08:02.433 DEBUG [] Scte35Decoder:641 - 72. (r)
2018-11-14 17:08:02.433 DEBUG [] Scte35Decoder:641 - 69. (i)
2018-11-14 17:08:02.433 DEBUG [] Scte35Decoder:641 - 67. (g)
2018-11-14 17:08:02.434 DEBUG [] Scte35Decoder:641 - 69. (i)
2018-11-14 17:08:02.434 DEBUG [] Scte35Decoder:641 - 6E. (n)
2018-11-14 17:08:02.434 DEBUG [] Scte35Decoder:641 - 61. (a)
2018-11-14 17:08:02.434 DEBUG [] Scte35Decoder:641 - 6C. (l)
2018-11-14 17:08:02.434 DEBUG [] Scte35Decoder:641 - 20. ( )
2018-11-14 17:08:02.435 DEBUG [] Scte35Decoder:641 - 4B. (K)
2018-11-14 17:08:02.435 DEBUG [] Scte35Decoder:641 - 69. (i)
2018-11-14 17:08:02.435 DEBUG [] Scte35Decoder:641 - 74. (t)
2018-11-14 17:08:02.435 DEBUG [] Scte35Decoder:641 - 74. (t)
2018-11-14 17:08:02.435 DEBUG [] Scte35Decoder:641 - 65. (e)
2018-11-14 17:08:02.436 DEBUG [] Scte35Decoder:641 - 6E. (n)
2018-11-14 17:08:02.436 DEBUG [] Scte35Decoder:641 - 20. ( )
2018-11-14 17:08:02.436 DEBUG [] Scte35Decoder:641 - 4D. (M)
2018-11-14 17:08:02.436 DEBUG [] Scte35Decoder:641 - 69. (i)
2018-11-14 17:08:02.436 DEBUG [] Scte35Decoder:641 - 74. (t)
2018-11-14 17:08:02.436 DEBUG [] Scte35Decoder:641 - 74. (t)
2018-11-14 17:08:02.436 DEBUG [] Scte35Decoder:641 - 65. (e)
2018-11-14 17:08:02.437 DEBUG [] Scte35Decoder:641 - 6E. (n)
2018-11-14 17:08:02.437 DEBUG [] Scte35Decoder:641 - 73. (s)
2018-11-14 17:08:02.437 DEBUG [] Scte35Decoder:641 - 
2018-11-14 17:08:02.437 DEBUG [] Scte35Decoder:641 - Type = Distributor Advertisement Start
2018-11-14 17:08:02.437 DEBUG [] Scte35Decoder:641 - Segment num = 0 Segments Expected = 0
2018-11-14 17:08:02.437 DEBUG [] Scte35Decoder:641 - CRC32 = 0xe6afd449
2018-11-14 17:08:02.438 DEBUG [] Scte35Decoder:641 - calc CRC32 = 0x00000000 --- Should = 0x00000000
2018-11-14 17:08:02.438 DEBUG [] Scte35SegmentationTriggersTest:45 - adBreakEnd
2018-11-14 17:08:02.440 DEBUG [] Scte35Decoder:641 - Hex=0x
2018-11-14 17:08:02.441 DEBUG [] Scte35Decoder:641 - FC305700000000000000FFF00506FE15E5C6200041023F43554549646187977FBF013050616464792773205075623A20486F6D65206F6620746865204F726967696E616C204B697474656E204D697474656E73330000E77778CE
Base64=/DBXAAAAAAAAAP/wBQb+FeXGIABBAj9DVUVJZGGHl3+/ATBQYWRkeSdzIFB1YjogSG9tZSBvZiB0aGUgT3JpZ2luYWwgS2l0dGVuIE1pdHRlbnMzAADnd3jO
2018-11-14 17:08:02.442 DEBUG [] Scte35Decoder:641 - Decoded length = 90
2018-11-14 17:08:02.442 DEBUG [] Scte35Decoder:641 - Table ID = 0xFC
2018-11-14 17:08:02.442 DEBUG [] Scte35Decoder:641 - MPEG Short Section
2018-11-14 17:08:02.442 DEBUG [] Scte35Decoder:641 - Not Private
2018-11-14 17:08:02.443 DEBUG [] Scte35Decoder:641 - Reserved = 0x3
2018-11-14 17:08:02.443 DEBUG [] Scte35Decoder:641 - Section Length = 87
2018-11-14 17:08:02.443 DEBUG [] Scte35Decoder:641 - Protocol Version = 0
2018-11-14 17:08:02.443 DEBUG [] Scte35Decoder:641 - unencrypted Packet
2018-11-14 17:08:02.443 DEBUG [] Scte35Decoder:641 - PTS Adjustment = 0x000000000
2018-11-14 17:08:02.444 DEBUG [] Scte35Decoder:641 - Tier = 0xfff
2018-11-14 17:08:02.444 DEBUG [] Scte35Decoder:641 - Splice Command Length = 0x5
2018-11-14 17:08:02.444 DEBUG [] Scte35Decoder:641 - Time Signal
2018-11-14 17:08:02.444 DEBUG [] Scte35Decoder:641 - Time = 0x015e5c620
2018-11-14 17:08:02.444 DEBUG [] Scte35Decoder:641 - Descriptor Loop Length = 65
2018-11-14 17:08:02.445 DEBUG [] Scte35Decoder:641 - Segmentation Descriptor - Length=63
2018-11-14 17:08:02.445 DEBUG [] Scte35Decoder:641 - Segmentation Event ID = 0x64618797
2018-11-14 17:08:02.445 DEBUG [] Scte35Decoder:641 - Segmentation Event Cancel Indicator NOT set
2018-11-14 17:08:02.445 DEBUG [] Scte35Decoder:641 - Delivery Not Restricted flag = 1
2018-11-14 17:08:02.445 DEBUG [] Scte35Decoder:641 - Program Segmentation flag SET
2018-11-14 17:08:02.445 DEBUG [] Scte35Decoder:641 - UPID Type = User Defined (Deprecated) length =48
Hex=0x
2018-11-14 17:08:02.446 DEBUG [] Scte35Decoder:641 - 50. (P)
2018-11-14 17:08:02.446 DEBUG [] Scte35Decoder:641 - 61. (a)
2018-11-14 17:08:02.446 DEBUG [] Scte35Decoder:641 - 64. (d)
2018-11-14 17:08:02.446 DEBUG [] Scte35Decoder:641 - 64. (d)
2018-11-14 17:08:02.447 DEBUG [] Scte35Decoder:641 - 79. (y)
2018-11-14 17:08:02.447 DEBUG [] Scte35Decoder:641 - 27. (')
2018-11-14 17:08:02.447 DEBUG [] Scte35Decoder:641 - 73. (s)
2018-11-14 17:08:02.447 DEBUG [] Scte35Decoder:641 - 20. ( )
2018-11-14 17:08:02.447 DEBUG [] Scte35Decoder:641 - 50. (P)
2018-11-14 17:08:02.448 DEBUG [] Scte35Decoder:641 - 75. (u)
2018-11-14 17:08:02.448 DEBUG [] Scte35Decoder:641 - 62. (b)
2018-11-14 17:08:02.448 DEBUG [] Scte35Decoder:641 - 3A. (:)
2018-11-14 17:08:02.448 DEBUG [] Scte35Decoder:641 - 20. ( )
2018-11-14 17:08:02.448 DEBUG [] Scte35Decoder:641 - 48. (H)
2018-11-14 17:08:02.449 DEBUG [] Scte35Decoder:641 - 6F. (o)
2018-11-14 17:08:02.449 DEBUG [] Scte35Decoder:641 - 6D. (m)
2018-11-14 17:08:02.449 DEBUG [] Scte35Decoder:641 - 65. (e)
2018-11-14 17:08:02.449 DEBUG [] Scte35Decoder:641 - 20. ( )
2018-11-14 17:08:02.450 DEBUG [] Scte35Decoder:641 - 6F. (o)
2018-11-14 17:08:02.450 DEBUG [] Scte35Decoder:641 - 66. (f)
2018-11-14 17:08:02.451 DEBUG [] Scte35Decoder:641 - 20. ( )
2018-11-14 17:08:02.451 DEBUG [] Scte35Decoder:641 - 74. (t)
2018-11-14 17:08:02.451 DEBUG [] Scte35Decoder:641 - 68. (h)
2018-11-14 17:08:02.451 DEBUG [] Scte35Decoder:641 - 65. (e)
2018-11-14 17:08:02.452 DEBUG [] Scte35Decoder:641 - 20. ( )
2018-11-14 17:08:02.452 DEBUG [] Scte35Decoder:641 - 4F. (O)
2018-11-14 17:08:02.452 DEBUG [] Scte35Decoder:641 - 72. (r)
2018-11-14 17:08:02.452 DEBUG [] Scte35Decoder:641 - 69. (i)
2018-11-14 17:08:02.453 DEBUG [] Scte35Decoder:641 - 67. (g)
2018-11-14 17:08:02.453 DEBUG [] Scte35Decoder:641 - 69. (i)
2018-11-14 17:08:02.453 DEBUG [] Scte35Decoder:641 - 6E. (n)
2018-11-14 17:08:02.453 DEBUG [] Scte35Decoder:641 - 61. (a)
2018-11-14 17:08:02.453 DEBUG [] Scte35Decoder:641 - 6C. (l)
2018-11-14 17:08:02.454 DEBUG [] Scte35Decoder:641 - 20. ( )
2018-11-14 17:08:02.454 DEBUG [] Scte35Decoder:641 - 4B. (K)
2018-11-14 17:08:02.454 DEBUG [] Scte35Decoder:641 - 69. (i)
2018-11-14 17:08:02.454 DEBUG [] Scte35Decoder:641 - 74. (t)
2018-11-14 17:08:02.454 DEBUG [] Scte35Decoder:641 - 74. (t)
2018-11-14 17:08:02.455 DEBUG [] Scte35Decoder:641 - 65. (e)
2018-11-14 17:08:02.455 DEBUG [] Scte35Decoder:641 - 6E. (n)
2018-11-14 17:08:02.455 DEBUG [] Scte35Decoder:641 - 20. ( )
2018-11-14 17:08:02.455 DEBUG [] Scte35Decoder:641 - 4D. (M)
2018-11-14 17:08:02.455 DEBUG [] Scte35Decoder:641 - 69. (i)
2018-11-14 17:08:02.455 DEBUG [] Scte35Decoder:641 - 74. (t)
2018-11-14 17:08:02.456 DEBUG [] Scte35Decoder:641 - 74. (t)
2018-11-14 17:08:02.456 DEBUG [] Scte35Decoder:641 - 65. (e)
2018-11-14 17:08:02.456 DEBUG [] Scte35Decoder:641 - 6E. (n)
2018-11-14 17:08:02.456 DEBUG [] Scte35Decoder:641 - 73. (s)
2018-11-14 17:08:02.456 DEBUG [] Scte35Decoder:641 - 
2018-11-14 17:08:02.457 DEBUG [] Scte35Decoder:641 - Type = Distributor Advertisement End
2018-11-14 17:08:02.457 DEBUG [] Scte35Decoder:641 - Segment num = 0 Segments Expected = 0
2018-11-14 17:08:02.457 DEBUG [] Scte35Decoder:641 - CRC32 = 0xe77778ce
2018-11-14 17:08:02.457 DEBUG [] Scte35Decoder:641 - calc CRC32 = 0x00000000 --- Should = 0x00000000
2018-11-14 17:08:02.457 DEBUG [] Scte35SegmentationTriggersTest:48 - programEnd
2018-11-14 17:08:02.458 DEBUG [] Scte35Decoder:641 - Hex=0x
2018-11-14 17:08:02.459 DEBUG [] Scte35Decoder:641 - FC305700000000000000FFF00506FE15E5C6200041023F43554549646187977FBF013050616464792773205075623A20486F6D65206F6620746865204F726967696E616C204B697474656E204D697474656E73110000DFD3B120
Base64=/DBXAAAAAAAAAP/wBQb+FeXGIABBAj9DVUVJZGGHl3+/ATBQYWRkeSdzIFB1YjogSG9tZSBvZiB0aGUgT3JpZ2luYWwgS2l0dGVuIE1pdHRlbnMRAADf07Eg
2018-11-14 17:08:02.459 DEBUG [] Scte35Decoder:641 - Decoded length = 90
2018-11-14 17:08:02.459 DEBUG [] Scte35Decoder:641 - Table ID = 0xFC
2018-11-14 17:08:02.459 DEBUG [] Scte35Decoder:641 - MPEG Short Section
2018-11-14 17:08:02.460 DEBUG [] Scte35Decoder:641 - Not Private
2018-11-14 17:08:02.460 DEBUG [] Scte35Decoder:641 - Reserved = 0x3
2018-11-14 17:08:02.460 DEBUG [] Scte35Decoder:641 - Section Length = 87
2018-11-14 17:08:02.460 DEBUG [] Scte35Decoder:641 - Protocol Version = 0
2018-11-14 17:08:02.460 DEBUG [] Scte35Decoder:641 - unencrypted Packet
2018-11-14 17:08:02.460 DEBUG [] Scte35Decoder:641 - PTS Adjustment = 0x000000000
2018-11-14 17:08:02.461 DEBUG [] Scte35Decoder:641 - Tier = 0xfff
2018-11-14 17:08:02.461 DEBUG [] Scte35Decoder:641 - Splice Command Length = 0x5
2018-11-14 17:08:02.461 DEBUG [] Scte35Decoder:641 - Time Signal
2018-11-14 17:08:02.461 DEBUG [] Scte35Decoder:641 - Time = 0x015e5c620
2018-11-14 17:08:02.461 DEBUG [] Scte35Decoder:641 - Descriptor Loop Length = 65
2018-11-14 17:08:02.461 DEBUG [] Scte35Decoder:641 - Segmentation Descriptor - Length=63
2018-11-14 17:08:02.462 DEBUG [] Scte35Decoder:641 - Segmentation Event ID = 0x64618797
2018-11-14 17:08:02.462 DEBUG [] Scte35Decoder:641 - Segmentation Event Cancel Indicator NOT set
2018-11-14 17:08:02.462 DEBUG [] Scte35Decoder:641 - Delivery Not Restricted flag = 1
2018-11-14 17:08:02.462 DEBUG [] Scte35Decoder:641 - Program Segmentation flag SET
2018-11-14 17:08:02.462 DEBUG [] Scte35Decoder:641 - UPID Type = User Defined (Deprecated) length =48
Hex=0x
2018-11-14 17:08:02.463 DEBUG [] Scte35Decoder:641 - 50. (P)
2018-11-14 17:08:02.463 DEBUG [] Scte35Decoder:641 - 61. (a)
2018-11-14 17:08:02.463 DEBUG [] Scte35Decoder:641 - 64. (d)
2018-11-14 17:08:02.463 DEBUG [] Scte35Decoder:641 - 64. (d)
2018-11-14 17:08:02.464 DEBUG [] Scte35Decoder:641 - 79. (y)
2018-11-14 17:08:02.464 DEBUG [] Scte35Decoder:641 - 27. (')
2018-11-14 17:08:02.464 DEBUG [] Scte35Decoder:641 - 73. (s)
2018-11-14 17:08:02.464 DEBUG [] Scte35Decoder:641 - 20. ( )
2018-11-14 17:08:02.464 DEBUG [] Scte35Decoder:641 - 50. (P)
2018-11-14 17:08:02.465 DEBUG [] Scte35Decoder:641 - 75. (u)
2018-11-14 17:08:02.465 DEBUG [] Scte35Decoder:641 - 62. (b)
2018-11-14 17:08:02.465 DEBUG [] Scte35Decoder:641 - 3A. (:)
2018-11-14 17:08:02.465 DEBUG [] Scte35Decoder:641 - 20. ( )
2018-11-14 17:08:02.465 DEBUG [] Scte35Decoder:641 - 48. (H)
2018-11-14 17:08:02.466 DEBUG [] Scte35Decoder:641 - 6F. (o)
2018-11-14 17:08:02.466 DEBUG [] Scte35Decoder:641 - 6D. (m)
2018-11-14 17:08:02.466 DEBUG [] Scte35Decoder:641 - 65. (e)
2018-11-14 17:08:02.466 DEBUG [] Scte35Decoder:641 - 20. ( )
2018-11-14 17:08:02.467 DEBUG [] Scte35Decoder:641 - 6F. (o)
2018-11-14 17:08:02.467 DEBUG [] Scte35Decoder:641 - 66. (f)
2018-11-14 17:08:02.468 DEBUG [] Scte35Decoder:641 - 20. ( )
2018-11-14 17:08:02.468 DEBUG [] Scte35Decoder:641 - 74. (t)
2018-11-14 17:08:02.468 DEBUG [] Scte35Decoder:641 - 68. (h)
2018-11-14 17:08:02.468 DEBUG [] Scte35Decoder:641 - 65. (e)
2018-11-14 17:08:02.468 DEBUG [] Scte35Decoder:641 - 20. ( )
2018-11-14 17:08:02.469 DEBUG [] Scte35Decoder:641 - 4F. (O)
2018-11-14 17:08:02.469 DEBUG [] Scte35Decoder:641 - 72. (r)
2018-11-14 17:08:02.469 DEBUG [] Scte35Decoder:641 - 69. (i)
2018-11-14 17:08:02.469 DEBUG [] Scte35Decoder:641 - 67. (g)
2018-11-14 17:08:02.470 DEBUG [] Scte35Decoder:641 - 69. (i)
2018-11-14 17:08:02.470 DEBUG [] Scte35Decoder:641 - 6E. (n)
2018-11-14 17:08:02.470 DEBUG [] Scte35Decoder:641 - 61. (a)
2018-11-14 17:08:02.470 DEBUG [] Scte35Decoder:641 - 6C. (l)
2018-11-14 17:08:02.471 DEBUG [] Scte35Decoder:641 - 20. ( )
2018-11-14 17:08:02.471 DEBUG [] Scte35Decoder:641 - 4B. (K)
2018-11-14 17:08:02.471 DEBUG [] Scte35Decoder:641 - 69. (i)
2018-11-14 17:08:02.471 DEBUG [] Scte35Decoder:641 - 74. (t)
2018-11-14 17:08:02.472 DEBUG [] Scte35Decoder:641 - 74. (t)
2018-11-14 17:08:02.472 DEBUG [] Scte35Decoder:641 - 65. (e)
2018-11-14 17:08:02.472 DEBUG [] Scte35Decoder:641 - 6E. (n)
2018-11-14 17:08:02.472 DEBUG [] Scte35Decoder:641 - 20. ( )
2018-11-14 17:08:02.473 DEBUG [] Scte35Decoder:641 - 4D. (M)
2018-11-14 17:08:02.473 DEBUG [] Scte35Decoder:641 - 69. (i)
2018-11-14 17:08:02.473 DEBUG [] Scte35Decoder:641 - 74. (t)
2018-11-14 17:08:02.473 DEBUG [] Scte35Decoder:641 - 74. (t)
2018-11-14 17:08:02.473 DEBUG [] Scte35Decoder:641 - 65. (e)
2018-11-14 17:08:02.473 DEBUG [] Scte35Decoder:641 - 6E. (n)
2018-11-14 17:08:02.474 DEBUG [] Scte35Decoder:641 - 73. (s)
2018-11-14 17:08:02.474 DEBUG [] Scte35Decoder:641 - 
2018-11-14 17:08:02.474 DEBUG [] Scte35Decoder:641 - Type = Program End
2018-11-14 17:08:02.474 DEBUG [] Scte35Decoder:641 - Segment num = 0 Segments Expected = 0
2018-11-14 17:08:02.474 DEBUG [] Scte35Decoder:641 - CRC32 = 0xdfd3b120
2018-11-14 17:08:02.474 DEBUG [] Scte35Decoder:641 - calc CRC32 = 0x00000000 --- Should = 0x00000000



references: 

https://www.scte.org/SCTEDocs/Standards/SCTE%2035%202017.pdf



todo: 	complete encoder.model.AvailDescriptor and 
		encoder.model.TimeDescriptor (for PTP inclusion)



forked from https://github.com/nfl/scte35