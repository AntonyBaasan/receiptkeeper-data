docker image build -f Dockerfile_Travis . --tag $DOCKER_REPO
docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD
docker push $DOCKER_REPO