# Atoka website screenshots

This is a small web service that given a company AtokaID stores screenshots of its websites on S3. 

## How to run
```bash
export ATOKEN=<your atoka API token>
aws configure  # if needed

./gradlew run
```


## Endpoint

```bash
curl localhost:8080/company/<atokaid>
``` 

The screenshots are saved on
```
s3://archiveitems/dev/data-innovation-bootcamp/website-screenshots/<atoka-id>/<domain>/<timestamp>.png```


