##### Example 3.2



_Let us consider CM Template T in Example 3.1. A CM Instance I for T can include the following elements. Security property p =(Confidentiality,{ctx=in-transit/at-rest}). Target of certification ToC =({θ1,θ2,θ3},<service>), where θ1={encryption,algo=XML-encryption,protocol=WS- Security,level=message-in-transit} implements a XML-encryption mechanism based on WS-Security securing public communica- tion channels, θ2={encryption,level=internal- communications,algo=HTTPS} implements an HTTPS communication channel securing internal cloud system communications, θ3={encryption,level=at rest,algo=encrypted FS} implements an encrypted file system securing stored data._

**property.txt** specifies the property to be certified

* Confidentiality

**tocs.xml** contains the three mechanism:

1. encryption-XML-encryption-WS-Security-message-in-transit
2. encryption-internal-communications-HTTPS
3. encryption-at-rest-encrypted-FS

**model.xml** is an xml representation of the STS model in figure:

<img src="https://raw.githubusercontent.com/SESARLab/tsc-matching/master/example/example3_2/model.png" width=120px>


**evidence.xml** specifies the required input and the expected output to analyse the model execution


