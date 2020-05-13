# shoppingAmplify

## How to Run this Code

1. In order to run this project, git clone this repository on your computer.
2. Install amplify CLI with sudo npm install -g @aws-amplify/cli
3. Pull the correct amplify backend  amplify pull --appId d1qmius55ek1j5 --envName rebuild
  *Select Yes when asked for AWS profile, and choose default options to connect to the backend.
4. There is a bug within Amplify that may require you to remove 'apply plugin: 'com.amplifyframework.amplifytools' in the project settings, rebuild, and then add the plugin back in


## Distribution of Work


Jed - Jed did much of the UI design and table layouts as well as adding elements like taxes to transations. 

Ramya - Ramya created Amplify backend environment and connected the environment to basic front-end UI (created the navigation graph and a few of the fragments). She added QR scanning functionality for store and QR scanning functionality for products

Raymond - Raymond added user authentication, view transactions, and shopping cart functionality using shared preferences

