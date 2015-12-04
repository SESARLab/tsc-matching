#!/bin/bash
mkdir perf
paste eu1modelcm5.txt eu1evidencecm5.txt eu1toccm5.txt | awk '{print $1,$2,$3}' > perf/eu1cm5p.txt
paste eu1modelcm6.txt eu1evidencecm6.txt eu1toccm6.txt | awk '{print $1,$2,$3}' > perf/eu1cm6p.txt
paste eu1modelcm7.txt eu1evidencecm7.txt eu1toccm7.txt | awk '{print $1,$2,$3}' > perf/eu1cm7p.txt
paste eu1modelcm8.txt eu1evidencecm8.txt eu1toccm8.txt | awk '{print $1,$2,$3}' > perf/eu1cm8p.txt
paste eu1modelcm9.txt eu1evidencecm9.txt eu1toccm9.txt | awk '{print $1,$2,$3}' > perf/eu1cm9p.txt
paste eu1modelcm10.txt eu1evidencecm10.txt eu1toccm10.txt | awk '{print $1,$2,$3}'> perf/eu1cm10p.txt

paste eu2modelcm5.txt eu2evidencecm5.txt eu2toccm5.txt | awk '{print $1,$2,$3}' > perf/eu2cm5p.txt
paste eu2modelcm6.txt eu2evidencecm6.txt eu2toccm6.txt | awk '{print $1,$2,$3}' > perf/eu2cm6p.txt
paste eu2modelcm7.txt eu2evidencecm7.txt eu2toccm7.txt | awk '{print $1,$2,$3}' > perf/eu2cm7p.txt
paste eu2modelcm8.txt eu2evidencecm8.txt eu2toccm8.txt | awk '{print $1,$2,$3}' > perf/eu2cm8p.txt
paste eu2modelcm9.txt eu2evidencecm9.txt eu2toccm9.txt | awk '{print $1,$2,$3}' > perf/eu2cm9p.txt
paste eu2modelcm10.txt eu2evidencecm10.txt eu2toccm10.txt | awk '{print $1,$2,$3}'> perf/eu2cm10p.txt