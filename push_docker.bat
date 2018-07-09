docker image build . --tag receiptkeeper-data

docker login
docker push antonybaasan/receiptkeeper-data:latest