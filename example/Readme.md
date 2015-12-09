##### Examples

Directory "Example" contains all artifacts related to Examples 3.1, 3.2 and 3.3.
This three examples represent respectively an example of CM Template, an example of CM Intance and an example of Certificate. Other examples that we have used during the Cumulus project are available [here](https://github.com/fgaudenzi/TBprototypeInstallation) and [here](https://github.com/fgaudenzi/TBprototypeInstallation)

Example 3.1 and Example 3.2 have the same structure, they are composed of four files:


* **property.txt** contains the property to be certified;
* **tocs.xml** contains the information about ToC (including mechanisms and services);
* **model.xml** contains an xml representation of the STS model;
* **evidence.xml** contains the test cases which drive the execution.


The four files contribute to the definition of the CM template and CM Instance.

Example 3.3 enriches Example 3.1 and Example 3.2 with:

* **ws.txt** which specifies the url where service is availabe.
* **evidenceCertificate.xml** wich contains the effective results of the tests executions.

Each folder contains a more detailed description of each example.