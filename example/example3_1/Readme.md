##### Example 3.1



_Let us consider a cloud storage service deployed on top of OpenStack, an open source IaaS solution, to be certified for property Confidentiality. A CM Template T for property Confidentiality can include the following elements. Security property p=(Confidentiality,{ctx=in-transit/at-rest}). Target of certification ToC=({θ1,θ2,θ3},<service>), where θ1={encryption,level=message-in-transit} is an encryption mechanism securing public communi- cation channels, θ2={encryption, level=internal- communications} is an encryption mechanism securing internal cloud system communications, θ3={encryption,level=data-at-rest} is an encryption mechanism securing stored data. Evidence collection model m specifies the flows of execution, which are used for test case generation, by combining mechanisms in ToC. It generates a set of test cases ev expressed in terms of test partitions DIn and DEO insisting on the above mechanisms. The life cycle automaton is fully defined in terms of states that can be assumed by a certificate with all mandatory transition conditions specified. Conditions are expressed in terms of specific aggregations on evidence and come from the certification authority._

**property.txt** specifies the property to be certified

* Confidentiality

**tocs.xml** contains the three mechanism:

1. encryption-message-in-transit
2. encryption-internal-communications
3. encryption-data-at-rest

**model.xml** is an xml representation of the STS model in figure:

<img src="https://raw.githubusercontent.com/SESARLab/tsc-matching/master/example/example3_1/model.png" width=100px>


**evidence** specifies the required input partitions and the expected output to analyse the model execution


