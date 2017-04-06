# AWS architecture for Abixen Platform

![Abixen Platform on AWS cloud](../documentation-image/abixen-platform-on-aws.png "Abixen Platform on AWS cloud")

Abixen Platform is fully compatible with AWS cloud and utilizes the following services:

   * **EC2** - used as a base for hosts running docker in ECS Cluster
   * **ALB** - modern version of load balancer aligned with microservices architecture topology
   * **ECS** - container orchestrator and scheduler for all services running as docker containers
   * **ECR** - private container registry for docker images
   * **Route53** - allows to use internal dns names for communication between microservices
   * **CloudWatch** - used as a central monitoring and logging
   * **Elasticache** - used internally by Abixen Platform components
   * **RDS** - database store for all components
   * **SES** - used for email communication


# Implementation

TBC
