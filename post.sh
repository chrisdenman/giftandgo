#!/usr/bin/env bash

curl  -v \
      -H "Content-Type: text/plain" \
      -H "HTTP_X_FORWARDED_FOR: google.com" \
      --data-binary "@input.txt" \
      http://127.0.0.1:8080/peopleSpeedData
