
# connect to cluster
gcloud container clusters get-credentials stagin
# create docker security secret
kubectl create secret docker-registry registrycred --docker-server=DOCKER_REGISTRY_SERVER --docker-username=DOCKER_USER --docker-password=DOCKER_PASSWORD --docker-email=DOCKER_EMAIL
# create TLS secret
kubectl create secret tls rk-data-tls-self --cert=c:\temp\tls.crt --key=c:\temp\tls.key