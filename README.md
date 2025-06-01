# Kubernetes Practical Guide: Manual Deployment of Spring Boot App

![Kubernetes](https://img.shields.io/badge/Kubernetes-v1.25+-326CE5?logo=kubernetes) 
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1+-6DB33F?logo=springboot)
![Docker](https://img.shields.io/badge/Docker-24.0+-2496ED?logo=docker)

**A hands-on guide for deploying Spring Boot applications to Kubernetes (K3s) with manual steps and verification**

## Kubernetes Workflow

1. Push Docker image to Docker Hub.
2. Kubernetes API receives cluster requests.
3. Scheduler assigns workload to appropriate nodes.
4. Kubelet pulls and runs containers.
5. Pods & Services expose app networking.
6. Auto-scaling & monitoring adjust resources based on traffic.

---

## Kubernetes Installation (First Time Server Setup)

### Install kubectl CLI

```bash
sudo apt update
curl -LO "https://dl.k8s.io/release/$(curl -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
chmod +x kubectl
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
kubectl version --client
```

### Install K3s (Lightweight Kubernetes)

```bash
curl -sfL https://get.k3s.io | sh -
sudo systemctl status k3s
```

### Configure kubeconfig

```bash
mkdir -p ~/.kube
sudo cp /etc/rancher/k3s/k3s.yaml ~/.kube/config
sudo chown $(whoami):$(whoami) ~/.kube/config
```

### Verify cluster

```bash
kubectl get nodes
```

## Manual Deployment Steps for Spring Boot App

## On your local computer
### 1. Package Spring Boot app

```bash
mvn clean package
```

### 2. Build Docker Image

```bash
docker build -t shamodha/vps-app:latest .
```

### 3. Login Docker Hub and Push Image

```bash
docker login
docker push shamodha/vps-app:latest
```

## On the Server (Kubernetes Server or using kubectl)

### 4. Create Deployment YAML file manually (deployment.yaml)

```bash
nano deployment.yaml
```

### Then copy and paste the deployment YAML content from this file:

[deployment.yaml](https://github.com/shamodhas/k8s-simple-deployment/blob/main/deployment.yaml)

### 5. Create Service YAML (service.yaml)

Create the `service.yaml` file manually:

```bash
nano service.yaml
```

### Then copy and paste the service YAML content from this file:

[service.yaml](https://github.com/shamodhas/k8s-simple-deployment/blob/main/service.yaml)

### 6. Deploy to Kubernetes

```bash
kubectl apply -f deployment.yaml
kubectl apply -f service.yaml
```
### 7. Verify Deployment

```bash
kubectl get deployments
kubectl get pods
kubectl get svc
```

### 8. Access Your App

#### If your environment does not support LoadBalancer, edit service.yaml:

```bash
type: NodePort
ports:
  - protocol: TCP
    port: 80
    targetPort: 8080
    nodePort: 30080
```

#### Redeploy service and access via:

```bash
http://<server-ip>:30080
```

### 9. Update App Manually

```bash
kubectl set image deployment/vps-app vps-app=shamodha/vps-app:latest
kubectl rollout status deployment/vps-app
```

### 10. Scale Pods Manually

```bash
kubectl scale deployment vps-app --replicas=3
```

### 11. Configure Auto-Scaling

```bash
kubectl autoscale deployment vps-app --cpu-percent=50 --min=2 --max=5
kubectl get hpa
```

### 12. Clean Up (Optional)

```bash
kubectl delete deployment vps-app
kubectl delete service vps-service
kubectl get all
docker rmi shamodha/vps-app:latest
```
