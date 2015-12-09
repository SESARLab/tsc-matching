#!/bin/bash
mkdir perf
paste eu1modelcm5.txt eu1evidencecm5.txt eu1toccm5.txt | awk '{print $1,$2,$3}' > perf/esaustivacm5p.txt
paste eu1modelcm6.txt eu1evidencecm6.txt eu1toccm6.txt | awk '{print $1,$2,$3}' > perf/esaustivacm6p.txt
paste eu1modelcm7.txt eu1evidencecm7.txt eu1toccm7.txt | awk '{print $1,$2,$3}' > perf/esaustivacm7p.txt
paste eu1modelcm8.txt eu1evidencecm8.txt eu1toccm8.txt | awk '{print $1,$2,$3}' > perf/esaustivacm8p.txt
paste eu1modelcm9.txt eu1evidencecm9.txt eu1toccm9.txt | awk '{print $1,$2,$3}' > perf/esaustivacm9p.txt
