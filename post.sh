#!/usr/bin/env bash

curl  -v \
      -H "Content-Type: text/plain" \
      -H "x-forwarded-for: google.co.uk" \
      --data-binary "@data.csv" \
      http://127.0.0.1:8080/peopleSpeedData
