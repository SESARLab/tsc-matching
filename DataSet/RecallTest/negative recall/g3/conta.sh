#!/bin/bash
echo "rami 3"
rmodelt=$(cat result1.txt |grep "rmodel:true"|wc -l)
rmodelf=$(cat result1.txt |grep "rmodel:false"|wc -l)
revidencet=$(cat result1.txt |grep "revidence:true"|wc -l)
revidencef=$(cat result1.txt |grep "revidence:false"|wc -l)
echo -e "Eu-1 \t\tSuccess \t\tFail"
echo -e "model \t\t$rmodelt \t\t$rmodelf"
echo -e "evidence \t$revidencet \t\t$revidencef"
rmodelt=$(cat result2.txt |grep "rmodel:true"|wc -l)
rmodelf=$(cat result2.txt |grep "rmodel:false"|wc -l)
revidencet=$(cat result2.txt |grep "revidence:true"|wc -l)
revidencef=$(cat result2.txt |grep "revidence:false"|wc -l)
echo -e "Eu-2 \t\tSuccess \t\tFail"
echo -e "model \t\t$rmodelt \t\t$rmodelf"
echo -e "evidence \t$revidencet \t\t$revidencef"