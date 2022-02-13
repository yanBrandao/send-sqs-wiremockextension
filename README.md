# async-send-sqs-wiremockextension
Async-Send-SQS-Transform is a [Wiremock](http://wiremock.org/) extension to send sqs message in background when receive a request in stub-server to validate async backend scenarios.

In simples words, when request is defined in mapping, you can send metadata to define a message to send in background to an amazon sqs queue.
Like example below:

![extension-flow](./assets/extension-flow.drawio.png)


## Explain mapping file

To use transform, is necessary to use the following lines:

```json
{
  "request": {
    "method": "(GET, POST, or etc...)",
    "url": "/you-mock-endpoint",
    "bodyPatterns" : [
      {
        "matchesJsonPath" : "$.metadata.queue_name"
      },
      {
        "matchesJsonPath" : "$.metadata.profile"
      },
      {
        "matchesJsonPath" : "$.metadata.container_name"
      },
      {
        "matchesJsonPath" : "$.data"
      }
    ]
  },
  "response": {
    "status": "(Any status return)",
    "transformers": ["async-send-sqs"]
  }
}
```

First, in `bodyPatterns`, is necessary to receive metada object, with following attributes:

 - `queue_name` the name of queue to send to sqs
 - `profile` the profile to load correct environment, you can use `InDocker`, `Cloud` or `Local`
 - `container_name` in `InDocker` profile, is necessary to info the localstack container name.
 
 At least is necessary to send message data to `sqs`, all fields can be send in data object, like payload below:

 ```json
{
    "metadata": {
        "queue_name": "sample-queue",
        "profile": "InDocker",
        "container_name": "172.31.0.3"
    },
    "data": {
        "name": "just a test"
    }
}
 ```

 the message in your `sqs` will be like this:

 ```json

 {
    "Messages": [
        {
            "MessageId": "b2b33c93-7911-da99-1279-7b4a49360b77",
            "ReceiptHandle": "jupkihjcjvgzxsnecwlborxakjuqbicosdqyhyqbmynlocljrqjfyedooijfezknbrmxlozgimjylulclkjhkzelovshghnevsfnwjxmfsuvnvuuxvlpalnmfdttfdgjofvoipyaqwszjikqcqulxdmplqaiojavonvtvqptcrfpozdgevhvctmed",
            "MD5OfBody": "97b7e9e2435616e00f5301eb638a806b",
            "Body": "{name=\"just a test\"}",
            "Attributes": {
                "SenderId": "AIDAIT2UOQQY3AUEKVGXU",
                "SentTimestamp": "1644726125589",
                "ApproximateReceiveCount": "1",
                "ApproximateFirstReceiveTimestamp": "1644726135340"
            }
        }
    ]
}

```




