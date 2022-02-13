echo "Starting to create SQS"
awslocal sqs create-queue \
        --queue-name sample-queue

echo "Done to create infrastructure successfully!"