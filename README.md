# Node.js Example on OCP

## OCP Online

Link: https://nodejs-ocp-demo-nodejs-ocp-demo.apps.us-east-2.starter.openshift-online.com/

Uses image stream method

Instructions:

1. Sign up for free Starter Plan [here](https://www.openshift.com/products/pricing/)
2. After your space provisions, create a project the online console
3. Create a Dockerfile
4. Either build the Docker image locally or link GitHub to Docker Hub and build everytime you push to a branch
5. On the OCP Console, Click Add -> Deploy Image
6. Select your image, (tks23/nodejs-ocp-demo)
7. A service which binds port 8080 to OCP's router should be automatically created
8. Map a route
   - I use TLS and redirect unsecure requests to HTTPS
   - https://nodejs-ocp-demo-nodejs-ocp-demo.apps.us-east-2.starter.openshift-online.com/

Updating:

1. Update the image in Docker Hub
2. Sync with Openshift

   `oc import-image nodejs-ocp-demo`

3. Automate the sync process with (not always instant)

   `oc import-image nodejs-ocp-demo --scheduled=true`

## OCP Minishift

Link: http://nodejs-ocp-demo-nodejs-ocp-demo.192.168.1.45.nip.io/

Uses oc cli and Docker build method

Instructions:

1. `oc project nodejs-ocp-demo`
2. `oc new-app https://github.com/shultztom/nodejs-ocp-demo.git -l name=nodejs-ocp-demo`
3. `oc expose svc/nodejs-ocp-demo`

Updating:

You can add a webhook to GitHub. However, since this instance is local, the hook from GitHub won't reach.

Manual Update: `oc start-build nodejs-ocp-demo`
